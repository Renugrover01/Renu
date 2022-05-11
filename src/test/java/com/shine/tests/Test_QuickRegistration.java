package com.shine.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonUtils;

public class Test_QuickRegistration extends TestBaseSetup {
	
	WebDriver _QuickRegistration;
	WebDriverWait _Wait;
	CommonUtils _CommonUtils;
	String qEmailid = "";
	
	static By quickRegisterBtn	= By.cssSelector("[value='Register']");
	By quickRegisterDiv	= By.id("id_jsrp_resumeUpload_div");
		
	static By qr_passwordtxt = By.id("Password");
	static By passwordtxt = By.id("id_raw_password");

	@BeforeClass
	public void TestSetup() {
		_QuickRegistration = getDriver(_QuickRegistration);
		_QuickRegistration.manage().deleteAllCookies();
		_Wait = new WebDriverWait(_QuickRegistration, 15);
		OpenBaseUrl(_QuickRegistration);
		_Utility.clickOnNotification(_QuickRegistration);
	}
	
	
	@Test(priority=25)
	public void test_QuickRegister(){
		Test_Search.simpleSearch("manager", _QuickRegistration);
		_Utility.Thread_Sleep(1000);
		WebElement scrollToElement = _QuickRegistration.findElement(quickRegisterDiv);
		_Utility.scrollTOElement(scrollToElement, _QuickRegistration);
		_Utility.Thread_Sleep(500);
		_Utility.elementDisplayPropertySetter("none", "id_filters", _QuickRegistration);
		qEmailid = fill_Email_Pwd(_QuickRegistration, true);
		_Utility.Thread_Sleep(1000);
		_QuickRegistration.findElement(quickRegisterBtn).click();
		_Utility.Thread_Sleep(1000);
		//Assert.assertEquals(_QuickRegistration.getTitle(), "Register Free on Shine.com | Apply Jobs Online");

	}
	

	@Test(priority=27)
	public void test_QuickRegister_Reg2(){
		Test_Registration.executeTestCase(false, false, "Quick Reg Flow 2 Exp", qEmailid, _QuickRegistration);
		_Utility.Thread_Sleep(2000);
		Assert.assertEquals(_QuickRegistration.getCurrentUrl(), baseUrl+"/myshine/registration/uploadresume/?from_registration_flow=True");
	}
	
	
	@Test(priority=28)
	public void test_QuickRegister_upload_resume(){
		Test_MidoutRegistration.resumeFileUpload(_QuickRegistration, "resumefile");
		_Utility.Thread_Sleep(2000);
		Assert.assertEquals(_QuickRegistration.getCurrentUrl(), baseUrl+"/myshine/registration/confirm/");
	}
	


	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_QuickRegistration!=null)
			_QuickRegistration.quit();

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _QuickRegistration);
	}

	
	/**
	 * fill Email and Password
	 * @param _Regsemdriver
	 */
	public String fill_Email_Pwd(WebDriver _Regsemdriver, boolean isQuickReg) {
		String email_id = _Utility.generateEmailid();
		APP_LOGS.debug("The registered email address for "+this.getClass().getName()+" is: "+email_id);
		_Utility.Thread_Sleep(2000);
		WebDriverWait wait = new WebDriverWait(_Regsemdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(Test_Registration.emailIdTxt));
		_Regsemdriver.findElement(Test_Registration.emailIdTxt).click();
		_Regsemdriver.findElement(Test_Registration.emailIdTxt).sendKeys(email_id);
        
		WebElement password = null;
		if(isQuickReg==true) {
			password = _Regsemdriver.findElement(qr_passwordtxt);
		}
		else {
			password = _Regsemdriver.findElement(passwordtxt);
		}
		_Utility.Thread_Sleep(1000);
		password.clear();
		password.sendKeys("123456");
		_Regsemdriver.findElement(Test_Registration.mobileTxt).clear();
		_Regsemdriver.findElement(Test_Registration.mobileTxt).sendKeys("9876556789");
		_Regsemdriver.findElement(Test_Registration.name).clear();
		_Regsemdriver.findElement(Test_Registration.name).sendKeys(TestBaseSetup.username);
		return email_id;
	}
	


}
