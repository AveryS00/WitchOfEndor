package http;

public class AppendSegmentRequest {
	String playlistName;
	String videoLocation;
	
	public AppendSegmentRequest (String playlistName, String videoLocation) {
		this.playlistName = playlistName;
		this.videoLocation = videoLocation;
	}
	
	public AppendSegmentRequest () {
	}
	
	public String toString () {
		return "AppendSegment(" + playlistName + ", " + videoLocation + ")";
	}
	
	public String getPlaylistName () {
		return playlistName;
	}
	
	public void setPlaylistName (String playlistName) {
		this.playlistName = playlistName;
	}
	
	public String getVideoLocation () {
		return videoLocation;
	}
	
	public void setVideoLocation (String videoLocation) {
		this.videoLocation = videoLocation;
	}
}
