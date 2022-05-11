package com.shine.tests;

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
import org.testng.Assert;

public class Test_ForgotPassword extends TestBaseSetup {

	static WebDriver _FPdriver;
	static WebDriverWait _Wait;
   
	//By loginLink          = By.partialLinkText("Sign In");
	By forgotPasswordLink = By.linkText("Forgot Password");
	By useremailid        = By.id("candidateemailid");
	By submitBtn          = By.id("id_fpSubmit");
	By success_msg        = By.id("id_successMsg_fp");
	By closeforgPass      = By.cssSelector(".ui-icon.ui-icon-closethick");


	@BeforeClass
	public void TestSetup() {
		_FPdriver = getDriver(_FPdriver);
		OpenBaseUrl(_FPdriver);
		//_Utility.clickOnNotification(_FPdriver);
		//_Wait = new WebDriverWait(_FPdriver, 15);
		_Utility.Thread_Sleep(1000);
		APP_LOGS.debug("[Starting Forgot Password Test with Email id: "+email_main+" ]");
	}

	@Test
	public void Test_ForgetPassword() {
		//_FPdriver.findElement(loginLink).click();
		_Utility.Thread_Sleep(1000);
		_FPdriver.findElement(By.partialLinkText("Sign in")).click();
		_Utility.Thread_Sleep(1000);
		_FPdriver.findElement(forgotPasswordLink).click();
		_FPdriver.findElement(useremailid).click();
		_FPdriver.findElement(useremailid).sendKeys(email_main);
		_Utility.Thread_Sleep(1000);
		//_Wait.until(ExpectedConditions.visibilityOfElementLocated(submitBtn));
		//_Wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
		_FPdriver.findElement(submitBtn).click();
		_Utility.Thread_Sleep(2000);
		//_Wait.until(ExpectedConditions.visibilityOfElementLocated(success_msg));
		Assert.assertEquals(_FPdriver.findElement(success_msg).getText(), "A link to reset your Shine Password has been sent to your Email Id", "Forgot password successful message is not shown");
		_FPdriver.findElement(closeforgPass).click();
	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _FPdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_FPdriver!=null)
			_FPdriver.quit();

	}

}

