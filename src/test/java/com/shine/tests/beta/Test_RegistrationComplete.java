package com.shine.tests.beta;


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
import com.shine.common.utils.CommonUtils;
import com.shine.listener.WebEventListener;

import org.testng.annotations.AfterMethod;

import org.testng.Assert;
import org.testng.ITestResult;	


public class Test_RegistrationComplete extends TestBaseSetup {

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
	static By submitFirstRegsitartionForm	= By.cssSelector("[class='uplod_btn']");
	static By userNameTxt			= By.id("id_name");
	static By emailIdTxt			= By.id("id_email");
	static By mobileTxt				= By.id("id_cell_phone");
	static By locationDD			= By.id("id_candidate_location");
	static By genderCheckBox		= By.id("id_gender_0");
	static By expTxt				= By.id("id_totalExperience");
	static By errorMsg          	= By.cssSelector("[for='id_email']");
	static By passwordtxt       	= By.id("id_raw_password");
	static By name 					= By.id("id_name");
	static By location				= By.id("id_candidate_location");
	static By regiFormAction		= By.id("reg1");
	static By expDD					= By.id("id_experience_in_years");

	static By jobTitle				= By.id("id_job_title");
	static By companyName			= By.id("id_company_name");
	//industry
	static By industrydd			= By.id("id_industry_id");
	//Department or FA
	static By departmentdd			= By.id("id_sub_field");
	//Job start
	static By jobStartMonthDD		= By.id("id_start_month");
	static By jobStartYeardd        = By.id("id_start_year");
	static By isCurrentJob          = By.id("id_is_current");
	//Education - click - select drop down and than sub drop down
	static By eduSpe                = By.xpath("//span[contains(text(),'Highest Qualification')]");
	static By eduSpeDD              = By.cssSelector("li[label='M.Tech'] label[data-group='group_28']");
	static By eduSoeDDForScroll	    = By.cssSelector("li[label='M.C.A'] label[data-group='group_23']");
	static By eduSpeSubdd           = By.id("id_education_specialization_128_555");
	static By eduInstituteName      = By.id("id_institute_name");
	static By yopDD                 = By.id("id_year_of_passout");
	//Select Salary
	static By selectSalaryLakhdd    = By.id("id_salary_in_lakh");
	static By selectSalaryThoudd    = By.id("id_salary_in_thousand");

