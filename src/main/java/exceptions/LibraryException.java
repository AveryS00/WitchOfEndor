package exceptions;

public class LibraryException extends Exception {
	
	private final String message;
	
	public LibraryException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
