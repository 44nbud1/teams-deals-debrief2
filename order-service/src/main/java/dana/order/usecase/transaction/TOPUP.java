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

    public JSONObject execute(JSONObject json){

        validateTOPUP.check(json);

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            throw new UserException(DealsStatus.USER_NOT_FOUND);
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
            throw new TOPUPFailedException(DealsStatus.TRANSACTION_CANT_PROCESS);
        }

        if (Double.valueOf(""+json.get("amount")) < 10000){
            throw new TOPUPFailedException(DealsStatus.MINIMUM_TOPUP);
        }

        User user = databaseMapper.getUserById(""+json.get("idUser"));

        if (user.getBalance() + Double.valueOf(""+json.get("amount")) > 1000000){
            throw new TOPUPFailedException(DealsStatus.MAXIMUM_BALANCE);
        }

        String partyCode = transactionRepository.getPartyCode(""+json.get("virtualNumber"));
        String phoneNumber = transactionRepository.getPhoneNumberFromVA(""+json.get("virtualNumber"));

        if (userRepository.doesPhoneNumberCorrect(""+json.get("idUser"), phoneNumber) == Boolean.FALSE){
            throw new TOPUPFailedException(DealsStatus.VIRTUAL_ACCOUNT_INVALID);
        }

        if (transactionRepository.checkTOPUPThirdParty(partyCode) == Boolean.FALSE){
            throw new TOPUPFailedException(DealsStatus.MERCHANT_NOT_AVAILABLE);
        }

        transactionRepository.TOPUPBalance(""+json.get("idUser"), Double.valueOf(""+json.get("amount")),
                ""+json.get("virtualNumber"), partyCode);

        Transaction transaction = databaseMapper.getLatestUserSuccessfulTransaction(""+json.get("idUser"));

        transactionBroadcaster.send(transaction.getIdTransaction());

        return ResponseWrapper.wrap("Your TOPUP is successful.", 201, null);
    }
}
