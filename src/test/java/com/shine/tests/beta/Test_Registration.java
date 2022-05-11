package com.shine.tests.beta;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.listener.LogAnalyzer;
import com.shine.page.JobAlert;
import com.shine.page.Registration;
import com.shine.page.Resume;


import org.testng.annotations.AfterMethod;

import org.testng.ITestResult;	


public class Test_Registration extends TestBaseSetup {
	WebDriver _RegistrationDriver;
	WebDriverWait _Wait;
	Registration _Registration;
	Resume _Resume;
	JobAlert _JobAlert;

	@BeforeClass
	public void TestSetup() {
		_RegistrationDriver = getDriver(_RegistrationDriver);
		_Wait = new WebDriverWait(_RegistrationDriver, 15);
		_Registration = new Registration(_RegistrationDriver);
		_Resume = new Resume(_RegistrationDriver);
		_JobAlert = new JobAlert(_RegistrationDriver);
		APP_LOGS.debug("****Starting registration tests***");
	}


	@Test (priority = 0)
	public void open_registration_page() {
		_Registration.open_registration_page();
		_Registration.isFirstRegistrationPageOpened();
	}

	@Test(priority=1)
	public void verify_open_registration_page_collect_view() {
		assertEquals(LogAnalyzer.analyzePageViewRequest(_RegistrationDriver), true);

	}
	@Test (priority = 2)
	public void verify_registration_step1() {
		email_main = _Registration.enter_email_id();
		_Registration.enter_password();
		_Registration.enter_mobile_number();
		_Registration.click_on_submit_button();

	}

	@Test(priority=3)
	public void verify_registration_page1_collect_view() {
		assertEquals(LogAnalyzer.analyzePageViewRequest(_RegistrationDriver), true);
	}

	@Test (priority = 4,dependsOnMethods= {"verify_registration_step1"})
	private void verify_fill_personal_detail() {
		_Registration.fill_and_submit_personal_details(email_main, false);
	}

	@Test (priority = 5,dependsOnMethods= {"verify_fill_personal_detail"})
	private void verify_fill_and_submit_experience() {
		_Registration.fill_and_submit_job_details();
	}

	@Test (priority = 6,dependsOnMethods= {"verify_fill_and_submit_experience"})
	private void verify_fill_and_submit_education_details() {
		_Registration.fill_and_submit_education_details();
	}

	@Test (priority = 7,dependsOnMethods= {"verify_fill_and_submit_education_details"})
	private void verify_fill_and_submit_skill_details() {
		_Registration.fill_and_submit_skill_details();
	}

	@Test(priority=8)
	public void verify_resume_upload_page_collect_view() {
		assertEquals(LogAnalyzer.analyzePageViewRequest(_RegistrationDriver), true);

	}

	@Test (priority = 9,dependsOnMethods= {"verify_fill_and_submit_skill_details"})
	private void verify_is_resume_page_opened() {
		_Wait.until(ExpectedConditions.urlContains("uploadresume"));
		assertEquals(_RegistrationDriver.getCurrentUrl(), baseUrl+"/myshine/registration/uploadresume/?from_registration_flow=True");
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _RegistrationDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_RegistrationDriver!=null)
			_RegistrationDriver.quit();

	}

}
