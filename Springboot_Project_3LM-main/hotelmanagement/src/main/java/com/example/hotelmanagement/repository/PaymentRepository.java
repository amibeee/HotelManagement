package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByReservationId(Long reservationId);
    List<Payment> findByPaymentStatus(String paymentStatus);
}