package com.walmart.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.Properties;
import java.util.Random;
import java.util.function.Predicate;

import com.walmart.model.RowDirection;
import com.walmart.model.Seat;

public class TicketServiceUtil {
	
	public static final String INVALID_ID_OR_EXPIRED= "Hold Id does not exists or resvation has timed out";
	public static final String SEAT_LIMIT= "You are exceeding the reservation seat limit";
	public static final String NO_SEATS_ON_SAME_ROW= "Number of seats requests can't be reserved on a row";
	public static final String SEATS_ARE_PENDING= "Seats are hold for five minutes";
	
	private static SecureRandom random = new SecureRandom();
	private static Random rand = new Random();
	
	public static Predicate<Seat> seatCanFulfillReservation(int numberOfSeats, int setsPerRow) {
	    return seat -> seat.getDirection() == RowDirection.RIGHT ? seat.getNumber() + numberOfSeats <= setsPerRow +1 :
	    		seat.getNumber() - numberOfSeats >= 0;
	}
	
	public static String nextConfirmationCode() {
        return new BigInteger(130, random).toString(10).substring(0, 10);
    }
	
	public static int nextReservationId() {
		return rand.nextInt(1000) + 1;
    }
	
	public static final Comparator<Seat> SeatComparator = 
			Comparator.comparingInt(Seat::getScore)
	          		  .thenComparingInt(Seat::getRow).reversed();
	
	public static Properties loadVenueProperties(){
		Properties prop = new Properties();
		InputStream input = null;

		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("venue.properties");
			prop.load(is);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return prop;
	}
	
	
}
