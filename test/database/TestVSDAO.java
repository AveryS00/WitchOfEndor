package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.VideoSegment;

public class TestVSDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rset;
	
	/**
	 * Constructs a DAO object for VideoSegments
	 * @throws SQLException if unable to connect to Database.
	 */
	public TestVSDAO (Connection conn) throws SQLException {
		this.conn = conn;
	}
	
	/**
	 * Returns a VideoSegment object from the database based on the location of the video which is the key.
	 * @param location The location of the video in the S3 bucket
	 * @return The VideoSegment object represented in the database, or null if there is no VideoSegment with that location.
	 * @throws SQLException
	 */
	public VideoSegment getVideoSegment (String location) throws SQLException {
		try {
			pstmt = conn.prepareStatement("select * from Video where videoLocation = ?;");
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
			pstmt = conn.prepareStatement("insert into Video values (?, ?, ?, ?, ?);");
			pstmt.setString(1, vs.name);
			pstmt.setString(2, vs.location);
			pstmt.setString(3, vs.character);
			if (vs.getIsLocal()) {
				pstmt.setString(4, "T");
			} else {
				pstmt.setString(4, "F");
			}
			if (vs.getIsMarked()) {
				pstmt.setString(5, "T");
			} else {
				pstmt.setString(5, "F");
			}
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
			pstmt = conn.prepareStatement("update Video set videoName = ?, characterName = ?, isLocal = ?, isMarked = ? "
					+ "where videoLocation = ?;");
			pstmt.setString(1, vs.name);
			pstmt.setString(2, vs.character);
			if (vs.getIsLocal()) {
				pstmt.setString(3, "T");
			} else {
				pstmt.setString(3, "F");
			}
			if (vs.getIsMarked()) {
				pstmt.setString(4, "T");
			} else {
				pstmt.setString(4, "F");
			}
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
	 * @param location The location of the VideoSegment to flip the status.
	 * @return True if one row was updated. False otherwise.
	 */
	public boolean flipMark (String location) {
		try {
			if (!isInDatabase(new VideoSegment(location, "", "", false, false))) {
				return false;
			}
			pstmt = conn.prepareStatement("update from Video set isMarked = ? where videoLocation = ?");
			if (getVideoSegment(location).getIsMarked()) {
				// If it is marked, unmark it.
				pstmt.setString(1, "F");
			} else {
				// Else mark it.
				pstmt.setString(1, "T");
			}
			pstmt.setString(2, location);
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
			pstmt = conn.prepareStatement("delete from Video where videoLocation = ?;");
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
			pstmt = conn.prepareStatement("select * from Video;");
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
		boolean isLocal;
		boolean isMarked;
		if (rset.getString("isLocal").equals("T")) {
			isLocal = true;
		} else {
			isLocal = false;
		}
		if (rset.getString("isMarked").equals("T")) {
			isMarked = true;
		} else {
			isMarked = false;
		}
		return new VideoSegment (rset.getString("videoLocation"), rset.getString("characterName"), rset.getString("videoName"),
				isLocal, isMarked);
	}
	
	/**
	 * Private helper method to check if a VideoSegment object is currently in the database by checking the primary key.
	 * @param vs VideoSegment to check for.
	 * @return True if the VideoSegment is in the database.
	 * @throws SQLException
	 */
	private boolean isInDatabase (VideoSegment vs) throws SQLException {
		try {
			pstmt = conn.prepareStatement("select * from Video where videoLocation = ?;");
			pstmt.setString(1, vs.location);
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
}
