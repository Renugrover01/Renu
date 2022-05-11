package com.shine.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.tests.Test_Search;
import com.shine.base.TestBaseSetup;

public class Test_JobApply extends TestBaseSetup {

	WebDriver _ApplyDriver;
	static WebDriverWait _Wait;
	String[] jobList = new String [3];

	By applyButton           = By.cssSelector(".cls_apply_button");
	By applyButtonJD		 = By.cssSelector(".sj.pull-left a");
	By uploadBox_ApplyButton = By.cssSelector("a.cls_upd_apply.upload_btn");
	static By JobApplyMessage= By.cssSelector(".AlreadyAppliedtext");
	By uploadResumeBtn		 = By.id("id_resume_new");
	//By uploadResume		 = By.id("id_file");
	By uploadResume			 = By.cssSelector("#id_newResumeInput input");
	By resumeErrorMessage	 = By.cssSelector(".error_text.floatleft");
	By resumeList			 = By.cssSelector(".resume_list.cls_rdb_new");
	By similarJob            = By.cssSelector(".applybuttonparichay");

	static By searchDivStat  = By.id("search_content");

	By jsrpJobSelector		= By.className("cls_chk_apply_cnd_job");
	By searchtTxtBox		= By.id("id_searchBase");

	By loadMoreBtn			= By.id("id_paginate_next");
	By applyAllSelectedJob  = By.className("bulkapply");
	By jobApplyMsg			= By.cssSelector("span.AlreadyAppliedtext");


	By appliedJobLink        = By.cssSelector(".cls_appliedjobs");
	By alreadyAppliedMessage = By.cssSelector(".alrd_applied");


	static By dontAskResumeCheckBox	 = By.cssSelector("#id_dontAskForResumeFromProfile");
	static By savedontAskResumeCheckBox = By.cssSelector("#id_dontAskForResumeSave");
	static By jsrpApplyBtn				 = By.cssSelector("div.applybuttonparichay");

	By sendMeSimilarJobLink = By.id("id_send_me_similar_job");

	@BeforeClass
	public void TestSetup() {
		_ApplyDriver = getDriver(_ApplyDriver);
		loggedInShine(_ApplyDriver, emailid, pass_new);
		_Utility.Thread_Sleep(2000);
		_Utility.clickOnNotification(_ApplyDriver);
		_Wait = new WebDriverWait(_ApplyDriver, 20);
		if(baseUrl.contains("sumosc")){
			jobList[0] = "sales";
			jobList[1] = "Genpact";
			jobList[2] = "marketing";

		}
		else{
			jobList[0] = "sales";
			jobList[1] = "manager";
			jobList[2] = "marketing";
			/*jobList[0] = "8528459";
			jobList[1] = "8587121";
			jobList[2] = "6979047";*/
		}
	}



	/*
	 * @Test(priority = 2) public void multiple_apply_case_1() {
	 * searchForJob("teacher", _ApplyDriver); int count =
	 * SelectAndApply_MultipleJobs(5, _ApplyDriver); APP_LOGS.debug("Your "+count+
	 * " applications have been submitted successfully.");
	 * //assertJobApplyMessage("Your " + count +
	 * " applications have been submitted successfully.", jobApplyMsg,
	 * _ApplyDriver);
	 * assertJobApplyMessage("Your application has been submitted successfully.",
	 * jobApplyMsg, _ApplyDriver); }
	 * 
	 * 
	 * @Test(priority = 6) public void multiple_apply_case_2() {
	 * searchForJob("teacher", _ApplyDriver); int count =
	 * SelectAndApply_MultipleJobs(5, _ApplyDriver); _Utility.Thread_Sleep(3000);
	 * APP_LOGS.debug("Your "+count+
	 * " applications have been submitted successfully.");
	 * //assertJobApplyMessage("Your "+count+
	 * " applications have been submitted successfully.", jobApplyMsg,
	 * _ApplyDriver);
	 * assertJobApplyMessage("Your application has been submitted successfully.",
	 * jobApplyMsg, _ApplyDriver); }
	 */


	/*
	 * @Test(priority = 8) public void testPostedDateonJDpage() {
	 * openjdpage_new(_ApplyDriver); _Utility.Thread_Sleep(2000);
	 * APP_LOGS.debug("Look for posted date on JD page");
	 * APP_LOGS.debug("the title of the window is: " + _ApplyDriver.getTitle());
	 * String postedondate =
	 * _ApplyDriver.findElement(By.cssSelector(".date>span")).getText();
	 * Assert.assertNotNull(postedondate, "NO Posted date found"); }
	 */



