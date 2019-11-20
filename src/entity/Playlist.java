package entity;

import java.util.ArrayList;

public class Playlist {
	ArrayList<VideoSegment> videos;
	String name;
	
	public Playlist (String name) {
		this.name = name;
	}
}
