package com.okta.examples.service.usecase;

import com.okta.examples.adapter.parser.Parser;
import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.request.CreateMerchantRequest;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.model.response.ResponseSuccess;
import com.okta.examples.service.microservice.VoucherDomain;
import com.okta.examples.service.validation.AdminValidation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminService {

    @Autowired
    VoucherDomain voucher;

    @Autowired
    AdminValidation validate;

    public ResponseEntity<?> createMerchant(String idUser, String idMerchant, CreateMerchantRequest createMerchantRequest, String path){

        System.out.println("Create Merchant. " +new Date());
        ResponseEntity<?> check = validate.createMerchant(createMerchantRequest, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }
        System.out.println("Create Merchant. Send data to voucher domain :" + Parser.toJsonString(createMerchantRequest));
        ResponseEntity<?> fromVoucher = voucher.createMerchant(idUser, idMerchant, createMerchantRequest);
        System.out.println("Get All Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_CREATED, path);
//        return ResponseSuccess.wrap200(voucher, "Voucher successfully created",
//                "/api/admin/"+idMerchant+"/merchant/"+idMerchant+"/vouchers");
    }

    public ResponseEntity<?> getAllVoucher(String page, String path){

        ResponseEntity<?> fromVoucher = voucher.getAllVoucherAdmin(page);
        System.out.println("Get All Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Transaction history are successfully collected",
//                "/api/admin/show-all-voucher");
    }

    public ResponseEntity<?> filterVoucher(String merchantCategory, String page, String path){

        ResponseEntity<?> fromVoucher = voucher.filterVoucherAdmin(merchantCategory, page);
        System.out.println("Filter Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/filter-voucher");
    }

    public ResponseEntity<?> searchVoucher(String merchantName, String page, String path){

        ResponseEntity<?> fromVoucher = voucher.searchVoucherAdmin(merchantName, page);
        System.out.println("Filter Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }

    public ResponseEntity<?> sortVoucher(String name, String page, String path){

        ResponseEntity<?> fromVoucher = voucher.sortVoucherAdmin(name, page);
        System.out.println("Filter Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }

    public ResponseEntity<?> voucherDetail(String idVoucher, String path){

        ResponseEntity<?> fromVoucher = voucher.voucherDetail(idVoucher);
        System.out.println("Voucher Detail. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }
}
