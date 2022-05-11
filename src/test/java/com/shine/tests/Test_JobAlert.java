package com.shine.tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.shine.base.TestBaseSetup;

public class Test_JobAlert extends TestBaseSetup{

	static WebDriver _JobAlertDriver;
	static WebDriverWait _Wait;

	static String randomSkill[] = null;
	private static Random rand = new Random();
	private static volatile SecureRandom numberGenerator = null;
	String skillName = "";
	String skillNameNL = "";

	String uid = "";
	String job_alert_emailid = "";

	By moreMenuLink		  = By.partialLinkText("More");
	By jobAlertMenuLink	  = By.linkText("My Job Alerts");
	//By showmorebtn      = By.cssSelector(".createAlert.cls_form_createAlert #id_showMore_anchor");
	By showmorebtn        = By.id("id_showMore_anchor");
	By jobAlertLink       = By.partialLinkText("My Job Alerts");
	By createJobAlertLink = By.cssSelector("a.yellowbutton");
	By jobAlertName       = By.name("name");
	By jobAlertKeyword    = By.id("id_keywords");

	By salaryDropDown     = By.name("sal");
	By expDropDown        = By.id("selectexp");
	By expSubDropDown     = By.name("exp");
	By msChoice 		  = By.className("ms-choice");
	By createJobAlert     = By.linkText("Create my job alert");

	By saveChanges  	  = By.linkText("Save changes");

	By deleteJobAlert     = By.cssSelector(".cls_deletealert");
	By confirmDeleteJA    = By.cssSelector("input.okbutton.cls_custom_jobalert_delete");
	By editJobAlert 	  = By.cssSelector("[class='editalert1']");

	By editTitle = By.cssSelector("[class='cls_alert_name']");



	@BeforeClass
	public void TestSetup() {
		job_alert_emailid =  email_main;
		_JobAlertDriver = getDriver(_JobAlertDriver);
		_Wait = new WebDriverWait(_JobAlertDriver, 20);
		_JobAlertDriver.manage().deleteAllCookies();
	}  

	@Test(priority = 0)
	public void test_jsrp_loggedout_location_check() {
		_Utility.Thread_Sleep(4000);
		_JobAlertDriver.get(baseUrl+"/job-search/jobs-in-delhi");
		JavascriptExecutor jse = (JavascriptExecutor) _JobAlertDriver;
		APP_LOGS.debug("Job alert link: "+jse.executeScript("return document.getElementsByClassName('cls_get_inbox')[0]"));
		if(jse.executeScript("return document.getElementsByClassName('cls_get_inbox')[0]")==null) {
			Assert.assertTrue(true);
		}
		else
			Assert.assertTrue(false);


	}

	@Test (priority=1)
	public void create_job_alert_nonloggedin(){
		_Utility.Thread_Sleep(3000);
		_JobAlertDriver.navigate().to(baseUrl+"/free-job-alerts/");
		_Utility.Thread_Sleep(4000);
		String skill = generateRandomSkill(getSkillList());
		create_edit_job_alert(_JobAlertDriver, skill, false, false);
		_Utility.Thread_Sleep(2000);
		String actual_msg = _JobAlertDriver.findElement(By.cssSelector(".aler1")).getText().trim();
		Assert.assertEquals(actual_msg, "Job Alert \""+skill+"\" created.\n\nYour email \""+job_alert_emailid+"\" is already registered with Shine. Login Now to see your job alerts in your account.");
	}

	/*
	 * @Test (priority=2) public void create_jsrp_job_alert_nonloggedin(){
	 * skillNameNL = fill_jobalert_popup(_JobAlertDriver, true); String actual_msg =
	 * _JobAlertDriver.findElement(By.
	 * cssSelector(".cls_alertresponse li:nth-child(1)")).getText().trim();
	 * Assert.assertEquals(actual_msg,
	 * "Your Job Alert \""+skillNameNL+"\" has been saved successfully!"); }
	 * 
	 * @Test (priority=3) public void verify_jsrp_job_alert_nonloggedin(){
	 * fill_jobalert_popup(_JobAlertDriver, false); _Utility.Thread_Sleep(3000);
	 * String actual_msg = _JobAlertDriver.findElement(By.
	 * cssSelector(".cls_alertresponse li:nth-child(1)")).getText().trim();
	 * Assert.assertEquals(actual_msg, "Sorry ! Alert with name \""
	 * +skillNameNL+"\" already exists against your email id "
	 * +job_alert_emailid+". Please choose some other name."); }
	 */


