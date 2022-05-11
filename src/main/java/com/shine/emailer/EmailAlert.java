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

public class EmailAlert {


	//********************* Email with attachment *********************
	private static Properties CONFIG;
	private static  int passedCase;
	private static int failedItems;
	private static int skipitems;
	static String directory = System.getProperty("user.dir");
	private static Logger APP_LOGS = Logger.getLogger("ShineCandidateLogger");

	private static String toEmail = "progressiveht@hindustantimes.com, manvi.agarwal@hindustantimes.com, rahul.gupta@hindustantimes.com, rajat.rastogi@hindustantimes.com";
	private static String subject = "";
	private static String baseUrl = "";

	public static void sendEmail(String body) throws Exception {
		try{

			//Decode data on other side, by processing encoded data
			byte[] valueDecoded= Base64.decodeBase64("c2hpbmVAMTIz".getBytes() );
			final String fromEmail = "shineautomationreport@gmail.com"; //requires valid email id

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


			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("f3282@htmedia.in", "alert@shine.com"));
			msg.setReplyTo(InternetAddress.parse("f3282@htmedia.in", false));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setHeader("X-Priority", "1");
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the bodypart in Email body
			messageBodyPart.setContent(body,"text/html");
			msg.setContent(body,"text/html");
			// Create a multipart message for attachment

			// creates body part for the attachment
			MimeBodyPart attachPart = new MimeBodyPart();
			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();
			File[] files = new File(directory+CONFIG.getProperty("scrfolder")).listFiles();
			String[] attachFiles = new String[files.length];
			int i =0;
			for(File file : files){
				if(file.isFile() && i<files.length){
					attachFiles[i] = file.getAbsolutePath();
				}
				i++;
			}
			if (attachFiles != null && attachFiles.length > 0) {
				for (String filePath : attachFiles) {
					File f = new File(filePath);
					attachPart = new MimeBodyPart();
					try {
						if(f.exists() && !f.isDirectory()){
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
		}catch (MessagingException e) {
			System.out.println(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



	public void sendAlert() throws Exception {
		CONFIG =new Properties();
		FileInputStream cn=new FileInputStream(directory+"/src/test/resources/config/config.properties");
		System.setProperty("logfilename", directory+"/logs/Application.log");
		PropertyConfigurator.configure(directory+"/src/test/resources/config/log4j.properties");
		CONFIG.load(cn);
		baseUrl =  CONFIG.getProperty("testSiteURL");
		String html  = getHTMLData();
		if(!html.equals(""));{
			sendEmail(html);
		}
	}

	public static String getHTMLData() {


		String[] text = new String[4];
		int totalcase = 0;
		String htmlBody = "";
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File(directory+CONFIG.getProperty("testngResultFile")));
			// normalize text representation
			doc.getDocumentElement ().normalize ();
			NodeList results = doc.getElementsByTagName("testng-results");
			Element firstNameElement = (Element)results.item(0);
			NamedNodeMap r2 = firstNameElement.getAttributes();
			System.out.println(r2.getLength());
			// get the pass, fail and skip counts of test case execution
			totalcase = Integer.parseInt(r2.getNamedItem("total").getNodeValue());
			passedCase = Integer.parseInt(r2.getNamedItem("passed").getNodeValue());
			failedItems = Integer.parseInt(r2.getNamedItem("failed").getNodeValue());
			skipitems = Integer.parseInt(r2.getNamedItem("skipped").getNodeValue());
			System.out.println("Total: "+totalcase+" Passed: "+passedCase+" Failed: "+failedItems+" skipped: "+skipitems);
			text[0] = "Total: "+totalcase;
			text[1] = "Passed: "+passedCase;
			text[2] = "failed: "+failedItems;
			text[3] = "Skipped: "+skipitems;

			APP_LOGS.debug("======>>Test Case Evaluation<<=======");
			APP_LOGS.debug("TOTAL: "+totalcase);
			APP_LOGS.debug("PASSED: "+passedCase);
			APP_LOGS.debug("FAILED: "+failedItems);
			APP_LOGS.debug("SKIPPED: "+skipitems);

			subject = "Shine Alert!! "+Arrays.toString(text);

			htmlBody =readFile(directory+CONFIG.getProperty("alertReport"));

			try{
				InetAddress address = InetAddress.getByName(new java.net.URL(baseUrl).getHost());
				String ip = address.getHostAddress();
				System.out.println(ip);
				System.out.println(address.getCanonicalHostName());
				String data2 = "<table style='width: 100%;'><tr style='background:#00c0e4;'>"
						+"<td colspan=2 align=center style='font-size:14px; color:white;'>"+ip+"<br><b>IP Address</b></td>"
						+"<td colspan=6 align=center style='font-size:14px; color:white;'>"+address.getCanonicalHostName()+"<br><b>Host Name</b></td>"
						+ "</tr></table>";
				htmlBody = htmlBody.replace("<span style='display:none;'>{{ng-report}}</span>", data2).replace("{{ng-url}}", baseUrl);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}

			return htmlBody;

		} catch(Exception e){
			APP_LOGS.debug("Exception: "+e.getMessage());
			return "";
		}


	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static String readFile( String file ) throws Exception {
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
            APP_LOGS.debug("Exception: " + e.getMessage());
            return "";
        }
    }


}	            
