//package Test;
//
//import com.MemberDomain.model.request.RegisterRequest;
//import com.MemberDomain.usecase.port.UserMapper;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TestRabbit
//{
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    @Qualifier("shareMemberForOrder")
//    FanoutExchange fanoutMemberForOrder;
//
//    @Autowired
//    @Qualifier("shareMemberForVoucher")
//    FanoutExchange fanoutMemberForVoucher;
//
//    @Autowired
//    UserMapper userMapper;
//
//    @PostMapping("test")
//    public void send(RegisterRequest idUser){
////        UserDataResponse userDataResponse = userMapper.getUserData(idUser);
////        System.out.println(userDataResponse.toString());
////        rabbitTemplate.convertAndSend(fanoutMemberForOrder.getName(), "", userDataResponse);
////        rabbitTemplate.convertAndSend(fanoutMemberForVoucher.getName(), "", userDataResponse);
//        System.out.println(idUser);
//                rabbitTemplate.convertAndSend(fanoutMemberForVoucher.getName(), "", idUser);
//    }
//}
