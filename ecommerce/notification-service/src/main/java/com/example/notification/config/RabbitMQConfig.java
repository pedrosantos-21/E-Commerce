package com.example.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_WELCOME_EMAIL = "customer.v1.welcome-email";

    // O Consumer só precisa declarar a fila para começar a escutá-la
    @Bean
    public Queue welcomeEmailQueue() {
        return QueueBuilder.durable(QUEUE_WELCOME_EMAIL).build();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
