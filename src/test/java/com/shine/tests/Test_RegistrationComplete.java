package com.shine.tests;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.tests.Test_MidoutRegistration;
import com.shine.common.utils.CommonUtils;
import com.shine.listener.WebEventListener;

import org.testng.annotations.AfterMethod;

import org.testng.Assert;
import org.testng.ITestResult;	


public class Test_RegistrationComplete extends TestBaseSetup {

	String url1 = "https://www.shine.com/sem/registration/?vendorid=25006&utm_source=google&utm_medium=cpc&utm_campaign=Content_Job_Portal&utm_adgroup=Naukrihub_com&utm_keyword={keyword=define}";
	String url2 = "https://www.shine.com/registration/?vendorid=29009&utm_source=google&utm_medium=cpc&utm_campaign=Content_Job_Portal&utm_adgroup=Naukrihub_com&utm_keyword={keyword=define}";

	String logout_url = "https://www.shine.com/myshine/logout/";
	String email_reg;
	public static int random;
	WebDriver _RegistrationDriver;
	WebEventListener _EventListener;
	private EventFiringWebDriver _EventFiringWebDriver;
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
	static By errorMsg          	= By.cssSelector(".error_message");
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
	//Education - click - select drop down and than sub drop down
	static By eduSpe                = By.id("id_hq_110");
	static By eduSpeDD              = By.cssSelector("[data-value='503']");
	static By eduInstituteName      = By.id("id_institute");
	static By yopDD                 = By.id("id_institute_year");
	static By yopDD_value                 = By.cssSelector(".vdp-datepicker__calendar");
	static By course_type           = By.id("id_course_type_1");
	//Select Salary
	static By selectSalaryLakhdd    = By.id("id_salary_lakh");
	static By selectSalaryLakhdd_value = By.cssSelector("[data-value='3']");
	//static By selectSalaryThoudd    = By.id("id_salary_thousand");
	static By selectSalaryThoudd_value    = By.xpath("//div[@id='id_salary_thousand_modal']//li[contains(text(),'30')]");

	static By skillName         = By.id("id_skills");
	static By skillExp 		    = By.id("id_skillform-0-level_id");
	static By addNewSkillBtn    = By.cssSelector("a.add-row > strong");


	public static By skillName(int position){
		return By.id("skillform-"+position+"-id_skillform-0-txt_skills");//Return skill Name div locator
	}
	public static By skillExp(int position){
		return By.id("skillform-"+position+"-id_skillform-0-level_id");	//Return skill Exp div locator
	}

	By email_already_exist_login_link = By.xpath("//*[@for='id_email']/a");


	int reg1Counter, reg2Counter, resumeUploadCounter = 0;

