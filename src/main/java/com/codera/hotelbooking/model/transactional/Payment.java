package com.codera.hotelbooking.model.transactional;

import java.time.LocalDateTime;

import com.codera.hotelbooking.model.master.Booking;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Payment(Long id, Booking booking, double amount, LocalDateTime paymentDate, String paymentMethod) {
		super();
		this.id = id;
		this.booking = booking;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
	}
	
	public Payment() {
		System.out.println("Payment Classs loaded");
	}
    
   
}
