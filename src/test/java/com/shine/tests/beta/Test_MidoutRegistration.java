package com.shine.tests.beta;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;

import static org.testng.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.listener.LogAnalyzer;
import com.shine.page.JobAlert;
import com.shine.page.Resume;


public class Test_MidoutRegistration extends TestBaseSetup {

	WebDriver _MidoutRegistrationDriver;
	WebDriverWait wait;
	Resume _Resume;
	JobAlert _JobAlert;
	
	By successMsg	= By.cssSelector(".wrapper>h4");
	By errorMsg		= By.cssSelector(".cls_error_resume");

	@BeforeClass
	public void TestSetup()  {
		APP_LOGS.debug("Starting the midout test");
		_MidoutRegistrationDriver = getDriver(_MidoutRegistrationDriver);
		wait = new WebDriverWait(_MidoutRegistrationDriver, 15);
		loggedInShine(_MidoutRegistrationDriver, email_main, pass_hc);
		_Utility.clickOnNotification(_MidoutRegistrationDriver);
	}


	
	@Test (priority =0)
	public void upload_excel_file() {
		_Resume.upload_resume("excel");
		_Utility.Thread_Sleep(1000);
		String errormsg = _MidoutRegistrationDriver.findElement(By.cssSelector(".cls_error_resume")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =1)
	public void upload_html_file() {
		_MidoutRegistrationDriver.navigate().refresh();
		_Resume.upload_resume("htmlfile");
		_Utility.Thread_Sleep(1000);
		String errormsg = _MidoutRegistrationDriver.findElement(By.cssSelector(".cls_error_resume")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =2)
	public void upload_jpg_image_file() {
		_MidoutRegistrationDriver.navigate().refresh();
		_Utility.Thread_Sleep(1000);
		_Resume.upload_resume("imagefile");
		String errormsg = _MidoutRegistrationDriver.findElement(By.cssSelector(".cls_error_resume")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =3)
	public void upload_file_without_extension() {
		_MidoutRegistrationDriver.navigate().refresh();
		_Utility.Thread_Sleep(1000);
		_Resume.upload_resume("no_extension");
		String errormsg = _MidoutRegistrationDriver.findElement(By.cssSelector(".cls_error_resume")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}

	
	@Test (priority = 5)	
	public void test_registration_upload_resume_greater_than_5mb() {
		_MidoutRegistrationDriver.navigate().refresh();
		if(CONFIG.getProperty("isTikaEnable").equals("false")) {
			APP_LOGS.debug("Start of invalid upload resume test");
			_Utility.Thread_Sleep(2000);
			_Resume.upload_resume("resume_file_greater_5mb");
			_Utility.Thread_Sleep(8000);
			String errormsg = _MidoutRegistrationDriver.findElement(By.cssSelector(".cls_error_resume")).getText();
			Assert.assertEquals(errormsg, "File size should be less than or equal to 5MB.");
		}
	}

	@Test (priority = 6)	
	public void test_RegistrationUploadInvalidResume() {
		_Utility.Thread_Sleep(2000);
		if(CONFIG.getProperty("isTikaEnable").equals("false")) {
			APP_LOGS.debug("Start of invalid upload resume test");
			_Utility.Thread_Sleep(2000);
			_Resume.upload_resume("resumeerrorfile");
			_Utility.Thread_Sleep(6000);
			checkErrorMessage();
		}
	}

	@Test (priority=9)
	public void verify_upload_resume() {
		_Resume.upload_resume("resumefile");
	}
	
	@Test(priority=10)
	public void verify_thank_you_page_collect_view() {
		assertEquals(LogAnalyzer.analyzePageViewRequest(_MidoutRegistrationDriver), true);

	}

	@Test (priority=11, dependsOnMethods= {"verify_upload_resume"})
	public void verify_reg_create_job_alert() {
		_JobAlert.fill_and_submit_job_alert();
		Assert.assertEquals(_MidoutRegistrationDriver.getTitle(),"Shine.com - My Shine | Home");

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _MidoutRegistrationDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_MidoutRegistrationDriver!=null)
			_MidoutRegistrationDriver.quit();

	}
	
	/*Assertion Check*/
	public void checkRegSuccessMsg(){
		String applymsg= _MidoutRegistrationDriver.findElement(successMsg).getText();
		String extarctUsername = StringUtils.substringBefore(emailid, "_").trim();
		Assert.assertEquals(applymsg, "Congratulations "+extarctUsername+", you have successfully registered!", "Registration step 2 not done successfully.");
		/*Assert.assertTrue(applymsg.contains("Congratulations"), "Result: "+applymsg);
		Assert.assertTrue(applymsg.contains("you have successfully registered!"), "Result: "+applymsg);*/
	}
	
	/**
	 * Check for invalid resume message
	 */
	public void checkErrorMessage() {
		String sucmsg = "";
		try {
			sucmsg = _MidoutRegistrationDriver.findElement(errorMsg).getText();
		}catch(Exception ex) {
			APP_LOGS.debug("Error message not found");
		}
		//Hack - Use different assertion in case if tika parser change
		if(CONFIG.getProperty("isTikaEnable").equals("true"))
			Assert.assertEquals(sucmsg, "Your name, email or phone number in resume must be same as in your profile.");
		else
			Assert.assertEquals(sucmsg, "Your name, email or phone number in resume must be same as in your profile.");
	}


}
