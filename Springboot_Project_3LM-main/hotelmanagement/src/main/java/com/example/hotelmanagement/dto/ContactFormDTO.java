package com.example.hotelmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactFormDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le message est obligatoire")
    private String message;
}
