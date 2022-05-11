package com.shine.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.listener.LogAnalyzer;
import com.shine.tests.Test_JobApply;

public class Test_PageView extends TestBaseSetup{

	WebDriver _PageViewDriver;
	WebDriverWait _Wait;


	@BeforeClass
	public void TestSetup() {
		_PageViewDriver = getDriver(_PageViewDriver);
		_Wait = new WebDriverWait(_PageViewDriver, 15);
	}

	@Test(priority=0)
	public void verify_login_page_collect_view() {
		_PageViewDriver.navigate().to(baseUrl+"/myshine/login/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		_Utility.Thread_Sleep(3000);
		
	}
	
	@Test(priority=1)
	public void verify_nlog_jsrp_page_collect_view() {
		_PageViewDriver.navigate().to(baseUrl+"/job-search/java-jobs/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		_Utility.Thread_Sleep(3000);
		
	}
	
	@Test(priority=2)
	public void verify_nlog_jd_page_collect_view() {
		Test_JobApply.openjdpage(_PageViewDriver, "java");
		_Utility.Thread_Sleep(2000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	
	@Test(priority=3)
	public void verify_home_page_collect_view() {
		loggedInShine(_PageViewDriver, email_new, pass_new);
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	
	@Test(priority=4)
	public void verify_profile_page_collect_view() {
		_Utility.Thread_Sleep(3000);
		_PageViewDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	@Test(priority=5)
	public void verify_inbox_job_alert_page_collect_view() {
		_Utility.Thread_Sleep(3000);
		_PageViewDriver.navigate().to(baseUrl+"/myshine/inbox/job_alert/");
		_Utility.Thread_Sleep(5000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	@Test(priority=6)
	public void verify_inbox_recruiter_page_collect_view() {
		_Utility.Thread_Sleep(3000);
		_PageViewDriver.navigate().to(baseUrl+"/myshine/inbox/recruiter_mails/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	
	@Test(priority=7)
	public void verify_job_alert_page_collect_view() {
		_Utility.Thread_Sleep(3000);
		_PageViewDriver.navigate().to(baseUrl+"/myshine/job-alerts/");
		_Utility.Thread_Sleep(5000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	
	@Test(priority=8)
	public void verify_activity_page_collect_view() {
		_Utility.Thread_Sleep(3000);
		_PageViewDriver.navigate().to(baseUrl+"/myshine/activities/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	
	@Test(priority=9)
	public void verify_account_setting_page_collect_view() {
		_Utility.Thread_Sleep(3000);
		_PageViewDriver.navigate().to(baseUrl+"/myshine/accountsettings/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		
	}
	
	@Test(priority=10)
	public void verify_jsrp_page_collect_view() {
		_PageViewDriver.navigate().to(baseUrl+"/job-search/java-jobs/");
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
		_Utility.Thread_Sleep(3000);
		
	}
	
	/*
	 * @Test(priority=11) public void verify_jd_page_collect_view() {
	 * Test_JobApply.openjdpage(_PageViewDriver, "java");
	 * _Utility.Thread_Sleep(2000);
	 * assertEquals(LogAnalyzer.analyzePageViewRequest(_PageViewDriver), true);
	 * 
	 * }
	 */
	
	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _PageViewDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_PageViewDriver!=null)
			_PageViewDriver.quit();

	}
	
	
	
}