	@Test (priority=4)
	public void test_create_job_alert(){
		open_job_alert_section(_JobAlertDriver, true);
		_JobAlertDriver.navigate().to(baseUrl+"/myshine/job-alerts/");
		_Utility.Thread_Sleep(4000);
		skillName = generateRandomSkill(getSkillList());
		create_edit_job_alert(_JobAlertDriver, skillName, false, true);
		Assert.assertEquals(_JobAlertDriver.getTitle(), "Manage Your Job Alerts");
	}

	@Test (priority=5, dependsOnMethods= {"test_create_job_alert"})
	public void verify_created_job_alert(){
		verify_job_alerts(false);
	}


	@Test (priority=6, dependsOnMethods= {"test_create_job_alert"})
	public void test_edit_job_alert() {  
		_Utility.Thread_Sleep(3000);
		create_edit_job_alert(_JobAlertDriver, skillName, true, true);
	}

	@Test (priority=7, dependsOnMethods= {"test_edit_job_alert"})
	public void verify_edit_job_alert(){
		verify_job_alerts(true);
	}

	/*
	 * @Test(priority=8) public void test_delete_JobAlert(){ WebDriverWait wait1 =
	 * new WebDriverWait(_JobAlertDriver, 30);
	 * wait1.until(ExpectedConditions.elementToBeClickable(deleteJobAlert));
	 * _JobAlertDriver.findElement(deleteJobAlert).click();
	 * _JobAlertDriver.findElement(confirmDeleteJA).click();
	 * 
	 * }
	 * 
	 * 
	 * @Test(priority = 9, dependsOnMethods= {"test_delete_JobAlert"}) public void
	 * test_jsrp_loggedin_popup() { _Utility.Thread_Sleep(4000);
	 * _JobAlertDriver.get(baseUrl+"/myshine/job-search/software-developer-jobs");
	 * skillNameNL = fill_loggedin_job_alert(_JobAlertDriver, true); String
	 * actual_msg = _JobAlertDriver.findElement(By.
	 * cssSelector(".cls_alertresponse li:nth-child(1)")).getText().trim();
	 * Assert.assertEquals(actual_msg,
	 * "Your Job Alert \""+skillNameNL+"\" has been saved successfully!");
	 * 
	 * }
	 */


