package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.*;
import dana.order.usecase.exception.PaymentFailedException;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.HistoryRepository;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class DetailedTransactionHistory {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    HistoryRepository historyRepository;

    public ResponseEntity<?> get(JSONObject json){

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, ""+json.get("path"));
        }

        Transaction transaction = databaseMapper.getTransactionById(Integer.valueOf(""+json.get("idTransaction")));

        if (transaction == null){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_NOT_FOUND, null, ""+json.get("path"));
        }

        if (!transaction.getIdUser().equals(""+json.get("idUser"))){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_WRONG_USER, null, ""+json.get("path"));
        }

        databaseMapper.fallingAllExpiredTransaction();

        JSONObject transactionDetails = historyRepository.getUserDetailedHistory(transaction.getIdTransaction());

        return ResponseWrapper.wrap(DealsStatus.TRANSACTION_HISTORY_COLLECTED, transactionDetails, ""+json.get("path"));
    }
}
