package com.shine.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.shine.tests.Test_Registration;
import com.shine.tests.Test_PixelRequest;
import com.shine.base.TestBaseSetup;

public class Test_MidoutFlow extends TestBaseSetup {


	By skipButton		 = By.linkText("Skip");
	By resumeUploadid = By.id("id_resume_file");

	WebDriver _MidoutFlowDriver;
	WebDriver _MSiteDriver;
	WebDriverWait wait;
	String[] email;


	@BeforeClass
	public void TestSetup()  {
		APP_LOGS.debug("Starting the midout test");
		_MidoutFlowDriver = getDriver(_MidoutFlowDriver);
		wait = new WebDriverWait(_MidoutFlowDriver, 15);
	}

	@Test (priority = 0)
	private void fill_registration_page_1() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/registration/parser/");
		email = Test_Registration.ExecuteRegistrationFlow(false, "/registration/parser/flow-2/", _MidoutFlowDriver);
		_MidoutFlowDriver.findElement(Test_PixelRequest.signUpBtn).click();
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
	private void verify_mo2_access_jsrp() {
		Test_Registration.fill_personal_details(email[0], false, false, _MidoutFlowDriver);
		_Utility.Thread_Sleep(2000);
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/job-search/java-jobs/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/job-search/java-jobs");
	}

	
	@Test (priority = 7)
	private void verify_register_user() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/registration/details/parser/");
		Test_Registration.executeTestCase(false, false, "SEM Flow 2 Exp",email[0], _MidoutFlowDriver);
	}

	@Test (priority = 8)
	private void verify_access_home_page_after_reg_pag2() {
		_Utility.Thread_Sleep(3000);
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/home/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/home/");
	}

	@Test (priority = 9)
	private void verify_access_search_page_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/job-search/java-jobs/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/job-search/java-jobs");
	}
	
	@Test (priority = 10)
	private void verify_access_profile_page_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/uploadresume/");
	}

	@Test (priority = 11)
	private void verify_access_inbox_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/inbox/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/uploadresume/");
	}
	
	@Test (priority = 12)
	private void verify_access_account_setting_after_reg_pag2() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/accountsettings/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/uploadresume/");
	}


	@Test (priority = 13)
	private void verify_mo2r0_user() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/logout");
		_Utility.Thread_Sleep(3000);
		_MidoutFlowDriver.navigate().to(baseUrl+"/registration/parser/");
		email = Test_Registration.ExecuteRegistrationFlow(false, "/registration/parser/flow-2/", _MidoutFlowDriver);
		_MidoutFlowDriver.findElement(Test_PixelRequest.signUpBtn).click();
	}



	@Test (priority = 14)
	private void verify_msite_mo2r0_user() {
		_MSiteDriver = getDriver(_MSiteDriver, "chromeMobile");
		_Utility.Thread_Sleep(1000);
		login(email[0], pass_hc, _MSiteDriver);
		_Utility.Thread_Sleep(1000);
		fill_personal_details(_MSiteDriver);
		_Utility.Thread_Sleep(1000);
		for(int i=0;i<3;i++){
			clickOnSkipButton(_MSiteDriver);
			_Utility.Thread_Sleep(1000);
		}
		_MSiteDriver.findElement(By.cssSelector(".applybutton.registerSkip")).click();
		_Utility.Thread_Sleep(2000);
		uploadResume("resumefile", _MSiteDriver);
	}


	@Test (priority = 15)
	private void verify_access_home_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/home/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 16)
	private void verify_access_search_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/job-search/java-jobs/");
		_Utility.Thread_Sleep(5000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/job-search/java-jobs");
	}

	@Test (priority = 17)
	private void verify_access_myprofile_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}

	@Test (priority = 18)
	private void verify_access_inbox_page_after_resume_upload() {
		_MidoutFlowDriver.navigate().to(baseUrl+"/myshine/inbox/");
		_Utility.Thread_Sleep(3000);
		String actual_url =_MidoutFlowDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/registration/details/parser/");
	}
	
	@Test (priority = 19)
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


	/**
	 * 
	 * @param _RegistrationFlowdriver
	 */
	public void clickOnSkipButton(WebDriver _RegistrationFlowdriver){
		WebDriverWait wait = new WebDriverWait(_RegistrationFlowdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(skipButton));
		APP_LOGS.debug("Click on Skip registraion button");
		_RegistrationFlowdriver.findElement(skipButton).click();
	}


	/**
	 * 
	 * @param userEmail
	 * @param password
	 * @param _LoginDriver
	 */
	public void login(String userEmail, String password, WebDriver _LoginDriver) {
		
		String protocol = "https://";
		if(baseUrl.contains("http://"))
			protocol = "http://";
		if(baseUrl.contains("sumosc.shine.com"))
			_LoginDriver.navigate().to(protocol+"sm.shine.com");
		else if(baseUrl.contains("sumosc1.shine.com"))
			_LoginDriver.navigate().to(protocol+"sm1.shine.com");
		else if(baseUrl.contains("pp-www.shine.com"))
			_LoginDriver.navigate().to(protocol+"pp-m.shine.com");
		else if(baseUrl.contains("172."))
			_LoginDriver.navigate().to(protocol+"sm.shine.com");
		else
			_LoginDriver.navigate().to(protocol+"m.shine.com");
		
		_Utility.Thread_Sleep(1000);
		_Utility.set_cookie(_LoginDriver, "InterstitialBanner", "1");
		_Utility.remove_InterstitialBanner(_LoginDriver);
		WebDriverWait _Wait = new WebDriverWait(_LoginDriver, 15);
		APP_LOGS.debug("Calling login Method");
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(By.linkText("Sign In"), _LoginDriver);
		_LoginDriver.findElement(By.linkText("Sign In")).click();
		//_Utility.javascript_click(By.linkText("Sign In"), _LoginDriver);
		_Utility.Thread_Sleep(4000);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		_LoginDriver.findElement(By.name("email")).clear();
		_LoginDriver.findElement(By.name("email")).sendKeys(userEmail);
		_LoginDriver.findElement(By.id("Password")).clear();
		_LoginDriver.findElement(By.id("Password")).sendKeys("123456");
		_LoginDriver.findElement(By.xpath("//input[@name='btn_homepage_signin']")).click();
		_Utility.Thread_Sleep(5000);
		_Utility.closeAppPopup(_LoginDriver);
		_Utility.closeNotification(_LoginDriver);
	}



	/**
	 * Resume uploader method
	 * @param _ResumeUploaddriver
	 */
	public void uploadResume(String filename, WebDriver _ResumeUploaddriver) {
		APP_LOGS.debug("Uploading Resume...");
		String resume = userDirectory+CONFIG.getProperty(filename);
		APP_LOGS.debug(resume);
		_ResumeUploaddriver.findElement(resumeUploadid).sendKeys(resume);
		/*JavascriptExecutor executor = (JavascriptExecutor)_ResumeUploaddriver;
		executor.executeScript("document.getElementById('id_resume_file').setAttribute('value', '"+resume+"')");*/
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug("Resume uploaded Successfully");
	}


	public void fill_personal_details(WebDriver driver) {
		driver.findElement(By.id("id_name")).sendKeys("Abhishek");
		_Utility.Thread_Sleep(2000);
		driver.findElement(By.id("id_location_span")).click();
		_Utility.Thread_Sleep(3000);
		driver.findElement(By.id("id_candidate_location_406")).click();
		_Utility.Thread_Sleep(2000);
		driver.findElement(By.id("id_totalExperience")).sendKeys("5");
		_Utility.Thread_Sleep(2000);
		driver.findElement(By.id("id_gender_0")).click();
		driver.findElement(By.partialLinkText("Save")).click();
		_Utility.Thread_Sleep(2000);
	}



}
