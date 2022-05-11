package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.emailer.ReadEmail;
import com.shine.listener.NetworkLogParser;

public class Test_Emailers  extends TestBaseSetup{

	static WebDriver  _EmailDriver;
	static WebDriverWait _Wait;
	ReadEmail _ReadEmail = null;

	@BeforeClass
	public void TestSetup() {
		_EmailDriver = getDriver(_EmailDriver);
		_ReadEmail = new ReadEmail();

	}
	
	
	@Test (priority=1, dataProvider="email_url_link")
	public void verify_email_urls(String url){

		if(url.indexOf("similarJob")==0) {
			url = url.replace("similarJob:", "").trim();
			verify_similar_job_link(_EmailDriver, url);
		}
		if(url.indexOf("matchingJob")==0) {
			url = url.replace("matchingJob:", "").trim();
			verify_matching_job_link(_EmailDriver, url);
		}
		if(url.indexOf("recruiterActivity")==0) {
			url = url.replace("recruiterActivity:", "").trim();
			verify_recruiter_activity_link(_EmailDriver, url);
		}
		if(url.indexOf("recentActivity")==0) {
			url = url.replace("recentActivity:", "").trim();
			verify_recent_activity_link(_EmailDriver, url);
		}
		if(url.indexOf("jobForYou")==0) {
			url = url.replace("jobForYou:", "").trim();
			verify_jobforyou_link(_EmailDriver, url);
		}
		if(url.indexOf("applyJobEmail")==0) {
			url = url.replace("applyJobEmail:", "").trim();
			verify_apply_job_link(_EmailDriver, url);
		}
		if(url.indexOf("jobApplication")==0) {
			url = url.replace("jobApplication:", "").trim();
			verify_job_application_link(_EmailDriver, url);
		}



	}

	private void verify_job_application_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		assertTrue(_EmailDriver.findElement(By.cssSelector(".cls_jobtitle")).isDisplayed());

	}


	private void verify_apply_job_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		_Utility.Thread_Sleep(5000);
		String alertMsg = _EmailDriver.findElement(By.cssSelector(".email_msg")).getText();
		try {
			assertEquals(alertMsg, "Your Response has been sent to the Recruiter");
		}catch(Throwable t) {
			assertTrue(_EmailDriver.getCurrentUrl().contains("/myshine/jobs/"));
		}
	}


	private void verify_jobforyou_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		assertTrue(_EmailDriver.findElement(By.cssSelector(".cls_jobtitle")).isDisplayed());
	}


	private void verify_recent_activity_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		assertTrue(_EmailDriver.findElement(By.cssSelector(".cls_jobtitle")).isDisplayed());
	}


	private void verify_recruiter_activity_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(4000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		assertTrue(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/activities/"));
		String actual = _EmailDriver.findElement(By.cssSelector(".JobY h2")).getText();
		assertEquals(actual, "Recruiter Actions");
	}


	private void verify_matching_job_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		assertTrue(_EmailDriver.findElement(By.cssSelector(".cls_jobtitle")).isDisplayed());
	}


	private void verify_similar_job_link(WebDriver _EmailDriver, String url) {
		_EmailDriver.get(url);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www")) {
			_EmailDriver.get(getParsedURL(_EmailDriver, url));
			_Utility.Thread_Sleep(1000);
		}
		assertFalse(_EmailDriver.getCurrentUrl().toString().contains(baseUrl+"/myshine/login/"));
		assertTrue(_EmailDriver.findElement(By.cssSelector(".cls_jobtitle")).isDisplayed());

	}


	@DataProvider(name = "email_url_link")
	public Iterator<String> email_url_link(){
		Iterator<String> arrayObject = _ReadEmail.getEmailURLs().iterator();
		return arrayObject;

	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _EmailDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_EmailDriver!=null)
			_EmailDriver.quit();

	}

	/**
	 * HACK FOR PREPROD URL
	 * @param _EmailDriver
	 * @param url
	 * @return
	 */
	public String getParsedURL(WebDriver _EmailDriver, String url) {
		NetworkLogParser _NetworkLogParser = new NetworkLogParser();
		String parsed_url = _NetworkLogParser.getURL(_EmailDriver, url);
		APP_LOGS.debug(parsed_url);
		return parsed_url;
	}

}