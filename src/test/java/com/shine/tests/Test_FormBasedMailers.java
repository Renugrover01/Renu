package com.shine.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;
import com.shine.common.utils.GenerateAutoLoginToken;

public class Test_FormBasedMailers extends TestBaseSetup{

	WebDriver _FormBasedMailersDriver;
	WebDriverWait _Wait;
	public String autoLoginToken = "";
	String form_based_mailer_url = "";
	String form_based_mailer_url_raw = "";

	@BeforeClass
	public void TestSetup() {
		_FormBasedMailersDriver = getDriver(_FormBasedMailersDriver);
		_Wait = new WebDriverWait(_FormBasedMailersDriver, 15);
		GenerateAutoLoginToken _GenerateAutoLoginToken = new GenerateAutoLoginToken();
		autoLoginToken = _GenerateAutoLoginToken.getToken(email_new, _FormBasedMailersDriver);
		APP_LOGS.debug("User Auto Loggedin Token: "+autoLoginToken);
		_FormBasedMailersDriver.get(baseUrl+"/myshine/logout/");
		_Utility.Thread_Sleep(1000);
		loggedInShine(_FormBasedMailersDriver, email_new, pass_new);
		_Utility.Thread_Sleep(1000);
		String uid = _Utility.get_user_uid(_FormBasedMailersDriver);
		form_based_mailer_url = baseUrl+"/myshine/login/?tc="+autoLoginToken+"%3D&next=%2Fcandidate%2Frevival%2Fupdateinfo%2F5a2838ca80cf2c39a5edcd34%2F%3Fis_autosubmit%3DTrue&salary_in_lakh=&experience_in_years=9&job_title=QA+Manager&company_name=HCL+Technologies&start_year=2015&start_month=3&end_year=2018&end_month=6&industry_id=&sub_field=&utm_source=gmail%26utm_medium%3Demail&etm_content=5a2838ca80cf2c39a5edcd34%7C%7C2018-07-19T11%3A50%3A10.650924%7CpdghXX0G2iKafySwUTiIrLeSqMfid%2Fh3IDLrYmPnI38%3D%7C&utm_campaign=formMailer&utm_content=FormBravo";
		form_based_mailer_url_raw = baseUrl+"/myshine/login/?tc="+autoLoginToken+"&next=/candidate/revival/updateinfo/"+uid+"/?is_autosubmit=True";
		APP_LOGS.debug("Form Based Mailer Link: "+form_based_mailer_url);
		APP_LOGS.debug("Form Based Mailer Link Raw: "+form_based_mailer_url_raw);

	}

	@Test(priority=0)
	public void verify_form_based_mailer_validation() {
		_FormBasedMailersDriver.navigate().to(form_based_mailer_url_raw);
		_Utility.Thread_Sleep(500);
		String vl = _FormBasedMailersDriver.findElement(By.cssSelector(".selectdiv")).getCssValue("color");
		APP_LOGS.debug(vl);
	}
	
	
	@Test(priority=1)
	public void verify_submit_form_based_mailer() {

	}
	
	
	@Test(priority=2)
	public void verify_validation() {

	}



	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _FormBasedMailersDriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_FormBasedMailersDriver!=null)
			_FormBasedMailersDriver.quit();

	}

}
