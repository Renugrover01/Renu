package com.shine.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.shine.base.TestBaseSetup;

public class Test_ResumeFileValidation extends TestBaseSetup {

	WebDriver _ResumeTestDriver;
	WebDriverWait _Wait;
	By resumeUpload    			= By.cssSelector("#id_resume");
	By resumeUploadNew 			= By.xpath("//a[contains(text(),'Upload new resume')]");
	By resumeFile      			= By.id("id_file");
	By resumeSubmit    			= By.cssSelector("input.submitred.resumeupload");
	By errorMessage    			= By.cssSelector(".error_text.floatleft");

	@BeforeClass
	public void TestSetup() {
		_ResumeTestDriver = getDriver(_ResumeTestDriver);
		loggedInShine(_ResumeTestDriver, email_main, pass_new);
		_Utility.clickOnNotification(_ResumeTestDriver);
		_Wait = new WebDriverWait(_ResumeTestDriver, 20);
		_Utility.Thread_Sleep(2000);
		_ResumeTestDriver.get(baseUrl+"/myshine/myprofile/");
	}



	@Test (priority =0)
	public void upload_resume_greater_than_5mb() {
		//upload correct resume
		moreThan5ResumeHack();
		upload_resume("resume_file_greater_5mb");
		_Utility.Thread_Sleep(3000);
		String errormsg = _ResumeTestDriver.findElement(By.cssSelector("[for='id_file']")).getText();
		Assert.assertEquals(errormsg, "File size should be less than or equal to 5MB.");

	}
	
	
	@Test (priority =1)
	public void upload_excel_file() {
		_ResumeTestDriver.navigate().refresh();
		_Utility.Thread_Sleep(2000);
		upload_resume("excel");
		_Utility.Thread_Sleep(1000);
		String errormsg = _ResumeTestDriver.findElement(By.cssSelector("[for='id_file']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =2)
	public void upload_html_file() {
		_ResumeTestDriver.navigate().refresh();
		upload_resume("htmlfile");
		_Utility.Thread_Sleep(1000);
		String errormsg = _ResumeTestDriver.findElement(By.cssSelector("[for='id_file']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =3)
	public void upload_jpg_image_file() {
		_ResumeTestDriver.navigate().refresh();
		upload_resume("imagefile");
		_Utility.Thread_Sleep(1000);
		String errormsg = _ResumeTestDriver.findElement(By.cssSelector("[for='id_file']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}
	
	@Test (priority =4)
	public void upload_file_without_extension() {
		_ResumeTestDriver.navigate().refresh();
		upload_resume("no_extension");
		_Utility.Thread_Sleep(1000);
		String errormsg = _ResumeTestDriver.findElement(By.cssSelector("[for='id_file']")).getText();
		Assert.assertEquals(errormsg, "Upload only a doc, docx, pdf, csv, txt, rtf file.");

	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _ResumeTestDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_ResumeTestDriver!=null)
			_ResumeTestDriver.quit();

	}



	/**
	 * Upload resume
	 * @param file_name
	 */
	public void upload_resume(String file_name) {
		_Utility.scrollTOElement(resumeUpload, _ResumeTestDriver);
		WebDriverWait _Wait = new WebDriverWait(_ResumeTestDriver, 30);	
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(resumeUploadNew));
		_Wait.until(ExpectedConditions.elementToBeClickable(resumeUploadNew));
		_ResumeTestDriver.findElement(resumeUploadNew).click();
		//upload incorrect resume
		_ResumeTestDriver.findElement(resumeFile).clear();
		JavascriptExecutor jse = (JavascriptExecutor)_ResumeTestDriver;
		jse.executeScript("document.getElementsByClassName('cls_fileName filename')[0].textContent = '"+file_name+"'");
		_ResumeTestDriver.findElement(resumeFile).sendKeys(userDirectory+CONFIG.getProperty(file_name));
		_Utility.Thread_Sleep(2000);
		_ResumeTestDriver.findElement(resumeSubmit).click();
	}


	private void moreThan5ResumeHack() {

		List<WebElement> resumeList = _ResumeTestDriver.findElements(By.cssSelector(".cls_resume_list em a"));
		if(resumeList.size()>=5) {
			try {
				_ResumeTestDriver.findElement(By.cssSelector(".okbutton.cls_deleteresumefromupload")).click();
				List<WebElement> deleteResumeList = _ResumeTestDriver.findElements(By.cssSelector(".cls_resume_list  span.cross"));
				for (WebElement resume: deleteResumeList) {
					resume.click();
					_Utility.Thread_Sleep(2000);
					_ResumeTestDriver.findElement(By.id("id_cpSubmit")).click();
					_Utility.Thread_Sleep(8000);
				}
			}catch(Throwable t) {
				APP_LOGS.debug("User has resume below maximum limit of 5");
			}
		}
	}





}
