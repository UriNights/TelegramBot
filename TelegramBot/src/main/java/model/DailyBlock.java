package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class DailyBlock {
	
	private static int maxReservesPerDay = 4;
	
	private final LocalDate date;
	private final ArrayList<Reservation> reservationList;
	
	public DailyBlock(LocalDate date) {
	
		this.date = date;
		this.reservationList = new ArrayList<>();
	}

	public boolean addReservation(Reservation reservation) {
		
		if (this.reservationList.size() < DailyBlock.maxReservesPerDay) {
			this.reservationList.add(reservation);
			return true;
		}
		
		return false;
	}
	
	
	// Getters and setters
	public static int getMaxReservesPerDay() {
		return DailyBlock.maxReservesPerDay;
	}

	public static void setMaxReservesPerDay(int maxReservesPerDay) {
		DailyBlock.maxReservesPerDay = maxReservesPerDay;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public ArrayList<Reservation> getReservationList() {
		return this.reservationList;
	}
}
