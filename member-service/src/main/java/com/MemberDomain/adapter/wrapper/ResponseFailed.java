package com.MemberDomain.adapter.wrapper;

import com.MemberDomain.adapter.status.DealsStatus;
import com.MemberDomain.adapter.time.Timestamps;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseFailed {

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

}
