package com.walmart.service

import java.util.HashSet;

import com.walmart.model.RowDirection;
import com.walmart.model.Seat
import com.walmart.model.SeatHold
import com.walmart.model.SeatStatus;
import com.walmart.model.Venue
import com.walmart.servcie.TicketServiceWalmartImpl

import static com.walmart.util.TicketServiceUtil.*
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class TicketServiceWalmartImplSpec extends Specification{
	 
	private TicketServiceWalmartImpl objectUnderTest;
	
	public void setup(){
		objectUnderTest = new TicketServiceWalmartImpl();
	}
	
	@Unroll
	public void "test that venue is initalized correctly"(){
		expect:
			objectUnderTest != null
			objectUnderTest.pendingReservations.size() == 0
			objectUnderTest.seats.size() == objectUnderTest.walmart.getNumberOfRows() * objectUnderTest.walmart.getSeatsPerRow()
			objectUnderTest.walmart.getMiddleSeat() == objectUnderTest.walmart.getSeatsPerRow()/2
			objectUnderTest.numSeatsAvailable() == objectUnderTest.seats.size()
	}


	//Test score for 10 * 10 venue
	@Unroll("Row Num: #rowNum Seat Num: #seatNum MiddleSeat Num: #middleSeatNum Numer Of Seats Per Row: #numberOfSeatsPerRow Score: #score")
	public void "test getScore method"(){
		expect:
			objectUnderTest.getScore(rowNum,seatNum,middleSeatNum,numberOfSeatsPerRow) == score
		where:
			rowNum << [0,10,10,10,10,10,10,10,10,10,10,9,9,9,9,9,9,9,9,9,9,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,7,7,7,7,7,6,6,6,6,6,6,6,6,6,6,
					   5,5,5,5,5,5,5,5,5,5,4,4,4,4,4,4,4,4,4,4,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1]
			seatNum << [0,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,
					   1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10]
			middleSeatNum << [0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,
							  5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5]
			numberOfSeatsPerRow << [0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10
									,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
									10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
			score << [0,21,22,23,24,25,25,24,23,22,21,19,20,21,22,23,23,22,21,20,19,17,18,19,20,21,21,20,19,18,17,15,16,17,18,19,19,18,17,16,15,
					  13,14,15,16,17,17,16,15,14,13,11,12,13,14,15,15,14,13,12,11,9,10,11,12,13,13,12,11,10,9,7,8,9,10,11,11,10,9,8,7,
					  5,6,7,8,9,9,8,7,6,5,3,4,5,6,7,7,6,5,4,3]
			
	}
	
	public void "test numSeatsAvailable method with no all seats been available"(){
		expect:
			objectUnderTest.numSeatsAvailable() == 100
	}
	
	public void "test numSeatsAvailable method with 10 seats on pending status and no booked"(){
		setup:
			objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			objectUnderTest.findAndHoldSeats(4,"aa2@aa.com")
			objectUnderTest.findAndHoldSeats(2,"aa3@aa.com")
		expect:
			objectUnderTest.numSeatsAvailable() == 90
	}
	
	public void "test numSeatsAvailable method with 10 seats booked"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			SeatHold s2 = objectUnderTest.findAndHoldSeats(4,"aa2@aa.com")
			SeatHold s3 = objectUnderTest.findAndHoldSeats(2,"aa3@aa.com")
			objectUnderTest.reserveSeats(s1.getId(),s1.getBookingEmail())
			objectUnderTest.reserveSeats(s2.getId(),s2.getBookingEmail())
			objectUnderTest.reserveSeats(s3.getId(),s3.getBookingEmail())
		expect:
			objectUnderTest.numSeatsAvailable() == 90
	}
	
	public void "test numSeatsAvailable method with 10 seats on pending status and 10 seats booked"(){
		setup:
			objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			objectUnderTest.findAndHoldSeats(4,"aa2@aa.com")
			objectUnderTest.findAndHoldSeats(2,"aa3@aa.com")
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			SeatHold s2 = objectUnderTest.findAndHoldSeats(4,"aa2@aa.com")
			SeatHold s3 = objectUnderTest.findAndHoldSeats(2,"aa3@aa.com")
			objectUnderTest.reserveSeats(s1.getId(),s1.getBookingEmail())
			objectUnderTest.reserveSeats(s2.getId(),s2.getBookingEmail())
			objectUnderTest.reserveSeats(s3.getId(),s3.getBookingEmail())
		expect:
			objectUnderTest.numSeatsAvailable() == 80
	}
	
	public void "test numSeatsAvailable method with 10 seats on pending status and 10 seats expired"(){
		setup:
			objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			objectUnderTest.findAndHoldSeats(4,"aa2@aa.com")
			objectUnderTest.findAndHoldSeats(2,"aa3@aa.com")
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			SeatHold s2 = objectUnderTest.findAndHoldSeats(4,"aa2@aa.com")
			SeatHold s3 = objectUnderTest.findAndHoldSeats(2,"aa3@aa.com")
			objectUnderTest.reserveSeats(s1.getId(),s1.getBookingEmail())
			objectUnderTest.reserveSeats(s2.getId(),s2.getBookingEmail())
			objectUnderTest.reserveSeats(s3.getId(),s3.getBookingEmail())
			Thread.sleep(3500);
		expect:
			objectUnderTest.numSeatsAvailable() == 90
	}
	
	public void "test findAndHoldSeats where request number is > than venue holdLimit"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(5,"aa1@aa.com")
		expect:
			s1.getId() == 0
			s1.getBookingEmail() == "aa1@aa.com"
			s1.getSeats() == null
			s1.getReservedTime() != null
			s1.getMessage() == SEAT_LIMIT
	}
	
	public void "test findAndHoldSeats where request can be handled"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com")
			Set<Seat> seats = new HashSet<>();
			seats.add(createSeat( row: 1, number:5,score:25,status:SeatStatus.PENDING,direction:RowDirection.LEFT));
			seats.add(createSeat( row: 1, number:4,score:24,status:SeatStatus.PENDING,direction:RowDirection.LEFT));
			seats.add(createSeat( row: 1, number:3,score:23,status:SeatStatus.PENDING,direction:RowDirection.LEFT));
			seats.add(createSeat( row: 1, number:2,score:22,status:SeatStatus.PENDING,direction:RowDirection.LEFT));
		expect:
			s1.getId() > 0
			s1.getBookingEmail() == "aa1@aa.com"
			s1.getSeats().size() == 4
			s1.getSeats().containsAll(seats)
			s1.getReservedTime() != null
			s1.getMessage() == SEATS_ARE_PENDING
	}
	
	public void "test findAndHoldSeats where request cannot be handled"(){
		setup:
			for(int i = 0; i < 20; i++)
			objectUnderTest.findAndHoldSeats(4,"aa@aa.com" +i);
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa3@aa.com")
		expect:
			s1.getId() == 0
			s1.getBookingEmail() == "aa3@aa.com"
			s1.getSeats() == null
			s1.getReservedTime() != null
			s1.getMessage() == NO_SEATS_ON_SAME_ROW
			objectUnderTest.numSeatsAvailable() == 20
	}
	
	public void "test reserveSeats with invalid Id"(){
		setup:
			objectUnderTest.findAndHoldSeats(4,"aa@aa.com");
			String code = objectUnderTest.reserveSeats(0,"");
		expect:
			code == INVALID_ID_OR_EXPIRED
	}
	
	public void "test reserveSeats with invalid email"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			String code = objectUnderTest.reserveSeats(s1.getId(),"");
		expect:
			code == INVALID_ID_OR_EXPIRED
	}
	
	public void "test reserveSeats with all reservation has timed out"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			SeatHold s2 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			SeatHold s3 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			SeatHold s4 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			Thread.sleep(3500);
			String code = objectUnderTest.reserveSeats(s1.getId(),"");
		expect:
			code == INVALID_ID_OR_EXPIRED
	}
	
	public void "test reserveSeats with resrvation been successfull"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			String code = objectUnderTest.reserveSeats(s1.getId(),s1.getBookingEmail());
		expect:
			code != null
			objectUnderTest.numSeatsAvailable() == 96
	}
	
	public void "test reserveSeats with some resrvation been successfull and some has timed out"(){
		setup:
			SeatHold s1 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			String code1 = objectUnderTest.reserveSeats(s1.getId(),s1.getBookingEmail());
			SeatHold s2 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			String code2 = objectUnderTest.reserveSeats(s2.getId(),s2.getBookingEmail());
			SeatHold s3 = objectUnderTest.findAndHoldSeats(4,"aa1@aa.com");
			String code3 = objectUnderTest.reserveSeats(s3.getId(),s3.getBookingEmail());
			SeatHold s4 = objectUnderTest.findAndHoldSeats(2,"aa1@aa.com");
			SeatHold s5 = objectUnderTest.findAndHoldSeats(2,"aa1@aa.com");
			SeatHold s6 = objectUnderTest.findAndHoldSeats(2,"aa1@aa.com");
			Thread.sleep(3500);
			String code4 = objectUnderTest.reserveSeats(s4.getId(),s4.getBookingEmail());
			String code5 = objectUnderTest.reserveSeats(s5.getId(),s5.getBookingEmail());
			String code6 = objectUnderTest.reserveSeats(s5.getId(),s6.getBookingEmail());
		expect:
			code1 != null
			code1.length() == 10
			code2 != null
			code3.length() == 10
			code3 != null
			code3.length() == 10
			code4 == INVALID_ID_OR_EXPIRED
			code5 == INVALID_ID_OR_EXPIRED
			code6 == INVALID_ID_OR_EXPIRED
			objectUnderTest.numSeatsAvailable() == 88
	}

		
	private Seat createSeat(Map seatInfo){
		Seat seat = new Seat(seatInfo['row'],seatInfo['number'],seatInfo['score'],seatInfo['direction']);
		seat.status = seatInfo['status'];
		return seat;
	}
	
}
