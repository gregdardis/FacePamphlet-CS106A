/** 
 * This class handles the conversion between data stored in java objects and the SQLite database.
 * Contains methods for querying and updating the data in the database for the "Friends" table. 
 */
public class FriendsDataSource implements DatabaseConstants {
	private Database db;
	
	public FriendsDataSource(Database db) {
		this.db = db;
	}
	
	public void createTable() {
		db.createTable(
				Friends.TABLE_NAME, 
				Friends.COLUMN_FRIEND1_CREATION, 
				Friends.COLUMN_FRIEND2_CREATION, 
				Friends.PRIMARY_KEY_INITIALIZATION, 
				Friends.FOREIGN_KEY_INITIALIZATION_FRIEND1, 
				Friends.FOREIGN_KEY_INITIALIZATION_FRIEND2
		);
	}
	
	/**
	 * Adds a friend to the Friends table.
	 * friend1 column contains the name of the person who added the friend,
	 * and the friend2 column contains the name of the person who was added.
	 */
	public void addFriend(FacePamphletProfile currentProfile, String name) {
		String[] columns = { Friends.COLUMN_FRIEND1, Friends.COLUMN_FRIEND2 };
		String[] values = { Database.quotations(currentProfile.getName()), Database.quotations(name) };
		db.insertRecord(Friends.TABLE_NAME, columns, values);
	}
	
}