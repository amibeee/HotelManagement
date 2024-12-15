package com.example.hotelmanagement.controller;
import com.example.hotelmanagement.dto.*;
import com.example.hotelmanagement.entity.Reservation;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.ReservationRepository;
import com.example.hotelmanagement.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {


    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }
    @DeleteMapping("/{userId}/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long userId,
            @PathVariable Long reservationId
    ) {
        reservationService.cancelReservation(reservationId, userId);
        return ResponseEntity.noContent().build();
    }







}