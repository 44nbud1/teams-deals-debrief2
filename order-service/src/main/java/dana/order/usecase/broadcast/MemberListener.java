package dana.order.usecase.broadcast;

import dana.order.entity.User;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemberListener {

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    UserRepository userRepository;

    @Qualifier("fanoutMember")
    @RabbitListener(queues = "deals.member.queuehanggu")
    public void receive(User user) {
        System.out.println(user);
        if (userRepository.doesUserExist(user.getIdUser()) == Boolean.FALSE){
            databaseMapper.createNewUser(user);
        }else{
            databaseMapper.updateAUser(user);
        }
        System.out.println("A new user: "+user.toJsonString());
    }
}
