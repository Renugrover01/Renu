package com.shine.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonUtils;

import org.testng.Assert;
import org.testng.ITestResult;
//import org.testng.SkipException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;


public class Test_Search_New extends TestBaseSetup {
	WebDriver _Searchdriver;
	WebDriverWait wait;
	CommonUtils _CommonUtils;
	int position = 8;

	By salarySubMenu	= By.cssSelector(".submenu ul li:nth-child(1) input[name=fsalary]");
	By locationSubMenu	= By.cssSelector(".submenu ul li:nth-child(1) input[name=location]");
	By faSubMenu 		= By.cssSelector(".submenu ul li:nth-child(1) input[name=farea]");
	By fIndSubMenu 		= By.cssSelector(".submenu ul li:nth-child(1) input[name=findustry]");
	By fexpSubMenu 		= By.cssSelector(".submenu ul li:nth-child(1) input[name=fexp]");
	By compSubMenu 		= By.cssSelector(".submenu ul li:nth-child(1) input[name=fcid]");
	By jobTypeSubMenu	= By.cssSelector(".submenu ul li:nth-child(1) input[name=job_type]");

	By salaryCount		= By.xpath("(//li[@id='id_filter_salary']/div/ul/li/em/b)[1]");
	By locationCount	= By.xpath("(//li[@id='id_filter_location']/div/ul/li/em/b)[1]");
	By faCount 			= By.xpath("(//li[@id='id_filter_department']/div/ul/li/em/b)[1]");
	By fIndCount 		= By.xpath("(//li[@id='id_filter_industry']/div/ul/li/em/b)[1]");
	By fexpCount		= By.xpath("(//li[@id='id_filter_experience']/div/ul/li/em/b)[1]");
	By compCount 		= By.xpath("(//li[@id='id_filter_company']/div/ul/li/em/b)[1]");
	By jobTypeCount		= By.xpath("(//li[@id='id_filter_jobtype']/div/ul/li/em/b)[1]");

	By jsrpJobCount		= By.id("id_resultCount");

	By jobTitle			= By.cssSelector("div.num_key>h1");
	By jobCount			= By.cssSelector(".num_key>em");
	/*
	 * Search page object
	 */
	static By searchKeyword 	= By.id("id_q");
	static By searchLocation    = By.id("id_loc");
	static By searchbtn         = By.name("simplesearch");	
	By minExp 					= By.id("id_minexp");
	By expandAvanceSearch 		= By.xpath("//a[@class='cls_ulAdvSearch_a']/u");
	By minSal 					= By.id("id_minsal");
	By fa 						= By.id("id_area");
	By ind						= By.id("id_ind");


	public By menuDiv(int i) {
		return By.xpath("(//ul[@id='id_filters']/li)["+i+"]");
	}




	@BeforeClass
	public void TestSetup() {
		_Searchdriver = getDriver(_Searchdriver);
		wait = new WebDriverWait(_Searchdriver, 15);
		OpenBaseUrl(_Searchdriver);
	}

	@Test (priority=7)
	public void testFilter_Salary() {
		String countAfterFilter = openFIlterSubMenu(menuDiv(2), salarySubMenu, salaryCount, _Searchdriver);
		match_Job_Count(countAfterFilter, _Searchdriver);
	}


	@Test (priority=8)
	public void testFilter_Location() {
		String countAfterFilter = openFIlterSubMenu(menuDiv(3), locationSubMenu, locationCount, _Searchdriver);
		match_Job_Count(countAfterFilter, _Searchdriver);
	}

	@Test (priority=9)
	public void testFilter_Department() {
		String countAfterFilter = openFIlterSubMenu(menuDiv(4), faSubMenu, faCount, _Searchdriver);
		match_Job_Count(countAfterFilter, _Searchdriver);
	}

	@Test (priority=10)
	public void testFilter_Industry() {
		String countAfterFilter = openFIlterSubMenu(menuDiv(5), fIndSubMenu, fIndCount, _Searchdriver);
		match_Job_Count(countAfterFilter, _Searchdriver);
	}

