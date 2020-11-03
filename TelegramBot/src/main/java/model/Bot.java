package model;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	private final String botUserName;
	private final String botToken;

	public Bot(String botUserName, String botToken) {
		this.botUserName = botUserName;
		this.botToken = botToken;
	}

	@Override
	public void onUpdateReceived(Update update) {

		final long chatId = update.getMessage().getChatId();

		String tmessage = "hola";
		switch (update.getMessage().getEntities().get(0).getText()) {
		case "/makereservation":
			tmessage = "reservation";
			break;
		case "/deletereservation":
			tmessage = "delete";
			break;
		default:
			break;
		}

		SendMessage message = new SendMessage().setChatId(chatId).setText(tmessage);
		try {
			execute(message); // Call method to send the message
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Getters and Setters
	@Override
	public String getBotUsername() {
		return this.botUserName;
	}

	@Override
	public String getBotToken() {
		return this.botToken;
	}
}
