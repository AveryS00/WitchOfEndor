package lambdas;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import database.RemoteSiteDAO;
import http.ListRemoteSitesResponse;

public class ListRemoteSitesHandler implements RequestHandler<Object,ListRemoteSitesResponse>{

	
	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<String> getRemoteSites() throws Exception {
		logger.log("in getRemoteSites");
		RemoteSiteDAO dao = new RemoteSiteDAO();
		return dao.listAllRemoteSites();
	}
	
	private AmazonS3 s3;
	
	List<String> systemRemoteSites() throws Exception 
	{
		logger.log("in systemRemoteSites");
		if (s3 == null)
		{
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		
		ArrayList<String> sysRemoteSites = new ArrayList<>();
		
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
				.withBucketName("")
				.withPrefix("remoteSites");
		
		logger.log("process request");
		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
		logger.log("process request succeeded");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for (S3ObjectSummary os: objects)
		{
			String name = os.getKey();
			logger.log("S3 found:" + name);
			
			if(name.endsWith("/"))
			{
				continue;
			}
			
			S3Object obj = s3.getObject("", name);
			
			try (S3ObjectInputStream constantStream = obj.getObjectContent())
			{
				
				int postSlash = name.indexOf('/');
				sysRemoteSites.add(name.substring(postSlash+1));
			}
			catch (Exception e)
			{
				logger.log("Unable to parse contents of " + name);
			}
		}
		
		return sysRemoteSites;
	}
	
	@Override
	public ListRemoteSitesResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all Remote Sites");

		ListRemoteSitesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<String> list = getRemoteSites();
			for (String rs : systemRemoteSites()) {
				if (!list.contains(systemRemoteSites())) {
					list.add(rs);
				}
			}
			response = new ListRemoteSitesResponse(list, 200);
		} catch (Exception e) {
			response = new ListRemoteSitesResponse(403, e.getMessage());
		}
		
		return response;
	}

}
