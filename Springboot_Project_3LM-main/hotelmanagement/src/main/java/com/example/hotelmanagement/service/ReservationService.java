package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.*;
import com.example.hotelmanagement.entity.*;
import com.example.hotelmanagement.repository.*;
import com.example.hotelmanagement.exception.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(res -> modelMapper.map(res, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setUser(user);

        Reservation savedReservation = reservationRepository.save(reservation);
        return modelMapper.map(savedReservation, ReservationDTO.class);
    }

    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return modelMapper.map(reservation, ReservationDTO.class);
    }
    public List<ReservationDTO> getReservationsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return reservationRepository.findByUserId(userId).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    public void cancelReservation(Long reservationId, Long userId) {
        try {

            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));


            if (!reservation.getUser().getId().equals(userId)) {
                throw new UnauthorizedActionException("You are not authorized to cancel this reservation");
            }


            reservationRepository.delete(reservation);
        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("Error cancelling reservation: " + e.getMessage(), e);
        }
    }

    public void updatePaymentStatus(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        reservation.setPaymentStatus("Paid");
        reservation.setPaymentTime(LocalDateTime.now());
        reservationRepository.save(reservation);
    }



}
