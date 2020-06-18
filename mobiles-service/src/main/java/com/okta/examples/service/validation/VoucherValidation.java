package com.okta.examples.service.validation;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class VoucherValidation {

    private final String regex_integer = "^[\\d]+$";

    public ResponseEntity<JSONObject> getAllVoucher(String page, String path){
        if (page == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (!Pattern.matches(regex_integer, page)){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> filterVoucher(String merchantCategory, String page, String path){
        if (merchantCategory == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (page == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (!Pattern.matches(regex_integer, page)){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> searchVoucher(String merchantName, String page, String path){
        if (merchantName == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (page == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (!Pattern.matches(regex_integer, page)){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<JSONObject> sortVoucher(String name, String page, String path){
        if (name == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_SORT_CRITERIA, path);
        }
        if (page == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (!Pattern.matches(regex_integer, page)){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        return ResponseSuccess.wrapOk();
    }
}
