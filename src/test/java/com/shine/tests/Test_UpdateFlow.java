package com.shine.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_UpdateFlow extends TestBaseSetup {

	WebDriver _Updflowdriver;
	WebDriverWait _Wait;
	//Date of Birth widget
	static By datePicker        = By.id("id_updateDateOfBirth");
	static By datePickerDate    = By.linkText("9");
	//Profile Title Widget
	static By resumeTitle		= By.id("id_ResumeTitle");
	//Notice Widget
	static By currentDuration	= By.id("id_current_duration");
	static By widgetHeadText	= By.cssSelector(".cls_update_title");

	static By linkedinHeadTitle	= By.cssSelector(".cls_update_text");
	static By linkedinSyncBtn	= By.cssSelector(".linkedin a");
	static By linkedinSkipBtn	= By.cssSelector(".cls_update_flow_linkedin .skipbutton.cls_skipbutton");

	static By certiTxtDiv		= By.id("id_txt_certicification");
	static By yearDD			= By.id("id_year");

	static By mobileNoSubmit 	= By.cssSelector("#id_mobile_verify button.applybutton");
	static By emailSubmit 		= By.cssSelector("#id_email_verify button.applybutton");
	static By skillSubmit 		= By.cssSelector("#id_skill_update button.applybutton");
	static By dobSubmit 		= By.cssSelector("#id_update_dob button.applybutton");
	static By profileTitleSubmit= By.cssSelector("#id_profile_verify button.applybutton");
	static By certiSubmit 		= By.cssSelector("#id_certification_verify button.applybutton");
	static By id_resume_skip	= By.id("id_resume_skip");


	public static By getSkillTxtDiv(int i) {
		return By.name("skill_"+i);
	}
	public static By getSkillExpDD(int i) {
		return By.name("selectskill_years_of_experience_"+i);
	}

	@BeforeClass
	public void TestSetup() {
		_Updflowdriver = getDriver(_Updflowdriver);
		_Wait = new WebDriverWait(_Updflowdriver, 10);
		loggedInShine(_Updflowdriver, emailid, pass_new);
		_Utility.clickOnNotification(_Updflowdriver);
	}


	@Test (priority=1)
	public  void verify_widget1() {
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}

	@Test (priority=2)
	private  void verify_widget2() {
		_Utility.Thread_Sleep(3000);
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}

	@Test (priority=3)
	private  void verify_widget3() {
		_Utility.Thread_Sleep(3000);
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}

	@Test (priority=4)
	private  void verify_widget4() {
		_Utility.Thread_Sleep(3000);
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}

	@Test (priority=5)
	private  void verify_widget5() {
		_Utility.Thread_Sleep(3000);
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}

	@Test (priority=6)
	private  void verify_widget6() {
		_Utility.Thread_Sleep(3000);
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}

	@Test (priority=7) 
	private  void verify_widget7() {
		_Utility.Thread_Sleep(3000);
		TestCase( _Updflowdriver.findElement(widgetHeadText).getText(), _Updflowdriver);
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Updflowdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Updflowdriver!=null)
			_Updflowdriver.quit();

	}

	/**
	 * Automatically work base on the onscreen widget
	 * @param title
	 * @param _Updflowdriver
	 */
	public static void TestCase(String title, WebDriver _Updflowdriver) {
		switch (title) {
		case "Verify your mobile number":
			update_MobileNo(_Updflowdriver);
			break;
		case "Update your skills":
			update_SkillAdd(_Updflowdriver);
			break;
		case "Verify your Email Id":
			update_Email(_Updflowdriver);
			break;
		case "Sync LinkedIn Profile":
			update_Linkedin_FLow(_Updflowdriver);
			break;
		case "Update your Profile Title":
			update_Title(_Updflowdriver);
			break;
		case "Update your Certification":
			update_Certificate(_Updflowdriver);
			break;
		case "Update your Date of Birth":
			update_DOB(_Updflowdriver);
			break;
		default:
			update_flow_handler(_Updflowdriver);
			APP_LOGS.info("Head title not found, instead found "+title);

		}
	}

	/**
	 * 
	 * @param _Updflowdriver
	 */
	private static void update_flow_handler(WebDriver _Updflowdriver) {
		try {
		_Utility.Thread_Sleep(1000);
		_Updflowdriver.findElement(id_resume_skip).click();
		}catch(Throwable t) {
			APP_LOGS.debug(t.getMessage());
		}
		
	}
	/*
	 * Mobile widget
	 */
	public static void update_MobileNo(WebDriver _Updmbdriver) {
		_Utility.Thread_Sleep(3000);
		assertHeadTitle("Verify your mobile number",widgetHeadText, _Updmbdriver);
		APP_LOGS.debug("1. title of contact number widget is found.");
		_Updmbdriver.findElement(mobileNoSubmit).click();

	}

	/*
	 * Email Widget
	 */

	public static void update_Email(WebDriver _Updemaildriver) { 
		_Utility.Thread_Sleep(4000);
		assertHeadTitle("Verify your Email Id",widgetHeadText, _Updemaildriver);
		_Updemaildriver.findElement(emailSubmit).click();
		_Utility.Thread_Sleep(4000);
	}

	/*
	 * Skill widget
	 */
	public static void update_SkillAdd(WebDriver _Updskadddriver){
		_Utility.Thread_Sleep(4000);
		assertHeadTitle("Update your skills",widgetHeadText, _Updskadddriver);
		_Updskadddriver.findElement(getSkillTxtDiv(1)).clear();
		_Updskadddriver.findElement(getSkillTxtDiv(1)).sendKeys("cucumber");
		_Updskadddriver.findElement(getSkillTxtDiv(2)).clear();
		_Updskadddriver.findElement(getSkillTxtDiv(2)).sendKeys("apple");
		_Updskadddriver.findElement(getSkillTxtDiv(3)).clear();
		_Updskadddriver.findElement(getSkillTxtDiv(3)).sendKeys("c#");
		//Hack for fresher flow - as fresher candidate don't have exp
		try {
			new Select(_Updskadddriver.findElement(getSkillExpDD(1))).selectByVisibleText("<1 Yr");
			new Select(_Updskadddriver.findElement(getSkillExpDD(2))).selectByVisibleText("2 Yrs");
			new Select(_Updskadddriver.findElement(getSkillExpDD(3))).selectByVisibleText("2 Yrs");
		}catch(Exception ex) {
			APP_LOGS.debug("Non IT User >> Year drop down not found!!!");
		}
		_Updskadddriver.findElement(skillSubmit).click();
	}

	/*
	 * Date of Birth widget
	 */
	public static void update_DOB(WebDriver _UpdDobdriver) { 
		_Utility.Thread_Sleep(3000);
		assertHeadTitle("Update your Date of Birth", widgetHeadText, _UpdDobdriver);
		_UpdDobdriver.findElement(datePicker).click();
		_Utility.Thread_Sleep(3000);
		_UpdDobdriver.findElement(datePickerDate).click();
		_Utility.Thread_Sleep(3000);
		_UpdDobdriver.findElement(dobSubmit).click();
		_Utility.Thread_Sleep(2000);

	}

	/*
	 * Profile Title Widget
	 */
	public static void update_Title(WebDriver _UpdflowTitledriver) { 	
		_Utility.Thread_Sleep(4000);
		assertHeadTitle("Update your Profile Title", widgetHeadText, _UpdflowTitledriver);
		_UpdflowTitledriver.findElement(resumeTitle).clear();
		_UpdflowTitledriver.findElement(resumeTitle).sendKeys("M.Tech with 6+ years of IT experience in Software Development and Solution Delivery");
		_Utility.Thread_Sleep(2000);
		_UpdflowTitledriver.findElement(profileTitleSubmit).click();

	}	

	/*
	 * Notice Widget
	 */
	public void testUpdateFlowNotice(WebDriver _Updnoticedriver) { 
		_Utility.Thread_Sleep(4000);
		Assert.assertEquals("Please tell us the notice period in your current organization.", _Updnoticedriver.findElement(widgetHeadText).getText());
		new Select(_Updnoticedriver.findElement(currentDuration)).selectByVisibleText("2 weeks");
		_Utility.Thread_Sleep(2000);
		_Updnoticedriver.findElement(By.cssSelector("button.txblue_button.save")).click();
	}

	/*
	 * Linkedin Widget
	 */
	public static void update_Linkedin_FLow(WebDriver _UpdflowLinkedindriver) { 	
		_Utility.Thread_Sleep(3000);
		assertHeadTitle("Updated profiles rank better",linkedinHeadTitle, _UpdflowLinkedindriver);
		_UpdflowLinkedindriver.findElement(linkedinSkipBtn).click();

	}

	/*
	 * Skill widget
	 */
	public static void update_Certificate(WebDriver _Updskadddriver){
		_Utility.Thread_Sleep(4000);
		assertHeadTitle("Update your Certification", widgetHeadText, _Updskadddriver);
		_Updskadddriver.findElement(certiTxtDiv).clear();
		_Updskadddriver.findElement(certiTxtDiv).sendKeys("J2EE");
		new Select(_Updskadddriver.findElement(yearDD)).selectByVisibleText("2017");
		_Updskadddriver.findElement(certiSubmit).click();
	}

	/**
	 * 
	 * @param checkTitle
	 * @param titleDiv
	 * @param _Driver
	 */
	public static void assertHeadTitle(String checkTitle, By titleDiv, WebDriver _Driver) {
		_Utility.Thread_Sleep(3000);
		String widgetHeadTxt = _Driver.findElement(titleDiv).getText();
		APP_LOGS.debug("Widget Found with headline: "+widgetHeadTxt);
		Assert.assertEquals(widgetHeadTxt, checkTitle);
	}

}

