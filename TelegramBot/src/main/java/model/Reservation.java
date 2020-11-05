package model;

import java.time.LocalTime;

public class Reservation {

	private TelegramUser telegramUser;
	private LocalTime startTime;
	private LocalTime periodeTime;
	
	public Reservation(TelegramUser telegramUser, LocalTime startTime, LocalTime periodeTime) {
		
		this.telegramUser = telegramUser;
		this.startTime = startTime;
		this.periodeTime = periodeTime;
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

	public LocalTime getPeriodeTime() {
		return periodeTime;
	}

	public void setPeriodeTime(LocalTime periodeTime) {
		this.periodeTime = periodeTime;
	}
}
