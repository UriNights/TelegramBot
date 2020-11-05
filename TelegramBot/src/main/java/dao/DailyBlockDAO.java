package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DailyBlockDAO {
	
	private MongoDatabase mongoDatabase;

	public DailyBlockDAO(MongoDatabase mongoDatabase) {

		this.mongoDatabase = mongoDatabase;
	}

	public MongoCollection<Document> getAllDailyBlocks() {

		return this.mongoDatabase.getCollection("DailyBlocks");
	}

	/*public void saveGame(String nick, int points) {

		MongoCollection<Document> topPlayersCollection = this.getTopPlayersCollection();

		List<OrderTopFive> listOfTopFive = new ArrayList<>();
		for (Document topPlayer : topPlayersCollection.find()) {
			OrderTopFive playerToAdd = new OrderTopFive(topPlayer.getString("nick"), topPlayer.getInteger("points", 0));
			listOfTopFive.add(playerToAdd);
		}
		listOfTopFive.add(new OrderTopFive(nick.toUpperCase(), points));

		Collections.sort(listOfTopFive);
		if (5 < listOfTopFive.size()) listOfTopFive.remove(listOfTopFive.size() - 1);
		
		topPlayersCollection.drop();

		for (int i = 0; i < listOfTopFive.size() ; i++) {
			Document topFivePlayer = new Document("_id", new ObjectId());
			topFivePlayer.append("nick", listOfTopFive.get(i).nick).append("points", listOfTopFive.get(i).points);

			topPlayersCollection.insertOne(topFivePlayer);;
		}
	}*/
}
