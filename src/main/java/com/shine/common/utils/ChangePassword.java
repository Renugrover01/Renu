package com.shine.common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.GenerateAutoLoginToken;

public class ChangePassword extends TestBaseSetup{
	WebDriver _ChangePasswordDriver;
	
	By emailTxtField	= By.id("id_email");
	By passwordTxtField = By.id("id_password");
	By looginPopLink	= By.name("btn_homepage_signin");
	GenerateAutoLoginToken _GenerateAutoLoginToken = new GenerateAutoLoginToken();
	
	
	/**
	 * 
	 * @param userEmailid
	 */
	public void resetPasswordRequest(String userEmailid) {
		_ChangePasswordDriver = getDriver(_ChangePasswordDriver,"chromeweb");
		String token = _GenerateAutoLoginToken.getToken(userEmailid, _ChangePasswordDriver);
		try {
			token = token.replaceAll("\\?tc=", "");
		}catch(Throwable t) {
			APP_LOGS.debug("?tc= not found in url");
		}
		System.out.println(token);
		changePassword(token, _ChangePasswordDriver);
		_Utility.Thread_Sleep(2000);
		_ChangePasswordDriver.close();
	}

	
	private void changePassword(String token, WebDriver _ChangePasswordDriver) {
		_ChangePasswordDriver.navigate().to(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(1000);
		_ChangePasswordDriver.get(baseUrl+"/change/password/?tc="+token);
		_Utility.Thread_Sleep(1000);
		_ChangePasswordDriver.findElement(By.id("id_new_password")).sendKeys("123456");
		_ChangePasswordDriver.findElement(By.id("id_confirm_password")).sendKeys("123456");
		_ChangePasswordDriver.findElement(By.id("btn_password")).click();
		_Utility.Accept_Alert(_ChangePasswordDriver);		
	}


	
	
}
