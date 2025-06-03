package com.example.kafkademo.service;

import com.example.kafkademo.controller.RegisterController;
import com.example.kafkademo.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumerService {

    @Autowired
    private RegisterController registerController;

    @KafkaListener(topics = "send-email", groupId = "email-group")
    public void consumeEmailMessage(ChatMessage message) {
        try {
            // 模擬發送郵件
            Thread.sleep(5000); // 模擬郵件發送延遲

            // 通知前端郵件已發送
            registerController.notifyEmailSent(message.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 