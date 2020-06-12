package com.danapprentech.debrief2.voucherservice.rabbit.consumer;//package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.rabbit.model.Transaction;
import com.danapprentech.debrief2.voucherservice.rabbit.model.UpdateQtyConsumer;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Consumer
{
    @Autowired
    VoucherRepository voucherRepository;

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

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive1(Transaction updateQtyConsumer) throws InterruptedException
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
    }
}
