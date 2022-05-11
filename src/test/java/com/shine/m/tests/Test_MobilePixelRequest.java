package com.shine.m.tests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.shine.listener.LogAnalyzer;
import com.shine.tests.Test_Registration;
import com.shine.base.TestBaseSetup;


public class Test_MobilePixelRequest extends TestBaseSetup {
	private String url = "/registration/?keyword=Jobs%20in%20Ajmer&vendorid=2500106&utm_source=google&utm_medium=cpc&utm_campaign=Top%20Cities-%20Exact-Mobile-Contexual&utm_adgroup=Mumbai&utm_placement={placement:define}&utm_content=exitOverlayT";
	private String url2 = "/sem/registration/?keyword=Jobs%20in%20Lucknow&Vendorid=2600006&utm_source=google&utm_medium=cpc&utm_campaign=Top%20Cities-%20BMM_Mobile&utm_adgroup=Lucknow&utm_keyword={keyword:define}";
	WebDriver _PixelCheckDriver;
	WebDriverWait _Wait;
	Test_Registration _Registration = new Test_Registration();
	By resumeUploadid = By.id("id_resume_file");
	String mBaseUrl = "";

	//MSite registration papge1
	static By registrationBtn		= By.id("id_register");
	By regiFormAction				= By.id("id_registration");
	static By userNameTxt			= By.id("id_firstName");
	static By emailIdTxt			= By.xpath("//input[@type='email']");
	static By mobileTxt				= By.id("id_cellphone");
	By submitFirstRegsitartionForm	= By.xpath("//button[contains(text(),'Continue')]");
	static By genderCheckbox 		= By.cssSelector("[data-label='1']");
	static By cityCheckbox 			= By.cssSelector("[data-label='406']");
	static By haveYouEverWorkedCheckbox = By.cssSelector("[name='Experience']");
	static By workExperienceYearTextbox = By.cssSelector("[data-target='#id_experience_modal']");
	static By experienceValueInYears = By.xpath("//*[@id='id_experience_modal'] //li[contains(@data-value,'11')]");
	static By workExperienceMonthTextbox = By.xpath("//*[@id=\"id_experience_month_modal\"]/div/div/div[2]/ul/li[3]");

	//Experience - click - select drop down and than sub drop down
	static By companyNameTextbox 	= By.id("id_company");
	static By designationTextbox 	= By.id("id_jobtitle");
	static By currentSalaryinLakh 	= By.id("id_salary_lakh");
	static By currentSalaryinThousand = By.id("id_salary_thousand");
	static By startDate 			= By.id("id_startyear");
	static By startDateCalender 	= By.className("vdp-datepicker__calendar");
	static By departmentCheckbox 	= By.cssSelector("[data-value='10013']");
	static By industryCheckbox 		= By.cssSelector(".card [data-value='18']");

	//Skill
	static By skillsTextbox 	  	= By.id("id_skills");

	//Education
	static By educationQualificationCheckbox 		= By.cssSelector("[data-value='110']");
	static By intituteNameTextbox = By.id("id_institute");
	static By courseTypeCheckbox = By.cssSelector("[data-label='1'");
	static By courseCompletionYear = By.cssSelector("[placeholder='Year']");



	@BeforeClass
	public void TestSetup() {
		_PixelCheckDriver = getDriver(_PixelCheckDriver, "chromeMobile");
		_Wait = new WebDriverWait(_PixelCheckDriver, 15);
		_PixelCheckDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if(baseUrl.contains("pp-www"))
			mBaseUrl = "https://pp-m.shine.com";
		else if(baseUrl.contains("sumosc"))
			mBaseUrl = "https://sm.shine.com";
		else 
			mBaseUrl = "https://m.shine.com";
	}


	@Test(priority=0)
	public void test_Pixel_Flow1(){
		_PixelCheckDriver.navigate().to(mBaseUrl+url);
		_Utility.Thread_Sleep(2000);
		perform_Registration(_PixelCheckDriver);
		_Utility.Thread_Sleep(3000);
		assertEquals(LogAnalyzer.analyzeSpecificRequest(_PixelCheckDriver), true);
	}

	@Test(priority=1)
	public void test_Pixel_Flow2(){
		_PixelCheckDriver.navigate().to(mBaseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(2000);
		_PixelCheckDriver.navigate().to(mBaseUrl+url2);
		perform_Registration(_PixelCheckDriver);
		_Utility.Thread_Sleep(2000);
		uploadResume("resumefile", _PixelCheckDriver);
		_Utility.Thread_Sleep(2000);
		assertEquals(LogAnalyzer.analyzeSpecificRequest(_PixelCheckDriver), true);
	}


	@AfterClass(alwaysRun = true)
	public void quitbrowser(){
		if(_PixelCheckDriver!=null)
			_PixelCheckDriver.quit();

	}


	@AfterMethod(alwaysRun = true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _PixelCheckDriver);
	}

