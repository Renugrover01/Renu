package com.shine.tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_Robots extends TestBaseSetup{

	WebDriver _Robotdriver;

	By robotTxt  = By.cssSelector("html>body>pre");
	By keyword   = By.id("id_q");
	By searchBtn = By.cssSelector("input.cls_btn_reg_job_srch.cls_searchbtnabv");
	By applyBtn  = By.linkText("Apply");


	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the robots test");
		_Robotdriver = getDriver(_Robotdriver);
	}

	@Test (priority =3, dataProvider="robotdata")
	public void testRobots (String url,String robotinstruction){
		_Robotdriver.navigate().to(url);
		String mapirobot=_Robotdriver.findElement(robotTxt).getText();
		APP_LOGS.debug("URL is.."+url);
		APP_LOGS.debug("robots text is  "+mapirobot);
		Assert.assertEquals(mapirobot,robotinstruction);
		_Utility.Thread_Sleep(1000);

	}

	@DataProvider
	public Object [][] robotdata(){
		Object [][] data= new Object [3][2];		

		data[0][0]="https://mapi.shine.com/robots.txt";
		data[0][1]="User-agent: *"+'\n'+'\n'+"Disallow: /myshine/"+'\n'+"Allow: /myshine/login/"+'\n'+'\n'+"Disallow: /1198510/"+'\n'+"Disallow: /*?*vendorid=*"+'\n'+"Disallow: /featuredcompanystats/"+'\n'+"Disallow: /lookup/"+'\n'+"Disallow: /job-search/similar/"+'\n'+"Disallow: /unsubscribe/"+'\n'+"Disallow: */?akamai_redirect=1*"+'\n'+'\n'+"User-agent: Scrapy"+'\n'+"Disallow: /";		

		data[1][0]="https://www.shine.com/robots.txt";
		data[1][1]="User-agent: *"+'\n'+'\n'+"Disallow: /myshine/"+'\n'+"Allow: /myshine/login/"+'\n'+'\n'+"Disallow: /1198510/"+'\n'+"Disallow: /*?*vendorid=*"+'\n'+"Disallow: /featuredcompanystats/"+'\n'+"Disallow: /lookup/"+'\n'+"Disallow: /job-search/similar/"+'\n'+"Disallow: /unsubscribe/"+'\n'+"Disallow: */?akamai_redirect=1*"+'\n'+'\n'+"User-agent: Scrapy"+'\n'+"Disallow: /";

		data[2][0]="https://one.shine.com/robots.txt";
		data[2][1]="User-agent: *"+'\n'+'\n'+"Disallow: /myshine/"+'\n'+"Allow: /myshine/login/"+'\n'+'\n'+"Disallow: /1198510/"+'\n'+"Disallow: /*?*vendorid=*"+'\n'+"Disallow: /featuredcompanystats/"+'\n'+"Disallow: /lookup/"+'\n'+"Disallow: /job-search/similar/"+'\n'+"Disallow: /unsubscribe/"+'\n'+"Disallow: */?akamai_redirect=1*"+'\n'+'\n'+"User-agent: Scrapy"+'\n'+"Disallow: /";

		return data;

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Robotdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Robotdriver!=null)
			_Robotdriver.quit();

	}
}
