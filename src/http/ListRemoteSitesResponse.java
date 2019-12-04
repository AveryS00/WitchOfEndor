package http;

import java.util.ArrayList;
import java.util.List;

public class ListRemoteSitesResponse {
	
	public final List<String> list;
	public final int statusCode;
	public final String error;
	
	public ListRemoteSitesResponse (List<String> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public ListRemoteSitesResponse (int code, String errorMessage) {
		this.list = new ArrayList<String>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyRemoteSites"; }
		return "AllRemoteSites(" + list.size() + ")";
	}

}
