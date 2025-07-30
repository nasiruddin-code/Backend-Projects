package com.codera.hotelbooking.model.master;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    
    private String roomNumber;
    private int capacity;
    private double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Room(Long id, Hotel hotel, String roomNumber, int capacity, double price) {
		super();
		this.id = id;
		this.hotel = hotel;
		this.roomNumber = roomNumber;
		this.capacity = capacity;
		this.price = price;
	}
    
    public Room() {
    	System.out.println("room entity loaded");
    }
}

