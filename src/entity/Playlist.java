package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Playlist {
	ArrayList<VideoSegment> videos;
	final String name;
	
	/**
	 * Constructor for a Playlist.
	 * @param name The name of the Playlist.
	 */
	public Playlist (String name) {
		this.name = name;
		videos = new ArrayList<VideoSegment>();
	}
	
	/**
	 * Appends the given VideoSegment to the end of the Playlist.
	 * @param vs The VideoSegment to append.
	 * @return True if the VideoSegment was appended. False otherwise.
	 */
	public boolean appendVideo (VideoSegment vs) {
		return videos.add(vs);
	}
	
	/**
	 * Removes All occurrences of the VideoSegment from the Playlist.
	 * @param vs The VideoSegment to remove.
	 * @return True if at least one occurrence of the VideoSegment was removed. False otherwise.
	 */
	public boolean removeSegment (VideoSegment vs) {
		return videos.removeAll(Collections.singletonList(vs));
	}
	
	/**
	 * Removes a single occurrence of a VideoSegment at the given index.
	 * @param index The index to of the video segment to remove.
	 * @return True if the VideoSegment at the index was removed. False otherwise.
	 */
	public boolean removeSegment (int index) {
		if (index > videos.size() || index < 0) {
			return false;
		}
		videos.remove(index);
		return true;
	}
	
	/**
	 * Returns an iterator to get all of the VideoSegments in the Playlist in order.
	 * @return An iterator of VideoSegments in the Playlist.
	 */
	public Iterator<VideoSegment> iterator () {
		return videos.iterator();
	}
	
	/**
	 * Equals method to see if two Playlists are equal.
	 * @param o The object to compare to.
	 * @return True if the Playlists have the same name and videos. False otherwise.
	 */
	@Override
	public boolean equals (Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Playlist) {
			Playlist p = (Playlist) o;
			return p.name.equals(this.name);
		}
		return false;
	}
	
	/**
	 * Outputs the name of the Playlist to be the string.
	 */
	public String toString () {
		return this.name;
	}
}
