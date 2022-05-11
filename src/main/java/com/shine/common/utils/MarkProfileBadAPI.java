package com.shine.common.utils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;

import com.shine.base.TestBaseSetup;

public class MarkProfileBadAPI extends TestBaseSetup{

	String key = ""; 
	String secret = "";

	public boolean set_profile_bad_api(String email, String access_token) {
		try {
			String api_url = baseUrl+"/api/v2/candidate/test-mark-bad-profile/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			String json = "{\"emails\":\""+email+"\"}";
			Entity<?> payload = Entity.json(json);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					//.header("Authorization", "Basic cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==")
					.header("Accept", "application/json")
					.header("Client-Access-Token", access_token)
					.post(payload);
			int status = response.getStatus();
			APP_LOGS.debug("API status: "+status);
			String api_result = response.readEntity(String.class);
			APP_LOGS.debug("API Resposne: "+api_result);
			response.close();
			client.close();
			if (status == 201 ||status == 200) {
				return true;
			}
		} catch (Exception ex) {
			APP_LOGS.debug("EMAIL: "+email);
			APP_LOGS.debug("error mark_profile_bad_api: "+ex.getMessage());
			return false;
		}
		return false;
	}



	/*GET API RESPONSE*/
	public String get_access_token(){
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
		catch(Exception ex){
			APP_LOGS.debug("error in get_access_token: "+ex.getMessage());
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
		catch(Exception ex){
			APP_LOGS.debug("error "+ex.getMessage());
			data = "";
		}
		return data;	


	}


}
