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


public class Test_VwoScript extends TestBaseSetup {

	WebDriver _VwoDriver;
	 WebDriverWait wait;
	// String keyword = "Java";
	
	
	@BeforeClass
	public void TestSetup() {
		_VwoDriver = getDriver(_VwoDriver);
		OpenBaseUrl(_VwoDriver);
		APP_LOGS.debug("Starting the VWO script track");
		wait = new WebDriverWait(_VwoDriver, 15);
	}

	@Test(priority = 0)
	public void verify_vwo_tracking_code() {
		_VwoDriver.navigate().to(baseUrl); // track GTM
		String page_source = _VwoDriver.getPageSource();
		assertTrue(page_source.contains("account_id=404008"), "can not find VWO script code");
	}

	@Test(priority = 1)
	public void verify_vwo_tracking_code_Jsrp() {
		_VwoDriver.navigate().to(baseUrl + "/job-search/");
		String page_source = _VwoDriver.getPageSource();
		_Utility.Thread_Sleep(1000);
		assertTrue(page_source.contains("account_id=404008"), "can not find VWO script code in Jsrp");
	}

	@Test(priority = 2)
	public void verify_vwo_tracking_Jsrp() {
		Test_Search.simpleSearch("java", _VwoDriver);
		String page_source = _VwoDriver.getPageSource();
		_Utility.Thread_Sleep(1000);
		assertTrue(page_source.contains("account_id=404008"), "can not find VWO script code in Jsrp");
	}

	@Test(priority = 3)
	public void verify_vwo_tracking_Jd() {
		Test_JobApply.openjdpage(_VwoDriver);
		_Utility.Thread_Sleep(1000);
		String page_source = _VwoDriver.getPageSource();
		assertTrue(page_source.contains("account_id=404008"), "can not find VWO script code in JDPage");
	}

	@Test(priority = 4)
	public void verify_loggedin_vwo_tracking_code() {
		loggedInShine(_VwoDriver, email_new, pass_new);
		_Utility.Thread_Sleep(1000);
		String page_source = _VwoDriver.getPageSource();
		_Utility.Thread_Sleep(1000);
		assertTrue(page_source.contains("account_id=404008"), "can not find VWO script code in loggedin page");
	}
	
	/*
	 * @Test(priority = 5) public void verify_loggedin_vwo_tracking_Jsrp() { String
	 * keyword = "java"; Test_Search.simpleSearch(keyword, _VwoDriver); String
	 * page_source = _VwoDriver.getPageSource(); Test_Search.simpleSearch(keyword,
	 * _VwoDriver); _Utility.Thread_Sleep(1000);
	 * assertTrue(page_source.contains("account_id=404008"),
	 * "can not find VWO script code in loggedin Jsrp"); }
	 * 
	 * @Test(priority = 6) public void verify_loggedin_vwo_tracking_Jd() {
	 * Test_JobApply.openjdpage(_VwoDriver); String page_source =
	 * _VwoDriver.getPageSource(); _Utility.Thread_Sleep(1000);
	 * assertTrue(page_source.contains("account_id=404008"),
	 * "can not find VWO script code in loggedin Jd"); }
	 */
	 

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _VwoDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_VwoDriver!=null)
			_VwoDriver.quit();

	}

}
