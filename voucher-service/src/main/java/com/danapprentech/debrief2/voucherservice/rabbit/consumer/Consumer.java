package com.danapprentech.debrief2.voucherservice.rabbit.consumer;//package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.model.response.VoucherResponse;
import com.danapprentech.debrief2.voucherservice.rabbit.model.MemberConsumer;
import com.danapprentech.debrief2.voucherservice.rabbit.model.TransactionConsumer;
import com.danapprentech.debrief2.voucherservice.rabbit.producer.RabbitMqProducer;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Consumer
{
    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    RabbitMqProducer mqProducer;

//    @RabbitListener(queues = "${spring.rabbitmq.queue.listener}",containerFactory = "createListener")
//    public void updateQuota(Transaction updateQtyConsumer)
//    {
//        System.out.println("-------------");
//        Long update = Long.valueOf(updateQtyConsumer.getIdGoods());
//        Voucher vouchers = voucherRepository.findByIdVoucher(update);
//        System.out.println(vouchers.getQuota() );
//        vouchers.setQuota(vouchers.getQuota()-1);
//        vouchers.setUpdateAt(new Date());
//        System.out.println("update sukses");
//        System.out.println(vouchers.getQuota());
//        voucherRepository.save(vouchers);
//    }

    @RabbitListener(queues = "deals.order.queueaiewufc")
    public void receive1(TransactionConsumer updateQtyConsumer) throws InterruptedException
    {
        System.out.println("-------------");
        Long update = Long.valueOf(updateQtyConsumer.getIdGoods());
        Voucher vouchers = voucherRepository.findByIdVoucher(update);
        System.out.println(vouchers.getQuota() );
        vouchers.setQuota(vouchers.getQuota()-1);
        vouchers.setUpdateAt(new Date());
        System.out.println("update sukses");
        System.out.println(vouchers.getQuota());
        voucherRepository.save(vouchers);

        // kirim keyogi

        Voucher voucher = voucherRepository.findByVoucherName(vouchers.getVoucherName());
        // response
        VoucherResponse voucherResponse = new VoucherResponse();
        voucherResponse.setVoucherName(voucher.getVoucherName());
        voucherResponse.setDiscount(voucher.getDiscount());
        voucherResponse.setVoucherPrice(voucher.getVoucherPrice());
        voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
        voucherResponse.setQuota(voucher.getQuota());
        voucherResponse.setExpiredDate(voucher.getExpiredDate());
        voucherResponse.setStatus(voucher.getStatus());
        voucherResponse.setIdMerchant(voucher.getMerchant().getIdMerchant());
        voucherResponse.setIdVoucher(voucher.getIdVoucher());
        mqProducer.sendToRabbitVoucher(voucherResponse);

    }

    //deals.member.hanggu
    @Qualifier("fanoutMember")
    @RabbitListener(queues = "deals.member.queuehanggu")
    public void receive(MemberConsumer user)
    {
        System.out.println(user);
    }

}
