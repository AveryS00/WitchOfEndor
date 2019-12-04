package http;

public class DeletePlaylistRequest {
	String playlistName;
	
	public DeletePlaylistRequest (String playlistName) {
		this.playlistName = playlistName;
	}
	
	public DeletePlaylistRequest () {
	}
	
	public String toString () {
		return "DeletePlaylist(" + playlistName + ")";
	}
	
	public String getPlaylistName () {
		return playlistName;
	}
	
	public void setPlaylistName (String playlistName) {
		this.playlistName = playlistName;
	}
}
