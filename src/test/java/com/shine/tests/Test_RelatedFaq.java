package com.shine.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import org.testng.asserts.SoftAssert;
import com.shine.tests.Test_Search;

public class Test_RelatedFaq extends TestBaseSetup {
	
	private WebDriver _RelatedFaqDriver;
	WebDriverWait _Wait;
	String keyword = "Java";
	
	By faq_title		= By.id("id_qnalink");
	By faq_quest       = By.className("question");
	
	@BeforeClass
	public void TestSetup() {
		_RelatedFaqDriver = getDriver(_RelatedFaqDriver);
		OpenBaseUrl(_RelatedFaqDriver);
		_Wait = new WebDriverWait(_RelatedFaqDriver, 15);
	}


	@Test(priority=0)
	public void open_related_faq() {
		Test_Search.simpleSearch(keyword, _RelatedFaqDriver);
		_Utility.Thread_Sleep(1000);
		_Utility.scrollTOElement(faq_title, _RelatedFaqDriver);
		String actual_related_faq_title = _RelatedFaqDriver.findElement(faq_title).getText().trim();
		Assert.assertEquals(actual_related_faq_title, ""+keyword+" Job Facts");
	}



	@Test(priority=1, dependsOnMethods= {"open_related_faq"})
	public void verify_related_faq_quest() {		
		List<WebElement> quest_list = _RelatedFaqDriver.findElements(faq_quest);
		SoftAssert _Assert = new SoftAssert();
		_Assert.assertEquals(quest_list.get(0).getText(), "What are the skils required to be a java developer?");
		_Assert.assertEquals(quest_list.get(1).getText(), "What are the responsibilities of a java developer?");
		_Assert.assertEquals(quest_list.get(2).getText(), "What is the salary of a java developer?");
		_Assert.assertAll();
	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _RelatedFaqDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_RelatedFaqDriver!=null)
			_RelatedFaqDriver.quit();

	}

	

}
