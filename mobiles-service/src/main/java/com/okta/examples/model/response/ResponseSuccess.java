package com.okta.examples.model.response;

import com.okta.examples.adapter.clock.Timestamps;
import com.okta.examples.model.status.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess {
    public static JSONObject wrap200(Object data, String message, String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", Timestamps.getNow());
        json.put("status", Integer.valueOf(200));
        json.put("data", data);
        json.put("message", message);
        json.put("path", path);

        return json;
    }

    public static ResponseEntity<JSONObject> wrapResponseSuccess(Object data, String message, int status, HttpStatus httpStatus, String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", Timestamps.getNow());
        json.put("status", status);
        json.put("data", data);
        json.put("message", message);
        json.put("path", path);

        return new ResponseEntity<>(json, httpStatus);
    }

    public static ResponseEntity<JSONObject> wrapResponse(Object data, DealsStatus dealsStatus, String path){
        JSONObject json = new JSONObject();
        json.put("timestamp", Timestamps.getNow());
        json.put("status", dealsStatus.getValue());
        json.put("data", data);
        json.put("message", dealsStatus.getMessage());
        json.put("path", path);

        return new ResponseEntity<>(json, dealsStatus.getStatus());
    }

    public static ResponseEntity<JSONObject> wrapOk(){
        return new ResponseEntity<>(new JSONObject(), HttpStatus.OK);
    }

}
