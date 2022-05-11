package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test_AccountSettings extends TestBaseSetup{

	static WebDriver  _SettingDriver;
	static WebDriverWait _Wait;

	By moreMenuLink			= By.xpath("//*[@class='spanName']");
	By desiredJobLink		= By.linkText("Account Settings");
	By acntSettHeadTitle	= By.id("id-privacy-status-title");
	By accountSettingMenu	= By.cssSelector(".dropdown.posRel.infomenu.carrerInfo.lgout .spanName");
	By accountSettingsLink	= By.linkText("Account Settings");
	By changepasswordlink	= By.name("link_changepsw");
	By old_password_txt		= By.id("id_old_password");
	By new_password_txt		= By.id("id_new_password");
	By confirm_password_txt = By.id("id_confirm_password");
	By submit_change_password_btn = By.id("id_cpSubmit");
	By old_password_val		= By.cssSelector("[for='id_old_password']");
	By new_password_val		= By.cssSelector("[for='id_new_password']");
	By confirm_password_val = By.cssSelector("[for='id_confirm_password']");
	By same_pwd_val_error 	= By.xpath("(//*[@class='error'])[1]");
	By cancel_button 		= By.id("ui-id-1");

	
	@BeforeClass
	public void TestSetup() {
		_SettingDriver = getDriver(_SettingDriver);
		OpenBaseUrl(_SettingDriver);
		loggedInShine(_SettingDriver, email_new, pass_new);	
		_Wait = new WebDriverWait(_SettingDriver, 20);
		_Utility.Thread_Sleep(500);
	}


	@Test (priority=0)
	public void test_OpenAccountSettings(){
		_Utility.openMenuLink(moreMenuLink, desiredJobLink, _SettingDriver);
		Assert.assertEquals(_SettingDriver.findElements(acntSettHeadTitle).get(0).getText(), "Privacy Status and Email Subscription Status");
	}
	

	@Test (priority=1)
	public void verify_change_password_button(){
		_Utility.Thread_Sleep(500);
		_SettingDriver.findElement(changepasswordlink).click();
		_Utility.Thread_Sleep(1000);
		String popup_text = _SettingDriver.findElement(By.cssSelector("[id='ui-id-1']")).getText();
		assertEquals(popup_text, "Change Password");
	}
	
	@Test (priority=2, dependsOnMethods= {"verify_change_password_button"})
	public void verify_new_password_validation(){
		_SettingDriver.findElement(submit_change_password_btn).click();
		_Utility.Thread_Sleep(500);
		assertEquals(_SettingDriver.findElement(By.xpath("(//*[@class='error'])[1]")).getText(), "Password is required");
	}
	
	@Test (priority=3, dependsOnMethods= {"verify_change_password_button"})
	public void verify_confirm_password_validation(){
		assertEquals(_SettingDriver.findElement(By.xpath("(//*[@class='error'])[1]")).getText(), "Password is required");
	}
	
	@Test (priority=4, dependsOnMethods= {"verify_change_password_button"})
	public void verify_old_password_validation(){
		assertEquals(_SettingDriver.findElement(By.xpath("(//*[@class='error'])[1]")).getText(), "Password is required");
	}
	
	
	@Test (priority=5, dependsOnMethods= {"verify_change_password_button"})
	public void verify_change_password(){
		_SettingDriver.findElement(old_password_txt).sendKeys(pass_new);
		_SettingDriver.findElement(new_password_txt).sendKeys("123456");
		_SettingDriver.findElement(confirm_password_txt).sendKeys("123456");
		_SettingDriver.findElement(submit_change_password_btn).click();
		_Utility.Thread_Sleep(2000);
		assertEquals(_SettingDriver.findElement(same_pwd_val_error).getText(), "New password must be different from old password");
		_Utility.Thread_Sleep(1000);
		_SettingDriver.findElement(cancel_button).click();
		_Utility.Thread_Sleep(1000);
	}
	
	@Test (priority=6)
	public void test_OpenAccountSettings_URL (){
		_SettingDriver.navigate().refresh();
		_Utility.Thread_Sleep(1000);
		_Utility.openMenuLink(accountSettingMenu, accountSettingsLink, _SettingDriver);
		_Utility.Thread_Sleep(1000);
		Assert.assertEquals(_SettingDriver.findElements(acntSettHeadTitle).get(0).getText(), "Privacy Status and Email Subscription Status");
	}

	@Test (priority=7)
	public void verify_edit_profile_status(){
		_Utility.Thread_Sleep(500);
		_SettingDriver.findElement(By.cssSelector("[class='edit']")).click();
		_Utility.Thread_Sleep(1000);
		Assert.assertTrue(_SettingDriver.findElement(By.cssSelector(".accountSetting__lists")).isDisplayed());
		_Utility.Thread_Sleep(500);
		_SettingDriver.findElement(By.id("id-edit-privacy-save")).click();
		
	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _SettingDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_SettingDriver!=null)
			_SettingDriver.quit();

	}

}
