package com.okta.examples.service.validation;

import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.request.CreateMerchantRequest;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminValidation {

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
}
