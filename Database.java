import java.sql.*;

// TODO: Delete from database when deleted from database HashMap by delete button.
// TODO: Images.
// TODO: Friends stuff with database.
// TODO: Deal with adding people who already exist? I think the functionality might already be fine. Double check that.

public class Database implements DatabaseConstants {
	
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
	 * Inserts a record into a table.
	 * @param columns Names of columns into which values will be inserted.
	 * @param values Values which will be inserted into the corresponding columns.
	 */
	public void insertRecord(String tableName, String[] columns, String[] values) {
		String sql = "INSERT INTO " + tableName + " (" + String.join(",\n", columns) + ")\nVALUES (" + String.join(",\n", values) + ");";
		executeSQLNoResult(sql);
	}
	
	public void updateRecord(String tableName, String[] columns, String[] values, String whereCondition) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i]);
			sb.append(" = ");
			sb.append(values[i]);
			sb.append(",");
		}
		sb.append(columns[columns.length - 1]);
		sb.append(" = ");
		sb.append(values[columns.length - 1]);
		String sql = "UPDATE " + tableName + " SET " + sb.toString() + " WHERE " + whereCondition;
		executeSQLNoResult(sql);
	}
	
	public ResultSet executeSelectQuery(String[] projection, String tableName) {
		String whereCondition = "1=1";
		return executeSelectQuery(projection, tableName, whereCondition);
	}
	
	public ResultSet executeSelectQuery(String[] projection, String tableName, String whereCondition) {
		String sql = "SELECT " + String.join(", ", projection) + " FROM " + tableName + " WHERE " + whereCondition + ";";
		return executeSQLForResult(sql);
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
	
	private ResultSet executeSQLForResult(String sql) {
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		return rs;
	}
	
	/** Puts a string in quotations and returns it. */
	public static String quotations(String str) {
		return "'" + str + "'";
	}
	
	
}
