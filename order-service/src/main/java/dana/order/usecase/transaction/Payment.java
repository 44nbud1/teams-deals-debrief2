package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.usecase.broadcast.TransactionBroadcaster;
import dana.order.usecase.exception.OrderFailedException;
import dana.order.usecase.exception.PaymentFailedException;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.port.VoucherRepository;
import dana.order.usecase.validate.ValidatePayAVoucher;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Payment {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidatePayAVoucher validatePayAVoucher;

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionBroadcaster transactionBroadcaster;

    public ResponseEntity<?> payAVoucher(JSONObject json){

        DealsStatus validation = validatePayAVoucher.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, ""+json.get("path"));
        }

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

        if (transaction.getIdTransactionStatus() != 4){
            return ResponseWrapper.wrap(DealsStatus.ALREADY_FINISH_TRANSACTION, null, ""+json.get("path"));
        }

        if (voucherRepository.validateExpiration(transaction.getIdGoods()) == Boolean.FALSE){
            databaseMapper.fallingATransaction(""+json.get("idUser"), Integer.valueOf(""+json.get("idTransaction")));
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_NOT_AVAILABLE, null, ""+json.get("path"));
        }

        if (transactionRepository.checkATransactionExpiration(Integer.valueOf(""+json.get("idTransaction"))) == Boolean.FALSE){
            databaseMapper.fallingATransaction(""+json.get("idUser"), Integer.valueOf(""+json.get("idTransaction")));
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_EXPIRED, null, ""+json.get("path"));
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

        consistency = Boolean.FALSE; counter = 0;
        while (consistency == Boolean.FALSE && counter < 3){
            if (transactionRepository.validateVoucherConsistency(transaction.getIdGoods()) == Boolean.FALSE){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){e.printStackTrace();}
            }else{
                consistency = Boolean.TRUE;
            }
            counter += 1;
        }

        if (consistency == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, ""+json.get("path"));
        }

        if (voucherRepository.validateQuantity(transaction.getIdGoods()) == Boolean.FALSE){
            databaseMapper.fallingATransaction(""+json.get("idUser"), Integer.valueOf(""+json.get("idTransaction")));
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_OUT_OF_STOCK, null, ""+json.get("path"));
        }

        User user = databaseMapper.getUserById(""+json.get("idUser"));

        if (user.getBalance() - transaction.getAmount() < 0){
            return ResponseWrapper.wrap(DealsStatus.BALANCE_NOT_ENOUGH, null, ""+json.get("path"));
        }

        transactionRepository.setFinishATransaction(Integer.valueOf(""+json.get("idTransaction")));
        transactionBroadcaster.send(Integer.valueOf(""+json.get("idTransaction")));

        return ResponseWrapper.wrap(DealsStatus.PAYMENT_SUCCESS, null, ""+json.get("path"));
    }
}
