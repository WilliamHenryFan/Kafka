package com.example.kafkademo.controller;

import com.example.kafkademo.service.KafkaConsumerService;
import com.example.kafkademo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/send")
    @ResponseBody
    public String sendMessage(@RequestParam String username, @RequestParam String message) {
        kafkaProducerService.sendMessage(username, message);
        return "Message sent";
    }

    @GetMapping("/chat/stream")
    public SseEmitter streamChat() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        kafkaConsumerService.addEmitter(emitter);
        return emitter;
    }
} 