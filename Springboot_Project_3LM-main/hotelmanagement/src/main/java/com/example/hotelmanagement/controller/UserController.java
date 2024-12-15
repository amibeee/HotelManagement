package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.*;
import com.example.hotelmanagement.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Content-Disposition")

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }


    @PatchMapping("/{id}/uploadImage")
    public ResponseEntity<UserDTO> uploadUserImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file) {
        UserDTO updatedUser = userService.updateUserImage(id, file);
        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

}