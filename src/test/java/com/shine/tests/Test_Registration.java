package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonUtils;
import com.shine.listener.LogAnalyzer;
import com.shine.listener.WebEventListener;
import com.shine.common.utils.ChangePassword;

import org.testng.annotations.AfterMethod;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;	


public class Test_Registration extends TestBaseSetup {

	public static String EmailID1;
	public static int random;
	WebDriver _RegistrationDriver;
	static WebDriverWait _Wait;
	static CommonUtils _CommonUtils;
	static boolean isFresher;
	static int flowFlag = 0;
	WebEventListener _EventListener;
	private EventFiringWebDriver _EventFiringWebDriver;

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

	By email_already_exist_login_link = By.className("error_sign");



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
		_Utility.Thread_Sleep(1000);
		APP_LOGS.debug("site url: "+baseUrl);
		APP_LOGS.debug("site url: "+_RegistrationDriver.getCurrentUrl());
		_RegistrationDriver.findElement(registrationLink).click();
		_Utility.Thread_Sleep(1500);
		Assert.assertEquals(_RegistrationDriver.getCurrentUrl(), baseUrl+"/registration/parser/");
	}

	@Test(priority=1)
	public void verify_open_registration_page_collect_view() {
		assertEquals(LogAnalyzer.analyzePageViewRequest(_RegistrationDriver), true);

	}


	@Test (priority = 2)
	public void registrationStep1() {
		String flowFound = "/registration/parser/flow-2/";
		APP_LOGS.debug("Flow Found: "+flowFound);
		String[] data = ExecuteRegistrationFlow(false, flowFound, _RegistrationDriver);
		email_main =  data[0];
		_RegistrationDriver.findElement(submitFirstRegsitartionForm).click();
	}

	/*
	 * @Test(priority=3) public void verify_registration_page1_collect_view() {
	 * assertEquals(LogAnalyzer.analyzePageViewRequest(_RegistrationDriver), true);
	 * 
	 * }
	 */


	@Test(priority = 4)
	public void verify_email_already_registered_alert() {
		_Utility.Thread_Sleep(1000);
		_RegistrationDriver.get(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(1000);
		open_RegistrationPage();
		_Wait.until(ExpectedConditions.elementToBeClickable(emailIdTxt));
		_RegistrationDriver.findElement(emailIdTxt).click();
		_RegistrationDriver.findElement(emailIdTxt).sendKeys(EmailID1);
		_RegistrationDriver.findElement(emailIdTxt).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(2000);
		_RegistrationDriver.findElement(error_msg).getText();
		APP_LOGS.debug("The error message is  "+error_msg);
		Assert.assertEquals("Email id already exists", "Email id already exists");

	}


	@Test(priority = 5, dependsOnMethods= {"verify_email_already_registered_alert"})
	public void verify_already_registered_alert_login_link() {
		_RegistrationDriver.findElement(email_already_exist_login_link).click();
		_Utility.Thread_Sleep(1000);
		String actual_url = _RegistrationDriver.getCurrentUrl();
		assertEquals(actual_url, baseUrl+"/myshine/login/");
	}

	@Test(priority = 6, alwaysRun=true)
	public void ChangePassword() {
		if(flowFlag==1) {
			ChangePassword _ChangePassword = new ChangePassword();
			_ChangePassword.resetPasswordRequest(TestBaseSetup.emailid);
		}
	}



	@Test (priority = 7)
	private void registrationStep2() {
		_RegistrationDriver.get(baseUrl);
		loggedInShine(_RegistrationDriver, emailid, "123456");
		executeTestFlow(false, false, flowFlag, emailid, _RegistrationDriver);
		_Utility.Thread_Sleep(2000);
	}

	@Test(priority=8)
	public void verify_registration_page2_collect_view() {
		assertEquals(LogAnalyzer.analyzePageViewRequest(_RegistrationDriver), true);

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
	 * Automatically work base on the onscreen registration form
	 * @param formAction
	 * @param _Updflowdriver
	 */
	public static String[] ExecuteRegistrationFlow(boolean isFresher, String formAction, WebDriver _Updflowdriver) {
		formAction = formAction.replace(baseUrl, "");
		switch (formAction) {
		case "/registration/parser/flow-1/":
			flowFlag = 1;
			return registrationFlow1(isFresher, false, _Updflowdriver);
		case "/registration/parser/flow-2/":
			flowFlag = 2;
			return registrationFlow2(_Updflowdriver);
		default:
			APP_LOGS.info("Form action not found, instead found "+formAction);
			return null;

		}
	}



	/**
	 * Registration flow 1 Method
	 * @param _RegFlow1Driver
	 */
	private static String[] registrationFlow1(boolean isFresher, boolean specialexpFlow, WebDriver _RegFlow1Driver) {
		_Wait = new WebDriverWait(_RegFlow1Driver, 15);
		EmailID1 = _Utility.generateEmailid();
		APP_LOGS.debug("Registered email id is ...." + EmailID1);
		TestBaseSetup.emailid = EmailID1;
		_Wait.until(ExpectedConditions.elementToBeClickable(userNameTxt));
		_RegFlow1Driver.findElement(userNameTxt).clear();
		_RegFlow1Driver.findElement(userNameTxt).sendKeys(TestBaseSetup.username);

		_Wait.until(ExpectedConditions.elementToBeClickable(emailIdTxt));
		_RegFlow1Driver.findElement(emailIdTxt).clear();
		_RegFlow1Driver.findElement(emailIdTxt).sendKeys(EmailID1);

		_RegFlow1Driver.findElement(mobileTxt).clear();
		_RegFlow1Driver.findElement(mobileTxt).sendKeys("9876556789");
		_RegFlow1Driver.findElement(passwordtxt).sendKeys("123456");
		_RegFlow1Driver.findElement(passwordtxt).sendKeys(Keys.TAB);
		
		/*
		 * _RegFlow1Driver.findElement(genderCheckBox).click();
		 * _RegFlow1Driver.findElement(locationDD).click(); _Utility.Thread_Sleep(1000);
		 * 
		 * if(isFresher==true) new
		 * Select(_RegFlow1Driver.findElement(expDD)).selectByVisibleText("0 Yr"); else
		 * if(specialexpFlow==true) new
		 * Select(_RegFlow1Driver.findElement(expDD)).selectByVisibleText("2 Yrs"); else
		 * new Select(_RegFlow1Driver.findElement(expDD)).selectByVisibleText("5 Yrs");
		 */
		
		String[] data =  {EmailID1, String.valueOf(flowFlag)} ;
		return data;
	}


	/**
	 * Registration flow 2 Method 
	 * @param _RegFlow2Driver
	 */
	private static String[] registrationFlow2(WebDriver _RegFlow2Driver) {		
		EmailID1 = _Utility.generateEmailid();
		APP_LOGS.debug("Registered email id is ...." + EmailID1);
		TestBaseSetup.emailid = EmailID1;
		_Wait = new WebDriverWait(_RegFlow2Driver, 15);
		_Wait.until(ExpectedConditions.elementToBeClickable(emailIdTxt));
		_RegFlow2Driver.findElement(emailIdTxt).clear();
		_RegFlow2Driver.findElement(emailIdTxt).sendKeys(EmailID1);
		_RegFlow2Driver.findElement(emailIdTxt).sendKeys(Keys.TAB);
		_Wait.until(ExpectedConditions.elementToBeClickable(userNameTxt));
		_RegFlow2Driver.findElement(userNameTxt).clear();
		_RegFlow2Driver.findElement(userNameTxt).sendKeys(TestBaseSetup.username);
		_RegFlow2Driver.findElement(mobileTxt).clear();
		_RegFlow2Driver.findElement(mobileTxt).sendKeys("9876556789");
		_RegFlow2Driver.findElement(passwordtxt).sendKeys("123456");
		_RegFlow2Driver.findElement(passwordtxt).sendKeys(Keys.TAB);
		String[] data =  {EmailID1, String.valueOf(flowFlag)} ;
		return data;

	}


	/*******************************************REG2*******************************************************************/


	public static void executeTestFlow(boolean isFresher, boolean specialexpFlow, int flowFlag,String email, WebDriver _RegMidoutDriver) {
		if(flowFlag == 1 && isFresher == false) {
			executeTestCase(isFresher, specialexpFlow, "Flow 1 Exp", email, _RegMidoutDriver);
		}
		else if(flowFlag == 2 && isFresher == false) {
			executeTestCase(isFresher, specialexpFlow, "Flow 2 Exp", email, _RegMidoutDriver);
		}
		else if(flowFlag == 1 && isFresher == true) {
			executeTestCase(isFresher, specialexpFlow, "Flow 1 Fresher", email, _RegMidoutDriver);
		}
		else if(flowFlag==2 && isFresher == true) {
			executeTestCase(isFresher, specialexpFlow, "Flow 2 Fresher", email, _RegMidoutDriver);
		}
		else 
			throw new SkipException("No flow found");
	}



	/**
	 * Automatically work base on the onscreen widget
	 * @param isFresher
	 * @param specialexpFlow
	 * @param testCaseName
	 * @param email
	 * @param _RegMidoutDriver
	 */
	public static void executeTestCase(boolean isFresher, boolean specialexpFlow, String testCaseName, String email, WebDriver _RegMidoutDriver) {
		APP_LOGS.info("Head title: "+testCaseName);
		switch (testCaseName) {
		case "Flow 1 Exp":
			fill_job_details(_RegMidoutDriver);
			fill_education_details(_RegMidoutDriver);
			fill_skill_details(_RegMidoutDriver);
			break;
		case "Flow 2 Exp":
			fill_personal_details_quick_reg(email, isFresher, specialexpFlow, _RegMidoutDriver);
			fill_job_details(_RegMidoutDriver);
			fill_skill_details(_RegMidoutDriver);
			fill_education_details(_RegMidoutDriver);
			break;
		case "Quick Reg Flow 2 Exp":
			fill_personal_details_quick_reg(email, isFresher, specialexpFlow, _RegMidoutDriver);
			fill_job_details(_RegMidoutDriver);
			fill_skill_details(_RegMidoutDriver);			
			fill_education_details(_RegMidoutDriver);
			break;	
		case "SEM Flow 2 Exp":
			fill_job_details(_RegMidoutDriver);
			fill_education_details(_RegMidoutDriver);
			fill_skill_details(_RegMidoutDriver);			
			break;
		case "Flow 1 Fresher":
			fill_education_details(_RegMidoutDriver);
			fill_skill_details(_RegMidoutDriver);
			break;
		case "Flow 2 Fresher":
			fill_personal_details(email, isFresher, specialexpFlow, _RegMidoutDriver);
			fill_education_details(_RegMidoutDriver);
			fill_skill_details(_RegMidoutDriver);
			break;
		default:
			APP_LOGS.info("test Case Name issue: "+testCaseName);

		}
	}

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
	 * @param isFresher
	 * @param specialexpFlow
	 * @param _RegMidoutDriver
	 */
	public static void fill_personal_details_quick_reg(String email, boolean isFresher, boolean specialexpFlow, WebDriver _RegMidoutDriver) {
		_RegMidoutDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		_Utility.Thread_Sleep(3000);		
		_RegMidoutDriver.findElement(genderCheckBox).click();
		_Utility.Thread_Sleep(3000);		
		_RegMidoutDriver.findElement(locationDD).click();
		_Utility.Thread_Sleep(2000);
		
		if(isFresher==true)	
		{	
			_RegMidoutDriver.findElement(workexp_fresher).click();
		}
		if(specialexpFlow==true) {	
		_RegMidoutDriver.findElement(workexp).click();
		_RegMidoutDriver.findElement(expDD).click();
		_Utility.Thread_Sleep(1000);
		_RegMidoutDriver.findElement(expDD_value).click();
		_Utility.Thread_Sleep(1000);
		try {
		_RegMidoutDriver.findElement(exp_monthDD_value).click();
		 }catch(Exception e) {
			 //element is not found, clicking logic below
		 _RegMidoutDriver.findElement(exp_monthDD).click();
		 _RegMidoutDriver.findElement(exp_monthDD_value).click();
		 }
		
		}
		else
		 _RegMidoutDriver.findElement(workexp).click();
		 _RegMidoutDriver.findElement(expDD).click();
		 _Utility.Thread_Sleep(1000);
		 _RegMidoutDriver.findElement(expDD_value).click();
		 _Utility.Thread_Sleep(1000);		 
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
	public static void fill_education_details(WebDriver _RegMidoutDriver) {
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

		_Utility.Thread_Sleep(1000);
		clickOnSaveButton(_RegMidoutDriver);

	}

	public static void  fill_skill_details_with_suggestion (WebDriver _RegMidoutDriver) {
		_Utility.Thread_Sleep(1000);
		APP_LOGS.debug("Submit skills");
		List<WebElement> skill_list_from_jt_suggester = _RegMidoutDriver.findElements(By.cssSelector("#id_recommendedSkillContainer a"));
		int size = skill_list_from_jt_suggester.size();
		for(int i=0;i<size;i++) {
			skill_list_from_jt_suggester.get(i).click();
			skill_list_from_jt_suggester = _RegMidoutDriver.findElements(By.cssSelector("#id_recommendedSkillContainer a"));
			size--;
		}
		_Utility.Thread_Sleep(1000);
		clickOnSaveButton(_RegMidoutDriver);

	}


	//static By nextButton = By.cssSelector(".cls_partial_save");
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




}
