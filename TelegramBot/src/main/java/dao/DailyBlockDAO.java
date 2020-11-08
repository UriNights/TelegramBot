package dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import model.DailyBlock;
import model.Reservation;

public class DailyBlockDAO {

	private MongoDatabase mongoDatabase;

	public DailyBlockDAO(MongoDatabase mongoDatabase) {

		this.mongoDatabase = mongoDatabase;
	}

	public MongoCollection<Document> getDailyBlocksCollection() {

		return this.mongoDatabase.getCollection("DailyBlocks");
	}

	public boolean addReservation(Reservation reservation, ObjectId _userID) throws MongoException {

		MongoCollection<Document> dailyBlocksCollection = this.getDailyBlocksCollection();
		Document dateBlock = dailyBlocksCollection.find(Filters.eq("date", reservation.getDate().toString())).first();

		if (dateBlock == null) { // There is no reservation before for this date

			dateBlock = new Document("_id", new ObjectId());

			List<Document> reservationsList = new ArrayList<>();
			reservationsList.add(new Document("_id", new ObjectId()).append("userID", _userID)
					.append("date", reservation.getDate().toString())
					.append("startTime", reservation.getStartTime().toString())
					.append("periodeTime", reservation.getPeriodeTime()));

			dateBlock.append("date", reservation.getDate().toString()).append("reservations", reservationsList)
					.append("reservationsCount", 1);
			dailyBlocksCollection.insertOne(dateBlock);

		} else {

			// If there are maximum reservations per day return false
			if (DailyBlock.getMaxReservesPerDay() <= dateBlock.getInteger("reservationsCount")) {
				return false;
			}

			if (this.isUserInThisDay(dateBlock, _userID)) { // Check if this user has some reservation this day
				return false;
			}

			Document reservationDoc = new Document("_id", new ObjectId()).append("userID", _userID)
					.append("date", reservation.getDate().toString())
					.append("startTime", reservation.getStartTime().toString())
					.append("periodeTime", reservation.getPeriodeTime());

			dailyBlocksCollection.updateOne(Filters.eq("_id", dateBlock.getObjectId("_id")),
					Updates.addToSet("reservations", reservationDoc));
			
			dailyBlocksCollection.updateOne(Filters.eq("_id", dateBlock.getObjectId("_id")), Updates.inc("reservationsCount", 1));
		}

		return true;
	}

	private boolean isUserInThisDay(Document dateBlock, ObjectId _userID) {

		return dateBlock.getList("reservations", Document.class).stream()
				.map(doc -> doc.getObjectId("userID"))
				.anyMatch(userID -> userID.equals(_userID));
	}
}