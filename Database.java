import java.sql.*;

public class Database {
	
	/* Instance variables */
	private Connection connection;
	
	/* Constants */
	private static final String URL = "jdbc:sqlite:FacePamphlet.db";
	
	/** Connects to the SQLite database */
	public void connect() {
		try {
			connection = DriverManager.getConnection(URL);
			System.out.println("Connection to SQLite has been established.");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
