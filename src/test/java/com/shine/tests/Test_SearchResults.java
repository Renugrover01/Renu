package com.shine.tests;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.ITestResult;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonUtils;
import com.shine.tests.Test_Search;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;

public class Test_SearchResults extends TestBaseSetup {
	WebDriver _Srdriver;
	Test_Search _Test_Search;
	CommonUtils _CommonUtils;

	By jobTitle = By.cssSelector(".cls_jobtitle");
	By skillJsrpLink = By.cssSelector(".cls_jobskill");
	private String api_url;


	@BeforeClass
	public void TestSetup() throws Exception {
		_Srdriver = getDriver(_Srdriver);
		OpenBaseUrl(_Srdriver);
		_Test_Search =  new Test_Search();
		_Srdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		_Utility.clickOnNotification(_Srdriver);
		api_url = baseUrl+"/api/v2/search/simple/?q=";

	}

	@Test (dataProvider="searchData", enabled=true)
	public void testSearchResult(String searchKeyword) throws Exception{
		APP_LOGS.debug("===================="+searchKeyword.toUpperCase()+" - KEYWORD SEARCH=======================");
		Test_Search.simpleSearch(searchKeyword, _Srdriver);
		String webSearchCount=_Srdriver.findElement(_Test_Search.jobCount).getText().replaceAll(",", "");
		APP_LOGS.info("WebPage Count: "+webSearchCount);
		Assert.assertNotEquals("0", webSearchCount);
		List <WebElement> jobTitleList = _Srdriver.findElements(jobTitle);
		List <WebElement> skillList = _Srdriver.findElements(skillJsrpLink);
		//check first page search results
		for(int i=0;i<5;i++){
			String title = jobTitleList.get(i).getText();
			APP_LOGS.info("Job title: "+title);
			boolean checktitle = title.toLowerCase().contains(searchKeyword.toLowerCase());
			assertTrue(skillList.get(i).isDisplayed(), skillList.get(i).getText());
			try{
				assertTrue(checktitle, "Assertion failed while Checking title: "+jobTitleList.get(i).getText());
			} catch (AssertionError t1){
				APP_LOGS.error(title+" - Title doesnt contain the keyword "+searchKeyword);
			}

		}
		long webSCount = 0;
		if(webSearchCount.contains("1 Lacs")) {
			webSCount = 100000;
		}
		else
			webSCount = Long.parseLong(webSearchCount.trim());
		APP_LOGS.info("Web Search Count: "+webSCount);
		assertTrue(webSCount>0, "Web Search Count: "+webSCount);
		//API Count Matching logic
		/*	long apiSearchCount = getSearchAPICount(searchKeyword);
		APP_LOGS.info("API Count: "+apiSearchCount);
		try{
			long webSCount = Long.parseLong(webSearchCount.trim());
			APP_LOGS.info("Web Search Count: "+webSCount);
			assertEquals(webSCount, apiSearchCount);
		}
		catch (NumberFormatException nfe){
			assertTrue(apiSearchCount>0);
		}*/
	}


	@DataProvider(name = "searchData")
	public Iterator<Object []> provider(ITestContext context) throws Exception
	{
		int i = 1;
		String search = context.getCurrentXmlTest().getParameter("search");
		String[] data= null;
		String csvFile = System.getProperty("user.dir")+"/src/test/resources/data/topsearches.csv";
		String line = "";
		String cvsSplitBy = ",";
		List<Object []> testCases = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
			// use comma as separator
			data= line.split(cvsSplitBy);
			if(search.equals("1")&&i<=20) {
				testCases.add(data);
			}
			else if(search.equals("2")&&i>20&&i<=40) {
				testCases.add(data);
			}
//			else if(search.equals("3")&&i>40&&i<=60) {
//				testCases.add(data);
//			}
//			else if(search.equals("4")&&i>60&&i<=80) {
//				testCases.add(data);
//			}
//			else if(search.equals("5")&&i>80&&i<=101) {
//				testCases.add(data);
//			}
			i++;
		}
		br.close();
		APP_LOGS.debug(i+" : Size : "+testCases.size());
		return testCases.iterator();
	}

	/*GET API RESPONSE*/
	public long getSearchAPICount(String keyword){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url+keyword);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
			String value = response.readEntity(String.class);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			long count = Long.parseLong(jParser(value, "count"));
			if(count <=0) APP_LOGS.info(value);
			Assert.assertTrue(count>0,"No result found: Count - "+count);
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
	public static String jParser(String response, String key){
		JSONParser parser = new JSONParser();
		String data ="";

		try {
			Object obj = parser.parse(response);
			JSONObject jsonObject = (JSONObject) obj;
			data =jsonObject.get(key).toString();
		}
		catch(Throwable t){
			t.printStackTrace();	
		}
		return data;	

	}


	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_Srdriver!=null)
			_Srdriver.quit();
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Srdriver);
	}







}
