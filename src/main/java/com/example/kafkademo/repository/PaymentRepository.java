package com.example.kafkademo.repository;

import com.example.kafkademo.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTimestampBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, String status);
    List<Payment> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
} 