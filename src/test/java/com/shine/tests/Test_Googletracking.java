package com.shine.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_Googletracking extends TestBaseSetup{
	
     WebDriver _GoogleTrackingDriver;
	 WebDriverWait wait;
	// String keyword = "Java";
	
	
	@BeforeClass
	public void TestSetup() {
		_GoogleTrackingDriver = getDriver(_GoogleTrackingDriver);
		OpenBaseUrl(_GoogleTrackingDriver);
		APP_LOGS.debug("Starting the GTM code track");
		wait = new WebDriverWait(_GoogleTrackingDriver, 15);
	}

	@Test(priority = 0)
	public void verify_google_tracking_code() {
		_GoogleTrackingDriver.navigate().to(baseUrl); // track GTM
		String page_source = _GoogleTrackingDriver.getPageSource();
		assertTrue(page_source.contains("GTM-K7VD6KX"), "can not find GTM code");
	}

	@Test(priority = 1)
	public void verify_google_tracking_code1() {
		_GoogleTrackingDriver.navigate().to(baseUrl + "/job-search/");
		String page_source = _GoogleTrackingDriver.getPageSource();
		_Utility.Thread_Sleep(500);
		assertTrue(page_source.contains("GTM-PF46HX"), "can not find GTM code");
	}

	@Test(priority = 2)
	public void verify_google_tracking_code2() {
		Test_Search.simpleSearch("java", _GoogleTrackingDriver);
		String page_source = _GoogleTrackingDriver.getPageSource();
		_Utility.Thread_Sleep(500);
		assertTrue(page_source.contains("GTM-K7VD6KX"), "can not find GTM code");
	}

	@Test(priority = 3)
	public void verify_google_tracking_code3() {
		Test_JobApply.openjdpage(_GoogleTrackingDriver);
		_Utility.Thread_Sleep(500);
		String page_source = _GoogleTrackingDriver.getPageSource();
		assertTrue(page_source.contains("GTM-K7VD6KX"), "can not find GTM code in JDPage");
	}

	@Test(priority = 4)
	public void verify_loggedin_google_tracking_code() {
		loggedInShine(_GoogleTrackingDriver, email_new, pass_new);
		_Utility.Thread_Sleep(500);
		String page_source = _GoogleTrackingDriver.getPageSource();
		_Utility.Thread_Sleep(1000);
		assertTrue(page_source.contains("GTM-PF46HX"), "can not find GTM code in loggedin page");
	}
	
	/*
	 * @Test(priority = 5) public void verify_loggedin_google_tracking_code1() {
	 * String keyword = "java"; Test_Search.simpleSearch(keyword,
	 * _GoogleTrackingDriver); String page_source =
	 * _GoogleTrackingDriver.getPageSource(); Test_Search.simpleSearch(keyword,
	 * _GoogleTrackingDriver); _Utility.Thread_Sleep(500);
	 * assertTrue(page_source.contains("GTM-PF46HX"),
	 * "can not find GTM code in loggedin page"); }
	 * 
	 * @Test(priority = 6) public void verify_loggedin_google_tracking_code2() {
	 * Test_JobApply.openjdpage(_GoogleTrackingDriver); String page_source =
	 * _GoogleTrackingDriver.getPageSource(); _Utility.Thread_Sleep(500);
	 * assertTrue(page_source.contains("GTM-PF46HX"),
	 * "can not find GTM code in loggedin page"); }
	 */
	 

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _GoogleTrackingDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_GoogleTrackingDriver!=null)
			_GoogleTrackingDriver.quit();

	}

	
}
