package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import entity.Library;
import http.AppendSegmentRequest;
import http.AppendSegmentResponse;
import http.DeletePlaylistRequest;
import http.DeletePlaylistResponse;

public class DeletePlaylistHandler implements RequestHandler<DeletePlaylistRequest, DeletePlaylistResponse> {

	@Override
	public DeletePlaylistResponse handleRequest(DeletePlaylistRequest req, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
