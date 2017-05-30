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
		db.close();
	}
	
}
