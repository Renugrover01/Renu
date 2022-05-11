package com.shine.emailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.shine.base.TestBaseSetup;

public class ReadEmail extends TestBaseSetup {

    static boolean check = false;
    private boolean similarFlag = false;
    private boolean matchingJobFlag = false;
    private boolean applyJobEmailFlag = false;
    private boolean jobForYouFlag = false;
    private boolean recentActivityFlag = false;
    private boolean recruiterActivityFlag = false;
    private boolean jobApplicationFlag = false;
    private List<String> urlList = new ArrayList<>();

    public List<String> getEmailURLs() {

        String host = "imap.googlemail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "manvi.agarwal191018@gmail.com";
        byte[] valueDecoded = Base64.decodeBase64("c2hpbmVAMTIzNDU=".getBytes());
        return check(host, mailStoreType, username, new String(valueDecoded));

    }

    /**
     * jobs similar
     * jobs matching
     * Required for QA Automation Enginner
     * jobs for you
     * recent activity
     * Recruiters contacted you
     * Your application
     */
    @SuppressWarnings("unused")
    public List<String> check(String host, String storeType, String user, String password) {
        try {
            //create properties field
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");

            //create email session object
            Session emailSession = Session.getDefaultInstance(properties, null);

            //create the imaps store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            APP_LOGS.debug("messages.length---" + messages.length);
            int messageLength = messages.length - 1;
            for (int i = messageLength; i > 0; i--) {
                Message message = messages[i];
                int email_no = i + 1;
                String email_date = message.getReceivedDate().toString();
                String email_subject = message.getSubject().toString();
                String email_from = message.getFrom()[0].toString();
                String email_content = message.getContent().toString();
				/*APP_LOGS.debug("---------------------------------");
				APP_LOGS.debug("Email Number " + email_no);
				APP_LOGS.debug("Date: " + email_date);
				APP_LOGS.debug("Subject: " + email_subject);
				APP_LOGS.debug("From: " + email_from);
				//APP_LOGS.debug("Content: " + email_content);*/


                if (email_subject.contains("jobs similar")) {
                    APP_LOGS.debug("Jobs similar Subject: " + email_subject);
                    if (!similarFlag)
                        similarJob(email_content);
                    similarFlag = true;
                }
                if (email_subject.contains("jobs matching")) {
                    APP_LOGS.debug("Jobs matching Subject: " + email_subject);
                    if (!matchingJobFlag)
                        matchingJob(email_content);
                    matchingJobFlag = true;
                }
                if (email_subject.contains("Required for QA Automation Enginner")) {
                    APP_LOGS.debug("Required for QA Automation Enginner >> Subject: " + email_subject);
                    if (!applyJobEmailFlag)
                        applyJobEmail(email_content);
                    applyJobEmailFlag = true;
                }
                if (email_subject.contains("jobs for you")) {
                    APP_LOGS.debug("Jobs for you Subject: " + email_subject);
                    if (!jobForYouFlag)
                        jobForYou(email_content);
                    jobForYouFlag = true;
                }
                if (email_subject.contains("based on your recent activity")) {
                    APP_LOGS.debug("Recent activity Subject: " + email_subject);
                    if (!recentActivityFlag)
                        recentActivity(email_content);
                    recentActivityFlag = true;
                }
                if (email_subject.contains("Recruiters contacted you through Shine.com")) {
                    APP_LOGS.debug("Recruiters contacted Subject: " + email_subject);
                    if (!recruiterActivityFlag)
                        recruiterActivity(email_content);
                    recruiterActivityFlag = true;
                    break;
                }
                if (email_subject.contains("Your application")) {
                    APP_LOGS.debug("Your application Subject: " + email_subject);
                    if (!jobApplicationFlag)
                        jobApplication(email_content);
                    jobApplicationFlag = true;
                }

                if (i <= messageLength - 100 || similarFlag && matchingJobFlag && applyJobEmailFlag && jobForYouFlag && recentActivityFlag && recruiterActivityFlag && jobApplicationFlag) {
                    break;
                }

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            APP_LOGS.debug(e.getMessage());
        }
        return urlList;
    }


    /**
     * @param html
     */
    private void jobApplication(String html) {
        try {
            Document doc = Jsoup.parse(html);
            String links = doc.select("tbody > tr > td > p > a").attr("href");
            urlList.add("jobApplication:" + links);
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }
    }

    /**
     * @param html
     */
    private void recruiterActivity(String html) {
        try {
            Document doc = Jsoup.parse(html);
            String link = doc.select("a:contains(View recruiter details)").attr("href");
            urlList.add("recruiterActivity:" + link.toString());
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }

    }

    /**
     * @param html
     */
    private void recentActivity(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tbody > tr > td > a[href]");
            String link = elements.get(2).attr("href");
            urlList.add("recentActivity:" + link.toString());
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }

    }

    /**
     * @param html
     */
    private void jobForYou(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tbody > tr > td > a[href]");
            String link = elements.get(4).attr("href");
            urlList.add("jobForYou:" + link.toString());
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }

    }

    /**
     * @param html
     */
    private void applyJobEmail(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tbody > tr > td > a[href]");
            String link = elements.get(4).attr("href");
            urlList.add("applyJobEmail:" + link.toString());
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }

    }


    /**
     * @param html
     */
    private void matchingJob(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tbody > tr > td > span > a[href]");
            String link = elements.get(2).attr("href");
            urlList.add("matchingJob:" + link.toString());
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }

    }

    /**
     * @param html
     */
    private void similarJob(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tbody > tr > td > a[href]");
            String link = elements.get(2).attr("href");
            urlList.add("similarJob:" + link);
        } catch (Exception ex) {
            APP_LOGS.debug(ex.getMessage());
        }

    }


}
