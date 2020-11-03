package controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import model.Bot;

public class BotController {

	private Bot botTelegram;

	public void start() {

		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			this.botTelegram = new Bot("SecretarySchedule", this.getProtectedToken());
			botsApi.registerBot(this.botTelegram);
			
		} catch (TelegramApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error taking token from 'protected' file.");
		}
	}

	private String getProtectedToken() throws IOException {

		Scanner scanFile = new Scanner(Paths.get("src/main/resources/dbconfig/protected"));
		return scanFile.nextLine();
	}
}
