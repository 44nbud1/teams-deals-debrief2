package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

    import org.springframework.amqp.core.*;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;


@Configuration
public class ConsumerFanoutConfig
{
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("deals.order.yes1sfe94hksjf");
    }

    @Bean
    @Qualifier("fanoutMember")
    public Queue memberQueue(){
        return new Queue("deals.member.queuehanggu");
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue("deals.order.queueaiewufc");
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue transactionQueue) {
        return BindingBuilder.bind(transactionQueue).to(fanout);
    }

    @Bean
    @Qualifier("fanoutMember")
    public Binding bindingMember(FanoutExchange fanoutMember,
                           Queue memberQueue) {
        return BindingBuilder.bind(memberQueue).to(fanoutMember);
    }

    @Bean
    public Consumer consumers() {
        return new Consumer();
    }

}