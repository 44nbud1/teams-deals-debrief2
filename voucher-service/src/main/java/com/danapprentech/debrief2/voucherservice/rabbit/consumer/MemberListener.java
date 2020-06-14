package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

import com.danapprentech.debrief2.voucherservice.rabbit.model.MemberConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberListener {


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Qualifier("shareMemberForVoucher")
    @RabbitListener(queues = "deals.queue.member.voucher")
    public void receive(MemberConsumer user)
    {
        System.out.println(user);
    }
}
