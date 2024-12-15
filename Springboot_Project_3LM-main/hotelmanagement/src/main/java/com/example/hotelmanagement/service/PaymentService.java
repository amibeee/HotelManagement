package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.PaymentDTO;
import com.example.hotelmanagement.entity.Payment;
import com.example.hotelmanagement.entity.Reservation;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.PaymentRepository;
import com.example.hotelmanagement.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Reservation reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        Payment payment = Payment.builder()
                .reservation(reservation)
                .amount(paymentDTO.getAmount())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .paymentStatus(paymentDTO.getPaymentStatus())
                .transactionDate(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        return convertToDTO(savedPayment);
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return convertToDTO(payment);
    }

    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        existingPayment.setPaymentStatus(paymentDTO.getPaymentStatus());

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return convertToDTO(updatedPayment);
    }

    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        paymentRepository.delete(payment);
    }

    public List<PaymentDTO> getPaymentsByReservation(Long reservationId) {
        return paymentRepository.findByReservationId(reservationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .reservationId(payment.getReservation().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionDate(payment.getTransactionDate())
                .build();
    }
}