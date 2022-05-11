package com.shine.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.shine.common.utils.CommonUtils;

import com.google.gson.Gson;
import com.shine.beans.GoogleFeed;


public class CreateEventHandlerHTMLReport {


    public int create_html_report(String response) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/reports/event_report.html");
        StringBuilder contentBuilder = new StringBuilder();
        CommonUtils _Utility = new CommonUtils();
        try (
                BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/test/resources/reports/event_report_template.html"));
        ) {
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String content = contentBuilder.toString();

        try (
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            String htmlPage = content;
            htmlPage = htmlPage.replace("{{project-name}}", "Shine Registration Click Event Analysis Report " + _Utility.getDateAsString());


            htmlPage = htmlPage.replace("{{expected}}", "");
            htmlPage = htmlPage.replace("Expected Range", "");
            htmlPage = htmlPage.replace("{{actual}}", "");
            htmlPage = htmlPage.replace("{{actual-text}}", "");

            htmlPage = htmlPage.replace("{{timestamp}}", _Utility.getDateAsString());
            htmlPage = htmlPage.replace("{{testid}}", "#" + _Utility.getRandomString(9));

            htmlPage = htmlPage.replace("{{report-url}}", "https://docs.google.com/spreadsheets/d/1LMA4HyPTsYh14nXT8rgYGp7nMlV79QuIOmbsGWozLTM/edit?ouid=101164526360436608188&usp=sheets_home&ths=true");
            String html_result = "";


            String html_header = "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"x_testOverview\" style=\"/* border-radius:9px; */max-width:600px;font-size:11px;background-color:#fff;border: 1px solid #cccccc;padding: 3px 0px 7px 5px;\">\n" +
                    "        <tbody>";
            String html_body = "";

            Gson gson = new Gson();

            GoogleFeed _GoogleFeed = gson.fromJson(response, GoogleFeed.class);
            System.out.println(response);
            html_body = html_body + "<tr><th align=\"left\" style=\" background: #a3c2f4;\"></th><th colspan=\"4\" style=\" background: #0288d1;\">Desktop</th><th colspan=\"4\" style=\" background: #85d485;\">MSite</th></tr>";
            html_body = html_body + "<tr style='background:#a3c2f4;'" +
                    "><th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$target.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$desktop.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$_cpzh4.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$_cre1l.$t + "</th>"
                    + "<th align=\"center\" style='border-right: 1px solid;'>" + _GoogleFeed.feed.entry.get(0).gsx$_chk2m.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$mobile.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$_ckd7g.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$_clrrx.$t + "</th>"
                    + "<th align=\"center\">" + _GoogleFeed.feed.entry.get(0).gsx$_cyevm.$t + "</th></tr>";
            html_body = html_body + "<tr><td colspan=\"9\"><hr style=\"display:block;border:0;border-top:1px solid #948f8f\"></td></tr>";

            for (int i = 1; i < _GoogleFeed.feed.entry.size(); i++) {
                html_body = html_body + "<tr><td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$target.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$desktop.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$_cpzh4.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$_cre1l.$t + "</td>"
                        + "<td align=\"center\" style='border-right: 1px solid;'>" + _GoogleFeed.feed.entry.get(i).gsx$_chk2m.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$mobile.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$_ckd7g.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$_clrrx.$t + "</td>"
                        + "<td align=\"center\">" + _GoogleFeed.feed.entry.get(i).gsx$_cyevm.$t + "</td></tr>";
                html_body = html_body + "<tr><td colspan=\"9\"><hr style=\"display:block;border:0;border-top:1px solid #948f8f\"></td></tr>";

            }


            html_result = html_header + html_body + " </tbody></table>";


            String result_status = "successfully!!!";
            String header_bar_color = "#039be5";
            String header_bar_border_color = "#0288d1";
            htmlPage = htmlPage.replace("{{header-bar-color}}", header_bar_color);
            htmlPage = htmlPage.replace("{{header-bar-border-color}}", header_bar_border_color);
            htmlPage = htmlPage.replace("{{seprator-color}}", header_bar_border_color);
            htmlPage = htmlPage.replace("{{status}}", "<b>Your test run completed " + result_status + "!!!</b>");

            htmlPage = htmlPage.replace("{{result}}", html_result);
            bufferedWriter.write(htmlPage);
            System.out.println("Html page created");
            bufferedWriter.flush();
            fileWriter.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return 0;


    }






}
