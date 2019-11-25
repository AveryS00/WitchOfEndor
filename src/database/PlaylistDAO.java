package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import entity.Playlist;
import entity.VideoSegment;

public class PlaylistDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rset;
	
	/**
	 * Constructs a DAO object for Playlists.
	 * @throws SQLException
	 */
	public PlaylistDAO () throws SQLException {
		conn = DatabaseConnection.connect();
	}

	/**
	 * Gets a Playlist object from the RDS.
	 * @param name The name of the playlist.
	 * @return The Playlist object.
	 * @throws SQLException
	 */
	public Playlist getPlaylist (String name) throws SQLException {
		try {
			pstmt = conn.prepareStatement("SELECT videoLocation, videoOrder FROM Playlist WHERE playlistID = (SELECT playlistID FROM Library "
					+ "WHERE playlistName = ?) ORDER BY videoOrder");
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			return generatePlaylist(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStmtRset();
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
			closeStmtRset();
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
			closeStmtRset();
		}
	}
	
	//TODO
	public boolean appendVideoToPlaylist (String name, VideoSegment vs) {
		return false;
	}
	
	//TODO
	public boolean removeVideoFromPlaylist (String name, String videoLocation) {
		return false;
	}
	
	/**
	 * Gets all of the playlist objects from the database (warning, inefficient)
	 * @return List of all playlist objects.
	 */
	public List<Playlist> listAllPlaylists () {
		try {
			List<Playlist> p = new ArrayList<Playlist>();
			pstmt = conn.prepareStatement("SELECT playlistName FROM Library");
			rset = pstmt.executeQuery();
			List<String> names = new ArrayList<String>();
			while (rset.next()) {
				names.add(rset.getString("playlistName"));
			}
			pstmt.close();
			rset.close();
			for (String name : names) {
				p.add(getPlaylist(name));
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeStmtRset();
		}
	}
	
	//TODO
	public List<Playlist> listAllPlaylistNames () {
		return null;
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
	 * @throws SQLException
	 */
	private Playlist generatePlaylist (String name) throws SQLException {
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
			closeStmtRset();
		}
	}
	
	/**
	 * Closes the Rset and Pstmt.
	 */
	private void closeStmtRset () {
		try {
			if (rset != null) {
				rset.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
