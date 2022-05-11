package com.shine.tests;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.ExcelReader;

public class Test_SearchURL extends TestBaseSetup{

	private WebDriver _SearchDriver;
	@SuppressWarnings("unused")
	private WebDriverWait wait;
	int flag =0 ;


	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the Search test");
		_SearchDriver = getDriver(_SearchDriver);
		wait = new WebDriverWait(_SearchDriver, 15);
		_SearchDriver.navigate().to(baseUrl);
		_Utility.clickOnNotification(_SearchDriver);
	}


	@Test (priority =1, dataProvider="searchdata")
	public void test_url(String Skill, String JT, String Comp, String Ind,String FA, String Loc, String expected)
	{
		APP_LOGS.info("===="+Skill+"===="+JT+"===="+Comp+"===="+Ind+"===="+FA+"===="+Loc+"===="+expected+"====");
		String expectedUrl = getExpectedUrl(Skill, JT, Comp, Ind, FA, Loc, expected);
		APP_LOGS.info("Expected URL: "+expectedUrl);
		String actualUrl = getActualUrl(Skill, JT, Comp, Ind, FA, Loc, expected);
		APP_LOGS.info("POST URL: "+actualUrl);
		_SearchDriver.navigate().to(actualUrl);
		_Utility.Thread_Sleep(4000);
		APP_LOGS.info("Actual GET URL: "+_SearchDriver.getCurrentUrl());
		if(!_SearchDriver.getCurrentUrl().toLowerCase().contains("akamai"))
			Assert.assertEquals(_SearchDriver.getCurrentUrl(), expectedUrl.toLowerCase());
		else APP_LOGS.info("Search URL relaxed: Test explicitly passed");

	}


	
	
	private String getActualUrl(String skill, String jT, String comp, String ind, String fA, String loc, String expected) {
		_SearchDriver.navigate().to(baseUrl);
		//_SearchDriver.findElement(By.id("id_searchBase")).click();
		_SearchDriver.findElement(By.id("id_q")).clear();
		_SearchDriver.findElement(By.id("id_loc")).clear();
		if(!skill.equals("")){
			_SearchDriver.findElement(By.id("id_q")).sendKeys(skill+", ");
			_SearchDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
		}
		if(!jT.equals("")){
			_SearchDriver.findElement(By.id("id_q")).sendKeys(jT+", ");
			_SearchDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
		}
		if(!comp.equals("")){
			_SearchDriver.findElement(By.id("id_q")).sendKeys(comp+" ");
			_SearchDriver.findElement(By.id("id_q")).sendKeys(Keys.TAB);
		}

		if(!loc.equals("")){
			_SearchDriver.findElement(By.id("id_loc")).clear();
			_SearchDriver.findElement(By.id("id_loc")).sendKeys(loc);
			_SearchDriver.findElement(By.id("id_loc")).sendKeys(Keys.TAB);
		}
		System.out.println(fA);
		if(!ind.equals("")||!fA.equals("")){
			_Utility.Thread_Sleep(1000);
			_SearchDriver.findElement(By.linkText("Advanced search")).click();
		}
		if(!ind.equals(""))
		{
			_Utility.Thread_Sleep(3000);
			new Select(_SearchDriver.findElement(By.id("id_ind"))).selectByVisibleText(ind);
		}
		if(!fA.equals(""))
		{
			_Utility.Thread_Sleep(3000);
			new Select(_SearchDriver.findElement(By.id("id_area"))).selectByVisibleText(fA);
		}

		_SearchDriver.findElement(By.cssSelector("[name='simplesearch']")).click();

		_Utility.Thread_Sleep(3000);
		return _SearchDriver.getCurrentUrl();
	}


	public String getExpectedUrl(String Skill, String JT, String Comp, String Ind,String FA, String Loc, String expected){
		if(Ind!=null||FA!=null)
		{
			flag = 1;
		}

		if(expected.contains("Skill"))
		{
			expected = expected.replace("Skill", Skill);
		}
		if(expected.contains("JT"))
		{
			expected = expected.replace("JT", JT);
		}
		if(expected.contains("Comp"))
		{
			expected = expected.replace("Comp", Comp);
		}
		if(expected.contains("FA"))
		{
			expected = expected.replace("FA", FA);
		}
		if(expected.contains("Ind"))
		{
			Ind = Ind+"-Industry";
			expected = expected.replace("Ind", Ind);
		}
		if(expected.contains("Loc"))
		{
			expected = expected.replace("Loc", Loc);
		}

		String Expected_url_bot = baseUrl+"/job-search/"+expected.replaceAll("/", "").replace(" ", "-").replace("---", "-").replaceAll("--", "-");
		APP_LOGS.info("URL Creted using logic : "+Expected_url_bot);

		return Expected_url_bot;
	}

	@DataProvider(name="searchdata")
	public Object[][] loginData() 
	{
		ExcelReader excelReader = new ExcelReader();
		Object[][] arrayObject = excelReader.getExcelData(userDirectory+"/src/test/resources/data/SearchResult.xls","Sheet1");
		return arrayObject;
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _SearchDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser()  {
		if(_SearchDriver!=null)
			_SearchDriver.quit();

	}

}
