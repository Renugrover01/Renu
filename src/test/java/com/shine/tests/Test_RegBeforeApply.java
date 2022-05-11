package com.shine.tests;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.shine.tests.Test_JobApply;
import com.shine.tests.Test_MidoutRegistration;
import com.shine.tests.Test_Registration;
import com.shine.tests.Test_Search;
import com.shine.base.TestBaseSetup;


public class Test_RegBeforeApply extends TestBaseSetup{

	WebDriver _Regbapplydriver;
	WebDriverWait _Wait;
	static boolean isFresher;
	static int flowFlag = 0;

	By registerToApplyBtn  = By.linkText("Register to Apply");
	By registerToApplyBtn2 = By.xpath("//a[starts-with(@id,'register_')]");
	By jobApplyMessage     = By.cssSelector("#id_jobApplyMsg");
	By continueTojob       = By.cssSelector(".cls_continueToJob");
	By applyWithoutReg     = By.cssSelector(".similar.cls_applyFromJd.cls_call_dialogbox");
	By facebookApplyBtn    = By.cssSelector("#id_FacebookApply strong");
	By linkedinApplyBtn	   = By.linkText("Apply with LinkedIn");
	By applyBtn            = By.cssSelector("div.applybuttonparichay");
	By registerNow         = By.linkText("Register now");

	@BeforeMethod
	public void TestSetup() throws Exception {
		APP_LOGS.debug("Starting the reg before apply test");
		_Regbapplydriver = getDriver(_Regbapplydriver);
		_Wait = new WebDriverWait(_Regbapplydriver, 15);
		OpenBaseUrl(_Regbapplydriver);
	}


	/*
	 * @Test(priority=3) public void testRegisterBeforeApplyFromJD() {
	 * Test_JobApply.openjdpage(_Regbapplydriver); _Utility.Thread_Sleep(3000);
	 * _Utility.clickOnNotification(_Regbapplydriver);
	 * clickOnApplyBtnJD(_Regbapplydriver, true); _Utility.Thread_Sleep(3000);
	 * test_Registration_Step_One(_Regbapplydriver);
	 * test_Registration_Step_two(_Regbapplydriver);
	 * Test_MidoutRegistration.resumeFileUpload(_Regbapplydriver, "resumefile");
	 * _Utility.Thread_Sleep(4000); checkRegSuccessMsg(_Regbapplydriver);
	 * _Regbapplydriver.findElement(continueTojob).click(); }
	 */

	//@Test(priority=5)
	public void testFBapplyfromJD()
	{		
		if(isPreprod==false){
			Test_JobApply.openjdpage(_Regbapplydriver);

			clickOnApplyBtnJD(_Regbapplydriver, false);
			checkfbfromModal();
		}
	}

	/*
	 * @Test(priority=7) public void testLinkedinapplyfromJD() {
	 * Test_JobApply.openjdpage(_Regbapplydriver);
	 * _Utility.clickOnNotification(_Regbapplydriver);
	 * clickOnApplyBtnJD(_Regbapplydriver, false); 
	 * checklinkedinfromModal(); }
	 */


	//@Test(priority=9)
	public void testFBapplyJSRP()
	{
		if(isPreprod==false){
			Test_Search.simpleSearch("ht media", _Regbapplydriver);
			_Utility.clickOnNotification(_Regbapplydriver);
			_Utility.Thread_Sleep(3000);
			List <WebElement> apply1 = _Regbapplydriver.findElements(applyBtn);
			apply1.get(1).click();
			checkfbfromModal();
		}
	}


	/*
	 * @Test(priority=11) public void testLinkedinapplyJSRP() {
	 * Test_Search.simpleSearch("ht media", _Regbapplydriver);
	 * _Utility.clickOnNotification(_Regbapplydriver); _Utility.Thread_Sleep(3000);
	 * List <WebElement> apply1 = _Regbapplydriver.findElements(applyBtn);
	 * apply1.get(0).click(); checklinkedinfromModal(); }
	 */

