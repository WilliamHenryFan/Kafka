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

    /***
     * Kafka 的 kafkaTemplate.send(...) 方法有多個多載版本：
     * send(String topic, V data) → 沒有指定 key → Kafka 會使用 "null key"
     * send(String topic, K key, V data) → 你可以指定 key → Kafka 會根據 key 做分區選擇
     * Kafka 會根據 key 計算 hash，來決定把訊息寫入哪個 partition。
     * 當 key 為 null 時，Kafka 會用 round-robin 方式將訊息平均分配到可用 partition，這樣可能導致順序錯亂。
     */


    /**
     * send(String topic, V data)
     * @param username
     * @param message
     */
    public void sendMessage(String username, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUsername(username);
        chatMessage.setContent(message);
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        chatMessage.setType("producer");

        kafkaTemplate.send(CHAT_TOPIC, chatMessage);
    }

    /**
     * send(String topic, K key, V data)
     * 若要確保同一 key（例如「某個用戶 ID」）的事件順序不亂，必須確保它永遠寫入同一個分區（Kafka 根據 key 做 hash 分配）。
     * @param username
     * @param email
     */
//    public void sendMessage(String username, String email) {
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setUsername(username);
//        chatMessage.setContent(email);
//        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        chatMessage.setType("email");
//
//        kafkaTemplate.send(topic,username,chatMessage);
//    }


    public void sendEmailMessage(String username, String email) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUsername(username);
        chatMessage.setContent("Email: " + email);
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        chatMessage.setType("email");

        kafkaTemplate.send(EMAIL_TOPIC, chatMessage);
    }
} 