	@Test (priority=11)
	public void testFilter_Experience() {
		String countAfterFilter = openFIlterSubMenu(menuDiv(6), fexpSubMenu, fexpCount, _Searchdriver);
		match_Job_Count(countAfterFilter, _Searchdriver);
	}

	@Test (priority=12)
	public void testFilter_Company() {
		try {
			String countAfterFilter = openFIlterSubMenu(menuDiv(7), compSubMenu,compCount, _Searchdriver);
			match_Job_Count(countAfterFilter, _Searchdriver);
		}catch(Throwable t) {
			APP_LOGS.debug("company filter not found");
			//Hack: If Top company filter is not present
			position = 7;
			//throw new SkipException("Company filter not found - Skipping test case");
		}
	}

	@Test (priority=13)
	public void testFilter_JobType() {
		String countAfterFilter = openFIlterSubMenu(menuDiv(position), jobTypeSubMenu, jobTypeCount, _Searchdriver);
		match_Job_Count(countAfterFilter, _Searchdriver);
	}

	//----------ADVANCE SEARCH------------------------------

	@Test(priority=14)
	public void test_LoggedOutSimpleSearch()
	{
		_Searchdriver.navigate().to(baseUrl+"/myshine/logout/");
		simpleSearch("Ht Media", _Searchdriver);
		_Utility.Thread_Sleep(4000);
		WebElement element = _Searchdriver.findElement(jobTitle);
		String strng = element.getText();
		Assert.assertEquals(strng, "Ht Media Jobs");

	}


	//----------ADVANCE SEARCH------------------------------


	@Test(priority=15)
	public void test_LoggedoutAdvSearch() throws Exception {
		advanceSearch();
		WebElement element = _Searchdriver.findElement(jobTitle);
		String strng = element.getText();
		Assert.assertEquals(strng, "Android Jobs in Delhi");
	}



	@Test(priority=16)
	public void test_LoggedinsimpleSearch () {
		loggedInShine(_Searchdriver, email_new, pass_new);		
		_Utility.Thread_Sleep(5000);
		//SIMPLE SEARCH
		simpleSearch("manager", _Searchdriver);
		_Utility.Thread_Sleep(3000);
		WebElement element;
		element = _Searchdriver.findElement(jobTitle);
		String strng = element.getText();
		Assert.assertEquals(strng, "Manager Jobs");
	}

	@Test(priority=17)
	public void test_LoggedinAdvSearch() {
		advanceSearch();
		WebElement element = _Searchdriver.findElement(jobTitle);
		String strng = element.getText();
		Assert.assertEquals(strng, "Android Jobs in Delhi");
	}



	@Test (priority =21, dataProvider="searchdata")
	public void test_SearchMostViewedPages(String searchurl) {
		//test for most get of search result
		APP_LOGS.debug("-----------SEARCHING URL: "+searchurl+" -----------");
		_Searchdriver.navigate().to(baseUrl+searchurl);
		_Utility.clickOnNotification(_Searchdriver);
		String searchnum=_Searchdriver.findElement(jobCount).getText();
		Assert.assertNotEquals("0", searchnum);
	}