	@Test(priority=13)
	public void testApplyRegisterfromJSRP() {
		Test_Search.simpleSearch("ht media", _Regbapplydriver);
		_Utility.clickOnNotification(_Regbapplydriver);
		_Utility.Thread_Sleep(3000);
		List <WebElement> apply1 = _Regbapplydriver.findElements(applyBtn);
		apply1.get(1).click();
		_Regbapplydriver.findElement(registerNow).click();
		_Utility.Thread_Sleep(3000);
		test_Registration_Step_One(_Regbapplydriver);
		test_Registration_Step_two(_Regbapplydriver);
		Test_MidoutRegistration.resumeFileUpload(_Regbapplydriver, "resumefile");
		_Utility.Thread_Sleep(4000);
		try {
		checkRegSuccessMsg(_Regbapplydriver);
		_Regbapplydriver.findElement(continueTojob).click();	
		}catch(Throwable t) {
			_Utility.Thread_Sleep(1000);
			_Regbapplydriver.navigate().back();
			_Utility.Thread_Sleep(2000);
			checkRegSuccessMsg(_Regbapplydriver);
			_Regbapplydriver.findElement(continueTojob).click();	
		}
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Regbapplydriver);
		try{
			if(_Regbapplydriver!=null){
				_Regbapplydriver.close();
			}
		}catch(WebDriverException ex){
			ex.printStackTrace();
		}
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Regbapplydriver!=null)
			_Regbapplydriver.quit();

	}

	/*Assertion CHeck*/
	public void checkRegSuccessMsg(WebDriver _RegSucessDriver){
		String applymsg= _RegSucessDriver.findElement(jobApplyMessage).getText();
		APP_LOGS.debug("[Test_RegBeforeApply: "+ applymsg+" ]");
		Assert.assertTrue(applymsg.contains("Congratulations"), "Result: "+applymsg);
		Assert.assertTrue(applymsg.contains("you have successfully applied for job!"), "Result: "+applymsg);
	}


	/*
	 * Facebook
	 */
	private void checkfbfromModal() {
		for (String winHandle : _Regbapplydriver.getWindowHandles()) {
			_Utility.Thread_Sleep(1000);
			_Regbapplydriver.switchTo().window(winHandle); 
			// switch focus of WebDriver to the next found window handle (that's your newly opened window)

		}
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(facebookApplyBtn));
		_Regbapplydriver.findElement(facebookApplyBtn).click();
		_Utility.Thread_Sleep(3000);
		int i=0;
		for (String winHandle1 : _Regbapplydriver.getWindowHandles()) {
			_Regbapplydriver.switchTo().window(winHandle1); 
			// switch focus of WebDriver to the next found window handle (that's your newly opened window)
			if(i==0) _Regbapplydriver.close();
			i++;
		}
		_Utility.Thread_Sleep(2000);
		Assert.assertEquals(_Regbapplydriver.getTitle(),"Facebook");
	}

	/*
	 * Linkedin
	 */
	private void checklinkedinfromModal() {
		for (String winHandle : _Regbapplydriver.getWindowHandles()) {
			_Regbapplydriver.switchTo().window(winHandle); 
			//switch focus of WebDriver to the next found window handle (that's your newly opened window)
		}
		_Utility.Thread_Sleep(3000);
		_Regbapplydriver.findElement(linkedinApplyBtn).click();
		_Utility.Thread_Sleep(2000);
		//Assert.assertEquals("Authorize | LinkedIn", _Regbapplydriver.getTitle());
		String actualTile = _Regbapplydriver.getTitle().trim();
		APP_LOGS.debug("Linkedin Title: "+actualTile);
		if(actualTile.contains("LinkedIn Login, LinkedIn Sign in | LinkedIn"))
			Assert.assertEquals(actualTile, "LinkedIn Login, LinkedIn Sign in | LinkedIn");
		if(actualTile.contains("Sign In to LinkedIn"))
			Assert.assertEquals(actualTile, "Sign In to LinkedIn");
		else if(actualTile.contains("Log In to LinkedIn"))
			Assert.assertEquals(actualTile, "Log In to LinkedIn");
		else if(actualTile.contains("Authorize | LinkedIn"))
			Assert.assertEquals(actualTile, "Authorize | LinkedIn");
		else if(actualTile.contains("LinkedIn Login"))
			Assert.assertTrue(actualTile.contains("LinkedIn Login"));
		else 
			Assert.assertTrue(false, "Linkedin Title not found: "+actualTile);
	}

	/**
	 * 
	 * @param _jdApplyDriver
	 * @param clickOnRegisterNow
	 */
	public void clickOnApplyBtnJD(WebDriver _jdApplyDriver, boolean clickOnRegisterNow){
		WebDriverWait _Wait = new WebDriverWait(_jdApplyDriver, 15);
		try {
			_jdApplyDriver.findElement(By.partialLinkText("Apply")).click();
		}
		catch (Throwable t){
			_jdApplyDriver.findElement(By.partialLinkText("Apply on website")).click();

		}
		if(clickOnRegisterNow==true){
			_Wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Register now")));
			_jdApplyDriver.findElement(By.linkText("Register now")).click();
		}
	}


	/**
	 * 
	 * @param _Regbapplydriver
	 */
	private void test_Registration_Step_One(WebDriver _Regbapplydriver) {
		String flowFound = "/registration/parser/flow-2/";
		//_Regbapplydriver.findElement(Test_Registration.regiFormAction).getAttribute("action");
		APP_LOGS.debug("Flow Found: "+flowFound);
		String[] data = Test_Registration.ExecuteRegistrationFlow(false, flowFound, _Regbapplydriver);
		flowFlag = Integer.parseInt(data[1]);
		_Utility.Thread_Sleep(1000);
		_Regbapplydriver.findElement(Test_Registration.submitFirstRegsitartionForm).click();
		_Utility.Thread_Sleep(3000);
	}

	/**
	 * 
	 * @param _Regbapplydriver
	 */
	private void test_Registration_Step_two(WebDriver _Regbapplydriver) {
		Test_Registration.executeTestFlow(false, false, flowFlag,emailid, _Regbapplydriver);
		_Utility.Thread_Sleep(5000);

	}




}



