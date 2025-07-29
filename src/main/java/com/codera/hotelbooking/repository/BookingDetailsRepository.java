package com.codera.hotelbooking.repository;

import com.codera.hotelbooking.model.transactional.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
    // You can add custom query methods here if needed
}
