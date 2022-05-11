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

public class Test_RelatedLinks extends TestBaseSetup {

	private WebDriver _RelatedLinksDriver;
	WebDriverWait _Wait;
	String keyword = "Java";

	By relatedllinkTitle		= By.id("id_relatedlink");
	By skill_related_link		= By.cssSelector("#id_rel_1 tr td");
	By location_related_link	= By.cssSelector("#id_rel_2 tr td");
	By job_title_related_link	= By.cssSelector("#id_rel_3 tr td");

	By loggedin_skill_related_link		= By.cssSelector("#id_table_relatedLinkss tr:nth-child(1) .chd");
	By loggedin_location_related_link	= By.cssSelector("#id_table_relatedLinkss tr:nth-child(2) .chd");
	By loggedin_job_title_related_link	= By.cssSelector("#id_table_relatedLinkss tr:nth-child(3) .chd");

	@BeforeClass
	public void TestSetup() {
		_RelatedLinksDriver = getDriver(_RelatedLinksDriver);
		OpenBaseUrl(_RelatedLinksDriver);
		_Wait = new WebDriverWait(_RelatedLinksDriver, 15);
	}


	@Test(priority=0)
	public void open_related_link() {
		Test_Search.simpleSearch(keyword, _RelatedLinksDriver);
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(relatedllinkTitle, _RelatedLinksDriver);
		String actual_related_link_title = _RelatedLinksDriver.findElement(relatedllinkTitle).getText().trim();
		Assert.assertEquals(actual_related_link_title, "Searches related to "+keyword+" Jobs");
	}



	@Test(priority=1, dependsOnMethods= {"open_related_link"})
	public void verify_realted_link_skills() {
		List<WebElement> skill_list = _RelatedLinksDriver.findElements(skill_related_link);
		SoftAssert _Assert = new SoftAssert();
		_Assert.assertEquals(skill_list.get(0).getText(), "Core Java");
		_Assert.assertEquals(skill_list.get(1).getText(), "Advanced Java");
		_Assert.assertEquals(skill_list.get(2).getText(), "Java Swing");
		_Assert.assertEquals(skill_list.get(3).getText(), "Java Flex");
		_Assert.assertEquals(skill_list.get(4).getText(), "Struts");
		_Assert.assertAll();
	}

	@Test(priority=2, dependsOnMethods= {"open_related_link"})
	public void verify_realted_link_locations() {
		List<WebElement> location_list = _RelatedLinksDriver.findElements(location_related_link);
		SoftAssert _Assert = new SoftAssert();
		_Assert.assertEquals(location_list.get(0).getText(), "Bangalore");
		_Assert.assertEquals(location_list.get(1).getText(), "Hyderabad");
		_Assert.assertEquals(location_list.get(2).getText(), "Delhi");
		_Assert.assertEquals(location_list.get(3).getText(), "Chennai");
		_Assert.assertEquals(location_list.get(4).getText(), "Pune");
		_Assert.assertAll();
	}


	
	  @Test(priority=3, dependsOnMethods= {"open_related_link"}) public void
	   verify_realted_link_job_titles() { List<WebElement> job_title_list =
	  _RelatedLinksDriver.findElements(job_title_related_link);
	   SoftAssert _Assert = new SoftAssert(); 
	  _Assert.assertEquals(job_title_list.get(0).getText(), "Java Developer");
	  _Assert.assertEquals(job_title_list.get(1).getText(), "Java Consultant"); 
	  _Assert.assertEquals(job_title_list.get(2).getText(), "Java Programmer");
	  _Assert.assertEquals(job_title_list.get(3).getText(), "Java Trainee");
	  _Assert.assertEquals(job_title_list.get(4).getText(), "Java J2Ee Developer");
	  _Assert.assertAll(); }
	 

	/*
	 * @Test(priority=4) public void open_loggedin_related_link() {
	 * loggedInShine(_RelatedLinksDriver, email_new, pass_new);
	 * _Utility.Thread_Sleep(1000); Test_Search.simpleSearch(keyword,
	 * _RelatedLinksDriver); _Utility.Thread_Sleep(2000);
	 * _Utility.scrollTOElement(relatedllinkTitle, _RelatedLinksDriver); String
	 * actual_related_link_title =
	 * _RelatedLinksDriver.findElement(relatedllinkTitle).getText().trim();
	 * Assert.assertEquals(actual_related_link_title,
	 * "Searches related to "+keyword+" Jobs"); }
	 * 
	 * 
	 * @Test(priority=5) public void verify_loggedin_realted_link_skills() {
	 * List<WebElement> skill_list =
	 * _RelatedLinksDriver.findElements(loggedin_skill_related_link); SoftAssert
	 * _Assert = new SoftAssert(); _Assert.assertEquals(skill_list.get(0).getText(),
	 * "Core Java"); _Assert.assertEquals(skill_list.get(1).getText(),
	 * "Advanced Java"); _Assert.assertEquals(skill_list.get(2).getText(),
	 * "Java Swing"); _Assert.assertEquals(skill_list.get(3).getText(),
	 * "Java Flex"); _Assert.assertEquals(skill_list.get(4).getText(), "Struts");
	 * _Assert.assertAll(); }
	 */

	/*
	 * @Test(priority=6, dependsOnMethods= {"verify_loggedin_realted_link_skills"})
	 * public void verify_loggedin_realted_link_locations() { List<WebElement>
	 * location_list =
	 * _RelatedLinksDriver.findElements(loggedin_location_related_link); SoftAssert
	 * _Assert = new SoftAssert();
	 * _Assert.assertEquals(location_list.get(0).getText(), "Bangalore");
	 * _Assert.assertEquals(location_list.get(1).getText(), "Hyderabad");
	 * _Assert.assertEquals(location_list.get(2).getText(), "Delhi");
	 * _Assert.assertEquals(location_list.get(3).getText(), "Chennai");
	 * _Assert.assertEquals(location_list.get(4).getText(), "Pune");
	 * _Assert.assertAll(); }
	 * 
	 * 
	 * 
	 * @Test(priority=7, dependsOnMethods= {"verify_loggedin_realted_link_skills"})
	 * public void verify_loggedin_realted_link_job_titles() { List<WebElement>
	 * job_title_list =
	 * _RelatedLinksDriver.findElements(loggedin_job_title_related_link); SoftAssert
	 * _Assert = new SoftAssert();
	 * _Assert.assertEquals(job_title_list.get(0).getText(), "Java Developer");
	 * _Assert.assertEquals(job_title_list.get(1).getText(), "Java Consultant");
	 * _Assert.assertEquals(job_title_list.get(2).getText(), "Java Programmer");
	 * _Assert.assertEquals(job_title_list.get(3).getText(), "Java Trainee");
	 * _Assert.assertEquals(job_title_list.get(4).getText(), "Java J2Ee Developer");
	 * _Assert.assertAll(); }
	 */
	 





	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _RelatedLinksDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_RelatedLinksDriver!=null)
			_RelatedLinksDriver.quit();

	}

}
