package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import model.DailyBlock;

public class DBController {

	private final Controller controller;
	private final BotController botController;
	
	private ArrayList<DailyBlock> daysList;
	private MongoClient mongoClient;

	public DBController(Controller controller) {

		this.controller = controller;
		this.botController = controller.getBotController();
		
		
		try {
			
			String mongoURI = Files.readAllLines(Paths.get("file.txt")).get(4);
			
			MongoClientURI connectionString = new MongoClientURI(mongoURI);
			this.mongoClient = new MongoClient(connectionString);

		} catch (IOException e) {

		}
	}

	public void addReserve(LocalDateTime dateAndTimeFormatted) {
		// TODO Auto-generated method stub
		
	}	
	
	
	// Open and close
	private void openConnection() {
		this.mongoClient.startSession();
	}
		
	protected void closeConection() {
		this.mongoClient.close();
	}
}