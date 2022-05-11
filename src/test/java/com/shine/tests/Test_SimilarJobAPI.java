package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.shine.base.TestBaseSetup;
import com.shine.beans.Search;

public class Test_SimilarJobAPI extends TestBaseSetup{
	WebDriver _SimilarJobDriver = null;
	WebDriverWait _Wait;

	String search_job_api = "";
	String similar_job_api ="";
	static String key = ""; 
	static String secret = "";
	static String access_token = "";
	static String user_token = "";

	@BeforeClass
	public void TestSetup() {
		String mobile_url = "";

		if(baseUrl.contains("pp-www.shine"))
			mobile_url = "https://pp-m.shine.com";
		else if(baseUrl.contains("https://www.shine"))
			mobile_url = "https://m.shine.com";
		else
			mobile_url = "https://sm.shine.com";
		search_job_api = mobile_url+"/api/v2/search/candidate/"+user_id+"/simple/?q=it-software-jobs&perpage=2000&sort=1";
		similar_job_api= mobile_url+"/api/v2/search/candidate/"+user_id+"/similar/?_=1543406760696&fl=id,jJobType,jJT,jTypeC,jEType&format=json&no_facets=True&perpage=7&";
		access_token = get_access_token();
		user_token = get_user_access_token(email_new, pass_new);
		APP_LOGS.info("access token: "+access_token);

	}

	ArrayList<String> contractual_job_list = new ArrayList<>();
	ArrayList<String> internship_job_list = new ArrayList<>();
	ArrayList<String> wfh_job_list = new ArrayList<>();
	ArrayList<String> regular_job_list = new ArrayList<>();


	@Test(priority=0)
	public void test_contractual_jobs(){
		String url = search_job_api+"&emp_type=2";
		System.out.println(url);
		String response = getAPIResponse(url);

		if(response!=null) {
			contractual_job_list = searchAPIResponseParser(response);
		}
		System.out.println(contractual_job_list);
	}

	@Test(priority=1, dependsOnMethods= {"test_contractual_jobs"})
	public void verify_contractual_jobs(){
		for(int i=0;i<contractual_job_list.size();i++) {
			String response = getAPIResponseWithAuth(similar_job_api+"jobid="+contractual_job_list.get(i));
			if(response!=null) {
				parseSimilarJobResponse(response, 2, contractual_job_list.get(i), "contractual");
			}
		}
	}


	@Test(priority=2)
	public void test_internship_jobs(){
		String url = search_job_api+"&emp_type=3";
		System.out.println(url);
		String response = getAPIResponse(url);

		if(response!=null) {
			internship_job_list = searchAPIResponseParser(response);
		}
		System.out.println(internship_job_list);
	}

	@Test(priority=3, dependsOnMethods= {"test_internship_jobs"})
	public void verify_internship_jobs(){
		for(int i=0;i<internship_job_list.size();i++) {
			String response = getAPIResponseWithAuth(similar_job_api+"jobid="+internship_job_list.get(i));
			if(response!=null) {
				parseSimilarJobResponse(response, 3, internship_job_list.get(i), "internship");
			}
		}
	}




	@Test(priority=4)
	public void test_wfh_job_jobs(){
		String url = search_job_api+"&emp_type=4";
		System.out.println(url);
		String response = getAPIResponse(url);

		if(response!=null) {
			wfh_job_list = searchAPIResponseParser(response);
		}
		System.out.println(contractual_job_list);
	}

	@Test(priority=5, dependsOnMethods= {"test_wfh_job_jobs"})
	public void verify_wfh_job_jobs(){
		for(int i=0;i<wfh_job_list.size();i++) {
			String response = getAPIResponseWithAuth(similar_job_api+"jobid="+wfh_job_list.get(i));
			if(response!=null) {
				parseSimilarJobResponse(response, 4, wfh_job_list.get(i), "wfh");
			}
		}
	}


