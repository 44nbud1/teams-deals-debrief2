package dana.order.controller;

import dana.order.usecase.history.DetailedTransactionHistory;
import dana.order.usecase.history.TransactionHistory;
import dana.order.usecase.transaction.Payment;
import dana.order.usecase.transaction.PlaceOrder;
import dana.order.usecase.transaction.TOPUP;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class MobileController {

    @Autowired
    PlaceOrder placeOrder;

    @Autowired
    Payment payment;

    @Autowired
    TOPUP topup;

    @Autowired
    TransactionHistory transactionHistory;

    @Autowired
    DetailedTransactionHistory detailedTransactionHistory;

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    @PostMapping(value = "/api/user/{idUser}/transaction/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> placeOrder(@PathVariable("idUser") String idUser,
                                        @RequestBody JSONObject json,
                                        @RequestParam(value = "key", required = false) String key){
        json.put("idUser", idUser);
        json.put("key", key);
        json.put("path", "/api/user/"+idUser+"/transaction/voucher");
        return placeOrder.buyAVoucher(json);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    @PutMapping(value = "/api/user/{idUser}/transaction/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> payOrder(@PathVariable("idUser") String idUser,
                                      @RequestBody JSONObject json){
        json.put("idUser", idUser);
        json.put("path", "/api/user/"+idUser+"/transaction/voucher");
        return payment.payAVoucher(json);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    @PostMapping(value = "/api/user/{idUser}/transaction/topup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> TOPUP(@PathVariable("idUser") String idUser,
                                   @RequestBody JSONObject json,
                                   @RequestParam(value = "key", required = false) String key){
        json.put("idUser", idUser);
        json.put("key", key);
        json.put("path", "/api/user/"+idUser+"/transaction/topup");
        return topup.execute(json);
    }

    @GetMapping(value = "/api/user/{idUser}/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transactionHistory(@PathVariable("idUser") String idUser,
                                                @RequestParam(value = "category", required = false) String category,
                                                @RequestParam(value = "filter-start-date", required = false) String startDate,
                                                @RequestParam(value = "filter-end-date", required = false) String endDate,
                                                @RequestParam(value = "page", required = false) String page){
        JSONObject json = new JSONObject();
        json.put("idUser", idUser);
        json.put("category", category);
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        json.put("page", page);
        json.put("path", "/api/user/"+idUser+"/transaction");
        return transactionHistory.get(json);
    }

    @GetMapping(value = "/api/user/{idUser}/transaction/{idTransaction}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detailedTransactionHistory(@PathVariable("idUser") String idUser,
                                                        @PathVariable("idTransaction") String idTransaction){
        JSONObject json = new JSONObject();
        json.put("idUser", idUser);
        json.put("idTransaction", idTransaction);
        json.put("path", "/api/user/"+idUser+"/transaction/"+idTransaction);
        return detailedTransactionHistory.get(json);
    }
}
