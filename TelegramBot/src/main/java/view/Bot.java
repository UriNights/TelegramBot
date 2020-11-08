package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import controller.BotController;
import utils.IOFilter;

public class Bot extends TelegramLongPollingBot {

	private final BotController botController;
	private final String botUserName;
	private final String botToken;

	private long currentChatId;
	
	private Integer lastMessageID;

	private boolean newUserFlag;
	private boolean reservationFlag;
	private boolean deleteFlag;

	public Bot(BotController botController, String botUserName, String botToken) {

		this.botController = botController;
		this.botUserName = botUserName;
		this.botToken = botToken;
	}

	public void onUpdateReceived(Update update) {

		// Normal messages
		if (update.hasMessage()) {
			currentChatId = update.getMessage().getChatId();

			// Exit from any menu
			if (update.getMessage().getText().equals("quit")) {
				this.allFlagsDown();
				return;
			}

			// Is a new user
			if (this.newUserFlag) {
				if (this.botController.newUser(update.getMessage().getText(), update.getMessage().getFrom().getId())) {
					
					this.sendMessage("Good news! You've been registered! Now you can use my commands.");
					this.newUserFlag = false;
				}
			}
			
			// Reservation on the way
			if (this.reservationFlag) {

				LocalDate dateFormatted = IOFilter.dateFormatter(update.getMessage().getText());

				if (dateFormatted == null) {
					this.sendMessage("Invalid format. Please, respect this format: yyyy-mm-dd");
					return;
				}

				// Earliest date is tomorrow
				if (dateFormatted.isBefore(LocalDate.now().plusDays(1))) {
					this.sendMessage("Please, the earliest valid date is from tomorrow.");
					return;
				}
				
				this.reservationFlag = false;
				this.botController.setDateToReservation(dateFormatted);
				this.askForHour();
			}

			// Delete reserve on the way
			if (this.deleteFlag) {
			}

			// Message received
			if (update.getMessage().getEntities() != null) {

				// Check if this user exist
				int userTelegramID = update.getMessage().getFrom().getId();
				if (this.botController.isUser(userTelegramID)) {

					switch (update.getMessage().getEntities().get(0).getText()) {

						case "/makereservation":
							this.botController.newReserve(userTelegramID);
							break;
	
						case "/deletereservation":
							break;
	
						case "/todaystates":
							break;
	
						default:
							break;
					}
					
				} else {  // User is not in our DB
					
					this.askForName();
				}
			}
		}

		// Callback query
		if (update.hasCallbackQuery()) {
			
			String[] splittedAnswer = update.getCallbackQuery().getData().split(";");

			switch (splittedAnswer[0]) {

				case "hour":
					this.deleteLastMessage();
					this.botController.setHourToReservation(splittedAnswer[1]);
					this.askForMinutes(splittedAnswer[1]);
					break;
					
				case "minutes":
					this.deleteLastMessage();
					this.botController.setMinutesToReservation(splittedAnswer[1]);
					this.askForPeriode(splittedAnswer[1]);
					break;
				
				case "periode":
					this.deleteLastMessage();
					this.botController.setPeriodeToReservation(splittedAnswer[1]);
					
					String reservationDone = this.botController.sendReserveToDB();
					
					if (reservationDone != null) {
						this.sendMessage(reservationDone);
					} else {
						this.sendMessage("Something wrong with your reservation. Please, try again.");
					}
					
					break;
					
				default:
					break;
			}
		}
	}

	private void allFlagsDown() {
		this.newUserFlag = false;
		this.reservationFlag = false;
		this.deleteFlag = false;
	}
	
	private void askForName() {
		this.sendMessage("Hey, you are a new user! :) Please, give us a name for you or 'quit'. (Be careful, at most we will take your next two words)");
		this.newUserFlag = true;
	}

	// Making reservation
	public void askForDate() {

		this.sendMessage("Let's make a reservation! Please, introduce a date: yyyy-mm-dd (or 'quit')");
		this.reservationFlag = true;
	}

	private void askForHour() {

		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
		List<InlineKeyboardButton> buttonsList;
		String buttonText;

		for (int i = 2; i < 4; i++) {	// Only from 9.00 to 20.00

			buttonsList = new ArrayList<>();
			for (int j = 0; j < 6; j++) {

				buttonText = String.valueOf((i * 6) + j - 3);
				buttonsList.add(new InlineKeyboardButton(buttonText + " h").setCallbackData("hour;" + buttonText));
			}

			keyboard.add(buttonsList);
		}

		SendMessage telegramMessage = new SendMessage().setChatId(currentChatId).setText("Select the hour to start:")
				.setReplyMarkup(new InlineKeyboardMarkup(keyboard));

		try {
			this.lastMessageID = execute(telegramMessage).getMessageId();
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void askForMinutes(String hour) {

		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
		List<InlineKeyboardButton> buttonsList = new ArrayList<>();

		for (int i = 0; i < 4; i++) {

			buttonsList.add(new InlineKeyboardButton(hour + ":" + ((i == 0) ? "00" : (i * 15)))
					.setCallbackData("minutes;" + (i * 15)));
		}

		keyboard.add(buttonsList);

		SendMessage telegramMessage = new SendMessage().setChatId(currentChatId).setText("Select a schedule:")
				.setReplyMarkup(new InlineKeyboardMarkup(keyboard));

		try {
			this.lastMessageID = execute(telegramMessage).getMessageId();
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void askForPeriode(String string) {

		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
		List<InlineKeyboardButton> buttonsList = new ArrayList<>();

		for (int i = 1; i <= 8; i++) {

			buttonsList.add(new InlineKeyboardButton((i * 15) + "'")
					.setCallbackData("periode;" + (i * 15)));
		}

		keyboard.add(buttonsList);

		SendMessage telegramMessage = new SendMessage().setChatId(currentChatId).setText("Select a periode for your reservation:")
				.setReplyMarkup(new InlineKeyboardMarkup(keyboard));

		try {
			this.lastMessageID = execute(telegramMessage).getMessageId();
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Send basic message and delete
	private void sendMessage(String message) {

		SendMessage telegramMessage = new SendMessage().setChatId(currentChatId).setText(message);

		try {
			execute(telegramMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteLastMessage() {
		
		try {
			execute(new DeleteMessage(this.currentChatId, this.lastMessageID));
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