	/*
	 * @Test(priority = 10) public void testApplyFromJDpage() {
	 * _ApplyDriver.findElement(applyButtonJD).click(); _Utility.Thread_Sleep(2000);
	 * assertMessage(_ApplyDriver); }
	 */


	@Test(priority = 13)
	public void testOpenAppliedJobs()  {
		_ApplyDriver.get(baseUrl + "/myshine/home/");
		_ApplyDriver.switchTo().parentFrame();
		_ApplyDriver.findElement(appliedJobLink).click();
		_Utility.Thread_Sleep(3000);
		List<WebElement> appliedMsg = _ApplyDriver.findElements(alreadyAppliedMessage);
		for(WebElement msg: appliedMsg) {
			APP_LOGS.debug("Applied Job message: "+msg.getText());
			Assert.assertEquals(msg.getText().trim(), "Already Applied","No applied jobs found.");
		}
	}


	/*
	 * @Test(priority = 14) public void testJsrpPage1ApplyOneJob() {
	 * searchForJob(jobList[0], _ApplyDriver); jobHandler(_ApplyDriver);
	 * //SelectAndApply_MultipleJobs(1, _ApplyDriver);
	 * //_ApplyDriver.findElement(uploadBox_ApplyButton).click();
	 * _Utility.clickOnNotification(_ApplyDriver); assertMessage(_ApplyDriver); }
	 */



	/*
	 * @Test(priority = 25) public void test_SimilarJobApply() {
	 * _Utility.Thread_Sleep(3000); _ApplyDriver.findElement(similarJob).click();
	 * _Utility.Thread_Sleep(3000); assertMessage(_ApplyDriver); }
	 */


	/*
	 * @Test(priority = 26) public void test_JD_SendMeSimilarJobLink() {
	 * _Utility.Thread_Sleep(2000); String jobid
	 * =_ApplyDriver.findElement(sendMeSimilarJobLink).getAttribute("data-href");
	 * APP_LOGS.info("Job id: "+jobid);
	 * _ApplyDriver.findElement(sendMeSimilarJobLink).click();
	 * _Utility.Thread_Sleep(3000); String currentURL =
	 * _ApplyDriver.getCurrentUrl(); APP_LOGS.info("current URL: "+currentURL);
	 * Assert.assertTrue(currentURL.contains(jobid)); }
	 */


