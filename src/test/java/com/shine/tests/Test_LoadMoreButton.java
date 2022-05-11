package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_LoadMoreButton extends TestBaseSetup{
	WebDriver _LoadMoredriver;

	@BeforeClass
	public void TestSetup() {
		_LoadMoredriver = getDriver(_LoadMoredriver);
		OpenBaseUrl(_LoadMoredriver);
	}


	@Test (priority=0)
	public void test_load_more_button() {
		_LoadMoredriver.get(baseUrl+"/job-search/java-jobs");
		_Utility.Thread_Sleep(3000);
		_Utility.scrollTOElement(By.xpath("//*[@class='submit submit1 pagination_button cls_pagination']"), _LoadMoredriver);
		_LoadMoredriver.findElement(By.xpath("//*[@class='submit submit1 pagination_button cls_pagination']")).click();
		_Utility.Thread_Sleep(3000);
		List<WebElement> job_list = _LoadMoredriver.findElements(By.cssSelector("#id_results .apply"));
		assertEquals(job_list.size(), 20);
	}

	@Test (priority=1)
	public void test_match_job_load_more_button() {
		loggedInShine(_LoadMoredriver, email_new, pass_new);
		_Utility.Thread_Sleep(3000);
		_Utility.scrollTOElement(By.id("id_loader_loadmore"), _LoadMoredriver);
		_LoadMoredriver.findElement(By.id("id_loader_loadmore")).click();
		_Utility.Thread_Sleep(5000);
		List<WebElement> job_list = _LoadMoredriver.findElements(By.cssSelector(".apply a"));
		assertEquals(job_list.size(), 40);
	}

	@Test (priority=2)
	public void test_match_job_load_apply_button() {
		_Utility.Thread_Sleep(3000);
		List<WebElement> job_list = _LoadMoredriver.findElements(By.cssSelector(".apply a"));
		job_list.get(25).click();
		Test_JobApply.assertMessage(_LoadMoredriver);
	}


	/*
	 * @Test (priority=3) public void test_jsrp_load_more_apply_button() {
	 * _Utility.Thread_Sleep(2000);
	 * _LoadMoredriver.get(baseUrl+"/job-search/java-jobs");
	 * _Utility.Thread_Sleep(2000);
	 * _Utility.scrollTOElement(By.id("id_loader_loadmore"), _LoadMoredriver);
	 * _LoadMoredriver.findElement(By.id("id_loader_loadmore")).click();
	 * _Utility.Thread_Sleep(5000); List<WebElement> job_list =
	 * _LoadMoredriver.findElements(By.cssSelector("#id_results .apply"));
	 * job_list.get(25).click(); Test_JobApply.assertMessage(_LoadMoredriver); }
	 */



	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_LoadMoredriver!=null)
			_LoadMoredriver.quit();

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _LoadMoredriver);
	}

	


}
