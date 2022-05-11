package com.shine.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shine.base.TestBaseSetup;

public class Login extends TestBaseSetup{

	WebDriver _Driver;
	WebDriverWait _Wait;



	public Login(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}




	public void open_login_page_url() {
		_Driver.navigate().to(baseUrl+"/myshine/login/");
	}

	/*****LOGIN POPUP WINDOW******/

	//@FindBy(partialLinkText ="Sign In")
	//WebElement SIGN_IN_BUTTON;

	@FindBy(id="id_email_login")
	WebElement LOGIN_POPUP_EMAIL_TEXT;
	
	@FindBy(id="id_email")
	WebElement LOGIN_EMAIL_TEXT;
	
	@FindBy(id="id_password")
	WebElement PASSWORD_TEXT;

	@FindBy(name="login_popup")
	WebElement LOGIN_POPUP;

	/**
	 * Login method
	 * @param _Logindriver
	 * @param email
	 * @throws Exception
	 */
	public void login(String email, String password) {
		OpenBaseUrl(_Driver);
		open_login_popup();
		enter_email(email);
		enter_password(password);
		submit_login_popup();
	}

	public void open_login_popup() {
		_Utility.Thread_Sleep(2000);
		_Driver.findElement(By.partialLinkText("Sign in")).click();
		//_Wait.until(ExpectedConditions.visibilityOf(SIGN_IN_BUTTON));		
		//SIGN_IN_BUTTON.click();
		_Utility.Thread_Sleep(1000);
	}


	public void enter_email(String email) {
		APP_LOGS.debug("Shine Login with email id:  "+email);
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_POPUP_EMAIL_TEXT));
		LOGIN_POPUP_EMAIL_TEXT.clear();
		LOGIN_POPUP_EMAIL_TEXT.sendKeys(email);
	}
	
	/**
	 * Hack for login page - /myshine/url/
	 * @param email
	 */
	public void enter_emailid(String email) {
		APP_LOGS.debug("Shine Login with email id:  "+email);
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_EMAIL_TEXT));
		LOGIN_EMAIL_TEXT.clear();
		LOGIN_EMAIL_TEXT.sendKeys(email);
	}

	public void enter_password(String password) {
		_Wait.until(ExpectedConditions.visibilityOf(PASSWORD_TEXT));
		PASSWORD_TEXT.clear();
		PASSWORD_TEXT.sendKeys(password);
	}

	public void submit_login_popup() {
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_POPUP));
		LOGIN_POPUP.click();
		_Utility.Thread_Sleep(2000);

	}


	/*****LOGIN VALIDATION******/

	@FindBy(css="[for='id_email_login']")
	WebElement LOGIN_POPUP_EMAIL_VALIDATION_ERROR_MSG;
	
	@FindBy(css="[for='id_email']")
	WebElement EMAIL_VALIDATION_ERROR_MSG;

	@FindBy(css="[for='id_password']")
	WebElement PWD_VALIDATION_ERROR_MSG;

	@FindBy(css=".cls_loginnotmatch")
	WebElement LOGIN_POPUP_VALIDATION_ERROR_MSG;
	
	@FindBy(css=".errorlist")
	WebElement LOGIN_VALIDATION_ERROR_MSG;

	public String getLoginPopupEmailValidationMessage() {
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_POPUP_EMAIL_VALIDATION_ERROR_MSG));
		String actualMSG = LOGIN_POPUP_EMAIL_VALIDATION_ERROR_MSG.getText().trim();
		APP_LOGS.debug("email validation message = "+actualMSG);
		return actualMSG;
	}
	
	public String getLoginEmailValidationMessage() {
		_Wait.until(ExpectedConditions.visibilityOf(EMAIL_VALIDATION_ERROR_MSG));
		String actualMSG = EMAIL_VALIDATION_ERROR_MSG.getText().trim();
		APP_LOGS.debug("email validation message = "+actualMSG);
		return actualMSG;
	}

	public String getPwdValidationMessage() {
		_Wait.until(ExpectedConditions.visibilityOf(PWD_VALIDATION_ERROR_MSG));
		String actualMSG = PWD_VALIDATION_ERROR_MSG.getText().trim();
		APP_LOGS.debug("Password validation message = "+actualMSG);
		return actualMSG;
	}

	public String getLoginPopupValidationMessage() {
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_POPUP_VALIDATION_ERROR_MSG));
		String actualMSG = LOGIN_POPUP_VALIDATION_ERROR_MSG.getText().trim();
		APP_LOGS.debug("Login validation message = "+actualMSG);
		return actualMSG;
	}
	
	public String getLoginValidationMessage() {
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_VALIDATION_ERROR_MSG));
		String actualMSG = LOGIN_VALIDATION_ERROR_MSG.getText().trim();
		APP_LOGS.debug("Login validation message = "+actualMSG);
		return actualMSG;
	}



	/***LOGIN PAGE: myshine/login ***/
	
	@FindBy(name="btn_homepage_signin")
	WebElement LOGIN_PAGE;

	public void loginPage(String email, String password) {
		open_login_page_url();
		enter_emailid(email);
		enter_password(password);
		submit_login();
	}


	public void submit_login() {
		_Wait.until(ExpectedConditions.visibilityOf(LOGIN_PAGE));
		LOGIN_PAGE.click();
		_Utility.Thread_Sleep(2000);

	}



}
