package com.shine.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.shine.common.utils.CommonUtils;
import com.shine.listener.LogAnalyzer;

@SuppressWarnings("unchecked")
public class TestBaseSetup {

	public static Properties CONFIG;
	public static Logger APP_LOGS		= Logger.getLogger("ShineCandidateLogger");
	public static CommonUtils _Utility	= new CommonUtils();

	public static String badprofile_email = "";

	public static String baseUrl;
	public static String browserType;
	static String driverPath			= "D:/browser_selenium/";

	public static String emailid		= "";
	public static String email_main		= "";
	public static String email_hc		= "qa.shinecandidate@gmail.com";
	public static String pass_hc		= "123456";
	public static String email_new      = "manvi.agarwal191018@gmail.com";
	public static String pass_new       = "123456";
	public static String userDirectory  = System.getProperty("user.dir");
	public static String username;
	public static String user_id		=  "5e424268ba0a186618881a19";

	/*Sauce Lab credentials*/
	public static final String USERNAME	  = "abhishekqaht";
	public static final String ACCESS_KEY = "53dc7d04-5924-4fb7-97c5-1486a7404ec1";
	public static final String URL        = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

	public static boolean isPreprod		= false;

	public static String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36";
	//"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36";

	public static String mobile_user_agent = "Mozilla/5.0 (Linux; U; Android 7.1.1; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.";

	/*Click event counter list*/
	public static ArrayList<Integer> clickEventList = new ArrayList<Integer>();
	
	public static Faker _Faker	= new Faker(new Locale("en-IND"));
	
	
	@BeforeSuite
	public static void setUp() throws IOException {
		System.setProperty("logfilename", userDirectory+"/logs/Application.log");
		APP_LOGS = Logger.getLogger("ShineCandidateLogger");
		PropertyConfigurator.configure(userDirectory+"/src/test/resources/config/log4j.properties");
		CONFIG = new Properties();
		FileInputStream cn = new FileInputStream(userDirectory+"/src/test/resources/config/config.properties");
		CONFIG.load(cn);
		/*Preprod hack*/
		if(userDirectory.toLowerCase().contains("preprod")){
			//baseUrl = CONFIG.getProperty("preprodURL");
			baseUrl = System.getenv("build");
			isPreprod = true;
		}
		else if(userDirectory.toLowerCase().contains("staging"))
			baseUrl = CONFIG.getProperty("staging_url");
		else 
			baseUrl = CONFIG.getProperty("testSiteURL");
		browserType = CONFIG.getProperty("browser");
		APP_LOGS.debug("Started the test");
		APP_LOGS.info("User directory found: "+userDirectory);
	}

	/**
	 * Set driver according to the preference - global.
	 * @param driverType -Java generic return type
	 * @return
	 */
	public <T> T getDriver(T driverType) {
		return (T) setDriver(browserType, driverType);
	}

	/**
	 * Set driver according to the preference - hard-coded.
	 * @param driverType
	 * @param browsername
	 * @return
	 */
	public <T> T getDriver(T driverType, String browsername) {
		return (T) setDriver(browsername, driverType);
	}


	/**
	 * Match and call the appropriate driver
	 * @param browserType
	 * @param driverType
	 */


	private <T> T setDriver(String browserType, T driverType) {
		switch (browserType.toLowerCase()) {
		case "chrome":
			driverType = (T) initChromeDriver();
			break;
		case "saucelab":
			driverType = (T) initSauceLab();
			break;
		case "firefox":
			driverType = (T) initFirefoxDriver();
			break;
		case "chromemobile":
			driverType = (T) initMobileChromeDriver();
			break;
		default:
			APP_LOGS.info("Browser : " + browserType+ " is invalid, Launching Chrome as default browser of choice..");
			driverType = (T) initChromeDriver();
		}
		return driverType;
	}


	/**
	 * Chrome driver sauce lab implementation
	 * @return WebDriver
	 */
	private WebDriver initSauceLab() {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 7");
		caps.setCapability("browser", "chrome");
		caps.setCapability("version", "58");
		URL url = null;
		try {
			url = new URL(URL);
		} catch (MalformedURLException e) {
			APP_LOGS.error("Incorrect URL: "+e.getMessage());
		}
		WebDriver driver = new RemoteWebDriver(url, caps);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}


