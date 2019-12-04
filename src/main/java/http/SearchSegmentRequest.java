package http;

public class SearchSegmentRequest {

	public String character;
	public String phrase;
	
	public SearchSegmentRequest() {
		
	}
	
	public SearchSegmentRequest(String character, String phrase) {
		this.character = character;
		this.phrase = phrase;
	}
	
}
