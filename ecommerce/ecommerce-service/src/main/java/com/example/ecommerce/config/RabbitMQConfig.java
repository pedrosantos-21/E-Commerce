package com.example.ecommerce.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "ecommerce.direct";
    public static final String DLX_NAME = "ecommerce.dlx"; // Exchange de Erro
    
    public static final String QUEUE_WELCOME_EMAIL = "customer.v1.welcome-email";
    public static final String QUEUE_WELCOME_EMAIL_DLQ = "customer.v1.welcome-email.dlq"; // Fila de Erro
    
    public static final String ROUTING_KEY_WELCOME = "customer.welcome";

    @Bean
    public DirectExchange ecommerceExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_NAME);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @DependsOn("rabbitAdmin")
    public Queue welcomeEmailQueue(RabbitAdmin rabbitAdmin) {
        // Delete the queue if it exists to ensure it's recreated with the correct arguments.
        // WARNING: This may cause message loss if messages are in the queue during application startup.
        rabbitAdmin.deleteQueue(QUEUE_WELCOME_EMAIL);

        Map<String, Object> args = new HashMap<>();
        // Se a mensagem falhar nesta fila, envie-a para a DLX
        args.put("x-dead-letter-exchange", DLX_NAME);
        args.put("x-dead-letter-routing-key", ROUTING_KEY_WELCOME);
        return QueueBuilder.durable(QUEUE_WELCOME_EMAIL).withArguments(args).build();
    }

    @Bean
    public Queue welcomeEmailDLQ() {
        return QueueBuilder.durable(QUEUE_WELCOME_EMAIL_DLQ).build();
    }

    @Bean
    public Binding welcomeEmailBinding() {
        return BindingBuilder.bind(welcomeEmailQueue()).to(ecommerceExchange()).with(ROUTING_KEY_WELCOME);
    }

    @Bean
    public Binding welcomeEmailDLQBinding() {
        return BindingBuilder.bind(welcomeEmailDLQ()).to(deadLetterExchange()).with(ROUTING_KEY_WELCOME);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
