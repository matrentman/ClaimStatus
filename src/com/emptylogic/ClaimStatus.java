package com.emptylogic;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;

@Path("/claim")
public class ClaimStatus {
	@Path("/inquiry")
	@POST
	@Consumes("text/plain")
	@Produces("application/json")
	public Response transmitClaimInquiry(String claim) throws JSONException 
	{
		HapiContext context = new DefaultHapiContext();
		Parser p = context.getGenericParser();
		Message hapiMessage = null;
		try {
			hapiMessage = p.parse(claim);
		} catch (EncodingNotSupportedException e) {
			e.printStackTrace();
		} catch (HL7Exception e) {
			e.printStackTrace();
		}
		
		Message ackMessage = null;
		try {
			ackMessage = hapiMessage.generateACK();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HL7Exception e) {
			e.printStackTrace();
		}
		
		ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		es.submit(new InquiryRunner());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ack", ackMessage);
		String result = "" + jsonObject;
		
		return Response.status(200).entity(result).build();
	}
}
