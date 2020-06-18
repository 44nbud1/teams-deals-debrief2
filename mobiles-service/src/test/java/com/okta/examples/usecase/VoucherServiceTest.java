package com.okta.examples.usecase;

import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.usecase.VoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;
    @Test
    public void getAllVoucherTest(){
        System.out.println("Get All Voucher Test");
        String path = "/api/user/show-all-voucher";
        String page = "0";
        assertTrue(voucherService.getAllVoucher(page, path).getStatusCode().is2xxSuccessful());

        page = "10";
        assertTrue(voucherService.getAllVoucher(page, path).getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherService.getAllVoucher(page, "").getBody().get("status"));

    }

    @Test
    public void filterVoucherTest(){
        System.out.println("Filter Voucher Test");
        String path = "/api/user/voucher/filter-voucher";
        String merchantCategory = "fnb";
        String page = "0";
        assertTrue(voucherService.filterVoucher(merchantCategory, page, path).getStatusCode().is2xxSuccessful());

        page = "10";
        assertFalse(voucherService.filterVoucher(merchantCategory, page, path).getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherService.filterVoucher(merchantCategory, page, "").getBody().get("status"));

    }

    @Test
    public void sortVoucherTest(){
        System.out.println("Sort Voucher Test");
        String path = "/api/user/sort-voucher";
        String name = "voucherPrice";
        String page = "0";
        assertTrue(voucherService.sortVoucher(name, page, path).getStatusCode().is2xxSuccessful());

        name = "wee";
        assertFalse(voucherService.sortVoucher(name, page, path).getStatusCode().is2xxSuccessful());

        page = "aa";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherService.sortVoucher(name, page, "").getBody().get("status"));

    }

    @Test
    public void searchVoucherTest(){
        System.out.println("Search Voucher Test");
        String path = "/api/user/search-voucher";
        String merchantName = "k";
        String page = "0";
        assertTrue(voucherService.searchVoucher(merchantName, page, path).getStatusCode().is2xxSuccessful());

        merchantName = "d";
        assertEquals(DealsStatus.VOUCHER_NOT_FOUND.getValue(), voucherService.searchVoucher(merchantName, page, path).getBody().get("status"));

        page = "a";
        assertEquals(DealsStatus.PAGE_NOT_FOUND.getValue(), voucherService.searchVoucher(merchantName, page, path).getBody().get("status"));
    }
}
