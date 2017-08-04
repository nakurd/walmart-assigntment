package com.walmart.model;

public class Seat {
	int row;
	int number;
	int score;
	SeatStatus status;
	RowDirection direction;
	
	public Seat(int row, int number, int score,RowDirection direction) {
		super();
		this.row = row;
		this.number = number;
		this.score = score;
		this.direction = direction;
		this.status = SeatStatus.AVAILABLE;
	}
	
	public RowDirection getDirection() {
		return direction;
	}

	public void setDirection(RowDirection direction) {
		this.direction = direction;
	}

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public SeatStatus getStatus() {
		return status;
	}
	public void setStatus(SeatStatus status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + row;
		result = prime * result + score;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Seat other = (Seat) obj;
		if (number != other.number)
			return false;
		if (row != other.row)
			return false;
		if (score != other.score)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seat [row=" + row + ", number=" + number + ", score=" + score
				+ ", status=" + status + ", direction=" + direction + "]";
	}
	

	
	
}
