package model;

public class TelegramUser {

	private final long _id;
	private String name;
	private final int userTelegramID;

	public TelegramUser(long _id, String name, int userTelegramID) {
		this._id = _id;
		this.name = name;
		this.userTelegramID = userTelegramID;
	}
	
	public TelegramUser(int userTelegramID) {
		this._id = 0;
		this.userTelegramID = userTelegramID;
	}
	
	public TelegramUser(String name, int userTelegramID) {
		this(userTelegramID);
		this.name = name;
	}

	// Getters and setters
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdTelegram() {
		return this.userTelegramID;
	}

	public long getId() {
		return this._id;
	}
}
