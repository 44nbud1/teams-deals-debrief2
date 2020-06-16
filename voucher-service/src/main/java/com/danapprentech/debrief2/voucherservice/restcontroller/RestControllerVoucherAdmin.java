package com.danapprentech.debrief2.voucherservice.restcontroller;

import com.danapprentech.debrief2.voucherservice.model.request.UpdateVoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import com.danapprentech.debrief2.voucherservice.scheduler.UpdateExpiredVoucher;
import com.danapprentech.debrief2.voucherservice.service.controller.RestControllerServiceImpl;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestControllerVoucherAdmin
{
    @Autowired
    RestControllerServiceImpl restControllerService;

    private static final Logger logger = LoggerFactory.getLogger(RestControllerVoucherAdmin.class);

    @PostMapping("/admin/{idUser}/merchant/{idMerchant}/vouchers")
    public ResponseEntity<?> createVoucher(@PathVariable Long idMerchant,
                                           @PathVariable String idUser,
                                           @RequestBody VoucherRequest voucherRequest,
                                           HttpServletRequest httpServletRequest)
    {
        return restControllerService.createVoucher(idMerchant,idUser,voucherRequest,httpServletRequest);
    }

    @GetMapping("/admin/show-all-voucher")
    public ResponseEntity<?> getAllVoucher(
            @RequestParam Optional<Integer> page,
            @RequestParam(defaultValue = "voucherName") String sortBy,
            HttpServletRequest httpServletReq)
    {
        return restControllerService.getAllVoucher(page,sortBy,httpServletReq);
    }

    ///admin/filter-voucher?filterByStatus=true&page=0
    @GetMapping("/admin/filterByStatus-voucher")
    public ResponseEntity<?> filterByStatus(
            @RequestParam Optional<Integer> page,
            @RequestParam String filterByStatus,
            @RequestParam(defaultValue = "voucherName") String sortBy,
            HttpServletRequest httpServletRequest)
    {
        return restControllerService.filterByStatus(page,filterByStatus,sortBy,httpServletRequest);
    }

    //  /admin/filterByMerchantName-voucher?merchantName=
    @GetMapping("/admin/findByMerchantName-voucher")
    public ResponseEntity<?> findByMerchantName(
            @RequestParam Optional<Integer> page,
            @RequestParam String merchantName,
            @RequestParam(defaultValue = "merchantName") String sortBy,
            HttpServletRequest httpServletRequest)
    {
        return restControllerService.findByMerchantName(page,merchantName,sortBy,httpServletRequest);
    }

    //  /admin/filterByMerchantName-voucher?merchantName=
    @GetMapping("/admin/voucher-detail-voucher/{idVoucher}")
    public ResponseEntity<?> voucherDetail(@PathVariable Long idVoucher,HttpServletRequest httpServletRequest)
    {
        return restControllerService.voucherDetail(idVoucher,httpServletRequest);
    }

    @PutMapping("/admin/update-status-voucher/{idVoucher}/restock")
    public ResponseEntity<?> updateVoucher(
            @PathVariable Long idVoucher,
            @RequestBody UpdateVoucherRequest updateVoucherRequest,
            HttpServletRequest httpServletRequest)
    {
        return restControllerService.updateVoucher(idVoucher,updateVoucherRequest,httpServletRequest);
    }

}