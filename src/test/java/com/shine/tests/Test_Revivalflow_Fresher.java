package com.shine.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.shine.base.TestBaseSetup;

public class Test_Revivalflow_Fresher extends TestBaseSetup{

	WebDriver _RevivalFlowdriver;
	WebDriverWait _Wait;
	
	static By fresher					= By.id("id_revival_fresher");
	static By resumeFile                = By.id("id_file");
    static By error_msg                 = By.className("error-msg-revival");

    @BeforeClass(alwaysRun=true)
        public void TestSetup() {
		_RevivalFlowdriver = getDriver(_RevivalFlowdriver);
		loggedInShine(_RevivalFlowdriver, "revivalfresher@mailinator.com", "123456");
		_Utility.Thread_Sleep(2000);
		_Utility.clickOnNotification(_RevivalFlowdriver);
		_Wait = new WebDriverWait(_RevivalFlowdriver, 20);

	}

	@Test (priority =1)
	public void open_revival_flow_url () {
		_RevivalFlowdriver.get(baseUrl+"/myshine/home/?utm_content=revival");
		_Utility.Thread_Sleep(1000);
	}
	
	@Test (priority =2)
	public void fill_exp_details() {
		_Utility.Thread_Sleep(1000);		
		_RevivalFlowdriver.findElement(fresher).click();
		 clickOnNextButton(_RevivalFlowdriver);
	}
	
	@Test (priority =3)
	public void fill_skill_details() {
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(By.className("skip-revival")).click();
		
	}
	
	@Test (priority =4)
	public void resumeFileUpload(WebDriver miduploaddriver) {
		resumeFileUpload(miduploaddriver, "resumefile");
		_Utility.Thread_Sleep(3000);
	}
	
	
	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_RevivalFlowdriver!=null)
			_RevivalFlowdriver.quit();

	}

	
	static By nextButton = By.cssSelector(".btn_next");

	/**
	 * 
	 * @param driver
	 */
	public static void clickOnNextButton(WebDriver driver) {
		try {
			List<WebElement> crossButtons = driver.findElements(nextButton);
			for(WebElement button : crossButtons) {
				if(button.isDisplayed()) {
					button.click();
					break;
				}
			}
		} catch (Throwable e) {
			APP_LOGS.error(e.getMessage());
		}
	}
	
	public static void resumeFileUpload(WebDriver resuploaddriver, String filename) {
		resuploaddriver.findElement(resumeFile).sendKeys(System.getProperty("user.dir")+CONFIG.getProperty(filename));
		_Utility.Thread_Sleep(3000);
	}

}
