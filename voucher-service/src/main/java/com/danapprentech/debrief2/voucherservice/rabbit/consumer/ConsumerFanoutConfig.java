package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

    import org.springframework.amqp.core.*;
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
    public Queue transactionQueue() {
        return new Queue("deals.order.queueaiewufc");
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue transactionQueue) {
        return BindingBuilder.bind(transactionQueue).to(fanout);
    }

    @Bean
    public Consumer consumers() {
        return new Consumer();
    }
}