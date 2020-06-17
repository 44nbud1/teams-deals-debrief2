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

        DealsStatus validation = validateBuyAVoucher.check(json);
        if (!validation.getStatus().is2xxSuccessful()){
            return ResponseWrapper.wrap(validation, null, ""+json.get("path"));
        }

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.USER_NOT_FOUND, null, ""+json.get("path"));
        }

        if (voucherRepository.validateExpiration(Integer.valueOf(""+json.get("idVoucher"))) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_NOT_AVAILABLE, null, ""+json.get("path"));
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
            if (transactionRepository.validateVoucherConsistency(Integer.valueOf(""+json.get("idVoucher"))) == Boolean.FALSE){
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

        if (voucherRepository.validateQuantity(Integer.valueOf(""+json.get("idVoucher"))) == Boolean.FALSE){
            return ResponseWrapper.wrap(DealsStatus.VOUCHER_OUT_OF_STOCK, null, ""+json.get("path"));
        }

        voucherRepository.insertNewOrder(""+json.get("idUser"), Integer.valueOf(""+json.get("idVoucher")));

        Transaction transaction = databaseRepository.getLatestUserInProgressTransaction(""+json.get("idUser"));
        JSONObject result = new JSONObject();
        result.put("idTransaction", transaction.getIdTransaction());

        return ResponseWrapper.wrap(DealsStatus.TRANSACTION_CREATED, result, ""+json.get("path"));
    }
}
