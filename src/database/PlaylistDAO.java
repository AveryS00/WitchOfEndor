package database;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaylistDAO {
	Connection conn;
	
	public PlaylistDAO () {
		try {
			conn = DatabaseConnection.connect();
		} catch (SQLException e) {
			conn = null;
		}
	}

	
	
}
