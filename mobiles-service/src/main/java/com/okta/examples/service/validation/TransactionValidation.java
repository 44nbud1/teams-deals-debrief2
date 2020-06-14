package com.okta.examples.service.validation;

import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class TransactionValidation {

    private final String regex_va = "^[\\d]{15,16}$";
    private final String regex_amount = "^(?=.*[\\d])(?!.*[\\D]).+$|^[\\d]+[.]{1}[\\d]+$";

    public ResponseEntity<?> createOrder(JSONObject data, String path){

        if (data.get("idVoucher") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> payOrder(JSONObject data, String path){

        if (data.get("idTransaction") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> payTopup(JSONObject data, String path){

        if (data.get("virtualNumber") == null || data.get("amount") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
        }

        if (!Pattern.matches(regex_va, ""+data.get("virtualNumber"))){
            return ResponseFailed.wrapResponse(DealsStatus.VIRTUAL_ACCOUNT_INVALID, path);
        }

        if (!Pattern.matches(regex_amount,""+data.get("amount"))){
            return ResponseFailed.wrapResponse(DealsStatus.AMOUNT_INVALID, path);
        }

        return ResponseSuccess.wrapOk();
    }
}
