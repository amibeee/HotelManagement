package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.RoomDTO;
import com.example.hotelmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<RoomDTO>> getRoomsByType(@PathVariable String roomType) {
        return ResponseEntity.ok(roomService.getRoomsByType(roomType));
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }
}