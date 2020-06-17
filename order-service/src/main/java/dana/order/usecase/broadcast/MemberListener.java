package dana.order.usecase.broadcast;

import dana.order.entity.User;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberListener {

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    UserRepository userRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Qualifier("shareMemberForOrder")
    @RabbitListener(queues = "deals.queue.member.order")
    public void receive(User user) {
        System.out.println("MEMBER RAW : "+user.toJsonString());
        if (userRepository.doesUserExist(user.getIdUser()) == Boolean.FALSE){
            databaseMapper.createNewUser(user);
        }else{
            databaseMapper.updateAUser(user);
        }
        System.out.println("MEMBER RESULT : "+user.toJsonString());
    }
}
