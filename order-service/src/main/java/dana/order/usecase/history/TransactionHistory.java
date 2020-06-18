package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.DatabaseRepository;
import dana.order.usecase.port.HistoryRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.validate.ValidateTransactionHistory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistory {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidateTransactionHistory validateTransactionHistory;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    DatabaseRepository databaseRepository;

    public ResponseEntity<?> get(JSONObject json){

        String idUser = ""+json.get("idUser");
        String path = ""+json.get("path");

        DealsStatus validation = validateTransactionHistory.check(json);

        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, path);
        }

        if (userRepository.doesUserExist(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, path);
        }

        databaseRepository.fallingAllExpiredTransaction();

        JSONObject result = historyRepository.getUserHistory(json);

        return ResponseWrapper.wrap(DealsStatus.TRANSACTION_HISTORY_COLLECTED, result, path);
    }
}
