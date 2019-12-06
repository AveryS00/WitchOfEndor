package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import entity.Playlist;

public class PlaylistDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rset;
	
	/**
	 * Constructs a DAO object for Playlists.
	 * @throws Exception 
	 */
	public PlaylistDAO () throws Exception {
		conn = DatabaseConnection.connect();
	}

	/**
	 * Gets a Playlist object from the RDS.
	 * @param name The name of the playlist.
	 * @return The Playlist object.
	 * @throws Exception 
	 */
	public Playlist getPlaylist (String name) throws Exception {
		try {
			pstmt = conn.prepareStatement("SELECT videoLocation, videoOrder FROM Playlist WHERE playlistID = (SELECT playlistID FROM Library "
					+ "WHERE playlistName = ?) ORDER BY videoOrder");
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			return generatePlaylist(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Creates a playlist in the RDS.
	 * @param name The name of the Playlist.
	 * @return True if created. False otherwise.
	 */
	public boolean createPlaylist (String name) {
		try {
			if (isInDatabase(name)) {
				return false;
			}
			pstmt = conn.prepareStatement("INSERT INTO Library (playlistID, playlistName) VALUES (?, ?)");
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Deletes the playlist from the RDS.
	 * @param name The name of the playlist to delete.
	 * @return True if deleted. False otherwise.
	 */
	public boolean deletePlaylist (String name) {
		try {
			if (!isInDatabase(name)) {
				return false;
			}
			pstmt = conn.prepareStatement("DELETE FROM Library WHERE playlistName = ?");
			pstmt.setString(1, name);
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
	 * Appends a video to the playlist in the RDS.
	 * @param name The name of the playlist.
	 * @param videoLocation the Location of the video in the S3 bucket.
	 * @return True if appended. False otherwise.
	 */
	public boolean appendVideoToPlaylist (String name, String videoLocation) {
		try {
			String id = getID(name);
			int order = getNumVideos(id);
			pstmt = conn.prepareStatement("INSERT INTO Playlist (playlistID, videoLocation, videoOrder) VALUES (?, ?, ?)");
			pstmt.setString(1, id);
			pstmt.setString(2, videoLocation);
			pstmt.setInt(3, order + 1);
			int numRows = pstmt.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Private helper method to get the ID of a playlist from the name.
	 * @param name The name of a playlist.
	 * @return The ID.
	 * @throws SQLException
	 */
	private String getID (String name) throws SQLException {
		try {
			pstmt = conn.prepareStatement("SELECT playlistID FROM Library WHERE playlistName = ?");
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			rset.next();
			String id = rset.getString("playlistID");
			return id;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Gets the number of videos currently in a playlist.
	 * @param id The ID of the playlist.
	 * @return The number of videos in the playlist.
	 * @throws SQLException
	 */
	private int getNumVideos(String id) throws SQLException {
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(videoOrder) AS num FROM Playlist WHERE playlistID = ?");
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			rset.next();
			int count = rset.getInt("num");
			return count;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Deletes the video at the specified spot in the playlist. Order starts at 1.
	 * @param name The name of the playlist.
	 * @param order The index of the Video.
	 * @return True if deleted. False otherwise.
	 */
	public boolean removeVideoFromPlaylist (String name, int order) {
		try {
			String id = getID(name);
			pstmt = conn.prepareStatement("DELETE FROM Playlist WHERE playlistID = ? AND videoOrder = ?");
			pstmt.setString(1, id);
			pstmt.setInt(2, order);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement("UPDATE Playlist SET videoOrder = videoOrder - 1 WHERE playlistID = ? AND videoOrder > ?");
			pstmt.setString(1, id);
			pstmt.setInt(2, order);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Gets all of the playlist objects from the database (warning, inefficient)
	 * @return List of all playlist objects.
	 */
	public List<Playlist> listAllPlaylists () {
		try {
			List<Playlist> p = new ArrayList<Playlist>();
			List<String> names = listAllPlaylistNames();
			for (String name : names) {
				p.add(getPlaylist(name));
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Gets all of the names of each playlist from the database.
	 * @return A list of all the names.
	 */
	public List<String> listAllPlaylistNames () {
		try {
			pstmt = conn.prepareStatement("SELECT playlistName FROM Library ORDER BY playlistName");
			rset = pstmt.executeQuery();
			List<String> names = new ArrayList<String>();
			while (rset.next()) {
				names.add(rset.getString("playlistName"));
			}
			return names;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
	
	/**
	 * Closes the connection to the database after all work has been finished with the DAO.
	 * @throws SQLException
	 */
	public void close () throws SQLException {
		conn.close();
	} 
	
	/**
	 * Private helper function to generate a playlist based on the current rset
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	private Playlist generatePlaylist (String name) throws Exception {
		Playlist p = new Playlist(name);
		VideoSegmentDAO VSDAO = new VideoSegmentDAO();
		while (rset.next()) {
			p.appendVideo(VSDAO.getVideoSegment(rset.getString("videoLocation")));
		}
		return p;
	}
	
	/**
	 * Private helper function to check if a playlist is in the Database
	 * @param name The name of the Playlist
	 * @return True if it is. False otherwise.
	 * @throws SQLException
	 */
	private boolean isInDatabase (String name) throws SQLException {
		try {
			pstmt = conn.prepareStatement("SELECT * FROM Library WHERE playlistName = ?");
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			boolean isInDB = rset.next();
			return isInDB;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseConnection.closeStmt(pstmt, rset);
		}
	}
}
