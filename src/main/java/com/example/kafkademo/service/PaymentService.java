package com.example.kafkademo.service;

import com.example.kafkademo.model.Payment;
import com.example.kafkademo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<String, Payment> paymentKafkaTemplate;

    @Autowired
    private PaymentRepository paymentRepository;

    private static final String PAYMENT_TOPIC = "payment-topic";

    public Payment savePayment(Payment payment) {
        payment.setTimestamp(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        paymentKafkaTemplate.send(PAYMENT_TOPIC, savedPayment);
        return savedPayment;
    }

    public List<Payment> searchPayments(LocalDateTime startDate, LocalDateTime endDate, String status) {
        if (status == null || status.equals("all")) {
            return paymentRepository.findByTimestampBetween(startDate, endDate);
        }
        return paymentRepository.findByTimestampBetweenAndStatus(startDate, endDate, status);
    }

    public void replayPayments(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> payments = paymentRepository.findByTimestampBetween(startDate, endDate);
        payments.forEach(payment -> paymentKafkaTemplate.send(PAYMENT_TOPIC, payment));
    }
} 