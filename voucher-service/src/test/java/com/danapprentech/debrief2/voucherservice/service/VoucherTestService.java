package com.danapprentech.debrief2.voucherservice.service;

import com.danapprentech.debrief2.voucherservice.model.Merchant;
import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class VoucherTestService
{
    @Autowired
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherService voucherService;

    @Test
    public void saveVoucher() throws ParseException {

        Merchant merchant1 = new Merchant();
        merchant1.setCreatedAt(new Date());
        merchant1.setUpdatedAt(new Date());
        merchant1.setIdMerchant(1001L);
        merchant1.setMerchantName("kfc");

        Voucher voucher2 = new Voucher(
                null,"1001L","Crazy Deals","kfc",10000.,10.,10000.,5,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .parse("2020-06-19 15:30:14.332"),Boolean.TRUE,new Date(),
                new Date(),merchant1);

//        voucher2.setStatus(Boolean.TRUE);
//        voucher2.setQuota(1000);
//        voucher2.setExpiredDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//                .parse("2021-06-15 15:30:14.332"));
//        voucher2.setMaxDiscount(5000D);
//        voucher2.setIdUser("11002");
//        voucher2.setCreateAt(new Date());
//        voucher2.setUpdateAt(new Date());
//        voucher2.setDiscount(10D);
//        voucher2.setIdVoucher(null);
////        voucher2.setMerchant(merchant1);
//        voucher2.setVoucherName("Crazy Deals");
//        voucher2.setVoucherPrice(10000D);

//        System.out.println(voucher2);
//        given(voucherRepository.save(voucher2)).willAnswer(invocation -> invocation.getArgument(0));

        Voucher savedVoucher = voucherService.save(voucher2);
        assertThat(savedVoucher).isNotNull();
        verify(voucherRepository).save(any(Voucher.class));

        /*
                User savedUser = userService.createUser(user);

        assertThat(savedUser).isNotNull();

        verify(userRepository).save(any(User.class));
         */

    }
}
