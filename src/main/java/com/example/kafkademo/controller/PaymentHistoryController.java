package com.example.kafkademo.controller;

import com.example.kafkademo.model.Payment;
import com.example.kafkademo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment-history")
public class PaymentHistoryController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String paymentHistoryPage() {
        return "payment-history";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Payment>> searchPayments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status) {
        
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(LocalTime.MAX) : null;
        
        List<Payment> payments = paymentService.searchPayments(startDateTime, endDateTime, status);
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/replay")
    @ResponseBody
    public ResponseEntity<Map<String, String>> replayPayments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        paymentService.replayPayments(startDateTime, endDateTime);
        return ResponseEntity.ok(Map.of("message", "重播請求已接收，正在處理中"));
    }
} 