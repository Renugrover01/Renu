package com.shine.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonUtils;

public class Test_ShortlistJobs extends TestBaseSetup{

	private WebDriver _ShortlistJobDriver;
	private WebDriverWait wait;
	CommonUtils _CommonUtils;	
	private String jobTitle ="";

	By saveBtn          = By.id("id_star");
	By jobForuLink      = By.linkText("Jobs for You");
	By shortlistJobLink = By.partialLinkText("Saved Jobs");
	By saveJobBtn       = By.linkText("Saved");
	By shortlistJobList = By.cssSelector("[itemprop='title']");
	By jobTitleTxt      = By.xpath("//span[@class='cls_jobtitle']");



	@BeforeClass	
	public void TestSetup() {
		_ShortlistJobDriver = getDriver(_ShortlistJobDriver);
		loggedInShine(_ShortlistJobDriver, email_main, pass_new);
		_Utility.clickOnNotification(_ShortlistJobDriver);
		wait = new WebDriverWait(_ShortlistJobDriver, 15);
	}

	@Test(priority=0)
	public void test_clickon_Save_btn(){
		Test_JobApply.openjdpage(_ShortlistJobDriver);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jobTitleTxt));
		jobTitle = _ShortlistJobDriver.findElement(jobTitleTxt).getText();
		APP_LOGS.debug(jobTitle);
		_Utility.Thread_Sleep(3000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(saveBtn));
		wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
		_ShortlistJobDriver.findElement(saveBtn).click();
		_Utility.Thread_Sleep(4000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(saveBtn));
		String actual = _ShortlistJobDriver.findElement(saveBtn).getText().trim();
		APP_LOGS.debug(actual+" - should be saved not save: \n "+_ShortlistJobDriver.getCurrentUrl()+" \n"+email_main);
		Assert.assertEquals(actual, "Saved");
	}	


	@Test(priority=1)
	public void open_ShortlistJobs(){
		_Utility.Thread_Sleep(3000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jobForuLink));
		_ShortlistJobDriver.findElement(jobForuLink).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(saveJobBtn));
		_ShortlistJobDriver.findElement(saveJobBtn).click();
		String expectedURL = baseUrl+"/myshine/shortlisted-jobs/";
        String actualURL   = _ShortlistJobDriver.getCurrentUrl();
        APP_LOGS.debug("Matching: "+actualURL+" :with: "+expectedURL);
        Assert.assertEquals(actualURL, expectedURL);
	}

	@Test(priority=2)
	public void verify_isJobSaved(){
		APP_LOGS.debug("verify_isJobSaved: "+email_main);
		wait.until(ExpectedConditions.visibilityOfElementLocated(shortlistJobList));
		List<WebElement> jobList = _ShortlistJobDriver.findElements(shortlistJobList);
		APP_LOGS.debug("Save job count: "+jobList.size());
		Assert.assertTrue(jobList.size() > 0, "Save job found is: "+jobList.size());
		String actualJobTitle = jobList.get(0).getText().trim();
		APP_LOGS.debug("jOB TITLE IS: "+actualJobTitle);
		Assert.assertEquals(actualJobTitle, jobTitle);

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _ShortlistJobDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_ShortlistJobDriver!=null)
			_ShortlistJobDriver.quit();

	}


}
