package com.example.rabbitmqdemo.console.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TaskReceiverApp {
    public static final String TASK_QUEUE_NAME = "task-queue1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("[*] Waiting for messages");

        channel.basicQos(1); // максимальное число необработанных задач можно брать

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);  //подтверждаем,что обработали посылку с указанным Tag и можно убирать ее из очереди
            } finally {
                System.out.println("[x] Done");

            }
        };
        boolean autoAck = false; // ручное подтверждение обработки сообщений
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
        });
    }

    public static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
