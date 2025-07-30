package com.codera.hotelbooking.model.transactional;

import com.codera.hotelbooking.model.master.Booking;

import jakarta.persistence.*;

@Entity
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    private String guestName;
    private String guestEmail;
    
    
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Booking getBooking() {
		return booking;
	}


	public void setBooking(Booking booking) {
		this.booking = booking;
	}


	public String getGuestName() {
		return guestName;
	}


	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	public String getGuestEmail() {
		return guestEmail;
	}


	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}


	public BookingDetails(Long id, Booking booking, String guestName, String guestEmail) {
		super();
		this.id = id;
		this.booking = booking;
		this.guestName = guestName;
		this.guestEmail = guestEmail;
	}
    
	public BookingDetails() {
		System.out.println("Booking details loaded");
	}
    
}
