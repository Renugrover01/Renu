package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_SimilarJob extends TestBaseSetup{
	WebDriver _SimilarJobDriver = null;
	WebDriverWait _Wait;

	String search_job_url = "";
	int page_counter = 2;

	String contractual_job_jd = "";
	String internship_job_jd = "";
	String wfh_job_jd = "";
	
	By jsrp_job_type		= By.cssSelector(".cls_jobType");	
	By jd_job_type_loggedin = By.cssSelector(".ul_sal");
	By similar_job_type		= By.cssSelector("li.snp_yoe_loc");	
	By jsrp_link			= By.cssSelector(".cls_searchresult_a");

	@BeforeClass
	public void TestSetup() {
		_SimilarJobDriver = getDriver(_SimilarJobDriver);
		_Wait = new WebDriverWait(_SimilarJobDriver, 15);
		search_job_url = baseUrl+"/job-search/java-jobs";

	}

	@Test(priority=0, dataProvider="verify_job_emp_type")
	public void test_contractual_jobs(String actual_emp_type){
		_Utility.Thread_Sleep(1000);
		assertEquals(actual_emp_type, "Contractual");
	}
	
	@Test(priority=1, dependsOnMethods= {"test_contractual_jobs"})
	public void verify_contractual_job_jd(){
		_Utility.Thread_Sleep(1000);
		_SimilarJobDriver.get(contractual_job_jd);
		_Utility.Thread_Sleep(1000);
		String actual_job_type = _SimilarJobDriver.findElement(jsrp_job_type).getText().trim();
		assertEquals(actual_job_type, "Contractual");
	}

	@Test(priority=2, dataProvider="verify_job_emp_type")
	public void test_internship_jobs(String actual_emp_type){
		_Utility.Thread_Sleep(1000);
		assertEquals(actual_emp_type, "Internship");
	}

	@Test(priority=3, dependsOnMethods= {"test_internship_jobs"})
	public void verify_internship_job_jd(){
		_Utility.Thread_Sleep(1000);
		_SimilarJobDriver.get(internship_job_jd);
		_Utility.Thread_Sleep(1000);
		String actual_job_type = _SimilarJobDriver.findElement(jsrp_job_type).getText().trim();
		assertEquals(actual_job_type, "Internship");
	}

	
	@Test(priority=4, dataProvider="verify_job_emp_type")
	public void test_wfh_job_jobs(String actual_emp_type){
		_Utility.Thread_Sleep(1000);
		assertEquals(actual_emp_type, "Work From Home");
	}

	@Test(priority=5, dependsOnMethods= {"test_wfh_job_jobs"})
	public void verify_wfh_job_jd(){
		_Utility.Thread_Sleep(1000);
		_SimilarJobDriver.get(wfh_job_jd);
		_Utility.Thread_Sleep(1000);
		String actual_job_type = _SimilarJobDriver.findElement(jsrp_job_type).getText().trim();
		assertEquals(actual_job_type, "Work From Home");
	}
	
	
	/************************* Logged-in user ****************************/

	@Test(priority=7, dependsOnMethods= {"test_contractual_jobs"})
	public void verify_loggedin_contractual_job_jd(){
		_Utility.Thread_Sleep(1000);
		loggedInShine(_SimilarJobDriver, email_new, pass_new);
		_Utility.Thread_Sleep(1000);
		_SimilarJobDriver.get(contractual_job_jd);
		_Utility.Thread_Sleep(1000);
		String actual_job_type = _SimilarJobDriver.findElement(jd_job_type_loggedin).getText().trim();
		assertTrue(actual_job_type.contains("Contractual"),"Contractual >> "+actual_job_type);

	}

	
	
	@Test(priority=8, dependsOnMethods= {"verify_loggedin_contractual_job_jd"})
	public void verify_loggedin_contractual_job_jd_similar_jobs(){
		_Utility.scrollTOElement(By.id("id_similarjobs_more"), _SimilarJobDriver);
		List<WebElement> similarJobList = _SimilarJobDriver.findElements(similar_job_type);
		for(WebElement similarjob:similarJobList) {
			matcher(similarjob.getText().trim(),"Contractual");
		}
	}
	
	@Test(priority=9, dependsOnMethods= {"test_internship_jobs"})
	public void verify_loggedin_internship_job_jd(){
		_Utility.Thread_Sleep(1000);
		_SimilarJobDriver.get(internship_job_jd);
		_Utility.Thread_Sleep(1000);
		String actual_job_type = _SimilarJobDriver.findElement(jd_job_type_loggedin).getText().trim();
		assertTrue(actual_job_type.contains("Internship"),"Internship >> "+actual_job_type);
	}
	
	
	@Test(priority=10, dependsOnMethods= {"verify_loggedin_internship_job_jd"})
	public void verify_loggedin_internship_job_jd_similar_jobs(){
		_Utility.scrollTOElement(By.id("id_similarjobs_more"), _SimilarJobDriver);
		List<WebElement> similarJobList = _SimilarJobDriver.findElements(similar_job_type);
		for(WebElement similarjob:similarJobList) {
			matcher(similarjob.getText().trim(),"Internship");
		}
	}

	@Test(priority=11, dependsOnMethods= {"test_wfh_job_jobs"})
	public void verify_loggein_wfh_job_jd(){
		_Utility.Thread_Sleep(1000);
		_SimilarJobDriver.get(wfh_job_jd);
		_Utility.Thread_Sleep(1000);
		String actual_job_type = _SimilarJobDriver.findElement(jd_job_type_loggedin).getText().trim();
		assertTrue(actual_job_type.contains("Work From Home"),"Work from home >> "+actual_job_type);

	}
	


	@Test(priority=12, dependsOnMethods= {"verify_loggein_wfh_job_jd"})
	public void verify_loggedin_wfh_job_jd_similar_jobs(){
		_Utility.scrollTOElement(By.id("id_similarjobs_more"), _SimilarJobDriver);
		List<WebElement> similarJobList = _SimilarJobDriver.findElements(similar_job_type);
		for(WebElement similarjob:similarJobList) {
			matcher(similarjob.getText().trim(),"Work From Home");
		}
	}


	@DataProvider
	public Iterator<String> verify_job_emp_type() {
		String url = "";
		url = search_job_url+"?emp_type="+page_counter;
		_SimilarJobDriver.get(url);
		List<WebElement> empTypeList = _SimilarJobDriver.findElements(By.cssSelector(".snp_yoe:nth-child(2)"));
		ArrayList<String> eType = new ArrayList<String>();
		for (WebElement empType: empTypeList) {
			eType.add(empType.getText().trim());
			if(page_counter==2 && contractual_job_jd.isEmpty()) {
				contractual_job_jd = _SimilarJobDriver.findElement(jsrp_link).getAttribute("href").trim();
			}
			else if(page_counter==3 && internship_job_jd.isEmpty()) {
				internship_job_jd = _SimilarJobDriver.findElement(jsrp_link).getAttribute("href").trim();
			}
			else if(page_counter==4 && wfh_job_jd.isEmpty()) {
				wfh_job_jd = _SimilarJobDriver.findElement(jsrp_link).getAttribute("href").trim();
			}

		}
		page_counter++;
		return eType.iterator();
	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _SimilarJobDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_SimilarJobDriver!=null)
			_SimilarJobDriver.quit();

	}


	public void matcher(String actual_value, String job_type) {
		try {
			assertTrue(actual_value.contains(job_type), " >> Similar job >>"+actual_value);
		}
		catch(AssertionError aerr) {
			APP_LOGS.debug("For = "+job_type+" >> Assertion error >> "+aerr.getMessage());
			if(job_type.contains("contractual")&&actual_value.contains("Contractual")) {
				assertFalse(actual_value.contains("Internship"), actual_value);
				assertFalse(actual_value.contains("Work From Home"), actual_value);
			}
			else if(job_type.contains("internship")&&actual_value.contains("Internship")) {
				assertFalse(actual_value.contains("Contractual"), actual_value);
				assertFalse(actual_value.contains("Work From Home"), actual_value);

			}
			else if(job_type.contains("wfh")&&actual_value.contains("Work From Home")) {
				assertFalse(actual_value.contains("Contractual"), actual_value);
				assertFalse(actual_value.contains("Internship"), actual_value);
			}
			else 
				assertFalse(actual_value.isEmpty(), actual_value);
			APP_LOGS.debug("************************");
		}

	}


}
