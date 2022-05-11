package com.shine.m.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

import com.shine.listener.WebEventListener;
import com.shine.base.TestBaseSetup;

public class Test_RegistrationComplete extends TestBaseSetup {

	String mBaseUrl = "";
	public static String EmailID1;
	private static WebDriver _RegistrationDriver;
	static WebDriverWait _Wait;
	WebEventListener _EventListener;
	private EventFiringWebDriver _EventFiringWebDriver;

	static By registrationBtn				= By.id("id_register");
	By regiFormAction						= By.id("id_registration");
	static By userNameTxt					= By.id("id_name");
	static By emailIdTxt					= By.id("id_email");
	static By mobileTxt						= By.id("id_cell_phone");
	static By locationDD					= By.id("id_location_span");
	static By locationsubDD					= By.id("id_candidate_location_406");
	static By genderCheckBox				= By.id("id_gender_0");
	static By expTxt						= By.id("id_totalExperience");
	static By submitFirstRegsitartionForm			= By.cssSelector("input.ui-btn.search-btn");

	static By alreadyRegValTxt				= By.xpath("(//label[@class='error_msg'])[1]");

	//By nextButton     	  = By.cssSelector(".ui-btn.search-btn.search-btn-100.cls_partial_save");
	static By nextButton   		  	 = By.linkText("Save");
	static By profileTitle     	  	 = By.id("id_profile_title");
	static By selectGender     	  	 = By.id("id_gender_0");
	//Select Salary
	static By selectSalaryLakhdd     = By.id("id_salary_lakh_span");
	static By selectSalaryLakhsubdd  = By.id("id_salary_in_lakh_5");
	static By selectSalaryThoudd     = By.id("id_salary_thousand_span");
	static By selectSalaryThousubdd  = By.id("id_salary_in_thousand_5");

	static By jobTitle      		 = By.id("id_job_title");
	static By companyName   		 = By.id("id_company_name");
	//industry
	static By industrydd    		 = By.id("id_industry_span");
	static By industrysdd 	  		 = By.id("id_industry_id_19");
	//Department or FA
	static By departmentdd			 = By.id("id_area_span");
	static By departmentsdd          = By.id("id_sub_field_1301");
	static By departmentsddForScroll = By.id("id_sub_field_4559");
	//Job start
	static By startMonthDD			 = By.id("id_start_month_span");
	static By startMonthSubDD		 = By.id("id_start_month_5");
	static By jobStartYeardd         = By.id("id_start_year_span");
	static By jobStartYearsdd   	 = By.id("id_start_year_2011");
	static By isCurrentJob           = By.id("id_is_current");
	//Education - click - select drop down and than sub drop down
	static By eduSpe                 = By.id("id_education_specialization_span");
	static By eduSpeDD               = By.cssSelector("li[label='M.Tech'] label[data-group='group_28']");
	static By eduSoeDDForScroll	     = By.cssSelector("li[label='M.Pharma'] label[data-group='group_26']");
	static By eduSpeSubdd            = By.id("id_education_specialization_128_555");
	static By eduInstituteName       = By.id("id_institute_name");
	static By yopDD                  = By.id("id_year_of_passout_span");
	static By yopSubdd               = By.id("id_year_of_passout_2012");
	//Skill
	static By skillName 	  	     = By.id("id_reg_skill");
	static By skillExpDD             = By.id("id_years_of_experience_0_span");
	static By skillExpSubDD          = By.id("id_skills-0-years_of_experience_6");
	static By skillSubmit            = By.cssSelector("input.ui-btn.search-btn");

	By resumeUploadid = By.id("id_resume_file");
	
	int reg1Counter, reg2Counter, resumeUploadCounter = 0;

