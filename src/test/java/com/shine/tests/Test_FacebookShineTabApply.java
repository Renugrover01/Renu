/**
 * Secure web site is used for FB career page for shine and some recruiters.
 * @author F3282
 * 
 */
package com.shine.tests;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_FacebookShineTabApply  extends TestBaseSetup{

	String secureURL = "/csm/search/453322/";
	String secureMyshineURL = "/myshine/csm/search/453322/";
	WebDriver _FacebookShineTabDriver;
	WebDriverWait _Wait;

	String actual_job_title = "";
	int actual_job_list_size = 0;


	private By keyword		   = By.id("id_q");
	private By searchBtn	   = By.cssSelector("input.cls_btn_reg_job_srch.cls_searchbtnabv");
	private By applyBtn		   = By.linkText("Apply");

	private By emailIdTxt	   = By.id("id_email_login");
	private By passTxt		   = By.id("id_password");
	private By loginBtn		   = By.cssSelector(".cls_candidate_login.submitred");

	private By jdMessageDiv	   = By.cssSelector(".links.pull-left.socialjdsnippet");	

	private By jobTitle		   = By.cssSelector("[itemprop='title']");
	private By jdDobTitle	   = By.cssSelector(".cls_jobtitle");

	public By messageDiv(int index) {
		return By.xpath("(//*[@class='alrd_applied cls_appliedJobMsg'])["+index+"]");
	}

	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the Shine Secure test");
		_FacebookShineTabDriver = getDriver(_FacebookShineTabDriver);
		_Wait = new WebDriverWait(_FacebookShineTabDriver, 10);
		_FacebookShineTabDriver.navigate().to(baseUrl+secureURL);
	}


	@Test (priority=0)
	public void open_facebook_shine_jsrp_Page(){
		search_job("java", _FacebookShineTabDriver);
		Assert.assertEquals("Apply", _FacebookShineTabDriver.findElement(applyBtn).getText());
		Assert.assertEquals("Shine.com", _FacebookShineTabDriver.getTitle());
	}



	@Test (priority=1)
	public void verify_facebook_shine_jsrp_apply() {
		int applyJobIndex = click_on_apply_btn(0, _FacebookShineTabDriver);
		_Utility.Thread_Sleep(2000);
		login(email_hc, pass_hc, _FacebookShineTabDriver);
		_Utility.Thread_Sleep(3000);
		check_job_applied_message(messageDiv(applyJobIndex), false, _FacebookShineTabDriver);

	}


	@Test (priority=2)
	public void verify_facebook_shine_jd_apply() {
		open_jd_page(1, _FacebookShineTabDriver);
		APP_LOGS.debug("Job Size: "+actual_job_list_size);
		APP_LOGS.debug("Facebook shine tab >> Actual Title: "+actual_job_title);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(jdDobTitle));
		String expected_title = _FacebookShineTabDriver.findElement(jdDobTitle).getText();
		APP_LOGS.debug("Facebook shine tab >> Expected Title: "+expected_title);
		Assert.assertEquals(actual_job_title, expected_title);
		check_job_applied_message(jdMessageDiv, true, _FacebookShineTabDriver);
	}

	@Test (priority=3)
	public void verify_facebook_shine_jsrp_myshine_apply() {
		_FacebookShineTabDriver.get(baseUrl+secureMyshineURL);
		search_job("customer", _FacebookShineTabDriver);
		int index = click_on_apply_btn(2, _FacebookShineTabDriver);
		_Utility.Thread_Sleep(3000);
		check_job_applied_message(messageDiv(index), false, _FacebookShineTabDriver);

	}

	@Test (priority=4)
	public void verify_facebook_shine_jd_myshine_apply() {
		open_jd_page(3, _FacebookShineTabDriver);
		APP_LOGS.debug("Job Size: "+actual_job_list_size);
		APP_LOGS.debug("Facebook shine tab >> Actual Title: "+actual_job_title);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(jdDobTitle));
		String expected_title = _FacebookShineTabDriver.findElement(jdDobTitle).getText();
		APP_LOGS.debug("Facebook shine tab >> Expected Title: "+expected_title);
		Assert.assertEquals(actual_job_title, expected_title);
		check_job_applied_message(jdMessageDiv, true, _FacebookShineTabDriver);
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _FacebookShineTabDriver);
	}	


	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_FacebookShineTabDriver!=null)
			_FacebookShineTabDriver.quit();

	}

	/**
	 * 
	 * @param search_keyword
	 * @param _FacebookShineTabDriver
	 */
	private void search_job(String search_keyword, WebDriver _FacebookShineTabDriver) {
		_FacebookShineTabDriver.findElement(keyword).clear();
		_FacebookShineTabDriver.findElement(keyword).sendKeys(search_keyword);
		_FacebookShineTabDriver.findElement(searchBtn).click();
	}




	/**
	 * Login in facebook shine tab
	 * @param email
	 * @param password
	 * @param _FacebookShineTabDriver
	 */
	private void login(String email, String password, WebDriver _FacebookShineTabDriver) {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(emailIdTxt));
		_FacebookShineTabDriver.findElement(emailIdTxt).sendKeys(email);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(passTxt));
		_FacebookShineTabDriver.findElement(passTxt).sendKeys(password);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn));
		_FacebookShineTabDriver.findElement(loginBtn).click();
	}

	/**
	 * click_on_apply_btn
	 * @param list_postion
	 * @param _FacebookShineTabDriver
	 */
	private int click_on_apply_btn(int list_postion, WebDriver _FacebookShineTabDriver) {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(applyBtn));
		List<WebElement> job_list = _FacebookShineTabDriver.findElements(applyBtn);
		job_list.get(list_postion).click();
		_Utility.Thread_Sleep(3000);
		if(!_FacebookShineTabDriver.getCurrentUrl().contains("shine.com")) {
			_FacebookShineTabDriver.navigate().back();
			_Utility.Thread_Sleep(2000);
			search_job("sales", _FacebookShineTabDriver);
			job_list = _FacebookShineTabDriver.findElements(applyBtn);
			job_list.get(list_postion).click();
			_Utility.Thread_Sleep(2000);
			return 3;
		}
		else {
			_Utility.Thread_Sleep(2000);
			return 1;
		}
		
	}

	/**
	 * 
	 * @param locator
	 * @param _FacebookShineTabDriver
	 */
	private void check_job_applied_message(By locator, Boolean isJD, WebDriver _FacebookShineTabDriver) {
		_Utility.Thread_Sleep(2000);
		String actual = _FacebookShineTabDriver.findElement(locator).getText().trim();
		APP_LOGS.debug("Message before apply button: "+actual);
		if(actual.contains("Apply") && isJD == true) {
			click_on_apply_btn(0, _FacebookShineTabDriver);
			actual = _FacebookShineTabDriver.findElement(locator).getText().trim();
			APP_LOGS.debug("Message after handling apply click on jd: "+actual);
		}
		if(actual.contains("Show Interest")){
			_FacebookShineTabDriver.findElement(By.linkText("Show Interest")).click();
			_Utility.Thread_Sleep(2000);
			actual = _FacebookShineTabDriver.findElement(locator).getText().trim();
			APP_LOGS.debug("Message after handling walkin apply click: "+actual);
		}
		if(actual.contains("Applied Successfully"))
			Assert.assertEquals(actual, "Applied Successfully");
		else 
			Assert.assertEquals(actual, "Already Applied");
	}

	/**
	 * 
	 * @param list_postion
	 * @param _FacebookShineTabDriver
	 * @return
	 */
	private void open_jd_page(int list_postion, WebDriver _FacebookShineTabDriver) {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitle));
		List<WebElement> job_list = _FacebookShineTabDriver.findElements(jobTitle);
		actual_job_title = job_list.get(list_postion).getText().trim();
		actual_job_list_size = job_list.size();
		job_list.get(list_postion).click();
	}





}
