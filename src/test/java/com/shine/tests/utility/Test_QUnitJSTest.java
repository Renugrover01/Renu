package com.shine.tests.utility;

import com.shine.base.TestBaseSetup;
import com.shine.beans.QunitReportData;
import com.shine.common.utils.CreateHTMLReport;
import com.shine.emailer.JTEST_Email;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_QUnitJSTest extends TestBaseSetup{


	String web_url = "https://sumosc.shine.com/qunit-test/";
	String msite_url = "https://sm.shine.com/qunit-test/";
	QunitReportData _QunitReportData;
	CreateHTMLReport _CreateHTMLReport;
	JTEST_Email _Email;

	WebDriver driver = null;
	@BeforeClass(alwaysRun = true)
	public void TestSetup() {
		String domain_name = System.getenv("build");
		if(domain_name!=null) {
			if(domain_name.contains("sumosc1.shine.com")) {
				web_url = "https://sumosc1.shine.com/qunit-test/";
				msite_url = "https://sm1.shine.com/qunit-test/";
			}
			else if (domain_name.contains("sumosc.shine.com")){
				web_url = "https://sumosc.shine.com/qunit-test/";
				msite_url = "https://sm.shine.com/qunit-test/";
			}
			else if (domain_name.contains("www")){
				web_url = "https://www.shine.com/qunit-test/";
				msite_url = "https://m.shine.com/qunit-test/";
			}
			else {
				throw new SkipException("No url found");
			}
		}
		else 
			throw new SkipException("No url found");
		driver = getDriver(driver);
		APP_LOGS.debug("Starting the Javascript test");
		_CreateHTMLReport = new CreateHTMLReport();
	}

	@Test (priority = 1, alwaysRun = true)
	public void test_web_javascript() throws Exception{
		driver.get(web_url);
		_Utility.Thread_Sleep(2000);
		//Document doc = Jsoup.connect(web_url).get();
		String html = driver.getPageSource();
		Document doc = Jsoup.parse(html);	
		_QunitReportData = new QunitReportData();
		_QunitReportData.setProject_name("#Shine Desktop Test");
		APP_LOGS.debug("Project Name: "+_QunitReportData.getProject_name());
		extract_data_from_html(doc, _QunitReportData);
		_CreateHTMLReport.create_html_report(_QunitReportData);
		_Email = new JTEST_Email();
		_Email.sendEmail(_QunitReportData);

	}

	@Test (priority = 2, alwaysRun = true)
	public void test_mobile_javascript() throws Exception{
		//Document doc = Jsoup.connect(msite_url).get();
		_Utility.Thread_Sleep(2000);
		driver = getDriver(driver);
		driver.get(msite_url);
		_Utility.Thread_Sleep(2000);
		String html = driver.getPageSource();
		Document doc = Jsoup.parse(html);
		_QunitReportData = new QunitReportData();
		_QunitReportData.setProject_name("#Shine Mobile Test");
		APP_LOGS.debug("Project Name: "+_QunitReportData.getProject_name());
		extract_data_from_html(doc, _QunitReportData);
		_CreateHTMLReport.create_html_report(_QunitReportData);
		_Email = new JTEST_Email();
		_Email.sendEmail(_QunitReportData);

	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(driver!=null)
			driver.quit();

	}


	/**
	 * Parse multiple data from HTML
	 * @param doc
	 */
	public void extract_data_from_html(Document doc, QunitReportData _QunitReportData) {
		_QunitReportData.setReport_header(doc.select("#qunit-header").html());
		APP_LOGS.debug("qunit header "+_QunitReportData.getReport_header());

		_QunitReportData.setUser_agent(doc.select("#qunit-userAgent").html());
		APP_LOGS.debug("user_agent "+_QunitReportData.getUser_agent());

		_QunitReportData.setTestcase_total_count(doc.select("#qunit-testresult-display .total").text()); 
		APP_LOGS.debug("total_testcase "+_QunitReportData.getTestcase_total_count());

		_QunitReportData.setTestcase_passed_count(doc.select("#qunit-testresult-display .passed").text()); 
		APP_LOGS.debug("passed_testcase "+_QunitReportData.getTestcase_passed_count());

		_QunitReportData.setTestcase_failed_count(doc.select("#qunit-testresult-display .failed").text()); 
		APP_LOGS.debug("failed_testcase "+_QunitReportData.getTestcase_failed_count());

		_QunitReportData.setReport_status(doc.getElementById("qunit-testresult-display").text()); 
		APP_LOGS.debug("Status: "+_QunitReportData.getReport_status());

		_QunitReportData.setTestcase_skipped_count(StringUtils.substringBetween(_QunitReportData.getReport_status(), "failed,", "skipped,").trim());
		APP_LOGS.debug("Skipped "+_QunitReportData.getTestcase_skipped_count());

		_QunitReportData.setResult(doc.select("#qunit-tests > li").outerHtml());
		APP_LOGS.debug("Status: "+_QunitReportData.getResult());
	}




}
