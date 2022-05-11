package com.shine.tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.shine.base.TestBaseSetup;

public class Test_JDShare extends TestBaseSetup {


	WebDriver _JDShareDriver;
	WebDriverWait _Wait;
	SoftAssert _SoftAssertion;

	By share_button		= By.cssSelector(".jobshare a");

	By id_share_email		= By.cssSelector("[data-type='email'] span");
	By id_share_linkedin	= By.cssSelector("[data-type='linkedin'] span");
	By id_share_twitter		= By.cssSelector("[data-type='twitter'] span");
	By id_share_facebook	= By.cssSelector("[data-type='facebook'] span");

	String jd_url = "";

	@BeforeClass
	public void TestStart() {
		_JDShareDriver = getDriver(_JDShareDriver);
		_Wait = new WebDriverWait(_JDShareDriver, 15);
	}

	@Test(priority=0)
	public void verify_jd_share_button() {
		open_jd_page();
		_Utility.Thread_Sleep(2000);
		jd_url = _JDShareDriver.getCurrentUrl();
		click_on_share_button();
		verify_isPopup_displayed();
	}

	@Test(priority=1, dependsOnMethods= {"verify_jd_share_button"})
	public void verify_share_email() {
		verify_share_button();
	}

	@Test(priority=2, dependsOnMethods= {"verify_jd_share_button"})
	public void verify_linkedin_share() {
		String switch_tab_url = verify_share_button();
		assertTrue(switch_tab_url.contains("linkedin"), "Linkedin >> Not present in >>"+switch_tab_url);
	}

	@Test(priority=3, dependsOnMethods= {"verify_jd_share_button"})
	public void verify_share_facebook() {
		String switch_tab_url = verify_share_button();
		assertTrue(switch_tab_url.contains("facebook"), "Facebook >> Not present in >>"+switch_tab_url);

	}

	@Test(priority=4, dependsOnMethods= {"verify_jd_share_button"})
	public void verify_share_twitter() {
		String switch_tab_url = verify_share_button();
		assertTrue(switch_tab_url.contains("twitter"), "Twitter >> Not present in >>"+switch_tab_url);
	}



	@Test(priority=5)
	public void verify_loggedin_jd_share_button() {
		loggedInShine(_JDShareDriver, email_new, pass_new);
		_Utility.Thread_Sleep(4000);
		jd_url = jd_url.replace(baseUrl+"/jobs/", baseUrl+"/myshine/jobs/");
		_JDShareDriver.navigate().to(jd_url);
		click_on_share_button();
		verify_isPopup_displayed();
	}

	@Test(priority=6, dependsOnMethods= {"verify_loggedin_jd_share_button"})
	public void verify_loggedin_share_email() {
		verify_share_button();
	}

	@Test(priority=7, dependsOnMethods= {"verify_loggedin_jd_share_button"})
	public void verify_loggedin_linkedin_share() {
		String switch_tab_url = verify_share_button();
		assertTrue(switch_tab_url.contains("linkedin"), "Linkedin >> Not present in >>"+switch_tab_url);

	}

	@Test(priority=8, dependsOnMethods= {"verify_loggedin_jd_share_button"})
	public void verify_loggedin_share_facebook() {
		String switch_tab_url = verify_share_button();
		assertTrue(switch_tab_url.contains("facebook"), "Facebook >> Not present in >>"+switch_tab_url);
	}

