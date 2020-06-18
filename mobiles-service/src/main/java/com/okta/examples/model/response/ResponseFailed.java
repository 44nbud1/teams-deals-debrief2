package com.okta.examples.model.response;

import com.okta.examples.adapter.clock.Timestamps;
import com.okta.examples.model.status.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseFailed {

    public static ResponseEntity<JSONObject> wrapResponseFailed(String message,
                                                       String status,
                                                       HttpStatus httpStatus,
                                                       String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", Timestamps.getNow());
        json.put("status", status);
        json.put("data", null);
        json.put("message", message);
        json.put("path", path);
        System.out.println(message+" "+ new Date());
        return new ResponseEntity<>(json, httpStatus);
    }

    public static ResponseEntity<JSONObject> wrapResponse(DealsStatus dealsStatus, String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", Timestamps.getNow());
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
        json.put("timestamp", Timestamps.getNow());
        json.put("status", dealsStatus.getValue());
        json.put("data", null);
        json.put("message", dealsStatus.getMessage());
        json.put("path", path);
        System.out.println(dealsStatus.getMessage()+" "+ new Date());
        return new ResponseEntity<>(json, dealsStatus.getStatus());
    }

}