package com.danapprentech.debrief2.voucherservice.restcontroller;


import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestQtyVoucher
{
    @Autowired
    VoucherRepository voucherRepository;

    @GetMapping("/showAll")
    public ResponseEntity<?> testQty()
    {
        return ResponseEntity.ok(voucherRepository.findAll());
    }
}