	/*@Test(priority=6)
	public void test_regular_jobs(){
		String url = search_job_api+"&emp_type=1";
		System.out.println(url);
		String response = getAPIResponse(url);

		if(response!=null) {
			regular_job_list = searchAPIResponseParser(response);
		}
		System.out.println(regular_job_list);
	}

	@Test(priority=7, dependsOnMethods= {"test_regular_jobs"})
	public void verify_regular_jobs(){
		for(int i=0;i<regular_job_list.size();i++) {
			String response = getAPIResponseWithAuth(similar_job_api+"jobid="+regular_job_list.get(i));
			if(response!=null) {
				parseSimilarJobResponse(response, 1, regular_job_list.get(i), "regular");
			}
		}
	}*/





	/**
	 * 
	 * @param job_type
	 */
	private void parseSimilarJobResponse(String response, int jEType, String forJobId, String job_type) {
		APP_LOGS.debug("********* "+job_type+" : "+forJobId+" ************");
		Gson _Gson = new Gson();
		Search _Search = _Gson.fromJson(response, Search.class);
		ArrayList<Integer> actual_jEType = new ArrayList<>();
		for (int i = 0; i < _Search.getResults().size(); i++) {
			actual_jEType.add(_Search.getResults().get(i).getjEType());
			String jobid = _Search.getResults().get(i).getId();
			APP_LOGS.debug(i+" Job employment type "+jobid+" :"+actual_jEType);
		}
		String actual_value = String.valueOf(actual_jEType);
		APP_LOGS.debug("For = "+job_type+" >> "+forJobId+" = Actual Job employment type =  "+actual_value);
		APP_LOGS.debug("For = "+job_type+" >> "+forJobId+" = Expected Job employment type = ["+jEType+", "+jEType+", "+jEType+", "+jEType+", "+jEType+", "+jEType+", "+jEType+"]");
		try {
			assertEquals(actual_value, "["+jEType+", "+jEType+", "+jEType+", "+jEType+", "+jEType+", "+jEType+", "+jEType+"]");
		}
		catch(AssertionError aerr) {
			APP_LOGS.debug("For = "+job_type+" >> "+forJobId+" >> Assertion error >> "+aerr.getMessage());
			if(job_type.contains("regular")&&actual_value.contains("1")) {
				assertFalse(actual_value.contains("2"), actual_value);
				assertFalse(actual_value.contains("3"), actual_value);
				assertFalse(actual_value.contains("4"), actual_value);
			}
			else if(job_type.contains("contractual")&&actual_value.contains("2")) {
				assertFalse(actual_value.contains("1"), actual_value);
				assertFalse(actual_value.contains("3"), actual_value);
				assertFalse(actual_value.contains("4"), actual_value);
			}
			else if(job_type.contains("internship")&&actual_value.contains("3")) {
				assertFalse(actual_value.contains("1"), actual_value);
				assertFalse(actual_value.contains("2"), actual_value);
				assertFalse(actual_value.contains("4"), actual_value);

			}
			else if(job_type.contains("wfh")&&actual_value.contains("4")) {
				assertFalse(actual_value.contains("1"), actual_value);
				assertFalse(actual_value.contains("2"), actual_value);
				assertFalse(actual_value.contains("3"), actual_value);
			}
			else 
				assertFalse(actual_value.isEmpty(), actual_value);
			APP_LOGS.debug("************************");
		}
	}

