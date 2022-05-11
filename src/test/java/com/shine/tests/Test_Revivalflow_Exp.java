package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.shine.base.TestBaseSetup;

public class Test_Revivalflow_Exp extends TestBaseSetup{

	WebDriver _RevivalFlowdriver;
	WebDriverWait _Wait;
	Gson _Gson = null;
	String waterMark = "";
	String uid = "";
	int current_exp_in_year;
	int current_exp_in_month;
	int current_salary_in_lakh;
	int current_salary_in_thou;
	
	By expDD				 = By.xpath("(//*[@id='exp-years-id-revival-flow'])");
	By expDD_value           = By.id("item-key-4");
	By exp_monthDD           = By.id("exp-months-id-revival-flow");
	By exp_monthDD_value     = By.id("item-key-6");
	By salary_lakh           = By.id("salary-lakh-id-revivalflow");
	By salary_lakh_value     = By.id("item-key-6");
	By salary_thousand       = By.id("salary-thousand-id-revivalflow");
	By salary_thousand_value = By.id("item-key-6");
	By resumeFile            = By.id("id_file");


	@BeforeClass
	public void TestSetup() {
		_Gson = new Gson();
		waterMark = _Utility.get_random_string();
		_RevivalFlowdriver = getDriver(_RevivalFlowdriver);
		loggedInShine(_RevivalFlowdriver, email_hc, pass_new);
		_Utility.Thread_Sleep(2000);
		_Utility.clickOnNotification(_RevivalFlowdriver);
		_Wait = new WebDriverWait(_RevivalFlowdriver, 20);
		_Gson = new Gson();
		JavascriptExecutor jse = (JavascriptExecutor) _RevivalFlowdriver;
		try {
			uid = (String) jse.executeScript("return sc.UID");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test (priority =1)
	public void open_revival_flow_url () {
		_RevivalFlowdriver.get(baseUrl+"/myshine/home/?utm_content=revival");
		_Utility.Thread_Sleep(2000);
		String popup_title = _RevivalFlowdriver.findElement(By.id("testing-id-welcome-div")).getText();
		assertTrue(popup_title.contains("Welcome"));
	}
	
	@Test (priority =2)
	public void fill_exp_details() {
		_Utility.Thread_Sleep(1000);
		
		_RevivalFlowdriver.findElement(expDD).click();
		_Utility.Thread_Sleep(1000);
		String previous_exp = _RevivalFlowdriver.findElement(expDD_value).getText();
		 int exp_value = Integer.parseInt(previous_exp)+1;
		 
		_RevivalFlowdriver.findElement(expDD_value).click();
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(exp_monthDD).click();
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(exp_monthDD_value).click();
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(salary_lakh).click();
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(salary_lakh_value).click();
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(salary_thousand).click();
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(salary_thousand_value).click();
		_Utility.Thread_Sleep(1000);
		 clickOnNextButton(_RevivalFlowdriver);
	}
	
	@Test (priority =3)
	public void fill_skill_details() {
		_Utility.Thread_Sleep(1000);
		_RevivalFlowdriver.findElement(By.className("skip-revival")).click();
	}
	
	@Test (priority =4)
	public void upload_resume_details(String filename) {
		_RevivalFlowdriver.findElement(resumeFile).sendKeys(System.getProperty("user.dir")+CONFIG.getProperty(filename));
		_Utility.Thread_Sleep(3000);
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

}
