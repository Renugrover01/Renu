package com.shine.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class Test_ContactUs extends TestBaseSetup{
	WebDriver _ContactUsDriver;
	WebDriverWait wait;

	String name     = "Testing - Kindly Ignore";
	String mobileno = "9876556789";
	String desc     = "This feedback is for Shine internal testing. Kindly ignore";
	String path     =  userDirectory+"/src/test/resources/data/";
	String candidate = "0";


	By contactNameTxt	= By.id("contactname");
	By contactemail		= By.id("contactemail");
	By cellPhoneTxt		= By.id("id_CellPhone");
	By description		= By.id("txtdesc");
	By Submit			= By.cssSelector("[class='redbtn mar']");

	By contactNameError	= By.cssSelector("[for='contactname']");
	By contactemailError= By.cssSelector("[for='contactemail']");
	By cellPhoneTxtError= By.cssSelector("[for='id_CellPhone']");
	By descriptionError = By.cssSelector("[for='txtdesc']");

	By resume 			= By.id("id_file");
	By resumeError		= By.cssSelector("[for='id_file']");

	By officialid       = By.id("contactemail");
	By contactCompany   = By.id("contactcompany");
	By contactCompanyUrl= By.id("contactcompanyurl");
	By contRemovalReason= By.id("contactremovalreason");
	By contactSubject   = By.id("contactsubject");

	By contactUSLink        = By.linkText("Contact Us");
	By reportJobPostingLink = By.linkText("Report a Job Posting");


	/*
	 * public By radioButton(int position) { return
	 * By.xpath("(//*[@class='cls_contacttype cls_input_validate']/input)["+position
	 * +"]"); }
	 */


	@BeforeClass
	public void TestSetup() throws Exception {
		_ContactUsDriver = getDriver(_ContactUsDriver);
		wait = new WebDriverWait(_ContactUsDriver, 20);
		_ContactUsDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		_ContactUsDriver.get(baseUrl+"/contactus/");
		_Utility.Thread_Sleep(500);
		_Utility.clickOnNotification(_ContactUsDriver);
	}

	
	  @Test(priority = 0) public void test_InvalidResume_Validation() {
	  fIleUploader("invalid_img.tpg"); 
	  _Utility.Thread_Sleep(500);
	  _ContactUsDriver.findElement(Submit).click(); 
	  _Utility.Thread_Sleep(500);
	  _Utility.explicitlyWaitForElementToLoad(_ContactUsDriver, resumeError, 15);
	 // String actual = _ContactUsDriver.findElement(resumeError).getText();
	  //Assert.assertEquals(actual, "Upload only a .jpg, .jpeg, .png, .bmp, .pdf, .doc, .docx, .rtf or .txt file.");
	  
	  }
	 


	@Test(priority = 1)
	public void test_BlankName_Validation() {
		try {
			checkFIeldWithMSG(contactNameError, "Name is required");
		}catch(AssertionError aex) {
			checkFIeldWithMSG(contactNameError, "Contact Name is required");
		}
	}

	@Test(priority = 2)
	public void test_BlankEmail_Validation() {
		checkFIeldWithMSG(contactemailError, "Email is required");
	}

	@Test(priority = 3)
	public void test_BlankMobile_Validation() {
		checkFIeldWithMSG(cellPhoneTxtError, "Phone no is required");
	}

	@Test(priority = 4)
	public void test_BlankDescription_Validation() {
		checkFIeldWithMSG(descriptionError, "Description is required");
	}


	@Test(priority = 5)
	public void test_InvalidEmail_Validation() {
		checkFIeldWithMSG("@test.com", contactemail, contactemailError, "Incorrect Email format");
	}

	@Test(priority = 6)
	public void test_InvalidMobile_Validation() {
		try {
			checkFIeldWithMSG("testdsa21", cellPhoneTxt, cellPhoneTxtError, "Enter your 10-digit Mobile No");
		}catch(AssertionError aex) {
			checkFIeldWithMSG("testdsa21", cellPhoneTxt, cellPhoneTxtError, "Mobile number cannot be less than 6 digits");
		}
	}


	@Test(priority = 7)
	public void test_Form_Submission_Candidate() throws InterruptedException {
		_ContactUsDriver.get(baseUrl+"/contactus/");
		_ContactUsDriver.findElement(contactNameTxt).clear();
		_ContactUsDriver.findElement(contactNameTxt).sendKeys("Candidate "+name);
		_ContactUsDriver.findElement(contactemail).clear();
		_ContactUsDriver.findElement(contactemail).sendKeys(email_new);
		_ContactUsDriver.findElement(cellPhoneTxt).clear();
		_ContactUsDriver.findElement(cellPhoneTxt).sendKeys(mobileno);
		_ContactUsDriver.findElement(description).clear();
		_ContactUsDriver.findElement(description).sendKeys(desc);
		new Select(_ContactUsDriver.findElement(By.id("reason_select"))).selectByValue("Contact Us-Feedback");		
		fIleUploader("valid_img.jpg");
		_ContactUsDriver.findElement(By.id("id_contact_us_candidate")).click();
		_ContactUsDriver.findElement(Submit).click();
		_Utility.Thread_Sleep(2000);
		String actual = _ContactUsDriver.findElement(By.cssSelector(".cls_displaymessage")).getText();
		Assert.assertEquals(actual, "Feedback/Suggestion Submitted Successfully");

	}
	@Test(priority = 8)
	public void test_Form_Submission_Recruiter() throws InterruptedException {
		_ContactUsDriver.navigate().refresh();
		_ContactUsDriver.findElement(contactNameTxt).clear();
		_ContactUsDriver.findElement(contactNameTxt).sendKeys("Recruiter "+name);
		_ContactUsDriver.findElement(contactemail).clear();
		_ContactUsDriver.findElement(contactemail).sendKeys(email_new);
		_ContactUsDriver.findElement(cellPhoneTxt).clear();
		_ContactUsDriver.findElement(cellPhoneTxt).sendKeys(mobileno);
		_ContactUsDriver.findElement(description).clear();
		_ContactUsDriver.findElement(description).sendKeys(desc);
		new Select(_ContactUsDriver.findElement(By.id("reason_select"))).selectByValue("Report a Job");		
		fIleUploader("valid_img.jpg");
		//_ContactUsDriver.findElement(radioButton(1)).click();
		_ContactUsDriver.findElement(By.id("id_contact_us_Recruiter")).click();
		_ContactUsDriver.findElement(Submit).click();
		_Utility.Thread_Sleep(2000);
		String actual = _ContactUsDriver.findElement(By.cssSelector(".cls_displaymessage")).getText();
		Assert.assertEquals(actual, "Feedback/Suggestion Submitted Successfully");


	}



	@Test(priority = 9)
	public void test_ReportJob_Form_Submission() {
		_ContactUsDriver.navigate().refresh();
		_Utility.Thread_Sleep(1000);
		_ContactUsDriver.navigate().to(baseUrl+"/contactus/?type=reportJobPosting");
		_ContactUsDriver.findElement(officialid).clear();
		_ContactUsDriver.findElement(officialid).sendKeys(email_new);
		_ContactUsDriver.findElement(cellPhoneTxt).clear();
		_ContactUsDriver.findElement(cellPhoneTxt).sendKeys(mobileno);
		_ContactUsDriver.findElement(contactNameTxt).clear();
		_ContactUsDriver.findElement(contactNameTxt).sendKeys(name);
		_ContactUsDriver.findElement(contactCompany).clear();
		_ContactUsDriver.findElement(contactCompany).sendKeys("HT Media LTD");
		_ContactUsDriver.findElement(contactCompanyUrl).clear();
		_ContactUsDriver.findElement(contactCompanyUrl).sendKeys("https://shine.com");
		_ContactUsDriver.findElement(contRemovalReason).clear();
		_ContactUsDriver.findElement(contRemovalReason).sendKeys(name);
		_ContactUsDriver.findElement(Submit).click();
		_Utility.Thread_Sleep(1000);
		String actual = _ContactUsDriver.findElement(By.cssSelector(".cls_displaymessage")).getText();
		Assert.assertEquals(actual, "Feedback/Suggestion Submitted Successfully");

	}




	@Test(priority = 10)
	public void test_loggedin_Form_Submission_Candidate()  {
		TestBaseSetup.loggedInShine(_ContactUsDriver, email_new, pass_new);
		_Utility.Thread_Sleep(1000);
		_ContactUsDriver.navigate().to(baseUrl+"/myshine/contactus/");
		_ContactUsDriver.findElement(description).clear();
		_ContactUsDriver.findElement(description).sendKeys(desc);
		new Select(_ContactUsDriver.findElement(By.id("reason_select"))).selectByValue("Contact Us-Feedback");
		//_ContactUsDriver.findElement(radioButton(0)).click();
		_ContactUsDriver.findElement(By.id("id_contact_us_candidate")).click();
		fIleUploader("valid_img.jpg");
		_ContactUsDriver.findElement(Submit).click();
		_Utility.Thread_Sleep(1000);
		String actual = _ContactUsDriver.findElement(By.cssSelector(".cls_displaymessage")).getText();
		Assert.assertEquals(actual, "Feedback/Suggestion Submitted Successfully");

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _ContactUsDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() throws Exception {
		if(_ContactUsDriver!=null)
			_ContactUsDriver.quit();

	}

	/**
	 * 
	 * @param imgName
	 */
	public void fIleUploader(String imgName)  {
		_ContactUsDriver.findElement(resume).clear();
		_ContactUsDriver.findElement(resume).sendKeys(path+imgName);
	}



	/**
	 * Check field for assertion message defined by user
	 * @param validationMSGdiv
	 * @param message
	 */
	public void checkFIeldWithMSG(By validationMSGdiv, String message ) {
		_Utility.explicitlyWaitForElementToLoad(_ContactUsDriver, validationMSGdiv, 15);
		String actual = _ContactUsDriver.findElement(validationMSGdiv).getText();
		Assert.assertEquals(actual, message);
	}
	/**
	 * Check field for assertion message defined by user
	 * @param fieldDiv
	 * @param validationMSGdiv
	 * @param message
	 */

	public void checkFIeldWithMSG(String data, By fieldDiv, By validationMSGdiv, String message ) {
		_ContactUsDriver.findElement(fieldDiv).sendKeys(data);
		_ContactUsDriver.findElement(fieldDiv).sendKeys(Keys.TAB);
		_Utility.explicitlyWaitForElementToLoad(_ContactUsDriver, validationMSGdiv, 15);
		String actual = _ContactUsDriver.findElement(validationMSGdiv).getText();
		Assert.assertEquals(actual, message);
	}




}
