package com.danapprentech.debrief2.voucherservice.exception.controller;

import com.danapprentech.debrief2.voucherservice.exception.response.ResponseFailed;
import com.danapprentech.debrief2.voucherservice.exception.statusexception.DealsStatus;
import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class FilterByStatusValidation
{
    public Boolean filterValidation(String filterByStatus, HttpServletRequest httpServletRequest)
    {
        Boolean status = null;
        if (filterByStatus.equalsIgnoreCase("true")) {
            status = Boolean.TRUE;
        } else if (filterByStatus.equalsIgnoreCase("false")) {
            status = Boolean.FALSE;
        } else {
            filterValidationError(httpServletRequest);
        }
        return status;
    }

    public ResponseEntity<?> filterValidationError(HttpServletRequest httpServletRequest)
    {
        return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_NAME, httpServletRequest.getServletPath());
    }
}
