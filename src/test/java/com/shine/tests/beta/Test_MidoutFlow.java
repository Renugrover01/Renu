package com.shine.tests.beta;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.page.MSiteRegistration;
import com.shine.page.Registration;

public class Test_MidoutFlow extends TestBaseSetup {


	By skipButton		 = By.linkText("Skip");
	By resumeUploadid = By.id("id_resume_file");

	WebDriver _MidoutFlowDriver;
	WebDriver _MSiteDriver;
	WebDriverWait wait;
	String email;
	Registration _Registration;
	MSiteRegistration _MSiteRegistration;


	@BeforeClass
	public void TestSetup()  {
		APP_LOGS.debug("Starting the midout test");
		_MidoutFlowDriver = getDriver(_MidoutFlowDriver);
		_Registration = new Registration(_MidoutFlowDriver);
		wait = new WebDriverWait(_MidoutFlowDriver, 15);
	}

	@Test (priority = 0)
	private void fill_registration_page_1() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/registration/parser/");
		email = _Registration.fill_and_submit_first_registration_page();
	}

	@Test (priority = 1)
	private void verify_access_home_page() {
		_Utility.Thread_Sleep(3000);
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/home/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 2)
	private void verify_access_search_page() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/job-search/java-jobs/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 3)
	private void verify_access_profile_page() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 4)
	private void verify_access_inbox() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/inbox/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 5)
	private void verify_access_account_setting() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/accountsettings/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}


	@Test (priority = 6)
	private void verify_register_user() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/registration/details/parser/");
		_Registration.fill_and_submit_second_registration_page(email, false);
	}

	@Test (priority = 7)
	private void verify_access_home_page_after_reg_pag2() {
		_Utility.Thread_Sleep(3000);
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/home/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/home/");
	}

	@Test (priority = 8)
	private void verify_access_search_page_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/job-search/java-jobs/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/job-search/java-jobs");
	}

	@Test (priority = 9)
	private void verify_access_profile_page_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/uploadresume/");
	}

	@Test (priority = 10)
	private void verify_access_inbox_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/inbox/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/uploadresume/");
	}

	@Test (priority = 11)
	private void verify_access_account_setting_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/accountsettings/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/uploadresume/");
	}


	@Test (priority = 12)
	private void verify_mo2r0_user() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/logout");
		_Utility.Thread_Sleep(3000);
		_MidoutFlowDriver.navigate().to(baseUrl+"/registration/parser/");
		email = _Registration.fill_and_submit_first_registration_page();
	}



	@Test (priority = 13)
	private void verify_msite_mo2r0_user() {
		_MSiteDriver = getDriver(_MSiteDriver, "chromeMobile");
		_MSiteRegistration = new MSiteRegistration(_MSiteDriver);
		_MSiteRegistration.login_in_msite(email, pass_hc);
		_Utility.Thread_Sleep(1000);
		_MSiteRegistration.fill_personal_details();
		_Utility.Thread_Sleep(1000);
		_MSiteRegistration.click_and_skip_all_section();
		_Utility.Thread_Sleep(2000);
		_MSiteRegistration.upload_resume("resumefile");
	}


	@Test (priority = 14)
	private void verify_access_home_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/home/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 15)
	private void verify_access_search_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/job-search/java-jobs/");
		_Utility.Thread_Sleep(5000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/job-search/java-jobs");
	}

	@Test (priority = 16)
	private void verify_access_myprofile_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 17)
	private void verify_access_inbox_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/inbox/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 18)
	private void verify_access_account_setting_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/accountsettings/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _MidoutFlowDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_MidoutFlowDriver!=null)
			_MidoutFlowDriver.quit();

	}


}
