package com.okta.examples.service.usecase;

import com.okta.examples.adapter.parser.Parser;
import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import com.okta.examples.service.microservice.VoucherDomain;
import com.okta.examples.service.validation.VoucherValidation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    @Autowired
    VoucherDomain voucher;

    @Autowired
    VoucherValidation validate;

    public ResponseEntity<?> getAllVoucher(String page, String path){

        ResponseEntity<?> check = validate.getAllVoucher(page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.getAllVoucher(page, path);
        System.out.println("Get All Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);

//        return ResponseSuccess.wrap200(voucher, "Success.",
//                "/api/user/show-all-voucher");
    }

    public ResponseEntity<?> filterVoucher(String merchantCategory, String page, String path){

        ResponseEntity<?> check = validate.filterVoucher(merchantCategory, page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.filterVoucher(merchantCategory, page);
        System.out.println("Filter Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
    }

    public ResponseEntity<?> searchVoucher(String merchantName, String page, String path){

        ResponseEntity<?> check = validate.searchVoucher(merchantName, page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.searchVoucher(merchantName, page);
        System.out.println("Search Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
    }

    public ResponseEntity<?> sortVoucher(String name, String page, String path){

        ResponseEntity<?> check = validate.sortVoucher(name, page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.sortVoucher(name, page);
        System.out.println("Sort Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
    }
}
