package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_MobileAppLink extends TestBaseSetup{

	WebDriver _Msecdriver = null;
	WebDriverWait _Wait;
	
	By phoneNoTxt       = By.xpath(".//*[@id='id_phn']");
	By submitRequestBtn = By.id("link_snd_btn");
	By okBtn            = By.cssSelector("div.divokbutton.okbutton.mobile_app_dwnload");
	
	@BeforeClass
	public void TestSetup() {
		_Msecdriver = getDriver(_Msecdriver);
		_Msecdriver.get(TestBaseSetup.baseUrl + "/mobileapp/");
		_Utility.set_flag_checkTimeStamp(_Msecdriver);
		_Msecdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		_Wait = new WebDriverWait(_Msecdriver, 10);
	}


	@Test(priority=0)
	public void test_MobileAppLink(){
		click_on_get_app_link();
		check_popup_msg();
		click_on_ok_popup_button();
	}
	
	
	/*
	 * @Test(priority=1) public void test_jsrp_mobilelink() {
	 * _Msecdriver.get(TestBaseSetup.baseUrl + "/job-search/java-jobs");
	 * click_on_get_app_link(); check_popup_msg(); click_on_ok_popup_button(); }
	 */
	
	

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Msecdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_Msecdriver!=null)
			_Msecdriver.quit();

	}
	
	
    /**
     * click_on_get_app_link
     */
	public void click_on_get_app_link() {
		_Utility.Thread_Sleep(2000);
		_Msecdriver.findElement(phoneNoTxt).click();
		_Msecdriver.findElement(phoneNoTxt).sendKeys("9876556789");;
		_Msecdriver.findElement(submitRequestBtn).click();
		_Utility.Thread_Sleep(1000);

	}
	
	/**
	 * check_popup_msg
	 */
	public void check_popup_msg() {
		String actual_msg = _Msecdriver.findElement(By.cssSelector(".customform1 .dvpopupmsg1")).getText();
		assertEquals(actual_msg, "Thank you for showing interest in Shine.com mobile app. You will soon receive a SMS with link to download the Shine mobile app");

	}
	
	/**
	 * click_on_popup_button
	 */
	public void click_on_ok_popup_button() {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(okBtn));
		_Msecdriver.findElement(okBtn).click();
		_Utility.Thread_Sleep(2000);
		_Wait.until(ExpectedConditions.invisibilityOfElementLocated(okBtn));
	}


}
