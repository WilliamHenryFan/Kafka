package com.example.kafkademo.service;

import com.example.kafkademo.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    private static final String CHAT_TOPIC = "chat-topic";
    private static final String EMAIL_TOPIC = "send-email";

    public void sendMessage(String username, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUsername(username);
        chatMessage.setContent(message);
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        chatMessage.setType("producer");

        kafkaTemplate.send(CHAT_TOPIC, chatMessage);
    }

    public void sendEmailMessage(String username, String email) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUsername(username);
        chatMessage.setContent("Email: " + email);
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        chatMessage.setType("email");

        kafkaTemplate.send(EMAIL_TOPIC, chatMessage);
    }
} 