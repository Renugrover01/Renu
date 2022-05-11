package com.shine.emailer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
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

public class SiteMapMailer {

	//********************* Email with attachment *********************
	public static Properties CONFIG;
	public static  String total_count;
	public static  String passed_count;
	public static String failed_count;
	public static String skip_count;

	public static void sendAttachmentEmail(Session session, String toEmail, String subject, String body) throws Exception {
		try{
			MimeMessage msg = new MimeMessage(session);      
			msg.setFrom(new InternetAddress("f3282@htmedia.in", "alert@shine.com"));
			msg.setReplyTo(InternetAddress.parse("f3282@htmedia.in", false));

			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			msg.setHeader("X-Priority", "1");

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			//Calling function to attach the HTML report in a Email Body

			//body=readFile(System.getProperty("user.dir")+"\\test-output\\emailable-report.html");
			body=readFile(System.getProperty("user.dir")+"/src/test/resources/reports/sitemap_report.html");

			// Fill the bodypart in Email body
			messageBodyPart.setContent(body,"text/html");
			msg.setContent(body,"text/html");
			msg.saveChanges();

			// Send message
			Transport.send(msg);
			System.out.println("Email Sent Successfully!!");
		}catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static String readFile( String file ) throws Exception {
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader( new FileReader (file));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");

		while( ( line = reader.readLine() ) != null ) {
			stringBuilder.append( line );
			stringBuilder.append( ls );
		}

		return stringBuilder.toString();
	}



	public void send_email(int valid_count) throws Exception{
		//Decode data on other side, by processing encoded data
		final byte[] valueDecoded= Base64.decodeBase64("c2hpbmVAMTIz".getBytes() );
		final String fromEmail = "shineautomationreport@gmail.com"; //requires valid email id

		System.out.println("value of passed test cases is..... "+ passed_count);
		final String toEmail;
		toEmail = "rahul.gupta@hindustantimes.com, vaibhav.baweja@hindustantimes.com, rishabh.bothra@hindustantimes.com, manvi.agarwal@hindustantimes.com, naman.agrawal@hindustantimes.com, anupam.prasad@hindustantimes.com, gagan.malhotra@hindustantimes.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");


		//create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail,  new String(valueDecoded));
			}
		};

		Session session = Session.getInstance(props, auth);
		//	System.out.println(CONFIG.getProperty("testSiteURL"));
		System.out.println("Authorization passed...");

		
		String message = "";
/*		if(valid_count>=17&&valid_count<=25)
			 message = "Passed - "+valid_count+" distinct Sitemap count found!!!";
		else if(valid_count<17)
			 message = "Failure - Only "+valid_count+" distinct Sitemap count found!!!";
		else if(valid_count>25)
			 message = "Failure - "+valid_count+" distinct Sitemap count found!!!";
		*/
		
		
		if(valid_count==20)
			 message = "Passed - "+valid_count+" distinct Sitemap count found!!!";
		else if(valid_count<20)
			 message = "Failure - Only "+valid_count+" distinct Sitemap count found!!!";
		else if(valid_count>20)
			 message = "Failure - "+valid_count+" distinct Sitemap count found!!!";
       
		
		//Calling Email Function with attachment
		sendAttachmentEmail(session, toEmail,message , "Sir, Check your Automation report with attached logs.");

	}     



}	            
