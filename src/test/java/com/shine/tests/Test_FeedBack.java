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

import com.shine.base.TestBaseSetup;

public class Test_FeedBack  extends TestBaseSetup{
	WebDriver _FeedbackDriver;
	WebDriverWait _Wait;

	By feedback_link = By.cssSelector(".cls_req_feedback");
	By email_id_text = By.cssSelector("[name='txt_email']");
	By feedback_msg =  By.cssSelector("[name='txt_msg']");
	//By feedback_submit_button = By.cssSelector(".btn btn-yellow mr-2");
	By feedback_message = By.cssSelector(".dvpopupmsg");
	By ok_button = By.cssSelector("[value='Ok']");
	
	
	@BeforeClass
	public void TestSetup() {
		_FeedbackDriver = getDriver(_FeedbackDriver);
		_Wait = new WebDriverWait(_FeedbackDriver, 20);
		OpenBaseUrl(_FeedbackDriver);
		_Utility.scrollTOElement(feedback_link, _FeedbackDriver);
	}

	
	@Test(priority = 0)
	public void verify_feedback_form() {
		_FeedbackDriver.findElement(feedback_link).click();
		_Utility.Thread_Sleep(1000);
		_FeedbackDriver.findElement(email_id_text).sendKeys(email_new);
		_FeedbackDriver.findElement(feedback_msg).sendKeys("Testing!!! Kindly ignore");
		_FeedbackDriver.findElement(By.name("btn_sub")).click();
	}
	
	@Test(priority = 1, dependsOnMethods= {"verify_feedback_form"})
	public void test_feedback_form_submit_message() {
		_Utility.Thread_Sleep(1000);
		String actual_message = _FeedbackDriver.findElement(feedback_message).getText();
		assertEquals(actual_message, "Thank You for your feedback");
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(ok_button));
		_FeedbackDriver.findElement(ok_button).click();
		
	}
	
	@Test(priority = 2, dependsOnMethods= {"test_feedback_form_submit_message"})
	public void test_feedback_form_submit() {
		_Utility.Thread_Sleep(1000);
		_Wait.until(ExpectedConditions.invisibilityOfElementLocated(ok_button));
	}


	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_FeedbackDriver!=null)
			_FeedbackDriver.quit();
	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _FeedbackDriver);
	}

}
