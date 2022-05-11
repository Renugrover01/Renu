package com.shine.recruiter.tests;


import com.shine.base.TestBaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_RecruiterLogin extends TestBaseSetup {

    static By userName = By.name("username");
    static By userPass = By.name("password");
    static By loginBtn = By.xpath(".//*[@value='Login']");
    static By resetBtn = By.linkText("Click here to reset session");
    static By clickToResetBtn = By.cssSelector("span.floatleft");
    static By loginPopup = By.id("loginbutton");
    private static WebDriver _LoginDriver;
    static WebDriverWait wait;

    @BeforeClass
    public void TestSetup() throws Exception {
        _LoginDriver = getDriver(_LoginDriver);
        wait = new WebDriverWait(_LoginDriver, 15);
        _LoginDriver.get(CONFIG.getProperty("recruiterSiteURL"));
        APP_LOGS.debug("Start of registration tests");
    }

    @Test(priority = 1)
    public void loggedInRecruiter() throws Exception {
        login("sunilht_a", "Sunil@123", _LoginDriver);
    }

    public static void login(String UserName, String PassWord, WebDriver _LoginDriver) {
        _Utility.Thread_Sleep(2000);
        _LoginDriver.findElement(loginPopup).click();
        _Utility.Thread_Sleep(500);
        _LoginDriver.findElement(userName).clear();
        _LoginDriver.findElement(userName).sendKeys(UserName);
        _LoginDriver.findElement(userPass).clear();
        _LoginDriver.findElement(userPass).sendKeys(PassWord);
        _LoginDriver.findElement(loginBtn).click();

        ResetUser(_LoginDriver);
    }

    public static void clickResetButton(WebDriver driver) {
        WebElement el = driver.findElement(resetBtn);
        try {
            if (el.isDisplayed())
                el.click();
        } catch (Throwable e) {
            //e.printStackTrace();
        }
    }

    public static void ResetUser(WebDriver driver) {
        try {
            String actual_txt = driver.findElement(clickToResetBtn).getText();
            APP_LOGS.debug("Actual text = " + actual_txt);
            if (actual_txt.equals("You are logged in from another computer.")) {
                clickResetButton(driver);
            } else {
            }
        } catch (Exception e) {
            APP_LOGS.debug("No any user is logged in currently");
        }
    }

    @AfterClass
    public void quitbrowser() {
        if (_LoginDriver != null)
            _LoginDriver.quit();

    }


    @AfterMethod
    public void takeScreenshot(ITestResult testResult) {
        TestBaseSetup.takeScreenshotOnFailure(testResult, _LoginDriver);
    }
}