package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

import com.danapprentech.debrief2.voucherservice.rabbit.model.MemberConsumer;
import com.danapprentech.debrief2.voucherservice.rabbit.model.RegisterRequest;
import com.danapprentech.debrief2.voucherservice.repository.MemberRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MemberListener {

    @Autowired
    MemberRepository memberRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Qualifier("shareMemberForVoucher")
    @RabbitListener(queues = "deals.queue.member.voucher")
    public void receive(RegisterRequest user)
    {
        System.out.println("receive data : "+user  );
        MemberConsumer memberConsumer = new MemberConsumer();
        memberConsumer.setIdUser(user.getIdUser());
        memberConsumer.setPhoneNumber(user.getPhoneNumber());
        memberConsumer.setCreatedAt(new Date());
        memberConsumer.setUpdatedAt(new Date());
        memberRepository.save(memberConsumer);
        System.out.println("save user id : "+user.getIdUser());
    }
}
