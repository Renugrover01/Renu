package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.ExcelReader;

public class Test_SearchMapping extends TestBaseSetup{


	WebDriver _SearchMappingDriver;
	WebDriverWait wait;
	int flag =0 ;


	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the Search test");
		_SearchMappingDriver = getDriver(_SearchMappingDriver);
		wait = new WebDriverWait(_SearchMappingDriver, 15);
	}

	@Test(priority = 0, dataProvider="searchdata")
	public void verify_search_url(String skill, String job_title, String company, String location, String ind, String fa, String min_exp, String expected_url_pattern, String expected_title, String expected_meta_desc) {
		search_and_verify_jsrp_url(false, skill, job_title, company, location, ind, fa, min_exp, expected_url_pattern);
		assertEquals(_SearchMappingDriver.getCurrentUrl(), baseUrl+"/job-search/"+expected_url_pattern);
		String actual_title = _SearchMappingDriver.getTitle();

		//get current date time with Date()
		Date date = new Date();
		SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
		String finalDate = dateFormatYear.format(date);
		if(actual_title.contains(finalDate)) {
			int index = actual_title.indexOf(finalDate);
			actual_title = actual_title.replaceAll("\\d","").replaceAll(" +", " ").trim();
			StringBuilder str = new StringBuilder(actual_title);
		   // insert character value at offset {Index}
		    str.insert(index, finalDate);
		    actual_title = str.toString().replaceAll(" +", " ").trim();
		}
		else {
			actual_title = actual_title.replaceAll("\\d","").replaceAll(" +", " ").trim();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
		expected_title = expected_title.replace("(mmm yyyy)", "("+dateFormat.format(date)+")");
		assertEquals(actual_title, expected_title);
		
		String actual_meta_desc = _SearchMappingDriver.findElement(By.cssSelector("[name='description']")).getAttribute("content");
		String actual_modified_meta_desc = actual_meta_desc.replaceAll("[^A-Za-z ]","");
		String expected_modified_meta_desc = expected_meta_desc.replaceAll("[^A-Za-z ]","");
		assertEquals(actual_modified_meta_desc, expected_modified_meta_desc);			

	}

	//@Test(priority = 1)
	public void login_in_shine() {
		loggedInShine(_SearchMappingDriver, email_new, pass_new);
		_Utility.Thread_Sleep(2000);
	}

	//@Test(priority = 2, dataProvider="searchdata", dependsOnMethods= {"login_in_shine"})
	public void verify_search_url_loggedin_user(String skill, String job_title, String company, String location, String ind, String fa, String min_exp, String expected_url_pattern, String expected_title) {
		search_and_verify_jsrp_url(true, skill, job_title, company, location, ind, fa, min_exp, expected_url_pattern);
		assertEquals(_SearchMappingDriver.getCurrentUrl(), baseUrl+"/myshine/job-search/"+expected_url_pattern);
	}

	@DataProvider(name="searchdata")
	public Object[][] search_data() {
		ExcelReader excelReader = new ExcelReader();
		Object[][] arrayObject = excelReader.getExcelData(userDirectory+"/src/test/resources/data/search_data_for_mapping.xls","Sheet1");
		return arrayObject;
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _SearchMappingDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_SearchMappingDriver!=null)
			_SearchMappingDriver.quit();

	}


	public void search_and_verify_jsrp_url(boolean isLoggedIn, String skill, String job_title, String company, String location, String ind, String fa, String min_exp, String expected_url_pattern) {
		_SearchMappingDriver.navigate().to(baseUrl);
		_Utility.Thread_Sleep(1000);
		if(isLoggedIn) {
			_SearchMappingDriver.findElement(By.id("id_searchBase")).click();;
		}
		_Utility.Thread_Sleep(500);
		_SearchMappingDriver.findElement(By.id("id_q")).clear();
		_SearchMappingDriver.findElement(By.id("id_loc")).clear();
		int flag = 0;
		if(skill.isEmpty()&&job_title.isEmpty()&&company.isEmpty()&&min_exp.isEmpty()&&ind.isEmpty()&&fa.isEmpty())
		{
			if(!location.isEmpty()){
				_SearchMappingDriver.findElement(By.id("id_q")).clear();
				_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(location);
				_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
				flag = 1;
			}
		}
		if(!skill.isEmpty()){
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(skill+", ");
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
		}
		if(!job_title.isEmpty()){
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(job_title+", ");
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
		}
		if(!company.isEmpty()){
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(company+" ");
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
		}

		if(!location.isEmpty()&&flag==0){
			_SearchMappingDriver.findElement(By.id("id_loc")).clear();
			_SearchMappingDriver.findElement(By.id("id_loc")).sendKeys(location);
			_SearchMappingDriver.findElement(By.id("id_loc")).sendKeys(Keys.TAB);
		}
		if(!min_exp.isEmpty()){
			_Utility.Thread_Sleep(3000);
			_SearchMappingDriver.findElement(By.id("id_q")).sendKeys("  fresher");
			//new Select(_SearchMappingDriver.findElement(By.id("id_minexp"))).selectByVisibleText(min_exp.trim());
		}

		if(!ind.isEmpty()||!fa.isEmpty()||!min_exp.isEmpty()){
			_Utility.Thread_Sleep(1000);
			WebElement adv_Search = _SearchMappingDriver.findElement(By.linkText("Advanced search"));
			JavascriptExecutor js = (JavascriptExecutor)_SearchMappingDriver;
			js.executeScript("arguments[0].click();", adv_Search);
		}
		if(!ind.isEmpty()){
			_Utility.Thread_Sleep(3000);
			new Select(_SearchMappingDriver.findElement(By.id("id_ind"))).selectByVisibleText(ind);
		}
		if(!fa.isEmpty()){
			_Utility.Thread_Sleep(3000);
			new Select(_SearchMappingDriver.findElement(By.id("id_area"))).selectByVisibleText(fa);
		}
		_SearchMappingDriver.findElement(By.cssSelector("[name='simplesearch']")).click();
		_Utility.Thread_Sleep(3000);
		_SearchMappingDriver.get(_SearchMappingDriver.getCurrentUrl());
		_Utility.Thread_Sleep(3000);
	}


}
