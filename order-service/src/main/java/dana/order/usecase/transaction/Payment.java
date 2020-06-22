package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.usecase.broadcast.TransactionBroadcaster;
import dana.order.usecase.port.*;
import dana.order.usecase.validate.ValidatePayAVoucher;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    TransactionRepository transactionRepository;

    @Autowired
    TransactionBroadcaster transactionBroadcaster;

    public ResponseEntity<?> payAVoucher(JSONObject json){

        String path = ""+json.get("path");

        DealsStatus validation = validatePayAVoucher.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, path);
        }

        String idUser = ""+json.get("idUser");
        Integer idTransaction = Integer.valueOf(""+json.get("idTransaction"));

        if (userRepository.doesUserExist(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, path);
        }

        Transaction transaction = transactionRepository.getTransactionById(idTransaction);

        if (transaction == null){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_NOT_FOUND, null, path);
        }

        if (!transaction.getIdUser().equals(""+json.get("idUser"))){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_NOT_FOUND , null, path);
        }

        if (transaction.getIdTransactionStatus() != 4){
            return ResponseWrapper.wrap(DealsStatus.ALREADY_FINISH_TRANSACTION, null, path);
        }

        if (voucherRepository.validateExpiration(transaction.getIdGoods()) == Boolean.FALSE){
            transactionRepository.fallingATransaction(idUser, idTransaction);
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_NOT_AVAILABLE, null, path);
        }

        if (transactionRepository.checkATransactionExpiration(idTransaction) == Boolean.FALSE){
            transactionRepository.fallingATransaction(idUser, idTransaction);
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_EXPIRED, null, path);
        }

        if (transactionRepository.validateBalanceConsistency(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, path);
        }

        if (transactionRepository.validateVoucherConsistency(transaction.getIdGoods()) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, path);
        }

        if (voucherRepository.validateQuantity(transaction.getIdGoods()) == Boolean.FALSE){
            transactionRepository.fallingATransaction(idUser, idTransaction);
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_OUT_OF_STOCK, null, path);
        }

        User user = userRepository.getUserById(idUser);

        if (user.getBalance() - transaction.getAmount() < 0){
            return ResponseWrapper.wrap(DealsStatus.BALANCE_NOT_ENOUGH, null, path);
        }

        if (transaction.getIdGoods() == 2){
            // Failed case
            transactionRepository.fallingATransaction(idUser, idTransaction);
            return ResponseWrapper.wrap(DealsStatus.PAYMENT_SUCCESS, null, path);
        }

        transactionRepository.setFinishATransaction(idTransaction);

        transactionBroadcaster.send(idTransaction);

        if (transaction.getIdGoods() == 1){
            // Refund case
            Transaction newest = transactionRepository.setRefund(transaction.getIdUser(), transaction.getAmount(), transaction.getIdGoods());
            transactionBroadcaster.send(newest.getIdTransaction());
        }

        return ResponseWrapper.wrap(DealsStatus.PAYMENT_SUCCESS, null, path);
    }
}
