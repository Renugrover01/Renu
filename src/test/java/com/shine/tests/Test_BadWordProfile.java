package com.shine.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_BadWordProfile extends TestBaseSetup{

	WebDriver _BadWordProfileDriver;
	String myProfileURL = "/myshine/myprofile/";
	String contactusURL = "/myshine/contactus/?type=badprofile";
	String badEmailId	=  "abhiresumeupload@gmail.com";

	By popupHeadTitle = By.id("ui-id-1");
	By dvpopupmsg	  = By.className("modal_children");
	By button_editprofile		  = By.id("id_badProfileEdit");
	//By button_contactus		  = By.id("id_badPorfileContactUs");

	String timestamp = "";

	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the jsrp jd test");
		timestamp = generateTimeStamp();
		_BadWordProfileDriver = getDriver(_BadWordProfileDriver);
		_BadWordProfileDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		loggedInShine(_BadWordProfileDriver, badEmailId, "shine@123");
		_Utility.clickOnNotification(_BadWordProfileDriver);
		_Utility.Thread_Sleep(2000);
	}


	@Test(priority=0)
	public void verify_alert() {
		String popupHeadmsgTxt = _BadWordProfileDriver.findElement(popupHeadTitle).getText().trim();
		APP_LOGS.debug("Alert Message: "+popupHeadmsgTxt);
		Assert.assertEquals(popupHeadmsgTxt, "Alert");
	}


	@Test(priority=1)
	public void verify_alert_Text() {
		String popupmsgTxt = _BadWordProfileDriver.findElement(dvpopupmsg).getText().trim();
		String expectedTxt = "We have found objectionable content in your shine profile. As a result your profile has been marked as inactive and will not be available for recruiter views. We request you to kindly remove this content.";
		APP_LOGS.debug("Alert Message: "+popupmsgTxt);
		Assert.assertEquals(popupmsgTxt, expectedTxt);
	}

	@Test(priority=2)
	public void verify_button_Presence() {
		String editProfileBtn = _BadWordProfileDriver.findElement(button_editprofile).getText();
		//String contactUSBtn = _BadWordProfileDriver.findElement(button_contactus).getText().trim();
		Assert.assertEquals(editProfileBtn, "Edit Profile");
		//Assert.assertEquals(contactUSBtn, "Contact Us");
	}

	/*
	 * @Test(priority=3) public void verify_contactUS_btn() {
	 * _Utility.Thread_Sleep(2000); List<WebElement> btnList =
	 * _BadWordProfileDriver.findElements(button); btnList.get(1).click();
	 * _Utility.Thread_Sleep(2000);
	 * Assert.assertEquals(_BadWordProfileDriver.getCurrentUrl(),
	 * baseUrl+contactusURL); }
	 */


	/*
	 * @Test(priority=4) public void verify_editProfile_btn() {
	 * //_BadWordProfileDriver.navigate().back(); _Utility.Thread_Sleep(1000);
	 * _BadWordProfileDriver.findElement(button_editprofile).click();
	 * _Utility.Thread_Sleep(1000);
	 * Assert.assertEquals(_BadWordProfileDriver.getCurrentUrl(),
	 * baseUrl+myProfileURL); }
	 * 
	 * 
	 * 
	 * @Test(priority=5) public void verify_profile_work_exp_edit() {
	 * Assert.assertEquals(_BadWordProfileDriver.getCurrentUrl(),
	 * baseUrl+myProfileURL); _Utility.Thread_Sleep(1000);
	 * _Utility.scrollTOElement(Test_MyProfile.workExpEdit, _BadWordProfileDriver);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.editbutton).click();
	 * _Utility.Thread_Sleep(2000); }
	 * 
	 * @Test(priority=6) public void verify_profile_job_detail_edit() {
	 * _Utility.Thread_Sleep(3000);
	 * _Utility.scrollTOElement(Test_MyProfile.addJobDetails,
	 * _BadWordProfileDriver);
	 * _BadWordProfileDriver.findElement(By.id("id_test_25")).click();
	 * _Utility.Thread_Sleep(3000);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.jobTitle).clear();
	 * _BadWordProfileDriver.findElement(Test_MyProfile.jobTitle).
	 * sendKeys("Fuck Job title "+timestamp);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.companyName).clear();
	 * _BadWordProfileDriver.findElement(Test_MyProfile.companyName).
	 * sendKeys("Fuck Technology "+timestamp);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.companyName).sendKeys(Keys.
	 * TAB); _Utility.Thread_Sleep(2000); try {
	 * _BadWordProfileDriver.findElement(By.cssSelector(".cls_addCompanyInOther")).
	 * click(); }catch(Throwable t) { APP_LOGS.
	 * debug("Handling [Please select from list. To add new] >> sttaus : Not found"
	 * ); }
	 * _BadWordProfileDriver.findElement(Test_MyProfile.saveExperience1).click(); }
	 * 
	 * @Test(priority=7) public void verify_profile_skill_edit() {
	 * _Utility.Thread_Sleep(3000);
	 * _BadWordProfileDriver.findElement(By.cssSelector("#id_test_24 span")).click()
	 * ; _Utility.Thread_Sleep(2000);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.skillName).clear();
	 * _BadWordProfileDriver.findElement(Test_MyProfile.skillName).
	 * sendKeys("Fuck Skill "+timestamp); _Utility.Thread_Sleep(3000);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.saveSkillBtn1).click();
	 * APP_LOGS.debug("BadWord Profile skill edit succesfully"); }
	 * 
	 * @Test(priority=8) public void verify_profile_certification_edit() {
	 * _Utility.Thread_Sleep(3000); _Utility.scrollTOElement(By.id("id_test_22"),
	 * _BadWordProfileDriver);
	 * _BadWordProfileDriver.findElement(By.id("id_test_22")).click();
	 * _Utility.Thread_Sleep(2000);
	 * _BadWordProfileDriver.findElement(Test_MyProfile.certiName).click();
	 * _BadWordProfileDriver.findElement(Test_MyProfile.certiName).
	 * sendKeys("Fuck Certificate "+timestamp);
	 * _BadWordProfileDriver.findElement(By.xpath("(//*[@value='Save'])[2]")).click(
	 * ); APP_LOGS.debug("BadWord Profile certification edit succesfully"); }
	 */



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _BadWordProfileDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_BadWordProfileDriver!=null)
			_BadWordProfileDriver.quit();

	}

	/**
	 * Generate Random number
	 * 
	 * @return
	 */
	public static String generateTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		String timestamp = dateFormat.format(date);
		APP_LOGS.debug("Time Stamp is ...."+timestamp);
		return timestamp;

	}

}