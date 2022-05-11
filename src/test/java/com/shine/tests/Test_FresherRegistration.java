package com.shine.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_FresherRegistration extends TestBaseSetup {
	WebDriver _FresherRegdriver;
	static WebDriverWait _Wait;
	String emailidFresher;
	static boolean isFresher;
	static int flowFlag = 0;

	@BeforeClass
	public void TestSetup() {
		_FresherRegdriver = getDriver(_FresherRegdriver);
		OpenBaseUrl(_FresherRegdriver);
		_Wait = new WebDriverWait(_FresherRegdriver, 15);
		APP_LOGS.debug("Start of registration tests");
	}

	
	

	@Test (priority = 0)
	public void open_RegistrationPage() {
		_FresherRegdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		_Utility.Thread_Sleep(1000);
		APP_LOGS.debug("site url: "+baseUrl);
		APP_LOGS.debug("site url: "+_FresherRegdriver.getCurrentUrl());
		_FresherRegdriver.findElement(Test_Registration.registrationLink).click();
		_Utility.Thread_Sleep(1000);
		Assert.assertEquals(_FresherRegdriver.getCurrentUrl(), baseUrl+"/registration/parser/");
	}


	@Test (priority = 1)
	public void test_fresher_registration_step1() {
		String flowFound = "/registration/parser/flow-2/";
		//_FresherRegdriver.findElement(Test_Registration.regiFormAction).getAttribute("action");
		APP_LOGS.debug("Flow Found: "+flowFound);
		String data[] = Test_Registration.ExecuteRegistrationFlow(true, flowFound, _FresherRegdriver);
		emailidFresher = data[0];
		flowFlag	   = Integer.parseInt(data[1]);
		_FresherRegdriver.findElement(Test_Registration.submitFirstRegsitartionForm).click();
	}


	@Test (priority = 2)
	private void test_fresher_registration_step2() {
		Test_Registration.executeTestFlow(true, false, flowFlag, emailidFresher, _FresherRegdriver);
		_Utility.Thread_Sleep(5000);
	}

	@Test (priority=3)
	public void Upload_Fresher_Resume() {
		Test_MidoutRegistration.testMidoutUploadResume(_FresherRegdriver);
	}

	@Test (priority=4)
	public void create_FresherJobAlert() {
		Test_MidoutRegistration.fill_JobAlertform(_FresherRegdriver);
		_FresherRegdriver.findElement(By.cssSelector(".yel_btn.cls_jobalert")).click();// click on create job alert
		_Utility.Thread_Sleep(2000);
		Assert.assertEquals(_FresherRegdriver.getTitle(),"Shine.com - My Shine | Home");
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _FresherRegdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_FresherRegdriver!=null)
			_FresherRegdriver.quit();

	}

}
