package com.shine.tests;

import java.util.List;

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
import com.shine.tests.Test_JobApply;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
public class Test_Inbox extends TestBaseSetup {

	WebDriver _Inboxdriver;
	static WebDriverWait _Wait;

	By inboxLInk          = By.linkText("Mailbox");
	By jobAlertLink       = By.xpath("//*[@data-whatsnew='job_alert_section_visit']/a");
	By recMailtLink       = By.xpath("//*[@data-whatsnew='recruiter_mail_section_visit']/a");
	By recMailtLink2      = By.partialLinkText("Recruiter mails");
	By openjobAlert 	  = By.cssSelector("span.snd");
	By latestJobMatch 	  = By.cssSelector(".JobY.mailer");
	By jobALertTxt  	  = By.id("id_job_alert");
	By recruiterEmailLink = By.cssSelector(".can_inbox1 span:nth-child(3)");
	By applyBtn           = By.cssSelector(".aply_btn");
	By applyBtn_hack      = By.cssSelector(".apply_right a");
	By recruiterEmailtxt  = By.id("id_recruiter_mail");
	By jobAlertListing	  = By.xpath("//div[@class='listinginside']/ul/li");
	By backToJobAlertLink = By.linkText("Back to job alert");
	By jd_already_applied = By.cssSelector(".AlreadyAppliedtext");

	@BeforeClass
	public void TestSetup() {
		_Inboxdriver = getDriver(_Inboxdriver);
		loggedInShine(_Inboxdriver, email_new, pass_new);
		_Wait = new WebDriverWait(_Inboxdriver, 15);
		_Utility.Thread_Sleep(4000);
		_Utility.clickOnNotification(_Inboxdriver);
		_Wait.until(ExpectedConditions.elementToBeClickable(inboxLInk));
		_Inboxdriver.findElement(inboxLInk).click();
	}

	@Test(priority = 0)
	public void test_Inbox_Tabs() {
		_Wait.until(ExpectedConditions.elementToBeClickable(recMailtLink));
		_Inboxdriver.findElement(recMailtLink).click();
		_Utility.Thread_Sleep(1000);
		_Wait.until(ExpectedConditions.elementToBeClickable(jobAlertLink));
		_Inboxdriver.findElement(jobAlertLink).click();
	}

	@Test(priority = 1)
	public void testInboxJobAlerts() {
		_Utility.Thread_Sleep(1000);
		try 
		{
			_Inboxdriver.findElement(openjobAlert).click();//opening a job alert
		} 
		catch (Throwable t) { System.out.println(t);}
		_Utility.Thread_Sleep(2000);
		try 
		{
			Assert.assertTrue(_Inboxdriver.findElement(latestJobMatch).getText().contains("Messages"));
			List<WebElement> jobList = _Inboxdriver.findElements(jobAlertListing);
			Assert.assertTrue(jobList.size()>0);
		} 
		catch (Throwable t) 
		{
			String message = _Inboxdriver.findElement(jobALertTxt).getText().trim();
			APP_LOGS.debug("INBOX: jOB Alert Message>> "+message);
			Assert.assertTrue(message.contains("You have not received any job alert mails in the past 15 days."),
					message);
		}

	}


	@Test(priority = 2)
	public void testInboxRecruiterEmails() {
		_Inboxdriver.navigate().to(baseUrl+"/myshine/inbox/");
		_Wait.until(ExpectedConditions.elementToBeClickable(recMailtLink));
		_Inboxdriver.findElement(recMailtLink).click();
		try {
			_Inboxdriver.findElement(recruiterEmailLink).click();
			_Utility.Thread_Sleep(2000);
			Assert.assertEquals(_Inboxdriver.getTitle(), "Shine.com - My Shine | Recruiter View");
			WebElement job_apply_button = _Inboxdriver.findElement(applyBtn);
			String jobsmail = job_apply_button.getText();
			APP_LOGS.debug(jobsmail);
			Assert.assertEquals(jobsmail, "Apply");
			job_apply_button.click();
			_Utility.Thread_Sleep(2000);
			String apply_msg = _Inboxdriver.findElement(By.cssSelector(".alrd_applied")).getText().trim();
			Assert.assertEquals(apply_msg, "Applied");

		} 
		catch (Throwable t) {
			try {
				_Inboxdriver.findElement(applyBtn_hack).click();
				_Utility.Thread_Sleep(2000);
				Test_JobApply _Apply = new Test_JobApply();
				_Inboxdriver.findElement(_Apply.applyButtonJD).click();
				_Utility.Thread_Sleep(3000);
				String applied_msg = _Inboxdriver.findElement(jd_already_applied).getText().trim();
				Assert.assertEquals(applied_msg, "Already applied");
			}
			catch (Throwable t2){
				String message = _Inboxdriver.findElement(recruiterEmailtxt).getText();
				APP_LOGS.debug("INBOX: Recruiter tab Message>> "+message);
				Assert.assertTrue(message.contains("You have not received any mails from recruiters in the past 15 days."),
						message);
			}
		}
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Inboxdriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Inboxdriver!=null)
			_Inboxdriver.quit();

	}

}
