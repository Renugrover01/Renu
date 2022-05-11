package com.shine.tests;

import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_ApplicationInsights extends TestBaseSetup {

	static WebDriver  _AppInsightdriver;
	static WebDriverWait wait;

	String insightHeadDiv  = "(//*[@class='flow']/h3)";
	By insightHeader       = By.cssSelector("#id_sliderHeading h2");
	By nextInsightsBtn 	   = By.linkText("Next");
	By jobIdAttribute  	   = By.xpath("//a[contains(@id,'apply_')]");
	By applicantCount  	   = By.id("id_jobApplicants");
	By applicantLoginLink  = By.id("id_application_insight_login");
	By applicantRegLink	   = By.id("id_insight_register");
	By nextInsightLink     = By.linkText("Next");

	@BeforeClass
	public void TestSetup() throws Exception {
		_AppInsightdriver = getDriver(_AppInsightdriver);	
		OpenBaseUrl(_AppInsightdriver);
		wait = new WebDriverWait(_AppInsightdriver, 10);
		_Utility.clickOnNotification(_AppInsightdriver);

	}

	@Test(priority=0)
	public void verify_isInsightDisplay_lo() throws Exception{
		Test_JobApply.openjdpage(_AppInsightdriver);
		String jobId = _AppInsightdriver.findElement(jobIdAttribute).getAttribute("data-jobid");
		APP_LOGS.debug("job id for Application Insight: "+jobId);
		_Utility.scrollTOElement(insightHeader, _AppInsightdriver);
		String actual_header =  _AppInsightdriver.findElement(insightHeader).getText();
		APP_LOGS.debug("Application isnight heading is: "+actual_header);
		Assert.assertEquals(actual_header, "Application Insights", "Heading Not found");
		String response = getInsightAPIData(jobId);
		String count = jParser(response, "job_apply_count");
		APP_LOGS.debug("Job Apply count: "+count);
		long jobCount = 0;
		try{
			jobCount = Long.parseLong(count);
		}
		catch(Throwable t){
			jobCount = 0 ;
		}
		if(jobCount>=10){
			((JavascriptExecutor) _AppInsightdriver).executeScript("arguments[0].scrollIntoView(true);", 
					_AppInsightdriver.findElement(insightHeader));
			wait.until(ExpectedConditions.visibilityOfElementLocated(nextInsightLink));
			_AppInsightdriver.findElement(nextInsightLink).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(applicantCount));
			WebElement applicantNUmber = _AppInsightdriver.findElement(applicantCount);
			APP_LOGS.debug("Application Number is:"+applicantNUmber.getText());
			Assert.assertTrue(applicantNUmber.isDisplayed(), applicantNUmber.getText());
		}
		else{
			wait.until(ExpectedConditions.visibilityOfElementLocated(applicantLoginLink));
			WebElement applicantLogin = _AppInsightdriver.findElement(applicantLoginLink);
			Assert.assertTrue(applicantLogin.isDisplayed(), applicantLogin.getText());
			APP_LOGS.debug("Application Number is:"+applicantLogin.getText());
			WebElement applicantRegisterLink = _AppInsightdriver.findElement(applicantRegLink);
			Assert.assertTrue(applicantRegisterLink.isDisplayed(), applicantRegisterLink.getText());
			APP_LOGS.debug("Application Number is:"+applicantRegisterLink.getText());
		}	
	}


	/*
	 * @Test(priority=1) public void verify_isInsightDisplay_li(){
	 * _Utility.Thread_Sleep(3000); TestBaseSetup.loggedInShine(_AppInsightdriver,
	 * email_new, pass_new); _Utility.Thread_Sleep(3000);
	 * Test_Search.simpleSearch("java", _AppInsightdriver); List<WebElement> jobList
	 * =
	 * _AppInsightdriver.findElements(By.xpath("//a[@class='cls_searchresult_a']"));
	 * for(WebElement singleJOB: jobList){ String jobId =
	 * singleJOB.getAttribute("data-jid"); String response =
	 * getInsightAPIData(jobId); long jobCount = 0; try{ jobCount =
	 * Long.parseLong(jParser(response, "job_apply_count")); } catch(Throwable t){
	 * jobCount = 0 ; } if(jobCount>10){ APP_LOGS.debug("JOB ID:"+jobId);
	 * _AppInsightdriver.navigate().to(baseUrl+"/jobs/"+jobId+"/");
	 * _Utility.scrollTOElement(By.cssSelector("#id_sliderHeading .heading"),
	 * _AppInsightdriver); _Utility.elementDisplayPropertySetter("none", "id_title",
	 * _AppInsightdriver);
	 * Assert.assertTrue(_AppInsightdriver.findElement(By.cssSelector(
	 * ".Work-Experience")).isDisplayed()); List<WebElement> el =
	 * _AppInsightdriver.findElements(By.
	 * xpath("//div[@class='job_insight slides cls_slider']"));
	 * APP_LOGS.debug("Application Insight size:"+el.size());
	 * Assert.assertTrue(el.size()==5, "Application Insights size is: "+el.size());
	 * break; } }
	 * 
	 * }
	 */


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _AppInsightdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_AppInsightdriver!=null)
			_AppInsightdriver.quit();

	}


	/*
	 * GET API RESPONSE
	 */
	public String getInsightAPIData(String keyword){
		try{
			ResteasyClient client = new ResteasyClientBuilder().build();
			String api_url = baseUrl+"/api/v2/search/application-insights/?q="+keyword;
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
			String apiResponse = response.readEntity(String.class);
			APP_LOGS.info("API Response: " + apiResponse);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			response.close(); 
			client.close();
			return apiResponse;
		}
		catch (Exception e) {
			APP_LOGS.fatal("Api comunication error: "+e.getMessage());
			return "";
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
			APP_LOGS.info("API Data: " + data);
		}
		catch(Throwable t){
			t.printStackTrace();	
		}
		return data;	

	}


}
