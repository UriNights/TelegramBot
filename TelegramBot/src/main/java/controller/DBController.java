package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DBController {

	private MongoClient mongoClient;

	public DBController() {

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