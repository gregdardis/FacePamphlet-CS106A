
public interface DatabaseConstants {
	
	public static class Profile {
		public static final String TABLE_NAME = "Profile";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_STATUS = "status";
		public static final String COLUMN_IMAGE = "image";
		public static final String COLUMN_NAME_CREATION = COLUMN_NAME + " TEXT PRIMARY KEY";
		public static final String COLUMN_STATUS_CREATION = COLUMN_STATUS + " TEXT";
		public static final String COLUMN_IMAGE_CREATION = COLUMN_IMAGE + " BLOB";
	}
}
