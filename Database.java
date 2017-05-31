import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;

// TODO: close database upon pressing the exit button.
// TODO: Images.
// TODO: Friends stuff with database.
// TODO: Deal with adding people who already exist? I think the functionality might already be fine. Double check that.

/* Problems with images:
 * updateRecord takes a String array, not a Blob. How do I fix this? Do I make a separate updateRecord which takes a Blob?
 * Does my method for getting a pixel array and putting it into a byte array and vice versa, work? */

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
	 * @param tableName Name of the table to insert a record into.
	 * @param columns Names of columns into which values will be inserted.
	 * @param values Values which will be inserted into the corresponding columns.
	 */
	public void insertRecord(String tableName, String[] columns, String[] values) {
		String sql = "INSERT INTO " + tableName + " (" + String.join(",\n", columns) + ")\nVALUES (" + String.join(",\n", values) + ");";
		executeSQLNoResult(sql);
	}
	
	/**
	 * Deletes a record from a table.
	 * @param tableName Name of the table to delete records from. 
	 * @param whereCondition Condition of what to delete. For example, "name = Greg" 
	 * will delete all entries where name = Greg.
	 */
	public void deleteRecord(String tableName, String whereCondition) {
		String sql = "DELETE FROM " + tableName + " WHERE " + whereCondition;
		executeSQLNoResult(sql);
	}
	
	/**
	 * Updates a record in a table.
	 * @param tableName Name of the table in which to update a record.
	 * @param columns Names of the columns of which to update a record.
	 * @param values Values you want to put into the column you are updating. 
	 * @param whereCondition Condition of what records to update. Example: "name = Greg" 
	 * updates selected columns of records where name = Greg. 
	 */
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
	
	public void updatePicture(String tableName, String pictureColumn, String filepath, String whereCondition) {
		String sql = "UPDATE " + tableName + " SET " + pictureColumn + " = ? WHERE " + whereCondition;
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setBytes(1, readFile(filepath));
			
			pstmt.executeUpdate();
			System.out.println("Stored the file in the BLOB column");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/** Converts an image file into a byte array and returns it. */
	private byte[] readFile(String filepath) {
		File file = new File(filepath);
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	
	public void readPicture(String tableName, String pictureColumn, String filepath, String whereCondition) {
		String sql = "SELECT " + pictureColumn + " FROM " + tableName + " WHERE " + whereCondition;
		ResultSet rs = executeSQLForResult(sql);
		FileOutputStream fos = null;
		
		try {
			File file = new File(filepath);
			fos = new FileOutputStream(file);
			System.out.println("Writing BLOB to file " + file.getAbsolutePath());
			
			if (rs.next()) {
				InputStream input = rs.getBinaryStream(pictureColumn);
				byte[] buffer = new byte[1024];
				while (input.read(buffer) > 0) {
					fos.write(buffer);
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Selects without a WHERE condition.
	 * @param projection What you want to select from the table. Example: "*".
	 * @param tableName Name of the table you want to select from. */
	public ResultSet executeSelectQuery(String[] projection, String tableName) {
		String whereCondition = "1=1";
		return executeSelectQuery(projection, tableName, whereCondition);
	}
	
	/**
	 * Selects only records with a certain whereCondition.
	 * @param projection What you want to select from the table. Example: "*".
	 * @param tableName Name of the table you want to select from. 
	 * @param whereCondition Condition of what records to select. 
	 * 
	 * Returns a ResultSet which can be iterated over.
	 * Example use: Getting profiles from database and putting them in database HashMap
	 * upon startup of application. 
	 */
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
	
	/** 
	 * Executes an SQL statement and returns a ResultSet which can be iterated over.
	 * Example: Selecting from a table.
	 * */
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
