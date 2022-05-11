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

public class AutoLoginTokenEmail {


	//********************* Email with attachment *********************


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
			String htmlBody = body;
			htmlBody = readFile(System.getProperty("user.dir")+"/src/test/resources/reports/auto_login_report.html");

			// Fill the bodypart in Email body
			messageBodyPart.setContent(htmlBody,"text/html");
			msg.setContent(htmlBody,"text/html");
			msg.saveChanges();

			// Send message
			Transport.send(msg);
			System.out.println("Email Sent Successfully!!");
		}catch (MessagingException e) {
			System.out.println("MessagingException: "+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException: "+e.getMessage());
		}
	}

	private static String readFile( String file ) {
		try(
		BufferedReader reader = new BufferedReader( new FileReader (file));
		) {
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		}catch(Exception ex){
			System.out.println("Error in reading file: "+ex.getMessage());
		}
		return "";
	}



	public void send_email() throws Exception {
		//Decode data on other side, by processing encoded data
		final byte[] valueDecoded= Base64.decodeBase64("c2hpbmVAMTIz".getBytes() );
		final String fromEmail = "shineautomationreport@gmail.com"; //requires valid email id

		final String toEmail;
		//toEmail = "abhishek.dhoundiyal@hindustantimes.com";
		toEmail = "apoorva.pandey@hindustantimes.com, rahul.gupta@hindustantimes.com, vaibhav.baweja@hindustantimes.com, manvi.agarwal@hindustantimes.com";


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

		//Calling Email Function with attachment
		sendAttachmentEmail(session, toEmail, "Auto login token" , "Sir/Mam, Check your Automation report with attached logs.");

	}

	    


}	            
