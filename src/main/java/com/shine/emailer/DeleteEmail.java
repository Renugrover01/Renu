package com.shine.emailer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.shine.base.TestBaseSetup;

public class DeleteEmail extends TestBaseSetup{
    static boolean check = false;
	public static boolean delete(String pop3Host, String storeType, String user, String password) {
		try {
			// get the session object
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3s.host", pop3Host);
			properties.put("mail.pop3s.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			//properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			Session emailSession = Session.getDefaultInstance(properties);
			// emailSession.setDebug(true);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(pop3Host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			if (!emailFolder.exists()) {  
				 System.out.println("inbox not found");  
				 System.exit(0);  
				 } 
			emailFolder.open(Folder.READ_WRITE);

			@SuppressWarnings("unused")
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				APP_LOGS.info("---------------------------------");
				APP_LOGS.info("Email Number " + (i + 1));
				APP_LOGS.info("Subject: " + message.getSubject());
				APP_LOGS.info("From: " + message.getFrom()[0]);

				String subject = message.getSubject();
				message.setFlag(Flags.Flag.DELETED, true);
				APP_LOGS.info("Marked DELETE for message: " + subject);
				System.out.println("Marked DELETE for message: " + subject);
				
			}
			// expunges the folder to remove messages which are marked deleted
			emailFolder.close(true);
			
			store.close();
			check = true;

		} catch (NoSuchProviderException e) {
			check = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			check = false;
			e.printStackTrace();
		}
		return check;
	}

}
