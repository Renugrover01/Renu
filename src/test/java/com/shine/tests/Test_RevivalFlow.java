package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Base64;
import java.util.HashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.shine.base.TestBaseSetup;
import com.shine.beans.CandidateProfile;

public class Test_RevivalFlow extends TestBaseSetup{

	WebDriver _RevivalFlowdriver;
	Gson _Gson = null;
	String waterMark = "";
	String uid = "";

	int current_exp_in_year;
	int current_exp_in_month;
	int current_salary_in_lakh;
	int current_salary_in_thou;


	@BeforeClass
	public void TestSetup() {
		_Gson = new Gson();
		waterMark = _Utility.get_random_string();
		_RevivalFlowdriver = getDriver(_RevivalFlowdriver);
		loggedInShine(_RevivalFlowdriver, email_new, pass_new);
		_Utility.Thread_Sleep(2000);
		_Gson = new Gson();
		JavascriptExecutor jse = (JavascriptExecutor) _RevivalFlowdriver;
		try {
			uid = (String) jse.executeScript("return sc.UID");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test (priority =1)
	public void open_revival_flow_url () {
		_RevivalFlowdriver.get(baseUrl+"/myshine/home/?utm_content=revival");
		_Utility.Thread_Sleep(2000);
		String popup_title = _RevivalFlowdriver.findElement(By.id("ui-id-1")).getText();
		assertTrue(popup_title.contains("Welcome back,"));
	}

	@Test (priority =2, dependsOnMethods= {"open_revival_flow_url"})
	public void open_revival_flow_popup () {
		String popup_title = _RevivalFlowdriver.findElement(By.id("ui-id-1")).getText();
		assertTrue(popup_title.contains("Welcome back,"));
		String popup_desc = _RevivalFlowdriver.findElement(By.cssSelector(".disqualify_box")).getText().trim();
		assertEquals(popup_desc, "Update your profile and increase recruiter views upto 3x.");

	}


	@Test(priority =3)
	public void select_salart_from_frop_down() {
		String previous_salary = _RevivalFlowdriver.findElement(By.xpath("(//*[@id='selectsalary_in_lakh'])")).getText();
		WebElement salaryList = _RevivalFlowdriver.findElement(By.xpath("(//select[@id='id_salary_in_lakh'])"));
		int salary_value = Integer.parseInt(previous_salary)+1;
		current_salary_in_lakh = salary_value;
		new Select(salaryList).selectByVisibleText(String.valueOf(salary_value));
	}


	@Test(priority =4)
	public void select_experience_from_frop_down() {
		String previous_exp = _RevivalFlowdriver.findElement(By.id("selectExperience")).getText().toLowerCase().replace("yrs", "").trim();
		WebElement expList = _RevivalFlowdriver.findElement(By.cssSelector("select#id_Experience"));
		int exp_value = Integer.parseInt(previous_exp)+1;
		current_exp_in_year = exp_value;
		new Select(expList).selectByVisibleText( String.valueOf(exp_value)+" Yrs");
	}


	@Test(priority =5)
	public void enter_company_name() {
		_RevivalFlowdriver.findElement(By.id("id_company_name")).clear();
		_RevivalFlowdriver.findElement(By.id("id_company_name")).sendKeys("HCL Technologies Pvt Ltd");
		_RevivalFlowdriver.findElement(By.id("id_company_name")).sendKeys(Keys.TAB);
	}

	@Test(priority =6)
	public void enter_job_title() {
		_RevivalFlowdriver.findElement(By.xpath("(//*[@id='id_job_title'])")).clear();
		_RevivalFlowdriver.findElement(By.xpath("(//*[@id='id_job_title'])")).sendKeys("Software Engineer "+waterMark);
		_RevivalFlowdriver.findElement(By.xpath("(//*[@id='id_job_title'])")).sendKeys(Keys.TAB);
	}

	@Test(priority =7)
	public void select_industry_from_frop_down() {
		WebElement industryList = _RevivalFlowdriver.findElement(By.cssSelector("select#id_industry"));
		new Select(industryList).selectByVisibleText("IT - Software");
	}

	@Test(priority =8)
	public void select_fa_from_frop_down() {
		WebElement faList = _RevivalFlowdriver.findElement(By.cssSelector("select#id_functional_area"));
		new Select(faList).selectByVisibleText("Application Programming / Maintenance");
	}

	@Test(priority =9)
	public void select_job_start_date() {
		WebElement monthList = _RevivalFlowdriver.findElement(By.id("id_start_month"));
		new Select(monthList).selectByVisibleText("Jan");
		WebElement yearList = _RevivalFlowdriver.findElement(By.id("id_start_year"));
		new Select(yearList).selectByVisibleText("2016");
	}

	@Test(priority =10)
	public void click_on_save_button() {
		_RevivalFlowdriver.findElement(By.id("id_saveJob")).click();
		_Utility.Thread_Sleep(2000);
	}

	@Test (priority =11, dependsOnMethods= {"click_on_save_button"})
	//@Test(priority =10)
	public void verify_employment_details() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api();
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _RevivalFlowdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_RevivalFlowdriver!=null)
			_RevivalFlowdriver.quit();

	}


