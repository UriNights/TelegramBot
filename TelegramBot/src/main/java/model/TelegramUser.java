package model;

public class TelegramUser {

	private String name;
	private final long idTelegram;
	
	public TelegramUser(String name, long idTelegram) {
		this.name = name;
		this.idTelegram = idTelegram;
	}
	
	
	// Getters and setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getIdTelegram() {
		return idTelegram;
	}
}
