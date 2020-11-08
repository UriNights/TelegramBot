package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

	private TelegramUser telegramUser;
	private LocalDate date;
	private LocalTime startTime;
	private int periodeTime; // This is in minutes
	
	public Reservation(int userTelegramID) {
		
		this.telegramUser = new TelegramUser(userTelegramID);
	}

	
	// Getters and Setters
	public TelegramUser getTelegramUser() {
		return this.telegramUser;
	}

	public void setUser(TelegramUser telegramUser) {
		this.telegramUser = telegramUser;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public void plusMinutes(long minutesToAdd) {
		this.startTime = this.startTime.plusMinutes(minutesToAdd);
	}

	public int getPeriodeTime() {
		return periodeTime;
	}

	public void setPeriodeTime(int periodeTime) {
		this.periodeTime = periodeTime;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
