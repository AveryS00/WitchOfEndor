package http;

public class MarkVideoSegmentRequest 
{
	public String location;
	public String character;
	public String name;
	public boolean isLocal;
	boolean isMarked;
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public MarkVideoSegmentRequest()
	{
		
	}
	
	public MarkVideoSegmentRequest(String location, String character, String name, boolean isLocal) {
		this.location = location;
		this.character = character;
		this.name = name;
		this.isLocal = isLocal;
		this.isMarked = false;
	}
	
	public MarkVideoSegmentRequest(String location)
	{
		this.location = location;
	}
	
	public String toString()
	{
		return "Delete segment at " + location;
	}
	
}
