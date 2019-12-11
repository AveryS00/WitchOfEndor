package http;

import java.util.ArrayList;
import java.util.List;
import entity.Playlist;

public class DeleteVideoSegmentRequest 
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
	
	public DeleteVideoSegmentRequest(String location)
	{
		this.location = location;
	}
	
	public String toString()
	{
		return "Delete segment at " + location;
	}
	
}
