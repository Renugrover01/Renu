package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_HomepageLinks extends TestBaseSetup{

	WebDriver _HomepageLinks = null;
	By searchDiv = By.id("id_q");
	By topEmployersList = By.cssSelector("#slides li .cmplogo a");
	By topConsultantLink = By.linkText("View all top companies");
	By getTopConsultantsList = By.cssSelector("#state .cmplogo a");

	@BeforeClass
	public void TestSetup() {
		_HomepageLinks = getDriver(_HomepageLinks);
		_HomepageLinks.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		_HomepageLinks.get(baseUrl);
		_Utility.Thread_Sleep(2000);
	}


	@Test(dataProvider="getTopEmployerUrlData")
	public void veriy_top_employer_url(String url) {
		APP_LOGS.debug("Top empoyer url: "+url);
		sendGet(url);
	}

	@Test(dataProvider="getTopConsultantsUrlData")
	public void veriy_top_consultant_url(String url) {
		APP_LOGS.debug("Top empoyer url: "+url);
		sendGet(url);
	}


	@DataProvider(name="getTopEmployerUrlData")
	public Iterator<String> getTopEmployerUrlData() {
		_Utility.scrollTOElement(searchDiv, _HomepageLinks);
		List<WebElement> empURLlist = _HomepageLinks.findElements(topEmployersList);
		ArrayList<String> mainUrlList = new ArrayList<String>();
		for (WebElement url :  empURLlist) {
			String href = url.getAttribute("href").trim();
			if(!href.isEmpty()) {
				String parseUrl = StringUtils.substringAfterLast(href, "&next=");
				if(parseUrl.isEmpty())
					mainUrlList.add(href);
				else
					mainUrlList.add(parseUrl);
			}
		}	
		APP_LOGS.debug("Top employer url: "+mainUrlList);
		return mainUrlList.iterator();
	}



	@DataProvider(name="getTopConsultantsUrlData")
	public Iterator<String> getTopConsultantsUrlData() {
		_Utility.scrollTOElement(searchDiv, _HomepageLinks);
		_HomepageLinks.findElement(topConsultantLink).click();
		_Utility.Thread_Sleep(1000);
		List<WebElement> empURLlist = _HomepageLinks.findElements(getTopConsultantsList);
		ArrayList<String> mainUrlList = new ArrayList<String>();
		for (WebElement url :  empURLlist) {
			String href = url.getAttribute("href").trim();
			if(!href.isEmpty()) {
				String parseUrl = StringUtils.substringAfterLast(href, "&next=");
				if(parseUrl.isEmpty())
					mainUrlList.add(href);
				else
					mainUrlList.add(parseUrl);
			}
		}	
		APP_LOGS.debug("Top Consultant url: "+mainUrlList);
		return mainUrlList.iterator();
	}



	@AfterMethod(alwaysRun = true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _HomepageLinks);
	}

	@AfterClass(alwaysRun = true)
	public void quitbrowser() {
		if(_HomepageLinks!=null)
			_HomepageLinks.quit();

	}


	private static void sendGet(String href){
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(href);

		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int code = 0;
		try {
			code = response.getStatusLine().getStatusCode();
		}catch(NullPointerException ex) {
			APP_LOGS.debug("NullPointerException: "+ex.getMessage());
		}
		APP_LOGS.info("["+code+"] - "+href);
		assertEquals(code, 200);

	}


}
