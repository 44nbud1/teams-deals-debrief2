package com.okta.examples.service.microservice;

import com.okta.examples.adapter.template.Template;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderDomain {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Template template;

    private final String api = "http://localhost:8084";

    public ResponseEntity<?> createOrder(String idUser, JSONObject data){
        return template.post(api+"/api/user/"+idUser+"/transaction/voucher", data);
    }

    public ResponseEntity<?> payOrder(String idUser, JSONObject data){
        return template.put(api+"/api/user/"+idUser+"/transaction/voucher", data);
    }

    public ResponseEntity<?> payTopup(String idUser, JSONObject data){
        return template.post(api+"/api/user/"+idUser+"/transaction/topup", data);
    }

    public ResponseEntity<?> transactionHistory(String idUser, String category, String filterStart, String filterEnd, String page, String path){

        String apiOrder = "";
        if (category != null){
            apiOrder += "category="+category;
        }

        if (filterStart != null){
            apiOrder += "&filter-start-date="+filterStart;
        }

        if (filterEnd != null){
            apiOrder += "&filter-end-date="+filterEnd;
        }

        if (page != null){
            apiOrder += "&page="+page;
        }

//        return template.get(api+path);
        return template.get(api+"/api/user/"+idUser+"/transaction" +
                            "?"+apiOrder);
    }

    public ResponseEntity<?> transactionDetail(String idUser, String idTransaction){
        return template.get(api+"/api/user/"+idUser+"/transaction/"+idTransaction);
    }
}
