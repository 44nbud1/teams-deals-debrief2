package com.danapprentech.debrief2.voucherservice.rabbit.consumer;//package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.model.response.VoucherResponse;
import com.danapprentech.debrief2.voucherservice.rabbit.model.MemberConsumer;
import com.danapprentech.debrief2.voucherservice.rabbit.model.TransactionConsumer;
import com.danapprentech.debrief2.voucherservice.rabbit.model.UpdateQtyConsumer;
import com.danapprentech.debrief2.voucherservice.rabbit.producer.RabbitMqProducer;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import com.danapprentech.debrief2.voucherservice.service.VoucherServiceImpl;
import org.hibernate.annotations.Synchronize;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class Consumer
{
    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherServiceImpl voucherService;

    @Autowired
    RabbitMqProducer mqProducer;

    @Qualifier("shareOrderForVoucher")
    @RabbitListener(queues = "deals.queue.order.voucher")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void receive1(TransactionConsumer updateQtyConsumer)
    {
        if (updateQtyConsumer.getIdGoods() ==1 && updateQtyConsumer.getIdTransaction() == 3)
        {
            System.out.println("***********************************************************************");
            System.out.println("Id transaction :"+updateQtyConsumer.getIdTransaction());
        }

        if (updateQtyConsumer.getIdGoods() != null && updateQtyConsumer.getIdTransactionStatus() == 1)
        {
            System.out.println("-------------");
            Long update = Long.valueOf(updateQtyConsumer.getIdGoods());
            Voucher vouchers = voucherService.findByIdVoucher(update);
            System.out.println(vouchers.getQuota());
            System.out.println(updateQtyConsumer.getIdUser());
            System.out.println("Voucher sebelum dibeli "+vouchers.getQuota() );
            vouchers.setQuota(vouchers.getQuota() - 1);
            System.out.println("Voucher sesudah dibeli "+vouchers.getQuota() );
            vouchers.setUpdateAt(new Date());

            System.out.println(vouchers.getQuota());
            System.out.println();
            voucherService.updateVoucher(vouchers);
            System.out.println("update sukses");
            System.out.println("-------------");

            // kirim keyogi
            Voucher voucher = voucherService.findByVoucherName(vouchers.getVoucherName());
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
            voucherResponse.setIdTransaction(updateQtyConsumer.getIdTransaction());
            mqProducer.sendToRabbitVoucher(voucherResponse);
        }

        if (updateQtyConsumer.getIdGoods() != null && updateQtyConsumer.getIdTransactionStatus() == 3)
        {
            System.out.println("-------------");
            Long update = Long.valueOf(updateQtyConsumer.getIdGoods());
            Voucher vouchers = voucherService.findByIdVoucher(update);
            System.out.println(updateQtyConsumer.getIdUser());
            System.out.println("Voucher sebelum refund "+vouchers.getQuota() );
            vouchers.setQuota(vouchers.getQuota() + 1);
            System.out.println("Voucher sesudah refund "+vouchers.getQuota() );
            vouchers.setUpdateAt(new Date());
            System.out.println(vouchers.getQuota());
            voucherService.updateVoucher(vouchers);
            System.out.println("update sukses");
            System.out.println("-------------");

            // kirim keyogi
            Voucher voucher = voucherService.findByVoucherName(vouchers.getVoucherName());
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
            voucherResponse.setIdTransaction(updateQtyConsumer.getIdTransaction());
            mqProducer.sendToRabbitVoucher(voucherResponse);

        }
        System.out.println("yogi kirim ke Members");
        System.out.println("***********************************************************************");

    }
}
