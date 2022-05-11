package com.shine.tests;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_VerifyResume extends TestBaseSetup{

	WebDriver _VerifyResumeDriver;
	static WebDriverWait _Wait;
	String api_url = "";
	String previous_resume_id = "";

	@BeforeClass
	public void TestSetup() {
		_VerifyResumeDriver = getDriver(_VerifyResumeDriver);
		api_url = baseUrl+"/api/v2/candidate/"+user_id+"/resumes/";
		_Wait = new WebDriverWait(_VerifyResumeDriver, 15);
	}

	@Test(priority=0)
	public void get_previous_default_resume() {
		previous_resume_id = get_CurrentDefaultResumeID();
		System.out.println(previous_resume_id);
	}

	String cid = "";

	@Test(priority=1)
	public void apply_with_different_resume() {
		loggedInShine(_VerifyResumeDriver, email_new, pass_new);
		//checkDontAskCheckbox(_VerifyResumeDriver);
		_Utility.clickOnNotification(_VerifyResumeDriver);
		_Utility.Thread_Sleep(2000);
		Test_JobApply.searchForJob("Genpact", _VerifyResumeDriver);
		_Utility.Thread_Sleep(3000);
		List<WebElement> apply1 = _VerifyResumeDriver.findElements(Test_JobApply.jsrpApplyBtn);
		_Utility.scrollTOElement(apply1.get(1), _VerifyResumeDriver);
		_Utility.elementDisplayPropertySetter("none", "id_filters", _VerifyResumeDriver);
		apply1.get(1).click();
		_Utility.Thread_Sleep(3000);
		List<WebElement> resumeList = _VerifyResumeDriver.findElements(By.cssSelector("#id_resumeList li"));
		if(resumeList.size()>1) {
			for(WebElement rlist: resumeList) {
				String resume = rlist.getText();
				if(!resume.contains("Default")) {
					WebElement changeResume = rlist.findElement(By.tagName("input"));
					cid = changeResume.getAttribute("value");
					APP_LOGS.debug(changeResume.getAttribute("value"));
					changeResume.click();
					_Utility.Thread_Sleep(2000);
					_VerifyResumeDriver.findElement(By.id("id_dontAskForResumeFromPopup")).click();
					_Utility.Thread_Sleep(2000);
					break;
				}
			}
			_Utility.Thread_Sleep(2000);
			_VerifyResumeDriver.findElement(By.cssSelector(".cls_upd_apply.upload_btn")).click();
			_Utility.Thread_Sleep(2000);
			String current_resume_id = get_CurrentDefaultResumeID();
			APP_LOGS.debug("Current Resume id: "+current_resume_id+" : Previous Resume id: "+previous_resume_id);
			Assert.assertNotEquals(current_resume_id, previous_resume_id);
			Assert.assertEquals(current_resume_id, cid);
		}

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _VerifyResumeDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_VerifyResumeDriver!=null)
			_VerifyResumeDriver.quit();
	}



	public void checkDontAskCheckbox(WebDriver _VerifyResumeDriver) {
		_Utility.Thread_Sleep(1000);
		_VerifyResumeDriver.navigate().to(baseUrl + "/myshine/myprofile/");
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(Test_JobApply.dontAskResumeCheckBox));
		WebElement checkbox = _VerifyResumeDriver.findElement(Test_JobApply.dontAskResumeCheckBox);
		if(checkbox.isSelected()) {
			APP_LOGS.debug("Is Check Box Selected: "+checkbox.isSelected());
			_VerifyResumeDriver.findElement(Test_JobApply.dontAskResumeCheckBox).click();
			_VerifyResumeDriver.findElement(Test_JobApply.savedontAskResumeCheckBox).click();
			_Utility.Thread_Sleep(1000);
			_Utility.dismiss_Alert(_VerifyResumeDriver);
			_Utility.Thread_Sleep(1000);
		}

	}

	/*GET API RESPONSE*/
	public String get_CurrentDefaultResumeID(){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Authorization", "Basic bWFudmkuYWdhcndhbDE5MTAxOEBnbWFpbC5jb206MTIzNDU2")
					.header("Accept", "application/json")
					.get();
			String value = response.readEntity(String.class);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			String count =jParser(value);
			response.close(); 
			client.close();
			return count;
		}
		catch (Exception e) {
			APP_LOGS.fatal("Api comunication error: "+e.getMessage());
			return null;
		}
	}

	/*Parse json response*/
	public String jParser(String response){
		String resume_id = null;

		try {
			JSONArray jsonArray = new JSONArray(response);
			APP_LOGS.debug(jsonArray);
			for(int i=0; i<jsonArray.length();i++) {
				System.err.println(jsonArray.get(i));
				JSONObject jobj = (JSONObject) jsonArray.get(i);
				int isDefault = jobj.getInt("is_default");
				System.err.println(isDefault);
				if(isDefault==1) {
					resume_id = jobj.get("id").toString();
					return resume_id;
				}
			}

		}
		catch(Throwable t){
			APP_LOGS.error("No Notification update found: "+t.getMessage());
			t.printStackTrace();	
		}
		return resume_id;	

	}



}
