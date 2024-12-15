package com.example.hotelmanagement.dto;

import com.example.hotelmanagement.entity.Room;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Room roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfPeople;
    private double amount;
    private String paymentStatus;
}