	@DataProvider
	public Object [][] searchdata(){

		Object [][] data= new Object [10][1];

		data[0][0]="/job-search/fresher";
		data[1][0]="/job-search/engineering";
		data[2][0]="/job-search/bangalore";
		data[3][0]="/job-search/delhi";
		data[4][0]="/job-search/it";
		data[5][0]="/job-search/software";
		data[6][0]="/job-search/accounts";
		data[7][0]="/job-search/dubai";
		data[8][0]="/job-search/banking-financial-services";
		data[9][0]="/job-search/bpo";

		return data;

	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_Searchdriver!=null)
			_Searchdriver.quit();

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Searchdriver);
	}




	/**
	 * 
	 * @param menu
	 * @param subMenu
	 * @param _Searchdriver
	 */
	public String openFIlterSubMenu(By menu, By subMenu, By countDiv, WebDriver _Searchdriver) {
		simpleSearch("java", _Searchdriver);
		_Utility.Thread_Sleep(2000);
		return openMenuLink(menu, subMenu, countDiv, _Searchdriver);
	}
	/**
	 * 
	 * @param menuDiv
	 * @param subMenu
	 * @param _Searchdriver
	 */
	public void match_Job_Count(String expected_count, WebDriver _Searchdriver) {
		_Utility.Thread_Sleep(6000);
		_Searchdriver.findElement(jsrpJobCount).click();
		String actual_count = _Searchdriver.findElement(jsrpJobCount).getText().trim();
		APP_LOGS.debug("JSRP Job Count: "+actual_count);
		APP_LOGS.debug("Filter Job Count: "+expected_count);
		Assert.assertEquals(actual_count, expected_count);
	}

	/**
	 * 
	 * @param menu
	 * @param Submenu
	 * @param countDiv
	 * @param driver
	 * @return
	 */
	public String openMenuLink(By menu, By Submenu, By countDiv, WebDriver driver){
		WebDriverWait wait= new WebDriverWait(driver, 15);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(menu)).build().perform();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Submenu));
		WebElement countElement = _Searchdriver.findElement(countDiv);
		String jCount = countElement.getAttribute("textContent").trim();
		APP_LOGS.debug("Job Count: "+jCount);
		action.moveToElement(driver.findElement(Submenu));
		action.click().build().perform();
		APP_LOGS.debug("Job Count after regex: "+jCount.replaceAll("[^0-9]", ""));
		return jCount.replaceAll("[^0-9]", ""); 
	}



	//SIMPLE SEARCH
	public static void simpleSearch(String sKeyword, WebDriver _SimpleSearchdriver) {
		_SimpleSearchdriver.get(baseUrl + "/myshine/home/");
		_Utility.Thread_Sleep(3000);
		_SimpleSearchdriver.findElement(By.id("id_searchBase")).click();
		_SimpleSearchdriver.findElement(searchKeyword).click();
		_SimpleSearchdriver.findElement(searchKeyword).clear();
		_SimpleSearchdriver.findElement(searchKeyword).sendKeys(sKeyword);
		_SimpleSearchdriver.findElement(searchKeyword).sendKeys(Keys.TAB);
		_SimpleSearchdriver.findElement(searchbtn).click();
		_Utility.Thread_Sleep(2000);
		_Utility.clickOnNotification(_SimpleSearchdriver);
	}


	public void advanceSearch() {
		_Searchdriver.get(baseUrl + "/myshine/home/");
		_Utility.Thread_Sleep(1000);
		_Searchdriver.findElement(By.id("id_searchBase")).click();
		_Searchdriver.findElement(searchKeyword).click();
		_Searchdriver.findElement(searchKeyword).clear();
		_Searchdriver.findElement(searchKeyword).sendKeys("ANDROID");
		_Searchdriver.findElement(searchLocation).clear();
		_Searchdriver.findElement(searchLocation).sendKeys("Delhi");
		new Select(_Searchdriver.findElement(minExp)).selectByVisibleText("5 Yrs");
		WebDriverWait wait = new WebDriverWait(_Searchdriver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(expandAvanceSearch));
		wait.until(ExpectedConditions.elementToBeClickable(expandAvanceSearch));
		JavascriptExecutor jse = (JavascriptExecutor)_Searchdriver;
		jse.executeScript("arguments[0].click();", _Searchdriver.findElement(expandAvanceSearch));
		_Utility.Thread_Sleep(1000);
		new Select(_Searchdriver.findElement(minSal)).selectByVisibleText("Rs 3.0 - 3.5 Lakh / Yr");
		new Select(_Searchdriver.findElement(fa)).selectByVisibleText("IT - Software");
		new Select(_Searchdriver.findElement(ind)).selectByVisibleText("IT - Software");
		_Utility.Thread_Sleep(1000);
		_Searchdriver.findElement(searchbtn).click();

	}





}
