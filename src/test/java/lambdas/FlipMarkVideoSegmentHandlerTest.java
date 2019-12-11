package lambdas;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.VideoSegmentDAO;
import entity.VideoSegment;
import http.DeleteVideoSegmentRequest;
import http.DeleteVideoSegmentResponse;
import http.MarkVideoSegmentRequest;
import http.MarkVideoSegmentResponse;

public class FlipMarkVideoSegmentHandlerTest extends LambdaTest
{
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
		dao.deleteVideoSegment("location");
		dao.close();
	}
	
	
	@Test
	public void test() throws Exception 
	{
		FlipMarkVideoSegmentHandler handler = new FlipMarkVideoSegmentHandler();
		Context context = createContext("FlipVideoSegment");
		assertFalse(dao.getVideoSegment("location").getIsMarked());
		MarkVideoSegmentRequest req =  new MarkVideoSegmentRequest("location");
		MarkVideoSegmentResponse response = handler.handleRequest(req, context);
		assertTrue(dao.getVideoSegment("location").getIsMarked());
		response = handler.handleRequest(req, context);
		assertFalse(dao.getVideoSegment("location").getIsMarked());
	}
}
