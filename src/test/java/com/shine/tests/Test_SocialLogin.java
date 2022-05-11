package com.shine.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_SocialLogin extends TestBaseSetup{

	private static WebDriver _SocialLoginDriver;
	private static 	WebDriverWait _Wait;

	/*Google*/
	By loginWithGoogleLink	= By.xpath("//*[contains(@class, 'social-login--btn google_button')]//*[text() = 'Sign-in with Google']");
	By signInBtn			= By.partialLinkText("Sign in");
	By emailTxtField		= By.cssSelector("[type='email']");
	By passField			= By.cssSelector("[type='password']");
	By nextBtn				= By.xpath("//span[contains(text(),'Next')]");
	/*Linkedin*/
	By loginThrLinkedinkLink= By.xpath("//*[contains(text(),'Sign-in with Linkedin')]");
	By linkedEmail			= By.cssSelector("[type='text']");
	By linkedPassword		= By.cssSelector("[type='password']");
	By linkedSignIn			= By.cssSelector("[type='submit']");
	By linkedinAllowBtn		= By.xpath("//button[contains(text(), 'Allow')]");




	@BeforeClass	
	public void TestSetup() {
		_SocialLoginDriver = getDriver(_SocialLoginDriver);
		OpenBaseUrl(_SocialLoginDriver);
		_Utility.clickOnNotification(_SocialLoginDriver);
		_Wait = new WebDriverWait(_SocialLoginDriver, 20);
	}


	/*
	 * @Test(priority=0) public void verify_google_login() {
	 * open_login_popup(_SocialLoginDriver);
	 * _Wait.until(ExpectedConditions.elementToBeClickable(loginWithGoogleLink));
	 * _SocialLoginDriver.findElement(loginWithGoogleLink).click();
	 * _Utility.Thread_Sleep(5000); String parent_window =
	 * switch_to_window(_SocialLoginDriver); google_login(_SocialLoginDriver);
	 * _SocialLoginDriver.switchTo().window(parent_window);
	 * _Utility.Thread_Sleep(5000); assertEquals(_SocialLoginDriver.getCurrentUrl(),
	 * baseUrl+"/myshine/home/");
	 * _SocialLoginDriver.get(baseUrl+"/myshine/logout/");
	 * 
	 * }
	 */
	
	@Test(priority=0)
	public void verify_linkedin_login_cancel() {
		_SocialLoginDriver.get(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(2000);
		_SocialLoginDriver.get(baseUrl);
		open_login_popup(_SocialLoginDriver);
		_Wait.until(ExpectedConditions.elementToBeClickable(loginThrLinkedinkLink));
		_SocialLoginDriver.findElement(loginThrLinkedinkLink).click();
		_Utility.Thread_Sleep(3000);
		_SocialLoginDriver.findElement(By.linkText("Cancel")).click();
		_Utility.Thread_Sleep(3000);
		String actual_msg = _SocialLoginDriver.findElement(By.id("status")).getText().trim();
		assertEquals(actual_msg, "User cancelled Linkedin Signin.");


	}


	@Test(priority=1)
	public void verify_linkedin_login() {
		_SocialLoginDriver.get(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(3000);
		_SocialLoginDriver.get(baseUrl);
		open_login_popup(_SocialLoginDriver);
		_Wait.until(ExpectedConditions.elementToBeClickable(loginThrLinkedinkLink));
		_SocialLoginDriver.findElement(loginThrLinkedinkLink).click();
		_Utility.Thread_Sleep(2000);
		linkedin_login(_SocialLoginDriver);
		_Utility.Thread_Sleep(3000);
		if(baseUrl.contains("pp-www.shine.com"))
			assertEquals(_SocialLoginDriver.getCurrentUrl(), "https://www.shine.com/myshine/home/");
		else
			assertEquals(_SocialLoginDriver.getCurrentUrl(), baseUrl+"/myshine/home/");


	}




	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _SocialLoginDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_SocialLoginDriver!=null)
			_SocialLoginDriver.quit();

	}



	/**
	 * Open login page
	 * @param _SocialLoginDriver
	 */
	public void open_login_popup(WebDriver _SocialLoginDriver) {
		_Utility.Thread_Sleep(2000);
		_SocialLoginDriver.findElement(signInBtn).click();
		_Utility.Thread_Sleep(2000);
	}

	/**
	 * Switch across multiple window
	 * @param _SocialLoginDriver
	 * @return
	 */
	public String switch_to_window(WebDriver _SocialLoginDriver) {
		String parentWindow = _SocialLoginDriver.getWindowHandle();
		for(String tab: _SocialLoginDriver.getWindowHandles()) {
			_SocialLoginDriver.switchTo().window(tab);
		}
		return parentWindow;
	}


	/**
	 * Login to google account
	 * @param _SocialLoginDriver
	 */
	public void google_login(WebDriver _GoogleLoginDriver) {
		_Utility.Thread_Sleep(3000);
		_GoogleLoginDriver.findElement(emailTxtField).sendKeys("manvi.agarwal191018@gmail.com");
		_GoogleLoginDriver.findElement(nextBtn).click();
		_Utility.Thread_Sleep(3000);
		_GoogleLoginDriver.findElement(passField).sendKeys("Password@1234");
		_GoogleLoginDriver.findElement(nextBtn).click();
		_Utility.Thread_Sleep(3000);
	}


	/**
	 * Linkedin login
	 * @param _LinkedinLogindriver
	 */
	private void linkedin_login(WebDriver _LinkedinLogindriver) {
		String actual_url = _LinkedinLogindriver.getCurrentUrl();
		Assert.assertTrue(actual_url.contains("linkedin"), actual_url);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(linkedEmail));
		_LinkedinLogindriver.findElement(linkedEmail).sendKeys("manvi.agarwal191018@gmail.com");
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(linkedPassword));
		_LinkedinLogindriver.findElement(linkedPassword).sendKeys("Password@1234");
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(linkedSignIn));
		_LinkedinLogindriver.findElement(linkedSignIn).click();
		_Utility.Thread_Sleep(1000);
		try {
			_LinkedinLogindriver.findElement(linkedinAllowBtn).click();
		}
		catch(Throwable t) {
			APP_LOGS.debug("Allow button not appeared");
		}
		_Utility.Thread_Sleep(3000);
	}





}
