package com.shine.tests;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.shine.base.TestBaseSetup;
import com.shine.beans.CandidateProfile;
import com.shine.tests.Test_MyProfile;

public class Test_DesiredJobDetails extends TestBaseSetup{

	WebDriver _DesiredJobDetailsDriver;
	WebDriverWait _Wait;

	Gson _Gson = null;
	String random_String = "";
	String uid = "";
	List<Integer> industry = new ArrayList<Integer>();
	List<Integer> functional_area =  new ArrayList<Integer>();
	List<Integer> minimum_salary =  new ArrayList<Integer>();
	List<Integer> maximum_salary =  new ArrayList<Integer>();
	List<Integer> candidate_location =  new ArrayList<Integer>();
	List<Integer> job_type = new ArrayList<Integer>();
	List<Integer> shift_type =  new ArrayList<Integer>();

	By certification_head_text	= By.id("id_certifications");
	By edit_desired_job			= By.cssSelector(".cls_jpbprefedit.edit a");
	
	//Role
	
	By dj_job_role = By.id("id_job_title");
	
	
	//Location
	By dj_location_dd = By.cssSelector("div.cls_dj_loc button");
	By dj_location_sdd_bangalore = By.id("id_candidate_location_243");
	By dj_location_sdd_chennai = By.id("id_candidate_location_244");
	By dj_location_sdd_gurgaon = By.id("id_candidate_location_423");
	//FA
	By dj_fa_dd = By.cssSelector("div.cls_dj_fa button");
	By dj_fa_sdd_equity= By.id("id_functional_area_724");
	By dj_fa_sdd_finance = By.id("id_functional_area_701");
	By dj_fa_sdd_backoffice = By.id("id_functional_area_1411");
	//Industry
	By dj_ind_dd = By.cssSelector("div.cls_dj_ind button");
	By dj_ind_sdd_it= By.id("id_industry_18");
	By dj_ind_sdd_manufacturing = By.id("id_industry_23");
	By dj_ind_sdd_engineering = By.id("id_industry_9");
	//Salary
	By dj_salary_dd = By.cssSelector("div.reg_text14 button");
	By dj_salary_sdd_zero = By.id("id_minimum_salary_0");
	By dj_salary_sdd_2_3 = By.id("id_minimum_salary_6");
	//Job Type
	By dj_Job_type_dd = By.cssSelector("div.reg_text2 button");
	By dj_Job_type_sdd_full_type = By.id("id_job_type_2");
	//Shift Type
	By dj_shift_type_dd = By.id("radio2");
	By dj_shift_type_morning = By.id("id_shift_type_0");
	By dj_shift_type_noon = By.id("id_shift_type_1");
	By dj_shift_type_evening = By.id("id_shift_type_2");
	By dj_shift_type_night = By.id("id_shift_type_3");
	By dj_shift_type_split = By.id("id_shift_type_4");
	By dj_shift_type_rotating = By.id("id_shift_type_5");
	//Save
	By dj_save_button = By.cssSelector(".cls_cand_edit_job_preferences_save");
	
	By body		= By.tagName("body");


