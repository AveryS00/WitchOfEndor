package http;

public class AppendSegmentRequest {
	public final String playlistName;
	public final String videoLocation;
	
	public AppendSegmentRequest (String playlistName, String videoLocation) {
		this.playlistName = playlistName;
		this.videoLocation = videoLocation;
	}
	
	public String toString() {
		return "AppendSegment(" + playlistName + ", " + videoLocation + ")";
	}
}
