package com.shine.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonUtils;

public class Test_CreateBadProfile extends TestBaseSetup {

	public static int random;
	WebDriver _RegistrationDriver;
	static WebDriverWait _Wait;
	static CommonUtils _CommonUtils;
	static boolean isFresher;
	static int flowFlag = 0;
	
	static By registrationLink             = By.partialLinkText("Register");
	static By submitFirstRegsitartionForm	= By.cssSelector("[class='btn mb-4 align-self-end mb-4']");
	
	static By userNameTxt			= By.id("id_name");
	static By emailIdTxt			= By.id("id_email");
	static By mobileTxt				= By.id("id_cell_phone");
	static By locationDD			= By.cssSelector(".Bangalore");
	static By genderCheckBox		= By.cssSelector(".female");
	static By expTxt				= By.id("id_totalExperience");
	static By passwordtxt       	= By.id("id_password");
	static By name 					= By.id("id_name");
	static By location				= By.id("id_location");
	static By regiFormAction		= By.id("reg1");
	static By expDD					= By.id("id_exp_years");
	static By expDD_value           = By.cssSelector("[data-value='5']");
	static By exp_monthDD           = By.id("id_exp_month");
	static By exp_monthDD_value     = By.cssSelector("[data-value='3']");
	static By workexp               = By.name("radioExperience");
	static By workexp_fresher		= By.cssSelector(".fresher");	
	static By jobTitle				= By.id("id_jobtitle");
	static By companyName			= By.id("id_company");
	//industry
	static By industrydd			= By.id("id_industry_18");
	//Department or FA
	static By departmentdd			= By.id("id_functionalArea_10013");
	static By departmentdd_value    = By.cssSelector("[data-value='1313']");
	//Job start
	static By jobStartMonthDD		= By.id("id_start_month");
	static By jobStartYeardd        = By.id("id_startyear");
	static By isCurrentJob          = By.id("id_is_current");
	//Skill
	static By skillName             = By.id("id_skills");
	static By skillExp 		        = By.id("id_skillform-0-level_id");
	static By addNewSkillBtn        = By.cssSelector("a.add-row > strong");
	
	//Education - click - select drop down and than sub drop down
	static By eduSpe                = By.id("id_hq_110");
	static By eduSpeDD              = By.cssSelector("[data-value='503']");
	static By eduInstituteName      = By.id("id_institute");
	static By yopDD                 = By.id("id_institute_year");
	static By yopDD_value           = By.cssSelector(".vdp-datepicker__calendar");
	static By course_type           = By.id("id_course_type_1");
	//Select Salary
	static By selectSalaryLakhdd    = By.id("id_salary_lakh");
	static By selectSalaryLakhdd_value = By.cssSelector("[data-value='3']");
	//static By selectSalaryThoudd    = By.id("id_salary_thousand");
	static By selectSalaryThoudd_value    = By.xpath("//div[@id='id_salary_thousand_modal']//li[contains(text(),'30')]");
	static By error_msg                  = By.xpath("//*[@class='error_message']");


	public static By skillName(int position){
		return By.id("skillform-"+position+"-id_skillform-0-txt_skills");//Return skill Name div locator
	}
	public static By skillExp(int position){
		return By.id("skillform-"+position+"-id_skillform-0-level_id");	//Return skill Exp div locator
	}

	By email_already_exist_login_link = By.xpath("//*[@for='id_email']/a");



	@BeforeClass(alwaysRun=true)
	public void TestSetup() {
		_RegistrationDriver = getDriver(_RegistrationDriver);
		OpenBaseUrl(_RegistrationDriver);
		_Wait = new WebDriverWait(_RegistrationDriver, 15);
		APP_LOGS.debug("Start of registration tests");
	}


	@Test (priority = 0)
	public void open_RegistrationPage() {
		_RegistrationDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug("site url: "+baseUrl);
		APP_LOGS.debug("site url: "+_RegistrationDriver.getCurrentUrl());
		_RegistrationDriver.findElement(registrationLink).click();
		_Utility.Thread_Sleep(3000);
		Assert.assertEquals(_RegistrationDriver.getCurrentUrl(), baseUrl+"/registration/parser/");
	}


	@Test (priority = 1)
	public void registrationStep1() {
		badprofile_email = fill_registration(_RegistrationDriver);
		_RegistrationDriver.findElement(submitFirstRegsitartionForm).click();
	}



	@Test (priority = 2)
	private void registrationStep2() {
		fill_personal_details(badprofile_email, isFresher, false, _RegistrationDriver);
		fill_education_details(_RegistrationDriver);
		//fill_job_details(_RegistrationDriver);
		fill_skill_details(_RegistrationDriver);
		_Utility.Thread_Sleep(6000);
	}

