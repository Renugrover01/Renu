package com.shine.tests;

import org.testng.annotations.Test;
import com.shine.base.TestBaseSetup;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;

public class Test_Email_UTM extends TestBaseSetup{

	WebDriver  _EmailUTMDriver;
	WebDriverWait _Wait;
	String url = "/myshine/login/?next=/myshine/activities/%3Futm_campaign%3DactivityMailer%26utm_source%3Dgmail.com%26utm_medium%3Demail%26etm_content%3Dloginviewdetai%257Cl1%257C2017-12-21T11%253A12%253A52.300049%257Cop8CYLN2QF944Tp1txJbFYQ1UT4Cyh6Ep2OlMxqb%2Bvw%253D%257C57c80ec0cce9fb054cb92de8";


	By loginTitleDiv = By.cssSelector(".formBox.pull-right h2");
	
	@BeforeClass
	public void beforeClass() {
		_EmailUTMDriver = getDriver(_EmailUTMDriver);

	}



	@Test(priority = 0)
	public void verify_expire_token_url() {
		_EmailUTMDriver.get(baseUrl+url);
		_Utility.Thread_Sleep(2000);
		String actual_title = _EmailUTMDriver.findElement(loginTitleDiv).getText().trim();
		Assert.assertEquals(actual_title, "Login with email");

	}


	@Test(priority = 1)
	public void verify_expire_token_url_parameters() {
		String actual_url = _EmailUTMDriver.getCurrentUrl();
		APP_LOGS.debug("Encoded url: "+actual_url);
		try {
			actual_url = java.net.URLDecoder.decode(actual_url, "UTF-8");
			APP_LOGS.debug("Decoded url: "+actual_url);
		} catch (UnsupportedEncodingException e) {
			APP_LOGS.error(e.getMessage());
		}
		assertTrue(actual_url.contains("utm_campaign"), actual_url);
		assertTrue(actual_url.contains("utm_source"), actual_url);
		assertTrue(actual_url.contains("etm_content"), actual_url);
		assertTrue(actual_url.contains("utm_medium"), actual_url);

	}



	@Test(priority = 2)
	public void verify_login_with_url_parameters() {
		login(_EmailUTMDriver, email_new, pass_new);
		_Utility.Thread_Sleep(3000);
		String actual_url = _EmailUTMDriver.getCurrentUrl();
		assertTrue(actual_url.contains("utm_campaign"), actual_url);
		assertTrue(actual_url.contains("utm_source"), actual_url);
		assertTrue(actual_url.contains("etm_content"), actual_url);
		assertTrue(actual_url.contains("utm_medium"), actual_url);

	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _EmailUTMDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_EmailUTMDriver!=null)
			_EmailUTMDriver.quit();

	}
	
	/**
	 * Login method
	 * @param _Logindriver
	 * @param email
	 * @throws Exception
	 */
	public static void login(WebDriver _Logindriver, String email, String password) {
		_Utility.Thread_Sleep(2000);
		_Logindriver.findElement(By.id("id_email")).clear();
		APP_LOGS.debug("Shine Login with email id:  "+email);
		_Logindriver.findElement(By.id("id_email")).sendKeys(email);
		_Logindriver.findElement(By.id("id_password")).clear();
		_Logindriver.findElement(By.id("id_password")).sendKeys(password);
		_Logindriver.findElement(By.id("btn_login")).click();
	}

}
