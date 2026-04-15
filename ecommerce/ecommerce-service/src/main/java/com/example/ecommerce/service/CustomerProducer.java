package com.example.ecommerce.service;

import com.example.ecommerce.config.RabbitMQConfig;
import com.example.ecommerce.dto.CustomerMessageDTO;
import com.example.ecommerce.model.Customer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendWelcomeMessage(Customer customer) {
        CustomerMessageDTO message = new CustomerMessageDTO(customer.getName(), customer.getEmail());
        
        System.out.println("Postando mensagem no RabbitMQ para o cliente: " + message.email());
        
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME, 
                RabbitMQConfig.ROUTING_KEY_WELCOME, 
                message
        );
    }
}
