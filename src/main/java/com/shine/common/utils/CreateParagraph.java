package com.shine.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.shine.base.TestBaseSetup;


public class CreateParagraph extends TestBaseSetup {

    @SuppressWarnings("deprecation")
    public static void createResume(String email, String filename) {
        try (
                //Write the Document in file system
                FileOutputStream out = new FileOutputStream(new File(filename));

        ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  //yyyyMMddHHmmss
            // get current date time with Date()
            Date date = new Date();

            String timestamp = dateFormat.format(date);
            //Blank Document
            @SuppressWarnings("resource")
            XWPFDocument document = new XWPFDocument();
            //create Paragraph
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            SecureRandom random = new SecureRandom();
            run.setText("User Email id: " + email);
            run.addBreak();
            run.setText("Mobile no: 9876556789");
            run.addBreak();
            run.addBreak();
            run.setText("Hi, this is a test profile created by shine.com for its internal testing purpose. If this profile shows up in your search result, kindly just ignore and proceed further.");
            run.addBreak();
            run.setText("Hi, this is a test profile created by shine.com for its internal testing purpose. If this profile shows up in your search result, kindly just ignore and proceed further.");
            run.addBreak();
            run.addBreak();
            run.setText("All the test profiles would be by the name of Shine Test Profile and hence you can distinguish these from the actual profiles");
            run.addBreak();
            run.addBreak();
            run.setText(new BigInteger(3030, random).toString(32).replaceAll("\\d", " "));
            run.addBreak();
            run.addBreak();
            run.addBreak();
            try {
                run.setText("Resume Generation id: " + UUID.randomUUID().toString());
                run.addBreak();
                run.setText("Resume created date: " + date.toGMTString());
                run.addBreak();
                run.setText("Resume created Time: " + date.getTime());
            } catch (Exception ex) {
                APP_LOGS.debug("DATE TIME HANDLER ON resume creation");
            }
            run.addBreak();
            run.setText("Resume Created By QA Team at " + timestamp);
            run.addBreak();
            run.addBreak();
            run.setText("Note: This is Auto generated email by SHINE QA TEAM - " + timestamp);

            document.write(out);
            out.close();
            System.out.println(email + " - written successfully - " + filename);
            APP_LOGS.info(email + " - written successfully - " + filename);
        } catch (Exception ex) {
            APP_LOGS.info("Error creating file: " + ex.getMessage());

        }
    }
}