package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.usecase.port.DatabaseMapper;
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
    DatabaseMapper databaseMapper;

    public ResponseEntity<?> get(JSONObject json){

        DealsStatus validation = validateTransactionHistory.check(json);

        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, ""+json.get("path"));
        }

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, ""+json.get("path"));
        }

        databaseMapper.fallingAllExpiredTransaction();

        JSONObject result = historyRepository.getUserHistory(json);

        return ResponseWrapper.wrap(DealsStatus.TRANSACTION_HISTORY_COLLECTED, result, ""+json.get("path"));
    }
}
