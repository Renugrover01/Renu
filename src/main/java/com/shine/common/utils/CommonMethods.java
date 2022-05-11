package com.shine.common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.shine.base.TestBaseSetup;

public class CommonMethods extends TestBaseSetup {
	
	public void djDebugRemover(WebDriver driver){
		try{
			Thread.sleep(2000);
			driver.findElement(By.id("djHideToolBarButton")).click();
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String todisable = "document.getElementById('djDebug').style.display = 'none';";
			javascript.executeScript(todisable);
			APP_LOGS.error("Toolbar removed from recruiter site:");
		}
		catch(Exception ex){
			APP_LOGS.error("Error Removing extra toolbar:");
			APP_LOGS.debug("Django error: May be you are testing live website");
		}
	}
	
	public void clickOnNotification(WebDriver driver) {
		try {
			driver.findElement(By.cssSelector(".js-hide-noti-div")).click();
		}
		catch(Exception e){
			APP_LOGS.debug(e.getMessage());
		}
	}

	/**
	 * Start simple search
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnSimpleSearch(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions action_simple = new Actions(driver);
		  WebElement menu_simple =  driver.findElement(By.linkText("Find Candidates"));
		  action_simple.moveToElement(menu_simple).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Simple Search")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/simplesearch/");
			}  
			_Utility.Thread_Sleep(2000);
	}

	/**
	 * Start advanced search.
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnAdvancedSearch(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions action_simple = new Actions(driver);
		  WebElement menu_simple =  driver.findElement(By.linkText("Find Candidates"));
		  action_simple.moveToElement(menu_simple).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Advanced Search")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/advancedsearch/");
			}
		_Utility.Thread_Sleep(2000);
	}

	/**
	 * Start simple search
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnPeopleSearch(WebDriver driver) {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions action_simple = new Actions(driver);
		  WebElement menu_simple =  driver.findElement(By.linkText("Find Candidates"));
		  action_simple.moveToElement(menu_simple).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("People Search")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/peoplesearch/");
			}
		_Utility.Thread_Sleep(2000);
	}

	/**
	 * Start simple search
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnITSkillSearch(WebDriver driver) {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions action_simple = new Actions(driver);
		  WebElement menu_simple =  driver.findElement(By.linkText("Find Candidates"));
		  action_simple.moveToElement(menu_simple).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("IT Skill Search")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/ITskillsearch/");
			}
		_Utility.Thread_Sleep(2000);
	}

	/**
	 * Start simple search
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnManageSearch(WebDriver driver) {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions action_simple = new Actions(driver);
		  WebElement menu_simple =  driver.findElement(By.linkText("Find Candidates"));
		  action_simple.moveToElement(menu_simple).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Manage Search")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/myshine/#!/savedsearch/");
			}
		_Utility.Thread_Sleep(2000);
	}

	/**
	 * Start for howing create new email template
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnCreateEmailTemplate(WebDriver driver) {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Email/ SMS"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("New Email Template")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/email/add/");
			} 			
	}

	/**
	 * Start for howing on manage folders
	 * @param driver
	 * @throws InterruptedException
	 */
	public void howeringOnManageFolder(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions action = new Actions(driver);
			WebElement menu =  driver.findElement(By.linkText("Folders"));
			action.moveToElement(menu).build().perform();	  		
			try 
			{  		
				driver.findElement(By.linkText("Manage Folders")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/folders/");
			}
		_Utility.Thread_Sleep(2000);
	}

	/**
	 * Start posting new smart match job
	 * @param driver
	 * @throws InterruptedException
	 */
	public void howeringOnManageJobs(WebDriver driver) {
  		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Actions action68 = new Actions(driver);
		WebElement menu68 = driver.findElement(By.linkText("Manage Jobs"));
		action68.moveToElement(menu68).build().perform();
			try 
			{  		
				driver.findElement(By.cssSelector("[href='/managejobs/']")).click();					
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/managejobs//");
			}
		_Utility.Thread_Sleep(2000);
		//assertEquals(_autoMatchCount.findElement(By.cssSelector("h3")).getText(), "Account Jobs");
			Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(), "My Job");
		APP_LOGS.debug("Match --->>> My Jobs"); 
  	}


