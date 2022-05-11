package com.shine.tests.utility;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CreateAutoLoginHTML;
import com.shine.common.utils.GenerateAutoLoginToken;
import com.shine.emailer.AutoLoginTokenEmail;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_SendTokenViaEmail extends TestBaseSetup{




	WebDriver _Driver = null;
	GenerateAutoLoginToken _GenerateAutoLoginToken;


	@BeforeClass
	public void TestSetup() {
		_Driver = getDriver(_Driver);
		_GenerateAutoLoginToken = new GenerateAutoLoginToken();
		_Driver.get(baseUrl);
	}


	@Test(priority =1)
	public void getURL(){
		String token = _GenerateAutoLoginToken.getToken(email_new, _Driver);
		if(!token.isEmpty()) {
			CreateAutoLoginHTML _CreateAutoLoginHTML = new CreateAutoLoginHTML();
			_CreateAutoLoginHTML.create_html_report(token);
			AutoLoginTokenEmail _AutoLoginTokenEmail = new AutoLoginTokenEmail();
			try {
				_AutoLoginTokenEmail.send_email();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Driver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Driver!=null)
			_Driver.quit();

	}

}
