package com.shine.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shine.base.TestBaseSetup;

public class JobAlert  extends TestBaseSetup{

	WebDriver _Driver;
	WebDriverWait _Wait;

	public JobAlert(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}

	static By name 		       = By.name("name");
	static By keyword 		   = By.id("id_keywords");
	
	
	@FindBy(name="name")
	WebElement JOB_ALERT_NAME;
	@FindBy(id="id_keywords")
	WebElement JOB_ALERT_KEYWORD_ID;
	@FindBy(css=".yel_btn.cls_jobalert")
	WebElement SUBMIT_REG_JOB_ALERT;
	
	public void submit_job_alert() {
		_Wait.until(ExpectedConditions.visibilityOf(SUBMIT_REG_JOB_ALERT));
		SUBMIT_REG_JOB_ALERT.click();
		_Utility.Thread_Sleep(2000);

	}
	public void enter_job_alert_name(String job_alert_name) {
		_Wait.until(ExpectedConditions.visibilityOf(JOB_ALERT_NAME));
		JOB_ALERT_NAME.sendKeys(job_alert_name);
		JOB_ALERT_NAME.sendKeys(Keys.TAB);
	}
	public void enter_job_alert_keyword(String job_alert_keyword) {
		_Wait.until(ExpectedConditions.visibilityOf(JOB_ALERT_KEYWORD_ID));
		JOB_ALERT_KEYWORD_ID.sendKeys(job_alert_keyword);
		JOB_ALERT_KEYWORD_ID.sendKeys(Keys.TAB);
		JOB_ALERT_KEYWORD_ID.sendKeys(Keys.ESCAPE);
		JOB_ALERT_KEYWORD_ID.sendKeys(Keys.TAB);
	}
	
	
	
	public void fill_and_submit_job_alert() {
		APP_LOGS.debug("Creating job-alert after registration step 2 and resume upload");
		enter_job_alert_name("myalert");
		enter_job_alert_keyword("Sales");
		submit_job_alert();
		_Utility.Thread_Sleep(1000);
	}
	


}
