package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.RoomDTO;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.BadRequestException;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public RoomDTO createRoom(RoomDTO roomDTO) {
        // Check if room number already exists
        if (roomRepository.findByRoomNumber(roomDTO.getRoomNumber()) != null) {
            throw new BadRequestException("Room number already exists");
        }

        Room room = Room.builder()
                .roomNumber(roomDTO.getRoomNumber())
                .roomType(roomDTO.getRoomType())
                .pricePerNight(roomDTO.getPricePerNight())
                .capacity(roomDTO.getCapacity())
                .isAvailable(roomDTO.isAvailable())
                .description(roomDTO.getDescription())
                .image(roomDTO.getImage())
                .build();

        Room savedRoom = roomRepository.save(room);
        return convertToDTO(savedRoom);
    }

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return convertToDTO(room);
    }

    @Transactional
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        // Update room details
        existingRoom.setRoomType(roomDTO.getRoomType());
        existingRoom.setPricePerNight(roomDTO.getPricePerNight());
        existingRoom.setCapacity(roomDTO.getCapacity());
        existingRoom.setAvailable(roomDTO.isAvailable());
        existingRoom.setDescription(roomDTO.getDescription());
        existingRoom.setImage(roomDTO.getImage());

        Room updatedRoom = roomRepository.save(existingRoom);
        return convertToDTO(updatedRoom);
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.delete(room);
    }

    public List<RoomDTO> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAvailableRooms() {
        return roomRepository.findByIsAvailable(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoomDTO convertToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .pricePerNight(room.getPricePerNight())
                .capacity(room.getCapacity())
                .isAvailable(room.isAvailable())
                .description(room.getDescription())
                .image(room.getImage())
                .build();
    }
}