	/**
	 * Start posting new walkins jobs
	 * @param driver
	 * @throws InterruptedException
	 */
	public void howeringOnPostAWalkinJobs(WebDriver driver) {
  		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Actions walkinAction = new Actions(driver);
		WebElement walkinMenu = driver.findElement(By.linkText("Manage Jobs"));
		walkinAction.moveToElement(walkinMenu).build().perform();
		try 
		{  		
			driver.findElement(By.linkText("Post A Walkin Job")).click();
		}
		catch(Exception e)
		{
			APP_LOGS.debug(e.getMessage());
			driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/post-walkin-job//");
		}
		_Utility.Thread_Sleep(2000);
		Assert.assertEquals("Job Details", driver.findElement(By.id("job_detail")).getText());
		APP_LOGS.debug("Match --->>> Job Details"); 
  	}

	/**
	 * Start posting new smart match job
	 * @param driver
	 * @throws InterruptedException
	 */
	public void howeringOnPostAJobs(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Actions action88 = new Actions(driver);
		WebElement menu88 = driver.findElement(By.linkText("Manage Jobs"));
		action88.moveToElement(menu88).build().perform();
		//driver.findElement(By.linkText("Post A Job")).click();
		try 
		{  		
			driver.findElement(By.linkText("Post A Job")).click();
		}
		catch(Exception e)
		{
			APP_LOGS.debug(e.getMessage());
			driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/post-job//");
		}
		Assert.assertEquals("Job Details", driver.findElement(By.id("job_detail")).getText());
		APP_LOGS.debug("Match --->>> Job Details"); 
  	}

	/**
	 * Start for howing create new email template.
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnERDashboard(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Employee Referral"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Dashboard")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/dashboard/");
			} 			
	}

	/**
	 * Start for howing create new email template
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnCompanyInfo(WebDriver driver) {
		  APP_LOGS.debug("......*...Start for howing create new email template......*...");
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Employee Referral"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Company Info")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/company/info/");
			} 			
	}

	/**
	 * Start for howing create new email template.
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnManageEmployee(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Employee Referral"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Manage Employees")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/manage-employees/");
			} 			
	}

	/**
	 * Start for howing create new email template.
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnHiringStage(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Employee Referral"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Hiring Stages")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/myshine/#!/hiringstages/");
			} 			
	}

	/**
	 * Start for howing on reported candidate
	 * @param driver
	 * @throws Exception
	 */
	public void howeringOnCandidatesLikeStar(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Folders"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Candidates You Like")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/folder/view/starredcand/");
			} 	
	}

	/**
	 * Start for howing on candidates star like
	 * @param driver
	 */
	public void howeringOnReportedCandidate(WebDriver driver)  {
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  Actions new_email_temp_action = new Actions(driver);
		  WebElement new_email_temp_menu =  driver.findElement(By.linkText("Folders"));
		  new_email_temp_action.moveToElement(new_email_temp_menu).build().perform();
			try 
			{  		
				driver.findElement(By.linkText("Reported Candidate")).click();
			}
			catch(Exception e)
			{
				APP_LOGS.debug(e.getMessage());
				driver.navigate().to(CONFIG.getProperty("testSiteURL")+"/folder/view/reportedcand/");
			} 			
	}
	
	public static String CurrentDatePlusOne() {
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			APP_LOGS.debug(dateFormat.format(currentDate));
			Calendar c = Calendar.getInstance();// convert date to calendar
			c.setTime(currentDate);
			// manipulate date    		        
			c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);		        
			Date currentDatePlusOne = c.getTime();// convert calendar to date
			APP_LOGS.debug(dateFormat.format(currentDatePlusOne));
			return dateFormat.format(currentDatePlusOne);
		}
		catch(Exception e){
			APP_LOGS.debug(e.getMessage());
			return "2017-03-31";
		}
	}
	
	public static String CurrentDate(){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dateobj = new Date();
			APP_LOGS.debug("Date : "+df.format(dateobj));
			return df.format(dateobj).toString();
		}
		catch(Exception e){
			APP_LOGS.debug(e.getMessage());
			return "2017-03-30";
		}
	}
	
	public static void industryFilter(WebDriver driver) throws Exception {
		driver.findElement(By.xpath(".//*[@id='facet_outer_4']/div/i")).click();
		List<WebElement> industryList = driver.findElements(By.id("industry"));
		industryList.size();
		
	}
	
	public boolean IsElementPresent(By by, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
  		try{
  			driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
			APP_LOGS.debug(e.getMessage());
            return false;
        }
	}
}
