package com.shine.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_ShineEmail extends TestBaseSetup  {

	WebDriver _Maildriver;
	private By goButton   	= By.cssSelector(".btn.btn-dark");
	private By inboxfield 	= By.id("inboxfield");
	private By inboxCount 	= By.cssSelector("#inboxpane .even");
	private String murl   	= "";
	private String splitEmail   	= "";

	@BeforeClass
	public void TestSetup()
	{
		_Maildriver = getDriver(_Maildriver);
		murl = "https://www.mailinator.com/v3/index.jsp?zone=public&query=";
		splitEmail = email_main.replace("@mailinator.com", "");
		APP_LOGS.debug("[Test Case: Test Mailinator Inbox: Split email id "+splitEmail+" ]");
	}

	/*
	 * @Method - Check user email
	 * assertion for 5 mails. Verify email, verify email from update flow,forgot password, 2 alert creation.
	 * hence check for 5 rows.
	 */
	@Test 
	public void test_MailinatorInbox() {
		openMailinatorSite();
		_Utility.Thread_Sleep(6000);
		int email_count = getEmailCount();
		APP_LOGS.debug(email_main+" contains email: "+email_count);
		try {
			Assert.assertTrue(email_count>=4, "Exepcted Email Count is 4 but found :"+email_count);
		}catch(AssertionError aex) {
			APP_LOGS.error("Mailinator hack:");
			_Utility.Thread_Sleep(3000);
			openMailinatorSite();
			_Utility.Thread_Sleep(6000);
			 email_count = getEmailCount();
			Assert.assertTrue(email_count>=4, "Exepcted Email Count is 4 but found :"+email_count);
		}
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Maildriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Maildriver!=null)
			_Maildriver.quit();

	}

	public void openMailinatorSite(){
		_Maildriver.get(murl+splitEmail);
	}

	public void enterEmailid(String email){
		_Maildriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		_Utility.Thread_Sleep(2000);
		_Maildriver.findElement(inboxfield).sendKeys(email);
	}

	public void clickOnSubmit(){
		_Maildriver.findElement(goButton).click();
		_Utility.Thread_Sleep(2000);
	}

	public int getEmailCount(){
		/*Mailinator popup handler hack*/
		try {
			Alert alert = _Maildriver.switchTo().alert();
			APP_LOGS.fatal("****Mailinator popup handled**** "+alert.getText());
			alert.accept();
			_Utility.Thread_Sleep(2000);
		}
		catch(Throwable t) {
			APP_LOGS.fatal("****No popup****");
		}
		List <WebElement> emailCount = _Maildriver.findElements(inboxCount);
		return emailCount.size();		

	}
}
