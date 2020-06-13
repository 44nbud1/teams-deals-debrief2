package dana.order.usecase.broadcast;

import dana.order.entity.Transaction;
import dana.order.usecase.port.DatabaseMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionBroadcaster {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Qualifier("shareOrderForVoucher")
    @Autowired
    FanoutExchange fanoutOrderForVoucher;

    @Qualifier("shareOrderForMember")
    @Autowired
    FanoutExchange fanoutOrderForMember;

    @Autowired
    DatabaseMapper databaseMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void send(Integer idTransaction){

        Transaction transaction = databaseMapper.getTransactionById(idTransaction);
        System.out.println(transaction.toJsonString());
        rabbitTemplate.convertAndSend(fanoutOrderForVoucher.getName(), "", transaction);
        rabbitTemplate.convertAndSend(fanoutOrderForMember.getName(), "", transaction);

    }
}
