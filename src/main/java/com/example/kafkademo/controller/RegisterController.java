package com.example.kafkademo.controller;

import com.example.kafkademo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class RegisterController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register/sync")
    public ResponseEntity<?> syncRegister(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");

        try {
            // 配置一個無法連接的 SMTP 服務器
            JavaMailSender mailSender = createMailSender();
            
            // 創建郵件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@example.com");
            message.setTo(email);
            message.setSubject("註冊確認信");
            message.setText("親愛的 " + username + "：\n\n感謝您註冊我們的服務！\n\n此致\n系統管理員");

            // 嘗試發送郵件（會因為無法連接 SMTP 服務器而失敗）
            mailSender.send(message);
            
            return ResponseEntity.ok(Map.of("message", "註冊成功"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "郵件發送失敗：" + e.getMessage()));
        }
    }

    private JavaMailSender createMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("192.168.1.1"); // 使用一個無法連接的 IP
        mailSender.setPort(25);
        mailSender.setUsername("user");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @PostMapping("/register/async")
    public ResponseEntity<?> asyncRegister(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");

        // 發送到 Kafka
        kafkaProducerService.sendEmailMessage(username, email);

        return ResponseEntity.ok(Map.of("message", "註冊成功"));
    }

    @GetMapping("/register/status")
    public SseEmitter registerStatus() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void notifyEmailSent(String username) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(Map.of(
                    "status", "EMAIL_SENT",
                    "username", username
                ));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        });
    }
} 