	/**
	 * Chrome driver web implementation
	 * @return WebDriver
	 */
	private static WebDriver initChromeDriver() {
		APP_LOGS.info("Launching google chrome with new profile..");
		String OS = System.getProperty("os.name").toString().toLowerCase();
		APP_LOGS.debug(OS);
		boolean macFlag = false;
		ChromeOptions options = new ChromeOptions();
		if(OS.indexOf("mac") >= 0) {
			macFlag = true;
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriverpath_mac"));
		}
		else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
			APP_LOGS.debug(CONFIG.getProperty("chromedriverpath_linux"));
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriverpath_linux"));
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			//options.addArguments("screenshot");
		}
		else {
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriverpath"));
		}
		//System.setProperty("webdriver.http.factory", "apache");

		if(CONFIG.getProperty("isBlockerEnable").contains("true")) {
			options.addExtensions(new File(userDirectory+"/src/test/resources/extensions/uBlock.crx"));
			options.addExtensions(new File(userDirectory+"/src/test/resources/extensions/chrome-ga-optout-extension.crx"));
		}
		else options.addArguments("--disable-extensions");
		
		options.addArguments("--allow-no-sandbox-job");
		options.addArguments("--ignore-certificate-errors"); 
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("disable-infobars");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-plugins-discovery");
		options.addArguments("--profile-directory=Default");
		options.addArguments("--fast-start");	
		options.addArguments("--disable-gpu");

		options.addArguments("--user-agent="+user_agent);

		options.setAcceptInsecureCerts(true);
		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		logPrefs.enable(LogType.DRIVER, Level.ALL);

		//options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		options.setCapability( "goog:loggingPrefs", logPrefs );

