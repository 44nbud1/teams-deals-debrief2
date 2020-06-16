//package com.danapprentech.debrief2.voucherservice.controller.admin;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import com.apple.eawt.Application;
//import com.danapprentech.debrief2.voucherservice.model.Voucher;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class AdminVoucherController {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @LocalServerPort
//    private int port;
//
//    private String getRootUrl() {
//        return "http://localhost:" + port;
//    }
//
//    @Test
//    public void contextLoads() {
//
//    }
//
//    @Test
//    public void testCreateEmployee() {
//        Voucher voucher = new Voucher();
//        voucher.setStatus(Boolean.TRUE);
//        voucher.setMerchantName("kfc combo");
//        voucher.setUpdateAt(new Date());
//
//        ResponseEntity<Voucher> postResponse = restTemplate.postForEntity(getRootUrl() +
//                        "/admin/12/merchant/1001/vouchers", voucher, Voucher.class);
//        assertNotNull(postResponse);
//        assertNotNull(postResponse.getBody());
//    }
//}