	@BeforeClass(alwaysRun=true)
	public void TestSetup() {
		_RegistrationDriver = getDriver(_RegistrationDriver);
		// Initializing EventFiringWebDriver using Firefox WebDriver instance
		_EventFiringWebDriver = new EventFiringWebDriver(_RegistrationDriver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		_EventListener = new WebEventListener();
		_EventFiringWebDriver.register(_EventListener);
		OpenBaseUrl(_EventFiringWebDriver);
		_Wait = new WebDriverWait(_EventFiringWebDriver, 15);
		APP_LOGS.debug("Start of registration tests");
	}



	@Test (priority = 0)
	public void open_RegistrationPage() {
		_EventFiringWebDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug("site url: "+baseUrl);
		APP_LOGS.debug("site url: "+_EventFiringWebDriver.getCurrentUrl());
		_EventFiringWebDriver.findElement(registrationLink).click();
		_Utility.Thread_Sleep(3000);
		Assert.assertEquals(_EventFiringWebDriver.getCurrentUrl(), baseUrl+"/registration/parser/");
	}


	@Test (priority = 1)
	public void registrationStep1() {
		String flowFound = "/registration/parser/flow-2/";
		APP_LOGS.debug("Flow Found: "+flowFound);
		email_reg = ExecuteRegistrationFlow(false, flowFound, _EventFiringWebDriver);
		_EventFiringWebDriver.findElement(submitFirstRegsitartionForm).click();
		reg1Counter = _EventListener.getRegistrationClickCount();
		APP_LOGS.debug("Registration 1 Click count = "+reg1Counter);
		TestBaseSetup.clickEventList.add(reg1Counter);
	}



	@Test (priority = 2)
	private void registrationStep2() throws InterruptedException {
		fill_personal_details(email_reg, isFresher, false, _EventFiringWebDriver);
		fill_job_details(_EventFiringWebDriver);
		fill_skill(_EventFiringWebDriver);	
		fill_education_details(_EventFiringWebDriver);
		_Utility.Thread_Sleep(6000);
		reg2Counter = _EventListener.getRegistrationClickCount() - reg1Counter;
		APP_LOGS.debug("Registration 2 Click count = "+reg2Counter);
		TestBaseSetup.clickEventList.add(reg2Counter);
	}

	int flag = 0;
	@Test (priority = 3)
	private void upload_resume() {
		upload_resume(_EventFiringWebDriver);
		_Utility.Thread_Sleep(6000);
		resumeUploadCounter = _EventListener.getRegistrationClickCount() - (reg2Counter+reg1Counter);
		APP_LOGS.debug("Upload Resume Click count = "+resumeUploadCounter);
		if(resumeUploadCounter<=0) {
			TestBaseSetup.clickEventList.add(1);
			flag =1;
		}
		else
			TestBaseSetup.clickEventList.add(resumeUploadCounter);
		
		_RegistrationDriver.navigate().to(logout_url);
	}

	/*
	 * @Test(priority=4) public void perform_registration_on_Campaign_url1() throws
	 * InterruptedException { _RegistrationDriver.navigate().to(url1);
	 * _Utility.Thread_Sleep(3000); APP_LOGS.debug("site url: "+url1);
	 * 
	 * email_reg = registrationFlow2(_EventFiringWebDriver);
	 * _EventFiringWebDriver.findElement(submitFirstRegsitartionForm).click();
	 * 
	 * fill_personal_details(email_reg, isFresher, false, _EventFiringWebDriver);
	 * fill_job_details(_EventFiringWebDriver);
	 * fill_education_details(_EventFiringWebDriver);
	 * fill_skill(_EventFiringWebDriver); _Utility.Thread_Sleep(6000);
	 * 
	 * upload_resume(_EventFiringWebDriver); _Utility.Thread_Sleep(6000);
	 * 
	 * _RegistrationDriver.navigate().to(logout_url);
	 * 
	 * }
	 * 
	 * @Test(priority=5) public void perform_registration_on_Campaign_url2() throws
	 * InterruptedException { _RegistrationDriver.navigate().to(url2);
	 * _Utility.Thread_Sleep(3000); APP_LOGS.debug("site url: "+url2);
	 * 
	 * email_reg = registrationFlow2(_EventFiringWebDriver);
	 * _EventFiringWebDriver.findElement(submitFirstRegsitartionForm).click();
	 * 
	 * fill_personal_details(email_reg, isFresher, false, _EventFiringWebDriver);
	 * fill_job_details(_EventFiringWebDriver);
	 * fill_education_details(_EventFiringWebDriver);
	 * fill_skill(_EventFiringWebDriver); _Utility.Thread_Sleep(6000);
	 * 
	 * upload_resume(_EventFiringWebDriver); _Utility.Thread_Sleep(6000);
	 * 
	 * }
	 */

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _EventFiringWebDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		int click_count = _EventListener.getRegistrationClickCount();
		if(flag==1)
			click_count = click_count +1;
		APP_LOGS.debug("Registration click = "+click_count);
		TestBaseSetup.clickEventList.add(click_count);
		if(_EventFiringWebDriver!=null)
			_EventFiringWebDriver.quit();

	}


	/*******************************************REG 1****************************************************************/


	/**
	 * Automatically work base on the onscreen registration form
	 * @param formAction
	 * @param _Updflowdriver
	 */
	public static String ExecuteRegistrationFlow(boolean isFresher, String formAction, EventFiringWebDriver _Updflowdriver) {
		formAction = formAction.replace(baseUrl, "");
		switch (formAction) {
		case "/registration/parser/flow-2/":
			flowFlag = 2;
			return registrationFlow2(_Updflowdriver);
		default:
			APP_LOGS.info("Form action not found, instead found "+formAction);
			return null;

		}
	}




