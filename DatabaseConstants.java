
public interface DatabaseConstants {
	
	public static class Profiles {
		public static final String TABLE_NAME = "Profiles";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_STATUS = "status";
		public static final String COLUMN_IMAGE = "image";
		public static final String COLUMN_NAME_CREATION = COLUMN_NAME + " TEXT PRIMARY KEY";
		public static final String COLUMN_STATUS_CREATION = COLUMN_STATUS + " TEXT";
		public static final String COLUMN_IMAGE_CREATION = COLUMN_IMAGE + " BLOB";
		public static final String IMAGE_DIRECTORY = "savedImages/";
	}
	
	public static class Friends {
		public static final String TABLE_NAME = "Friends";
		public static final String COLUMN_FRIEND1 = "friend1";
		public static final String COLUMN_FRIEND2 = "friend2";
		public static final String COLUMN_FRIEND1_CREATION = COLUMN_FRIEND1 + " TEXT";
		public static final String COLUMN_FRIEND2_CREATION = COLUMN_FRIEND2 + " TEXT";
		public static final String PRIMARY_KEY_INITIALIZATION = "PRIMARY KEY (" + COLUMN_FRIEND1 + ", " + COLUMN_FRIEND2 + ")";
		public static final String FOREIGN_KEY_INITIALIZATION_FRIEND1 = "FOREIGN KEY (" + COLUMN_FRIEND1 + ") REFERENCES " + Profiles.TABLE_NAME + "(" + Profiles.COLUMN_NAME + ")";
		public static final String FOREIGN_KEY_INITIALIZATION_FRIEND2 = "FOREIGN KEY (" + COLUMN_FRIEND2 + ") REFERENCES " + Profiles.TABLE_NAME + "(" + Profiles.COLUMN_NAME + ")";
		public static final String FOREIGN_KEY_INITIALIZATION = "CONSTRAINT fk_" + Profiles.TABLE_NAME + "\n" + FOREIGN_KEY_INITIALIZATION_FRIEND1 + "\n" + FOREIGN_KEY_INITIALIZATION_FRIEND2;
		
		public static final String SECOND_FOREIGN_KEY_INITIALIZATION = "CONSTRAINT fk_Profiles \nFOREIGN KEY (friend1, friend2) REFERENCES Profiles(name, name))";
	}
}
