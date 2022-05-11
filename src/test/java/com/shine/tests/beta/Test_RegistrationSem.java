package com.shine.tests.beta;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.page.JobAlert;
import com.shine.page.Registration;
import com.shine.page.Resume;

public class Test_RegistrationSem extends TestBaseSetup {

	WebDriver _RegistrationSemDriver;
	Registration _Registration;
	Resume _Resume;
	JobAlert _JobAlert;
	String fresher_email_id;

	@BeforeClass
	public void TestSetup() {
		_RegistrationSemDriver = getDriver(_RegistrationSemDriver);
		_Registration = new Registration(_RegistrationSemDriver);
		_Resume = new Resume(_RegistrationSemDriver);
		_JobAlert = new JobAlert(_RegistrationSemDriver);
		APP_LOGS.debug("****Starting registration tests***");
	}


	@Test (priority = 0)
	public void open_registration_page() {
		_Registration.open_sem_registration_page_url();
		_Registration.isFirstSemRegistrationPageOpened();
	}


	@Test (priority = 1)
	public void verify_registration_step1() {
		fresher_email_id = _Registration.enter_email_id();
		_Registration.enter_password();
		_Registration.enter_mobile_number();
		_Registration.click_on_submit_button();
	}


	@Test (priority = 2,dependsOnMethods= {"verify_registration_step1"})
	private void verify_fill_personal_detail() {
		_Registration.fill_and_submit_personal_details(fresher_email_id, false);
	}

	@Test (priority = 3,dependsOnMethods= {"verify_fill_personal_detail"})
	private void verify_fill_and_submit_experience() {
		_Registration.fill_and_submit_job_details();
	}

	@Test (priority = 4,dependsOnMethods= {"verify_fill_and_submit_experience"})
	private void verify_fill_and_submit_education_details() {
		_Registration.fill_and_submit_education_details();
	}

	@Test (priority = 5,dependsOnMethods= {"verify_fill_and_submit_education_details"})
	private void verify_fill_and_submit_skill_details() {
		_Registration.fill_and_submit_skill_details();
	}

	@Test (priority=6,dependsOnMethods= {"verify_fill_and_submit_skill_details"})
	public void verify_upload_resume() {
		_Resume.upload_resume("resumefile");
	}

	@Test (priority=7, dependsOnMethods= {"verify_upload_resume"})
	public void verify_reg_create_job_alert() {
		_JobAlert.fill_and_submit_job_alert();
		Assert.assertEquals(_RegistrationSemDriver.getTitle(),"Shine.com - My Shine | Home");
	}
	
	@Test (priority=8, dependsOnMethods= {"verify_upload_resume"})
	public void verify_home_page_opened() {
		Assert.assertEquals(_RegistrationSemDriver.getCurrentUrl(),baseUrl+"/myshine/home/");
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _RegistrationSemDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_RegistrationSemDriver!=null)
			_RegistrationSemDriver.quit();

	}



}
