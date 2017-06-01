import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** 
 * This class handles the conversion between data stored in java objects and the SQLite database.
 * Contains methods for querying and updating the data in the database for the "Friends" table. 
 */
public class FriendsDataSource implements DatabaseConstants {
	private Database db;
	
	public FriendsDataSource(Database db) {
		this.db = db;
	}
	
	/** Creates the Friends table. */
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
	public void addFriendToDatabase(FacePamphletProfile currentProfile, String name) {
		String[] columns = { Friends.COLUMN_FRIEND1, Friends.COLUMN_FRIEND2 };
		String[] values = { Database.quotations(currentProfile.getName()), Database.quotations(name) };
		db.insertRecord(Friends.TABLE_NAME, columns, values);
	}
	
	/** Called upon starting the application. 
	 * Gets the friendsList for a profile and returns it. 
	 */
	public ArrayList<String> getFriendsList(FacePamphletProfile profile) {
		ArrayList<String> friendsList = new ArrayList<String>();
		
		String sql = "SELECT " + Friends.COLUMN_FRIEND2 + " AS f FROM " +
				Friends.TABLE_NAME + " WHERE " + Friends.COLUMN_FRIEND1 + " = " 
				+ Database.quotations(profile.getName()) + " UNION " + 
				" SELECT " + Friends.COLUMN_FRIEND1 + " AS f FROM " +
				Friends.TABLE_NAME + " WHERE " + Friends.COLUMN_FRIEND2 + " = " 
				+ Database.quotations(profile.getName());
		ResultSet rs = db.executeSQLForResult(sql);
		
		try {
			while (rs.next()) {
				String friendName = rs.getString("f");
				friendsList.add(friendName);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return friendsList;
	}
	
}