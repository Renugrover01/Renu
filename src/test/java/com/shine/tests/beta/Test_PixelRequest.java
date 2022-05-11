package com.shine.tests.beta;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.listener.LogAnalyzer;
import com.shine.page.Registration;
import com.shine.page.Resume;

public class Test_PixelRequest extends TestBaseSetup{

	private String url = "/registration/?keyword=Jobs%20in%20Ajmer&vendorid=782500106&utm_source=google&utm_medium=cpc&utm_campaign=Top%20Cities-%20Exact-Mobile-Contexual&utm_adgroup=Mumbai&utm_placement={placement:define}&utm_content=exitOverlayT";
	private String url2 = "/sem/registration/?keyword=Jobs%20in%20Lucknow&Vendorid=780039001&utm_source=google&utm_medium=cpc&utm_campaign=Top%20Cities-%20BMM_Mobile&utm_adgroup=Lucknow&utm_keyword={keyword:define}";
	private String url3 = "/job-search/ht-media-jobs?vendorid=782600006&utm_campaign=registeroverlayT";

	WebDriver _PixelCheckDriver;
	WebDriverWait _Wait;
	Registration _Registration;
	Resume _Resume;
	String pixel_email = "";


	@BeforeClass
	public void TestSetup() {
		_PixelCheckDriver = getDriver(_PixelCheckDriver);
		_Registration = new Registration(_PixelCheckDriver);
		_Resume = new Resume(_PixelCheckDriver);
		_Wait = new WebDriverWait(_PixelCheckDriver, 15);
		_PixelCheckDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}


	@Test(priority = 0)
	public void test_Pixel_Flow1(){
		perform_registration_flow_test(baseUrl+url, false);
	}

	
	@Test(priority = 1)
	public void test_Pixel_Flow2(){
		_PixelCheckDriver.navigate().to(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(5000);
		perform_registration_flow_test(baseUrl+url2, false);
	}

	@Test(priority = 2)
	public void test_Pixel_Flow3(){
		_PixelCheckDriver.navigate().to(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(3000);
		perform_registration_flow_test(baseUrl+url3, true);

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

	/**
	 * 
	 * @param url
	 * @param isJSRPFlow
	 */
	public void perform_registration_flow_test(String url, boolean isJSRPFlow) {
		_PixelCheckDriver.navigate().to(url);
		if(isJSRPFlow)
			_Registration.perform_jsrp_complete_registration_flow_test(true);
		else
			_Registration.perform_complete_registration_flow_test();
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzeSpecificRequest(_PixelCheckDriver), true);
	}






}
