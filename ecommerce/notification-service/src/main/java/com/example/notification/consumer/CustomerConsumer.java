package com.example.notification.consumer;

import com.example.notification.dto.CustomerMessageDTO;
import com.example.notification.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_WELCOME_EMAIL)
    public void consumeWelcomeMessage(CustomerMessageDTO message) {
        System.out.println("--------------------------------------------------");
        System.out.println("MICROSERVIÇO DE NOTIFICAÇÃO: Mensagem Recebida!");
        System.out.println("Processando e-mail para: " + message.email());
        System.out.println("Status: E-mail de Boas-vindas enviado com sucesso!");
        System.out.println("--------------------------------------------------");
    }
}