	static By skillName         = By.id("id_reg_skill");
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
		TestBaseSetup.clickEventList.add(reg1Counter);
	}



	@Test (priority = 2)
	private void registrationStep2() {
		fill_personal_details(email_reg, isFresher, false, _EventFiringWebDriver);
		fill_job_details(_EventFiringWebDriver);
		fill_education_details(_EventFiringWebDriver);
		fill_skill_details(_EventFiringWebDriver);	
		_Utility.Thread_Sleep(6000);
		reg2Counter = _EventListener.getRegistrationClickCount() - reg1Counter;
		TestBaseSetup.clickEventList.add(reg2Counter);
	}

	int flag = 0;
	@Test (priority = 3)
	private void upload_resume() {
		upload_resume(_EventFiringWebDriver);
		_Utility.Thread_Sleep(6000);
		resumeUploadCounter = _EventListener.getRegistrationClickCount() - (reg2Counter+reg1Counter);
		if(resumeUploadCounter<=0) {
			TestBaseSetup.clickEventList.add(1);
			flag =1;
		}
		else
			TestBaseSetup.clickEventList.add(resumeUploadCounter);
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _EventFiringWebDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		int click_count = _EventListener.getRegistrationClickCount();;
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
		_Utility.Thread_Sleep(4000);
		_RegMidoutDriver.findElement(userNameTxt).clear();
		String extarctUsername = StringUtils.substringBefore(email, "_").trim();
		APP_LOGS.debug("USERNAME: "+extarctUsername);
		_RegMidoutDriver.findElement(userNameTxt).sendKeys(extarctUsername);
		new Select(_RegMidoutDriver.findElement(locationDD)).selectByVisibleText("Delhi");
		if(isFresher==true)
			new Select(_RegMidoutDriver.findElement(expDD)).selectByVisibleText("0 Yr");
		else if(specialexpFlow==true)	
			new Select(_RegMidoutDriver.findElement(expDD)).selectByVisibleText("2 Yrs");
		else 	
			new Select(_RegMidoutDriver.findElement(expDD)).selectByVisibleText("5 Yrs");
		_RegMidoutDriver.findElement(genderCheckBox).click();
		clickOnSaveButton(_RegMidoutDriver);
	}



	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_job_details(EventFiringWebDriver _RegMidoutDriver) {
		_Utility.Thread_Sleep(3000);
		try {

			_RegMidoutDriver.findElement(jobTitle).clear();
			_RegMidoutDriver.findElement(jobTitle).sendKeys("Soft");
			_Utility.Thread_Sleep(4000);
			_RegMidoutDriver.findElement(jobTitle).sendKeys(Keys.ARROW_DOWN);
			_Utility.Thread_Sleep(300);
			_RegMidoutDriver.findElement(jobTitle).sendKeys(Keys.TAB);
			// _RegMidoutDriver.findElement(By.id("ui-id-14")).click();

			_RegMidoutDriver.findElement(companyName).clear();
			_RegMidoutDriver.findElement(companyName).click();
			_RegMidoutDriver.findElement(companyName).sendKeys("HCL");
			_Utility.Thread_Sleep(2000);
			_RegMidoutDriver.findElement(companyName).sendKeys(Keys.ARROW_DOWN);
			_Utility.Thread_Sleep(300);
			_RegMidoutDriver.findElement(companyName).sendKeys(Keys.TAB);
			_Utility.Thread_Sleep(1000);
			_RegMidoutDriver.findElement(companyName).sendKeys(Keys.TAB);
			_RegMidoutDriver.findElement(By.xpath("(//button[@type='button'])[1]")).click();
			_RegMidoutDriver.findElement(By.id("id_industry_id_18")).click();

			_RegMidoutDriver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
			_Utility.scrollTOElement(By.id("id_sub_field_1405"), _RegMidoutDriver);
			_RegMidoutDriver.findElement(By.id("id_sub_field_1405")).click();

			new Select(_RegMidoutDriver.findElement(selectSalaryLakhdd)).selectByVisibleText("5");
			new Select(_RegMidoutDriver.findElement(selectSalaryThoudd)).selectByVisibleText("0");

			new Select(_RegMidoutDriver.findElement(jobStartMonthDD)).selectByVisibleText("Jan");
			new Select(_RegMidoutDriver.findElement(jobStartYeardd)).selectByVisibleText("2012");

			_RegMidoutDriver.findElement(isCurrentJob).click();

		} catch (Exception e) {

			System.out.println("fresher candidate");

		}
		clickOnSaveButton(_RegMidoutDriver);
	}


	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_education_details(EventFiringWebDriver _RegMidoutDriver) {
		WebDriverWait _Wait = new WebDriverWait(_RegMidoutDriver, 15);
		_Utility.Thread_Sleep(3000);
		_RegMidoutDriver.findElement(eduSpe).click();
		_Utility.scrollTOElement(eduSoeDDForScroll, _RegMidoutDriver);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(eduSpeDD));
		_RegMidoutDriver.findElement(eduSpeDD).click();
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(eduSpeSubdd));
		_RegMidoutDriver.findElement(eduSpeSubdd).click();

		_RegMidoutDriver.findElement(eduInstituteName).clear();
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys("indian institu");
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys(Keys.ARROW_DOWN);
		_Utility.Thread_Sleep(3000);
		_RegMidoutDriver.findElement(eduInstituteName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(1000);
		new Select(_RegMidoutDriver.findElement(yopDD)).selectByVisibleText("2012");
		clickOnSaveButton(_RegMidoutDriver);

	}

	/**
	 * 
	 * @param _RegMidoutDriver
	 */
	public static void fill_skill_details (EventFiringWebDriver _RegMidoutDriver) {
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.navigate().refresh();
		_Utility.Thread_Sleep(5000);
		APP_LOGS.debug("Submit skills");
		List<WebElement> skill_list_from_jt_suggester = _RegMidoutDriver.findElements(By.cssSelector("#id_recommendedSkillContainer a"));
		skill_list_from_jt_suggester.get(0).click();
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(skillName).clear();
		_RegMidoutDriver.findElement(skillName).sendKeys("ja");
		_Utility.Thread_Sleep(3000);
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.ARROW_DOWN);
		_Utility.Thread_Sleep(2000);
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(3000);
		_RegMidoutDriver.findElement(skillName).sendKeys(",");
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.TAB);
		_RegMidoutDriver.findElement(skillName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(5000);
		List<WebElement> skill_list = _RegMidoutDriver.findElements(By.cssSelector("#id_recommendedSkillContainer a"));
		skill_list.get(0).click();
		_Utility.Thread_Sleep(3000);
		clickOnSaveButton(_RegMidoutDriver);

	}


	static By nextButton = By.cssSelector(".cls_partial_save");

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
			e.printStackTrace();
		}
	}

	static By resumeFile       = By.id("id_file");
	/**
	 * upload_resume
	 * @param _PixelCheckDriver
	 */
	public void upload_resume(EventFiringWebDriver _PixelCheckDriver) {
		//UPLOAD VALID RESUME
		resumeFileUpload("resumefile");
		_Utility.Thread_Sleep(5000);
	}

	public void resumeFileUpload(String filename) {
		_EventFiringWebDriver.findElement(resumeFile).sendKeys(System.getProperty("user.dir")+CONFIG.getProperty(filename));
		_Utility.Thread_Sleep(1000);
	}


}
