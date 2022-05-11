package com.shine.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shine.base.TestBaseSetup;

public class Resume extends TestBaseSetup{

	WebDriver _Driver;
	WebDriverWait _Wait;

	public Resume(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}

	public void open_sem_registration_page_url() {
		_Driver.navigate().to(baseUrl+"/upload/resume/");
	}

	@FindBy(id="id_file")
	WebElement RESUME_UPLOAD_FILE_ID;
	
	
	public void upload_resume(String filename) {
		_Utility.Thread_Sleep(2000);
		String file_path = System.getProperty("user.dir")+CONFIG.getProperty(filename);
		APP_LOGS.debug("Uploading resume: "+file_path);
		RESUME_UPLOAD_FILE_ID.sendKeys(file_path);
		_Utility.Thread_Sleep(2000);
	}
	
}
