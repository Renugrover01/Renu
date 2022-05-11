package com.shine.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_CheckBadProfile extends TestBaseSetup {
	
	
	WebDriver _CheckBadProfileDriver;
	WebDriverWait _Wait;
	
	String myProfileURL = "/myshine/myprofile/";
	String contactusURL = "/myshine/contactus/?type=badprofile";
	
	By popupHeadTitle = By.id("ui-id-1");
	By dvpopupmsg	  = By.className("modal_children");
	By button_editprofile		  = By.id("id_badProfileEdit");
	//By button_contactus		  = By.id("id_badPorfileContactUs");
	
	@BeforeClass
	public void TestSetup() {
		_CheckBadProfileDriver = getDriver(_CheckBadProfileDriver);
		loggedInShine(_CheckBadProfileDriver, badprofile_email, "123456");
		_Utility.clickOnNotification(_CheckBadProfileDriver);
		_Wait = new WebDriverWait(_CheckBadProfileDriver, 20);
		_Utility.Thread_Sleep(2000);
	}

	

	@Test(priority=0)
	public void verify_alert() {
		String popupHeadmsgTxt = _CheckBadProfileDriver.findElement(popupHeadTitle).getText().trim();
		APP_LOGS.debug("Alert Message: "+popupHeadmsgTxt);
		Assert.assertEquals(popupHeadmsgTxt, "Alert");
	}


	@Test(priority=1)
	public void verify_alert_Text() {
		String popupmsgTxt = _CheckBadProfileDriver.findElement(dvpopupmsg).getText().trim();
		String expectedTxt = "We have found objectionable content in your shine profile. As a result your profile has been marked as inactive and will not be available for recruiter views. We request you to kindly remove this content.";
		APP_LOGS.debug("Alert Message: "+popupmsgTxt);
		Assert.assertEquals(popupmsgTxt, expectedTxt);
	}

	@Test(priority=2)
	public void verify_button_Presence() {
		String editProfileBtn = _CheckBadProfileDriver.findElement(button_editprofile).getText();
		//String contactUSBtn = _CheckBadProfileDriver.findElement(button_contactus).getText().trim();
		Assert.assertEquals(editProfileBtn, "Edit Profile");
		//Assert.assertEquals(contactUSBtn, "Contact Us");
	}

	/*
	 * @Test(priority=3) public void verify_contactUS_btn() {
	 * _Utility.Thread_Sleep(1000);
	 * _CheckBadProfileDriver.findElement(button_contactus).click();
	 * _Utility.Thread_Sleep(1000);
	 * Assert.assertEquals(_CheckBadProfileDriver.getCurrentUrl(),
	 * baseUrl+contactusURL); }
	 */


	@Test(priority=4)
	public void verify_editProfile_btn() {
		//_CheckBadProfileDriver.navigate().back();
		_Utility.Thread_Sleep(1000);
		_CheckBadProfileDriver.findElement(button_editprofile).click();
		_Utility.Thread_Sleep(1000);
		Assert.assertEquals(_CheckBadProfileDriver.getCurrentUrl(), baseUrl+myProfileURL);
	}

	
	
	
	
	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _CheckBadProfileDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_CheckBadProfileDriver!=null)
			_CheckBadProfileDriver.quit();

	}

}