	@Test(priority = 30)
	public void testLoggedOutApply()  {
		_ApplyDriver.get(baseUrl + "/myshine/logout/");
		searchForJob(jobList[1], _ApplyDriver);
		List<WebElement> applyloggedout = _ApplyDriver.findElements(jsrpApplyBtn);
		_Utility.Thread_Sleep(3000);
		_Utility.scrollTOElement(applyloggedout.get(0), _ApplyDriver);
		_Utility.elementDisplayPropertySetter("none", "id_filters", _ApplyDriver);
		applyloggedout.get(0).click();

		_ApplyDriver.findElement(By.id("id_email_login")).clear();
		_ApplyDriver.findElement(By.id("id_email_login")).sendKeys(TestBaseSetup.emailid);
		_ApplyDriver.findElement(By.id("id_password")).clear();
		_ApplyDriver.findElement(By.id("id_password")).sendKeys(TestBaseSetup.pass_new);
		_ApplyDriver.findElement(By.name("login_popup")).click();

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _ApplyDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_ApplyDriver!=null)
			_ApplyDriver.quit();

	}

	/**
	 * 
	 * @param keyword
	 * @param _ApplyDriver
	 */
	public static void searchForJob(String keyword,WebDriver _ApplyDriver) {
		/*if(baseUrl.contains("sumosc")||baseUrl.contains("sumosc1")) 
			Test_Search.simpleSearch("HT Media Ltd", _ApplyDriver);
		else */
			
			Test_Search.simpleSearch(keyword, _ApplyDriver);
	}


	/**
	 * 
	 * @param expected_message
	 * @param by
	 * @param _ApplyDriver
	 */
	public void assertJobApplyMessage(String expected_message, By by, WebDriver _ApplyDriver) {
		_Utility.Thread_Sleep(5000);
		String actual_message = _ApplyDriver.findElement(by).getText();
		APP_LOGS.debug("Actual Message: "+actual_message);
		APP_LOGS.debug("Expected Message: "+expected_message);
		Assert.assertTrue(actual_message.contains(expected_message), actual_message);
		APP_LOGS.debug("The URL of application submit page is " + _ApplyDriver.getCurrentUrl());

	}

	/**
	 * Method to select and apply multiple jobs
	 * @param _ApplyDriver
	 */
	public int SelectAndApply_MultipleJobs(int selectCount, WebDriver _ApplyDriver) {
		int count =0;
		List<WebElement> jobSelectorList = new ArrayList<WebElement>();
		for(int j=0;j<5;j++){
			jobSelectorList = _ApplyDriver.findElements(jsrpJobSelector);
			if(jobSelectorList.size()==0){
				APP_LOGS.debug("***No normal Job Found in JSRP - Check next page****");
				_ApplyDriver.findElement(loadMoreBtn).click();
				_Utility.Thread_Sleep(2000);
				jobSelectorList = _ApplyDriver.findElements(jsrpJobSelector);
			}
			else break;
			APP_LOGS.debug(j+"Page JOB SIZE: "+jobSelectorList.size());
		}
		if(jobSelectorList.size()!=0||jobSelectorList!=null){
			_Utility.elementDisplayPropertySetter("none", "id_filters", _ApplyDriver);
			for (int i = 0; i < selectCount; i++) {
				try {
					_Utility.scrollTOElement(jobSelectorList.get(i), _ApplyDriver);
					jobSelectorList.get(i).click();
					count++;

				} catch (Exception e) {
					APP_LOGS.error("Not able to click");
				}
			}
			_Utility.scrollTOElement(searchtTxtBox, _ApplyDriver);
			_ApplyDriver.findElement(applyAllSelectedJob).click();
			_Utility.Thread_Sleep(4000);

		}
		return count;
	}





	public static void assertMessage(WebDriver _ApplyDriver){
		_Utility.clickOnNotification(_ApplyDriver);
		_Wait = new WebDriverWait(_ApplyDriver, 15);
		_Utility.Thread_Sleep(4000);
		_Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(JobApplyMessage));
		String sucmsg = _ApplyDriver.findElement(JobApplyMessage).getText();
		APP_LOGS.debug("Message on applying job: "+sucmsg);
		//Assert.assertEquals(sucmsg, "Your application has been submitted successfully.");
		if(sucmsg.contains("Your application has been submitted successfully."))
			Assert.assertTrue(sucmsg.trim().contains("Your application has been submitted successfully."),sucmsg);
		else if(sucmsg.contains("Thanks for showing your interest. We will send you a reminder 1 day before this walk-in"))
			Assert.assertTrue(sucmsg.trim().contains("Thanks for showing your interest. We will send you a reminder 1 day before this walk-in"),sucmsg);
		else if(sucmsg.contains("Thanks for showing your interest. We have sent you a calender invite on"))
			Assert.assertTrue(sucmsg.trim().contains("Thanks for showing your interest. We have sent you a calender invite on"),sucmsg);
		else
			Assert.assertTrue(sucmsg.trim().contains("You have been redirected to the company website to complete the application for this job. Please see similar jobs that you can apply to."),sucmsg);


	}




	public void applyfromJsrp(List<WebElement> applybuttonlist) {
		_Utility.scrollTOElement(applybuttonlist.get(2), _ApplyDriver);
		applybuttonlist.get(2).click();
		_Utility.Thread_Sleep(5000);
		_Utility.scrollTOElement(By.cssSelector(".cls_upd_apply.upload_btn"), _ApplyDriver);
		_ApplyDriver.findElement(By.cssSelector(".cls_upd_apply.upload_btn")).click();

	}


	public static void openjdpage(WebDriver _0penjdDriver) {
		searchForJob("Manager", _0penjdDriver);
		_Utility.Thread_Sleep(2000);
		List<WebElement> apply4 = _0penjdDriver.findElements(By.cssSelector(".snp"));
		APP_LOGS.debug("Current window handle is" + _0penjdDriver.getWindowHandle());
		_Utility.scrollTOElement(searchDivStat, _0penjdDriver);
		apply4.get(0).click();
		_Utility.Thread_Sleep(2000);
		for (String winHandle : _0penjdDriver.getWindowHandles()) {

			_0penjdDriver.switchTo().window(winHandle); // switch focus of
			// WebDriver to the next found window handle(that's your newly opened window)
			APP_LOGS.debug("Current window handle after JD Click is" + _0penjdDriver.getWindowHandle());
			_Utility.Thread_Sleep(1000);
		}
		_Utility.Thread_Sleep(1000);
	}

	public static void openjdpage(WebDriver _0penjdDriver, String Keyword) {
		Test_Search.simpleSearch(Keyword, _0penjdDriver);
		_Utility.Thread_Sleep(2000);
		List<WebElement> apply4 = _0penjdDriver.findElements(By.cssSelector(".snp"));
		APP_LOGS.debug("Current window handle is" + _0penjdDriver.getWindowHandle());
		_Utility.scrollTOElement(searchDivStat, _0penjdDriver);
		apply4.get(0).click();
		_Utility.Thread_Sleep(2000);
		for (String winHandle : _0penjdDriver.getWindowHandles()) {
			_0penjdDriver.switchTo().window(winHandle); // switch focus of
			// WebDriver to the next found window handle(that's your newly opened window)
			APP_LOGS.debug("Current window handle after JD Click is" + _0penjdDriver.getWindowHandle());
			_Utility.Thread_Sleep(2000);
		}
		_Utility.Thread_Sleep(1000);
	}

	public static void openjdpage_new(WebDriver opjddriver) {
		_Wait = new WebDriverWait(opjddriver, 15);
		searchForJob("Java", opjddriver);
		//.search_listingleft .cls_searchresult_a
		List<WebElement> apply4 = null;
		int breakLoop = 0;
		for(int j=0;j<5;j++){
			apply4 = opjddriver.findElements(By.cssSelector("a.searchresult_link"));
			for(WebElement app: apply4){
				String jobTypeCheck = app.getAttribute("href").toString();
				APP_LOGS.debug(jobTypeCheck);
				if(jobTypeCheck.contains("referrals")||jobTypeCheck.contains("powered-by-round-one")||jobTypeCheck.contains("powered-by")||jobTypeCheck.contains("roundone")){
					APP_LOGS.debug("No normal Job Found in JSRP - Check next page");
					//_Utility.Thread_Sleep(2000);
				}
				else {
					APP_LOGS.debug(jobTypeCheck);
					APP_LOGS.debug(app.getAttribute("innerHTML"));
					_Utility.scrollTOElement(app, opjddriver);
					_Wait.until(ExpectedConditions.elementToBeClickable(app));
					_Utility.elementDisplayPropertySetter("none", "id_filters", opjddriver);
					//Actions action = new Actions(opjddriver);
					//action.moveToElement(app).click().perform();
					_Utility.Thread_Sleep(1000);
					app.click(); 
					breakLoop=1;
					break;
				}
			}
			if(breakLoop>0) break;
			opjddriver.findElement(By.cssSelector(".cls_paginate.submit")).click();

		}
		APP_LOGS.debug("Current window handle is" + opjddriver.getWindowHandle());
		_Utility.Thread_Sleep(3000);
		for (String winHandle : opjddriver.getWindowHandles()) {

			opjddriver.switchTo().window(winHandle); // switch focus of
			// WebDriver to the next found window handle(that's your newly opened window)
			APP_LOGS.debug("Current window handle after JD Click is" + opjddriver.getWindowHandle());
			_Utility.Thread_Sleep(2000);
		}
		APP_LOGS.debug("JD URL :"+opjddriver.getCurrentUrl());
	}

	/*
	 * Handle Third party/ Round one job - Matching job in My profile
	 */
	public void jobHandler(WebDriver driver)  {
		List<WebElement> applyBtn = driver.findElements(applyButton);

		for (WebElement apply : applyBtn) {
			_Utility.Thread_Sleep(2000);
			try{
				if (apply.getAttribute("href").contains("javascript:void(0);")) {
					apply.click();
					// driver.findElement(By.cssSelector(".chkboxlen.txmyjob .apply
					// a.cls_call_dialogbox")).click();
					if (driver.getTitle().contains("Shine.com")) {
						_Wait.until(ExpectedConditions
								.visibilityOfAllElementsLocatedBy(uploadBox_ApplyButton));
						driver.findElement(uploadBox_ApplyButton).click();
						String message = driver.findElement(jobApplyMsg).getText();
						Assert.assertTrue(message.contains("Your application has been submitted successfully."));
						break;
					} else if (driver.getTitle().contains("Job Applied")) {
						_Wait.until(ExpectedConditions
								.visibilityOfAllElementsLocatedBy(jobApplyMsg));
						String message = driver.findElement(jobApplyMsg).getText();
						Assert.assertTrue(message.contains("Your application has been submitted successfully."));
						break;
					}

				} else {
					APP_LOGS.debug("No simple job found: " + apply.getAttribute("href"));
				}
			}
			catch(Exception ex){
				APP_LOGS.debug("Round one job -no href found");
				continue;
			}
		}
	}


}