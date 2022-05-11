package com.shine.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
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
import com.shine.common.utils.CommonUtils;

public class Test_JobDetails extends TestBaseSetup {

	static WebDriver _JDdriver;
	static WebDriverWait wait;
	static CommonUtils _CommonUtils;

	By nextBtn           = By.cssSelector(".cls_nxtjob.floatleft.disable");
	By jdTitle           = By.cssSelector(".heading.floatleft");
	By recruiterDetails  = By.cssSelector(".ropen.cls_rect_detail_div");
	By similarJobLink    = By.cssSelector(".similar.cls_smljob");
	By recruiterJobLink  = By.partialLinkText("by this recruiter");
//	By recruiterApply    = By.xpath("//a[@class='bulkapply']");
	By skillElements     = By.xpath("//span[@itemprop='skills']");
	By skillText 		 = By.cssSelector(".num_key>h1");

	@BeforeClass
	public void TestSetup() {
		_JDdriver= getDriver(_JDdriver);
		wait = new WebDriverWait(_JDdriver, 20);
		loggedInShine(_JDdriver, email_main, pass_new);
		_Utility.Thread_Sleep(2000);
		_Utility.clickOnNotification(_JDdriver);
		wait = new WebDriverWait(_JDdriver, 20);

	}

	@Test(priority=5)
	public void testSimilarJobs() {
		Test_JobApply.openjdpage(_JDdriver);
		APP_LOGS.debug("jOB Details: Similar Jobs: "+_JDdriver.getTitle());
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(recruiterDetails, _JDdriver);
		_Utility.elementDisplayPropertySetter("none", "id_title", _JDdriver);
		_JDdriver.findElement(similarJobLink).click();
		_JDdriver.switchTo().parentFrame();
		_Utility.Thread_Sleep(3000);
		String page_url = _JDdriver.getCurrentUrl();
		Assert.assertTrue(page_url.contains("/job-search/similar/?jobid="), page_url);
		//Assert.assertTrue(_JDdriver.getTitle().contains("Similar Jobs"), _JDdriver.getTitle());
	}


	@Test(priority=6)
	public void testAllJobsByRecruiter() {
		Test_JobApply.openjdpage(_JDdriver);
		SwitchTOCurrentWindow(_JDdriver);
		_Utility.clickOnNotification(_JDdriver);
		APP_LOGS.debug("jOB Details: testAllJobsByRecruiter Jobs: "+_JDdriver.getTitle());
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(recruiterDetails, _JDdriver);
		_Utility.elementDisplayPropertySetter("none", "id_title", _JDdriver);
		wait.until(ExpectedConditions.elementToBeClickable(recruiterJobLink));
		_JDdriver.findElement(recruiterJobLink).click();	
		_Utility.Thread_Sleep(3000);
		String page_url = _JDdriver.getCurrentUrl();
		Assert.assertTrue(page_url.contains("?rect="), page_url);
		//_Utility.scrollTOElement(recruiterApply, _JDdriver);
		//String alljobs1=_JDdriver.findElement(recruiterApply).getText().trim();
		//Assert.assertEquals(alljobs1, "Apply to all Selected Jobs");


	}

	@Test(priority=7)
	public void testSkillLink() {
		Test_JobApply.openjdpage(_JDdriver, "java");
		_Utility.clickOnNotification(_JDdriver);
		SwitchTOCurrentWindow(_JDdriver);
		APP_LOGS.debug("jOB Details: testSkillLink Jobs: "+_JDdriver.getTitle());
		List <WebElement> Skills = _JDdriver.findElements(skillElements);
		_Utility.scrollTOElement(Skills.get(0), _JDdriver);
		_Utility.elementDisplayPropertySetter("none", "id_title", _JDdriver);
		System.out.println("The name of the skill on JD page is "+Skills.get(0).getText());
		wait.until(ExpectedConditions.visibilityOf(Skills.get(0)));
		String skillname = Skills.get(0).getText(); 
		skillname = skillname.replace(",", "").trim();
		APP_LOGS.debug("Job Skill: "+skillname);
		Skills.get(0).click();
		_Utility.Thread_Sleep(3000);
		SwitchTOCurrentWindow(_JDdriver);
		_JDdriver.switchTo().activeElement();
		String alljobs2=_JDdriver.findElement(skillText).getText();
		APP_LOGS.debug("Job Skill2: "+alljobs2);
		Assert.assertTrue(alljobs2.contains("Jobs"), "Checking for {Jobs} key: "+alljobs2);
		Assert.assertTrue(alljobs2.toLowerCase().contains(skillname.toLowerCase()), skillname+" doesn't contain in: "+alljobs2);
	}
	
	
	@Test(priority=8)
	public void test_third_party_job() {
		_Utility.Thread_Sleep(3000);
		_JDdriver.get(baseUrl+"/myshine/job-search/ibm-jobs");
		_Utility.Thread_Sleep(1000);
		List<WebElement> job_list = _JDdriver.findElements(By.cssSelector(".cls_jobtitle"));
		job_list.get(0).click();
		
  }
	@Test(priority=9, dependsOnMethods={"test_third_party_job"})
	public void test_third_party_jd_title() {
		String jd_job_title = _JDdriver.findElement(By.cssSelector("[itemprop=\"title\"]")).getText();
		APP_LOGS.debug("jd_job_title "+jd_job_title);
		assertEquals(jd_job_title.isEmpty(), false);
  }
	
	@Test(priority=10, dependsOnMethods={"test_third_party_job"})
	public void test_third_party_jd_apply_button() {
		WebElement apply_button = _JDdriver.findElement(By.xpath("(//*[contains(text(),'Apply')])[1]"));
		APP_LOGS.debug("jd_job_title "+apply_button.getText());
		assertEquals(apply_button.getText().isEmpty(), false);
		apply_button.click();
		Test_JobApply.assertMessage(_JDdriver);  
  }
	
	
	
	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_JDdriver!=null){
			_JDdriver.quit();
		}
	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _JDdriver);
	}



	/**
	 * Switch TO Current Window
	 * @param _JDdriver
	 */
	private void SwitchTOCurrentWindow(WebDriver _JDdriver){
		for (String winHandle1 : _JDdriver.getWindowHandles()) {
			_JDdriver.switchTo().window(winHandle1); 
			// switch focus of WebDriver to the next found window handle (that's your newly opened window)
		}
	}


}
