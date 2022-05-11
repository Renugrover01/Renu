package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.GenerateAutoLoginToken;

public class Test_VerifyEmailLink extends TestBaseSetup {

	WebDriver _VeirfyEmailDriver;
	WebDriverWait _Wait;
	String token = "";
	String verify_email_url = "";

	@BeforeClass
	public void TestSetup() {
		_VeirfyEmailDriver = getDriver(_VeirfyEmailDriver);
		_Wait = new WebDriverWait(_VeirfyEmailDriver, 10);
		GenerateAutoLoginToken _GenerateAutoLoginToken = new GenerateAutoLoginToken();
		token = _GenerateAutoLoginToken.getToken(email_main, _VeirfyEmailDriver);
		verify_email_url = baseUrl+"/myshine/verify-email/"+token+"/";
	}

	
	@Test (priority=1) 
	private  void open_verify_email_link_on_mailinator() {
		_VeirfyEmailDriver.get(verify_email_url);
		_Utility.Thread_Sleep(2000);
		_Utility.closeNotification(_VeirfyEmailDriver);
		String actual_msg = _VeirfyEmailDriver.findElement(By.cssSelector(".login_con_div h1")).getText().trim();
		assertEquals(actual_msg, "Your email has been verified");
	}
	
	@Test (priority=2) 
	private  void verify_email_link_on_profile() {
		_VeirfyEmailDriver.get(baseUrl+"/myshine/logout");
		_Utility.Thread_Sleep(2000);
		loggedInShine(_VeirfyEmailDriver, email_main, pass_new);
		_Utility.Thread_Sleep(2000);
		_VeirfyEmailDriver.get(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(1000);
		String html = _VeirfyEmailDriver.findElement(By.id("id_em_email")).getAttribute("outerHTML");
		assertTrue(html.contains("class=\"tick\""), html);
	}
	


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _VeirfyEmailDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_VeirfyEmailDriver!=null)
			_VeirfyEmailDriver.quit();

	}

	
	public void change_password_page() {
		_VeirfyEmailDriver.findElement(By.id("id_new_password")).sendKeys("123456");
		_VeirfyEmailDriver.findElement(By.id("id_confirm_password")).sendKeys("123456");
		_VeirfyEmailDriver.findElement(By.id("btn_password")).click();
		_Utility.Thread_Sleep(1000);
		Alert alert = _VeirfyEmailDriver.switchTo().alert();
		String alert_msg = alert.getText();
		alert.accept();
		assertEquals(alert_msg, "Your password has been reset successfully");
	}
}
