package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.listener.LogAnalyzer;

public class Test_PixelRequest extends TestBaseSetup{

	private String url = "/registration/?keyword=Jobs%20in%20Ajmer&vendorid=782500106&utm_source=google&utm_medium=cpc&utm_campaign=Top%20Cities-%20Exact-Mobile-Contexual&utm_adgroup=Mumbai&utm_placement={placement:define}&utm_content=exitOverlayT";
	private String url2 = "/sem/registration/?keyword=Jobs%20in%20Lucknow&Vendorid=780039001&utm_source=google&utm_medium=cpc&utm_campaign=Top%20Cities-%20BMM_Mobile&utm_adgroup=Lucknow&utm_keyword={keyword:define}";
	private String url3 = "/job-search/ht-media-jobs?vendorid=782600006&utm_campaign=registeroverlayT";

	WebDriver _PixelCheckDriver;
	WebDriverWait _Wait;
	Test_QuickRegistration _QuickRegistration = new Test_QuickRegistration();
	Test_RegistrationSem _RegistrationSem = new Test_RegistrationSem();

	By jsrpSignUp			= By.cssSelector("[value='Sign up']");
	By quickRegisterDiv		= By.id("id_jsrp_resumeUpload_div");
	By fresherSelect     	= By.id("id_is_fresher_0");
	By desiredFA         	= By.xpath("//*[starts-with(@id, 'id_preferred_fa_2')]");
	By desiredFA2        	= By.xpath("//*[starts-with(@id, 'id_preferred_fa_4')]");
	static By signUpBtn		= By.cssSelector("[type='submit']");

	@BeforeClass
	public void TestSetup() {
		_PixelCheckDriver = getDriver(_PixelCheckDriver);
		_Wait = new WebDriverWait(_PixelCheckDriver, 15);
		_PixelCheckDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}


	@Test(priority=0)
	public void test_Pixel_Flow1(){
		_PixelCheckDriver.navigate().to(baseUrl+url);
		String email = fill_and_submit_registartion_page1(_PixelCheckDriver);
		fill_and_submit_registartion_page2(email, _PixelCheckDriver);
		upload_resume(_PixelCheckDriver);
		assertEquals(LogAnalyzer.analyzeSpecificRequest(_PixelCheckDriver), true);
	}

	@Test(priority=1)
	public void test_Pixel_Flow2(){
		_PixelCheckDriver.navigate().to(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(5000);
		 _PixelCheckDriver.navigate().to(baseUrl+url2);
		String email = fill_and_submit_registartion_page1(_PixelCheckDriver);
		fill_and_submit_registartion_page2(email, _PixelCheckDriver);
		upload_resume(_PixelCheckDriver);
		assertEquals(LogAnalyzer.analyzeSpecificRequest(_PixelCheckDriver), true);
	}
	
	@Test(priority=2)
	public void test_Pixel_Flow3(){
		_PixelCheckDriver.navigate().to(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(3000);
		_PixelCheckDriver.navigate().to(baseUrl+url3);
		_Utility.Thread_Sleep(1000);
		String email_id = _QuickRegistration.fill_Email_Pwd(_PixelCheckDriver, true);
		_PixelCheckDriver.findElement(jsrpSignUp).click();
		//Reg page 2
		fill_and_submit_registartion_page2(email_id, _PixelCheckDriver);
		_Utility.Thread_Sleep(1000);
		upload_resume(_PixelCheckDriver);
		_Utility.Thread_Sleep(6000);
		assertEquals(LogAnalyzer.analyzeSpecificRequest(_PixelCheckDriver), true);

	}


	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_PixelCheckDriver!=null)
			_PixelCheckDriver.quit();

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _PixelCheckDriver);
	}
	
	
	
	/***
	 * fill_and_submit_registartion_page1
	 * @param _PixelCheckDriver
	 * @return
	 */
	public String fill_and_submit_registartion_page1(WebDriver _PixelCheckDriver) {
		String[] email_id = Test_Registration.ExecuteRegistrationFlow(false, "/registration/parser/flow-2/", _PixelCheckDriver);
		_Utility.Thread_Sleep(2000);
		_PixelCheckDriver.findElement(signUpBtn).click();
		_Utility.Thread_Sleep(4000);
		return email_id[0];
	}

	/**
	 * fill_and_submit_registartion_page2
	 * @param email
	 * @param _PixelCheckDriver
	 */
	public void fill_and_submit_registartion_page2(String email, WebDriver _PixelCheckDriver) {
		Test_Registration.executeTestCase(false, false, "Flow 2 Exp",email, _PixelCheckDriver);
		_Utility.Thread_Sleep(3000);
	}

	/**
	 * upload_resume
	 * @param _PixelCheckDriver
	 */
	public void upload_resume(WebDriver _PixelCheckDriver) {
		//UPLOAD VALID RESUME
		Test_MidoutRegistration.resumeFileUpload(_PixelCheckDriver, "resumefile");
		_Utility.Thread_Sleep(5000);
	}





}