	@BeforeClass
	public void TestSetup() {
		_RegistrationDriver = getDriver(_RegistrationDriver, "chromeMobile");
		// Initializing EventFiringWebDriver using Firefox WebDriver instance
		_EventFiringWebDriver = new EventFiringWebDriver(_RegistrationDriver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		_EventListener = new WebEventListener();
		_EventFiringWebDriver.register(_EventListener);
		if(baseUrl.contains("sumosc"))
			mBaseUrl = "https://sm.shine.com";
		else mBaseUrl = "https://m.shine.com";
		_EventFiringWebDriver.get(mBaseUrl);
		_Wait = new WebDriverWait(_RegistrationDriver, 15);
	}



	@Test(priority = 1)
	public void Test_Registration_Page1() {
		_Utility.scrollTOElement(registrationBtn, _EventFiringWebDriver);
		_Utility.Thread_Sleep(2000);
		_Wait.until(ExpectedConditions.elementToBeClickable(registrationBtn));
		_EventFiringWebDriver.findElement(registrationBtn).click();
		_Utility.Thread_Sleep(4000);
		fill_registration_page1(_EventFiringWebDriver);
		_EventFiringWebDriver.findElement(submitFirstRegsitartionForm).click();
		_Utility.Thread_Sleep(5000);
		reg1Counter = _EventListener.getRegistrationClickCount();
		TestBaseSetup.clickEventList.add(reg1Counter);
		Assert.assertEquals(_EventFiringWebDriver.getCurrentUrl(), mBaseUrl+"/myshine/sem/registration/details/");
	}


	int flag = 0;
	@Test(priority = 2)
	public void Verify_Registration_Page2() {
		fill_job_details(_EventFiringWebDriver);
		fill_education_details(_EventFiringWebDriver);
		fill_skill_details(_EventFiringWebDriver);
		_Utility.Thread_Sleep(5000);
		reg2Counter = _EventListener.getRegistrationClickCount() - reg1Counter;
		TestBaseSetup.clickEventList.add(reg2Counter);
	}

	@Test(priority=3)
	public void test_upload_resume(){
		_Utility.Thread_Sleep(3000);
		uploadResume("resumefile", _EventFiringWebDriver);
		_Utility.Thread_Sleep(2000);
		resumeUploadCounter =  _EventListener.getRegistrationClickCount() - (reg2Counter+reg1Counter);
		if(resumeUploadCounter<=0) {
			TestBaseSetup.clickEventList.add(1);
			flag =1;
		}
		else
			TestBaseSetup.clickEventList.add(resumeUploadCounter);
		assertEquals(_EventFiringWebDriver.getCurrentUrl(), mBaseUrl+"/myshine/registration/confirm/");
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		takeScreenshotOnFailure(testResult, _EventFiringWebDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		int click_count =  _EventListener.getRegistrationClickCount();
		if(flag==1)
			click_count = click_count +1;
		APP_LOGS.debug("Registration click = "+click_count);
		TestBaseSetup.clickEventList.add(click_count);
		if(_EventFiringWebDriver!=null)
			_EventFiringWebDriver.quit();

	}
	 



	/**
	 * Registration flow 1 Method
	 * @param _EventFiringWebDriver
	 */
	private void fill_registration_page1(EventFiringWebDriver _EventFiringWebDriver) {
		WebDriverWait _Wait = new WebDriverWait(_EventFiringWebDriver, 15);
		EmailID1 = _Utility.generateEmailid();
		APP_LOGS.debug("Registered email id is ...." + EmailID1);
		TestBaseSetup.emailid = EmailID1;
		_Wait.until(ExpectedConditions.elementToBeClickable(userNameTxt));
		_EventFiringWebDriver.findElement(userNameTxt).clear();
		_EventFiringWebDriver.findElement(userNameTxt).sendKeys(TestBaseSetup.username);

		_Wait.until(ExpectedConditions.elementToBeClickable(emailIdTxt));
		_EventFiringWebDriver.findElement(emailIdTxt).clear();
		_EventFiringWebDriver.findElement(emailIdTxt).sendKeys(EmailID1);

		_EventFiringWebDriver.findElement(mobileTxt).clear();
		_EventFiringWebDriver.findElement(mobileTxt).sendKeys("9876556789");

		_EventFiringWebDriver.findElement(locationDD).click();
		_Utility.Thread_Sleep(2000);
		_EventFiringWebDriver.findElement(locationsubDD).click();

		_Utility.Thread_Sleep(2000);

		_EventFiringWebDriver.findElement(genderCheckBox).click();

		_Utility.scrollTOElement(expTxt, _EventFiringWebDriver);
		_EventFiringWebDriver.findElement(expTxt).sendKeys("5");
	}

	/**************************************REG PAGE 2****************************************************************/



	public static void fill_job_details(EventFiringWebDriver _EventFiringWebDriver) {
		APP_LOGS.info("Add job details - profile >> Executing");
		_Utility.Thread_Sleep(2000);
		try {

			_EventFiringWebDriver.findElement(jobTitle).clear();
			_EventFiringWebDriver.findElement(jobTitle).sendKeys("Soft");
			_Utility.Thread_Sleep(3000);
			_EventFiringWebDriver.findElement(jobTitle).sendKeys(Keys.ARROW_DOWN);
			_EventFiringWebDriver.findElement(jobTitle).sendKeys(Keys.TAB);
			// _EventFiringWebDriver.findElement(By.id("ui-id-14")).click();
			_Utility.Thread_Sleep(1000);
			_EventFiringWebDriver.findElement(jobTitle).sendKeys(Keys.TAB);
			_EventFiringWebDriver.findElement(companyName).clear();
			_EventFiringWebDriver.findElement(companyName).click();
			_EventFiringWebDriver.findElement(companyName).sendKeys("HCL");
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(companyName).sendKeys(Keys.ARROW_DOWN);
			_EventFiringWebDriver.findElement(companyName).sendKeys(Keys.TAB);
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(companyName).sendKeys(Keys.TAB);
			_EventFiringWebDriver.findElement(industrydd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(industrysdd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(departmentdd).click();
			_Utility.scrollTOElement(departmentsddForScroll, _EventFiringWebDriver);
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(departmentsdd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(startMonthDD).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(startMonthSubDD).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(jobStartYeardd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(jobStartYearsdd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(selectSalaryLakhdd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(selectSalaryLakhsubdd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(selectSalaryThoudd).click();
			_Utility.Thread_Sleep(2000);
			_EventFiringWebDriver.findElement(selectSalaryThousubdd).click();


		} catch (Exception e) {

			System.out.println("fresher candidate");

		}
		clickOnSaveButton(_EventFiringWebDriver);
		APP_LOGS.info("Add job details - profile >> Done");
	}


	public static void fill_education_details(EventFiringWebDriver _EventFiringWebDriver) {
		APP_LOGS.info("Add education details >> Executing");
		WebDriverWait _Wait = new WebDriverWait(_EventFiringWebDriver, 20);
		_Utility.Thread_Sleep(4000);
		_EventFiringWebDriver.findElement(eduSpe).click();
		_Utility.scrollTOElement(eduSoeDDForScroll, _EventFiringWebDriver);
		_Utility.Thread_Sleep(4000);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(eduSpeDD));
		_EventFiringWebDriver.findElement(eduSpeDD).click();
		_Utility.Thread_Sleep(2000);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(eduSpeSubdd));
		_EventFiringWebDriver.findElement(eduSpeSubdd).click();
		_Utility.Thread_Sleep(2000);
		_EventFiringWebDriver.findElement(eduInstituteName).clear();
		_EventFiringWebDriver.findElement(eduInstituteName).sendKeys("Indian");
		_Utility.Thread_Sleep(1000);
		_EventFiringWebDriver.findElement(eduInstituteName).sendKeys(Keys.ARROW_DOWN);
		_EventFiringWebDriver.findElement(eduInstituteName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(3000);
		_EventFiringWebDriver.findElement(eduInstituteName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(3000);
		_EventFiringWebDriver.findElement(yopDD).click();
		_Utility.Thread_Sleep(2000);
		_EventFiringWebDriver.findElement(yopSubdd).click();

		clickOnSaveButton(_EventFiringWebDriver);
		APP_LOGS.info("Add education details >> Done");

	}



	public static void fill_skill_details(EventFiringWebDriver _EventFiringWebDriver) {
		APP_LOGS.info("Add skill details >> Executing");
		_Utility.Thread_Sleep(8000);
		List<WebElement> skill_list_from_jt_suggester = _EventFiringWebDriver.findElements(By.cssSelector("#id_recommendedSkillContainer a"));
		skill_list_from_jt_suggester.get(0).click();
		_EventFiringWebDriver.findElement(skillName).sendKeys("sal");
		_Utility.Thread_Sleep(1000);
		_EventFiringWebDriver.findElement(skillName).sendKeys(Keys.ARROW_DOWN);
		_EventFiringWebDriver.findElement(skillName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(2000);
		_EventFiringWebDriver.findElement(skillName).sendKeys(",");
		_EventFiringWebDriver.findElement(skillName).sendKeys(Keys.TAB);
		List<WebElement> skill_list = _EventFiringWebDriver.findElements(By.cssSelector("#id_recommendedSkillContainer a"));
		skill_list.get(0).click();
		_Utility.Thread_Sleep(5000);
		clickOnSaveButton(_EventFiringWebDriver);
		APP_LOGS.info("Add skill details >> Done");

	}

	public static void clickOnSaveButton(EventFiringWebDriver _EventFiringWebDriver){
		WebDriverWait _Wait = new WebDriverWait(_EventFiringWebDriver, 20);
		_Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(nextButton));
		_EventFiringWebDriver.findElement(nextButton).click();
	}

	/**
	 * Resume uploader method
	 * @param _EventFiringWebDriver
	 */
	public void uploadResume(String filename, EventFiringWebDriver _EventFiringWebDriver) {
		APP_LOGS.debug("Uploading Resume...");
		String resume = userDirectory+CONFIG.getProperty(filename);
		APP_LOGS.debug(resume);
		_EventFiringWebDriver.findElement(resumeUploadid).sendKeys(resume);
		/*JavascriptExecutor executor = (JavascriptExecutor)_EventFiringWebDriver;
		executor.executeScript("document.getElementById('id_resume_file').setAttribute('value', '"+resume+"')");*/
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug("Resume uploaded Successfully");
	}




}