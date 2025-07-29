package com.codera.hotelbooking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codera.hotelbooking.model.master.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Add custom query methods if needed
}
