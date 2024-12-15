package com.example.hotelmanagement.dto;

import lombok.*;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String role;
    private String avatarUrl;
    private String imagePath;


}