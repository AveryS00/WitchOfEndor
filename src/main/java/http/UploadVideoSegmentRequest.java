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
	
<<<<<<< HEAD
	public void setBase64encoded (String base64encoded) {
=======
	public void setBased64encoded (String base64encoded) {
>>>>>>> branch 'master' of https://github.com/AveryS00/WitchOfEndor.git
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