	@Test(priority=9, dependsOnMethods= {"verify_loggedin_jd_share_button"})
	public void verify_loggedin_share_twitter() {
		String switch_tab_url = verify_share_button();
		assertTrue(switch_tab_url.contains("twitter"), "Twitter >> Not present in >>"+switch_tab_url);
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _JDShareDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_JDShareDriver!=null)
			_JDShareDriver.quit();

	}


	public String verify_share_button() {
		check_isNavigate_back_required();
		click_on_share_button();
		String method_name = new Exception().getStackTrace()[1].getMethodName();
		method_name = method_name.replace("verify_loggedin", "verify");
		APP_LOGS.debug("Method Name = "+method_name);
		switch(method_name){
		case "verify_linkedin_share":
			return share_via_linkedin();

		case "verify_share_facebook":
			return share_via_facebook();

		case "verify_share_email":
			share_via_email();
			break;			
		case "verify_share_twitter":
			return share_via_twitter();

		}
		return "";

	}

	 By searchDivStat  = By.id("search_content");

	/**
	 * Open share popup
	 * @param _JDShareDriver
	 */
	public void open_jd_page() {
		_Utility.Thread_Sleep(3000);
		Test_JobApply.searchForJob("HT Media Pvt Ltd", _JDShareDriver);
		_Utility.Thread_Sleep(2000);
		List<WebElement> job_list = _JDShareDriver.findElements(By.cssSelector(".snp"));
		APP_LOGS.debug("Current window handle is" + _JDShareDriver.getWindowHandle());
		_Utility.scrollTOElement(searchDivStat, _JDShareDriver);
		job_list.get(0).click();
		_Utility.Thread_Sleep(2000);
		String parent_frame = _JDShareDriver.getWindowHandle();
		for (String winHandle : _JDShareDriver.getWindowHandles()) {
			_JDShareDriver.switchTo().window(winHandle); // switch focus of
			if(parent_frame.equals(winHandle)) {
				String switch_tab_url = _JDShareDriver.getCurrentUrl();
				APP_LOGS.debug("switch_tab_url = "+switch_tab_url);
				_JDShareDriver.close();
				_Utility.Thread_Sleep(1000);
			}
		}
		_Utility.Thread_Sleep(2000);
	}

	public void click_on_share_button() {
		_Utility.Thread_Sleep(3000);
		_Utility.elementDisplayPropertySetter("block", "id_shareSocial", _JDShareDriver);
		_Utility.Thread_Sleep(2000);
	}

	public void verify_isPopup_displayed() {
		_Utility.Thread_Sleep(2000);
		assertTrue(_JDShareDriver.findElement(share_button).isDisplayed());
	}

	/**
	 * check_isNavigate_back_required
	 */
	public void check_isNavigate_back_required() {
		_Utility.Thread_Sleep(2000);
		String url = _JDShareDriver.getCurrentUrl();
		if(!url.equals(jd_url)) 
			_JDShareDriver.navigate().back();
		else
			_JDShareDriver.navigate().refresh();
		_Utility.Thread_Sleep(2000);
	}




	private void share_via_email() {
		_Utility.Thread_Sleep(2000);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(id_share_email));
		assertTrue(_JDShareDriver.findElement(id_share_email).isDisplayed());

	}

	/**
	 * Share_via_whatsapp_or_sms
	 * @param by
	 * @param assert_message
	 */
	public void share_via_email(By by, String assert_message) {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(id_share_email));
		assertTrue(_JDShareDriver.findElement(id_share_email).isDisplayed());

	}

	private String share_via_twitter() {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(id_share_twitter));
		_JDShareDriver.findElement(id_share_twitter).click();
		return switch_to_window_and_get_url();
	}

	private String share_via_facebook() {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(id_share_facebook));
		_JDShareDriver.findElement(id_share_facebook).click();
		return switch_to_window_and_get_url();
	}


	private String share_via_linkedin() {
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(id_share_linkedin));
		_JDShareDriver.findElement(id_share_linkedin).click();
		return switch_to_window_and_get_url();

	}

	public String switch_to_window_and_get_url() {
		_Utility.Thread_Sleep(2000);
		String parent_frame = _JDShareDriver.getWindowHandle();
		for (String winHandle : _JDShareDriver.getWindowHandles()) {
			_JDShareDriver.switchTo().window(winHandle); // switch focus of
			// WebDriver to the next found window handle(that's your newly opened window)
			APP_LOGS.debug("Current window handle after JD Click is" + winHandle);
			_Utility.Thread_Sleep(2000);
			if(!parent_frame.equals(winHandle)) {
				String switch_tab_url = _JDShareDriver.getCurrentUrl();
				APP_LOGS.debug("switch_tab_url = "+switch_tab_url);
				_JDShareDriver.close();
				_JDShareDriver.switchTo().window(parent_frame);
				return switch_tab_url;

			}
		}

		return "";

	}





}
