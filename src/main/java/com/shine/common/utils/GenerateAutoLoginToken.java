package com.shine.common.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shine.base.TestBaseSetup;

public class GenerateAutoLoginToken extends TestBaseSetup{

	By emailTxtField	= By.id("id_email");
	By passwordTxtField = By.id("id_password");
	By looginPopLink	= By.name("btn_homepage_signin");

	/**
	 * 
	 * @param userEmailid
	 * @return
	 */
	public String getToken(String userEmailid, WebDriver _ChangePasswordDriver) {
		loginInShineWeb(email_new, pass_new, _ChangePasswordDriver);
		_Utility.Thread_Sleep(2000);
		_ChangePasswordDriver.navigate().to(baseUrl+"/myshine/admin/auto-login");
		_Utility.Thread_Sleep(2000);
		APP_LOGS.debug(userEmailid);
		_ChangePasswordDriver.findElement(By.id("id_Email")).sendKeys(userEmailid);
		_ChangePasswordDriver.findElement(By.xpath("//*[@type='submit']")).click();
		String url = _ChangePasswordDriver.findElement(By.tagName("body")).getText();
		APP_LOGS.debug(url);
		String token = StringUtils.substringAfter(url, "/login/").trim();
		try {
			token = token.replaceAll("\\?tc=", "");
		}catch(Throwable t) {
			APP_LOGS.debug("?tc= not found in url");
		}
		APP_LOGS.debug(token);
		return token;

	}


	/**
	 * 
	 * @param userEmailid
	 * @param _WebLoginDriver
	 */
	public void loginInShineWeb(String userEmailid, String userPass, WebDriver _WebLoginDriver){
		_WebLoginDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		_WebLoginDriver.navigate().to(baseUrl+"/myshine/login/");
		_Utility.Thread_Sleep(2000);
		_WebLoginDriver.findElement(emailTxtField).clear();
		_WebLoginDriver.findElement(emailTxtField).sendKeys(userEmailid);
		_WebLoginDriver.findElement(passwordTxtField).clear();
		_WebLoginDriver.findElement(passwordTxtField).sendKeys(userPass);
		_Utility.Thread_Sleep(2000);
		_WebLoginDriver.findElement(looginPopLink).click();

	}

}
