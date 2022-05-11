package com.shine.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shine.base.TestBaseSetup;

public class Common  extends TestBaseSetup{
	
	WebDriver _Driver;
	WebDriverWait _Wait;

	public Common(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}

}
