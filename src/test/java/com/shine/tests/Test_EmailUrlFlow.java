package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.GenerateAutoLoginToken;

public class Test_EmailUrlFlow extends TestBaseSetup{

	WebDriver _EmailUrlFlowDriver;
	WebDriverWait _Wait;
	public String autoLoginToken = "";
	String blockRecruiter_url = "";
	String jobalert_url = "";
	String accountSetting_url = "";
	String privacy_url = "";
	String VERIFY_EMAIL_URL = "";

	static String email_alert_status;
	static boolean privacy_status;
	static boolean sms_alert_flag;
	static boolean receive_other_product_info;
	static boolean recruiter_and_shine_partner_email;

	By jobAlertTitle			= By.cssSelector(".JobY");
	By accountSettingHeadTitle	= By.id("id-privacy-status-title");
	By verify_email_msg			= By.cssSelector(".login_con_div.white");


	@BeforeClass
	public void TestSetup() {
		_EmailUrlFlowDriver = getDriver(_EmailUrlFlowDriver);
		_Wait = new WebDriverWait(_EmailUrlFlowDriver, 15);
		GenerateAutoLoginToken _GenerateAutoLoginToken = new GenerateAutoLoginToken();
		autoLoginToken = _GenerateAutoLoginToken.getToken(email_new, _EmailUrlFlowDriver);
		APP_LOGS.debug("User Auto Loggedin Token: "+autoLoginToken);
		_Utility.Thread_Sleep(2000);
		
		blockRecruiter_url = baseUrl+"/myshine/block_recruiter/?tc="+autoLoginToken+"&utm_source=gmail.com&utm_medium=email&etm_content=|2018-06-04T09:00:30.115024|8viUOOg3qVCa7EEh0UMFhGzBH+E7NAauktxNZtkCXaY=|0&utm_campaign=sendJob_cdoKnfrbDlfOE4POKgXtyYMctdBWuSxMtw9WhkbzVEk=&rec=General%20Motors";
		jobalert_url = baseUrl+"/myshine/login/?tc="+autoLoginToken+"&next=/myshine/job-alerts/";
		accountSetting_url = baseUrl+"/myshine/login/?tc="+autoLoginToken+"&next=/myshine/accountsettings/&appParams={%22enb_block%22%3A%22cls_editPrivacy%22}&utm_source=gmail.com&utm_medium=email&etm_content=UpdateProfile|L1|2018-06-04T10:29:28.109867|wRrSSFkkfQeoGoa8kioY3i3m8eXMnbM2WeRVnhJamNA=|daily&utm_campaign=SimilarJob";		
		privacy_url = baseUrl+"/unsubscribe/?tc="+autoLoginToken+"&t=0&j=2&utm_source=gmail.com&utm_medium=email&etm_content=|L22|2018-05-29T13:57:03.856084|wRrSSFkkfQeoGoa8kioY3i3m8eXMnbM2WeRVnhJamNA=|5a28df0580cf2c150e807fbd&utm_campaign=profileAlert";
		VERIFY_EMAIL_URL = baseUrl+"/myshine/verify-email/"+autoLoginToken+"/";
	
		APP_LOGS.debug("blockRecruiter_url: "+blockRecruiter_url);
		APP_LOGS.debug("jobalert_url: "+jobalert_url);
		APP_LOGS.debug("accountSetting_url: "+accountSetting_url);
		APP_LOGS.debug("privacy_url: "+privacy_url);
		APP_LOGS.debug("VERIFY_EMAIL_URL "+VERIFY_EMAIL_URL);
	
	}



	/*
	 * @Test(priority=1) public void verify_block_recruiter_email_link() {
	 * _EmailUrlFlowDriver.get(blockRecruiter_url);
	 * _Utility.closeNotification(_EmailUrlFlowDriver); _Utility.Thread_Sleep(3000);
	 * check_assertion("You will no longer receive emails from General Motors. The blocked company will also not be able to search for your profile in the database."
	 * , _EmailUrlFlowDriver); getRequestAPI(baseUrl+"/api/v2/candidate/"+user_id+
	 * "/block-recruiters/","General Motors", "4602", true);
	 * deleteRecruiter("General Motors", "4602"); _Utility.Thread_Sleep(2000);
	 * getRequestAPI(baseUrl+"/api/v2/candidate/"+user_id+
	 * "/block-recruiters/","General Motors", "4602", true); }
	 */



	@Test(priority=2)
	public void verify_jobalert_email_link() {
		_EmailUrlFlowDriver.get(jobalert_url);
		_Utility.Thread_Sleep(3000);
		String actual_title = _EmailUrlFlowDriver.findElement(jobAlertTitle).getText();
		APP_LOGS.debug("Job Alert Title: "+actual_title);
		assertTrue(actual_title.contains("My Job Alerts"), "Actual title"+actual_title);
	}


	@Test(priority=3)
	public void verify_account_setting_email_link() {
		_EmailUrlFlowDriver.get(accountSetting_url);
		_Utility.Thread_Sleep(3000);
		String actual_title = _EmailUrlFlowDriver.findElement(accountSettingHeadTitle).getText();
		APP_LOGS.debug("Job Alert Title: "+actual_title);
		assertTrue(actual_title.contains("Privacy Status and Email Subscription Status"), "Actual title"+actual_title);
	}


