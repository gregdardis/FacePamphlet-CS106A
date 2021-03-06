import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.GImage;

/** 
 * This class handles the conversion between data stored in java objects and the SQLite database.
 * Contains methods for querying and updating the data in the database for the "Profiles" table. 
 */
public class ProfilesDataSource implements DatabaseConstants, FacePamphletConstants {
	private Database db;
	private FriendsDataSource friendsDataSource;
	
	public ProfilesDataSource(Database db, FriendsDataSource friendsDataSource) {
		this.db = db;
		this.friendsDataSource = friendsDataSource;
	}
	
	/** Creates the Profiles table */
	public void createTable() {
		db.createTable(
				Profiles.TABLE_NAME,
				Profiles.COLUMN_NAME_CREATION,
				Profiles.COLUMN_STATUS_CREATION,
				Profiles.COLUMN_IMAGE_CREATION
		);
	}
	
	/**
	 * Adds a profile to the Profiles table.
	 * This method only adds the name to the table because that is all that
	 * exists when the Profile is created and needs to be added to the table.
	 */
	public void addProfile(FacePamphletProfile profile) {
		String[] columns = { Profiles.COLUMN_NAME };
		String[] values = { Database.quotations(profile.getName()) };
		db.insertRecord(Profiles.TABLE_NAME, columns, values);
	}
	
	/** Updates the Profiles table with a profile's status. */
	public void changeStatus(FacePamphletProfile profile) {
		String[] columns = { Profiles.COLUMN_STATUS };
		String[] values = { Database.quotations(profile.getStatus()) };
		String whereCondition = Profiles.COLUMN_NAME + " = " + Database.quotations(profile.getName());
		db.updateRecord(Profiles.TABLE_NAME, columns, values, whereCondition);
	}
	
	/** Updates the Profiles table with the profile's picture. */
	public void updatePicture(FacePamphletProfile profile, String filename) {
		String whereCondition = Profiles.COLUMN_NAME + " = " + Database.quotations(profile.getName());
		db.updatePicture(Profiles.TABLE_NAME, Profiles.COLUMN_IMAGE, filename, whereCondition);
	}
	
	/** Deletes a whole profile from the Profiles table by deleting the record. */
	public void deleteProfile(FacePamphletProfile profile) {
		String whereCondition = Profiles.COLUMN_NAME + " = " + Database.quotations(profile.getName());
		db.deleteRecord(Profiles.TABLE_NAME, whereCondition);
	}
	
	/** 
	 * Gets profiles from Profiles table in SQLite database and adds them to the
	 * a "profiles" HashMap to be used upon starting the application as the database.
	 */
	public Map<String, FacePamphletProfile> getAllProfiles() {
		String[] projection = { "*" };
		ResultSet rs = db.executeSelectQuery(projection, Profiles.TABLE_NAME);
		Map<String, FacePamphletProfile> profiles = new HashMap<>();
		try {
			while (rs.next()) {
				FacePamphletProfile profile = makeProfile(rs);
				profiles.put(profile.getName(), profile);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return profiles;
	}
	
	/** 
	 * Called upon starting the app, uses a ResultSet to make a FacePamphletProfile
	 * and return it to be used in the database HashMap.
	 */
	private FacePamphletProfile makeProfile(ResultSet rs) {
		FacePamphletProfile profile = null;
		String name;
		String status;
		try {
			name = rs.getString(Profiles.COLUMN_NAME);
			status = rs.getString(Profiles.COLUMN_STATUS);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		db.readPicture(Profiles.TABLE_NAME, Profiles.COLUMN_IMAGE, Profiles.IMAGE_DIRECTORY, name + ".jpg", Profiles.COLUMN_NAME + " = " + Database.quotations(name));
		String filepath = Profiles.IMAGE_DIRECTORY + name + ".jpg";
		profile = new FacePamphletProfile(name, status, filepath);
		profile.setFriendList(friendsDataSource.getFriendsList(profile));
		return profile;
	}
}
