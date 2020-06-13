package com.okta.examples.service.validation;

import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.request.CreateMerchantRequest;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

@Service
public class AdminValidation {

    private final String regex_integer = "^[\\d]+$";
    private final String regex_double = "^(?=.*[\\d])(?!.*[\\D]).+$|^[\\d][.][\\d]+$";

    public ResponseEntity<?> createMerchant(CreateMerchantRequest createMerchantRequest, String path){

//        if (createMerchantRequest.getVoucherName() == null || createMerchantRequest.getVoucherPrice() == null ||
//            createMerchantRequest.getDiscount() == null || createMerchantRequest.getMaxDiscount() == null ||
//            createMerchantRequest.getQuota()== null || createMerchantRequest.getExpiredDate() == null ||
//            createMerchantRequest.getStatus() == null){
//            return ResponseFailed.wrapResponse(DealsStatus.FILL_ALL_FORMS, path);
//        }

        if (createMerchantRequest.getVoucherName() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_NAME, path);
        }
        if (createMerchantRequest.getVoucherPrice() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_PRICE, path);
        }
        if (createMerchantRequest.getDiscount() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_DISCOUNT, path);
        }
        if (createMerchantRequest.getMaxDiscount() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_MAX_DISCOUNT, path);
        }
        if (createMerchantRequest.getQuota() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_QUOTA, path);
        }
        if (createMerchantRequest.getExpiredDate() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_EXPIRED_DATE, path);
        }
        if (createMerchantRequest.getStatus() == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_STATUS, path);
        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> test(JSONObject data, String path){
        if (data.get("voucherName") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_NAME, path);
        }
        if (data.get("voucherPrice") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_PRICE, path);
        }
        if (data.get("discount") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_DISCOUNT, path);
        }
        if (data.get("maxDiscount") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_MAX_DISCOUNT, path);
        }
        if (data.get("quota") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_QUOTA, path);
        }
        if (data.get("expiredDate") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_EXPIRED_DATE, path);
        }
        if (data.get("status") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_STATUS, path);
        }
        if (!Pattern.matches(regex_double, ""+data.get("discount")) ||
                !Pattern.matches(regex_double, ""+data.get("maxDiscount")) ||
                !Pattern.matches(regex_double, ""+data.get("voucherPrice")) ||
                !Pattern.matches(regex_integer, ""+data.get("quota")) ||
                !checkDate(""+data.get("expiredDate"))){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
        }

        if (!(""+data.get("status")).equals("false")) {
            if(!(""+data.get("status")).equals("true")){
                return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
            }
        }
        return ResponseSuccess.wrapOk();
    }

    private Boolean checkDate(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            df.parse(date);
            return Boolean.TRUE;
        } catch (ParseException e) {
            return Boolean.FALSE;
        }
    }
}
