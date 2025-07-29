package com.codera.hotelbooking.service;

import com.codera.hotelbooking.model.transactional.BookingDetails;
import com.codera.hotelbooking.repository.BookingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingDetailsService {

    private final BookingDetailsRepository bookingDetailsRepository;

    @Autowired
    public BookingDetailsService(BookingDetailsRepository bookingDetailsRepository) {
        this.bookingDetailsRepository = bookingDetailsRepository;
    }

    public List<BookingDetails> getAllBookingDetails() {
        return bookingDetailsRepository.findAll();
    }

    public Optional<BookingDetails> getBookingDetailsById(Long id) {
        return bookingDetailsRepository.findById(id);
    }

    public BookingDetails createBookingDetails(BookingDetails bookingDetails) {
        return bookingDetailsRepository.save(bookingDetails);
    }

    public BookingDetails updateBookingDetails(BookingDetails bookingDetails) {
        return bookingDetailsRepository.save(bookingDetails);
    }

    public void deleteBookingDetails(Long id) {
        bookingDetailsRepository.deleteById(id);
    }
}
