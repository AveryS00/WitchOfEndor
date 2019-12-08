package http;

public class UploadVideoSegmentRequest {
	public String character;
	public String text;
	public String base64encoded;
	
	public UploadVideoSegmentRequest(String character, String text, String base64encoded) {
		this.character = character;
		this.text = text;
		this.base64encoded = base64encoded;
	}
	
	public UploadVideoSegmentRequest() {
		
	} 
	
	public void setCharacter (String character){
		this.character = character;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public void setBased64encoded (String base64encoded) {
		this.base64encoded = base64encoded;
	}
	
	public String getCharacter(){
		return character;
	}
	
	public String getText() {
		return text;
	}
	
	public String getBase64encoded() {
		return base64encoded;
	}
}