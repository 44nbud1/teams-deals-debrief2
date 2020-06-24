package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.usecase.broadcast.TransactionBroadcaster;
import dana.order.usecase.port.DatabaseRepository;
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
    UserRepository userRepository;

    @Autowired
    TransactionBroadcaster transactionBroadcaster;

    public ResponseEntity<?> execute(JSONObject json){

        String path = ""+json.get("path");
        String key = (json.get("key") == null) ? null : ""+json.get("key");

        if (key != null && userRepository.getUniqueKey(key) != null){
            return new ResponseEntity<>(userRepository.getUniqueKey(key), HttpStatus.CREATED);
        }

        DealsStatus validation = validateTOPUP.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, path);
        }

        String idUser = ""+json.get("idUser");
        String virtualNumber = ""+json.get("virtualNumber");
        Double amount = Double.valueOf(""+json.get("amount"));

        if (userRepository.doesUserExist(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, path);
        }

        if (transactionRepository.validateBalanceConsistency(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, path);
        }

        if (amount < 10000){
            return ResponseWrapper.wrap(DealsStatus.MINIMUM_TOPUP, null, path);
        }

        User user = userRepository.getUserById(idUser);

        if (user.getBalance() + amount > 1000000){
            return ResponseWrapper.wrap(DealsStatus.MAXIMUM_BALANCE, null, path);
        }

        String partyCode = transactionRepository.getPartyCode(virtualNumber);
        String phoneNumber = transactionRepository.getPhoneNumberFromVA(virtualNumber);

        if (userRepository.doesPhoneNumberCorrect(idUser, phoneNumber) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.VIRTUAL_ACCOUNT_INVALID, null, path);
        }

        if (transactionRepository.checkTOPUPThirdParty(partyCode) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.MERCHANT_NOT_AVAILABLE, null, path);
        }

        Integer idTransaction = transactionRepository.TOPUPBalance(idUser, amount, virtualNumber, partyCode);

        Transaction transaction = transactionRepository.getTransactionById(idTransaction);

        transactionBroadcaster.send(transaction.getIdTransaction());

        String response = ResponseWrapper.jsonWrap(DealsStatus.TOPUP_SUCCESS, null, path).toString();

        if (key != null){
            userRepository.addUniqueKey(key, response);
        }

        return ResponseWrapper.wrap(DealsStatus.TOPUP_SUCCESS, null, path);
    }
}
