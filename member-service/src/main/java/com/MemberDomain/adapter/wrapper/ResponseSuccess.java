package com.MemberDomain.adapter.wrapper;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.time.Timestamps;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess {

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
        System.out.println("Validation Success");
        return new ResponseEntity<>(new JSONObject(), HttpStatus.OK);
    }

}
