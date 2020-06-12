package com.MemberDomain.usecase.broadcast;

import com.MemberDomain.model.Transaction;
import com.MemberDomain.model.response.UserDataResponse;
import com.MemberDomain.usecase.port.BalanceMapper;
import com.MemberDomain.usecase.port.UserMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    @Autowired
    UserMapper userMapper;

    @Autowired
    BalanceMapper balanceMapper;

    @Autowired
    MemberBroadcaster memberBroadcaster;

    @Qualifier("fanoutOrder")
    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(Transaction transaction) {
        UserDataResponse userDataResponse = userMapper.getUserData(transaction.getIdUser());

        double finalAmount;

        if(transaction.getIsCredit() == Boolean.FALSE){
            finalAmount = userDataResponse.getBalance() - transaction.getAmount();
        }
        else {
            finalAmount = userDataResponse.getBalance() + transaction.getAmount();
        }

        balanceMapper.updateBalance(finalAmount, transaction.getIdUser());
        UserDataResponse userDataResponse1 = userMapper.getUserData(transaction.getIdUser());
        System.out.println("A new balance: "+userDataResponse1.toString());
        memberBroadcaster.send(transaction.getIdUser());
    }
}
