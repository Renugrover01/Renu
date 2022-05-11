package com.shine.tests;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

import org.testng.Assert;

public class Test_WVMP extends TestBaseSetup {

	private static WebDriver _WVMPdriver;
	private static WebDriverWait _Wait;
	static int flag = 0;

	By profilePerfLink = By.linkText("Recruiter Actions");
	By wvmpLink = By.cssSelector(".cls_can_Account.cls_who_ved_pro");
	By wvmpHeadTitle = By.cssSelector(".JobY h2");
	By activityMsg = By.cssSelector(".txwhitebase_email  b");
	By whoviewedList = By.cssSelector(".search_listing.search_listing_blue.srch_bg");

	By activityMessage = By.cssSelector(".activity li");
	By emailCount = By.cssSelector(".cls_view_recruiter_action_mail .snd");
	By wvmpHead = By.cssSelector(".wvmp_head span");

	@BeforeClass
	public void TestSetup() {
		_WVMPdriver = getDriver(_WVMPdriver);
		OpenBaseUrl(_WVMPdriver);
		loggedInShine(_WVMPdriver, email_new, pass_new);
		_Utility.clickOnNotification(_WVMPdriver);
		_Wait = new WebDriverWait(_WVMPdriver, 15);
	}

	@Test(priority = 0)
	public void testOpenWvmp() {
		_Utility.Thread_Sleep(2000);
		_Utility.openMenuLink(Test_MyProfile.userAccMenuLink, profilePerfLink, _WVMPdriver);
		_Utility.Thread_Sleep(2000);
		_Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(wvmpHeadTitle));
		_Utility.Thread_Sleep(1000);
		String wvmpTitle = _WVMPdriver.findElement(wvmpHeadTitle).getText().trim();
		Assert.assertEquals(wvmpTitle, "Recruiter Actions");
	}

	@Test(priority = 1)
	public void test_profile_view() {
		try {
			Assert.assertEquals(
					"There has been no recruiter activity on your Profile.\n"
							+ "Please keep your Profile updated to ensure that you are visible to Recruiters.",
					_WVMPdriver.findElement(activityMsg).getText());
		} catch (AssertionError ae) {
			APP_LOGS.error("BUG FOUND: No profile view exist. - " + ae.getMessage());
			throw new AssertionError("No profile view Message present -" + ae.getMessage()
					+ ", Note: For more details kindly consult the QA Team");
		} catch (Exception ex) {
			flag = 1;
			APP_LOGS.error("No profile view - Testing for valid message now. - " + ex.getMessage());
		}
		if (flag == 1) {
			List<WebElement> profileview = _WVMPdriver.findElements(whoviewedList);
			APP_LOGS.info("Total no of view on one page: " + profileview.size());
			Assert.assertTrue(profileview.size() > 0, String.valueOf(profileview.size()));
		}

	}

	@Test(priority = 2)
	public void test_pview_activity() {
		if (flag == 1) {
			List<WebElement> profileview = _WVMPdriver.findElements(whoviewedList);
			APP_LOGS.info("Total no of view on one page: " + profileview.size());
			for (WebElement activity : profileview) {
				boolean[] fieldsToInclude = Match_Activity(activity);
				APP_LOGS.debug(fieldsToInclude);
				boolean actual = ArrayUtils.contains(fieldsToInclude, true);
				System.out.println(actual);
				APP_LOGS.debug("Match result is: " + actual);
				Assert.assertEquals(actual, true);
			}
		} else
			throw new SkipException("Explicitly Skipping this exception as No View Found");
	}

	/*
	 * @Test(priority = 3, dependsOnMethods = {"test_pview_activity"}) public void
	 * test_recruiter_view() { List<WebElement> profileview =
	 * _WVMPdriver.findElements(whoviewedList);
	 * APP_LOGS.info("Total no of view on one page: "+profileview.size()); String
	 * data = profileview.get(0).findElement(activityMessage).getText(); data =
	 * StringUtils.substringBefore(data, " on ").trim(); String[] dataArray =
	 * data.split("\n"); APP_LOGS.info("Data is: "+Arrays.asList(dataArray)); Set
	 * flag based on the found result boolean isProfileViewed =
	 * Arrays.asList(dataArray).contains("Profile Viewed"); boolean isEmailRecieved
	 * = Arrays.asList(dataArray).contains("Contacted via Email"); boolean
	 * isJobFound = Arrays.asList(dataArray).contains("Jobs Found"); boolean
	 * isFoundViaAutomatch = Arrays.asList(dataArray).contains("Automatch"); boolean
	 * isProfileShortlisted =
	 * Arrays.asList(dataArray).contains("Profile Shortlisted"); boolean
	 * isResumeDownloaded = Arrays.asList(dataArray).contains("Resume Downloaded");
	 * boolean isFoundViaAutomatch = Arrays.asList(dataArray).contains("Automatch");
	 * boolean isFoundViaAutomatch = Arrays.asList(dataArray).contains("Automatch");
	 * APP_LOGS.info("Email Recieved = "+isEmailRecieved);
	 * APP_LOGS.info("FoundJob = "+isJobFound);
	 * APP_LOGS.info("Profile Viewed = "+isProfileViewed);
	 * APP_LOGS.info("Profile Shortlisted = "+isProfileShortlisted);
	 * APP_LOGS.info("Found via Automatch = "+isFoundViaAutomatch);
	 * APP_LOGS.info("Resume Downloaded = "+isResumeDownloaded);
	 * 
	 * profileview.get(0).findElement(By.tagName("a")).click();
	 * _Utility.Thread_Sleep(1000); if(isEmailRecieved == true && isJobFound == true
	 * && isFoundViaAutomatch == true && isProfileShortlisted == true &&
	 * isProfileViewed == true&&isResumeDownloaded ==true) { checkisEmailRecieved();
	 * checkisJobFound(); checkisFoundViaAutomatch(); checkisProfileShortlisted();
	 * checkisProfileViewed(); isResumeDownloaded(); } else if(isEmailRecieved ==
	 * true && isJobFound == true) { checkisEmailRecieved(); checkisJobFound(); }
	 * else if(isEmailRecieved == true && isFoundViaAutomatch == true) {
	 * checkisEmailRecieved(); checkisFoundViaAutomatch(); } else if(isEmailRecieved
	 * == true && isProfileShortlisted == true) { checkisEmailRecieved();
	 * checkisProfileShortlisted(); } else if(isJobFound == true &&
	 * isFoundViaAutomatch== true) { checkisJobFound(); checkisFoundViaAutomatch();
	 * } else if(isJobFound == true) { checkisJobFound(); } else if(isEmailRecieved
	 * == true) { checkisEmailRecieved(); } else if(isFoundViaAutomatch == true) {
	 * checkisFoundViaAutomatch(); } else if(isProfileShortlisted == true) {
	 * checkisProfileShortlisted(); } else if(isProfileViewed == true) {
	 * checkisProfileViewed(); } else if(isResumeDownloaded== true) {
	 * isResumeDownloaded(); }
	 * 
	 * 
	 * 
	 * }
	 */

	/* Match Activity data */
	private boolean[] Match_Activity(WebElement activity) {
		boolean[] bdata = new boolean[6];
		String[] fixedArray = { "Resume Downloaded", "Profile Shortlisted", "Contacted via Email", "Profile Viewed",
				"Contacted via SMS", "Contacted via Call" };
		String data = activity.findElement(activityMessage).getText();
		APP_LOGS.debug(data);
		data = StringUtils.substringBefore(data, " on ").trim();
		APP_LOGS.debug(data);
		String[] dataArray = data.split("\n");
		APP_LOGS.debug("Data is: " + Arrays.asList(dataArray));
		for (int i = 0; i < fixedArray.length; i++) {
			boolean matchResult = Arrays.asList(dataArray).contains(fixedArray[i]);
			APP_LOGS.debug(matchResult);
			bdata[i] = matchResult;
		}
		return bdata;
	}

	/* Check email received - Method */
	private void checkisEmailRecieved() {
		List<WebElement> emailCountList = _WVMPdriver.findElements(emailCount);
		int count = emailCountList.size();
		APP_LOGS.info("Email count =  " + count);
		Assert.assertTrue(count > 0, "Email count: " + count);
		String expected_job_total = emailCountList.get(0).getText().trim();
		emailCountList.get(0).click();
		_Utility.Thread_Sleep(1000);
		String actual_job_total = _WVMPdriver.findElement(By.cssSelector(".fst")).getText();
		Assert.assertEquals(actual_job_total, expected_job_total);
		_WVMPdriver.navigate().back();
	}

	/* Check Job found - Method */
	private void checkisJobFound() {

	}

	/* Check Candidature Viewed Method */
	private void checkisFoundViaAutomatch() {

	}

	public void checkisProfileShortlisted() {
		assertChecker("Profile Shortlisted");
	}

	public void checkisProfileViewed() {
		assertChecker("Profile Viewed");
	}

	public void isResumeDownloaded() {
		assertChecker("Resume Downloaded");

	}

	public void assertChecker(String expected_matcher) {
		String actual = _WVMPdriver.findElement(wvmpHead).getText();
		APP_LOGS.debug("checkisResumeDownloaded Data is: " + actual);
		actual = StringUtils.substringBefore(actual, " on ").trim();
		Assert.assertTrue(actual.contains(expected_matcher), actual);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _WVMPdriver);
	}

	@AfterClass(alwaysRun = true)
	public void quitbrowser() {
		if (_WVMPdriver != null)
			_WVMPdriver.quit();

	}

}
