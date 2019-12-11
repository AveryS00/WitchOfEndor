package http;

public class DeleteVideoSegmentRequest 
{
	public String location;
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public DeleteVideoSegmentRequest(String location)
	{
		this.location = location;
	}
	
	public String toString()
	{
		return "Delete segment at " + location;
	}
	
}
