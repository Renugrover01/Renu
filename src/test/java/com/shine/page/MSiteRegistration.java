package com.shine.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shine.base.TestBaseSetup;

public class MSiteRegistration extends TestBaseSetup{

	WebDriver _Driver;
	WebDriverWait _Wait;

	public MSiteRegistration(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}



	@FindBy(linkText="Skip")
	WebElement SKIP_BUTTON_ID;

	@FindBy(css=".applybutton.registerSkip")
	WebElement REGISTRATION_SKIP_POPUP;

	public void click_on_skip_button() {
		APP_LOGS.debug("Click on MSite Skip registration button");
		_Wait.until(ExpectedConditions.visibilityOf(SKIP_BUTTON_ID));
		_Wait.until(ExpectedConditions.elementToBeClickable(SKIP_BUTTON_ID));
		SKIP_BUTTON_ID.click();
	}

	public void click_and_skip_all_section() {
		for(int i=0;i<3;i++){
			click_on_skip_button();
		}
		_Wait.until(ExpectedConditions.visibilityOf(REGISTRATION_SKIP_POPUP));
		REGISTRATION_SKIP_POPUP.click();
	}



	@FindBy(linkText="Sign In")
	WebElement SIGN_IN_BUTTON_ID;
	@FindBy(id="Password")
	WebElement PASSWORD_ID;	
	@FindBy(name="email")
	WebElement EMAIL_ID;
	@FindBy(xpath="//input[@name='btn_homepage_signin']")
	WebElement SUBMIT_BUTTON_ID;


	public void click_on_sign_in_button() {
		APP_LOGS.debug("Click on MSite sign-in button");
		_Utility.scrollTOElement(SIGN_IN_BUTTON_ID, _Driver);
		_Wait.until(ExpectedConditions.visibilityOf(SIGN_IN_BUTTON_ID));
		_Wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN_BUTTON_ID));
		SIGN_IN_BUTTON_ID.click();
	}

	public void enter_email_id(String email) {
		_Wait.until(ExpectedConditions.visibilityOf(EMAIL_ID));
		EMAIL_ID.clear();
		EMAIL_ID.sendKeys(email);
		EMAIL_ID.sendKeys(Keys.TAB);
	}

	public void enter_password(String password) {
		_Wait.until(ExpectedConditions.visibilityOf(PASSWORD_ID));
		PASSWORD_ID.clear();
		PASSWORD_ID.sendKeys(password);
		PASSWORD_ID.sendKeys(Keys.TAB);
	}

	public void click_on_submit_button() {
		_Wait.until(ExpectedConditions.visibilityOf(SUBMIT_BUTTON_ID));
		if(SUBMIT_BUTTON_ID.isDisplayed())
			SUBMIT_BUTTON_ID.click();
	}

	/**
	 * 
	 * @param userEmail
	 * @param password
	 * @param _LoginDriver
	 */
	public void login_in_msite(String userEmail, String password) {
		if(baseUrl.contains("sumosc.shine.com"))
			_Driver.navigate().to("https://sm.shine.com");
		else if(baseUrl.contains("sumosc1.shine.com"))
			_Driver.navigate().to("https://sm1.shine.com");
		else if(baseUrl.contains("pp-www.shine.com"))
			_Driver.navigate().to("https://pp-m.shine.com");
		else
			_Driver.navigate().to("https://m.shine.com");
		_Utility.Thread_Sleep(1000);
		_Utility.set_cookie(_Driver, "InterstitialBanner", "1");
		_Utility.remove_InterstitialBanner(_Driver);
		APP_LOGS.debug("Calling login Method");
		_Utility.Thread_Sleep(2000);
		click_on_sign_in_button();
		_Utility.Thread_Sleep(4000);
		enter_email_id(userEmail);
		enter_password(password);
		click_on_submit_button();
		_Utility.Thread_Sleep(5000);
		_Utility.closeAppPopup(_Driver);
		_Utility.closeNotification(_Driver);
	}

	@FindBy(id="id_resume_file")
	WebElement RESUME_UPLOADER_ID;


	/**
	 * Resume uploader method
	 * @param _ResumeUploaddriver
	 */
	public void upload_resume(String filename) {
		APP_LOGS.debug("Uploading Resume...Please wait...");
		String resume = userDirectory+CONFIG.getProperty(filename);
		APP_LOGS.debug(resume);
		RESUME_UPLOADER_ID.sendKeys(resume);
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug("Resume uploaded Successfully");
	}

	@FindBy(id="id_name")
	WebElement USERNAME_ID;
	@FindBy(id="id_location_span")
	WebElement LOCATION_ID;
	@FindBy(id="id_candidate_location_406")
	WebElement LOCATION_SUB_ID;
	@FindBy(id="id_totalExperience")
	WebElement EXPERIENCE_ID;


	@FindBy(id="id_gender_0")
	WebElement GENDER_MALE_ID;

	@FindBy(partialLinkText="Save")
	WebElement PD_SAVE_BUTTON;



	public void enter_user_name(String name) {
		_Wait.until(ExpectedConditions.visibilityOf(USERNAME_ID));
		USERNAME_ID.clear();
		USERNAME_ID.sendKeys(name);
		USERNAME_ID.sendKeys(Keys.TAB);
	}

	public void select_location() {
		_Wait.until(ExpectedConditions.visibilityOf(LOCATION_ID));
		if(LOCATION_ID.isDisplayed()) {
			LOCATION_ID.click();
			_Utility.Thread_Sleep(2000);
			if(LOCATION_SUB_ID.isDisplayed())
				LOCATION_SUB_ID.click();
		}
	}

	public void enter_total_experience(String exp_year) {
		_Wait.until(ExpectedConditions.visibilityOf(EXPERIENCE_ID));
		EXPERIENCE_ID.clear();
		EXPERIENCE_ID.sendKeys(exp_year);
		EXPERIENCE_ID.sendKeys(Keys.TAB);
	}

	public void select_gender() {
		_Wait.until(ExpectedConditions.visibilityOf(GENDER_MALE_ID));
		if(GENDER_MALE_ID.isDisplayed()) 
			GENDER_MALE_ID.click();
	}

	public void save_personal_detail_Section() {
		_Wait.until(ExpectedConditions.visibilityOf(PD_SAVE_BUTTON));
		if(PD_SAVE_BUTTON.isDisplayed()) 
			PD_SAVE_BUTTON.click();
	}


	public void fill_personal_details() {
		enter_user_name("Abhishek");
		_Utility.Thread_Sleep(2000);
		select_location();
		_Utility.Thread_Sleep(2000);
		enter_total_experience("5");
		_Utility.Thread_Sleep(2000);
		select_gender();
		save_personal_detail_Section();
		_Utility.Thread_Sleep(2000);
	}



}
