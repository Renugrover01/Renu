package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_AutoSuggester extends TestBaseSetup{

	WebDriver driver;
	WebDriverWait wait;
	String BaseURL;

	By searchKeyword = By.id("id_q");
	By locationFIeld = By.id("id_loc");
	
	By suggesterList = By.cssSelector("[class='ui-corner-all']");

	@BeforeClass(alwaysRun=true)
	public void testSetup(){
		driver = getDriver(driver);
		wait = new WebDriverWait(driver,30);
		driver.get(baseUrl);
		_Utility.Thread_Sleep(2000);
		loggedInShine(driver, email_new, pass_new);
		_Utility.Thread_Sleep(2000);

	}


	@Test(priority=2)
	public void selectOptionWithText() {
		search_keyword("Java");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggesterList));
		List<WebElement> list = driver.findElements(suggesterList);
		APP_LOGS.debug("Auto Suggest List ::" + list.size());
		for(int i = 0 ;i< list.size();i++)
		{
			APP_LOGS.debug(list.get(i).getText());

		}
		assertTrue(list.size()>0, "List size found: "+list.size());

	}		


	@Test(priority=3)
	public void test_SkillSelector(){
		//To select Java
		selectSuggester("Java");
		//To select another skill 
		selectSuggester("Maven");
		_Utility.Thread_Sleep(2000);
		String actualKeyword = driver.findElement(searchKeyword).getAttribute("value").trim();
		assertEquals(actualKeyword, "Java, Maven,");

	}
	
	
	

	@AfterMethod(alwaysRun=true)
	public void take_screenshot(ITestResult _ITestResult){
		takeScreenshotOnFailure(_ITestResult, driver);
	}

	@AfterClass(alwaysRun=true)
	public void closeBrowser() {
		if(driver!=null)
			driver.quit();
	}
	
	

	/**
	 * To select skill from suggester
	 * @param keyword
	 */
	public void selectSuggester(String keyword) {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggesterList));
		List<WebElement> list2 = driver.findElements(suggesterList);
		for(int j = 0 ;j< list2.size();j++)
		{
			if(list2.get(j).getText().equalsIgnoreCase(keyword))
			{
				list2.get(j).click();
				break;
			}
		}
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void search_keyword(String keyword){
		//To click on search bar
		driver.findElement(By.id("id_searchBase")).click();
 		//To send initials
		driver.findElement(By.id("id_q")).click();
		driver.findElement(By.id("id_q")).sendKeys(keyword);
		_Utility.Thread_Sleep(2000);


 	}


}




