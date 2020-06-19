package com.okta.examples.validation;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.validation.AdminValidation;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Service
public class AdminValidationTest {

    @Autowired
    AdminValidation adminValidation;

    @Test
    public void createMerchantValidationTest(){
        System.out.println("Create Merchant Validation Test");
        assertFalse(adminValidation.test(null, "/").getStatusCode().is2xxSuccessful());

        JSONObject test = new JSONObject();

        assertFalse(adminValidation.test(test, "/").getStatusCode().is2xxSuccessful());

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", "1000");
        test.put("discount", "50");
        test.put("maxDiscount", "5000");
        test.put("quota", "10");
        test.put("expiredDate", "2020-06-14 12:06:14");
        test.put("status", "true");
        assertTrue(adminValidation.test(test, "/").getStatusCode().is2xxSuccessful());

        test.put("voucherName", null);
        assertEquals(DealsStatus.FILL_VOUCHER_NAME.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", null);
        assertEquals(DealsStatus.FILL_VOUCHER_PRICE.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", "1000");
        test.put("discount", null);
        assertEquals(DealsStatus.FILL_DISCOUNT.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", "1000");
        test.put("discount", "50");
        test.put("maxDiscount", null);
        assertEquals(DealsStatus.FILL_MAX_DISCOUNT.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", "1000");
        test.put("discount", "50");
        test.put("maxDiscount", "5000");
        test.put("quota", null);
        assertEquals(DealsStatus.FILL_QUOTA.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", "1000");
        test.put("discount", "50");
        test.put("maxDiscount", "5000");
        test.put("quota", "10");
        test.put("expiredDate", null);
        assertEquals(DealsStatus.FILL_EXPIRED_DATE.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("voucherName", "Combo Asik");
        test.put("voucherPrice", "1000");
        test.put("discount", "50");
        test.put("maxDiscount", "5000");
        test.put("quota", "10");
        test.put("expiredDate", "2020-06-14 12:06:14");
        test.put("status", null);
        assertEquals(DealsStatus.FILL_STATUS.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("status", "true");
        test.put("discount", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("discount", "50");
        test.put("maxDiscount", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("maxDiscount", "5000");
        test.put("quota", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("quota", "10");
        test.put("expiredDate", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), adminValidation.test(test, "/").getBody().get("status"));

        test.put("expiredDate", "2020-06-14 12:06:14");
        test.put("status", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), adminValidation.test(test, "/").getBody().get("status"));

    }

    @Test
    public void getAllVoucherValidationTest(){
        System.out.println("Get All Voucher Admin Validation Test");
        String page = null;
        assertFalse(adminValidation.getAllVoucher(page, "/").getStatusCode().is2xxSuccessful());

        page = "0";
        assertTrue(adminValidation.getAllVoucher(page, "/").getStatusCode().is2xxSuccessful());

        page = "a";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.getAllVoucher(page, "/").getBody().get("status"));

        page = "-1";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.getAllVoucher(page, "/").getBody().get("status"));

    }

    @Test
    public void filterVoucherValidationTest(){
        System.out.println("Filter Voucher Admin Validation Test");
        String page = null; String merchantCategory = null;
        assertFalse(adminValidation.filterVoucher(merchantCategory, page, "/").getStatusCode().is2xxSuccessful());

        merchantCategory = "fnb";
        assertFalse(adminValidation.filterVoucher(merchantCategory, page, "/").getStatusCode().is2xxSuccessful());

        page = "0";
        assertTrue(adminValidation.filterVoucher(merchantCategory, page, "/").getStatusCode().is2xxSuccessful());

        page = "a";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.filterVoucher(merchantCategory, page, "/").getBody().get("status"));

        page = "-1";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.filterVoucher(merchantCategory, page, "/").getBody().get("status"));

    }

    @Test
    public void sortVoucherValidationTest(){
        System.out.println("Sort Voucher Admin Validation Test");
        String page = null; String name = null;
        assertFalse(adminValidation.sortVoucher(name, page, "/").getStatusCode().is2xxSuccessful());

        name = "true";
        assertFalse(adminValidation.sortVoucher(name, page, "/").getStatusCode().is2xxSuccessful());

        page = "0";
        assertTrue(adminValidation.sortVoucher(name, page, "/").getStatusCode().is2xxSuccessful());

        page = "a";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.sortVoucher(name, page, "/").getBody().get("status"));

        page = "-1";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.sortVoucher(name, page, "/").getBody().get("status"));

    }

    @Test
    public void searchVoucherValidationTest(){
        System.out.println("Search Voucher Admin Validation Test");
        String page = null; String merchantName = null;
        assertFalse(adminValidation.searchVoucher(merchantName, page, "/").getStatusCode().is2xxSuccessful());

        merchantName = "k";
        assertFalse(adminValidation.searchVoucher(merchantName, page, "/").getStatusCode().is2xxSuccessful());

        page = "0";
        merchantName = "k";
        assertTrue(adminValidation.searchVoucher(merchantName, page, "/").getStatusCode().is2xxSuccessful());

        page = "a";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.searchVoucher(merchantName, page, "/").getBody().get("status"));

        page = "-1";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), adminValidation.searchVoucher(merchantName, page, "/").getBody().get("status"));

    }

    @Test
    public void voucherDetailValidationTest(){
        System.out.println("Search Voucher Admin Validation Test");
        String idVoucher = null;
        assertFalse(adminValidation.voucherDetail(idVoucher, "/").getStatusCode().is2xxSuccessful());

        idVoucher = "1";
        assertTrue(adminValidation.voucherDetail(idVoucher, "/").getStatusCode().is2xxSuccessful());

        idVoucher = "a";
        assertEquals(DealsStatus.VOUCHER_NOT_FOUND.getValue(), adminValidation.voucherDetail(idVoucher, "/").getBody().get("status"));
    }

    @Test
    public void UpdateVoucherValidationTest(){
        System.out.println("Update Voucher Admin Validation Test");
        String idVoucher = null;
        assertFalse(adminValidation.updateVoucher(idVoucher, null, "/").getStatusCode().is2xxSuccessful());

        idVoucher = "1";
        assertFalse(adminValidation.updateVoucher(idVoucher, null, "/").getStatusCode().is2xxSuccessful());

        JSONObject test = new JSONObject();
        test.put("status", "true");
        test.put("quota", "5");
        assertTrue(adminValidation.updateVoucher(idVoucher, test, "/").getStatusCode().is2xxSuccessful());

        test.put("status", null);
        assertEquals(DealsStatus.FILL_STATUS.getValue(), adminValidation.updateVoucher(idVoucher, test, "/").getBody().get("status"));

        test.put("status", "aa");
        assertEquals(DealsStatus.STATUS_INVALID.getValue(), adminValidation.updateVoucher(idVoucher, test, "/").getBody().get("status"));

        test.put("status", "true");
        test.put("quota", "aa");
        assertEquals(DealsStatus.DATA_INVALID.getValue(), adminValidation.updateVoucher(idVoucher, test, "/").getBody().get("status"));

    }

}