	@Test(priority = 10)
	public void test_jsrp_loggedin_location_check() {
		_Utility.Thread_Sleep(4000);
		_JobAlertDriver.get(baseUrl+"/myshine/job-search/jobs-in-delhi");
		JavascriptExecutor jse = (JavascriptExecutor) _JobAlertDriver;
		APP_LOGS.debug("Job alert link: "+jse.executeScript("return document.getElementsByClassName('cls_get_inbox')[0]"));
		if(jse.executeScript("return document.getElementsByClassName('cls_get_inbox')[0]")==null) {
			Assert.assertTrue(true);
		}
		else
			Assert.assertTrue(false);


	}



	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_JobAlertDriver!=null)
			_JobAlertDriver.quit();

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _JobAlertDriver);
	}


	/*********************************************************************************************************/

	/**
	 * 
	 * @param _JobAlertDriver
	 * @return
	 */
	public String fill_jobalert_popup(WebDriver _JobAlertDriver, boolean flag) {
		_JobAlertDriver.navigate().to(baseUrl+"/job-search/insurance-jobs");
		_Utility.Thread_Sleep(2000);
		_Utility.closeNotification(_JobAlertDriver);
		_Utility.Thread_Sleep(1000);
		_JobAlertDriver.findElement(By.cssSelector(".jsrpCount a.cls_get_inbox")).click();
		if(flag == true)
			skillNameNL = generateRandomSkill(getSkillList());
		_JobAlertDriver.findElement(By.id("id_name")).sendKeys(skillNameNL);
		_JobAlertDriver.findElement(By.id("id_mail")).sendKeys(job_alert_emailid);
		_JobAlertDriver.findElement(By.cssSelector(".cls_jobalert.yellowbutton")).click();
		_Utility.Thread_Sleep(3000);
		return skillNameNL;
	}

	public String fill_loggedin_job_alert(WebDriver _JobAlertDriver, boolean flag) {
		_JobAlertDriver.findElement(By.cssSelector(".cls_get_inbox")).click();
		_Utility.Thread_Sleep(1000);
		String name = generateRandomSkill(getSkillList());
		_JobAlertDriver.findElement(By.cssSelector(".cls_alertname")).sendKeys(name);
		_JobAlertDriver.findElement(By.cssSelector(".cls_jobalert.yellowbutton")).click();
		_Utility.Thread_Sleep(3000);
		return name;
	}




	/**
	 * 
	 * @param _JobAlertDriver
	 * @param isLoggedIn
	 */
	public void open_job_alert_section(WebDriver _JobAlertDriver, boolean isLoggedIn ){
		if(isLoggedIn) {
			loggedInShine(_JobAlertDriver, job_alert_emailid, pass_new);
			_Utility.Thread_Sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) _JobAlertDriver;
			try {
				uid = (String) jse.executeScript("return sc.UID");
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		_Utility.Thread_Sleep(3000);
		_Utility.clickOnNotification(_JobAlertDriver);
		_Utility.openMenuLink(moreMenuLink, jobAlertMenuLink, _JobAlertDriver);
	}




	/**
	 * create job alert
	 * @param _JobAlertDriver
	 * @param randomSkills
	 * @param editFlag
	 */
	public void create_edit_job_alert(WebDriver _JobAlertDriver, String randomSkills, boolean editFlag, boolean isLoggedIn) {
		//click on the create new job alert link
		_Utility.Thread_Sleep(5000);
		if(editFlag==true) {
			List<WebElement>jobAlertList = _JobAlertDriver.findElements(editJobAlert);
			for(int i=0; i<jobAlertList.size();i++) {
				String skillTitle =  _JobAlertDriver.findElements(editTitle).get(i).getText();
				if(skillTitle.contains(skillName)) {
					_Utility.scrollTOElement(jobAlertList.get(i), _JobAlertDriver);
					jobAlertList.get(i).click();
					break;
				}
			}

			_Utility.Thread_Sleep(3000);
			//_Utility.scrollTOElement(jobAlertKeyword, _JobAlertDriver);
			fill_job_alerts(randomSkills, true);
			new Select( _JobAlertDriver.findElement(salaryDropDown)).selectByValue("8");
			_Utility.scrollTOElement(expDropDown, _JobAlertDriver);
			new Select( _JobAlertDriver.findElement(expSubDropDown)).selectByValue("5");
			List <WebElement> multi_drop_down =  _JobAlertDriver.findElements(msChoice);
			_Utility.scrollTOElement(multi_drop_down.get(0), _JobAlertDriver);
			//clicks on the location dropdown
			select_location(_JobAlertDriver, multi_drop_down.get(0), "423");
			//clicks on the FA 
			select_department(_JobAlertDriver, multi_drop_down.get(1), "718");
			//clicks on the Industry
			select_industry(_JobAlertDriver, multi_drop_down.get(2), "23");
			_Wait.until(ExpectedConditions.elementToBeClickable(saveChanges));
			_JobAlertDriver.findElement(saveChanges).click();
		}
		else {
			if(isLoggedIn==false) {
				_JobAlertDriver.findElement(By.id("id_mail")).sendKeys(job_alert_emailid);
				_JobAlertDriver.findElement(createJobAlertLink).click();
				fill_job_alerts(randomSkills, false);
			}
			else {
				_JobAlertDriver.findElement(createJobAlertLink).click();
				fill_job_alerts(randomSkills, false);
				_Utility.Thread_Sleep(3000);
				new Select( _JobAlertDriver.findElement(salaryDropDown)).selectByValue("11");
				_Utility.scrollTOElement(expDropDown, _JobAlertDriver);
				new Select( _JobAlertDriver.findElement(expSubDropDown)).selectByValue("7");
				List <WebElement> multi_drop_down =  _JobAlertDriver.findElements(msChoice);
				//clicks on the location dropdown
				select_location(_JobAlertDriver, multi_drop_down.get(0), "406");
				//clicks on the FA 
				select_department(_JobAlertDriver, multi_drop_down.get(1), "703");
				//clicks on the Industry
				select_industry(_JobAlertDriver, multi_drop_down.get(2), "9");
			}
			_Utility.Thread_Sleep(6000);
			_Wait.until(ExpectedConditions.elementToBeClickable(createJobAlert));
			_JobAlertDriver.findElement(createJobAlert).click();
			_Utility.Thread_Sleep(6000);
		}
	}
	/**
	 * 
	 * @param _JobAlertDriver
	 * @param drop_down_selector
	 * @param location_id
	 */
	public void select_location(WebDriver _JobAlertDriver,WebElement drop_down_selector, String location_id) {
		drop_down_selector.click();
		_Utility.Thread_Sleep(1000);
		//clicks on the location dropdown
		_JobAlertDriver.findElement(By.xpath(".//*[@id='id_locid_"+location_id+"']")).click();
		_JobAlertDriver.findElement(By.xpath(".//*[@id='id_locid_"+location_id+"']")).sendKeys(Keys.ESCAPE);
	}
	/**
	 * 
	 * @param _JobAlertDriver
	 * @param drop_down_selector
	 * @param department_id
	 */
	public void select_department(WebDriver _JobAlertDriver,WebElement drop_down_selector, String department_id) {
		drop_down_selector.click();
		_Utility.Thread_Sleep(1000);
		//clicks on the FA
		_JobAlertDriver.findElement(By.xpath(".//*[@id='id_area_"+department_id+"']")).click();
		_JobAlertDriver.findElement(By.xpath(".//*[@id='id_area_"+department_id+"']")).sendKeys(Keys.ESCAPE);
	}
	/**
	 * 
	 * @param _JobAlertDriver
	 * @param drop_down_selector
	 * @param industry_id
	 */
	public void select_industry(WebDriver _JobAlertDriver,WebElement drop_down_selector, String industry_id) {
		drop_down_selector.click();
		_Utility.Thread_Sleep(1000);
		//clicks on the Industry
		_JobAlertDriver.findElement(By.xpath(".//*[@id='id_ind_"+industry_id+"']")).click();
		_JobAlertDriver.findElement(By.xpath(".//*[@id='id_ind_"+industry_id+"']")).sendKeys(Keys.ESCAPE);
	}




	/**
	 * fill job alerts
	 * @param randomSkills
	 */
	public void fill_job_alerts(String randomSkills, boolean isEdit) {
		if(isEdit == false) {
			_JobAlertDriver.findElement(jobAlertName).clear();
			_JobAlertDriver.findElement(jobAlertName).sendKeys(randomSkills);
		}
		_Utility.scrollTOElement(jobAlertKeyword, _JobAlertDriver);
		_JobAlertDriver.findElement(jobAlertKeyword).click();
		_JobAlertDriver.findElement(jobAlertKeyword).clear();
		_JobAlertDriver.findElement(jobAlertKeyword).sendKeys(randomSkills);
		_Utility.Thread_Sleep(1000);
		_JobAlertDriver.findElement(jobAlertKeyword).sendKeys(Keys.TAB);
		if(isEdit == false)
			clickOnShowMore();
	}

	public void clickOnShowMore(){
		try {
			_Wait.until(ExpectedConditions.elementToBeClickable(showmorebtn));
			WebElement showMore = _JobAlertDriver.findElement(showmorebtn);
			showMore.click();
		}catch(Throwable t) {
			APP_LOGS.debug("Show more button not available "+t.getMessage());
		}
	}


	/**
	 * Get Skill List from csv file
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> getSkillList()
	{
		ArrayList<String> skillArray = new ArrayList<String>();
		String csvFile = System.getProperty("user.dir")+"/src/test/resources/data/topsearches.csv";
		String line = "";
		String cvsSplitBy = ",";
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String data = line.split(cvsSplitBy)[0].toString();
				//System.out.println(data);
				skillArray.add(data);
			} 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return skillArray;
	}


	/**
	 * Generate Skill
	 * @param rSkills
	 * @return
	 */
	public static String generateRandomSkill(ArrayList<String> rSkills) {
		SecureRandom ng = numberGenerator;
		if(ng == null) {
			numberGenerator = ng = new SecureRandom();
		}
		String skillrandom = rSkills.get(rand.nextInt(rSkills.size())).toLowerCase()+"_"+Long.toHexString(ng.nextLong());
		APP_LOGS.info("Generated Random Skill: " + skillrandom);
		return skillrandom;

	}

	/*********************************************************************************************************/


	/*GET API RESPONSE*/
	public void verify_job_alerts(boolean isEdit){
		try{
			APP_LOGS.debug("UID: "+uid);
			String api_url = baseUrl+"/api/v2/candidate/"+uid+"/job-alerts/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			APP_LOGS.debug(api_url);
			String data = job_alert_emailid+":123456";
			String encodedBytes = Base64.getEncoder().encodeToString((data).getBytes("UTF-8"));
			APP_LOGS.debug("encodedBytes " + encodedBytes);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Authorization", "Basic "+encodedBytes)
					.header("Accept", "application/json")
					.get();
			//cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==
			String api_result = response.readEntity(String.class);
			APP_LOGS.debug("Job Alert Api Response with isEdit flag: "+isEdit+" = "+api_result);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			assert_response(api_result, isEdit);
			response.close(); 
			client.close();
		}
		catch (Exception e) {
			APP_LOGS.debug("Error: "+e.getMessage());
		}
	}

	/*Parse json response*/
	public void assert_response(String response, boolean isEdit){
		String email = null, name = null, keywords = null, locid = null, exp = null, area = null, ind = null, sal = null;
		try {
			JSONArray arrayList = new JSONArray(response);
			int flag =0;
			for(int i=0;i<arrayList.length();i++){
				JSONObject obj = (JSONObject) arrayList.get(i);
				keywords = obj.getString("keywords");
				if(keywords.equals(skillName)) {
					email = obj.getString("email");
					name = obj.getString("name");
					locid = obj.get("locid").toString();
					exp = obj.getString("exp");
					area = obj.get("area").toString();
					ind = obj.get("ind").toString();
					sal = obj.getString("sal");
					flag = 1;
					break;
				}
			}
			if(flag == 1 && isEdit == false) {
				SoftAssert _SoftAssert = new SoftAssert();
				_SoftAssert.assertEquals(email, job_alert_emailid);
				_SoftAssert.assertEquals(name, skillName);
				_SoftAssert.assertEquals(keywords, skillName);
				_SoftAssert.assertTrue(locid.contains("406"), "locationid 406 not found: data>> "+locid);
				_SoftAssert.assertEquals(exp, "7");
				_SoftAssert.assertTrue(area.contains("703"), "Area_id 703 not found: data>> "+area);
				_SoftAssert.assertTrue(ind.contains("9"), "Ind_id 9 not found: data>> "+ind);
				_SoftAssert.assertEquals(sal, "11");
				_SoftAssert.assertAll();
			}
			else if(flag == 1 && isEdit == true) {
				SoftAssert _SoftAssert = new SoftAssert();
				_SoftAssert.assertEquals(email, job_alert_emailid);
				_SoftAssert.assertEquals(name, skillName);
				_SoftAssert.assertEquals(keywords, skillName);
				_SoftAssert.assertTrue(locid.contains("406"), "locationid 406 not found: data>> "+locid);
				_SoftAssert.assertTrue(locid.contains("423"), "locationid 423 not found: data>> "+locid);
				_SoftAssert.assertEquals(exp, "5");
				_SoftAssert.assertTrue(area.contains("703"), "Area_id 703 not found: data>> "+area);
				_SoftAssert.assertTrue(area.contains("718"), "Area_id 718 not found: data>> "+area);
				_SoftAssert.assertTrue(ind.contains("23"), "Ind_id 23 not found: data>> "+ind);
				_SoftAssert.assertTrue(ind.contains("9"), "Ind_id 9 not found: data>> "+ind);
				_SoftAssert.assertEquals(sal, "8");
				_SoftAssert.assertAll();
			}
			else {
				Assert.assertTrue(false, "No matching job alert found");
			}

		}
		catch (JSONException e) {
			APP_LOGS.fatal("error: " + e.getMessage());
		}


	}



}