		//Hack for - Chrome is being controlled by automated test software
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));


		String downloadFilepath = System.getProperty("user.dir")+"/downloads/";
		APP_LOGS.debug("Chrome Download path set to: "+downloadFilepath);
		File downloadt_folder = new File(downloadFilepath);
		if (!downloadt_folder.exists()){
			downloadt_folder.mkdir();
		}

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.prompt_for_download", false);
		prefs.put("download.default_directory", downloadFilepath);
		prefs.put("profile.default_content_setting_values.notifications", 1);


		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = null;
		ChromeDriverService driverService = ChromeDriverService.createDefaultService();
		try {
			driver = new ChromeDriver(driverService, options);
		}catch(Throwable t) {
			APP_LOGS.error(t.getMessage());
			t.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if(macFlag==true) {
			Dimension dim = new Dimension(1250,800);
			driver.manage().window().setSize(dim);
		}

		/**** RESUME DOWNLOAD HACK ON HEADLESS BROWSERS *****/
		Map<String, Object> commandParams = new HashMap<>();
		commandParams.put("cmd", "Page.setDownloadBehavior");
		Map<String, String> params = new HashMap<>();
		params.put("behavior", "allow");
		params.put("downloadPath", downloadFilepath);
		commandParams.put("params", params);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String command = objectMapper.writeValueAsString(commandParams);
			String u = driverService.getUrl().toString() + "/session/" + ((RemoteWebDriver)driver).getSessionId() + "/chromium/send_command";
			APP_LOGS.debug(u);
			HttpPost request = new HttpPost(u);
			request.addHeader("content-type", "application/json");
			request.setEntity(new StringEntity(command));
			httpClient.execute(request);
		}catch (Exception e) {
			APP_LOGS.debug("resume downloaded hack is not working: "+e.getMessage());
		}
		/**********************END***********************/
		return driver;
	}



	/**
	 * Chromedriver windows web emulator implementation
	 * @return Web-driver type object
	 */
	public static WebDriver initMobileChromeDriver() {
		String OS = System.getProperty("os.name").toString().toLowerCase();
		APP_LOGS.debug("Detecting os : "+OS);
		ChromeOptions options = new ChromeOptions();
		if(OS.indexOf("mac") >= 0) {
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriverpath_mac"));
		}
		else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriverpath_linux"));
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
		}
		else {
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriverpath"));
		}


		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Nexus 5");
		//mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		logPrefs.enable(LogType.DRIVER, Level.ALL);

		options.setExperimentalOption("mobileEmulation", mobileEmulation);
		options.addArguments("--allow-no-sandbox-job");
		options.addArguments("--ignore-certificate-errors"); 
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-plugins-discovery");
		options.addArguments("--profile-directory=Default");
		options.addArguments("--disable-notifications");
		options.addArguments("--fast-start");
		options.addArguments("--disable-gpu");
		options.addArguments("--user-agent="+mobile_user_agent);

		options.setAcceptInsecureCerts(true);
		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

		options.setCapability( "goog:loggingPrefs", logPrefs );
		//options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		/*Hack for - Chrome is being controlled by automated test software*/
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		options.setExperimentalOption("prefs", prefs);

		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().setPosition(new Point(0, 0));
		//driver.manage().window().setSize(new Dimension(376, 730));
		driver.manage().window().setSize(new Dimension(450, 720));
		return driver;
	}


	/**
	 * Firefox driver web implementation
	 * @return WebDriver
	 */
	private static WebDriver initFirefoxDriver() {
		APP_LOGS.info("Launching Firefox browser..");
		System.setProperty("webdriver.gecko.driver", CONFIG.getProperty("firefoxdriverpath"));
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}



	/****************************COMMON METHOD *********************************************************/	


	/** 
	 * Open Base URL mentioned on the project config file
	 * @param _OpenBrowserDriver
	 * @throws Exception
	 */
	public static void OpenBaseUrl(WebDriver _OpenBrowserDriver){	
		System.setProperty("java.net.preferIPv4Stack" , "true");
		_OpenBrowserDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//_OpenBrowserDriver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		try {
			_OpenBrowserDriver.navigate().to(baseUrl);
			_Utility.set_flag_checkTimeStamp(_OpenBrowserDriver);
		}catch(Throwable t) {
			JavascriptExecutor js = (JavascriptExecutor) _OpenBrowserDriver;
			for (int i=0; i<25; i++){ 
				_Utility.Thread_Sleep(1000);
				String isReady = js.executeScript("return document.readyState").toString();
				//To check page ready state.
				if (isReady.equals("complete")){ 
					APP_LOGS.debug("is Dom Ready: "+isReady);
					break; 
				}   
			}
			try {
				APP_LOGS.debug("---Tryin to Stop loading-----");
				js.executeScript("return window.stop()");
				APP_LOGS.debug("---Done-----");
				_OpenBrowserDriver.navigate().to(baseUrl);
				_Utility.set_flag_checkTimeStamp(_OpenBrowserDriver);
				APP_LOGS.debug("---Navigation Done-----");
			}catch(Throwable er) {}
			_Utility.Thread_Sleep(1000);
			_OpenBrowserDriver.navigate().to(baseUrl);
			_Utility.set_flag_checkTimeStamp(_OpenBrowserDriver);
		}
	}


	/**
	 * Login method
	 * @param _Logindriver
	 * @param email
	 * @throws Exception
	 */
	public static void loggedInShine(WebDriver _Logindriver, String email, String password) {
		OpenBaseUrl(_Logindriver);
		_Logindriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		_Utility.Thread_Sleep(2000);
		_Logindriver.findElement(By.partialLinkText("Sign in")).click();
		_Utility.Thread_Sleep(2000);
		_Logindriver.findElement(By.id("id_email_login")).clear();
		APP_LOGS.debug("Shine Login with email id:  "+email);
		_Logindriver.findElement(By.id("id_email_login")).sendKeys(email);
		_Logindriver.findElement(By.id("id_password")).clear();
		_Logindriver.findElement(By.id("id_password")).sendKeys(password);
		_Logindriver.findElement(By.name("login_popup")).click();
		_Utility.Thread_Sleep(3000);
	}



	/**
	 * Take Screenshot on failure and send failure live data to telegram api
	 * @param testResult
	 * @param driver
	 */
	public static void takeScreenshotOnFailure(ITestResult testResult, WebDriver driver){
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
			//get current date time with Date()
			Date date = new Date();
			if (testResult.getStatus() == ITestResult.FAILURE) {
				APP_LOGS.debug("[RESULT: "+new Exception().getStackTrace()[1].getClassName()+"."+testResult.getName() + " - FAILED]");
				LogAnalyzer.analyzeLog(driver);
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String directory = userDirectory+CONFIG.getProperty("scrfolder")+testResult.getName()+"_"+dateFormat.format(date)+".jpg";
				FileUtils.copyFile(scrFile, new File(directory));	
			} 
			else if(testResult.getStatus() == ITestResult.SUCCESS){
				APP_LOGS.debug("[RESULT: "+new Exception().getStackTrace()[1].getClassName()+"."+testResult.getName() + " - PASSED]");
			}
			else{
				APP_LOGS.debug("[RESULT: "+new Exception().getStackTrace()[1].getClassName()+"."+testResult.getName() + " - SKIPPED]");
			}

		}
		catch(Exception ex){
			APP_LOGS.fatal(testResult.getName()+": "+ex.getMessage());
		}

	}


}


