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
			System.out.println("Connection to SQLite database has been established.");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/** Closes the connection to the SQLite database */
	public void close() {
		try {
			connection.close();
			System.out.println("Connection to database has been closed.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Creates a table.
	 * @param strings Columns in the table with their name and type separated by commas from the next column in the table.
	 * Example: name TEXT PRIMARY KEY
	 */
	public void createTable(String tableName, String...strings) {
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n" + String.join(",\n", strings) + ");";
		executeSQLNoResult(sql);
	}
	
	/** 
	 * Executes an sql statement and does not return a result.
	 * Example: Creating or updating a table. 
	 */
	private void executeSQLNoResult(String sql) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
