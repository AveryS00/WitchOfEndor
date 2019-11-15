package entity;

import java.util.ArrayList;

public class Library {
	ArrayList<Playlist> playlists;
	ArrayList<VideoSegment> videos;
	ArrayList<String> urls;
	
	public Library () {
		playlists = new ArrayList<Playlist>();
		videos = new ArrayList<VideoSegment>();
		urls = new ArrayList<String>();
	}
}
