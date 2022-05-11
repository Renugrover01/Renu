package com.shine.tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_JSRP_JD_SaveUrl extends TestBaseSetup{

	WebDriver _JSRPJDDriver;


	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the jsrp jd test");
		_JSRPJDDriver = getDriver(_JSRPJDDriver);
		_JSRPJDDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(priority=0)
	public void verify_JSRPSaveData() {
		Test_Search.simpleSearch("Java", _JSRPJDDriver);
		testSessionStorage(_JSRPJDDriver, "jsrplinksave");
	}

	@Test(priority=1)
	public void verify_JDSaveData() {
		Test_JobApply.openjdpage(_JSRPJDDriver);
		_Utility.Thread_Sleep(5000);
		testSessionStorage(_JSRPJDDriver, "jdlinksave");
	}


	/**
	 * Common method to get session storage and verify it with the URL
	 * 
	 * @param _JSRPJDDriver
	 * @param sessionId
	 */
	public void testSessionStorage(WebDriver _JSRPJDDriver, String sessionId) {
		JavascriptExecutor jse = (JavascriptExecutor) _JSRPJDDriver;
		String sessionStorageData = (String) jse.executeScript("return common.getSessionStorage('"+sessionId+"')");
		APP_LOGS.debug("Session Data: "+sessionStorageData);
		String url = _JSRPJDDriver.getCurrentUrl();
		url = url.replaceAll(baseUrl, "");
		APP_LOGS.debug("Test Url : "+url);
		Assert.assertEquals(sessionStorageData, url);
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _JSRPJDDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_JSRPJDDriver!=null)
			_JSRPJDDriver.quit();

	}

}