	@BeforeClass
	public void TestSetup() {
		random_String = _Utility.get_random_string();
		_Gson = new Gson();
		_DesiredJobDetailsDriver = getDriver(_DesiredJobDetailsDriver);
		loggedInShine(_DesiredJobDetailsDriver, email_main, pass_new);
		_Utility.clickOnNotification(_DesiredJobDetailsDriver);
		_Wait = new WebDriverWait(_DesiredJobDetailsDriver, 20);
		_Utility.openMenuLink(Test_MyProfile.userAccMenuLink, Test_MyProfile.myProfileLink, _DesiredJobDetailsDriver);
		JavascriptExecutor jse = (JavascriptExecutor) _DesiredJobDetailsDriver;
		try {
			uid = (String) jse.executeScript("return sc.UID");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	@Test (priority =0)
	public void verify_edit_desired_job() {
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(certification_head_text, _DesiredJobDetailsDriver);
		_DesiredJobDetailsDriver.findElement(edit_desired_job).click();
		_Utility.Thread_Sleep(2000);
	}

	
	@Test (priority =1)
	public void verify_edit_desired_job_role() {
		_Utility.Thread_Sleep(1000);
		_DesiredJobDetailsDriver.findElement(dj_job_role).clear();
		_DesiredJobDetailsDriver.findElement(dj_job_role).sendKeys("Java Software Engineer");
		_Utility.Thread_Sleep(2000);
	}


	@Test (priority =2)
	public void verify_edit_desired_job_location() {
		_Utility.Thread_Sleep(3000);
		_DesiredJobDetailsDriver.findElement(dj_location_dd).click();
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_location_sdd_bangalore).click();
		_DesiredJobDetailsDriver.findElement(dj_location_sdd_chennai).click();
		_DesiredJobDetailsDriver.findElement(dj_location_sdd_gurgaon).click();
		_Utility.Thread_Sleep(500);
		_DesiredJobDetailsDriver.findElement(dj_location_sdd_bangalore).sendKeys(Keys.ESCAPE);
	}


	@Test (priority =3)
	public void verify_edit_desired_fa() {
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_fa_dd).click();
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_fa_sdd_equity).click();
		_DesiredJobDetailsDriver.findElement(dj_fa_sdd_finance).click();
		_DesiredJobDetailsDriver.findElement(dj_fa_sdd_backoffice).click();
		_Utility.Thread_Sleep(500);
		_DesiredJobDetailsDriver.findElement(dj_fa_sdd_equity).sendKeys(Keys.ESCAPE);
	}



	@Test (priority =4)
	public void verify_edit_desired_industry() {
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_ind_dd).click();
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_ind_sdd_it).click();
		_DesiredJobDetailsDriver.findElement(dj_ind_sdd_manufacturing).click();
		_DesiredJobDetailsDriver.findElement(dj_ind_sdd_engineering).click();
		_Utility.Thread_Sleep(500);
		_DesiredJobDetailsDriver.findElement(dj_ind_sdd_it).sendKeys(Keys.ESCAPE);
	}


	@Test (priority =5)
	public void verify_edit_desired_salary() {
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(dj_ind_dd, _DesiredJobDetailsDriver);
		_DesiredJobDetailsDriver.findElement(dj_salary_dd).click();
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_salary_sdd_2_3).click();
		
	}


	@Test (priority =6)
	public void verify_edit_desired_Job_type() {
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_Job_type_dd).click();
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_Job_type_sdd_full_type).click();
	}



	@Test (priority =7)
	public void verify_edit_desired_shift_type() {
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_shift_type_dd).click();
		_Utility.Thread_Sleep(2000);
		_DesiredJobDetailsDriver.findElement(dj_shift_type_morning).click();
		_DesiredJobDetailsDriver.findElement(dj_shift_type_noon).click();
		_DesiredJobDetailsDriver.findElement(dj_shift_type_evening).click();
		_DesiredJobDetailsDriver.findElement(dj_shift_type_night).click();
		_DesiredJobDetailsDriver.findElement(dj_shift_type_split).click();
		_DesiredJobDetailsDriver.findElement(dj_shift_type_rotating).click();
	}



	@Test (priority =8)
	public void verify_save_desired_job() {
		_Utility.Thread_Sleep(1000);
		_DesiredJobDetailsDriver.findElement(dj_save_button).click();
		_Utility.Thread_Sleep(3000);
		_Wait.until(ExpectedConditions.invisibilityOfElementLocated(dj_save_button));
	}

	@Test (priority =9, dependsOnMethods= {"verify_save_desired_job"})
	public void verify_desired_job() {
		verify_profile_api("desired_job");
	}
	
	
	@Test (priority =10)
	public void verify_desired_job_location_api() {
		Assert.assertTrue(String.valueOf(candidate_location).contains("[243, 244, 423]"), String.valueOf(candidate_location));
		
	}


	@Test (priority =11)
	public void verify_desired_fa_api() {
		Assert.assertTrue(String.valueOf(functional_area).contains("[724, 701, 1411]"), String.valueOf(functional_area));
		
	}



	@Test (priority =12)
	public void verify_desired_industry_api() {
		Assert.assertTrue(String.valueOf(industry).contains("[9, 18, 23]"), String.valueOf(industry));
		
	}


	@Test (priority =13)
	public void verify_desired_salary_api() {
		Assert.assertTrue(String.valueOf(minimum_salary).contains("[6]"), String.valueOf(minimum_salary));
		Assert.assertTrue(String.valueOf(maximum_salary).contains("[6]"), String.valueOf(maximum_salary));	
		
		
	}


	@Test (priority =14)
	public void verify_desired_Job_type_api() {
		Assert.assertTrue(String.valueOf(job_type).contains("[2]"), String.valueOf(job_type));
		
	}



	@Test (priority =15)
	public void verify_desired_shift_type_api() {
		Assert.assertTrue(String.valueOf(shift_type).contains("[1, 2, 3, 4, 5, 6]"), String.valueOf(shift_type));
		
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _DesiredJobDetailsDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_DesiredJobDetailsDriver!=null)
			_DesiredJobDetailsDriver.quit();

	}



	/***************************************************************************************/		


	/*GET API RESPONSE*/
	public void verify_profile_api(String sectionName){
		try{
			APP_LOGS.debug("UID: "+uid);
			String api_url = baseUrl+"/api/v2/candidate-profiles/"+uid+"/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			APP_LOGS.debug(api_url);
			String data = email_main+":123456";	
			String encodedBytes = Base64.getEncoder().encodeToString((data).getBytes("UTF-8"));
			APP_LOGS.debug("encodedBytes " + encodedBytes);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Authorization", "Basic "+encodedBytes)
					.header("Accept", "application/json")
					.get();
			//cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==
			String api_result = response.readEntity(String.class);
			APP_LOGS.debug("Job Alert Api Response: "+api_result);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			assert_response(api_result, sectionName);
			response.close(); 
			client.close();
		}
		catch (Exception e) {
			APP_LOGS.debug("Errot: "+e.getMessage());
		}
	}

	/*Parse json response*/
	public void assert_response(String response, String sectionName){
		CandidateProfile _CandidateProfile = _Gson.fromJson(response, CandidateProfile.class);
		int flag = 0;
		for(int i=0; i<_CandidateProfile.getDesired_job().size();) {
			industry = _CandidateProfile.getDesired_job().get(i).getIndustry();
			APP_LOGS.debug("Industry: "+industry);
			Collections.sort(industry);
			APP_LOGS.debug("Industry after sort: "+industry);
			functional_area = _CandidateProfile.getDesired_job().get(i).getFunctional_area();
			APP_LOGS.debug("functional_area: "+functional_area);
			Collections.sort(functional_area);
			APP_LOGS.debug("functional_area after sort: "+functional_area);
			minimum_salary = _CandidateProfile.getDesired_job().get(i).getMinimum_salary();
			APP_LOGS.debug("minimum_salary: "+minimum_salary);
			maximum_salary = _CandidateProfile.getDesired_job().get(i).getMaximum_salary();
			APP_LOGS.debug("maximum_salary: "+maximum_salary);
			candidate_location = _CandidateProfile.getDesired_job().get(i).getCandidate_location();
			APP_LOGS.debug("candidate_location: "+candidate_location);
			Collections.sort(candidate_location);
			APP_LOGS.debug("candidate_location after sort: "+candidate_location);
			job_type = _CandidateProfile.getDesired_job().get(i).getJob_type();
			APP_LOGS.debug("job_type: "+job_type);
			Collections.sort(job_type);
			APP_LOGS.debug("job_type after sort: "+job_type);
			shift_type = _CandidateProfile.getDesired_job().get(i).getShift_type();
			APP_LOGS.debug("shift_type: "+shift_type);
			Collections.sort(shift_type);
			APP_LOGS.debug("shift_type after sort: "+shift_type);
			
			flag = 1;
			break;
		}

		if(flag==0)
			Assert.assertTrue(false, "Assertion failed explicitly - No pre-filled skill found");



	}

}


