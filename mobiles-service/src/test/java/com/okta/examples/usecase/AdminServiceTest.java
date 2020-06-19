package com.okta.examples.usecase;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.usecase.AdminService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Test
    public void createMerchantTest(){
        System.out.println("Create Merchant Validation Test");
        String path = "/api/admin/126/merchant/1001/vouchers";
        String idUser = "126";
        String idMerchant = "1001";
        assertFalse(adminService.createMerchant(idUser,idMerchant,null, path).getStatusCode().is2xxSuccessful());

        JSONObject test = new JSONObject();

        assertFalse(adminService.createMerchant(idUser, idMerchant,test, path).getStatusCode().is2xxSuccessful());

        test.put("voucherName", "Combo ssikasa");
        test.put("voucherPrice", "1000");
        test.put("discount", "50");
        test.put("maxDiscount", "5000");
        test.put("quota", "10");
        test.put("expiredDate", "2020-06-14 12:06:14");
        test.put("status", "true");
        assertFalse(adminService.createMerchant(idUser, idMerchant, test, path).getStatusCode().is2xxSuccessful());

    }

    @Test
    public void getAllVoucherTest(){
        System.out.println("Get All Voucher Test");
        String path = "/api/user/show-all-voucher";
        String page = "0";
        assertTrue(adminService.getAllVoucher(page, path).getStatusCode().is2xxSuccessful());

        page = "10";
        assertTrue(adminService.getAllVoucher(page, path).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void filterVoucherTest(){
        System.out.println("Filter Voucher Test");
        String path = "/api/admin/voucher/filterByStatus";
        String merchantCategory = "true";
        String page = "0";
        assertTrue(adminService.filterVoucher(merchantCategory, page, path).getStatusCode().is2xxSuccessful());

        page = "10";
        assertTrue(adminService.filterVoucher(merchantCategory, page, path).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void sortVoucherTest(){
        System.out.println("Sort Voucher Test");
        String path = "/api/admin/sort-voucher";
        String name = "fnb";
        String page = "0";
        assertFalse(adminService.sortVoucher(name, page, path).getStatusCode().is2xxSuccessful());

    }

    @Test
    public void searchVoucherTest(){
        System.out.println("Search Voucher Test");
        String path = "/api/admin/search-voucher";
        String merchantName = "k";
        String page = "0";
        assertTrue(adminService.searchVoucher(merchantName, page, path).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void voucherDetailTest(){
        System.out.println("Voucher Detail Test");
        String path = "/api/admin/voucher-detail-voucher/12";
        String idVoucher = "1";

        assertTrue(adminService.voucherDetail(idVoucher, path).getStatusCode().is2xxSuccessful());

        idVoucher = "aa";
        assertEquals(DealsStatus.VOUCHER_NOT_FOUND.getValue(), adminService.voucherDetail(idVoucher, path).getBody().get("status"));

    }

    @Test
    public void updateVoucherTest(){
        System.out.println("Update Voucher Test");
        String path = "/api/admin/update-status-voucher/12/restock";
        String idVoucher = "12";

        JSONObject test = new JSONObject();
        test.put("status", "false");
        assertTrue(adminService.updateVoucher(idVoucher, test, path).getStatusCode().is2xxSuccessful());

        idVoucher = "aa";
        assertEquals(DealsStatus.VOUCHER_NOT_FOUND.getValue(), adminService.voucherDetail(idVoucher, path).getBody().get("status"));

        idVoucher = "400";
        assertEquals(DealsStatus.VOUCHER_NOT_FOUND.getValue(), adminService.voucherDetail(idVoucher, path).getBody().get("status"));

    }
}
