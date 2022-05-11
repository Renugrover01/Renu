/**
 * Common utility methods
 * @author f3282
 */

package com.shine.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.shine.base.TestBaseSetup;


public class CommonUtils extends TestBaseSetup {


	/**
	 * Debugger Tool Removal
	 * @param driver
	 * @param id
	 */
	public void debuggerRemover(WebDriver driver, String id){
		try{
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String todisable = "document.getElementById('"+id+"').style.display = 'none';";
			javascript.executeScript(todisable);
		}
		catch(Exception ex){
			APP_LOGS.error("No Django debbuger toolbar Found");
		}
	}

	/**
	 * pop up notification handler
	 * 
	 * @param driver
	 */
	public void clickOnNotification(WebDriver driver){
		try{
			APP_LOGS.debug("---Trying to close Notification popup---");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='js-close-noti-popup pushNotification']")));
			driver.findElement(By.xpath("//*[@class='js-close-noti-popup pushNotification']")).click();
			APP_LOGS.debug("-----------Notification Closed----------");
		}
		catch(Exception ex){
			APP_LOGS.debug("-----------No Notification Found---------");
		}
	}

	/**
	 * @param driver
	 */
	public void dirCleaner(WebDriver driver) {
		File f = new File(CONFIG.getProperty("scrfolder"));
		try {
			FileUtils.cleanDirectory(f);
		} catch (IOException e) {
            APP_LOGS.debug(e.getMessage());
		}

	}

	/**
	 * @param alertDriver
	 */

	public void Accept_Alert(WebDriver alertDriver){
		try{
			Alert alert = alertDriver.switchTo().alert();
			alert.accept();
		}catch(Exception ex){
			APP_LOGS.error("No alert Found");
		}
	}


	public void dismiss_Alert(WebDriver alertDriver){
		try{
			alertDriver.switchTo().alert().dismiss();
		}catch(Exception ex){
			APP_LOGS.error("No alert Found");
		}
	}

	public static void pagedown(WebDriver driver){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, 250);");

	}
	/**
	 * close web engage
	 * 
	 * @param webengdriver
	 */
	public static void closewebengage(WebDriver webengdriver){
		webengdriver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
		webengdriver.findElement(By.xpath("//*[@id='webklipper-publisher-widget-container-notification-close-div']")).click();
		webengdriver.switchTo().parentFrame();
	}

	/**
	 * Common method for Web-driver wait
	 * 
	 * @param driver
	 * @param locator
	 * @param timeOutInSeconds
	 */
	public void explicitlyWaitForElementToLoad(WebDriver driver, By locator, long timeOutInSeconds) {
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
		/*new WebDriverWait(driver, timeOutInSeconds).until(new Function<WebDriver, Boolean>() {
			Boolean isElementFound = Boolean.FALSE;
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(locator);
					isElementFound = element.isDisplayed();
				} catch (NoSuchWindowException e) {
					System.out.println("Your Window Name not found");
					System.out.println(driver.getTitle());
					return isElementFound;
				}
				return isElementFound;
			}
		});*/
	}
	/**
	 * Fluent wait implementation
	 * @param driver
	 * @param locator
	 * @param timeOutInSeconds
	 * @param pooltimeOutInSeconds
	 */
	public void fluentlyWaitForElementToLoad(WebDriver driver, By locator, long timeOutInSeconds, long pooltimeOutInSeconds) {
		// Waiting [timeOutInSeconds] seconds for an element to be present on the page, checking
		// for its presence once every [pooltimeOutInSeconds] seconds.
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		.withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
		.pollingEvery(pooltimeOutInSeconds, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class);
		@SuppressWarnings("unused")
		WebElement foo = wait.until(new Function<WebDriver, WebElement>() 
		{
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

	}

	/**
	 * Method will take screenshot and create image type jpg
	 * @param imageDriver
	 * @param fileName
	 * @param filePath
	 * @throws IOException
	 * @type   JPG
	 */
	public String createImage(WebDriver imageDriver, String fileName, String filePath) throws IOException {
		File scrFile = ((TakesScreenshot) imageDriver).getScreenshotAs(OutputType.FILE);
		String file = filePath+fileName+".jpg";
		FileUtils.copyFile(scrFile, new File(file));	
		return file;

	}

	/**
	 * 
	 * @param time
	 */
	public void Thread_Sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			APP_LOGS.error("Interrupted! "+e);
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 
	 * @param by
	 * @param driver
	 */
	public void scrollTOElement(By by, WebDriver driver){
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
	}
	/**
	 * 
	 * @param el
	 * @param driver
	 */
	public void scrollTOElement(WebElement el, WebDriver driver){
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
	}

	/**
	 * 
	 * @param driver
	 */
	public void scroll_down(WebDriver driver){
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000);");
	}
	/**
	 * 
	 * @param driver
	 */
	public void scroll_up(WebDriver driver){
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-1000);");
	}
	
	public void elementDisplayPropertySetter_class(String setStaus, String class_path, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		try {
			executor.executeScript("document.getElementsByClassName('"+class_path+"')[0].style.display='"+setStaus+"';");
		}catch(Exception ex) {
			APP_LOGS.debug("No "+class_path+" found");
		}

	}
	
	public void elementDisplayPropertySetter_class(int index, String setStaus, String class_path, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		try {
			executor.executeScript("document.getElementsByClassName('"+class_path+"')["+index+"].style.display='"+setStaus+"';");
		}catch(Exception ex) {
			APP_LOGS.debug("No "+class_path+" found");
		}

	}


	/**
	 * 
	 * @param menu
	 * @param subMenu
	 * @param driver
	 */
	public void openMenuLink(By menu, By subMenu, WebDriver driver){
		WebDriverWait wait= new WebDriverWait(driver, 20);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(menu)).build().perform();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(subMenu));
		action.moveToElement(driver.findElement(subMenu));
		action.click().build().perform();

	}
	/**
	 * 
	 * @param setStaus
	 * @param id
	 * @param driver
	 */
	public void elementDisplayPropertySetter(String setStaus, String id, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("document.getElementById('"+id+"').style.display='"+setStaus+"';");
	}

	/**
	 * 
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	/**
	 * Get current url
	 * @param driver
	 * @return
	 */
	public String getCurrentPageURL(WebDriver driver){
		try{
			return driver.getCurrentUrl();
		}
		catch(Exception ex){
			return "";
		}
	}

	/**
	 * Hack to avoid unnecessary marketing popup
	 * @param driver
	 */
	public void set_flag_checkTimeStamp(WebDriver driver) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			long dt = new Date().getTime();
			APP_LOGS.debug("Set timestamp field to: "+dt);
			jse.executeScript("return localStorage.setItem('checkTimeStamp',"+dt+")");
			String checkTimeStamp = (String) jse.executeScript("return localStorage.checkTimeStamp");
			APP_LOGS.debug("Get timestamp field: "+checkTimeStamp);
		}
		catch(Throwable t){
			APP_LOGS.fatal("set_flag_checkTimeStamp method failed: "+t.getMessage());
		}

	}


	/**
	 * Generate random number
	 * @return
	 */
	public String get_random_string() {
		return RandomStringUtils.random(15, true, false);
	}

	public String get_user_uid(WebDriver driver) {
		String uid = "";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			uid = String.valueOf(jse.executeScript("return sc.UID"));
		}catch(Exception ex) {
            APP_LOGS.debug(ex.getMessage());
		}
		return uid;
	}


	/**
	 * 
	 * @param by
	 * @param driver
	 */
	public void javascript_click(By by, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", driver.findElement(by));

	}
	public void javascript_click(WebElement ele, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", ele);

	}



	/**************MSITE*************/

	public void set_cookie(WebDriver driver, String cookieName, String cookieValue) {
		Cookie name = new Cookie(cookieName, cookieValue);
		driver.manage().addCookie(name);		
		// After adding the cookie we will check that by displaying all the cookies.
		Set<Cookie> cookiesList =  driver.manage().getCookies();
		for(Cookie getcookies :cookiesList) {
			APP_LOGS.info("Cookie = "+getcookies );
		}
	}

	public void remove_InterstitialBanner(WebDriver driver) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("return sessionStorage.setItem('InterstitialBanner', true);");
			APP_LOGS.info("Interstitial banner set to true");
		}catch(Exception e) {
			APP_LOGS.debug("Interstitial Banner Not Set to True = "+e.getMessage());
		}
	}
	public void closeAppPopup(WebDriver _CloseAppPopupDriver) {
		try{
			scrollTOElement(By.cssSelector("a.cls_link.link"), _CloseAppPopupDriver);
			_CloseAppPopupDriver.findElement(By.cssSelector("a.cls_link.link")).click();
			APP_LOGS.info("Interstitial banner appears");
		} catch (Exception e) {
			// This block is to catch app pop up
			APP_LOGS.info("No Interstitial banner appears");
		}
		remove_InterstitialBanner(_CloseAppPopupDriver);
	}

	public void closeNotification(WebDriver driver){
		try{
			driver.findElement(By.cssSelector(".js-close-noti-popup.pushNotification")).click();
		}
		catch(Exception ex){
			APP_LOGS.info("No Notification");
		}
	}


	public int randomIndex(int size) {

		int randomNum = 0;
		try {
			randomNum = ThreadLocalRandom.current().nextInt(1, size);
			return randomNum;
		}catch(IllegalArgumentException ilarg){
			return 1;
		}
	}


	/*************************************RANDOM EMAIL ID GENERATOR******************************************************/

	/*
	 * Generate Email Id logic
	 * This method will generate random users
	 * 
	 */
	public static final List<String> email_list = new ArrayList<>();
	public String generateEmailid() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		String email_id_generated = generateName()+dateFormat.format(date)+"@mailinator.com";
		APP_LOGS.debug("Generated Email Id is: "+email_id_generated);
		if(!email_id_generated.isEmpty()) {
			email_list.add(email_id_generated);
			APP_LOGS.debug("Email Id list is: "+email_list);
		}
		return email_id_generated;
	}

    /**
     * @return
     */
    public static String getRandomString(int length) {
        String range = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder data = new StringBuilder();
        SecureRandom rand = new SecureRandom(); // Compliant for security-sensitive use cases
        while (data.length() < length) {
            int index = (int) (rand.nextFloat() * range.length());
            data.append(range.charAt(index));
        }
        return data.toString();

    }

    /**
     * Method to get Date as String
     *
     * @return
     */
    public String getDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static String getSaltString() {
		String range = "1234567890";
		StringBuilder data = new StringBuilder();
        // SecureRandom is preferred to Random
        SecureRandom rand  = new SecureRandom(); // Compliant for security-sensitive use cases
		while (data.length() < 6) {
			int index = (int) (rand.nextFloat() * range.length());
			data.append(range.charAt(index));
		}
		return data.toString();

	}

	private static String[] Beginning = { "manvi", "rahul", "kanak","sanjana","karan","sunil", "rajesh", "shubham",
			"amit", "ajay", "rohit", "neha", "aditi", "pooja", "chandini", "mohit", "sidharth",
			"rahul", "ankit", "piyush", "sachin", "amita", "sunny", "isha", "esha", "anjali",
			"ankita", "richa", "shivangi", "shweta", "sameer", "sakshi", "geetika", "arvind", "Aakash", "Alok", "Aman", "Panav",
			"deepak", "dhruv", "sagar", "hemang", "anju", "reema", "harsha", "kiran", "suresh", "harish", "sudha", "vijay", "sanjeev",
			"vikrant", "sanjay", "mohan", "Jignesh", "manish", "narayan", "shilpa", "gagan", "dinesh", "prachi", "sanjay", "parveen", "meenakshi", 
			"dhirendra", "saroj", "yashpal", "sheenu", "raghu", "kapil", "rishi", "niraj", "preeti", "vaishali", "bipin", "saloni", "tina", "satish"

	};

	private static String[] End = { "gupta", "sharma", "dutta","rawat", "thakur", "kaushik", "yadav", "verma",
			"bisht", "anand", "arora", "aggarwal", "das", "singh", "kaur", "kejriwal", "gandhi", "patel",
			"reddy", "bakshi", "banerjee", "bhatt", "Chawla", "jain", "chopra", "kapoor", "dubey",
			"sahani", "raut", "nigam", "saxena", "patil", "jamwal", "sethy", "khosla", "chakraborty", "jha", 
			"negi", "ghosh", "agrawal", "tanwar", "srivastava", "mishra", "upadhyay", "goswami", "prasad", "joshi"


	};



	public static String generateName() {
        SecureRandom rand = new SecureRandom(); // Compliant for security-sensitive use cases
		String firstname = Beginning[rand.nextInt(Beginning.length)].toLowerCase();
		String lastname = End[rand.nextInt(End.length)].toLowerCase();
		TestBaseSetup.username = firstname+" "+lastname;
		return firstname+"_"+lastname;
	}



}

