package com.okta.examples.usecase;

import com.okta.examples.service.usecase.VoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    }

    @Test
    public void sortVoucherTest(){
        System.out.println("Sort Voucher Test");
        String path = "/api/user/sort-voucher";
        String name = "fnb";
        String page = "0";
        assertFalse(voucherService.sortVoucher(name, page, path).getStatusCode().is2xxSuccessful());

    }

    @Test
    public void searchVoucherTest(){
        System.out.println("Search Voucher Test");
        String path = "/api/user/search-voucher";
        String merchantName = "k";
        String page = "0";
        assertTrue(voucherService.searchVoucher(merchantName, page, path).getStatusCode().is2xxSuccessful());
    }
}
