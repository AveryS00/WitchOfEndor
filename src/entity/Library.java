package entity;

import java.util.ArrayList;
import java.util.List;

import exceptions.LibraryException;

public class Library {
	List<Playlist> playlists;
	List<VideoSegment> segments;
	List<String> remoteURLs;
	
	public Library() {
		playlists = new ArrayList<Playlist>();
		segments = new ArrayList<VideoSegment>();
		remoteURLs = new ArrayList<String>();
		populateLibraryFromDB();
	}
	
	void populateLibraryFromDB() {
		
	}
	
	/**
	 * Adds video segment to library
	 * @param segment Video segment to add
	 * @return true if segment is not already in library and was able to be added
	 * @throws LibraryException if segment is already in library
	 */
	boolean addSegment(VideoSegment segment) throws LibraryException {
		for(int i = 0; i < segments.size(); i++)
		{
			if(segments.get(i).equals(segment))
			{
				throw new LibraryException("Segment already in library.");
			}
		}
		segments.add(segment);
		return true;
	}
	
	/**
	 * Removes video segment from library
	 * @param segment Video segment to remove
	 * @return true if segment was already in library and was able to be removed
	 * @throws LibraryException if segment is not in library
	 */
	boolean removeSegment(VideoSegment segment) throws LibraryException {
		for(int i = 0; i < segments.size(); i++)
		{
			if(segments.get(i).equals(segment))
			{
				segments.remove(i);
				return true;
			}
		}
		throw new LibraryException("Segment not in library.");
	}
	
	// PARTICIPANT METHODS
	
	/**
	 * Creates playlist in library and saves to DB
	 * @param name unique playlist name 
	 * @return true if playlist was added and saved
	 * @throws LibraryException if playlist with same name is already in library
	 */
	public boolean createPlaylist(String name) throws LibraryException {
		for(int i = 0; i < playlists.size(); i++)
		{
			if(playlists.get(i).equals(new Playlist(name)))
			{
				throw new LibraryException("Segment already in library.");
			}
		}
		playlists.add(new Playlist(name));
		return true;
	}
	
	/**
	 * Deletes playlist in library and updates DB
	 * @param name name of playlist to delete
	 * @return true if playlist was deleted
	 * @throws LibraryException if playlist is not in library
	 */
	public boolean deletePlaylist(String name) throws LibraryException {
		for(int i = 0; i < playlists.size(); i++)
		{
			if(playlists.get(i).equals(new Playlist(name)))
			{
				playlists.remove(i);
				return true;
			}
		}
		throw new LibraryException("Segment not in library.");
	}
	
	/**
	 * Gets playlist from library with given name
	 * @param name playlist to get
	 * @return Playlist from library
	 * @throws LibraryException if playlist with name doesnt exist
	 */
	public Playlist getPlaylist(String name) throws LibraryException {
		for(int i = 0; i < playlists.size(); i++)
		{
			if(playlists.get(i).equals(new Playlist(name)))
			{
				return playlists.get(i);
			}
		}
		throw new LibraryException("Named playlist doesn not exist");
	}
	
	public List<Playlist> getPlaylists() {
		return this.playlists;
	}
	
	
	
	/**
	 * Appends video segment to end of playlist and updates DB
	 * @param p playlist 
	 * @param v video segment
	 * @return true if video segment was appended to playlist
	 * @throws LibraryException 
	 */
	public boolean appendSegmentToPlaylist(Playlist p, VideoSegment v) throws LibraryException {
		// how does this handle trying to add the same segment twice
		return false;
	}
	
	/**
	 * Removes video segment from playlist and updates DB
	 * @param p playlist
	 * @param v video segment
	 * @return true if segment was removed
	 * @throws LibraryException if segment is not in playlist
	 */
	public boolean removeSegmentFromPlaylist(Playlist p, VideoSegment v) throws LibraryException {
		// how does this handle trying to remove a segment that is in the playlist twice
		return false;
	}
	
	/**
	 * 
	 * @return all local video segments
	 */
	public List<VideoSegment> getLocalSegments() {
		return null;
	}
	
	/**
	 * 
	 * @return all remote video segments
	 */
	public List<VideoSegment> getRemoteSegment() {
		return null;
	}
	
	/**
	 * Searches local library for segments that contain phrase and was spoken by character 
	 * @param character character to search for
	 * @param phrase phrase to search for
	 * @return list of all matching segments
	 * @throws LibraryException if no character or phrase is specified
	 */
	public List<VideoSegment> searchForSegments(String character, String phrase) throws LibraryException {
		// easier to handle in browser
		return null;
	}

	/**
	 * Adds video segment to library and updates db
	 * @param segment segment to add
	 * @return true if segment was added
	 * @throws LibraryException if segment is already in library
	 */
	public boolean addSegmentToLibrary(VideoSegment segment) throws LibraryException{
		return false;
	}
	
	// ADMIN METHODS
	
	/**
	 * Marks segment for viewing remotely and updates DB
	 * @param segment segment to mark
	 * @return true if segment is able to be marked
	 * @throws LibraryException if segment is already marked
	 */
	public boolean markSegment(VideoSegment segment) throws LibraryException {
		return false;
	}
	
	/**
	 * Unmarks segment for remote viewing and updates DB
	 * @param segment segment to unmark
	 * @return true if segment is able to be unmarked
	 * @throws LibraryException if segment is already unmarked
	 */
	public boolean unmarkSegment(VideoSegment segment) throws LibraryException {
		return false;
	}
	
	/**
	 * Deletes video segment from local library and updates DB
	 * @param segment segment to delete
	 * @return true if segment is able to be deleted
	 * @throws LibraryException is segment does not exist in library
	 */
	public boolean deleteSegment(VideoSegment segment) throws LibraryException {
		return false;
	}
	
	/**
	 * Registers url to retrieve remote segments from
	 * @param url url to register
	 * @return true if url is registered
	 * @throws LibraryException if url is already registered
	 */
	public boolean registerRemoteURL(String url) throws LibraryException {
		return false;
	}
	
	/**
	 * Unregisters a remote url
	 * @param url url to unregister
	 * @return true if url is registers
	 * @throws LibraryException if url does not exist in librarys
	 */
	public boolean unregisterRemoteURL(String url) throws LibraryException {
		return false;
	}
	
	
	
}
