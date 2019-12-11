package lambdas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.VideoSegmentDAO;
import entity.VideoSegment;
import http.DeleteVideoSegmentRequest;
import http.DeleteVideoSegmentResponse;

public class DeleteVideoSegmentHandlerTest extends LambdaTest {

	VideoSegmentDAO dao;
	VideoSegment testVS;
	
	@Before
	public void setUp() throws Exception 
	{
		dao = new VideoSegmentDAO();
		testVS = new VideoSegment("location", "character", "name", true);
		dao.addVideoSegment(testVS);
	}
	
	
	@After
	public void tearDown() throws Exception 
	{
		dao.close();
	}
	
	
	@Test
	public void test() 
	{
		boolean isTrue = false;
		DeleteVideoSegmentHandler handler = new DeleteVideoSegmentHandler();
		Context context = createContext("DeleteVideoSegment");
		DeleteVideoSegmentRequest req =  new DeleteVideoSegmentRequest("location");
		for(VideoSegment vs : dao.listAllVideoSegments())
		{
			if(vs.location.equals("location"))
			{
				isTrue = true;
			}
		}
		assertTrue(isTrue);
		DeleteVideoSegmentResponse response = handler.handleRequest(req, context);
		System.out.println(response.toString());
		assertEquals(response.statusCode,200);
		isTrue = false;
		for(VideoSegment vs : dao.listAllVideoSegments())
		{
			if(vs.location.equals("location"))
			{
				isTrue = true;
			}
		}
		assertFalse(isTrue);
	}
	
	@Test
	public void throwError () {
		DeleteVideoSegmentRequest req = new DeleteVideoSegmentRequest("SomeLocationThatDoesntExist");
		DeleteVideoSegmentResponse resp = new DeleteVideoSegmentHandler().handleRequest(req, createContext("DeleteVideoSegment"));
		resp.toString();
		assertEquals(resp.statusCode, 422);
	}

}
