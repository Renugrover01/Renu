package com.shine.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.json.JSONObject;

import com.shine.base.TestBaseSetup;

public class Test_NotificationBell extends TestBaseSetup{

	WebDriver _NotificationDriver;
	WebDriverWait _Wait;
	String api_url = "";
	//String checkItem = "Recruiter Email";
	int notificationCount = 0;
	int recentSearchFlag  = 0;
	int globalJSRPCount	  = 0;
	ArrayList<String> dataList = new ArrayList<String>();

	By notiCount   = By.cssSelector(".notify_count.cls_notify_count");
	By notiLink    = By.cssSelector(".cls_show_notify");
	By notilisting = By.xpath("//ul[@class='notification_list cls_alert_list']/li");

	By recentSearchLink = By.xpath("//a[@data-key='recent_search']");
	By recentSearchCount= By.xpath("//a[@data-key='recent_search']/span[@class='noti_left']");
	By jsrpCount		= By.id("id_resultCount");

	By jsrpLoadMore = By.id("id_loader_loadmore");




	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the Notification test");
		api_url = baseUrl+"/api/v2/candidate-notification-data/5e424268ba0a186618881a19/";
		_NotificationDriver = getDriver(_NotificationDriver);
		_Wait = new WebDriverWait(_NotificationDriver, 15);
		OpenBaseUrl(_NotificationDriver);
		loggedInShine(_NotificationDriver, email_new, pass_new);
		_Utility.clickOnNotification(_NotificationDriver);
	}


	@Test(priority=0)
	public void verify_NotificationBell() {
		notificationCount = get_NewDataCount();
		APP_LOGS.debug("API Notification Count: "+notificationCount);
		Assert.assertTrue(notificationCount>0);
	}


	@Test(priority=1)
	public void verify_NotificationCount() {
		String countData = _NotificationDriver.findElement(notiCount).getText().toString();
		APP_LOGS.debug("Web Notification count in string: "+countData);
		int webCount = 0;
		if(StringUtils.isEmpty(countData))
			webCount = 0;
		else webCount = Integer.parseInt(countData);
		APP_LOGS.debug("Web Notification count in int: "+webCount);
		Assert.assertTrue(webCount==notificationCount, countData);
	}

	@Test(priority=2)
	public void test_clickOnBell() {
		_Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(notiLink));
		_NotificationDriver.findElement(notiLink).click();	
		_Utility.Thread_Sleep(2000);
		JavascriptExecutor jse = (JavascriptExecutor) _NotificationDriver;
		String sessionStorageData = (String) jse.executeScript("return $('.notification_list').length").toString();
		APP_LOGS.debug("Session Data: "+sessionStorageData);
		Assert.assertTrue(Integer.parseInt(sessionStorageData)>=1);
	}


	@Test(priority=3, dataProvider="notificationName")
	public void verify_isNotificationNew(String checkItem) {
		List<WebElement> notiList = _NotificationDriver.findElements(notilisting);
		for(WebElement noti: notiList) {
			String notiTxt = noti.findElement(By.tagName("a")).getAttribute("data-key");
			APP_LOGS.debug("Checking for - "+notiTxt+" - Notification");
			if(notiTxt.contains("recent_search")||checkItem.contains("recent_search"))
				recentSearchFlag = 1;
			if(notiTxt.contains(checkItem)) {
				String newCheck = noti.getAttribute("class");
				APP_LOGS.debug("Checking for new_alert class presence: "+newCheck);
				Assert.assertTrue(newCheck.contains("new_alert"), newCheck);
				break;
			}

		}
	}

	/*@Test(priority=4)
	public void verify_RecentSearch_Count() {
		if(recentSearchFlag==0)
			throw new SkipException("Recent Search Not Found");
		String strCount = _NotificationDriver.findElement(recentSearchCount).getText();
		int expected_count = Integer.parseInt(strCount.replaceAll("[^0-9]", ""));
		APP_LOGS.info("Notifciation count: "+expected_count);
		APP_LOGS.info("Notifciation: Global API Count "+globalJSRPCount);
	    if(expected_count <999)
	    	  Assert.assertTrue(globalJSRPCount==expected_count, "API Count"+globalJSRPCount+" : "+expected_count);
	  	else 
	  		Assert.assertTrue(globalJSRPCount>0);



	}*/

	/*
	 * @Test(priority=4) public void verify_RecentSearch_Count() {
	 * if(recentSearchFlag==0) throw new SkipException("Recent Search Not Found");
	 * String strCount =
	 * _NotificationDriver.findElement(recentSearchCount).getText(); int
	 * expected_count = Integer.parseInt(strCount.replaceAll("[^0-9]", ""));
	 * globalJSRPCount = expected_count;
	 * APP_LOGS.info("Notifciation count: "+expected_count);
	 * _Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
	 * recentSearchLink));
	 * _NotificationDriver.findElement(recentSearchLink).click();
	 * _Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jsrpCount));
	 * String jsrpStrCount = _NotificationDriver.findElement(jsrpCount).getText();
	 * int actual_count = Integer.parseInt(jsrpStrCount.replaceAll("[^0-9]", ""));
	 * APP_LOGS.info("Notifciation: JSRP "+actual_count);
	 * APP_LOGS.info("Notifciation: Global API Count "+globalJSRPCount);
	 * if(expected_count <999) Assert.assertEquals(actual_count, expected_count);
	 * else Assert.assertTrue(actual_count==globalJSRPCount);
	 * 
	 * }
	 */

	/*
	 * @Test(priority=5, dependsOnMethods={"verify_RecentSearch_Count"}) public void
	 * verify_JSRP() { _Utility.Thread_Sleep(2000); try {
	 * _Utility.scrollTOElement(jsrpLoadMore, _NotificationDriver);
	 * _Utility.elementDisplayPropertySetter("none", "id_filters",
	 * _NotificationDriver);
	 * _Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jsrpLoadMore)
	 * ); _NotificationDriver.findElement(jsrpLoadMore).click(); }
	 * catch(NoSuchElementException ex) {
	 * APP_LOGS.debug("Limited result found on JSRP: Single page found: "
	 * +globalJSRPCount); } catch(TimeoutException ex) {
	 * APP_LOGS.debug("Limited result found on JSRP: Single page found: "
	 * +globalJSRPCount); }
	 * _Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jsrpCount));
	 * String jsrpStrCount = _NotificationDriver.findElement(jsrpCount).getText();
	 * int actual_count = Integer.parseInt(jsrpStrCount.replaceAll("[^0-9]", ""));
	 * APP_LOGS.info("Notifciation: verify_JSRP "+actual_count);
	 * Assert.assertEquals(actual_count, globalJSRPCount);
	 * 
	 * }
	 */



	@DataProvider(name="notificationName")
	public Iterator<String> checkNotification() {
		return dataList.iterator();
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _NotificationDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_NotificationDriver!=null)
			_NotificationDriver.quit();

	}


	/*GET API RESPONSE*/
	public int get_NewDataCount(){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Authorization", "Basic bWFudmkuYWdhcndhbDE5MTAxOEBnbWFpbC5jb206MTIzNDU2")
					.header("Accept", "application/json")
					.get();
			String value = response.readEntity(String.class);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			int count =jParser(value, "new_data");
			response.close(); 
			client.close();
			return count;
		}
		catch (Exception e) {
			APP_LOGS.fatal("Api comunication error: "+e.getMessage());
			return 0;
		}
	}

	/*Parse json response*/
	public int jParser(String response, String key){
		int data = 0;

		try {
			JSONObject obj = new JSONObject(response);
			JSONObject jsonObject = (JSONObject) obj;
			APP_LOGS.debug(jsonObject);
			JSONObject newData = (JSONObject) jsonObject.get(key);
			data = newData.length();
			for (String keyName : JSONObject.getNames(newData))
			{
				APP_LOGS.debug(keyName);
				dataList.add(keyName);
			}
			try {
				JSONObject new_data = (JSONObject) jsonObject.get("new_data");
				JSONObject recent_search = (JSONObject) new_data.get("recent_search");
				System.out.println(recent_search);
				String count = recent_search.get("count").toString();
				globalJSRPCount = Integer.parseInt(count);

			}
			catch(Throwable t){
				APP_LOGS.error("No Recent search Notification found: "+t.getMessage());
				t.printStackTrace();	
			}

			APP_LOGS.debug("Notification array: "+dataList);
		}
		catch(Throwable t){
			APP_LOGS.error("No Notification update found: "+t.getMessage());
			t.printStackTrace();	
		}
		return data;	

	}



}
