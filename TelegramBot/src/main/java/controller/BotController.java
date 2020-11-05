package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import utils.IOFilter;
import view.Bot;

public class BotController {

	private Controller controller;
	private DBController dbController;
	
	private Bot botTelegram;

	public BotController(Controller controller) {
		
		this.controller = controller;
		this.dbController = controller.getDbController();
	}
	
	public void startBot() {

		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			this.botTelegram = new Bot(this, "SecretarySchedule", this.getProtectedToken());
			botsApi.registerBot(this.botTelegram);

		} catch (TelegramApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error taking token from 'protected' file.");
			System.exit(1);
		}
	}

	private String getProtectedToken() throws IOException {

		return Files.readAllLines(Paths.get("src/main/resources/dbconfig/protected")).get(1);
	}

	
	// Commands
	
	public boolean makeReserve(String dateAndTime) {

		LocalDateTime dateAndTimeFormatted = IOFilter.dateAndTimeFormatter(dateAndTime);
		
		if (dateAndTimeFormatted == null) {
			this.botTelegram.sendMessage("Invalid format. Please, respect this format: yyyy-mm-dd hh:mm:ss");
			return false;
		}
		
		this.dbController.addReserve(dateAndTimeFormatted);
		
		this.dbController.closeConection();
		
		return true;
	}

	public void deleteReserve() {

	}
}
