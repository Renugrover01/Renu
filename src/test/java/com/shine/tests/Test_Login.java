package com.shine.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.page.Login;

public class Test_Login extends TestBaseSetup{

	WebDriver _LoginDriver = null;
	Login _Login = null;

	@BeforeClass
	public void TestSetup() {
		_LoginDriver = getDriver(_LoginDriver);
		_Login = new Login(_LoginDriver);

	}

	@Test(priority=1, description= "Login with blank input")
	public void verify_login_with_blank_input() {
		_Login.login("", "");
		assertEquals(_Login.getLoginPopupEmailValidationMessage(), "Email ID is required");
		assertEquals(_Login.getPwdValidationMessage(), "Password is required");

	}

	@Test(priority=2, description= "Login using Invalid Password")
	public void verify_login_with_invalid_password() {
		_Login.login(email_new, "passasddadsword");	
		assertEquals(_Login.getLoginPopupValidationMessage(), "Email Id and Password did not match.");
	}


	@Test(priority=3, description= "Login with invalid email and password")
	public void verify_login_with_invalid_inputs() {
		_Login.login( "qualityabhisdsdshek@gmail.com", "passasddadsword");
		assertEquals(_Login.getLoginPopupValidationMessage(), "This email-id is not registered. Please register.");
	}

	@Test(priority=4,  description= "Login with valid email and password")
	public void verify_login_with_valid_inputs() {
		_Login.login(email_new, pass_new);
		assertEquals(_LoginDriver.getCurrentUrl(), baseUrl+"/myshine/home/");

	}
	
	
	@Test(priority=5, alwaysRun=true)
	public void verify_loginpage_with_blank_input() {
        _LoginDriver.get(baseUrl+"/myshine/logout");
        _Login.loginPage("", "");
    	assertEquals(_Login.getLoginEmailValidationMessage(), "Email ID is required");
		assertEquals(_Login.getPwdValidationMessage(), "Password is required");

	}


	@Test(priority=6)
	public void verify_loginpage_with_invalid_password() {
		_Login.loginPage(email_new, "passasddadsword");	
		assertEquals(_Login.getLoginValidationMessage(), "Email Id and Password did not match.");
	}


	@Test(priority=7)
	public void verify_loginpage_with_invalid_inputs() {
		_Login.loginPage( "qualityabhisdsdshek@gmail.com", "passasddadsword");
		assertEquals(_Login.getLoginValidationMessage(), "This email-id is not registered. Please register.");
	}

	@Test(priority=8)
	public void verify_loginpage_with_valid_inputs() {
		_Login.loginPage(email_new, pass_new);
		assertEquals(_LoginDriver.getCurrentUrl(), baseUrl+"/myshine/home/");

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _LoginDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_LoginDriver!=null)
			_LoginDriver.quit();

	}




}
