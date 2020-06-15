package com.okta.examples.service.validation;

import com.okta.examples.model.status.DealsStatus;
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
    private final String regex_double = "^(?=.*[\\d])(?!.*[\\D]).+$|^[\\d]+[.]{1}[\\d]+$";

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

    public ResponseEntity<JSONObject> test(JSONObject data, String path){
        if (data == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_NAME, path);
        }
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
        System.out.println();
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
//        if (Integer.parseInt(page) < 0){
//            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
//        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> searchVoucher(String merchantName, String page, String path){
        if (merchantName == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (page == null){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
        if (!Pattern.matches(regex_integer, page)){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
//        if (Integer.parseInt(page) < 0){
//            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
//        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> sortVoucher(String name, String page, String path){
        if (name == null){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
        }
        if (page == null){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
        }
        if (!Pattern.matches(regex_integer, page)){
            return ResponseFailed.wrapResponse(DealsStatus.PAGE_NOT_FOUND, path);
        }
//        if (Integer.parseInt(page) < 0){
//            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
//        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> voucherDetail(String idVoucher, String path){
        if (idVoucher == null){
            return ResponseFailed.wrapResponse(DealsStatus.VOUCHER_NOT_FOUND, path);
        }
        if (!Pattern.matches(regex_integer, idVoucher) ){
            return ResponseFailed.wrapResponse(DealsStatus.VOUCHER_NOT_FOUND, path);
        }
        return ResponseSuccess.wrapOk();
    }

    public ResponseEntity<?> updateVoucher(String idVoucher, JSONObject data, String path){

        if (idVoucher == null){
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
        }
        if (data == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_STATUS, path);
        }
        if (data.get("status") == null){
            return ResponseFailed.wrapResponse(DealsStatus.FILL_STATUS, path);
        }
        if (!(""+data.get("status")).equals("false")) {
            if(!(""+data.get("status")).equals("true")){
                return ResponseFailed.wrapResponse(DealsStatus.STATUS_INVALID, path);
            }
        }
        if (data.get("quota") != null) {
            if (!Pattern.matches(regex_integer, ""+data.get("quota"))) {
                return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, path);
            }
        }
        return ResponseSuccess.wrapOk();
    }

    private Boolean checkDate(String date){
//        String regex = "^[\\d]{4}[-]{1}[\\d]{2}[-]{1}[\\d]{2}+$";
//        if(!Pattern.matches(regex, date)){
//            return Boolean.FALSE;
//        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setLenient(false);
        try {
            df.parse(date);
            return Boolean.TRUE;
        } catch (ParseException e) {
            return Boolean.FALSE;
        }
    }
}
