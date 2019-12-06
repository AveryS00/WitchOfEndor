package http;

public class UploadVideoSegmentRequest {
	public String character;
	public String text;
	public String based64encoded;
	
	public UploadVideoSegmentRequest(String character, String text, String based64encoded) {
		this.character = character;
		this.text = text;
		this.based64encoded = based64encoded;
	}
	
	public UploadVideoSegmentRequest() {
		
	} 
	
	public void setCharacter (String character){
		this.character = character;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public void setBased64encoded (String based64encoded) {
		this.based64encoded = based64encoded;
	}
	
	public String getCharacter(){
		return character;
	}
	
	public String getText() {
		return text;
	}
	
	public String getBased64encoded() {
		return based64encoded;
	}
}