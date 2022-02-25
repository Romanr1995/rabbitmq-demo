package com.example.rabbitmqdemo.springboot;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    public void receiverMessage(String message) {
        System.out.println("Received from topic <" + message + ">");
    }
}
