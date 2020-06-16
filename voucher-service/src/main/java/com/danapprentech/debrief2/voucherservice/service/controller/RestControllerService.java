package com.danapprentech.debrief2.voucherservice.service.controller;

import com.danapprentech.debrief2.voucherservice.model.request.UpdateVoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface RestControllerService
{
    public ResponseEntity<?> createVoucher(Long idMerchant,
                                           String idUser,
                                           VoucherRequest voucherRequest,
                                           HttpServletRequest httpServletRequest);

    public ResponseEntity<?> getAllVoucher(
                                            Optional<Integer> page,
                                            String sortBy,
                                            HttpServletRequest httpServletRequest);

    public ResponseEntity<?> filterByStatus(
                                            Optional<Integer> page,
                                            String filterByStatus,
                                            String sortBy,
                                            HttpServletRequest httpServletRequest);

    public ResponseEntity<?> findByMerchantName(
                                            Optional<Integer> page,
                                            String merchantName,
                                            String sortBy,
                                            HttpServletRequest httpServletRequest);

    public ResponseEntity<?> voucherDetail(
                                            Long idVoucher,
                                            HttpServletRequest httpServletRequest);

    public ResponseEntity<?> updateVoucher(
                                            Long idVoucher,
                                            UpdateVoucherRequest updateVoucherRequest,
                                            HttpServletRequest httpServletRequest);

    public ResponseEntity<?> filterByMerchantCategory(
                                            Optional<Integer> page,
                                            Optional<String> merchantCategory,
                                            String sortBy,
                                            HttpServletRequest httpServletRequest);

    public ResponseEntity<?> SortByMerchantName(
                                            Optional<Integer> page,
                                            Optional<String> sortBy,
                                            HttpServletRequest httpServletRequest);
}
