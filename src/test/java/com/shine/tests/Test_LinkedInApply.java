package com.shine.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_LinkedInApply extends TestBaseSetup{

	WebDriver driver;

	//For job search
	By searchTextBox = By.id("id_q");
	//For username
	By usernameTextBox = By.id("username");
	//For Mobile
	By mobileTextBox =By.id("id_mobile");
	//for Job Title
	By jobTitle = By.id("id_job_title");


	@BeforeClass
	public void testSetup(){
		driver = getDriver(driver);
		driver.get(baseUrl);
		//	driver.findElement(By.cssSelector("[class='js-close-noti-popup pushNotification']")).click();
		_Utility.Thread_Sleep(2000);
		_Utility.set_flag_checkTimeStamp(driver);
		Test_Search.simpleSearch("Software Developer", driver);
	}


	@Test(priority=1)
	public void testRegistrationBeforeApply() throws InterruptedException{
		//Click on Apply
		driver.findElement(By.linkText("Apply")).click();
		_Utility.Thread_Sleep(2000);
		driver.findElement(By.id("id_email_login")).click();
		//LinkedIn 
		driver.findElement(By.cssSelector("[class='cls_linkedInApply']")).click();
		_Utility.Thread_Sleep(1000);;
		//Enter User name
		driver.findElement(usernameTextBox).click();
		driver.findElement(usernameTextBox).sendKeys("shinesocialtest@gmail.com");
		driver.findElement(usernameTextBox).sendKeys(Keys.TAB);
		//Enter Password
		driver.findElement(By.id("password")).sendKeys("shine@123");
		//Enter Click
		driver.findElement(By.cssSelector("[class= 'btn__primary--large from__button--floating']")).click();

	}
	

	@Test(priority=2)
	public void testFillLinkedinForm(){

		driver.findElement(mobileTextBox).click();
		driver.findElement(mobileTextBox).clear();
		driver.findElement(mobileTextBox).sendKeys("9876556789");

		Select drplocation= new Select(driver.findElement(By.id("id_location")));
		drplocation.selectByVisibleText("Delhi");

		driver.findElement(By.id("id_experience_in_years")).click();
		Select expyears= new Select(driver.findElement(By.id("id_experience_in_years")));
		expyears.selectByVisibleText("5 Yrs");
		driver.findElement(By.id("id_experience_in_years")).sendKeys(Keys.TAB);

		Select expmonths= new Select(driver.findElement(By.id("id_experience_in_months")));
		expmonths.selectByVisibleText("0 Month");
		driver.findElement(By.id("id_experience_in_months")).sendKeys(Keys.TAB);


		Select industry= new Select(driver.findElement(By.id("id_industry")));
		industry.selectByVisibleText("IT - Software");
		driver.findElement(By.id("id_industry")).sendKeys(Keys.TAB);


		Select department= new Select(driver.findElement(By.id("id_functional_area")));
		department.selectByVisibleText("Audit");
		driver.findElement(By.id("id_functional_area")).sendKeys(Keys.TAB);


		Select salarylakh= new Select(driver.findElement(By.id("id_salary_in_lakh")));
		salarylakh.selectByVisibleText("6");
		driver.findElement(By.id("id_salary_in_lakh")).sendKeys(Keys.TAB);


		Select salarythousand= new Select(driver.findElement(By.id("id_salary_in_thousand")));
		salarythousand.selectByVisibleText("30");
		driver.findElement(By.id("id_salary_in_thousand")).sendKeys(Keys.TAB);


		driver.findElement(By.id("id_resume_type_0")).click();
		driver.findElement(By.id("id_resume")).sendKeys(System.getProperty("user.dir")+"/src/test/resources/data/Resume.docx");

		driver.findElement(By.xpath("//*[@id=\"id_linkedInApplyForm\"]/div/div/ul/li[10]/input")).click();



	}

	@Test(priority=3)
	public void testRegistrationForm(){
		//Capturing Text
		String actualApplyMsg = driver.findElement(By.cssSelector("[class='successmessage']")).getText().trim();
		assertEquals(actualApplyMsg, "Your Application has been submitted successfully.");

		APP_LOGS.debug("Text: "+actualApplyMsg);

		//Enter Job Title
		driver.findElement(jobTitle).click();
		driver.findElement(jobTitle).sendKeys("QA Analyst");
		_Utility.Thread_Sleep(2000);
		driver.findElement(jobTitle).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(1000);

		//Enter Company name
		driver.findElement(By.id("id_company_name")).sendKeys("IBM Technologies");
		driver.findElement(By.id("id_company_name")).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(2000);

		//Select Industry
		driver.findElement(By.cssSelector("[class='selectboxdiv cls_has_tooltip cls_selectbox']")).click();
		Select drpindustry= new Select(driver.findElement(By.cssSelector("[class='selectboxdiv cls_has_tooltip cls_selectbox']")));
		drpindustry.selectByVisibleText("IT - Software");
		driver.findElement(By.cssSelector("[class='selectboxdiv cls_has_tooltip cls_selectbox']")).sendKeys(Keys.TAB);

		//Select Department
		driver.findElement(By.xpath("//*[@id=\"id_registration_from_two\"]/div[1]/div[2]/ul/li[4]/div[1]/div/button/span")).click();
		_Utility.Thread_Sleep(1000);
		driver.findElement(By.id("id_functional_area_4556")).click();

		//Duration of job
		driver.findElement(By.id("id_start_month")).click();
		Select drpexp_in_month= new Select(driver.findElement(By.id("id_start_month")));
		drpexp_in_month.selectByVisibleText("Feb");

		driver.findElement(By.id("id_start_year")).click();
		Select drpexp_in_years= new Select(driver.findElement(By.id("id_start_year")));
		drpexp_in_years.selectByVisibleText("2017");

		//Currently I work here
		driver.findElement(By.id("id_is_current")).click();

		//Scroll down
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		_Utility.Thread_Sleep(3000);		


		//Highest Education
		driver.findElement(By.cssSelector("[class='placeholder']")).click();
		driver.findElement(By.cssSelector("[label=\"B.A\"] label[data-group=\"group_2\"]")).click();;
		driver.findElement(By.id("id_education_specialization_102_501")).click();

		//Institue name
		driver.findElement(By.id("id_institute_name")).click();
		driver.findElement(By.id("id_institute_name")).sendKeys("Institute of Arts and Humanities");
		driver.findElement(By.id("id_institute_name")).sendKeys(Keys.TAB);

		//Year of Passout
		driver.findElement(By.id("id_year_of_passout")).click();
		Select passingyear= new Select(driver.findElement(By.id("id_year_of_passout")));
		passingyear.selectByVisibleText("2011");

		//Skills
		driver.findElement(By.id("id_skillform-0-txt_skills")).click();
		driver.findElement(By.id("id_skillform-0-txt_skills")).sendKeys("Java,JavaScript");

		//Click on Register
		driver.findElement(By.cssSelector("[class='uplod_btn pullLeft mar_tp13'][value='Register']")).click();

		//Verify Text
		String actual_apply_msg2 = driver.findElement(By.id("id_confirmMsg")).getText().trim();
		assertEquals(actual_apply_msg2, "Congratulations Abhishek, you have successfully registered!");

		APP_LOGS.debug("Text: "+actual_apply_msg2);
		
	}
	
	
	@Test(priority=4)
	public void testMyProfile(){
		//Click on Skip
		driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form/ul/li[12]/div/a[2]")).click();

		//Capture Text
		String actual_apply_msg = driver.findElement(By.cssSelector("span.AlreadyAppliedtext")).getText();
		assertEquals("Your application has been submitted successfully." + "\n" +"The company may try to reach you at  shinesocialtest@gmail.com or 9876556789. Update if incorrect.",actual_apply_msg);
		APP_LOGS.debug(actual_apply_msg);
		_Utility.Thread_Sleep(2000);

		//Go to My Profile
		WebElement ele = driver.findElement(By.cssSelector("[class='dropdown posRel infomenu carrerInfo'] [class='dropdown-toggle'] [class='caret']"));
		Actions action = new Actions(driver);
		action.moveToElement(ele).perform();

		driver.findElement(By.xpath("//*[@id=\"ReactContainer\"]/div/header/nav/div/div/ul[1]/li[4]/ul/li[2]/ul/li[1]/a")).click();

		// Scroll a little down
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,500)");
		_Utility.Thread_Sleep(3000);

		//Editing personal Info

		//Click on edit
		driver.findElement(By.xpath("//*[@id=\"id_myProfileNew\"]/li[1]/span/a/span")).click();
		_Utility.Thread_Sleep(2000);
		driver.findElement(By.id("id_email")).click();
		driver.findElement(By.id("id_email")).clear();
		driver.findElement(By.id("id_email")).sendKeys(_Utility.generateEmailid());
	
		//Click on DOB to open calender
		WebElement dateBox = driver.findElement(By.id("id_date_of_birth"));
		dateBox.click();

		//Enter DOB
		driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/div/div")).click();
		_Utility.Thread_Sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[2]/td[4]/a")).click();

		driver.findElement(By.id("id_location")).sendKeys(Keys.TAB);
		driver.findElement(By.id("id_gender_0")).sendKeys(Keys.TAB);

		//Click on save
		driver.findElement(By.cssSelector("[class='submitred'][value='Save']")).click();

		String currentURL= driver.getCurrentUrl();
		assertEquals(baseUrl+"/myshine/myprofile/", currentURL);	
	}

	@AfterMethod
	public void takeScreenshot(ITestResult testResult){
		takeScreenshotOnFailure(testResult, driver);
	}

	@AfterClass
	public void quitbrowser(){
		if(driver!=null)
			driver.quit();
	}

}



