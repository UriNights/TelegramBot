package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import model.Reservation;
import model.TelegramUser;
import utils.IOFilter;
import view.Bot;

public class BotController {

	private DBController dbController;
	
	private Bot botTelegram;
	
	private Reservation reservation;

	public BotController(Controller controller) {
		
		this.dbController = controller.getDbController();
		
		this.reservation = null;
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

		return Files.readAllLines(Paths.get("src/main/resources/dbconfig/protected")).get(0);
	}

	
	// Reservation actions
	
	public void newReserve(int userTelegramID) {
		
		this.reservation = new Reservation(userTelegramID);
		this.botTelegram.askForDate();
	}

	public void setDateToReservation(LocalDate dateFormatted) {

		this.reservation.setDate(dateFormatted);
	}

	public void setHourToReservation(String hour) {
		
		this.reservation.setStartTime(LocalTime.of(Integer.parseInt(hour), 0));
	}

	public void setMinutesToReservation(String minutesToAdd) {

		this.reservation.plusMinutes(Long.parseLong(minutesToAdd));
	}
	
	public void setPeriodeToReservation(String string) {

		this.reservation.setPeriodeTime(Integer.parseInt(string));
	}

	public String sendReserveToDB() {

		if (this.dbController.addReserveToDB(this.reservation)) {
			return "Reservation done at " + this.reservation.getDate() + " -> From " + reservation.getStartTime() + " to " + this.reservation.getStartTime().plusMinutes(this.reservation.getPeriodeTime());
		}
		
		return null;
	}

	
	// To DBController
	public boolean isUser(int telegramID) {
		
		return this.dbController.isUser(telegramID);
	}

	public boolean newUser(String textAnswer, int telegramUserID) {
		
		return this.dbController.addUser(new TelegramUser(IOFilter.firstTwoWords(textAnswer), telegramUserID));
	}
}
