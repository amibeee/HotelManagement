package com.example.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
    private String avatarUrl;
    @Column(length = 500) // Adjust length as needed
    private String imagePath;

}