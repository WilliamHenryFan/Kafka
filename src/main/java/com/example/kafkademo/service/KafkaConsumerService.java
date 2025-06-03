package com.example.kafkademo.service;

import com.example.kafkademo.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaConsumerService {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(ChatMessage message) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(message);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        });
    }

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }
} 