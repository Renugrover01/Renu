package com.shine.emailer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;


import com.shine.beans.QunitReportData;

public class JTEST_Email {


    //********************* Email with attachment *********************
    private String passedCount;

    public void sendAttachmentEmail(Session session, String toEmail, String subject) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("f3282@htmedia.in", "NoReply-HT"));
            msg.setReplyTo(InternetAddress.parse("f3282@htmedia.in", false));

            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            msg.setHeader("X-Priority", "1");

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            //Calling function to attach the HTML report in a Email Body
            String body = readFile(System.getProperty("user.dir") + "/src/test/resources/reports/report.html");

            // Fill the bodypart in Email body
            messageBodyPart.setContent(body, "text/html");
            msg.setContent(body, "text/html");
            msg.saveChanges();

            // Send message
            Transport.send(msg);
            System.out.println("Email Sent Successfully!!");
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        }
    }

    private String readFile(String file) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
        ) {
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "";
        }
    }


    public void sendEmail(QunitReportData qunitReportData) {
        //Decode data on other side, by processing encoded data
        byte[] valueDecoded = Base64.decodeBase64("c2hpbmVAMTIz".getBytes());
        final String fromEmail = "shineautomationreport@gmail.com"; //requires valid email id
        String[] result = new String[4];
        try {
            // get the pass, fail and skip counts of test case execution
            result[0] = "Total: " + qunitReportData.getTestcase_total_count();
            result[1] = "Passed: " + qunitReportData.getTestcase_passed_count();
            result[2] = "Failed: " + qunitReportData.getTestcase_failed_count();
            result[3] = "Skipped: " + qunitReportData.getTestcase_skipped_count();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("value of passed test cases is..... " + passedCount);
        final String toEmail;
        toEmail = "manvi.agarwal@hindustantimes.com, rahul.gupta@hindustantimes.com, shakti.sharma@hindustantimes.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, new String(valueDecoded));
            }
        };

        Session session = Session.getInstance(props, auth);
        System.out.println("Authorization passed...");

        //Calling Email Function with attachment
        sendAttachmentEmail(session, toEmail, qunitReportData.getProject_name() + " " + Arrays.toString(result));


    }


}	            
