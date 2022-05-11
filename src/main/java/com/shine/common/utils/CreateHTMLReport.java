package com.shine.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.shine.common.utils.CommonUtils;

import com.shine.base.TestBaseSetup;
import com.shine.beans.QunitReportData;

public class CreateHTMLReport extends TestBaseSetup {


    public void create_html_report(QunitReportData _QunitReportData) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/reports/report.html");
        StringBuilder contentBuilder = new StringBuilder();
        CommonUtils _Utility = new CommonUtils();
        try (
                BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/test/resources/reports/report_template.html"));
        ) {
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
        } catch (IOException e) {
            APP_LOGS.error(e.getMessage());
        }
        String content = contentBuilder.toString();


        try (
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            String htmlPage = content;
            htmlPage = htmlPage.replace("{{project-name}}", _QunitReportData.getProject_name() + " " + _Utility.getDateAsString());
            htmlPage = htmlPage.replace("{{status}}", _QunitReportData.getReport_status());
            htmlPage = htmlPage.replace("{{total}}", _QunitReportData.getTestcase_total_count());
            htmlPage = htmlPage.replace("{{passed}}", _QunitReportData.getTestcase_passed_count());
            htmlPage = htmlPage.replace("{{failed}}", _QunitReportData.getTestcase_failed_count());
            htmlPage = htmlPage.replace("{{skipped}}", _QunitReportData.getTestcase_skipped_count());
            htmlPage = htmlPage.replace("{{USER-AGENT}}", _QunitReportData.getUser_agent());
            htmlPage = htmlPage.replace("{{timestamp}}", _Utility.getDateAsString());
            htmlPage = htmlPage.replace("{{testid}}", "#" + _Utility.getRandomString(9));
            htmlPage = htmlPage.replace("{{result}}", "<ul style=\"list-style-type: none;\">" + _QunitReportData.getResult() + "</ul>");
            htmlPage = htmlPage.replace("</li>", "</li><li><br></li>");
            htmlPage = htmlPage.replace("<ol class=\"qunit-assert-list qunit-collapsed\">", "<ol class=\"qunit-assert-list qunit-collapsed\" style=\"list-style-type: none;\">");
            htmlPage = htmlPage.replace("class=\"pass\">", "class=\"pass\" style=\"color: #3C510C;background-color: #e6e2e2;border-left: 10px solid #C6E746;border-radius: 4px;padding: 6px 2px 6px 8px;text-align: left;word-wrap: break-word;\">");
            htmlPage = htmlPage.replace("class=\"fail\">", "class=\"pass\" style=\"color: #3C510C;background-color: #e6e2e2;border-left: 10px solid #EE5757;border-radius: 4px;padding: 6px 2px 6px 8px;text-align: left;word-wrap: break-word;\">");

            bufferedWriter.write(htmlPage);
            APP_LOGS.info("QUNIT Html page created");
        } catch (IOException e) {
            APP_LOGS.error("IO Exception "+e.getMessage());
        }


    }




}