	/**
	 * Perform Registration
	 * @param _PixelCheckDriver
	 */
	public void perform_Registration(WebDriver _PixelCheckDriver) {
		_Utility.Thread_Sleep(1000);
		//_PixelCheckDriver.findElement(_Registration.regiFormAction).getAttribute("action");
		registrationFlow1(true, _PixelCheckDriver);
		_PixelCheckDriver.findElement(submitFirstRegsitartionForm).click();
		_Utility.Thread_Sleep(1000);
		fill_reg_page_2(_PixelCheckDriver);
	}


	/**
	 * Resume uploader method
	 * @param _ResumeUploaddriver
	 */
	public void uploadResume(String filename, WebDriver _ResumeUploaddriver) {
		APP_LOGS.debug("Uploading Resume...");
		String resume = userDirectory+CONFIG.getProperty(filename);
		APP_LOGS.debug(resume);
		_ResumeUploaddriver.findElement(resumeUploadid).sendKeys(resume);
		/*JavascriptExecutor executor = (JavascriptExecutor)_ResumeUploaddriver;
		executor.executeScript("document.getElementById('id_resume_file').setAttribute('value', '"+resume+"')");*/
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug("Resume uploaded Successfully");
	}


	/**
	 * Registration flow 1 Method
	 * @param _RegFlow1Driver
	 */
	private void registrationFlow1(boolean isFresher, WebDriver _RegFlow1Driver) {
		_Wait = new WebDriverWait(_RegFlow1Driver, 15);
		String mEmailid = _Utility.generateEmailid();
		APP_LOGS.debug("Registered email id is ...." + mEmailid);
		_Wait.until(ExpectedConditions.elementToBeClickable(userNameTxt));
		_RegFlow1Driver.findElement(userNameTxt).clear();
		_RegFlow1Driver.findElement(userNameTxt).sendKeys(TestBaseSetup.username);

		_RegFlow1Driver.findElement(emailIdTxt).clear();
		_RegFlow1Driver.findElement(emailIdTxt).sendKeys(mEmailid);

		_RegFlow1Driver.findElement(mobileTxt).clear();
		_RegFlow1Driver.findElement(mobileTxt).sendKeys("9876556789");

		_Utility.Thread_Sleep(1000);

	}

	/**
	 * Registration flow 2
	 * @param _MidoutDriver
	 */

	public static void fill_reg_page_2(WebDriver _MidoutDriver) {
		fill_personal_details(_MidoutDriver);
		fill_experience_details(_MidoutDriver);
		fill_skill_details(_MidoutDriver);
		fill_education_details(_MidoutDriver);
	}


	public static void fill_personal_details(WebDriver _MidoutDriver) {
		_MidoutDriver.findElement(genderCheckbox).click();
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(cityCheckbox).click();
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(haveYouEverWorkedCheckbox).click();
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(workExperienceYearTextbox).click();
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(experienceValueInYears).click();
		_Utility.Thread_Sleep(500);
		_MidoutDriver.findElement(workExperienceMonthTextbox).click();
		_Utility.Thread_Sleep(1000);
	}

	public static void fill_experience_details(WebDriver _MidoutDriver) {
		_MidoutDriver.findElement(companyNameTextbox).sendKeys("HT Media");
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(companyNameTextbox).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(designationTextbox).sendKeys("Developer");
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(designationTextbox).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(startDate).click();
		_MidoutDriver.findElement(startDateCalender).click();
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(currentSalaryinLakh).click();
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(By.cssSelector("[data-value='2']")).click();
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(By.cssSelector("[data-value='0']")).click();
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(departmentCheckbox).click();
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(By.cssSelector("[data-value='4559']")).click();
		_Utility.Thread_Sleep(2000);
		_MidoutDriver.findElement(industryCheckbox).click();
		_Utility.Thread_Sleep(2000);
	}

	public static void fill_education_details(WebDriver _MidoutDriver) {
		_MidoutDriver.findElement(educationQualificationCheckbox).click();
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(By.cssSelector("[data-value='503']")).click();
		_Utility.Thread_Sleep(1500);
		_MidoutDriver.findElement(intituteNameTextbox).sendKeys("IIM");
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(intituteNameTextbox).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(500);
		_MidoutDriver.findElement(intituteNameTextbox).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(500);
		_MidoutDriver.findElement(courseCompletionYear).click();
		_Utility.Thread_Sleep(500);
		_MidoutDriver.findElement(By.xpath("//*[contains(text(),'2011')]")).click();
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(courseTypeCheckbox).click();
		_Utility.Thread_Sleep(1500);
	}

	public static void fill_skill_details(WebDriver _MidoutDriver) {
		_MidoutDriver.findElement(skillsTextbox).sendKeys("Java");
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(skillsTextbox).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(skillsTextbox).sendKeys("Maven");
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(skillsTextbox).sendKeys(Keys.ENTER);
		_MidoutDriver.findElement(skillsTextbox).sendKeys("Python");
		_Utility.Thread_Sleep(1000);
		_MidoutDriver.findElement(skillsTextbox).sendKeys(Keys.ENTER);
		_MidoutDriver.findElement(By.cssSelector("[class='btn btn_next']")).click();
		_Utility.Thread_Sleep(1000);
	}


}
