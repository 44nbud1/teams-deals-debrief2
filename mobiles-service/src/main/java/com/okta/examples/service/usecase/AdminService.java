package com.okta.examples.service.usecase;

import com.okta.examples.adapter.parser.Parser;
import com.okta.examples.model.status.DealsStatus;
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


    public ResponseEntity<?> createMerchant(String idUser, String idMerchant, JSONObject data, String path){

        System.out.println("Create Merchant Validation. " + Parser.toJsonString(data));
        ResponseEntity<?> check = validate.test(data, path);

        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }
        System.out.println("Create Merchant. Send data to voucher domain :" + Parser.toJsonString(data));
        ResponseEntity<?> fromVoucher = voucher.createMerchant(idUser, idMerchant, data);
        System.out.println("Create Merchant. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_CREATED, path);
//        return ResponseSuccess.wrap200(voucher, "Voucher successfully created",
//                "/api/admin/"+idMerchant+"/merchant/"+idMerchant+"/vouchers");
    }

    public ResponseEntity<?> getAllVoucher(String page, String path){

        System.out.println("Get All Voucher Validation. " +Parser.toJsonString(page));
        ResponseEntity<?> check = validate.getAllVoucher(page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.getAllVoucherAdmin(page);
        System.out.println("Get All Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Transaction history are successfully collected",
//                "/api/admin/show-all-voucher");
    }

    public ResponseEntity<?> filterVoucher(String merchantCategory, String page, String path){

        System.out.println("Filter Voucher Validation. " +Parser.toJsonString(merchantCategory + page));
        ResponseEntity<?> check = validate.filterVoucher(merchantCategory, page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.filterVoucherAdmin(merchantCategory, page);
        System.out.println("Filter Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/filter-voucher");
    }

    public ResponseEntity<?> searchVoucher(String merchantName, String page, String path){

        System.out.println("Search Voucher Validation. " +Parser.toJsonString(merchantName+page));
        ResponseEntity<?> check = validate.searchVoucher(merchantName, page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.searchVoucherAdmin(merchantName, page);
        System.out.println("Search Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }

    public ResponseEntity<?> sortVoucher(String name, String page, String path){

        System.out.println("Sort Voucher Validation. " + Parser.toJsonString(name+page));
        ResponseEntity<?> check = validate.sortVoucher(name, page, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.sortVoucherAdmin(name, page);
        System.out.println("Sort Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }

    public ResponseEntity<JSONObject> voucherDetail(String idVoucher, String path){

        System.out.println("Voucher Detail Validation. " +Parser.toJsonString(idVoucher));
        ResponseEntity<JSONObject> check = validate.voucherDetail(idVoucher, path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }

        ResponseEntity<?> fromVoucher = voucher.voucherDetail(idVoucher);
        System.out.println("Voucher Detail. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.VOUCHER_DETAIL_COLLECTED, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }

    public ResponseEntity<JSONObject> updateVoucher(String idVoucher, JSONObject data, String path){

        System.out.println("Update Voucher Validation. " +Parser.toJsonString(data));
        ResponseEntity<JSONObject> check = validate.updateVoucher(idVoucher,data,  path);
        if (!check.getStatusCode().is2xxSuccessful()){
            return check;
        }
        System.out.println("Update Voucher. Send data to voucher domain :" + Parser.toJsonString(data));
        ResponseEntity<?> fromVoucher = voucher.updateVoucher(idVoucher, data, path);
        System.out.println("Update Voucher. Receive data from voucher domain :"+ fromVoucher.getBody().toString());

        JSONObject jsonVoucher = Parser.parseJSON(fromVoucher.getBody().toString());
        String message = ""+ jsonVoucher.get("message");
        String status = ""+ jsonVoucher.get("status");

        if (!fromVoucher.getStatusCode().is2xxSuccessful()){
            if (fromVoucher.getBody().toString().toLowerCase().contains("connection refused")){
                return ResponseFailed.wrapResponse(DealsStatus.REQUEST_TIME_OUT, path);
            }
            return ResponseFailed.wrapResponseFailed(message, status, fromVoucher.getStatusCode(), path);
        }

        JSONArray voucher = (JSONArray) jsonVoucher.get("data");

        return ResponseSuccess.wrapResponse(voucher, DealsStatus.STATUS_CHANGE, path);
//        return ResponseSuccess.wrap200(voucher, "Success",
//                "/api/admin/findByMerchantName-voucher");
    }
}
