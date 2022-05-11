package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test_IVRCall extends TestBaseSetup{

	String ivr_url = "";


	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the IVR Call test");

	}

	@Test (priority =1, dataProvider="ivrData")
	public void testRobots (String mobileNumber){
		ivr_url = "http://cloud-voice.solutionsinfini.com/shine/obd_new/receive.php?userid=5a2838ca80cf2c39a5edcd34&retry=2&number="+mobileNumber;
		String res = getAPIRrequest(ivr_url);
		verify_api_response(res, mobileNumber);
	}

	

	@DataProvider
	public Object [][] ivrData(){
		Object [][] data= new Object [1][1];

		data[0][0]="9717680353";

		return data;

	}


	/**
	 * 
	 * @param url
	 * @return
	 */
	public String getAPIRrequest(String url) {
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(url)
					.build();
			Response response;

			response = client.newCall(request).execute();
			String res = response.body().string();
			APP_LOGS.debug(res);
			response.close();
			return res;
		} catch (IOException e) {
			APP_LOGS.fatal("API Error: "+e.getMessage());
			return "";
		}


	}
	
	/**
	 * 
	 * @param res
	 * @param expected_mobileno
	 */
	private void verify_api_response(String res, String expected_mobileno) {
		JSONObject resJSon = new JSONObject(res);
		String status = resJSon.getString("status");
		String message = resJSon.getString("message");
		APP_LOGS.debug("Status: "+status);
		APP_LOGS.debug("Message: "+message);
		JSONObject data =  resJSon.getJSONObject("data");
		JSONObject dataObject = data.getJSONObject("0");
		String actual_mobileno = dataObject.get("mobile").toString();
		String sent_status = dataObject.get("status").toString();
		//Check For assertion
		assertEquals(status, "200");
		assertEquals(message, "OK");
		assertEquals(actual_mobileno, expected_mobileno);
		assertEquals(sent_status, "SENT");
	
	}
}