package com.okta.examples.validation;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.validation.VoucherValidation;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VoucherValidationTest {

    @Autowired
    VoucherValidation voucherValidation;

    @Test
    public void getAllVoucherValidationTest(){
        System.out.println("Get All Voucher Validation Test");

        assertFalse(voucherValidation.getAllVoucher(null, "").getStatusCode().is2xxSuccessful());

        String page = "0";
        assertTrue(voucherValidation.getAllVoucher(page, "").getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherValidation.getAllVoucher(page, "").getBody().get("status"));
    }

    @Test
    public void filterVoucherValidationTest(){
        System.out.println("Filter Voucher Validation Test");

        String merchantCategory = null;
        String page = null;
        assertFalse(voucherValidation.filterVoucher(merchantCategory, page, "").getStatusCode().is2xxSuccessful());

        merchantCategory = "aa";
        assertFalse(voucherValidation.filterVoucher(merchantCategory, page, "").getStatusCode().is2xxSuccessful());

        page = "0";
        assertTrue(voucherValidation.filterVoucher(merchantCategory, page, "").getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherValidation.filterVoucher(merchantCategory, page, "").getBody().get("status"));
    }

    @Test
    public void sortVoucherValidationTest(){
        System.out.println("Sort Voucher Validation Test");

        String name = null;
        String page = null;
        assertFalse(voucherValidation.sortVoucher(name, page, "").getStatusCode().is2xxSuccessful());

        name = "aa";
        assertFalse(voucherValidation.sortVoucher(name, page, "").getStatusCode().is2xxSuccessful());

        page = "0";
        assertTrue(voucherValidation.sortVoucher(name, page, "").getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherValidation.sortVoucher(name, page, "").getBody().get("status"));

    }

    @Test
    public void searchVoucherValidationTest(){
        System.out.println("Search Voucher Validation Test");

        String merchantName = null;
        String page = null;
        assertFalse(voucherValidation.searchVoucher(merchantName, page, "").getStatusCode().is2xxSuccessful());

        merchantName = "aa";
        assertFalse(voucherValidation.searchVoucher(merchantName, page, "").getStatusCode().is2xxSuccessful());

        page = "0";
        assertTrue(voucherValidation.searchVoucher(merchantName, page, "").getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherValidation.searchVoucher(merchantName, page, "").getBody().get("status"));

    }
}
