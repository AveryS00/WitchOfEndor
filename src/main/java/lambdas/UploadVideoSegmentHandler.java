package lambdas;

import http.UploadVideoSegmentRequest;
import http.UploadVideoSegmentResponse;
import java.io.ByteArrayInputStream;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import database.VideoSegmentDAO;
import entity.VideoSegment;

public class UploadVideoSegmentHandler implements RequestHandler<UploadVideoSegmentRequest, UploadVideoSegmentResponse> {
	public LambdaLogger logger;
	private AmazonS3 s3 = null;
	private final String URL = "https://cs3733-witch-of-endor.s3.us-east-2.amazonaws.com/VideoSegments/";	
			
	@Override
	public UploadVideoSegmentResponse handleRequest(UploadVideoSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to upload video segment.");
		String text = req.text.replaceAll("\\s+","");

		UploadVideoSegmentResponse response = null;
		logger.log(req.toString());

		VideoSegmentDAO dao;
		
		byte[] encoded = java.util.Base64.getDecoder().decode(req.based64encoded);
		try {
			if (addToBucket(encoded, text)) {
				dao = new VideoSegmentDAO();
				VideoSegment vs = new VideoSegment(URL + text, req.character, req.text, true);
				if (dao.addVideoSegment(vs)) {
					return new UploadVideoSegmentResponse(200, "Video segment uploaded: " + req.text);
				}
			}
			response = new UploadVideoSegmentResponse(400, "Unable to upload video segment " + req.text);
		} catch (Exception e) {
			response = new UploadVideoSegmentResponse(500, "Unable to upload video segment " + req.text + " " + e.getMessage());
		}
		
		return response;
	}
	
	public boolean addToBucket (byte[] contents, String name) throws Exception {
		logger.log("trying to add to bucket");
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("cs3733-witch-of-endor", "VideoSegments/" + name, bais, omd).withCannedAcl(CannedAccessControlList.PublicRead));
		return true;
	}
}