	@Test (priority = 3)
	private void upload_resume() {
		upload_resume(_RegistrationDriver);
		_Utility.Thread_Sleep(6000);
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _RegistrationDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_RegistrationDriver!=null)
			_RegistrationDriver.quit();

	}


	/*******************************************REG 1****************************************************************/



	/**
	 * Registration flow 2 Method 
	 * @param _RegFlow2Driver
	 */
	private static String fill_registration(WebDriver _RegFlow2Driver) {
		String email = _Utility.generateEmailid();
		APP_LOGS.debug("Registered email id is ...." + email);
		_Wait = new WebDriverWait(_RegFlow2Driver, 15);
		_Wait.until(ExpectedConditions.elementToBeClickable(emailIdTxt));
		_RegFlow2Driver.findElement(emailIdTxt).clear();
		_RegFlow2Driver.findElement(emailIdTxt).sendKeys(email);
		_RegFlow2Driver.findElement(emailIdTxt).sendKeys(Keys.TAB);
		_RegFlow2Driver.findElement(userNameTxt).clear();
		_RegFlow2Driver.findElement(userNameTxt).sendKeys(TestBaseSetup.username);
		_RegFlow2Driver.findElement(passwordtxt).sendKeys("123456");
		_RegFlow2Driver.findElement(passwordtxt).sendKeys(Keys.TAB);
		_RegFlow2Driver.findElement(mobileTxt).clear();
		_RegFlow2Driver.findElement(mobileTxt).sendKeys("9876556789");
		return email;

	}


	/*******************************************REG2*******************************************************************/


	/**
	 * 
	 * @param isFresher
	 * @param specialexpFlow
	 * @param _RegMidoutDriver
	 */
	public static void fill_personal_details(String email, boolean isFresher, boolean specialexpFlow, WebDriver _RegMidoutDriver) {
		_RegMidoutDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		_Utility.Thread_Sleep(3000);		
		_RegMidoutDriver.findElement(genderCheckBox).click();
		_Utility.Thread_Sleep(3000);		
		_RegMidoutDriver.findElement(locationDD).click();
		_Utility.Thread_Sleep(2000);		
        _RegMidoutDriver.findElement(workexp_fresher).click();		
		clickOnSaveButton(_RegMidoutDriver);
	}



	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_job_details(WebDriver _RegMidoutDriver) {

		_Utility.Thread_Sleep(1000);

		_RegMidoutDriver.findElement(companyName).clear();
		_RegMidoutDriver.findElement(companyName).click();
		_RegMidoutDriver.findElement(companyName).sendKeys("HCL Technologies Limited");
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(companyName).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(companyName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);
		
		_RegMidoutDriver.findElement(jobTitle).clear();
		_RegMidoutDriver.findElement(jobTitle).sendKeys("Fuck Software Engineer");
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(jobTitle).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(jobTitle).sendKeys(Keys.ENTER);			
		_Utility.Thread_Sleep(2000);
		
		_RegMidoutDriver.findElement(jobStartYeardd).click();
		_Utility.Thread_Sleep(500);
		_RegMidoutDriver.findElement(By.xpath("//*[contains(text(),'January')]")).click();						
	//	_RegMidoutDriver.findElement(isCurrentJob).click();
		
		_Utility.Thread_Sleep(2000);
		
		_RegMidoutDriver.findElement(selectSalaryLakhdd).click();
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(selectSalaryLakhdd_value).click();			
		_Utility.Thread_Sleep(500);
		
		_RegMidoutDriver.findElement(selectSalaryThoudd_value).click();			
		_Utility.Thread_Sleep(1000);
		
		_RegMidoutDriver.findElement(departmentdd).click();
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(departmentdd_value).click();			
		_Utility.Thread_Sleep(2000);
					
		_RegMidoutDriver.findElement(industrydd).click();			
		_Utility.Thread_Sleep(2000);

  		clickOnSaveButton(_RegMidoutDriver);

	}


	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_education_details(WebDriver _RegMidoutDriver) {
		
		WebDriverWait _Wait = new WebDriverWait(_RegMidoutDriver, 15);
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(eduSpe).click();
		_RegMidoutDriver.findElement(eduSpeDD).click();
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(eduInstituteName).clear();
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys("fuck institute name");
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(yopDD).click();
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(yopDD_value).click();
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(course_type).click();
		_Utility.Thread_Sleep(1000);
		clickOnSaveButton(_RegMidoutDriver);

	}

	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_skill_details (WebDriver _RegMidoutDriver) {
		
		_Utility.Thread_Sleep(1000);
		APP_LOGS.debug("Submit skills");
		_RegMidoutDriver.findElement(skillName).clear();
		_RegMidoutDriver.findElement(skillName).sendKeys("java,");
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);

		_RegMidoutDriver.findElement(skillName).clear();
		_RegMidoutDriver.findElement(skillName).sendKeys("python,");
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);

		_RegMidoutDriver.findElement(skillName).clear();
		_RegMidoutDriver.findElement(skillName).sendKeys("databases,");
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);

		_RegMidoutDriver.findElement(skillName).clear();
		_RegMidoutDriver.findElement(skillName).sendKeys("c++,");
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);

		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(skillName).clear();
		_RegMidoutDriver.findElement(skillName).sendKeys("javascript,");
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);

		clickOnSaveButton(_RegMidoutDriver);
		


	}


	static By nextButton = By.cssSelector(".btn_next_rec");

	/**
	 * 
	 * @param driver
	 */
	public static void clickOnSaveButton(WebDriver driver) {
		try {
			List<WebElement> crossButtons = driver.findElements(nextButton);
			for(WebElement button : crossButtons) {
				if(button.isDisplayed()) {
					button.click();
					break;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * upload_resume
	 * @param _PixelCheckDriver
	 */
	public void upload_resume(WebDriver _PixelCheckDriver) {
		//UPLOAD VALID RESUME
		Test_MidoutRegistration.resumeFileUpload(_PixelCheckDriver, "resumefile");
		_Utility.Thread_Sleep(5000);
	}


}
