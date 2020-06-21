package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.*;
import dana.order.usecase.port.*;
import dana.order.usecase.validate.ValidateDetailedTransactionHistory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DetailedTransactionHistory {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DatabaseRepository databaseRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    ValidateDetailedTransactionHistory validateDetailedTransactionHistory;

    public ResponseEntity<?> get(JSONObject json){

        String path = ""+json.get("path");

        DealsStatus validation = validateDetailedTransactionHistory.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, path);
        }

        String idUser = ""+json.get("idUser");
        Integer idTransaction = Integer.valueOf(""+json.get("idTransaction"));

        if (userRepository.doesUserExist(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, path);
        }

        Transaction transaction = databaseRepository.getTransactionById(idTransaction);

        if (transaction == null){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_NOT_FOUND, null, path);
        }

        if (!transaction.getIdUser().equals(idUser)){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_NOT_FOUND, null, path);
        }

        databaseRepository.fallingAllExpiredTransaction();

        JSONObject transactionDetails = historyRepository.getUserDetailedHistory(transaction.getIdTransaction());

        return ResponseWrapper.wrap(DealsStatus.DETAILED_HISTORY_COLLECTED, transactionDetails, path);
    }
}
