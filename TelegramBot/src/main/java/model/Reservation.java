package model;

import java.time.LocalTime;

public class Reservation {

	private String user;
	private LocalTime startTime;
	private LocalTime periodeTime;
	
	public Reservation(String user, LocalTime startTime, LocalTime periodeTime) {
		
		this.user = user;
		this.startTime = startTime;
		this.periodeTime = periodeTime;
	}

	
	// Getters and Setters
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
