package com.codera.hotelbooking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codera.hotelbooking.model.master.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Add custom query methods if needed
}
