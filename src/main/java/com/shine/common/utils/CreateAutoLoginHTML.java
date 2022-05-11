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


public class CreateAutoLoginHTML {


    public int create_html_report(String token) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/reports/auto_login_report.html");
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/test/resources/reports/auto_login_template.html"));) {
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
            htmlPage = htmlPage.replace("{{DATE}}", getDateAsString());
            htmlPage = htmlPage.replace("{{data}}", "<b>" + token + "</b>");
            bufferedWriter.write(htmlPage);
            System.out.println("Html page created");
            bufferedWriter.flush();
            fileWriter.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return 0;


    }


    /**
     * Method to get Date as String
     *
     * @return
     */
    private static String getDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("dd - MMMM yyyy hh:mm:ss a");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
