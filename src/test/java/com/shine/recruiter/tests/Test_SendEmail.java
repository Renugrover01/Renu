package com.shine.recruiter.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_SendEmail extends TestBaseSetup {
    private static WebDriver _SendEmailDriver;
    public static WebDriverWait wait;

    By searchByEmailid = By.id("id_field_2");
    By emailIdDiv = By.id("id_keyword");
    By searchCandidateBtn = By.id("id_people_search");

    By selectCandidate = By.id("check_1");
    By sendEmailLink = By.linkText("Send Email");
    By submitEmailReq = By.cssSelector("input.okbutton.cls_sendsessionemail");
    By selectTemplateDD = By.id("id_existing_template");
    By previewSendBtn = By.cssSelector("input.yelw_btn.cls_dup_email");
    By messagePopup = By.id("post_msg");
    By messagePopupDuplicate = By.cssSelector("h3");
    By checklink = By.name("check");
    By folderSearch = By.name("folder_search");
    By emailSuccessMsg = By.cssSelector("#id_sendemailpopup > div.marauto > ul > li");
    By okBtn = By.cssSelector("[value='OK']");
    By duplicateMsg = By.id("duplicatecand_msg");

    By dupCancel = By.id("duplicatecand_cancel");
    By cancelLink = By.linkText("Cancel");

    String recruiterURL = "";


    @BeforeClass
    public void TestSetup() {
        _SendEmailDriver = getDriver(_SendEmailDriver);
        _Utility.Thread_Sleep(3000);
        recruiterURL = CONFIG.getProperty("recruiterSiteURL");
        _SendEmailDriver.get(recruiterURL);
        wait = new WebDriverWait(_SendEmailDriver, 15);
        APP_LOGS.debug("Start login for sending email to candidates");
        com.shine.recruiter.tests.Test_RecruiterLogin.login("careerplus9_21", "Media@123", _SendEmailDriver);
    }


    @Test(priority = 0, dataProvider = "emailList")
    public void testPeopleSearchByNumber(String emailId) {
        _SendEmailDriver.navigate().to(recruiterURL + "/peoplesearch/");
        _Utility.Thread_Sleep(3000);
        _SendEmailDriver.findElement(searchByEmailid).click();
        _SendEmailDriver.findElement(emailIdDiv).clear();
        _SendEmailDriver.findElement(emailIdDiv).sendKeys(emailId);
        _SendEmailDriver.findElement(searchCandidateBtn).click();
        _Utility.Thread_Sleep(2000);
        emailToAllCandidates();
    }

    @DataProvider
    public Object[][] emailList() {
        Object[][] Search = new Object[1][1];
        Search[0][0] = "quality.a.bhishek@gmail.com";

        return Search;
    }


    public void emailToAllCandidates() {
        _SendEmailDriver.findElement(selectCandidate).click();
        _Utility.Thread_Sleep(1000);
        _SendEmailDriver.findElement(sendEmailLink).click();
        _SendEmailDriver.findElement(submitEmailReq).click();
        _Utility.Thread_Sleep(2000);
        new Select(_SendEmailDriver.findElement(selectTemplateDD)).selectByVisibleText("QA Testing Template");
        _Utility.Thread_Sleep(2000);
        _SendEmailDriver.findElement(previewSendBtn).click();
        _Utility.Thread_Sleep(2000);
        try {
            ((JavascriptExecutor) _SendEmailDriver).executeScript("arguments[0].scrollIntoView(true);", _SendEmailDriver.findElement(By.xpath("//input[@value='Send Now']")));
            _SendEmailDriver.findElement(By.xpath("//input[@value='Send Now']")).click();
            String sendEmailHeaderText = "";
            try {
            	sendEmailHeaderText = _SendEmailDriver.findElement(messagePopup).getText();
            } catch (Exception e) {
            	APP_LOGS.debug("Duplicate mail message found");
            	sendEmailHeaderText = _SendEmailDriver.findElement(messagePopupDuplicate).getText();
            }
            if (sendEmailHeaderText.contains("Duplicate Alert !!")) {
                Assert.assertEquals(sendEmailHeaderText, "Duplicate Alert !!");
                _Utility.Thread_Sleep(2000);
                _SendEmailDriver.findElement(checklink).click();
                _Utility.Thread_Sleep(2000);
                _SendEmailDriver.findElement(folderSearch).click();
            }
            _Utility.Thread_Sleep(2000);
            Assert.assertEquals(_SendEmailDriver.findElement(emailSuccessMsg).getText(), "Your Emails have been sent successfully.");
            _SendEmailDriver.findElement(okBtn).click();
            _Utility.Thread_Sleep(2000);
        } catch (Exception e) {
            _Utility.Thread_Sleep(2000);
            _SendEmailDriver.findElement(previewSendBtn).click();
            Assert.assertEquals(_SendEmailDriver.findElement(duplicateMsg).getText(), "This Email will be sent to 0 of 1 candidates. 0 candidates did not match the job criteria and 1 candidates received duplicate mails.");
            _SendEmailDriver.findElement(dupCancel).click();
            _Utility.Thread_Sleep(2000);
            _SendEmailDriver.findElement(cancelLink).click();
        }
        APP_LOGS.debug("Finished send email to all candidates on the page\n");
    }

    @AfterClass
    public void quitbrowser() {
        if (_SendEmailDriver != null)
            _SendEmailDriver.quit();

    }


    @AfterMethod
    public void takeScreenshot(ITestResult testResult) {
        TestBaseSetup.takeScreenshotOnFailure(testResult, _SendEmailDriver);
    }

}
