package entity;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	List<VideoSegment> videos;
	final String name;
	
	/**
	 * Constructor for a Playlist.
	 * @param name The name of the Playlist.
	 */
	public Playlist (String name) {
		this.name = name;
		videos = new ArrayList<VideoSegment>();
	}
	
	public List<VideoSegment> getVideos() {
		return this.videos;
	}
	
	public String getName() {
		return name;
	}
	
	
	/**
	 * Appends the given VideoSegment to the end of the Playlist.
	 * @param vs The VideoSegment to append.
	 * @return True if the VideoSegment was appended. False otherwise.
	 */
	public boolean appendVideo (VideoSegment vs) {
		return videos.add(vs);
	}
}
