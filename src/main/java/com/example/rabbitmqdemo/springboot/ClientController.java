package com.example.rabbitmqdemo.springboot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public String sendMessage() {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.TOPIC_EXCHANGER_NAME, "foo.bar.baz", "Hello from RabbitMQ!");
        return "OK";
    }
}
