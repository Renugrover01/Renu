package com.shine.tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_MidoutRegistration extends TestBaseSetup {

	private static WebDriver _Midoutregisdriver;
	static WebDriverWait wait;

	static By resumeFile       = By.id("id_file");
	static By submitResume     = By.xpath("//input[@value='Submit']");
	static By resumeMsg        = By.cssSelector(".complete_reg>h3");
	static By resumeBtn        = By.xpath("//*[@id='button']");
	static By successMsg       = By.cssSelector(".wrapper>h4");
	//static By confirmMsg 	   = By.id("id_confirmMsg");
	static By errorMsg		   = By.xpath("//*[@class='keyword upload_error cls_error_resume']");
	static By createJobAlert   = By.cssSelector(".yel_btn.cls_jobalert");
	static By name 		       = By.name("name");
	static By keyword 		   = By.id("id_keywords");

	@BeforeClass
	public void TestSetup()  {
		APP_LOGS.debug("Starting the midout test");
		_Midoutregisdriver = getDriver(_Midoutregisdriver);
		wait = new WebDriverWait(_Midoutregisdriver, 15);
		loggedInShine(_Midoutregisdriver, emailid, "123456");
		_Utility.clickOnNotification(_Midoutregisdriver);
	}


	
	@Test (priority =1)
	public void upload_excel_file() {
		resumeFileUpload("excel");
		_Utility.Thread_Sleep(1000);
		String errormsg = _Midoutregisdriver.findElement(By.xpath("//*[@class='keyword upload_error cls_error_resume']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =2)
	public void upload_html_file() {
		_Midoutregisdriver.navigate().refresh();
		resumeFileUpload("htmlfile");
		_Utility.Thread_Sleep(1000);
		String errormsg = _Midoutregisdriver.findElement(By.xpath("//*[@class='keyword upload_error cls_error_resume']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =3)
	public void upload_jpg_image_file() {
		_Midoutregisdriver.navigate().refresh();
		_Utility.Thread_Sleep(1000);
		resumeFileUpload("imagefile");
		String errormsg = _Midoutregisdriver.findElement(By.xpath("//*[@class='keyword upload_error cls_error_resume']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =4)
	public void upload_file_without_extension() {
		_Midoutregisdriver.navigate().refresh();
		_Utility.Thread_Sleep(1000);
		resumeFileUpload("no_extension");
		String errormsg = _Midoutregisdriver.findElement(By.xpath("//*[@class='keyword upload_error cls_error_resume']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}

	
	@Test (priority = 5)	
	public void test_registration_upload_resume_greater_than_5mb() {
		_Midoutregisdriver.navigate().refresh();
		if(CONFIG.getProperty("isTikaEnable").equals("false")) {
			APP_LOGS.debug("Start of invalid upload resume test");
			_Utility.Thread_Sleep(1000);
			resumeFileUpload(_Midoutregisdriver, "resume_file_greater_5mb");
			_Utility.Thread_Sleep(2000);
			String errormsg = _Midoutregisdriver.findElement(By.xpath("//*[@class='keyword upload_error cls_error_resume']")).getText();
			Assert.assertEquals(errormsg, "File size should be less than or equal to 5MB.");
		}
	}

	@Test (priority = 6)	
	public void test_RegistrationUploadInvalidResume() {
		_Utility.Thread_Sleep(1000);
		if(CONFIG.getProperty("isTikaEnable").equals("false")) {
			APP_LOGS.debug("Start of invalid upload resume test");
			_Utility.Thread_Sleep(1000);
			resumeFileUpload(_Midoutregisdriver, "resumeerrorfile");
			_Utility.Thread_Sleep(2000);
			checkErrorMessage(_Midoutregisdriver);
		}
	}


	
	  @Test (priority = 7) 
	  public void test_RegistrationUploadValidResume() {
	  _Utility.Thread_Sleep(500); 
	  resumeFileUpload(_Midoutregisdriver,"resumefile"); 
	  checkRegSuccessMsg(_Midoutregisdriver); 
	  
	  }
	  
	  @Test (priority = 8) 
	  private void testContToJobMgr() {
	  testContinueTOJobManager(_Midoutregisdriver);
	  }
	 

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Midoutregisdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_Midoutregisdriver!=null)
			_Midoutregisdriver.quit();

	}

	/*Assertion Check*/
	public static void checkRegSuccessMsg(WebDriver _RegSucessDriver){
		String applymsg= _RegSucessDriver.findElement(successMsg).getText();
		String extarctUsername = StringUtils.substringBefore(emailid, "_").trim();
		Assert.assertEquals(applymsg, "Congratulations "+extarctUsername+", you have successfully registered!", "Registration step 2 not done successfully.");
		/*Assert.assertTrue(applymsg.contains("Congratulations"), "Result: "+applymsg);
		Assert.assertTrue(applymsg.contains("you have successfully registered!"), "Result: "+applymsg);*/
	}

	/**
	 * Check for invalid resume
	 * @param _Regsemdriver
	 */
	public static void checkErrorMessage(WebDriver _Regsemdriver) {
		String sucmsg = "";
		try {
			sucmsg = _Midoutregisdriver.findElement(errorMsg).getText();
		}catch(Exception ex) {
			APP_LOGS.debug("Error message not found");
		}
		//Hack - Use different assertion in case if tika parser change
		if(CONFIG.getProperty("isTikaEnable").equals("true"))
			Assert.assertEquals(sucmsg, "Your name, email or phone number in resume must be same as in your profile.");
		else
			Assert.assertEquals(sucmsg, "Your name, email or phone number in resume must be same as in your profile.");
	}


	/*
	 * Commom methods
	 * 
	 */

	public static void testMidoutLoginfromHome()  {
		APP_LOGS.debug("Start of midout registration tests");
		_Midoutregisdriver.get(baseUrl + "/myshine/registration/details/");

	}


	public static void testRegistrationUploadInvalidResumenew(WebDriver regsemdriver) {
		APP_LOGS.debug("Start of invalid upload resume test");
		_Utility.Thread_Sleep(2000);
		resumeFileUpload(regsemdriver, "resumeerrorfile");
		_Utility.Thread_Sleep(2000);
		//UPLOAD VALID RESUME
		resumeFileUpload(regsemdriver, "resumefile");
		String sucmsg1 = regsemdriver.findElement(errorMsg).getText();
		Assert.assertEquals(sucmsg1, "Congratulations Shine Test, you have successfully registered!", "Registration step 2 not done successfully.");
	}



	public static void testMidoutUploadResume(WebDriver miduploaddriver) {
		APP_LOGS.debug("Start of upload resume test");
		_Utility.Thread_Sleep(1000);
		resumeFileUpload(miduploaddriver, "resumefile");
		_Utility.Thread_Sleep(1000);
		String sucmsg = miduploaddriver.findElement(successMsg).getText();
		Assert.assertTrue(sucmsg.contains("Congratulations"), "Result: "+sucmsg);
		Assert.assertTrue(sucmsg.contains("you have successfully registered!"), "Result: "+sucmsg);
	}

	public static void resumeFileUpload(WebDriver resuploaddriver, String filename) {
		resuploaddriver.findElement(resumeFile).sendKeys(System.getProperty("user.dir")+CONFIG.getProperty(filename));
		_Utility.Thread_Sleep(3000);
	}
	
	public static void resumeFileUpload(String filename) {
		_Midoutregisdriver.findElement(resumeFile).sendKeys(System.getProperty("user.dir")+CONFIG.getProperty(filename));
		_Utility.Thread_Sleep(1000);
	}
	
	

	public static void testContinueTOJobManager(WebDriver _Midoutregisdriver) {
		fill_JobAlertform(_Midoutregisdriver);
		_Midoutregisdriver.findElement(createJobAlert).click(); //create job alert
		_Utility.Thread_Sleep(2000);
		Assert.assertEquals(_Midoutregisdriver.getTitle(),"Shine.com - My Shine | Home");

	}

	public static String getCurrentWindowTitle()
	{
		String windowTitle = _Midoutregisdriver.getTitle();
		return windowTitle;

	}

	public static void fill_JobAlertform(WebDriver _Fjadriver) {
		APP_LOGS.debug("Create alert page after registration step 2");
		_Fjadriver.findElement(name).clear();
		_Fjadriver.findElement(name).sendKeys("myalert");
		_Fjadriver.findElement(keyword).clear();
		_Fjadriver.findElement(keyword).sendKeys("Sales");
		_Utility.Thread_Sleep(2000);
		_Fjadriver.findElement(keyword).sendKeys(Keys.TAB);
		_Fjadriver.findElement(keyword).sendKeys(Keys.ESCAPE);
	}

}
