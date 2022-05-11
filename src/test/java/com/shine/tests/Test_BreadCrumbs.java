package com.shine.tests;

import static org.testng.Assert.assertEquals;
import java.util.List;
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
import com.shine.common.utils.ExcelReader;

public class Test_BreadCrumbs extends TestBaseSetup{

	WebDriver _BreadCrumbsDriver;



	@BeforeClass
	public void TestSetup() {
		APP_LOGS.debug("Starting the jsrp jd test");
		_BreadCrumbsDriver = getDriver(_BreadCrumbsDriver);
		_Utility.Thread_Sleep(1000);
	}

	@Test(priority=0, dataProvider="getBreadCrumbs")
	public void open_search_page(String search_params, String pattern1, String pattern2) {
		_BreadCrumbsDriver.get(baseUrl+search_params);
		_Utility.Thread_Sleep(1000);
		List<WebElement> breadCrumbList = _BreadCrumbsDriver.findElements(By.cssSelector("[itemprop=\"name\"]"));
		System.out.println(breadCrumbList.size());
		assertEquals(breadCrumbList.get(0).getText().trim(), pattern1);
		assertEquals(breadCrumbList.get(1).getText().trim(), pattern2);
	//	assertEquals(breadCrumbList.get(2).getText().trim(), pattern3);
	//	assertEquals(breadCrumbList.get(3).getText().trim(), pattern4);

	}


	@DataProvider(name="getBreadCrumbs")
	public Object[][] getBreadCrumbs() {
		ExcelReader excelReader = new ExcelReader();
		Object[][] arrayObject = excelReader.getExcelData(userDirectory+"/src/test/resources/data/breadcrumb.xls","breadcrumb");
		return arrayObject;
	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _BreadCrumbsDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_BreadCrumbsDriver!=null)
			_BreadCrumbsDriver.quit();

	}

}
