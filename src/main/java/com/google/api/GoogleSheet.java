package com.google.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shine.base.TestBaseSetup;

public class GoogleSheet extends TestBaseSetup 
{

	private static String accessToken ="";
	private static String sheetId ="";


	public GoogleSheet() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = dateFormat.format(date);
		sheetId ="1LMA4HyPTsYh14nXT8rgYGp7nMlV79QuIOmbsGWozLTM";
		APP_LOGS.debug(year+" Sheet id: "+sheetId);
	}

	public static void updateExcel(ArrayList<Integer> responseArray, int count, String token, boolean isMobile) throws IOException
	{
		String dataset = "";
		for(int i=0;i<responseArray.size();i++){
			dataset = dataset +", "+String.valueOf(responseArray.get(i));

		}


		if(!token.equals("null"))
		{
			APP_LOGS.debug("New sheet to be updated: "+count);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
			//get current date time with Date()
			Date date = new Date();
			String timestamp=dateFormat.format(date);

			String sheetNumber = "A"+count;
			try
			{
				String api_url = "https://content-sheets.googleapis.com/v4/spreadsheets/"+sheetId+"/values/"+sheetNumber+"?valueInputOption=RAW&includeValuesInResponse=true&alt=json";

				APP_LOGS.debug(api_url);
				@SuppressWarnings("rawtypes")
				Entity data = Entity.json("{'values':[['"+timestamp+"'"+dataset+"]]}");
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target(api_url);
				Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
						.header("Authorization", "Bearer "+token)
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.header("key", "AIzaSyDJ9ceeEgagLKM566vd35nELUhNYnoYydQ")
						.put(data);
				String value = response.readEntity(String.class);
				APP_LOGS.debug("API Response: "+value);
				APP_LOGS.debug("status: " + response.getStatus());
				APP_LOGS.debug("Updated successfully");
				response.close(); 
				client.close();

			}
			catch(Exception ex)
			{
				APP_LOGS.debug("Error"+ex.getMessage());
			}

		}
		else
		{
			APP_LOGS.debug("token found: "+token);
		}
	}

	/**
	 * Get token from google auth play
	 * 
	 * @return TOKEN
	 */

	public static String getToken()
	{
		try
		{
			APP_LOGS.debug("Opening browser");
			String OS = System.getProperty("os.name").toString().toLowerCase();
			APP_LOGS.debug(OS);
			String chromedriver = "";
			if(OS.indexOf("mac") >= 0) {
				chromedriver = "/Users/shine/selenium_driver/chromedriver";
			}
			else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
				chromedriver = "/usr/local/bin/chromedriver/chromedriver_v74";

			}
			System.setProperty("webdriver.chrome.driver", chromedriver);
			System.setProperty("java.net.preferIPv4Stack" , "true");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--allow-no-sandbox-job");
			options.addArguments("--ignore-certificate-errors"); 
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			options.addArguments("--disable-plugins-discovery");
			options.addArguments("--profile-directory=Default");
			options.addArguments("--fast-start");
			options.addArguments("--disable-gpu");
			options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
			options.setAcceptInsecureCerts(true);
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);	

			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");

			//Hack for - Chrome is being controlled by automated test software
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);

			options.setExperimentalOption("prefs", prefs);
			WebDriver driver = new ChromeDriver(options);			

			APP_LOGS.debug("Browser opened");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			driver.navigate().to("https://developers.google.com/oauthplayground/");
			Thread.sleep(9000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("scopes")));
			driver.findElement(By.id("scopes")).sendKeys("https://www.googleapis.com/auth/spreadsheets");
			Thread.sleep(2000);
			driver.findElement(By.id("scopes")).sendKeys(Keys.TAB);

			Thread.sleep(7000);
			try
			{
				wait.until(ExpectedConditions.elementToBeClickable(By.id("authorizeApisButton")));
				driver.findElement(By.id("authorizeApisButton")).click();
			}
			catch(Exception ex)
			{
				APP_LOGS.debug("Error while clicking on authorizeApisButton : "+ex.getMessage());
				driver.navigate().refresh();
				driver.findElement(By.id("scopes")).sendKeys("https://www.googleapis.com/auth/spreadsheets");
				Thread.sleep(2000);
				driver.findElement(By.id("scopes")).sendKeys(Keys.TAB);
				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(By.id("authorizeApisButton"))).click().build().perform();

			}

			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='email']")));
			driver.findElement(By.cssSelector("[type='email']")).sendKeys("qualityabhishek@gmail.com");
			try{
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Next')]")));
				driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
			}catch(Exception ex){
				APP_LOGS.debug("Executing hack when element not found: "+ex.getMessage());
				System.out.println(ex.getMessage());
				wait.until(ExpectedConditions.elementToBeClickable(By.id("next")));
				driver.findElement(By.id("next")).click();

			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='password']")));
			driver.findElement(By.cssSelector("[type='password']")).sendKeys("Candidate@1234");



			try{
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Next')]")));
				driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();

			}catch(Exception ex){
				APP_LOGS.debug("Executing hack when element not found: "+ex.getMessage());
				wait.until(ExpectedConditions.elementToBeClickable(By.id("signIn")));
				driver.findElement(By.id("signIn")).click();
			}


			try
			{
				Thread.sleep(3000);
				wait.until(ExpectedConditions.elementToBeClickable(By.id("submit_approve_access")));
				driver.findElement(By.id("submit_approve_access")).click();
			}
			catch(Exception ex)
			{
				APP_LOGS.debug("Executing hack: "+ex.getMessage());
				/*Hack*/
				try {
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#submit_approve_access content span")));
					driver.findElement(By.cssSelector("#submit_approve_access content span")).click();
				}
				catch(Exception exp){
					APP_LOGS.debug("Executing hack when page doesn't redirect to the next page: "+exp.getMessage());
					/*Hack if page doesn't redirect to the next page*/
					driver.navigate().refresh();
					driver.navigate().back();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[contains(text(),'abhishek')]")).click();
					Thread.sleep(3000);
					wait.until(ExpectedConditions.elementToBeClickable(By.id("submit_approve_access")));
					driver.findElement(By.id("submit_approve_access")).click();
				}
			}

			Thread.sleep(3000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("exchangeCode")));
			driver.findElement(By.id("exchangeCode")).click();

			Thread.sleep(5000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("step2Title")));
			driver.findElement(By.id("step2Title")).click();

			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("access_token_field")));
			accessToken = driver.findElement(By.id("for_access_token")).getAttribute("value");
			APP_LOGS.debug("token: "+accessToken);

			if(accessToken==null)
			{
				try
				{
					accessToken = driver.findElement(By.cssSelector("#response span:nth-child(6)")).getText().replaceAll("\"", "");
				}
				catch(Exception ex)
				{
					APP_LOGS.debug("not access token found: "+ex.getMessage());
				}
			}
			APP_LOGS.debug("token: "+accessToken);
			driver.quit();
			saveToken(accessToken);
			return accessToken;
		}
		catch(Exception ex)
		{
			APP_LOGS.debug("Error in getToken main method"+ex.getMessage());
			return "null";
		}

	}

	private static void saveToken(String token) {
		try(OutputStream  output = new FileOutputStream(userDirectory+"/src/test/resources/config/OR.properties");){
			Properties prop = new Properties();
			prop.setProperty("token_cache", token);
			prop.store(output, null);
		}
		catch(Exception ex)
		{
			APP_LOGS.debug("Error in saveToken method"+ex.getMessage());

		}

	}


	/*Get Last update row number*/
	public static String getLastUpdateRow(boolean isResponseRequired) {
		try
		{
			//old 2017 sheet
			//String api_url = "https://spreadsheets.google.com/feeds/list/1qa16FkTtnWzRghILW8lz7wSUUOwKbrieVrzyU8uz280/1/public/values?alt=json";
			//New sheet 2018 URL
			String api_url = "https://spreadsheets.google.com/feeds/list/"+sheetId+"/1/public/values?alt=json";
			APP_LOGS.debug(api_url);
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.header("key", "AIzaSyDJ9ceeEgagLKM566vd35nELUhNYnoYydQ")
					.get();
			String value = response.readEntity(String.class);
			//APP_LOGS.debug("API Response: "+value);
			APP_LOGS.debug("status: " + response.getStatus());
			APP_LOGS.debug("Updated successfully");
			response.close(); 
			client.close();
			if(isResponseRequired)
				return value;
			else
				return String.valueOf(parseResponse(value));
		}
		catch(Throwable ex)
		{
			APP_LOGS.debug("Error in getLastUpdateRow method"+ex.getMessage());
			return "0";
		}
	}
	
	

	/*Parse response from JSON*/
	private static int parseResponse(String response) {
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(response);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject jsonObject2 = (JSONObject) jsonObject.get("feed");
			JSONObject jsonObject3 = (JSONObject) jsonObject2.get("openSearch$totalResults");
			int count = Integer.parseInt(jsonObject3.get("$t").toString());
			return count;
		} catch (Exception ex) {
			APP_LOGS.debug("Error in parseResponse method: "+ex.getMessage());
			return 0;
		}
	}




}

