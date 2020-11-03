package app;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import model.TelegramBot;

public class MainClass {

	public static void main(String[] args) {
		
		Scanner scanFile = null;
		try {
			scanFile = new Scanner(Paths.get("src/main/resources/dbconfig/protected"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new TelegramBot("YourSecretary", scanFile.nextLine()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
