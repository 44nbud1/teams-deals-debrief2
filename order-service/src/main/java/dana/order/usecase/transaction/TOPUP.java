package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.usecase.broadcast.TransactionBroadcaster;
import dana.order.usecase.exception.OrderFailedException;
import dana.order.usecase.exception.TOPUPFailedException;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.validate.ValidateTOPUP;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TOPUP {

    @Autowired
    ValidateTOPUP validateTOPUP;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionBroadcaster transactionBroadcaster;

    public ResponseEntity<?> execute(JSONObject json){

        DealsStatus validation = validateTOPUP.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, ""+json.get("path"));
        }

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, ""+json.get("path"));
        }

        Boolean consistency = Boolean.FALSE;
        Integer counter = 0;
        while (consistency == Boolean.FALSE && counter < 3){
            if (transactionRepository.validateBalanceConsistency(""+json.get("idUser")) == Boolean.FALSE){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){e.printStackTrace();}
            }else {
                consistency = Boolean.TRUE;
            }
            counter += 1;
        }

        if (consistency == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, ""+json.get("path"));
        }

        if (Double.valueOf(""+json.get("amount")) < 10000){
            return ResponseWrapper.wrap(DealsStatus.MINIMUM_TOPUP, null, ""+json.get("path"));
        }

        User user = databaseMapper.getUserById(""+json.get("idUser"));

        if (user.getBalance() + Double.valueOf(""+json.get("amount")) > 1000000){
            return ResponseWrapper.wrap(DealsStatus.MAXIMUM_BALANCE, null, ""+json.get("path"));
        }

        String partyCode = transactionRepository.getPartyCode(""+json.get("virtualNumber"));
        String phoneNumber = transactionRepository.getPhoneNumberFromVA(""+json.get("virtualNumber"));

        if (userRepository.doesPhoneNumberCorrect(""+json.get("idUser"), phoneNumber) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.VIRTUAL_ACCOUNT_INVALID, null, ""+json.get("path"));
        }

        if (transactionRepository.checkTOPUPThirdParty(partyCode) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.MERCHANT_NOT_AVAILABLE, null, ""+json.get("path"));
        }

        transactionRepository.TOPUPBalance(""+json.get("idUser"), Double.valueOf(""+json.get("amount")),
                ""+json.get("virtualNumber"), partyCode);

        Transaction transaction = databaseMapper.getLatestUserSuccessfulTransaction(""+json.get("idUser"));

        transactionBroadcaster.send(transaction.getIdTransaction());

        return ResponseWrapper.wrap(DealsStatus.TOPUP_SUCCESS, null, ""+json.get("path"));
    }
}
