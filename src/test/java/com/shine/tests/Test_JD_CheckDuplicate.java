package com.shine.tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

public class Test_JD_CheckDuplicate extends TestBaseSetup {

	WebDriver _driver;
	WebDriverWait wait;

	By search = By.id("id_q");
	By enter = By.xpath("//*[@id=\"frm_adv_srch\"]/div[2]/input");
	By selectJob = By.cssSelector("ul li.snp.cls_jobtitle");
	By dupDept = By.cssSelector(".sal_fun_ind ul li:nth-child(1) span");
	By dupInd = By.cssSelector(".sal_fun_ind ul li:nth-child(2) span");
	By dupSkill = By.cssSelector(".sal_fun_ind ul li:nth-child(3) span");

	By otherDetailsSection = By.xpath("//*[contains(text(),'Other details')]");

	@BeforeClass
	public void TestSetup() {
		_driver = getDriver(_driver);
		wait = new WebDriverWait(_driver, 15);
		_driver.get(baseUrl);
	}

	@Test(priority=1, dataProvider="searchdata" )
	public void Search(String searchUrl) {
		_Utility.Thread_Sleep(2000);
		_driver.navigate().to(baseUrl+searchUrl);
		switchToWindow(0);
		//Search and click on JD
		_driver.findElement(selectJob).click();
		_Utility.Thread_Sleep(3000);
		switchToWindow(1);
		_Utility.Thread_Sleep(2000);
		duplicate_dept();
		duplicate_ind();
		duplicate_skill();
		_driver.close();
		switchToWindow(0);
	}



	@DataProvider
	public Object [][] searchdata(){
		Object [][] data= new Object [10][1];
		data[0][0]="/job-search/sales";
		data[1][0]="/job-search/engineering";
		data[2][0]="/job-search/bangalore";
		data[3][0]="/job-search/delhi";
		data[4][0]="/job-search/it";
		data[5][0]="/job-search/software";
		data[6][0]="/job-search/python";
		data[7][0]="/job-search/dubai";
		data[8][0]="/job-search/banking-financial-services";
		data[9][0]="/job-search/bpo";

		return data;

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		//Error Screenshots
		TestBaseSetup.takeScreenshotOnFailure(testResult, _driver);
	}

	@AfterClass
	public void quitbrowser() {
		if(_driver!=null)
			_driver.quit();
	}
	
	
	/**
	 * Duplicate Department
	 */
	public void duplicate_dept(){
		_Utility.scrollTOElement(otherDetailsSection, _driver);
		List<WebElement> deptElementList = _driver.findElements(dupDept);
		verify_duplicate_data(deptElementList);
	}

	/**
	 * Duplicate Industry
	 */
	public void duplicate_ind(){
		//List of Industries
		List<WebElement> indElementList = _driver.findElements(dupInd);
		verify_duplicate_data(indElementList);
	}

	/**
	 * Duplicate Skill
	 */
	public void duplicate_skill() {
		List<WebElement> skillElementList = _driver.findElements(dupSkill);
		String otherSkill = _driver.findElement(By.cssSelector(".sal_fun_ind ul li:nth-child(3) em")).getText();
		System.out.println(otherSkill);
		if(!otherSkill.equalsIgnoreCase("Other Skills:"))
			verify_duplicate_data(skillElementList);
		_Utility.Thread_Sleep(2000);
	}

	
	
	
	/**
	 * Window Handlers
	 * @param index
	 */
	public void switchToWindow(int index) {
		ArrayList<String> newtab = new ArrayList<String> (_driver.getWindowHandles());
		_driver.switchTo().window(newtab.get(index));
		
	}

	/***
	 * Verify Duplicate data
	 * @param ElementList
	 */
	public void verify_duplicate_data(List<WebElement> ElementList) {
		//Print Duplicate entries
		Set<String> uniqueEntry = new LinkedHashSet<>();
		Set<String> duplicateEntry = new LinkedHashSet<>();
		for (WebElement element : ElementList){
			String entry = element.getText().replaceAll(",", "").trim();
			if(!entry.isEmpty()){
				if (!uniqueEntry.contains(entry)){
					uniqueEntry.add(entry);
				}
				else{
					duplicateEntry.add(entry);
				}
			}
		}
		APP_LOGS.debug("Unique:"+uniqueEntry);
		APP_LOGS.debug("Duplicates Entry:"+duplicateEntry);
		assertTrue(duplicateEntry.isEmpty(), "Duplicate entry found: "+duplicateEntry);
	}
}
