package com.danapprentech.debrief2.voucherservice.exception.validate;

import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;

import java.util.Calendar;
import java.util.Date;

public interface ValidationRegex
{
    boolean NumberOnlyValidator (Integer number);
    boolean voucherName (String voucherName);
    boolean aplhabetOnly(String voucher);
    Calendar testCalendar();
    Date testDate(VoucherRequest voucherRequest);

    //LengthUsername
    //NumberOnlyValidator
}
