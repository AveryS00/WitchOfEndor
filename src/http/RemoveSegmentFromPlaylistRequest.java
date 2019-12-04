package http;

public class RemoveSegmentFromPlaylistRequest {
	
	public int location;
	public String playlistName;
	
	public RemoveSegmentFromPlaylistRequest() {
		 
	}
	
	public RemoveSegmentFromPlaylistRequest(String playlistName, int location) {
		 this.location = location;
		 this.playlistName = playlistName;
	}

}
