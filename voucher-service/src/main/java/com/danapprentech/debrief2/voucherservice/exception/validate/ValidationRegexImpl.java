package com.danapprentech.debrief2.voucherservice.exception.validate;

import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationRegexImpl implements ValidationRegex
{
    private Pattern pattern;
    private Matcher matcher;

    private static final String NUMBER_ONLY                 =
            "[0-9]";
    private static final String ALPHABET                    =
            "[a-zA-Z\\s']+";

    @Override
    public boolean NumberOnlyValidator (Integer number)
    {
        pattern = Pattern.compile(NUMBER_ONLY);
        matcher = pattern.matcher(String.valueOf(number));
        return matcher.matches();
    }

    @Override
    public boolean voucherName (String voucherName)
    {
        boolean status;
        if (voucherName.length() < 3 || voucherName.length() > 20)
        {
            return status = false;
        }
        return status = true;
    }

    @Override
    public boolean aplhabetOnly(String voucher) {
        pattern = Pattern.compile(ALPHABET);
        matcher = pattern.matcher(String.valueOf(voucher));
        return matcher.matches();
    }

    @Override
    public Calendar testCalendar() {
        Calendar testCalendar = Calendar.getInstance();
        testCalendar.setTime(new Date());
        testCalendar.add(Calendar.DATE, 30);
        return testCalendar;
    }

    @Override
    public Date testDate (VoucherRequest voucherRequest) {
        Date testDate = voucherRequest.getExpiredDate();
        return testDate;
    }
}
