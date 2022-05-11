package com.shine.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;


public class Test_ExitAlertpopup extends TestBaseSetup {
    WebDriver _ExitAlertpopup;
    WebDriverWait wait;

    String parameter = "?utm_content=exitOverlayT";
    String assertMsg1 = "Your profile is incomplete";
    String assertMsg2 = "A huge salary hike awaits you at the other end of profile completion";


    By headTxt = By.cssSelector("[class='head']");
    By midTxt = By.cssSelector("[class='mid']");


    @BeforeClass
    public void TestSetup() {
        _ExitAlertpopup = getDriver(_ExitAlertpopup);
    }

    @Test(priority = 0)
    public void verify_popup_registration() {
        checkAlertPopup(baseUrl + "/registration/" + parameter, assertMsg1, assertMsg2);
    }

    @Test(priority = 1)
    public void verify_popup_registrationSEM() {
        checkAlertPopup(baseUrl + "/sem/registration/" + parameter, assertMsg1, assertMsg2);
    }

    public void checkAlertPopup(String url, String message1, String message2) {
        APP_LOGS.debug("url: " + url);
        APP_LOGS.debug("Message 1: " + message1);
        APP_LOGS.debug("Message 2: " + message2);
        _ExitAlertpopup.navigate().to("URL: " + url);
        _Utility.Thread_Sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) _ExitAlertpopup;
        js.executeScript("$(document).trigger('mouseleave')");

        _Utility.Thread_Sleep(3000);
        Assert.assertEquals(_ExitAlertpopup.findElement(headTxt).getText(), message1);
        Assert.assertEquals(_ExitAlertpopup.findElement(midTxt).getText(), message2);

    }


    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult testResult) {
        TestBaseSetup.takeScreenshotOnFailure(testResult, _ExitAlertpopup);
    }

    @AfterClass(alwaysRun = true)
    public void quitbrowser() {
        if (_ExitAlertpopup != null)
            _ExitAlertpopup.quit();

    }
}
