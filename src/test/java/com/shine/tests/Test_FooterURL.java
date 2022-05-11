package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.listener.Transformer;
import com.shine.base.TestBaseSetup;

public class Test_FooterURL extends TestBaseSetup{

	WebDriver _FooterURLDriver;


	@BeforeClass
	public void TestSetup() {
		TestNG testNG = new TestNG();
	    testNG.addListener(new Transformer());
		APP_LOGS.debug("Starting the Footer URL test");
		_FooterURLDriver = getDriver(_FooterURLDriver);
	}


	@Test (priority =0, dataProvider="footerurl")
	public void verify_footer_url (String url){
		verify_url(baseUrl+url, false, false);
	}

	@Test (priority =1, dataProvider="footerurl")
	public void verify_footer_redirect_url (String url){
		verify_url(baseUrl+"/myshine"+url, false, true);
	}

	@Test(priority = 3)
	public void login() {
		loggedInShine(_FooterURLDriver, email_new, pass_new);
		_Utility.Thread_Sleep(500);
	}

	@Test (priority =4, dataProvider="footerurl", dependsOnMethods={"login"})
	public void verify_loggedin_footer_url (String url){
		verify_url(baseUrl+"/myshine"+url, true, false);
	}

	@Test (priority =5, dataProvider="footerurl", dependsOnMethods={"login"})
	public void verify_loggedin_footer_redirect_url (String url){
		verify_url(baseUrl+url, true, true);
	}


	@DataProvider(name="footerurl")
	public Object [] footerurl(){
		Object [] data= new Object [8];
		data[0]="/aboutus/";		
		data[1]="/contactus/";
		data[2]="/privacypolicy/";
		data[3]="/termsandconditions/";
		data[4]="/disclaimer/";		
		data[5]="/contactus/?type=reportJobPosting";
		data[6]="/securityadvice/";
		data[7]="/faqs/";

		return data;

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _FooterURLDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_FooterURLDriver!=null)
			_FooterURLDriver.quit();

	}


	/**
	 * Verify URL
	 * @param actual_url
	 * @param isLoggedIn
	 * @param isRedirectCheck
	 */
	public void verify_url(String actual_url, boolean isLoggedIn, boolean isRedirectCheck){

		_FooterURLDriver.get(actual_url);
		if(isLoggedIn) {
			if(isRedirectCheck) {
				actual_url = actual_url.replace(baseUrl, baseUrl+"/myshine");
			}
		}
		else {
			if(isRedirectCheck) {
				/*Hack for url encoding*/
				if(actual_url.contains("?type=reportJobPosting"))
					actual_url = actual_url.replace(baseUrl+"/myshine", baseUrl+"/myshine/login/?next=/myshine").replace("?type=", "%3Ftype%3D");
				else
					actual_url = actual_url.replace(baseUrl+"/myshine", baseUrl+"/myshine/login/?next=/myshine");
			}
			try {
				Alert alert = _FooterURLDriver.switchTo().alert();
				assertTrue(alert.getText().toLowerCase().contains("sign in"));
			}
			catch(NoAlertPresentException ex) {
				APP_LOGS.debug(ex.getMessage());
			}
		}
		String current_url = _FooterURLDriver.getCurrentUrl();
		checkURLConnectionStatus(actual_url, isLoggedIn, false);
		assertEquals(current_url, actual_url);
		try {
			String issue = _FooterURLDriver.findElement(By.cssSelector(".base div strong")).getText();
			assertTrue(issue.contains("Sorry, for some reason, we can't find the page you are looking for. "), issue);
		}
		catch(NoSuchElementException ex) {
			APP_LOGS.debug(ex.getMessage());
		}


	}


	/**
	 * Get URL Connection Status
	 * @param url
	 * @param isLoggedIn
	 * @param isMSite
	 */
	public void checkURLConnectionStatus(String url, boolean isLoggedIn, boolean isMSite){
		try {
			String encoded = Base64.getEncoder().encodeToString((email_new+":"+pass_new).getBytes(StandardCharsets.UTF_8));
			java.net.URL obj = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(2500);
			conn.setRequestMethod("GET");
			if(isLoggedIn)
				conn.setRequestProperty("Authorization", "Basic "+encoded);
			if(isMSite)
				conn.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
			else
				conn.addRequestProperty("User-Agent", "Mozilla");

			conn.setRequestMethod("GET");
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("Referer", baseUrl);
			int status = conn.getResponseCode();
			APP_LOGS.debug("["+status+"] : Request URL ... " + url);
			// get redirect url from "location" header field
			String newUrl = conn.getHeaderField("Location");					
			APP_LOGS.debug("Redirect to URL : " + newUrl);
			assertEquals(status, 200);

			APP_LOGS.debug("Done");
			if(conn != null) {
				conn.disconnect(); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}


}




