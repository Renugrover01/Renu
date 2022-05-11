package com.shine.tests;


import static org.testng.Assert.assertEquals;


import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import org.testng.annotations.AfterClass;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeClass;

//import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;


import com.shine.base.TestBaseSetup;


public class Test_QNA_Admin extends TestBaseSetup {

    WebDriver _QNAdriver;
    WebDriverWait wait;

    @BeforeClass
    public void TestSetup() {

        _QNAdriver = getDriver(_QNAdriver);
        wait = new WebDriverWait(_QNAdriver, 15);
        _QNAdriver.get(baseUrl);
        _QNAdriver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void qnaAdminLogin() {

        _Utility.Thread_Sleep(2000);
        //Login
        _QNAdriver.findElement(By.xpath("/html/body/div[2]/section/div[1]/div[1]/div[1]/a/div")).click();
        WebElement email = _QNAdriver.findElement(By.id("id_email_login"));
        email.clear();
        email.sendKeys(email_new);
        WebElement password = _QNAdriver.findElement(By.id("id_password"));
        password.clear();
        password.sendKeys(pass_new);
        _QNAdriver.findElement(By.xpath("//*[@id=\"cndidate_login_widget\"]/div/form[5]/ul/li[5]/input")).click();
        _Utility.Thread_Sleep(2000);
        assertEquals(_QNAdriver.getCurrentUrl(), baseUrl + "/myshine/home/");
        _Utility.Thread_Sleep(2000);
        //Admin Page
        _QNAdriver.get(baseUrl + "/myshine/admin/");
        _Utility.Thread_Sleep(2000);
        //QnA Page
        _QNAdriver.findElement(By.linkText("QnA's")).click();
        _Utility.Thread_Sleep(2000);
    }

    @Test(priority = 2)
    public void qnaAddUpdate() {
        _QNAdriver.findElement(By.xpath("/html/body/div[1]/button")).click();
        _Utility.Thread_Sleep(2000);
    }

    @Test(priority = 3)
    public void qnaRetrieve() {
        //Admin Page
        _QNAdriver.findElement(By.xpath("/html/body/div/form/button")).click();
        _QNAdriver.findElement(By.linkText("QnA's")).click();
        _Utility.Thread_Sleep(2000);
        //Retrieve Page
        _QNAdriver.findElement(By.xpath("/html/body/div[3]/button")).click();

        //Fill Date
        _QNAdriver.findElement(By.id("id_job_title")).sendKeys("Business analyst");
        _QNAdriver.findElement(By.xpath("/html/body/div/form/input[2]")).click();
        _Utility.Thread_Sleep(2000);
    }


    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult testResult) {

        TestBaseSetup.takeScreenshotOnFailure(testResult, _QNAdriver);
    }

    @AfterClass(alwaysRun = true)
    public void quitbrowser() {

        if (_QNAdriver != null)
            _QNAdriver.quit();
    }
}



