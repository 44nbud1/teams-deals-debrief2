package com.okta.examples.controller;

import com.okta.examples.adapter.status.DealsStatus;
import com.okta.examples.model.request.CreateMerchantRequest;
import com.okta.examples.model.response.ResponseFailed;
import com.okta.examples.service.usecase.AdminService;
import com.okta.examples.service.usecase.SessionService;
import com.okta.examples.service.validation.SessionValidation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    SessionValidation sessionValidation;

    @Autowired
    SessionService sessionService;

    @PostMapping(value = "/{idUser}/merchant/{idMerchant}/vouchers")
    public ResponseEntity<?> createMerchant(@PathVariable("idUser") String idUser,
                                            @PathVariable("idMerchant") String idMerchant,
                                            @RequestBody(required = false) JSONObject data,
                                            HttpServletRequest request){
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        data.put("idUser",sessionService.getIdUserSession(request.getHeader("Authorization").substring(7)));
        return adminService.createMerchant(idUser, idMerchant, data, request.getServletPath());
    }

    @GetMapping("/show-all-voucher")
    public ResponseEntity<?> getAllVoucher(@RequestParam(value = "page", required = false) String page,
                                           HttpServletRequest request) {
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        return adminService.getAllVoucher(page, request.getServletPath());
    }

    @GetMapping("/voucher/filterByStatus")
    public ResponseEntity<?> filterVoucher(@RequestParam(value = "filterByStatus", required = false) String merchantCategory,
                                           @RequestParam(value = "page", required = false) String page,
                                           HttpServletRequest request){
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        return adminService.filterVoucher(merchantCategory, page, request.getServletPath());
    }

    @GetMapping("/findByMerchantName-voucher")
    public ResponseEntity<?> searchVoucher(@RequestParam("merchantName") String merchantName,
                                           @RequestParam(value = "page", required = false) String page,
                                           HttpServletRequest request){
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        return adminService.searchVoucher(merchantName, page, request.getServletPath());
    }

    @GetMapping("/sort-voucher")
    public ResponseEntity<?> sortVoucher(@RequestParam("sortBy") String name,
                                         @RequestParam(value = "page", required = false) String page,
                                         HttpServletRequest request){
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        return adminService.sortVoucher(name, page, request.getServletPath());
    }

    @GetMapping("/voucher-detail-voucher/{idVoucher}")
    public ResponseEntity<?> voucherDetail(@PathVariable("idVoucher") String idVoucher,
                                           HttpServletRequest request){
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        return adminService.voucherDetail(idVoucher, request.getServletPath());
    }

    @PutMapping("/update-status-voucher/{idVoucher}/restock")
    public ResponseEntity<?> updateVoucher(@PathVariable("idVoucher") String idVoucher,
                                           @RequestBody(required = false) JSONObject data,
                                           HttpServletRequest request){
        if (!sessionValidation.requestId(request)){
            return ResponseFailed.unAuthorized(request.getServletPath());
        }
        return adminService.updateVoucher(idVoucher, data, request.getServletPath());
    }
}