	/**
	 * Registration flow 2 Method 
	 * @param _RegFlow2Driver
	 */
	private static String registrationFlow2(EventFiringWebDriver _RegFlow2Driver) {
		String email = _Utility.generateEmailid();
		APP_LOGS.debug("Registered email id is ...." + email);
		_Wait = new WebDriverWait(_RegFlow2Driver, 15);
		_Wait.until(ExpectedConditions.elementToBeClickable(emailIdTxt));
		_RegFlow2Driver.findElement(emailIdTxt).clear();
		_RegFlow2Driver.findElement(emailIdTxt).sendKeys(email);
		_RegFlow2Driver.findElement(emailIdTxt).sendKeys(Keys.TAB);
		_Wait.until(ExpectedConditions.elementToBeClickable(userNameTxt));
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
	public static void fill_personal_details(String email, boolean isFresher, boolean specialexpFlow, EventFiringWebDriver _RegMidoutDriver) {
		_Utility.Thread_Sleep(3000);
		_RegMidoutDriver.findElement(genderCheckBox).click();
		_RegMidoutDriver.findElement(locationDD).click();
		
		if(isFresher==true)	
		{	
			_RegMidoutDriver.findElement(workexp_fresher).click();
		}
		if(specialexpFlow==true) {	
		_RegMidoutDriver.findElement(workexp).click();
		_RegMidoutDriver.findElement(expDD).click();
		_RegMidoutDriver.findElement(expDD_value).click();
		_RegMidoutDriver.findElement(exp_monthDD_value).click();
		
		}
		else
		 _RegMidoutDriver.findElement(workexp).click();
		 _RegMidoutDriver.findElement(expDD).click();
		 _RegMidoutDriver.findElement(expDD_value).click();
		 try {
			 _RegMidoutDriver.findElement(exp_monthDD_value).click();
		 }catch(Exception e) {
			 //element is not found, clicking logic below
			 _RegMidoutDriver.findElement(exp_monthDD).click();
			 _RegMidoutDriver.findElement(exp_monthDD_value).click();
		 }
		 
		clickOnSaveButton(_RegMidoutDriver);
	}



	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_job_details(EventFiringWebDriver _RegMidoutDriver) {
		_Utility.Thread_Sleep(1000);

		_RegMidoutDriver.findElement(companyName).clear();
		_RegMidoutDriver.findElement(companyName).click();
		_RegMidoutDriver.findElement(companyName).sendKeys("HCL Technologies Limited");
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(companyName).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(companyName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);
		
		_RegMidoutDriver.findElement(jobTitle).clear();
		_RegMidoutDriver.findElement(jobTitle).sendKeys("Software Developer");
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
	public static void fill_education_details(EventFiringWebDriver _RegMidoutDriver) {
		WebDriverWait _Wait = new WebDriverWait(_RegMidoutDriver, 15);
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(eduSpe).click();
		_RegMidoutDriver.findElement(eduSpeDD).click();
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(eduInstituteName).clear();
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys("IIIT Hyderabad");
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys(Keys.ENTER);
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(yopDD).click();
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(By.xpath("//*[@class='vdp-datepicker__calendar']//*[text()='2020']")).click();
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(course_type).click();
		_Utility.Thread_Sleep(1000);
		clickOnSaveButton(_RegMidoutDriver);

	}

	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_skill_details (EventFiringWebDriver _RegMidoutDriver) {
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
	public static void clickOnSaveButton(EventFiringWebDriver driver) {
		try {
			List<WebElement> crossButtons = driver.findElements(nextButton);
			for(WebElement button : crossButtons) {
				if(button.isDisplayed()) {
					button.click();
					break;
				}
			}
		} catch (Throwable e) {
			APP_LOGS.error(e.getMessage());
		}
	}

	/**
	 * upload_resume
	 * @param _PixelCheckDriver
	 */
	public void upload_resume(EventFiringWebDriver _PixelCheckDriver) {
		//UPLOAD VALID RESUME
		Test_MidoutRegistration.resumeFileUpload(_PixelCheckDriver, "resumefile");
		_Utility.Thread_Sleep(5000);
	}
	
	public void fill_skill(EventFiringWebDriver _Updflowdriver) throws InterruptedException {
				Thread.sleep(2000);
				_RegistrationDriver.findElement(By.id("id_skills")).click();
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys("Selenium");
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys("Java");
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys("HTML");
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				_RegistrationDriver.findElement(By.id("id_skills")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				clickOnSaveButton(_EventFiringWebDriver);

	}


}
