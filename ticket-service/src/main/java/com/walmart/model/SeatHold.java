package com.walmart.model;

import java.time.LocalDateTime;
import java.util.Set;

public class SeatHold {
	private int id;
	private String message;
	private Set<Seat> seats;
	private String bookingEmail;
	private LocalDateTime reservedTime = LocalDateTime.now();
	
	
	public SeatHold(int id, String message, Set<Seat> seats,
			String bookingEmail) {
		super();
		this.id = id;
		this.message = message;
		this.seats = seats;
		this.bookingEmail = bookingEmail;
	}
	
	public SeatHold(int id, String message, String bookingEmail) {
		super();
		this.id = id;
		this.message = message;
		this.bookingEmail = bookingEmail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Seat> getSeats() {
		return seats;
	}
	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}
	public String getBookingEmail() {
		return bookingEmail;
	}
	public void setBookingEmail(String bookingEmail) {
		this.bookingEmail = bookingEmail;
	}
	public LocalDateTime getReservedTime() {
		return reservedTime;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bookingEmail == null) ? 0 : bookingEmail.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((reservedTime == null) ? 0 : reservedTime.hashCode());
		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatHold other = (SeatHold) obj;
		if (bookingEmail == null) {
			if (other.bookingEmail != null)
				return false;
		} else if (!bookingEmail.equals(other.bookingEmail))
			return false;
		if (id != other.id)
			return false;
		if (reservedTime == null) {
			if (other.reservedTime != null)
				return false;
		} else if (!reservedTime.equals(other.reservedTime))
			return false;
		if (seats == null) {
			if (other.seats != null)
				return false;
		} else if (!seats.equals(other.seats))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SeatHold [id=" + id + ", message=" + message + ", seats="
				+ seats + ", bookingEmail=" + bookingEmail + ", reservedTime="
				+ reservedTime + "]";
	}

	
	
}
