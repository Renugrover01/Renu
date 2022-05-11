package com.shine.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import org.testng.annotations.AfterMethod;
import org.testng.ITestResult;

public class Test_ProfileDeleteInfo extends TestBaseSetup{
	WebDriver _ProfileDeleteDriver;
	WebDriverWait _Wait;
	String TestLink_testCase="";

	By myProfileLink	= By.linkText("My Profile");

	By delJobLink		= By.id("id_1_jobs_cross");
	By certHeadDiv		= By.id("id_certifications");
	By delresume        = By.id("id_cpSubmit");
	By deledu		    = By.id("id_1_education_delete");
	By delexp		    = By.id("id_1_job_delete");
	By delcertificate   = By.id("id_1_certification_delete");
	By delskill         = By.id("id_1_skill_delete");
	By eduHeadDiv		= By.id("id_educationdetails");
	By delEduLink		= By.id("id_1_education_cross");
	By delCertiLink		= By.id("id_1_certification_cross");
	By delSkillLink		= By.id("id_1_skill_cross");
	By skillLink		= By.cssSelector("#id_skills");
	By certiLink        = By.cssSelector("#id_certifications");
	By expHeadDiv 		= By.xpath("(//div[@id='experience'])");
	By deleteResumeLink = By.xpath("//a[@class='cls_delete']/span");

	@BeforeClass
	public void TestSetup() {
		_ProfileDeleteDriver = getDriver(_ProfileDeleteDriver);
		APP_LOGS.debug("Executing: Test_ProfileDeleteInfo");
		loggedInShine(_ProfileDeleteDriver, email_new, pass_new);
		_Utility.Thread_Sleep(2000);
		_Utility.clickOnNotification(_ProfileDeleteDriver);
		_Utility.openMenuLink(Test_MyProfile.userAccMenuLink, myProfileLink, _ProfileDeleteDriver);
		_Wait = new WebDriverWait(_ProfileDeleteDriver, 10);
	}


	@Test (priority=1)
	public void test_DeleteExp () {
		TestLink_testCase = "Validate deletion of experience";
		WebElement deleteexplink = _ProfileDeleteDriver.findElement(delJobLink);
		_Utility.scrollTOElement(expHeadDiv, _ProfileDeleteDriver);
		deleteexplink.click();
		_ProfileDeleteDriver.switchTo().parentFrame();
		_Wait.until(ExpectedConditions.elementToBeClickable(delexp));
		_ProfileDeleteDriver.findElement(delexp).click();
	}

	@Test (priority=2)
	public void test_DeleteEdu () {
		TestLink_testCase = "Validate deletion of experience";
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(eduHeadDiv, _ProfileDeleteDriver);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(delEduLink));
		_ProfileDeleteDriver.findElement(delEduLink).click();
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(deledu));
		_ProfileDeleteDriver.findElement(deledu).click();
	}
	
	@Test (priority=3)
	public void test_DeleteSkill() {
		TestLink_testCase = "Validate deletion of skill";
		_Utility.Thread_Sleep(2000);
		((JavascriptExecutor) _ProfileDeleteDriver).executeScript("arguments[0].scrollIntoView(true);",
		_ProfileDeleteDriver.findElement(skillLink));
		_ProfileDeleteDriver.findElement(delSkillLink).click();
		_Utility.Thread_Sleep(3000);
		_Wait.until(ExpectedConditions.elementToBeClickable(delskill));
		_ProfileDeleteDriver.findElement(delskill).click();

	}

	@Test (priority=4)
	public void test_DeleteCertification () {
		TestLink_testCase = "Validate deletion of certification";
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(certHeadDiv, _ProfileDeleteDriver);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(delCertiLink));
		_ProfileDeleteDriver.findElement(delCertiLink).click();
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(delcertificate));
		_ProfileDeleteDriver.findElement(delcertificate).click();
		_Utility.Thread_Sleep(1000);

	}


	/*
	 * @Test (priority=5) public void test_DeleteResume () { TestLink_testCase =
	 * "Validate deletion of resume"; _Utility.Thread_Sleep(5000);
	 * _Utility.scrollTOElement(deleteResumeLink, _ProfileDeleteDriver);
	 * _ProfileDeleteDriver.findElement(deleteResumeLink).click();
	 * _Utility.Thread_Sleep(3000);
	 * _Wait.until(ExpectedConditions.elementToBeClickable(delresume));
	 * _ProfileDeleteDriver.findElement(delresume).click(); }
	 */


	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_ProfileDeleteDriver!=null)
			_ProfileDeleteDriver.quit();

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _ProfileDeleteDriver);
	}




}