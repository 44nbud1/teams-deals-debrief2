package dana.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastingConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Bean
    ConnectionFactory connectionFactory()
    {
        AbstractConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPort(port);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(username);
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory createListener(ConnectionFactory connectionFactory)
    {
        SimpleRabbitListenerContainerFactory containerFactory =
                new SimpleRabbitListenerContainerFactory();

        containerFactory.setConnectionFactory(connectionFactory);

        containerFactory.setMaxConcurrentConsumers(10);

        containerFactory.setConcurrentConsumers(5);
        containerFactory.setAutoStartup(true);
        containerFactory.setMessageConverter(messageConverter());
        containerFactory.setPrefetchCount(1);
        containerFactory.setDefaultRequeueRejected(false);
        return containerFactory;
    }

    @Bean
    @Qualifier("shareOrderForVoucher")
    public FanoutExchange fanoutOrderForVoucher(){
        return new FanoutExchange("deals.fanout.order.voucher");
    }

    @Bean
    @Qualifier("shareOrderForMember")
    public FanoutExchange fanoutOrderForMember(){
        return new FanoutExchange("deals.fanout.order.member");
    }

    @Bean
    @Qualifier("shareMemberForOrder")
    public FanoutExchange fanoutMemberForOrder(){
        return new FanoutExchange("deals.fanout.member.order");
    }

    @Bean
    @Qualifier("shareOrderForVoucher")
    public Queue queueOrderForVoucher() {
        return new Queue("deals.queue.order.voucher");
    }

    @Bean
    @Qualifier("shareOrderForMember")
    public Queue queueOrderForMember() {
        return new Queue("deals.queue.order.member");
    }

    @Bean
    @Qualifier("shareMemberForOrder")
    public Queue queueMemberForOrder() {
        return new Queue("deals.queue.member.order");
    }

    @Bean
    @Qualifier("shareMemberForOrder")
    public Binding binding(FanoutExchange fanoutMemberForOrder,
                            Queue queueMemberForOrder) {
        return BindingBuilder.bind(queueMemberForOrder).to(fanoutMemberForOrder);
    }

}
