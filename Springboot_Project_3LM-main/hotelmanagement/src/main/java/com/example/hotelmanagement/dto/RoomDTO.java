package com.example.hotelmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String roomType;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private boolean isAvailable;
    private String description;
    private String image;
}