package com.shine.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_G_Analytic extends TestBaseSetup{
	
     WebDriver _G_AnalyticDriver;
	 WebDriverWait wait;
	// String keyword = "Java";
	
	
	@BeforeClass
	public void TestSetup() {
		_G_AnalyticDriver = getDriver(_G_AnalyticDriver);
		OpenBaseUrl(_G_AnalyticDriver);
		APP_LOGS.debug("Starting the Google Analytic code track");
		wait = new WebDriverWait(_G_AnalyticDriver, 15);
	}

	@Test(priority = 0)
	public void verify_g_analytic_tracking_code() {
		_G_AnalyticDriver.navigate().to(baseUrl); // track GTM
		String page_source = _G_AnalyticDriver.getPageSource();
		assertTrue(page_source.contains("UA-3537905-1"), "can not find Google Analytic code");
	}

	@Test(priority = 1)
	public void verify_g_analytic_tracking_code1() {
		_G_AnalyticDriver.navigate().to(baseUrl + "/job-search/");
		String page_source = _G_AnalyticDriver.getPageSource();
		_Utility.Thread_Sleep(500);
		assertTrue(page_source.contains("UA-3537905-1"), "can not find Google Analytic code");
	}

	@Test(priority = 2)
	public void verify_g_analytic_tracking_code2() {
		Test_Search.simpleSearch("java", _G_AnalyticDriver);
		String page_source = _G_AnalyticDriver.getPageSource();
		_Utility.Thread_Sleep(500);
		assertTrue(page_source.contains("UA-3537905-1"), "can not find Google Analytic code");
	}

	@Test(priority = 3)
	public void verify_g_analytic_tracking_code3() {
		Test_JobApply.openjdpage(_G_AnalyticDriver);
		_Utility.Thread_Sleep(500);
		String page_source = _G_AnalyticDriver.getPageSource();
		assertTrue(page_source.contains("UA-3537905-1"), "can not find Google Analytic code in JDPage");
	}

	@Test(priority = 4)
	public void verify_loggedin_g_analytic_tracking_code() {
		loggedInShine(_G_AnalyticDriver, email_new, pass_new);
		_Utility.Thread_Sleep(500);
		String page_source = _G_AnalyticDriver.getPageSource();
		_Utility.Thread_Sleep(500);
		assertTrue(page_source.contains("UA-3537905-1"), "can not find Google Analytic code in loggedin page");
	}
	
	/*
	 * @Test(priority = 5) public void verify_loggedin_g_analytic_tracking_code1() {
	 * String keyword = "java"; Test_Search.simpleSearch(keyword,
	 * _G_AnalyticDriver); String page_source = _G_AnalyticDriver.getPageSource();
	 * Test_Search.simpleSearch(keyword, _G_AnalyticDriver);
	 * _Utility.Thread_Sleep(500); assertTrue(page_source.contains("UA-3537905-1"),
	 * "can not find Google Analytic code in loggedin page"); }
	 * 
	 * @Test(priority = 6) public void verify_loggedin_google_tracking_code2() {
	 * Test_JobApply.openjdpage(_G_AnalyticDriver); String page_source =
	 * _G_AnalyticDriver.getPageSource(); _Utility.Thread_Sleep(500);
	 * assertTrue(page_source.contains("UA-3537905-1"),
	 * "can not find Google Analytic code in loggedin page"); }
	 */
	 

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _G_AnalyticDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_G_AnalyticDriver!=null)
			_G_AnalyticDriver.quit();

	}

	
}
