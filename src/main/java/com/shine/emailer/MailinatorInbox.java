package com.shine.emailer;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class MailinatorInbox extends TestBaseSetup  {

	WebDriver _Maildriver;
	private By goButton   = By.cssSelector(".btn.btn-dark");
	private By inboxfield = By.id("inboxfield");
	private By inboxCount = By.xpath("//*[@id='inboxpane']/li");
	private By passwordDiv = By.xpath("//td[contains(text(), 'Your password: ')]/strong");
	//private By passwordDiv = By.id("password");
	private String murl   = "";
	
	@Test
	public void tt() {
		System.err.println(getUserPassword("dhruvkhatkar"));
	}

	/**
	 * @Method - Read user password from mailinator inbox
	 * @param emailId
	 * @return
	 */
	public String getUserPassword(String emailId) {
		String password = null;
		_Maildriver = getDriver(_Maildriver);
		murl = "https://www.mailinator.com";
		try {
			openMailinatorSite();
			enterEmailid(emailId);
			clickOnSubmit();
			List <WebElement> emailList = _Maildriver.findElements(inboxCount);
			for(WebElement email: emailList) {
				String subjectLine = email.getText();
				APP_LOGS.debug("Email Subject Line: "+subjectLine);
				if(subjectLine.contains("Your Shine Password")) {
					email.click();
					_Maildriver.switchTo().frame(_Maildriver.findElement(By.id("msg_body")));
					password = _Maildriver.findElement(passwordDiv).getText().trim();
					APP_LOGS.debug("Email password: "+password);
					break;
				}
			}

			quitbrowser();
			return password;
		}
		catch(Throwable t) {
			return password;
		}
	}

	public void openMailinatorSite(){
		_Maildriver.navigate().to(murl);
		_Maildriver.manage().window().maximize();
	}

	public void enterEmailid(String email){
		_Maildriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		_Maildriver.findElement(inboxfield).sendKeys(email);
	}

	public void clickOnSubmit(){
		_Maildriver.findElement(goButton).click();
	}

	public void quitbrowser() {
		if(_Maildriver!=null)
			_Maildriver.quit();

	}
}


