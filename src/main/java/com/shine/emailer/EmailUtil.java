package com.shine.emailer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Arrays;

import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;



public class EmailUtil {


    //********************* Email with attachment *********************
    private static Properties CONFIG;
    private static float passedCase;
    private static float failedItems;
    private static float skipItems;
    private static final String SERVER_NAME = "preprod";
   // private static final String USER_EMAIL =  "rahul.gupta@hindustantimes.com, manvi.agarwal@hindustantimes.com";
    private static final String USER_EMAIL =  "qa.shinecandidate@gmail.com";
    private static String directory = System.getProperty("user.dir");
    private static Logger APP_LOGS = Logger.getLogger("ShineCandidateLogger");

    public static void sendAttachmentEmail(Session session, String toEmail, String subject, String body) {
        String htmlBody = body;
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("f3282@htmedia.in", "NoReply-HT"));
            msg.setReplyTo(InternetAddress.parse("f3282@htmedia.in", false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            //Calling function to attach the HTML report in a Email Body
            htmlBody = readFile(directory + CONFIG.getProperty("customReport"));
            // Fill the bodypart in Email body
            htmlBody = htmlBody.replace("<tr><td style='display:none;'>{{ng-report}}</td></tr>", createResult());
            messageBodyPart.setContent(htmlBody, "text/html");
            msg.setContent(htmlBody, "text/html");
            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            String screenshotDirectoryName = directory + CONFIG.getProperty("scrfolder");
            File screenshotFile = new File(screenshotDirectoryName);
            if (!screenshotFile.exists()) {
                screenshotFile.mkdir();
            }
            File[] files = new File(screenshotDirectoryName).listFiles();
            String[] attachFiles = new String[files.length + 2];
            int i = 0;
            for (File file : files) {
                if (file.isFile() && i < files.length + 1) {
                    attachFiles[i] = file.getAbsolutePath();
                }
                i++;
            }
            attachFiles[files.length] = directory + "/logs/Application.log";
            attachFiles[files.length + 1] = directory + "/logs/DBApplication.log";

            if (attachFiles != null && attachFiles.length > 0) {
                // creates body part for the attachment
                MimeBodyPart attachPart = null;
                for (String filePath : attachFiles) {
                    File f = new File(filePath);
                    attachPart = new MimeBodyPart();
                    try {
                        if (f.exists() && !f.isDirectory()) {
                            attachPart.attachFile(filePath);
                        } else {
                            continue;

                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                    multipart.addBodyPart(attachPart);
                }
            }
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            msg.setContent(multipart);
            msg.saveChanges();

            // Send message
            Transport.send(msg);
            System.out.println("Email Sent Successfully!!");
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("sendAttachmentEmail Error: " + e.getMessage());
        }
    }

    private static String readFile(String file) {
        StringBuilder stringBuilder = null;
        String fileData = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String line = null;
            stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            fileData = stringBuilder.toString();
        } catch (Exception e) {
            System.err.println("File read Error: " + e.getMessage());
        }

        return fileData;
    }


    public static void main(String[] args) throws Exception {
        CONFIG = new Properties();
        FileInputStream cn = new FileInputStream(directory + "/src/test/resources/config/config.properties");
        System.setProperty("logfilename", directory + "/logs/Application.log");
        PropertyConfigurator.configure(directory + "/src/test/resources/config/log4j.properties");
        CONFIG.load(cn);
        //Decode data on other side, by processing encoded data
        byte[] valueDecoded = Base64.decodeBase64("c2hpbmVAMTIz".getBytes());
        final String fromEmail = "shineautomationreport@gmail.com"; //requires valid email id

        String[] testResultList = new String[4];
        int totalcase = 0;
        try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(directory + CONFIG.getProperty("testngResultFile")));
            // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList results = doc.getElementsByTagName("testng-results");
            Element firstNameElement = (Element) results.item(0);
            NamedNodeMap r2 = firstNameElement.getAttributes();
            System.out.println(r2.getLength());
            // get the pass, fail and skip counts of test case execution
            totalcase = Integer.parseInt(r2.getNamedItem("total").getNodeValue());
            passedCase = Integer.parseInt(r2.getNamedItem("passed").getNodeValue());
            failedItems = Integer.parseInt(r2.getNamedItem("failed").getNodeValue());
            skipItems = Integer.parseInt(r2.getNamedItem("skipped").getNodeValue());
            APP_LOGS.debug("Total: " + totalcase + " Passed: " + passedCase + " Failed: " + failedItems + " skipped: " + skipItems);
            testResultList[0] = "Total: " + totalcase;
            testResultList[1] = "Passed: " + (int)passedCase;
            testResultList[2] = "failed: " + (int)failedItems;
            testResultList[3] = "Skipped: " + (int)skipItems;

            APP_LOGS.debug("======>>Test Case Evaluation<<=======");
            APP_LOGS.debug("TOTAL: " + totalcase);
            APP_LOGS.debug("PASSED: " +passedCase);
            APP_LOGS.debug("FAILED: " + failedItems);
            APP_LOGS.debug("SKIPPED: " + skipItems);


        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        final String toEmail;
        String reportRAGStatus = "";
        if (directory.contains("Shine_Candidate_Registration_Test") || directory.contains("Shine_Candidate_Basic_Test")) {
            if(failedItems >= 26) {
            	reportRAGStatus = "[RED]";
            	toEmail = "manvi.agarwal@hindustantimes.com, qa.shinecandidate@gmail.com, rahul.gupta@hindustantimes.com, anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com, adarsh.aman@hindustantimes.com, megha.agarwal@hindustantimes.com, arpit.vijaywargiya@hindustantimes.com";
            	//toEmail = USER_EMAIL;
            	
            } 
        else if (failedItems >= 3) {
        		reportRAGStatus = "[AMBER]";
                toEmail = "manvi.agarwal@hindustantimes.com, qa.shinecandidate@gmail.com, rahul.gupta@hindustantimes.com, anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com, adarsh.aman@hindustantimes.com, megha.agarwal@hindustantimes.com, arpit.vijaywargiya@hindustantimes.com"; // any email id
                
                if (!System.getProperty("user.dir").toLowerCase().contains(SERVER_NAME)) {
                    // Code commented for future useEmailAlert obj = new EmailAlert();obj.sendAlert();*/
                }
            }
        
        else if (directory.contains("Shine_Candidate_Basic_Test") && failedItems >= 2) {
        	reportRAGStatus = "[AMBER]";
            toEmail = "manvi.agarwal@hindustantimes.com, qa.shinecandidate@gmail.com, rahul.gupta@hindustantimes.com, anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com, adarsh.aman@hindustantimes.com, luv.phogaat@hindustantimes.com"; // any email id
        }
            
        else {
        	    reportRAGStatus = "[Green]";
               // toEmail = USER_EMAIL; // any email id
        	     toEmail = "manvi.agarwal@hindustantimes.com, qa.shinecandidate@gmail.com, rahul.gupta@hindustantimes.com, vaibhav.baweja@hindustantimes.com, anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com, adarsh.aman@hindustantimes.com, luv.phogaat@hindustantimes.com";
        }
        } else {
        	
        	        	
            if (failedItems > 7 && skipItems > 3) {
            	reportRAGStatus = "[AMBER]";
                toEmail = "manvi.agarwal@hindustantimes.com, qa.shinecandidate@gmail.com, rahul.gupta@hindustantimes.com, anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com, adarsh.aman@hindustantimes.com, megha.agarwal@hindustantimes.com, arpit.vijaywargiya@hindustantimes.com";
            }            
           else {
            	reportRAGStatus = "[Green]"; 
                toEmail = "manvi.agarwal@hindustantimes.com, qa.shinecandidate@gmail.com, rahul.gupta@hindustantimes.com, vaibhav.baweja@hindustantimes.com,  anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com, adarsh.aman@hindustantimes.com, megha.agarwal@hindustantimes.com, arpit.vijaywargiya@hindustantimes.com"; // any email id
            }

        }

        Properties props = new Properties();
		/*
		props.put("mail.smtp.host", "pod51024.outlook.com"); //SMTP Host - office 
		// props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host - office
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		 */

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, new String(valueDecoded));
            }
        };

        Session session = Session.getInstance(props, auth);
        System.out.println("Authorization passed...");
        String baseUrl = "";
        if (directory.toLowerCase().contains(SERVER_NAME)) {
            baseUrl = System.getenv("build");
        } else if (directory.toLowerCase().contains("staging"))
            baseUrl = CONFIG.getProperty("staging_url");
        else
            baseUrl = CONFIG.getProperty("testSiteURL");

        /**
         * Send result summary
         */

        //Calling Email Function with attachment
        EmailUtil.sendAttachmentEmail(session, toEmail, " TR: " + reportRAGStatus + " "
        		+baseUrl+" " + Arrays.toString(testResultList), "Sir, Check your Automation report with attached logs.");
		/*DBConnect _Dbs = new DBConnect();
		_Dbs.insertData(CONFIG.getProperty("testSiteURL"), totalcase, Passedcase, failedItems, skipitems);*/


    }


    private static String createResult() {
        //writer.print("<center><h3>" + title + " - " + getDateAsString() + "</h3></center>");
        String baseUrl = "";
        if (directory.toLowerCase().contains(SERVER_NAME)) {
            baseUrl = CONFIG.getProperty("preprodURL");
        } else
            baseUrl = CONFIG.getProperty("testSiteURL");
        try {
            float total = passedCase + failedItems + skipItems;

            String data = "<tr><td colspan=2 align=center style='font-size:26px; color:#002d66;'>" + (int)total + "<br>Total</td>"
                    + "<td colspan=2 align=center style='font-size:26px; color:#33ff33;'>" + (int)passedCase + "<br>PASSED</td>"
                    + "<td colspan=2 align=center style='font-size:26px; color:salmon;'>" + (int)failedItems + "<br>FAILED</td>"
                    + "<td colspan=2 align=center style='font-size:26px; color: #ffce01;'>" + (int)skipItems + "<br>SKIPPED</td></tr>";

            data = data + createGraph();

            data = getIPAddress(baseUrl, data);
            return data;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return "<td colspan=8></td>";
        }


    }

    /**
     *
     * @param baseUrl
     * @param data
     * @return
     */
    public static String getIPAddress(String baseUrl, String data){
        try {
            InetAddress address = InetAddress.getByName(new java.net.URL(baseUrl).getHost());
            String ip = address.getHostAddress();
            if (directory.toLowerCase().contains(SERVER_NAME))
                ip = System.getenv("build");
            System.out.println(ip);
            System.out.println(address.getCanonicalHostName());
            String ipData = "<tr style='background:#00c0e4;'>"
                    + "<td colspan=2 align=center style='font-size:14px; color:white;'>" + ip + "<br><b>IP Address</b></td>"
                    + "<td colspan=6 align=center style='font-size:14px; color:white;'>" + address.getCanonicalHostName() + "<br><b>Host Name</b></td>"
                    + "</tr>";
            data = data + ipData;
        } catch (Exception e) {
            System.err.println("Erro in Getting ip address: " + e.getMessage());
        }
        return data;
    }

    private static String createGraph() {
        try {
            System.out.println("Passed case: " + passedCase);
            System.out.println("Failed case: " + failedItems);
            System.out.println("Skipped case: " + skipItems);
            float totalCases = passedCase + failedItems + skipItems;
            int passedPercent = Math.round((passedCase * 100) / totalCases);
            int failedPercent = Math.round((failedItems * 100) / totalCases);
            int skippedPercent = Math.round((skipItems * 100) / totalCases);
            System.out.println(totalCases);
            System.out.println("Passed case %: " + passedPercent);
            System.out.println("Failed case %: " + failedPercent);
            System.out.println("Skipped case %: " + skippedPercent);

            if (passedPercent < 4) {
                passedPercent = (int) Math.ceil((passedCase * 100) / totalCases);
            }

            if (skippedPercent < 4) {
                skippedPercent = (int) Math.ceil((skipItems * 100) / totalCases);
            }

            if (failedPercent < 4) {
                failedPercent = (int) Math.ceil((failedItems * 100) / totalCases);
            }

            return "<tr>\n" +
                    "    <td colspan=\"8\"><br>\n" +
                    "<img src=\"https://image-charts.com/chart?cht=bvg&chd=t:100," + passedPercent + "," + failedPercent + "," + skippedPercent + "&chco=76A4FB|dff4b6|fbc7a0|ffff00&chxt=x,y&chxl=0:|Total|Passed|Failed|Skipped&chs=700x200\">\n" +
                    "    </td>\n" +
                    "</tr>";

        } catch (Exception e) {
            System.err.println("Error in creating graph: " + e.getMessage());
            return "";
        }

    }

}	            

