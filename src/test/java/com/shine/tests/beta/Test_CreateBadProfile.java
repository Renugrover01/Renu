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

public class Test_CreateBadProfile extends TestBaseSetup {

	WebDriver _CreateBadProfileDriver;
	Registration _Registration;
	Resume _Resume;
	JobAlert _JobAlert;


	@BeforeClass
	public void TestSetup() {
		_CreateBadProfileDriver = getDriver(_CreateBadProfileDriver);
		_Registration = new Registration(_CreateBadProfileDriver);
		_Resume = new Resume(_CreateBadProfileDriver);
		_JobAlert = new JobAlert(_CreateBadProfileDriver);
		APP_LOGS.debug("****Starting registration tests***");
	}


	@Test (priority = 0)
	public void open_registration_page() {
		_Registration.open_registration_page();
		_Registration.isFirstRegistrationPageOpened();
	}

	@Test (priority = 1)
	public void verify_registration_step1() {
		email_main = _Registration.enter_email_id();
		_Registration.enter_password();
		_Registration.enter_mobile_number();
		_Registration.click_on_submit_button();

	}

	@Test (priority = 2,dependsOnMethods= {"verify_registration_step1"})
	private void verify_fill_personal_detail() {
		_Registration.fill_and_submit_personal_details(email_main, false);
	}

	@Test (priority = 3,dependsOnMethods= {"verify_fill_personal_detail"})
	private void verify_fill_and_submit_experience() {
		_Registration.fill_and_submit_job_details("Software Fuck Developer", "HCL FUCK Limited", true);
	}

	@Test (priority = 4,dependsOnMethods= {"verify_fill_and_submit_experience"})
	private void verify_fill_and_submit_education_details() {
		_Registration.fill_and_submit_education_details("Fuck Institute of Technology");
	}

	@Test (priority = 5,dependsOnMethods= {"verify_fill_and_submit_education_details"})
	private void verify_fill_and_submit_skill_details() {
		_Registration.fill_and_submit_skill_details("fuck");
	}

	@Test (priority=6,dependsOnMethods= {"verify_fill_and_submit_skill_details"})
	public void verify_upload_resume() {
		_Resume.upload_resume("resumefile");
	}

	@Test (priority=7, dependsOnMethods= {"verify_upload_resume"})
	public void verify_reg_create_job_alert() {
		_JobAlert.fill_and_submit_job_alert();
		Assert.assertEquals(_CreateBadProfileDriver.getTitle(),"Shine.com - My Shine | Home");

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _CreateBadProfileDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_CreateBadProfileDriver!=null)
			_CreateBadProfileDriver.quit();

	}


}
