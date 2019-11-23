package entity;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	final List<VideoSegment> videos;
	String name;
	
	public Playlist (String name) {
		this.name = name;
		videos = new ArrayList<VideoSegment>();
	}
	
	boolean addSegment(VideoSegment segment) {
		return false;
	}
	
	boolean removeSegment(VideoSegment segment) {
		return false;
	}
	
	boolean rename(String name) {
		return false;
	}
	
}