	/*
	 * @Test(priority=4) public void verify_account_privacy_status_email_link() {
	 * _EmailUrlFlowDriver.get(privacy_url); _Utility.Thread_Sleep(3000);
	 * check_assertion("You will no longer receive Shine Alerts and Notifications",
	 * _EmailUrlFlowDriver);
	 * getRequestAPI(baseUrl+"/api/v2/candidate/"+user_id+"/alert-settings/", "",
	 * "", false); APP_LOGS.debug("Email alert status: "+email_alert_status);
	 * APP_LOGS.debug("sms_alert_flag: "+sms_alert_flag); try {
	 * assertEquals(email_alert_status, "job_alert_mails"); }catch(AssertionError
	 * ex) { assertEquals(email_alert_status, "no_emails"); }
	 * 
	 * }
	 */


	@Test(priority=5)
	public void verify_email_link() {
		_Utility.Thread_Sleep(2000);
		_EmailUrlFlowDriver.get(VERIFY_EMAIL_URL);
		_Utility.Thread_Sleep(3000);
		String actual_msg = _EmailUrlFlowDriver.findElement(By.cssSelector(".login_con_div.white h1")).getText().trim();
		assertEquals(actual_msg, "Your email has been verified");
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _EmailUrlFlowDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_EmailUrlFlowDriver!=null)
			_EmailUrlFlowDriver.quit();

	}


	/**
	 * Check Assertion
	 * @param actual_title
	 * @param _EmailUrlFlowDriver
	 */
	public void check_assertion(String actual_title, WebDriver _EmailUrlFlowDriver ) {
		actual_title = _EmailUrlFlowDriver.findElement(verify_email_msg).getText();
		APP_LOGS.debug("Alert Title: "+actual_title);
		assertTrue(actual_title.contains(actual_title), "Actual title"+actual_title);

	}




	/**
	 * 
	 * @param company_name
	 * @param company_ids
	 */
	public void deleteRecruiter(String company_name, String company_ids) {
		deleteRecruiterApi(baseUrl+"/api/v2/candidate/"+user_id+"/block-recruiters/");

	}

	/**
	 * 
	 * @param company_name
	 * @param company_ids
	 */
	public void getRecruiter(String company_name, String company_ids) {
		deleteRecruiterApi(baseUrl+"/api/v2/candidate/"+user_id+"/block-recruiters/");

	}


	/*GET API RESPONSE*/
	public boolean deleteRecruiterApi(String api_url){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			String jsonData = "{}";
			Entity<?> payload = Entity.json(jsonData);
			Response response = target.request(MediaType.APPLICATION_FORM_URLENCODED)
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Authorization", "Basic cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==")
					.header("Accept", "application/json")
					.header("referer", baseUrl+"/myshine/accountsettings/")
					.put(payload);
			String value = response.readEntity(String.class);
			APP_LOGS.info("Delete Api response" + value);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 201);
			response.close(); 
			client.close();
			return true;
		}
		catch (Exception e) {
			APP_LOGS.fatal("Api comunication error: "+e.getMessage());
			return false;
		}
	}

	/*GET API RESPONSE*/
	public void getRequestAPI(String api_url, String company_name, String company_ids, boolean isBlockRecruiter){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Authorization", "Basic cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==")
					.header("Accept", "application/json")
					.get();
			String api_response = response.readEntity(String.class);
			APP_LOGS.info("Api response" + api_response);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			response.close(); 
			client.close();
			if(isBlockRecruiter)
				jsonParser(api_response, company_name, company_ids);
			else
				jParser_alert_settings(api_response);
		}
		catch (Exception e) {
			APP_LOGS.fatal("Api comunication error: "+e.getMessage());
		}
	}


	/**
	 * JSON Parser
	 * @param api_response
	 * @param company_name
	 * @param company_ids
	 */
	private void jsonParser(String api_response, String company_name, String company_ids) {
		if(api_response.equals("{}")) {
			APP_LOGS.debug("Api Response: "+api_response);
		}
		else {
			JSONObject obj = new JSONObject(api_response);
			JSONObject jsonObject = (JSONObject) obj;
			String actual_company_name = jsonObject.get("company_name").toString();
			APP_LOGS.debug("actual_company_name: "+actual_company_name);
			String actual_company_ids = jsonObject.get("company_ids").toString();
			APP_LOGS.debug("actual_company_ids: "+actual_company_ids);
			APP_LOGS.debug("Api Response before delete"+api_response);
			assertTrue(actual_company_name.contains(company_name), actual_company_name);
			assertTrue(actual_company_ids.contains(company_ids), actual_company_ids);

		}

	}






	/*Parse json response*/
	public static void jParser_alert_settings(String response){
		try {
			JSONObject obj = new JSONObject(response);
			JSONObject jsonObject = (JSONObject) obj;
			email_alert_status = jsonObject.get("email_alert_status").toString();
			privacy_status = (boolean) jsonObject.get("privacy_status");
			sms_alert_flag = (boolean) jsonObject.get("sms_alert_flag");
			receive_other_product_info = (boolean) jsonObject.get("receive_other_product_info");
			recruiter_and_shine_partner_email = (boolean) jsonObject.get("recruiter_and_shine_partner_email");

		}
		catch(Throwable t){
			APP_LOGS.error("No data found: "+t.getMessage());
			t.printStackTrace();	
		}

	}










}
