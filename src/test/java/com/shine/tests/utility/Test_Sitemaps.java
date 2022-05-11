package com.shine.tests.utility;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CreateSiteMapHTMLReport;
import com.shine.emailer.SiteMapMailer;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Test_Sitemaps extends TestBaseSetup{

	WebDriver _SitemapDriver = null;
	Map<String, Integer> distinctWordCounts = null;
	int count = 0;
	

	@BeforeClass
	public void TestSetup() {
		_SitemapDriver = getDriver(_SitemapDriver);
		_SitemapDriver.get("https://www.shine.com/index-all-sitemap.xml");
	}


	@Test(priority =1)
	public void getURL(){
		List<WebElement> sitemapList = _SitemapDriver.findElements(By.cssSelector("sitemap loc"));
		distinctWordCounts = new TreeMap<>();
		for (WebElement sitemap:sitemapList) {
			String sitemapUrl = sitemap.getText();
			sitemapUrl =  sitemapUrl.replace("https://www.shine.com/sitemaps/", "");
			sitemapUrl = StringUtils.substringBefore(sitemapUrl, "_sitemap");
			sitemapUrl = StringUtils.substringBefore(sitemapUrl, "_");
			//System.out.println(sitemapUrl);
			if (distinctWordCounts.containsKey(sitemapUrl)) {
				// If the String found => Increment the count we've seen it.
				distinctWordCounts.put(sitemapUrl, distinctWordCounts.get(sitemapUrl) + 1);
			} else {
				//If the String not seen or found => Set the count seen to 1.
				distinctWordCounts.put(sitemapUrl, 1);
			}
		}
		int count = 0;
		for (Entry<String, Integer> entry : distinctWordCounts.entrySet()) {
			System.out.println(entry.getKey() + " occurs " + entry.getValue() + " times.");
			count = count + entry.getValue();
		}
		System.out.println(count);
	}

	@Test(priority =2, dependsOnMethods= {"getURL"})
	public void create_html(){
		CreateSiteMapHTMLReport _CreateSiteMapHTMLReport = new CreateSiteMapHTMLReport();
		count = _CreateSiteMapHTMLReport.create_html_report(distinctWordCounts);
	}


	@Test(priority =3, dependsOnMethods= {"create_html"})
	public void send_email(){
		SiteMapMailer _Email = new SiteMapMailer();
		try {
			_Email.send_email(count);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _SitemapDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_SitemapDriver!=null)
			_SitemapDriver.quit();

	}

}
