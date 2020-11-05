package controller;

public class Controller {

	private final DBController dbController;
	private final BotController botController;
	
	public Controller() {
		this.dbController = new DBController(this);
		this.botController = new BotController(this);
	}

	public void start() {

		this.botController.startBot();
	}
	
	
	// Getters and setters
	protected DBController getDbController() {
		return this.dbController;
	}

	protected BotController getBotController() {
		return this.botController;
	}
}
