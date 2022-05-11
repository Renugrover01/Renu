package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_PremiumJobs extends TestBaseSetup {

	WebDriver _PremiumJobdriver;
	WebDriverWait _Wait;

	int relevance_count =0;
	int recent_count =0;
	int jd_count =0;
	String company_jd_url = "";
	String company_premium_jd_url = "";

	By search_textBox = By.id("id_searchBase");
	By skill_textBox = By.id("id_q");
	By search_button = By.name("simplesearch");
	By job_title = By.xpath("(//*[@class='snp cls_jobtitle'])[1]");
	By premium_job_logo_on_JSRP = By.xpath("(//*[@class='premium_jobs'])[1]");
	
	By premium_job_logo = By.xpath("//*[@class='premium_jobs']");
	By load_more   = By.xpath("//*[@class='submit submit1 pagination_button cls_pagination']");
	By type_of_job = By.cssSelector("#id_results .typeofjob");

	@BeforeClass
	public void TestSetup() {
		_PremiumJobdriver = getDriver(_PremiumJobdriver);
		_PremiumJobdriver.get(baseUrl+"/job-search/java-jobs");
		_Utility.set_flag_checkTimeStamp(_PremiumJobdriver);
		_Utility.closeNotification(_PremiumJobdriver);
		_Utility.Thread_Sleep(1000);
	}


	@Test(priority=0)
	public void open_recent_search() {
		_PremiumJobdriver.get(baseUrl+"/job-search/java-jobs?sort=1");
		_Utility.Thread_Sleep(1000);
	}


		@Test(priority=1, dataProvider="premium_job_list")
	public void verify_premium_job(String cname) {
		_PremiumJobdriver.get(baseUrl+"/job-search/java-jobs?sort=1");
		_Utility.Thread_Sleep(500);
		Test_Search.simpleSearch(cname, _PremiumJobdriver);
		_PremiumJobdriver.findElement(load_more).click();
		List<WebElement> cnameList = _PremiumJobdriver.findElements(By.cssSelector(".cls_cmpname"));
		for(int i=0;i<cnameList.size();i++) {
			APP_LOGS.debug("Company Name = "+cnameList.get(i).getText());
			if(cname.toLowerCase().contains(cnameList.get(i).getText().toLowerCase())) {
				try {
					if(company_premium_jd_url.isEmpty()) {
						try {
							company_premium_jd_url = _PremiumJobdriver.findElements(By.cssSelector("a.cls_searchresult_a")).get(i).getAttribute("href");
							APP_LOGS.debug("Premium Company JD URL = "+company_premium_jd_url);
						}catch(Exception ex) {
							APP_LOGS.error("Premium job jd url not found = "+ex.getMessage());

						}
					}
					String job_type  = _PremiumJobdriver.findElements(type_of_job).get(i).getText().trim();
					APP_LOGS.debug(cname+"  >> Job Type found >> "+job_type);
					//job_type = job_type.replace("Hot", "");
					job_type = job_type.replace("Walk-in", "").trim();
					assertEquals(job_type, "Premium");
				}catch(IndexOutOfBoundsException ex) {
					APP_LOGS.error("Premium job logo not found = "+ex.getMessage());
					assertEquals("Premium Job", "No premium job");
				}
			}
			else {
				APP_LOGS.error(cname+"  != "+cnameList.get(i).getText());
			}
		}
	}

	/*
	 * @Test(priority=2, dependsOnMethods= {"verify_premium_job"}) public void
	 * verify_jd_premium_job_logo() {
	 * APP_LOGS.error("Premium job url found = "+company_premium_jd_url);
	 * if(!company_premium_jd_url.isEmpty()) {
	 * _PremiumJobdriver.get(company_premium_jd_url); _Utility.Thread_Sleep(1500);
	 * String job_type =
	 * _PremiumJobdriver.findElement(premium_job_logo).getText().trim();
	 * APP_LOGS.debug("JD Job Type found: "+job_type); assertEquals(job_type,
	 * "Premium"); } else throw new
	 * SkipException("No JD URL Found = "+company_premium_jd_url);
	 * 
	 * }
	 */

	/*
	 * @Test(priority=3) public void verify_premium_job_in_login() {
	 * _PremiumJobdriver.navigate().to(baseUrl); loggedInShine(_PremiumJobdriver,
	 * email_new, pass_new);
	 * _PremiumJobdriver.navigate().to(baseUrl+"/myshine/home/");
	 * _Utility.Thread_Sleep(1000);
	 * _PremiumJobdriver.findElement(search_textBox).click();
	 * _Utility.Thread_Sleep(1000);
	 * _PremiumJobdriver.findElement(skill_textBox).sendKeys("Infosys Limited");
	 * _Utility.Thread_Sleep(1000);
	 * _PremiumJobdriver.findElement(skill_textBox).sendKeys(Keys.TAB);
	 * _Utility.Thread_Sleep(1000);
	 * _PremiumJobdriver.findElement(search_button).click();
	 * _Utility.Thread_Sleep(2000);
	 * 
	 * boolean is_premiumjob_logo_present =
	 * _PremiumJobdriver.findElement(premium_job_logo_on_JSRP).isDisplayed(); if
	 * (is_premiumjob_logo_present) { APP_LOGS.debug("Premium Job available");
	 * assertEquals(is_premiumjob_logo_present, true); } else
	 * APP_LOGS.debug("No Premium job found"); }
	 */

	/*
	 * @Test(priority=4) public void verify_jd_premium_job_logo_in_login() {
	 * _PremiumJobdriver.findElement(job_title).click();
	 * _Utility.Thread_Sleep(2000);
	 * 
	 * boolean is_premiumjob_logo_present =
	 * _PremiumJobdriver.findElement(premium_job_logo).isDisplayed(); if
	 * (is_premiumjob_logo_present) { APP_LOGS.debug("Premium Job available");
	 * assertEquals(is_premiumjob_logo_present, true); } else
	 * APP_LOGS.debug("No Premium job found");
	 * 
	 * 
	 * }
	 */
	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _PremiumJobdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_PremiumJobdriver!=null)
			_PremiumJobdriver.quit();

	}



	@DataProvider(name="get_job_posted_date")
	public Iterator<String> getJobPostedDate() {
		_Utility.Thread_Sleep(1000);
		_Utility.scrollTOElement(By.id("id_paginate_next"), _PremiumJobdriver);
		_PremiumJobdriver.findElement(By.id("id_paginate_next")).click();
		_Utility.Thread_Sleep(1000);
		ArrayList<String> jsrpPostedDateList = new ArrayList<String>();
		List<WebElement> jsrp_posted_date_list  = _PremiumJobdriver.findElements(By.cssSelector("ul li.time"));
		for(WebElement posted_date : jsrp_posted_date_list) {
			jsrpPostedDateList.add(posted_date.getText());
		}
		return jsrpPostedDateList.iterator();
	}


	@DataProvider(name = "premium_job_list")
	public Iterator<String> premium_job_list() throws Exception
	{
		String csvFile = System.getProperty("user.dir")+"/src/test/resources/data/premium_job_list.csv";
		String line = "";
		List<String> random_cname = new ArrayList<>();
		List<String> cname_list = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
			cname_list.add(line);

		}
		br.close();
		Random rand = new Random();
		random_cname.add(cname_list.get(rand.nextInt(cname_list.size())));
		random_cname.add(cname_list.get(rand.nextInt(cname_list.size())));
		if(random_cname.get(0).equals(random_cname.get(1))) {
			random_cname.set(1, cname_list.get(rand.nextInt(cname_list.size())));
		}
		APP_LOGS.debug(random_cname.get(0)+" : Company Name : "+random_cname.get(1));
		APP_LOGS.debug(random_cname);
		return random_cname.iterator();
	}


}

