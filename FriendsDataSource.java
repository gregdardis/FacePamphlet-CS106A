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
	
	public void createTable() {
		db.createTableWithDeleteCascade(
				Friends.TABLE_NAME, 
				Friends.COLUMN_FRIEND1_CREATION, 
				Friends.COLUMN_FRIEND2_CREATION, 
				Friends.PRIMARY_KEY_INITIALIZATION,
				"FOREIGN KEY (friend1) REFERENCES Profiles(name) ON DELETE CASCADE,\nFOREIGN KEY (friend2) REFERENCES Profiles(name)"
//				"CONSTRAINT fk_Profiles \nFOREIGN KEY (friend1, friend2) REFERENCES Profiles(name, name)"
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