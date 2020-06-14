package com.okta.examples.adapter.template;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketException;
import java.rmi.ConnectException;

@Service
public class Template {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${jwt.id.mobile}")
    private String token;

    @Bean
    public RestTemplate restsTemplate() {
        return new RestTemplate();
    }

    public ResponseEntity<?> post(String api, Object data){
        ResponseEntity<?> result = null;
        System.out.println(api);
        try {
            //result= restTemplate.postForEntity(api, data, JSONObject.class);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth("dasdasdasdasdasda");
            HttpEntity entity = new HttpEntity(data, headers);
            result = restTemplate.exchange(api, HttpMethod.POST, entity, JSONObject.class);
        }
        catch (HttpClientErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (HttpServerErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (RestClientException e){
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    public ResponseEntity<?> get(String api){
        ResponseEntity<?> result = null;
        System.out.println(api);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth("dasdasdasdasdasda");
            HttpEntity entity = new HttpEntity(headers);
            result= restTemplate.exchange(api, HttpMethod.GET, entity, JSONObject.class);
        }
        catch (HttpClientErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (HttpServerErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (RestClientException e){
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    public ResponseEntity<?> put(String api, Object data){
        ResponseEntity<?> result = null;
        System.out.println(api);
        try {
            HttpEntity entity = new HttpEntity(data);
            result = restTemplate.exchange(api, HttpMethod.PUT,  entity, JSONObject.class);
        }
        catch (HttpClientErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (HttpServerErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (RestClientException e){
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    public ResponseEntity<?> patch(String api, Object data){
        ResponseEntity<?> result = null;
        System.out.println(api);
        try {
            HttpEntity entity = new HttpEntity(data);
            result = restTemplate.exchange(api, HttpMethod.PATCH,  entity, JSONObject.class);
        }
        catch (HttpClientErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (HttpServerErrorException e){
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        catch (RestClientException e){
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
