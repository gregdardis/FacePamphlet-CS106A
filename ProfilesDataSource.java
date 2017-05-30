import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.GImage;

/** 
 * This class handles the conversion between data stored in java objects and the SQLite database.
 * Contains methods for querying and updating the data in the database for the "Profiles" table. 
 */
public class ProfilesDataSource implements DatabaseConstants {
	private Database db;
	
	public ProfilesDataSource(Database db) {
		this.db = db;
	}
	
	public void createTable() {
		db.createTable(
				Profiles.TABLE_NAME,
				Profiles.COLUMN_NAME_CREATION,
				Profiles.COLUMN_STATUS_CREATION,
				Profiles.COLUMN_IMAGE_CREATION
		);
	}
	
	public void addProfile(FacePamphletProfile profile) {
		String[] columns = { Profiles.COLUMN_NAME };
		String[] values = { Database.quotations(profile.getName()) };
		db.insertRecord(Profiles.TABLE_NAME, columns, values);
	}
	
	public void changeStatus(FacePamphletProfile profile) {
		String[] columns = { Profiles.COLUMN_STATUS };
		String[] values = { Database.quotations(profile.getStatus()) };
		String whereCondition = Profiles.COLUMN_NAME + " = " + Database.quotations(profile.getName());
		db.updateRecord(Profiles.TABLE_NAME, columns, values, whereCondition);
	}
	
	public void deleteProfile(FacePamphletProfile profile) {
		String whereCondition = Profiles.COLUMN_NAME + " = " + Database.quotations(profile.getName());
		db.deleteRecord(Profiles.TABLE_NAME, whereCondition);
		db.close();
	}
	
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
	
	private FacePamphletProfile makeProfile(ResultSet rs) {
		FacePamphletProfile profile = null;
		try {
			String name = rs.getString(Profiles.COLUMN_NAME);
			String status = rs.getString(Profiles.COLUMN_STATUS);
			GImage image = null;
			profile = new FacePamphletProfile(name, status, image);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return profile;
	}
	
}
