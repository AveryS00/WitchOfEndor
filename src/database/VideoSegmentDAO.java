package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.VideoSegment;

public class VideoSegmentDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rset;
	
	/**
	 * Constructs a DAO object for VideoSegments
	 * @throws SQLException if unable to connect to Database.
	 */
	public VideoSegmentDAO () throws SQLException {
		this.conn = DatabaseConnection.connect();
	}
	
	/**
	 * Returns a VideoSegment object from the database based on the location of the video which is the key.
	 * @param location The location of the video in the S3 bucket
	 * @return The VideoSegment object represented in the database, or null if there is no VideoSegment with that location.
	 * @throws SQLException
	 */
	public VideoSegment getVideoSegment (String location) throws SQLException {
		try {
			pstmt = conn.prepareStatement("SELECT * FROM Video WHERE videoLocation = ?");
			pstmt.setString(1, location);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				VideoSegment vs = generateVideoSegment();
				return vs;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStmtRset();
		}
	}
	
	/**
	 * Adds the passed in VideoSegment to the AWS RDS if it is not in there already.
	 * @param vs The VideoSegment to add to RDS
	 * @return True if the VideoSegment was added to the database. False otherwise.
	 */
	public boolean addVideoSegment (VideoSegment vs) {
		try {
			if (isInDatabase(vs)) {
				return false;
			}
			pstmt = conn.prepareStatement("INSERT INTO Video (videoLocation, videoName, characterName, isLocal, isMarked) VALUES (?, ?, ?, ?, ?)");
			pstmt.setString(1, vs.location);
			pstmt.setString(2, vs.name);
			pstmt.setString(3, vs.character);
			pstmt.setBoolean(4, vs.getIsLocal());
			pstmt.setBoolean(5, vs.getIsMarked());
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
	 * Updates the information in the database based on the given VideoSegment
	 * @param vs The VideoSegment that will be written into the 
	 * @return True if could update the info. False otherwise.
	 */
	public boolean updateVideoSegment (VideoSegment vs) {
		try {
			if (!isInDatabase(vs)) {
				return false;
			}
			pstmt = conn.prepareStatement("UPDATE Video SET videoName = ?, characterName = ?, isLocal = ?, isMarked = ? "
					+ "WHERE videoLocation = ?");
			pstmt.setString(1, vs.name);
			pstmt.setString(2, vs.character);
			pstmt.setBoolean(3, vs.getIsLocal());
			pstmt.setBoolean(4, vs.getIsMarked());
			pstmt.setString(5, vs.location);
			int updatedRows = pstmt.executeUpdate();
			return (updatedRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeStmtRset();
		}
	}
	
	/**
	 * Flips the mark status in the database.
	 * @param vs The VideoSegment to flip the status of.
	 * @return True if one row was updated. False otherwise.
	 */
	public boolean flipMark (VideoSegment vs) {
		try {
			if (!isInDatabase(new VideoSegment(vs.location, "", "", false, false))) {
				return false;
			}
			pstmt = conn.prepareStatement("UPDATE Video SET isMarked = ? WHERE videoLocation = ?");
			pstmt.setBoolean(1, !vs.getIsMarked());
			pstmt.setString(2, vs.location);
			int count = pstmt.executeUpdate();
			return (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeStmtRset();
		}
	}
	
	/**
	 * Deletes the VideoSegment from the Database.
	 * @param location The URL of the VideoSegment in the S3 Bucket.
	 * @return True if could delete the VideoSegment. False otherwise.
	 */
	public boolean deleteVideoSegment (String location) {
		try {
			if (!isInDatabase(new VideoSegment(location, "", "", false, false))) {
				return false;
			}
			pstmt = conn.prepareStatement("DELETE FROM Video WHERE videoLocation = ?");
			pstmt.setString(1, location);
			int numRows = pstmt.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeStmtRset();
		}
	}
	
	/**
	 * Creates a List of all VideoSegments currently in the Database.
	 * @return A list of all VideoSegments in Database.
	 */
	public List<VideoSegment> listAllVideoSegments () {
		List<VideoSegment> list = new ArrayList<VideoSegment>();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM Video");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(generateVideoSegment());
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeStmtRset();
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
	 * Closes the PreparedStatement and ResultSet
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
	
	/**
	 * Helper method to create a VideoSegment object based on the current tuple of the result set.
	 * @param rset The ResultSet to take the tuple from.
	 * @return A VideoSegment object based on the current tuple of rset.
	 * @throws SQLException
	 */
	private VideoSegment generateVideoSegment () throws SQLException {
		return new VideoSegment (rset.getString("videoLocation"), rset.getString("characterName"), rset.getString("videoName"),
				rset.getBoolean("isLocal"), rset.getBoolean("isMarked"));
	}
	
	/**
	 * Private helper method to check if a VideoSegment object is currently in the database by checking the primary key.
	 * @param vs VideoSegment to check for.
	 * @return True if the VideoSegment is in the database.
	 * @throws SQLException
	 */
	private boolean isInDatabase (VideoSegment vs) throws SQLException {
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Video WHERE videoLocation = ?");
			boolean isInDB;
			try {
				pstmt.setString(1, vs.location);
				ResultSet rset = pstmt.executeQuery();
				
				try {
					isInDB = rset.next();
				} finally {
					if (rset != null) {
						rset.close();
					}
				}
				
				return isInDB;
			} finally {
				if (pstmt != null) {
					pstmt.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
