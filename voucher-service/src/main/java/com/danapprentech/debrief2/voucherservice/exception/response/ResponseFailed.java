package com.danapprentech.debrief2.voucherservice.exception.response;

import com.danapprentech.debrief2.voucherservice.exception.statusexception.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseFailed {

    public static ResponseEntity<JSONObject> wrapResponseFailed(String message,
                                                       String status, HttpStatus httpStatus,
                                                       String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", new Date());
        json.put("status", status);
        json.put("data", null);
        json.put("message", message);
        json.put("path", path);
        System.out.println(message+" "+ new Date());
        return new ResponseEntity<>(json, httpStatus);
    }

    public static ResponseEntity<JSONObject> wrapResponse(DealsStatus dealsStatus, String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", new Date());
        json.put("status", dealsStatus.getValue());
        json.put("data", null);
        json.put("message", dealsStatus.getMessage());
        json.put("path", path);
        System.out.println(dealsStatus.getMessage()+" "+ new Date());
        return new ResponseEntity<>(json, dealsStatus.getStatus());
    }

    public static ResponseEntity<JSONObject> unAuthorized(String path){
        JSONObject json = new JSONObject();
        DealsStatus dealsStatus = DealsStatus.NOT_AUTHORIZED;
        json.put("timestamp", new Date());
        json.put("status", dealsStatus.getValue());
        json.put("data", null);
        json.put("message", dealsStatus.getMessage());
        json.put("path", path);
        System.out.println(dealsStatus.getMessage()+" "+ new Date());
        return new ResponseEntity<>(json, dealsStatus.getStatus());
    }

}
