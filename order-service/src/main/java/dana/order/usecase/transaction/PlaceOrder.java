package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.DealsStatus;
import dana.order.entity.Transaction;
import dana.order.usecase.port.*;
import dana.order.usecase.validate.ValidateBuyAVoucher;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrder{

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidateBuyAVoucher validateBuyAVoucher;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DatabaseRepository databaseRepository;

    public ResponseEntity<?> buyAVoucher(JSONObject json){

        String path = ""+json.get("path");

        DealsStatus validation = validateBuyAVoucher.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, path);
        }

        String idUser = ""+json.get("idUser");
        Integer idVoucher = Integer.valueOf(""+json.get("idVoucher"));

        if (userRepository.doesUserExist(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, path);
        }

        if (voucherRepository.validateExpiration(idVoucher) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_NOT_AVAILABLE, null, path);
        }

        if (transactionRepository.validateBalanceConsistency(idUser) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, path);
        }

        if (transactionRepository.validateVoucherConsistency(idVoucher) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CANT_PROCESS, null, path);
        }

        if (voucherRepository.validateQuantity(idVoucher) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_OUT_OF_STOCK, null, path);
        }

        Integer idTransaction = voucherRepository.insertNewOrder(idUser, idVoucher);

        Transaction transaction = databaseRepository.getTransactionById(idTransaction);

        JSONObject result = new JSONObject();
        result.put("idTransaction", transaction.getIdTransaction());

        return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CREATED, result, path);
    }
}