	/*********************************************************************************************************/		


	/*GET API RESPONSE*/
	public void verify_profile_api(){
		try{
			APP_LOGS.debug("UID: "+uid);
			String api_url = baseUrl+"/api/v2/candidate-profiles/"+uid+"/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			APP_LOGS.debug(api_url);
			String data = email_new+":123456";	
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
			assert_response(api_result);
			response.close(); 
			client.close();
		}
		catch (Exception e) {
			APP_LOGS.debug("Error "+e.getMessage());
		}
	}

	public void assert_response(String response){
		CandidateProfile _CandidateProfile = _Gson.fromJson(response, CandidateProfile.class); 
		int flag = 0;
		for(int i=0; i<_CandidateProfile.getJobs().size(); i++) {
			String job_title = _CandidateProfile.getJobs().get(i).getJob_title();
			if(job_title.equals("Software Engineer "+waterMark)) {
				int sub_field = _CandidateProfile.getJobs().get(i).getSub_field();
				int industry_id = _CandidateProfile.getJobs().get(i).getIndustry_id();
				boolean is_current = _CandidateProfile.getJobs().get(i).isIs_current();
				String company_name = _CandidateProfile.getJobs().get(i).getCompany_name();
				int start_year = _CandidateProfile.getJobs().get(i).getStart_year();
				int start_month = _CandidateProfile.getJobs().get(i).getStart_month();
				int end_year, end_month = 0;
				try {
					end_year = _CandidateProfile.getJobs().get(i).getEnd_year();
				}catch(Throwable t) {
					APP_LOGS.debug("Handling null point on end_year");
					end_year = 0;
				}
				try {
					end_month = _CandidateProfile.getJobs().get(i).getEnd_month();
				}catch(Throwable t) {
					APP_LOGS.debug("Handling null point on end_month");
					end_month = 0;
				}
				String sub_field_display_value = _CandidateProfile.getJobs().get(i).getSub_field_display_value();
				String industry_id_display_value = _CandidateProfile.getJobs().get(i).getIndustry_id_display_value();

				Assert.assertEquals(sub_field, 4559);
				Assert.assertEquals(job_title, "Software Engineer "+waterMark);
				Assert.assertEquals(industry_id, 18);
				Assert.assertEquals(is_current, true);
				Assert.assertEquals(company_name, "HCL Technologies Pvt Ltd");
				Assert.assertEquals(start_year, 2016);
				Assert.assertEquals(start_month, 1);
				Assert.assertEquals(end_year, 0);
				Assert.assertEquals(end_month, 0);
				Assert.assertEquals(sub_field_display_value, "Application Programming / Maintenance");
				Assert.assertEquals(industry_id_display_value, "IT - Software");
				flag = 1;
				break;
			}
		}

		for(int i=0; i<_CandidateProfile.getWorkex().size(); i++) {
			int experience_in_years = _CandidateProfile.getWorkex().get(i).getExperience_in_years();
			//int experience_in_months = _CandidateProfile.getWorkex().get(i).getExperience_in_months();
			int salary_in_lakh = _CandidateProfile.getWorkex().get(i).getSalary_in_lakh();
			//int salary_in_thousand = _CandidateProfile.workex.get(i).salary_in_thousand;
			experience_in_years = experience_decoder().get(experience_in_years);
			Assert.assertEquals(experience_in_years, current_exp_in_year);
			Assert.assertEquals(salary_in_lakh, current_salary_in_lakh);

		}
		if(flag==0)
			Assert.assertTrue(false, "Assertion failed explicitly - No job found");

	}



	public HashMap<Integer,Integer> experience_decoder() {
		HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();  
		map.put(2,0);
		map.put(4,1);
		map.put(5,2);
		map.put(6,3);
		map.put(7,4);
		map.put(8,5);
		map.put(9,6);
		map.put(10,7);
		map.put(11,8);
		map.put(12,9);
		map.put(13,10);
		map.put(14,11);
		map.put(15,12);
		map.put(16,13);
		map.put(17,14);
		map.put(18,15);
		map.put(19,16);
		map.put(20,17);
		map.put(21,18);
		map.put(22,19);
		map.put(23,20);
		map.put(24,21);
		map.put(25,22);
		map.put(26,23);
		map.put(27,24);
		map.put(28,25);
		map.put(28,26);
		map.put(28,27);
		map.put(28,28);
		map.put(28,29);
		map.put(28,30);
		map.put(28,31);
		map.put(28,32);
		map.put(28,33);
		map.put(28,34);
		map.put(28,35);
		map.put(28,36);
		map.put(28,37);
		map.put(28,38);
		map.put(28,39);
		map.put(28,40);
		return map; 
	}
	
	public HashMap<Integer,Integer> salary_decoder() {
		HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
		return map;  
		
	}
}
