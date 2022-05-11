package com.shine.recruiter.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.google.common.base.CharMatcher;
import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CommonMethods;

public class Test_AdvancedSearch_Boolean extends TestBaseSetup {
    private static WebDriver _booleanDriver;
    int i = 0;
    public static String[] arrsimple = new String[4];
    String w1 = "";

    static By advancedSearchSubmitBtn = By.id("id_advanced_search");
    static By searchResultCounts = By.cssSelector("h4.pullLeft");
    static By noResultsFound = By.cssSelector("div.noresultclass.floatleft");

    CommonMethods commonMethod = new CommonMethods();

    @BeforeClass
    public void TestSetup() {
        _booleanDriver = getDriver(_booleanDriver);
        _Utility.Thread_Sleep(3000);
        //OpenBaseUrl(_booleanDriver);
        _booleanDriver.navigate().to("https://recruiter.shine.com");
        commonMethod.djDebugRemover(_booleanDriver);
        APP_LOGS.debug("Start for additional paramter in advanced search login");
        com.shine.recruiter.tests.Test_RecruiterLogin.login("sunilht_7", "Sunil@123", _booleanDriver);
        commonMethod.clickOnNotification(_booleanDriver);
    }

    @Test(priority = 2, dataProvider = "booleanSearchParam")
    public void Test_AdvSearch_Boolean(String searchKeyword, String sws) throws Exception {
        commonMethod.howeringOnAdvancedSearch(_booleanDriver);
        _booleanDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        _booleanDriver.findElement(By.id("id_boolean")).click();
        _Utility.Thread_Sleep(2000);
        _booleanDriver.findElement(By.id("id_boolean_keyword")).sendKeys(searchKeyword);//ENter Location
        _booleanDriver.findElement(advancedSearchSubmitBtn).click();
        //Assert.assertNotEquals(_booleanDriver.getTitle(), " Advanced Search");
        _Utility.Thread_Sleep(3000);
        long expectedResults = Long.parseLong(getResutls(_booleanDriver));
        System.out.println("Expected Results - " + expectedResults);

        _booleanDriver.findElement(By.id("id_searchinsearch")).clear();
        ;
        _booleanDriver.findElement(By.id("id_searchinsearch")).sendKeys(sws);
        _booleanDriver.findElement(By.id("id_searchinsearch")).sendKeys(Keys.ENTER);
        _Utility.Thread_Sleep(3000);
        long actualResults = Long.parseLong(getResutls(_booleanDriver));
        System.out.println("Actual Results - " + actualResults);
        _Utility.Thread_Sleep(2000);
        Assert.assertTrue(actualResults < expectedResults);
    }

    @SuppressWarnings("deprecation")
    public String getResutls(WebDriver _booleanDriver) {
        try {
            String result = _booleanDriver.findElement(By.cssSelector("h4.pullLeft")).getText().toString();
            Assert.assertNotSame(result, "0 Candidate(s) Found", "Result expected, but 0 candidates found!");
            w1 = CharMatcher.DIGIT.retainFrom(_booleanDriver.findElement(By.cssSelector("h4.pullLeft")).getText());
            APP_LOGS.debug("Match --->>> " + w1 + " Candidate(s) Found");
            arrsimple[i] = w1;
            System.out.println("Value of i - " + i + "Value of w--" + arrsimple[i]);
            i++;
            return w1;
        } catch (Exception e) {
            String noresults = _booleanDriver.findElement(By.cssSelector("div.noresultclass.floatleft")).getText();
            Assert.assertEquals(noresults, "No Results Found!!! Please verify Spellings and broaden the Search Criteria...");
            return "0";
        }
    }

    @DataProvider
    public Object[][] booleanSearchParam() {
        Object[][] booleanSearch = new Object[2][2];
        booleanSearch[0][0] = CONFIG.getProperty("BooleanSearch1");
        booleanSearch[0][1] = "javascript";
        booleanSearch[1][0] = CONFIG.getProperty("BooleanSearch2");
        booleanSearch[1][1] = "Project Manager";
        return booleanSearch;
    }

    @AfterMethod
    public void takeScreenShot(ITestResult testResult) {
        takeScreenshotOnFailure(testResult, _booleanDriver);
    }

    @AfterClass
    public void tearDown() {
        _booleanDriver.quit();
    }
}
