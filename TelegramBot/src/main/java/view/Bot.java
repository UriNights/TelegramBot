package view;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import controller.BotController;

public class Bot extends TelegramLongPollingBot {

	private final BotController botController;
	private final String botUserName;
	private final String botToken;

	private long currentChatId;
	private boolean reservationFlag;
	private boolean deleteFlag;

	public Bot(BotController botController, String botUserName, String botToken) {
		this.botController = botController;
		this.botUserName = botUserName;
		this.botToken = botToken;
	}

	public void onUpdateReceived(Update update) {

		currentChatId = update.getMessage().getChatId();
		
		if (update.getMessage().getText().equals("quit")) {
			this.allFlagsDown();
			return;
		}

		if (this.reservationFlag) {
			this.reservationFlag = !this.botController.makeReserve(update.getMessage().getText());
			return;
		}

		if (this.deleteFlag) {
		}

		if (update.getMessage().getEntities() != null) {
			
			switch (update.getMessage().getEntities().get(0).getText()) {

			case "/makereservation":
				this.askForDateAndTime();
				break;

			case "/deletereservation":
				break;

			case "/todaystates":
				break;
				
			default:
				break;
			}
		}
	}

	private void allFlagsDown() {
		this.reservationFlag = false;
		this.deleteFlag = false;
	}
	
	
	// Answers
	private void askForDateAndTime() {

		this.reservationFlag = true;
		this.sendMessage(
				"Let's make a reservation! Please, introduce a date and time with this format: yyyy-mm-dd hh:mm:ss (or write quit)");
	}

	
	// Send message
	public void sendMessage(String message) { // **** Què passa si el missatge dóna error i no s'envia?

		SendMessage telegramMessage = new SendMessage().setChatId(currentChatId).setText(message);

		try {
			execute(telegramMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	

	// Getters and Setters
	public String getBotUsername() {
		return this.botUserName;
	}

	@Override
	public String getBotToken() {
		return this.botToken;
	}
}
