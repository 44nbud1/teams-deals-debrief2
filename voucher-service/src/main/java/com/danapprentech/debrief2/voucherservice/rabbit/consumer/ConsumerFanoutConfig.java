package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

    import org.springframework.amqp.core.*;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;


@Configuration
public class ConsumerFanoutConfig
{
    @Bean
    @Qualifier("shareOrderForVoucher")
    public FanoutExchange fanoutOrderForVoucher() {
        return new FanoutExchange("deals.fanout.order.voucher");
    }

    @Bean
    @Qualifier("shareOrderForVoucher")
    public Queue queueOrderForVoucher() {
        return new Queue("deals.queue.order.voucher");
    }

    @Bean
    @Qualifier("shareOrderForVoucher")
    public Binding binding(FanoutExchange fanoutOrderForVoucher, Queue queueOrderForVoucher) {
        return BindingBuilder.bind(queueOrderForVoucher).to(fanoutOrderForVoucher);
    }

    @Bean
    public Consumer consumers() {
        return new Consumer();
    }
}