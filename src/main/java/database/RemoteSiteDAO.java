package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RemoteSiteDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rset;
	
	/**
	 * Constructs a connection to the RDS to get and remove RemoteSites.
	 * @throws Exception 
	 */
	public RemoteSiteDAO () throws Exception {
		conn = DatabaseConnection.connect();
	}
	
	/**
	 * Returns all of the URLs in the RDS.
	 * @return A list of String URLs.
	 */
	public List<String> listAllRemoteSites () {
		try {
			List<String> urls = new ArrayList<String>();
			pstmt = conn.prepareStatement("SELECT * FROM RemoteSite");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				urls.add(rset.getString("url"));
			}
			return urls;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Adds a remote URL to the RDS.
	 * @param url The URL to add.
	 * @return True if added. False otherwise.
	 */
	public boolean addRemoteSite (String url) {
		try {
			if (isInDatabase(url)) {
				return false;
			}
			pstmt = conn.prepareStatement("INSERT INTO RemoteSite (url) Values (?)");
			pstmt.setString(1, url);
			int numRows = pstmt.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Removes the URL from the RDS.
	 * @param url The URL to remove.
	 * @return True if removed. False otherwise.
	 */
	public boolean removeRemoteSite (String url) {
		try {
			if (!isInDatabase(url)) {
				return false;
			}
			pstmt = conn.prepareStatement("DELETE FROM RemoteSite WHERE url = ?");
			pstmt.setString(1, url);
			int numRows = pstmt.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Checks if a URL is in the Database.
	 * @param url The URL to insert.
	 * @return True if in. False otherwise.
	 * @throws SQLException
	 */
	private boolean isInDatabase (String url) throws SQLException {
		try {
			pstmt = conn.prepareStatement("SELECT * FROM RemoteSite WHERE url = ?");
			pstmt.setString(1, url);
			rset = pstmt.executeQuery();
			boolean inDB = rset.next();
			return inDB;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
}
