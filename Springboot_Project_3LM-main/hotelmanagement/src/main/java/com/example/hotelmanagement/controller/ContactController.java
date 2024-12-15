package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.ContactFormDTO;
import com.example.hotelmanagement.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@Valid @RequestBody ContactFormDTO contactForm, HttpServletRequest request) {
        System.out.println("Received a " + request.getMethod() + " request");

        String adminMessage = "Nouveau message reçu de " + contactForm.getName() + " (" + contactForm.getEmail() + "):\n\n"
                + contactForm.getMessage();
        emailService.sendEmail("admin@example.com", "Nouveau message de contact", adminMessage);


        String userMessage = "Bonjour " + contactForm.getName() + ",\n\nMerci de nous avoir contactés ! "
                + "Nous avons bien reçu votre message et nous reviendrons vers vous sous peu.\n\nCordialement,\nL'équipe.";
        emailService.sendEmail(contactForm.getEmail(), "Confirmation de réception", userMessage);

        return ResponseEntity.ok("Message envoyé avec succès !");
    }
}
