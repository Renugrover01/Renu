package com.shine.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.shine.tests.Test_Registration;
import com.shine.tests.Test_MidoutRegistration;

import com.shine.base.TestBaseSetup;

public class Test_RegistrationSem extends TestBaseSetup {

	static String emailidsem[];
	WebDriver _Regsemdriver;
	static WebDriverWait wait;


	public By pfIndSubDropDown(int i){
		return By.id("id_industry_"+i);
	}


	@BeforeClass
	public void TestSetup(){
		_Regsemdriver = getDriver(_Regsemdriver);
		OpenBaseUrl(_Regsemdriver);
		_Utility.clickOnNotification(_Regsemdriver);
		wait = new WebDriverWait(_Regsemdriver, 15);
		APP_LOGS.debug("Start of SEM registration tests");
		APP_LOGS.debug("SEM registration URL is "+baseUrl+"/sem/registration/");
		_Regsemdriver.navigate().to(baseUrl+"/sem/registration/");
		_Utility.clickOnNotification(_Regsemdriver);
	}


	@Test (priority=0)
	public void test_RegistrationSem_Step1() {
		emailidsem = Test_Registration.ExecuteRegistrationFlow(true, "/registration/parser/flow-2/", _Regsemdriver);
		_Regsemdriver.findElement(Test_Registration.submitFirstRegsitartionForm).click();
	}


	@Test (priority=1)
	public void test_RegistrationSem_Step2() throws Exception{
		Test_Registration.executeTestFlow(false, false, 2, emailidsem[0], _Regsemdriver);
		_Utility.Thread_Sleep(1000);
	}


	@Test (priority=2)
	public void test_Midout_UploadResume() {
		Test_MidoutRegistration.testMidoutUploadResume(_Regsemdriver);
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Regsemdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Regsemdriver!=null)
			_Regsemdriver.quit();

	}



}
