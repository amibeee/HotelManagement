package com.example.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name ="room_id")
    private Room roomId;


    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfPeople;
    private double amount;
    private String paymentStatus;
    private LocalDateTime paymentTime;

}