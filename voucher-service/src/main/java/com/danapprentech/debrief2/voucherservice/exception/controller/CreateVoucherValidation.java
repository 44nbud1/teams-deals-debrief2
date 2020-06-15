package com.danapprentech.debrief2.voucherservice.exception.controller;

import com.danapprentech.debrief2.voucherservice.exception.statusexception.DealsStatus;
import com.danapprentech.debrief2.voucherservice.exception.response.ResponseFailed;
import com.danapprentech.debrief2.voucherservice.exception.validate.ValidationRegexImpl;
import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.response.MessageResponse;
import com.danapprentech.debrief2.voucherservice.rabbit.producer.RabbitMqProducer;
import com.danapprentech.debrief2.voucherservice.repository.MerchantCategoryRepository;
import com.danapprentech.debrief2.voucherservice.repository.MerchantRepository;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import com.danapprentech.debrief2.voucherservice.service.VoucherOutletServiceImpl;
import com.danapprentech.debrief2.voucherservice.service.controller.RestControllerServiceImpl;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class CreateVoucherValidation
{

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherOutletServiceImpl voucherOutletService;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    ValidationRegexImpl validationRegex;

    @Autowired
    MerchantCategoryRepository merchantCategoryRepository;

    @Autowired
    RabbitMqProducer rabbitMqProducer;

    @Autowired
    ValidationRegexImpl validation;

    private static final Logger logger = LoggerFactory.getLogger(RestControllerServiceImpl.class);

    // scheduler
    @Autowired
    private Scheduler scheduler;

    public ResponseEntity<?> createVoucher(Long idMerchant,
                                           String idUser,
                                           VoucherRequest voucherRequest,
                                           HttpServletRequest httpServletRequest)
    {

         final String regex_integer = "^[\\d]+$";
         final String regex_double = "^(?=.*[\\d])(?!.*[\\D]).+$|^[\\d]+[.]{1}[\\d]+$";

        if (!idUser.equalsIgnoreCase("12")) {
            return ResponseFailed.wrapResponse(DealsStatus.USER_NOT_FOUND, httpServletRequest.getServletPath());
        }

        if (merchantRepository.findByIdMerchant(idMerchant) == null) {
            return ResponseFailed.wrapResponse(DealsStatus.ID_MERCHANT_NOAT_FOUND, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getVoucherName() == null) {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_NAME, httpServletRequest.getServletPath());

        }

        if (voucherRequest.getDiscount() == null) {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_DISCOUNT, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getExpiredDate() == null) {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_EXPIRED_DATE, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getMaxDiscount() == null) {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_MAX_DISCOUNT, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getQuota() == null) {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_QUOTA, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getStatus() == null)
        {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_STATUS, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getVoucherPrice() == null) {
            return ResponseFailed.wrapResponse(DealsStatus.FILL_VOUCHER_PRICE, httpServletRequest.getServletPath());
        }

        if (validation.NumberOnlyValidator(voucherRequest.getQuota())) {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getQuota() > 1000) {
            return ResponseFailed.wrapResponse(DealsStatus.MAXIMUM_QUOTA, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getVoucherPrice() > 1000000) {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getVoucherName().length() < 3 || voucherRequest.getVoucherName().length() > 20) {
            return ResponseFailed.wrapResponse(DealsStatus.VOUCHER_NAME_INVALID, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getDiscount() <= 0 || voucherRequest.getDiscount() >= 100) {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());

        }

        if (voucherRequest.getVoucherPrice() < 0 || voucherRequest.getVoucherPrice() > 1000000) {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());
        }

        if (voucherRequest.getMaxDiscount() < 0 || voucherRequest.getMaxDiscount() > 1000000) {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());
        }

        System.out.println("ini tanggal sekarang "+ new Date());

        Date testDate1 = validationRegex.testDate(voucherRequest);
        Calendar testCalendar1 = validationRegex.testCalendar();

        if (testDate1.after(testCalendar1.getTime())){
            return ResponseFailed.wrapResponse(DealsStatus.EXPIRATION_DATE_INVALID, httpServletRequest.getServletPath());

        }

        if (testDate1.before(new Date()))
        {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());
        }

        if (!validation.aplhabetOnly(voucherRequest.getVoucherName())) {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());
        }

        if (!validation.aplhabetOnly(voucherRequest.getVoucherName()))
        {
            return new ResponseEntity<>(new MessageResponse("Your data is invalid.","043","/api/admin/"+idUser+"/merchant/"+idMerchant+"/vouchers",new Date()),
                    HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches(regex_double, ""+voucherRequest.getDiscount()) ||
                !Pattern.matches(regex_double, ""+voucherRequest.getMaxDiscount()) ||
                !Pattern.matches(regex_double, ""+voucherRequest.getVoucherPrice()) ||
                !Pattern.matches(regex_integer, ""+voucherRequest.getQuota()))
        {
            return ResponseFailed.wrapResponse(DealsStatus.DATA_INVALID, httpServletRequest.getServletPath());

        }

        if (voucherRepository.findByVoucherName(voucherRequest.getVoucherName()) != null) {
            return ResponseFailed.wrapResponse(DealsStatus.VOUCHER_NAME_EXISTS, httpServletRequest.getServletPath());
        }

        return ResponseFailed.wrapResponse(DealsStatus.STATUS_CHANGE, httpServletRequest.getServletPath());
    }
}
