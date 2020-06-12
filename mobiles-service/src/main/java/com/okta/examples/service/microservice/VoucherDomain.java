package com.okta.examples.service.microservice;

import com.okta.examples.model.request.CreateMerchantRequest;
import com.okta.examples.adapter.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VoucherDomain {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Template template;

    private final String api = "http://localhost:8081/api";

    public ResponseEntity<?> getVoucher(){
        return template.get(api+"/admin/filterByStatus-voucher?filterByStatus=false");
    }

    public ResponseEntity<?> getAllVoucher(String page, String path){
        return template.get(api+"/user/show-all-voucher" +
                "?page=" +page);
    }

    public ResponseEntity<?> filterVoucher(String merchantCategory, String page){
        return template.get(api+"/user/filter-voucher" +
                "?merchantCategory=" +merchantCategory+"&" +
                "page=" +page);
    }

    public ResponseEntity<?> searchVoucher(String merchantName, String page){
        return template.get(api+"/user/findByMerchantName-voucher" +
                "?merchantName=" +merchantName+"&" +
                "page=" +page);
    }

    public ResponseEntity<?> sortVoucher(String name, String page){
        return template.get(api+"/user/sort-voucher" +
                "?sortBy=" +name+"&" +
                "page=" +page);
    }

    public ResponseEntity<?> createMerchant(String idUser, String idMerchant, CreateMerchantRequest createMerchantRequest){
        return template.post(api+"/admin/"+idUser+"/merchant/"+idMerchant+"/vouchers", createMerchantRequest);
    }

    public ResponseEntity<?> getAllVoucherAdmin(String page){
        return template.get(api+"/admin/show-all-voucher" +
                "?page=" +page);
    }

    public ResponseEntity<?> voucherDetail(String idVoucher){
        return template.get(api+"/admin/voucher-detail-voucher/"+idVoucher);
    }

    public ResponseEntity<?> filterVoucherAdmin(String merchantCategory, String page){
        return template.get(api+"/admin/filterByStatus-voucher" +
                "?filterByStatus=" +merchantCategory+"&" +
                "page=" +page);
    }

    public ResponseEntity<?> searchVoucherAdmin(String merchantName, String page){
        return template.get(api+"/admin/findByMerchantName-voucher" +
                "?merchantName=" +merchantName+"&" +
                "page=" +page);
    }

    public ResponseEntity<?> sortVoucherAdmin(String name, String page){
        return template.get(api+"/admin/sort-voucher" +
                "?sortBy=" +name+"&" +
                "page=" +page);
    }
}
