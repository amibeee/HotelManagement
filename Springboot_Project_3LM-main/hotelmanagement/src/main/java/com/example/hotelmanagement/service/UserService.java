package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.*;
import com.example.hotelmanagement.entity.*;
import com.example.hotelmanagement.repository.*;
import com.example.hotelmanagement.exception.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.upload.dir:${user.home}/uploads/}")
    private String uploadDir;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setAddress(userDTO.getAddress());



        User updatedUser = userRepository.save(existingUser);


        System.out.println("Updated user saved: " + updatedUser);



        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public UserDTO updateUserImage(Long id, MultipartFile file) {
        // Validate file
        validateImageFile(file);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        try {

            Path uploadPath = Paths.get(uploadDir, "users")
                    .toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String newFilename = "user-" + id + "-" + System.currentTimeMillis() + fileExtension;

            // Full path for the file
            Path targetLocation = uploadPath.resolve(newFilename);

            // Copy file to target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Update user's image path
            String relativePath = "uploads/users/" + newFilename;
            user.setImagePath(relativePath);

            // Save updated user
            User updatedUser = userRepository.save(user);

            // Map and return DTO
            return modelMapper.map(updatedUser, UserDTO.class);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + file.getOriginalFilename(), ex);
        }
    }




    private void validateImageFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new BadRequestException("Cannot upload empty file");
        }


        long MAX_FILE_SIZE = 5 * 1024 * 1024;
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File size should not exceed 5MB");
        }


        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif"))) {
            throw new BadRequestException("Only JPEG, PNG, and GIF images are allowed");
        }
    }
}
