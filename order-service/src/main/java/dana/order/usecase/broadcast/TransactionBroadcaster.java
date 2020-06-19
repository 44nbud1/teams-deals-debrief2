package dana.order.usecase.broadcast;

import dana.order.entity.Transaction;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.DatabaseRepository;
import dana.order.usecase.port.TransactionRepository;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
    DatabaseRepository databaseRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void send(Integer idTransaction){

        Transaction transaction = databaseRepository.getTransactionById(idTransaction);
        System.out.println("SENDING TO ALL : "+transaction.toJsonString());
        rabbitTemplate.convertAndSend(fanoutOrderForVoucher.getName(), "", transaction);
        rabbitTemplate.convertAndSend(fanoutOrderForMember.getName(), "", transaction);

    }
}
