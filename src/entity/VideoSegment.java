package entity;

public class VideoSegment {
	public final String location;
	public final String character;
	public final String name;
	boolean isLocal;
	boolean isMarked;
	
	/**
	 * Public constructor for a video segment which is by default unmarked.
	 * @param location The url for the location of the video in the S3 bucket.
	 * @param character The character in the video segment that is speaking.
	 * @param name The text that is said in the video clip.
	 * @param isLocal Whether the video segment is local or remote. True if local.
	 */
	public VideoSegment (String location, String character, String name, boolean isLocal) {
		this.location = location;
		this.character = character;
		this.name = name;
		this.isLocal = isLocal;
		this.isMarked = false;
	}
	
	/**
	 * Public constructor for video segment which also allows for the setting of marked status.
	 * @param location The url for the location of the video in the S3 bucket.
	 * @param character The character in the video segment that is speaking.
	 * @param name The text that is said in the video clip.
	 * @param isLocal Whether the video segment is local or remote. True if local.
	 * @param isMarked Whether the video segment is marked to be shown locally only. True if marked.
	 */
	public VideoSegment (String location, String character, String name, boolean isLocal, boolean isMarked) {
		this.location = location;
		this.character = character;
		this.name = name;
		this.isLocal = isLocal;
		this.isMarked = isMarked;
	}
	
	/**
	 * Changes the mark status of the video segment. If video segment is marked will unmark and vice versa.
	 */
	public void changeMark () {
		isMarked = !isMarked;
	}
	
	/**
	 * Gets the current marked status of the video segment.
	 * @return True if the video is marked to be local only. False otherwise.
	 */
	public boolean getIsMarked () {
		return isMarked;
	}
	
	/**
	 * Gets whether the current video segment is local or not.
	 * @return True if the video is local. False otherwise.
	 */
	public boolean getIsLocal () {
		return isLocal;
	}
	
	/**
	 * Checks if this object is equal to another object.
	 * @param o The object to check with.
	 * @return True if the objects are equal. False otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof VideoSegment) {
			VideoSegment vs = (VideoSegment) o;
			return this.location.equals(vs.location) && this.name.equals(vs.name) && this.character.equals(vs.character);
		}
		return false;
	}
}
