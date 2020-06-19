package com.okta.examples.usecase;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.usecase.TransactionService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;
    @Test
    public void createOrderVoucherTest(){
        System.out.println("Create Order Voucher Test");
        String path = "/api/user/19/transaction/voucher";
        String idUser = "19";
        JSONObject test = new JSONObject();

        assertFalse(transactionService.createOrderVoucher(idUser,test, path).getStatusCode().is2xxSuccessful());

        test.put("idVoucher", 1);
//        assertTrue(transactionService.createOrderVoucher(idUser, test, path).getStatusCode().is2xxSuccessful());

        test.put("idVoucher", 999999);
        assertEquals(DealsStatus.VOUCHER_NOT_AVAILABLE.getValue(), transactionService.createOrderVoucher(idUser,test, path).getBody().get("status"));

    }

    @Test
    public void payOrderVoucherTest(){
        System.out.println("Pay Voucher Test");
        String path = "/api/user/19/transaction/voucher";
        String idUser = "19";
        JSONObject test = new JSONObject();

        assertFalse(transactionService.payOrderVoucher(idUser,test, path).getStatusCode().is2xxSuccessful());

        test.put("idTransaction", 73);
//        assertTrue(transactionService.payOrderVoucher(idUser, test, path).getStatusCode().is2xxSuccessful());

        test.put("idTransaction", 999999);
        assertEquals(DealsStatus.TRANSACTION_NOT_FOUND.getValue(), transactionService.payOrderVoucher(idUser,test, path).getBody().get("status"));

    }

    @Test
    public void payTopupTest(){
        System.out.println("Pay Topup Test");
        String path = "/api/user/19/transaction/topup";
        String idUser = "19";
        JSONObject test = new JSONObject();

        assertFalse(transactionService.payTopup(idUser,test, path).getStatusCode().is2xxSuccessful());

        test.put("virtualNumber", "9030087819411111");
        test.put("amount", 200000);
//        assertTrue(transactionService.payTopup(idUser, test, path).getStatusCode().is2xxSuccessful());

        test.put("virtualNumber", "9030087819411111");
        test.put("amount", 500000000);
        assertEquals(DealsStatus.MAXIMUM_BALANCE.getValue(), transactionService.payTopup(idUser,test, path).getBody().get("status"));

        test.put("virtualNumber", "9031087819411111");
        test.put("amount", 200000);
//        assertEquals(DealsStatus.MERCHANT_NOT_AVAILABLE.getValue(), transactionService.payTopup(idUser,test, path).getBody().get("status"));

    }

    @Test
    public void transactionHistoryTest(){
        System.out.println("Transactinn History Test");
        String path = "/api/user/19/transaction";
        String idUser = "19";
        String category = "COMPLETED";
        String filterStart = "2020-06-12";
        String filterEnd = "2020-06-19";
        String page = "0";
        assertTrue(transactionService.transactionHistory(idUser, category,filterStart,filterEnd,page, path).getStatusCode().is2xxSuccessful());

    }

    @Test
    public void transactionDetailTest(){
        System.out.println("Transactinn DEtail Test");
        String path = "/api/user/19/transaction";
        String idUser = "19";
        String idTransaction ="73";
        assertTrue(transactionService.transactionDetail(idUser,idTransaction, path).getStatusCode().is2xxSuccessful());

        idTransaction = "1";
        assertEquals(DealsStatus.TRANSACTION_NOT_FOUND.getValue(), transactionService.transactionDetail(idUser,idTransaction, path).getBody().get("status"));
    }
}
