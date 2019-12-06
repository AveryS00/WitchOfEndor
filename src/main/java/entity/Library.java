package entity;

import java.util.ArrayList;
import java.util.List;

import database.PlaylistDAO;
import database.RemoteSiteDAO;
import database.VideoSegmentDAO;
import exceptions.LibraryException;

public class Library {
	List<Playlist> playlists;
	List<VideoSegment> segments;
	List<String> remoteURLs;

	public Library() throws LibraryException {
		playlists = new ArrayList<Playlist>();
		segments = new ArrayList<VideoSegment>();
		remoteURLs = new ArrayList<String>();
		populateLibraryFromDB();
	}

	void populateLibraryFromDB() throws LibraryException {
		try {
			VideoSegmentDAO segmentDAO = new VideoSegmentDAO();
			segments = segmentDAO.listAllVideoSegments();
			segmentDAO.close();
			PlaylistDAO playlistDAO = new PlaylistDAO();
			playlists = playlistDAO.listAllPlaylists();
			playlistDAO.close();
			RemoteSiteDAO remoteDAO = new RemoteSiteDAO();
			remoteURLs = remoteDAO.listAllRemoteSites();
			remoteDAO.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LibraryException("Failed to Access Database");
		}
	}
	
	public List<Playlist> getPlaylists() throws LibraryException {
		if(this.playlists.size() == 0) {
			throw new LibraryException("No playlists in library");
		}
		return this.playlists;
	}
	
	public Playlist getPlaylist(String name) throws LibraryException {
		for(Playlist p : playlists) {
			if(p.name.equals(name)) {
				return p;
			}
		}
		throw new LibraryException("Playlist not Found with name " + name);
	}
	
	public List<VideoSegment> getSegments() throws LibraryException {
		if(this.segments.size() == 0) {
			throw new LibraryException("No Video Segments in Library");
		}
		return this.segments;
	}
	
	public VideoSegment getVideoSegment(String location) throws LibraryException {
		for(VideoSegment vs : segments) {
			if(vs.location.equals(location)) {
				return vs;
			}
		}
		throw new LibraryException("No video Segment found for location " + location);
	}
	
	public List<String> getRemoteSites() throws LibraryException {
		if(this.remoteURLs.size() == 0) {
			throw new LibraryException("No Remote Sites in Library");
		}
		return this.getRemoteSites();
	}


}