	/*GET API RESPONSE*/
	public static String getAPIResponse(String api_url){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.header("Client-Access-Token", access_token)
					.header("User-Access-Token", user_token)
					.header("User-Agent", mobile_user_agent)
					.get();
			String res = response.readEntity(String.class);
			int status = response.getStatus();
			Assert.assertEquals(status, 200);
			response.close(); 
			client.close();
			return res;
		}
		catch (Exception e) {
			return null;
		}
	}
	/*GET API RESPONSE*/
	public static String getAPIResponseWithAuth(String api_url){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json; charset=utf-8")
					.header("Accept", "application/json")
					//.header("Authorization", "Basic cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==")
					.header("Client-Access-Token", access_token)
					.header("User-Access-Token", user_token)
					.header("User-Agent", mobile_user_agent)
					.get();
			String res = response.readEntity(String.class);
			int status = response.getStatus();
			Assert.assertEquals(status, 200);
			response.close(); 
			client.close();
			return res;
		}
		catch (Exception e) {
			return null;
		}
	}


	/**
	 * Parse Search API Response
	 * @param response
	 * @return
	 */
	private ArrayList<String> searchAPIResponseParser(String response) {
		ArrayList<String> jobList = new ArrayList<>();
		Gson _Gson = new Gson();
		Search _Search = _Gson.fromJson(response, Search.class);
		for (int i = 0; i < _Search.getResults().size(); i++) {
			String id = _Search.getResults().get(i).getId();
			APP_LOGS.debug("Job id "+i+" :"+id);
			jobList.add(id);
		}
		return jobList;
	}



	/*GET API RESPONSE*/
	public static String get_access_token(){
		try{
			String api_url = baseUrl+"/api/v2/client/access/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			if(baseUrl.contains("sumosc")||baseUrl.contains("sm")||baseUrl.contains("172.")) {
				key = "EgbwTQyHpXy5BhYUE0SBkvCmcKOFcZMStcKlKLwemAs";
				secret = "YyXDWOddaovu5MwphGh75x7CYSvgSZHzTSsRPiW2vSw";
			}
			else {
				key = "oyBAGl6hI3QWdEq0f0TzQ98Pr8BFKYXM5e29dr27LX4";
				secret = "BLYBxMhY0RI3FR6DrIwGK9L5F28ygoZxJavKDWBoHAU";
			}
			String json = "{\"key\":\""+key+"\",\"secret\":\""+secret+"\"}";
			APP_LOGS.debug(json);
			Entity<?> payload = Entity.json(json);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.post(payload);
			int status = response.getStatus();
			APP_LOGS.debug("API status: "+status);
			String api_result = response.readEntity(String.class);
			APP_LOGS.debug("API Resposne: "+api_result);
			response.close(); 
			client.close();
			if(status == 201) {
				return parse_token(api_result);
			}
		}
		catch(Throwable t){	
			t.printStackTrace();
		}
		return "";
	}


	public static String  parse_token(String response) {
		String data = "";

		try {
			JSONObject obj = new JSONObject(response);
			JSONObject jsonObject = (JSONObject) obj;
			String access_token = jsonObject.getString("access_token");
			APP_LOGS.debug("JSon Parser Resposne: "+access_token);
			return access_token;					
		}
		catch(Throwable t){	
			APP_LOGS.debug("error "+t.getMessage());
			data = "";
		}
		return data;	


	}

	public static String get_user_access_token(String email, String password) {
		try {
			String api_url = baseUrl+"/api/v2/user/access/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			String json = "{\"email\":\""+email+"\",\"password\":\""+password+"\"}";;
			Entity<?> payload = Entity.json(json);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json; charset=utf-8")
					//.header("Authorization", "Basic cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==")
					.header("Accept", "application/json")
					.post(payload);
			int status = response.getStatus();
			APP_LOGS.debug("API status: "+status);
			String api_result = response.readEntity(String.class);
			APP_LOGS.debug("API Resposne: "+api_result);
			response.close();
			client.close();
			return parser(api_result);

		} catch (Throwable t) {
			APP_LOGS.debug("EMAIL: "+email);
			APP_LOGS.debug("error mark_profile_bad_api: "+t.getMessage());
			return "";
		}
	}

	private static String parser(String api_result) {
		String data = "";

		try {
			JSONObject obj = new JSONObject(api_result);
			JSONObject jsonObject = (JSONObject) obj;
			String access_token = jsonObject.getString("access_token");
			APP_LOGS.debug("JSon Parser Resposne: "+access_token);
			return access_token;					
		}
		catch(Throwable t){	
			APP_LOGS.debug("error "+t.getMessage());
			data = "";
		}
		return data;	
	}



}
