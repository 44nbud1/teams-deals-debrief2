package com.MemberDomain.usecase.broadcast;

import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.port.UserMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemberBroadcaster {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("shareMemberForOrder")
    FanoutExchange fanoutMemberForOrder;

    @Autowired
    UserMapper userMapper;

    public void send(String idUser){
        UserDataResponse userDataResponse = userMapper.getUserData(idUser);
        System.out.println(userDataResponse.toString());
        rabbitTemplate.convertAndSend(fanoutMemberForOrder.getName(), "", userDataResponse);
    }
}
