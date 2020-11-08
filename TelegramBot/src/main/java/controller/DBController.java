package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import dao.DailyBlockDAO;
import dao.UserDAO;
import model.Reservation;
import model.TelegramUser;

public class DBController {

	private final BotController botController;

	private final DailyBlockDAO dailyBlockDAO;
	private final UserDAO userDAO;

	private MongoClient mongoClient;

	public DBController(Controller controller) {

		this.botController = controller.getBotController();

		try {

			String mongoURI = Files.readAllLines(Paths.get("src/main/resources/dbconfig/protected")).get(3);

			MongoClientURI connectionString = new MongoClientURI(mongoURI);
			this.mongoClient = new MongoClient(connectionString);

		} catch (IOException e) {

		}

		this.dailyBlockDAO = new DailyBlockDAO(mongoClient.getDatabase("TelegramBot"));
		this.userDAO = new UserDAO(mongoClient.getDatabase("TelegramBot"));
	}

	// Users
	public boolean isUser(int telegramID) {

		return this.userDAO.isUserPresent(telegramID);
	}

	public boolean addUser(TelegramUser newTelegramUser) {

		try {
			this.userDAO.addUser(newTelegramUser);
			
		} catch (MongoException me) {
			return false;
		}
		
		return true;
	}

	// Reservations
	public boolean addReserveToDB(Reservation reservation) {
		
		ObjectId _userID = this.userDAO.getUserID(reservation.getTelegramUser().getIdTelegram());
		
		try {
			return this.dailyBlockDAO.addReservation(reservation, _userID);
			
		} catch (MongoException me) {
			return false;
		}
	}

	// Open and close
	private void openConnection() {
		this.mongoClient.startSession();
	}

	protected void closeConection() {
		this.mongoClient.close();
	}
}