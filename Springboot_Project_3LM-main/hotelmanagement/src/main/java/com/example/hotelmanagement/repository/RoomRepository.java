package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomType(String roomType);
    List<Room> findByIsAvailable(boolean isAvailable);
    Room findByRoomNumber(String roomNumber);
}