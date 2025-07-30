package com.codera.hotelbooking.model.master;

import java.time.LocalDateTime;

import com.codera.hotelbooking.model.authentication.User;


import jakarta.persistence.*;;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public Booking(Long id, User user, Room room, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
    
    public Booking() {
    	System.out.println("Booking entity loaded");
    }
}
