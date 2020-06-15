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

    public JSONObject get(JSONObject json){

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            throw new UserException(DealsStatus.USER_NOT_FOUND);
        }

        Transaction transaction = databaseMapper.getTransactionById(Integer.valueOf(""+json.get("idTransaction")));

        if (transaction == null){
            throw new PaymentFailedException(DealsStatus.TRANSACTION_NOT_FOUND);
        }

        if (!transaction.getIdUser().equals(""+json.get("idUser"))){
            throw new PaymentFailedException(DealsStatus.TRANSACTION_WRONG_USER);
        }

        databaseMapper.fallingAllExpiredTransaction();

        JSONObject transactionDetails = historyRepository.getUserDetailedHistory(transaction.getIdTransaction());

        return ResponseWrapper.wrap("Your transaction history has been collected", 200, transactionDetails);
    }
}
