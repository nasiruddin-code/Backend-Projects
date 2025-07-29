package com.codera.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codera.hotelbooking.model.master.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Add custom query methods if needed
}
