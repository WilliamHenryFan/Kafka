package com.example.kafkademo.model;

import lombok.Data;

@Data
public class ChatMessage {
    private String username;
    private String content;
    private String timestamp;
    private String type;
} 