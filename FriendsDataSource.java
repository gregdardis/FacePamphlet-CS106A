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
	
}