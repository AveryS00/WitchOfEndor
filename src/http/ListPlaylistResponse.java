package http;

import java.util.ArrayList;
import java.util.List;

import entity.Playlist;

public class ListPlaylistResponse {
	public final List<Playlist> list;
	public final int statusCode; 
	public final String error;

	public ListPlaylistResponse(List<Playlist> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}

	public ListPlaylistResponse(int code, String errorMessage) {
		this.list = new ArrayList<Playlist>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString () {
		if (list == null) {
			return "No Playlists";
		} else {
			return "AllPlaylists(" + list.size() + ")";
		}
	}
}
