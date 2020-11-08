package dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import model.TelegramUser;

public class UserDAO {

	private MongoDatabase mongoDatabase;

	public UserDAO(MongoDatabase mongoDatabase) {

		this.mongoDatabase = mongoDatabase;
	}

	public MongoCollection<Document> getUsersCollection() {

		return this.mongoDatabase.getCollection("Users");
	}

	public boolean isUserPresent(int telegramID) {

		MongoCollection<Document> usersCollection = this.getUsersCollection();
		System.out.println(usersCollection);

		if (usersCollection.find(Filters.eq("telegramID", telegramID)).first() == null) {

			return false;
		}

		return true;
	}

	public void addUser(TelegramUser newTelegramUser) throws MongoException {

		MongoCollection<Document> usersCollection = this.getUsersCollection();
		System.out.println(usersCollection);

		Document newUser = new Document("_id", new ObjectId());

		newUser.append("name", newTelegramUser.getName()).append("telegramID", newTelegramUser.getIdTelegram());
		usersCollection.insertOne(newUser);
	}
	
	public ObjectId getUserID(int telegramUserID) throws MongoException {

		MongoCollection<Document> usersCollection = this.getUsersCollection();
		System.out.println(usersCollection);

		Document user = usersCollection.find(Filters.eq("telegramID", telegramUserID)).first();
		
		return user.getObjectId("_id");
	}
}
