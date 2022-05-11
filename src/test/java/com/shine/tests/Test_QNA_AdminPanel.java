package com.shine.tests;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

//import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;

//import org.testng.annotations.AfterClass;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

//import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;


import com.shine.base.TestBaseSetup;

import com.shine.common.utils.ExcelReader;


public class Test_QNA_AdminPanel extends TestBaseSetup {
    WebDriver _QNAdriver;
    WebDriverWait wait;
    ArrayList<String> url_params;
    String download_path = "";


    @BeforeClass
    public void TestSetup() {
        download_path = System.getProperty("user.dir") + "\\downloads\\";
        APP_LOGS.debug("Resume download path >> " + download_path);
        clean_download_folder_files(download_path);
        _QNAdriver = getDriver(_QNAdriver);
        url_params = new ArrayList<String>();
        wait = new WebDriverWait(_QNAdriver, 15);
        _QNAdriver.get(baseUrl);
        _QNAdriver.manage().window().maximize();

    }

    @Test(priority = 0)
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


    @Test(priority = 1)
    public void qnaAddUpdate() {
        //Add and Update Page
        _QNAdriver.findElement(By.xpath("/html/body/div[1]/button")).click();
    }

    @Test(priority = 2, dataProvider = "qna_readData")
    public void verify_isQNAPresent(String skill, String jt, String company, String location, String ind, String fa, String minExp) throws InterruptedException {


        if (!skill.isEmpty()) {
            _QNAdriver.findElement(By.id("id_skill1")).sendKeys(skill);
            //_QNAdriver.findElement(By.id("id_skill2")).sendKeys("Communication");
        }

        if (!jt.isEmpty()) {
            _QNAdriver.findElement(By.id("id_job_title")).sendKeys(jt);

        }

        if (!company.isEmpty()) {

            _QNAdriver.findElement(By.id("id_company")).sendKeys(company);
        }

        if (!location.isEmpty()) {
            _QNAdriver.findElement(By.id("id_loc")).sendKeys(location);

        }


        if (!ind.isEmpty()) {
            Select indname = new Select(_QNAdriver.findElement(By.id("id_ind")));
            indname.selectByVisibleText(ind);
        }

        if (!fa.isEmpty()) {
            Select dept = new Select(_QNAdriver.findElement(By.id("id_area")));
            dept.selectByVisibleText(fa);
        }

        if (!minExp.isEmpty()) {
            WebElement minexpval = _QNAdriver.findElement(By.id("id_minexp"));
            minexpval.click();
        }

        //Submit Fields
        _QNAdriver.findElement(By.xpath("/html/body/div/form/input[2]")).click();
        //Add Question and Answers
        WebElement priority = _QNAdriver.findElement(By.name("priority0"));
        priority.clear();
        priority.sendKeys("1");
        WebElement ques = _QNAdriver.findElement(By.name("question0"));
        ques.clear();
        ques.sendKeys("What are the roles and responsibilities of a Java Developer");
        _Utility.Thread_Sleep(3000);

        _QNAdriver.switchTo().frame(_QNAdriver.findElement(By.id("id_textarea_0_ifr")));
        WebElement textArea = _QNAdriver.findElement(By.xpath("//*[@data-id='id_textarea_0']"));
        textArea.clear();
        textArea.click();
        WebElement answer = _QNAdriver.findElement(By.xpath("//*[@data-id='id_textarea_0']"));
        answer.sendKeys("A Java developer is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.");

        _QNAdriver.switchTo().defaultContent();
        JavascriptExecutor js = ((JavascriptExecutor) _QNAdriver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        _Utility.Thread_Sleep(3000);

        _QNAdriver.findElement(By.xpath("/html/body/form/div[6]/input")).click();
        _Utility.Thread_Sleep(3000);
        //Print Successfully added Message
        String finalMsg = _QNAdriver.findElement(By.className("success")).getText();
        assertFalse(finalMsg.isEmpty());
        finalMsg = finalMsg.replace("QnA updated successfully for ", "").trim();
        url_params.add(finalMsg);
    }

    @DataProvider
    public Object[][] qna_readData() {
        ExcelReader excelReader = new ExcelReader();
        Object[][] arrayObject = excelReader.getExcelData(userDirectory + "/src/test/resources/data/qna_mapping.xls", "Sheet1");
        return arrayObject;
    }

    @Test(priority = 3)
    public void qnaRetrieve() throws InterruptedException {
        //Admin Page
        _QNAdriver.findElement(By.xpath("/html/body/div/form/button")).click();
        _QNAdriver.findElement(By.linkText("QnA's")).click();
        _Utility.Thread_Sleep(2000);
        //Retrieve Page
        _QNAdriver.findElement(By.xpath("/html/body/div[3]/button")).click();
        //Filling data fields
        qnaFillData();
    }

    @Test(priority = 4)
    public void qnaDelete() throws InterruptedException {
        //Scroll to the end of the page
        JavascriptExecutor js = ((JavascriptExecutor) _QNAdriver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        _Utility.Thread_Sleep(3000);
        //Delete QnA
        _QNAdriver.findElement(By.linkText("Delete QA's")).click();
        //Delete Message
        String deleteMsg = _QNAdriver.findElement(By.className("success")).getText();
        APP_LOGS.debug(deleteMsg);
    }

    @Test(priority = 5)
    public void qna_retrieveData() throws InterruptedException {
        //Retrieve same data
        qnaFillData();
        //Error Message
        String errorMsg = _QNAdriver.findElement(By.className("error")).getText();
        APP_LOGS.debug(errorMsg);
    }


    public void qnaFillData() throws InterruptedException {
        _QNAdriver.findElement(By.id("id_skill1")).sendKeys("communication");
        _QNAdriver.findElement(By.id("id_loc")).sendKeys("delhi");
        _QNAdriver.findElement(By.id("id_job_title")).sendKeys("Sales Engineer");
        _Utility.Thread_Sleep(2000);
        Select ind_name = new Select(_QNAdriver.findElement(By.id("id_ind")));
        ind_name.selectByVisibleText("IT - Software");
        _Utility.Thread_Sleep(2000);
        WebElement min_exp = _QNAdriver.findElement(By.id("id_minexp"));
        min_exp.click();
        _QNAdriver.findElement(By.xpath("/html/body/div/form/input[2]")).click();
    }

    @Test(priority = 6)
    public void qnaBulk_uploader() throws InterruptedException {
        //Admin Page
        _QNAdriver.findElement(By.xpath("/html/body/div/form/button")).click();
        _QNAdriver.findElement(By.linkText("QnA's")).click();
        _Utility.Thread_Sleep(2000);
        //Bulk Upload
        _QNAdriver.findElement(By.xpath("/html/body/div[2]/button")).click();
        //Valid data
        _QNAdriver.findElement(By.xpath("//*[@id=\"id_bulk_qa_file\"]")).sendKeys(userDirectory + "/src/test/resources/data/bulkupload_valid.xlsx");
        _QNAdriver.findElement(By.cssSelector("[value=\"Upload\"]")).click();
        //Invalid data
        _QNAdriver.findElement(By.xpath("//*[@id=\"id_bulk_qa_file\"]")).sendKeys(userDirectory + "/src/test/resources/data/bulkupload_invalid.xlsx");
        _QNAdriver.findElement(By.cssSelector("[value=\"Upload\"]")).click();

        List<WebElement> errorList = _QNAdriver.findElements(By.id("failed_row"));
        for (int i = 0; i < errorList.size(); i++) {
            WebElement er = errorList.get(i);
            APP_LOGS.debug(er.getText());
        }
        /**
         //Verify retrieve
         _QNAdriver.findElement(By.xpath("/html/body/div[3]/button"));
         qnaData();
         List<WebElement> quesList= _QNAdriver.findElements(By.id(""));
         List<WebElement> ansList= _QNAdriver.findElements(By.id(""));
         assertTrue(quesList.size()==ansList.size(), "Ques-Ans incomplete");
         for(int i=0; i<quesList.size(); i++) {
         WebElement ql = quesList.get(i);
         APP_LOGS.debug(ql.getText());
         }
         for(int a=0; a<ansList.size(); a++) {
         WebElement al = ansList.get(a);
         APP_LOGS.debug(al.getText());
         }
         **/
    }


    @Test(priority = 7)
    public void qnadownload() throws InterruptedException {
        System.out.println(download_path);
        //Download File
        _QNAdriver.findElement(By.xpath("/html/body/div[4]/button")).click();
        _Utility.Thread_Sleep(2000);
        verify_downloaded_files(new File(download_path), "qna.csv");
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult testResult) {
        //Error Screenshots
        TestBaseSetup.takeScreenshotOnFailure(testResult, _QNAdriver);
    }


    @AfterClass(alwaysRun = true)
    public void quitbrowser() {
        APP_LOGS.debug(url_params);
        if (_QNAdriver != null)

            _QNAdriver.quit();
    }

    private void clean_download_folder_files(String directory) {
        try {
            FileUtils.cleanDirectory(new File(directory));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void verify_downloaded_files(final File folder, String expected_file_name) {
        assertTrue(folder.listFiles().length > 0, "File not found");
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String actual_file_name = fileEntry.getName();
                assertEquals(actual_file_name, expected_file_name);
            }
        }
    }
}