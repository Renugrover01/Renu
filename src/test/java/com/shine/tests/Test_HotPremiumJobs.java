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

public class Test_HotPremiumJobs extends TestBaseSetup {

	WebDriver _HotPremiumJobdriver;
	WebDriverWait _Wait;

	int relevance_count =0;
	int recent_count =0;
	int jd_count =0;
	String company_jd_url = "";
	String company_premium_jd_url = "";

	By hot_job_logo = By.cssSelector(".hot_jobs");
	By premium_job_logo = By.cssSelector(".premium_jobs");
	By load_more   = By.id("id_paginate_next");
	By type_of_job = By.cssSelector("#id_results .typeofjob");

	@BeforeClass
	public void TestSetup() {
		_HotPremiumJobdriver = getDriver(_HotPremiumJobdriver);
		_HotPremiumJobdriver.get(baseUrl+"/job-search/java-jobs");
		_Utility.set_flag_checkTimeStamp(_HotPremiumJobdriver);
		_Utility.closeNotification(_HotPremiumJobdriver);
		_Utility.Thread_Sleep(2000);
	}

	@Test(priority=0, dataProvider="get_job_posted_date")
	public void verify_relevance_jsrp_date_for_hot_job(String posted_date) {
		relevance_count++;
		APP_LOGS.debug(relevance_count);
		verify_hot_job(posted_date, relevance_count-1);
	}


	@Test(priority=1)
	public void open_recent_search() {
		_HotPremiumJobdriver.get(baseUrl+"/job-search/java-jobs?sort=1");
		_Utility.Thread_Sleep(2000);
	}


	@Test(priority=2, dataProvider="get_job_posted_date")
	public void verify_recent_jsrp_date_for_hot_job(String posted_date) {
		recent_count++;
		verify_hot_job(posted_date, recent_count-1);

	}

	@Test(priority=3, dependsOnMethods= {"verify_recent_jsrp_date_for_hot_job"})
	public void verify_jd_hot_job_logo() {
		APP_LOGS.error("HOT job url found = "+company_jd_url);
		if(!company_jd_url.isEmpty()) {
			_HotPremiumJobdriver.get(company_jd_url);
			String job_type  = _HotPremiumJobdriver.findElement(hot_job_logo).getText().trim();
			APP_LOGS.debug("JD Job Type found: "+job_type);
			try {
				assertEquals(job_type, "Hot");
			}catch(AssertionError aex) {
				String job_type2  = _HotPremiumJobdriver.findElement(premium_job_logo).getText().trim();
				APP_LOGS.debug("JD Job Type found: "+job_type2);
				assertEquals(job_type2, "Premium");
			}
		}
		else
			throw new SkipException("No HOT JOB JD URL Found = "+company_jd_url);

	}



	@Test(priority=4, dataProvider="premium_job_list")
	public void verify_premium_job(String cname) {
		_HotPremiumJobdriver.get(baseUrl+"/job-search/java-jobs?sort=1");
		_Utility.Thread_Sleep(500);
		Test_Search.simpleSearch(cname, _HotPremiumJobdriver);
		_HotPremiumJobdriver.findElement(load_more).click();
		List<WebElement> cnameList = _HotPremiumJobdriver.findElements(By.cssSelector(".cls_cmpname"));
		for(int i=0;i<cnameList.size();i++) {
			APP_LOGS.debug("Company Name = "+cnameList.get(i).getText());
			if(cname.toLowerCase().contains(cnameList.get(i).getText().toLowerCase())) {
				try {
					if(company_premium_jd_url.isEmpty()) {
						try {
							company_premium_jd_url = _HotPremiumJobdriver.findElements(By.cssSelector("a.cls_searchresult_a")).get(i).getAttribute("href");
							APP_LOGS.debug("Premium Company JD URL = "+company_premium_jd_url);
						}catch(Exception ex) {
							APP_LOGS.error("Premium job jd url not found = "+ex.getMessage());

						}
					}
					String job_type  = _HotPremiumJobdriver.findElements(type_of_job).get(i).getText().trim();
					APP_LOGS.debug(cname+"  >> Job Type found >> "+job_type);
					job_type = job_type.replace("Hot", "");
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

	@Test(priority=5, dependsOnMethods= {"verify_premium_job"})
	public void verify_jd_premium_job_logo() {
		APP_LOGS.error("Premium job url found = "+company_premium_jd_url);
		if(!company_premium_jd_url.isEmpty()) {
			_HotPremiumJobdriver.get(company_premium_jd_url);
			_Utility.Thread_Sleep(1000);
			String job_type  = _HotPremiumJobdriver.findElement(premium_job_logo).getText().trim();
			APP_LOGS.debug("JD Job Type found: "+job_type);
			assertEquals(job_type, "Premium");
		}
		else
			throw new SkipException("No JD URL Found = "+company_premium_jd_url);

	}





	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _HotPremiumJobdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_HotPremiumJobdriver!=null)
			_HotPremiumJobdriver.quit();

	}



	@DataProvider(name="get_job_posted_date")
	public Iterator<String> getJobPostedDate() {
		_Utility.Thread_Sleep(3000);
		_Utility.scrollTOElement(By.id("id_paginate_next"), _HotPremiumJobdriver);
		_HotPremiumJobdriver.findElement(By.id("id_paginate_next")).click();
		_Utility.Thread_Sleep(3000);
		ArrayList<String> jsrpPostedDateList = new ArrayList<String>();
		List<WebElement> jsrp_posted_date_list  = _HotPremiumJobdriver.findElements(By.cssSelector("ul li.time"));
		for(WebElement posted_date : jsrp_posted_date_list) {
			jsrpPostedDateList.add(posted_date.getText());
		}
		return jsrpPostedDateList.iterator();
	}
	/**
	 * verify hot job
	 * @param hot_job_list
	 * @param posted_date
	 * @param count
	 */
	public void verify_hot_job(String posted_date, int count){
		if(isHotJob(posted_date)) {
			APP_LOGS.debug(posted_date);
			String job_type  = _HotPremiumJobdriver.findElements(type_of_job).get(count).getText().trim();
			APP_LOGS.debug("Job Type found: "+job_type);
			if(job_type.contains("Premium")) {
				job_type = job_type.replace("Hot", "");
				job_type = job_type.replace("Walk-in", "").trim();
				assertEquals(job_type, "Premium");
			}
			else {
				job_type = job_type.replace("Premium", "");
				job_type = job_type.replace("Walk-in", "").trim();
				assertEquals(job_type, "Hot");
				if(company_jd_url.isEmpty())
					company_jd_url = _HotPremiumJobdriver.findElements(By.cssSelector(".cls_searchresult_a")).get(count).getAttribute("href");

			}
		}
		else {
			String job_type  = _HotPremiumJobdriver.findElements(type_of_job).get(count).getText().trim();
			APP_LOGS.debug("Job Type found: "+job_type);
			job_type = job_type.replace("Premium", "");
			job_type = job_type.replace("Walk-in", "").trim();
			assertNotEquals(job_type, "Hot");
		}
	}



	public boolean isHotJob(String date) {
		if(date.contains("Posted Today"))
			return true;
		else if(date.contains("Posted Yesterday"))
			return true;
		else if(date.contains("Posted 2 days ago"))
			return true;
		else
			return false;
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
