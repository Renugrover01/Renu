package com.shine.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.shine.base.TestBaseSetup;

public class Search extends TestBaseSetup{

	WebDriver _Driver;
	WebDriverWait _Wait;

	public Search(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}

	public void open_shine_home_page() {
		_Driver.navigate().to(baseUrl + "/myshine/home/");
	}

	@FindBy(id="id_q")
	WebElement SEARCH_KEYWORD;
	@FindBy(id="id_loc")
	WebElement SEARCH_LOCATION;
	@FindBy(name="simplesearch")
	WebElement SEARCH_BUTTOM;
	@FindBy(id="id_searchBase")
	WebElement SEARCH_BASE;

	public void enter_search_keyword(String search_keyword) {
		_Wait.until(ExpectedConditions.visibilityOf(SEARCH_KEYWORD));
		SEARCH_KEYWORD.clear();
		SEARCH_KEYWORD.sendKeys(search_keyword);
		SEARCH_KEYWORD.sendKeys(Keys.TAB);
	}

	public void enter_search_location(String search_location) {
		_Wait.until(ExpectedConditions.visibilityOf(SEARCH_LOCATION));
		SEARCH_LOCATION.clear();
		SEARCH_LOCATION.sendKeys(search_location);
		SEARCH_LOCATION.sendKeys(Keys.TAB);
	}
	public void click_on_search_base() {
		_Wait.until(ExpectedConditions.visibilityOf(SEARCH_BASE));
		if(SEARCH_BASE.isDisplayed())
			SEARCH_BASE.click();
	}

	public void click_on_search_button() {
		_Wait.until(ExpectedConditions.visibilityOf(SEARCH_BUTTOM));
		if(SEARCH_BUTTOM.isDisplayed())
			SEARCH_BUTTOM.click();
	}


	//SIMPLE SEARCH
	public void searchFor(String keyword) {
		open_shine_home_page();
		_Utility.set_flag_checkTimeStamp(_Driver);
		click_on_search_base();
		enter_search_keyword(keyword);
		click_on_search_button();
	}

	@FindBy(id="id_minexp")
	WebElement SEARCH_MIN_EXP;
	@FindBy(xpath="//a[@class='cls_ulAdvSearch_a']/u")
	WebElement ADVANCE_SEARCH_EXPAND_BUTTON;
	@FindBy(id="id_minsal")
	WebElement SEARCH_SALARY;
	@FindBy(id="id_area")
	WebElement SEARCH_DEPARTMENT;
	@FindBy(id="id_ind")
	WebElement SEARCH_INDUSTRY;

	public void select_search_experience(String experience_year) {
		new Select(SEARCH_MIN_EXP).selectByVisibleText(experience_year+" Yrs");
	}
	public void select_search_salary(String salary_range) {
		new Select(SEARCH_SALARY).selectByVisibleText(salary_range);
	}
	public void select_search_department(String fa) {
		new Select(SEARCH_DEPARTMENT).selectByVisibleText(fa);
	}
	public void select_search_industry(String industry) {
		new Select(SEARCH_INDUSTRY).selectByVisibleText(industry);
	}


	/**
	 * Simple Hard-coded Advance Search
	 */
	public void perform_advance_search() {
		open_shine_home_page();
		_Utility.set_flag_checkTimeStamp(_Driver);
		click_on_search_base();
		enter_search_keyword("ANDROID");
		enter_search_location("Delhi");
		select_search_experience("5");
		_Utility.javascript_click(ADVANCE_SEARCH_EXPAND_BUTTON, _Driver);
		select_search_salary("Rs 3.0 - 3.5 Lakh / Yr");
		select_search_department("IT - Software");
		select_search_industry("IT - Software");
		click_on_search_button();

	}

	/**
	 * Advance Search
	 * @param keyword
	 * @param location
	 * @param experience_year
	 * @param salary_range
	 * @param fa
	 * @param industry
	 */
	public void perform_advance_search(String keyword, String location, String experience_year, String salary_range, String fa, String industry) {
		open_shine_home_page();
		_Utility.set_flag_checkTimeStamp(_Driver);
		click_on_search_base();
		enter_search_keyword(keyword);
		enter_search_location(location);
		select_search_experience(experience_year);
		_Utility.javascript_click(ADVANCE_SEARCH_EXPAND_BUTTON, _Driver);
		select_search_salary(salary_range);
		select_search_department(fa);
		select_search_industry(industry);
		click_on_search_button();

	}



	/**************************** SEARCH FILTER ********************************************/	

	@FindBy(css= ".submenu ul li:nth-child(1) input[name=fsalary]")
	WebElement SEARCH_FILTER_SALARY_SUBMENU;
	@FindBy(css= ".submenu ul li:nth-child(1) input[name=location]")
	WebElement SEARCH_FILTER_LOCATION_SUBMENU;
	@FindBy(css= ".submenu ul li:nth-child(1) input[name=farea]")
	WebElement SEARCH_FILTER_FA_SUBMENU;
	@FindBy(css= ".submenu ul li:nth-child(1) input[name=findustry]")
	WebElement SEARCH_FILTER_INDUSTRY_SUBMENU;
	@FindBy(css= ".submenu ul li:nth-child(1) input[name=fexp]")
	WebElement SEARCH_FILTER_EXPERIENCE_SUBMENU;
	@FindBy(css= ".submenu ul li:nth-child(1) input[name=fcid]")
	WebElement SEARCH_FILTER_COMPANY_SUBMENU;
	@FindBy(css= ".submenu ul li:nth-child(1) input[name=job_type]")
	WebElement SEARCH_FILTER_JOBTYPE_SUBMENU;

	@FindBy(xpath= "(//li[@id='id_filter_salary']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_SALARY_COUNT;
	@FindBy(xpath= "(//li[@id='id_filter_location']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_LOCATION_COUNT;
	@FindBy(xpath= "(//li[@id='id_filter_department']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_FA_COUNT;
	@FindBy(xpath= "(//li[@id='id_filter_industry']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_INDUSTRY_COUNT;
	@FindBy(xpath= "(//li[@id='id_filter_experience']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_EXPERIENCE_COUNT;
	@FindBy(xpath= "(//li[@id='id_filter_company']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_COMPANY_COUNT;
	@FindBy(xpath= "(//li[@id='id_filter_jobtype']/div/ul/li/em/b)[1]")
	WebElement SEARCH_FILTER_JOBTYPE_COUNT;



	/**
	 * Dynamic menu id generator
	 * @param i
	 * @return
	 */
	public By menuDiv(int i) {
		return By.xpath("(//ul[@id='id_filters']/li)["+i+"]");
	}


	public String filter_Search_based_on_salary(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_SALARY_SUBMENU, SEARCH_FILTER_SALARY_COUNT);
		return actual_count_after_filter;
	}
	public String filter_Search_based_on_location(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_LOCATION_SUBMENU, SEARCH_FILTER_LOCATION_COUNT);
		return actual_count_after_filter;
	}
	public String filter_Search_based_on_fa(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_FA_SUBMENU, SEARCH_FILTER_FA_COUNT);
		return actual_count_after_filter;
	}
	public String filter_Search_based_on_industry(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_INDUSTRY_SUBMENU, SEARCH_FILTER_INDUSTRY_COUNT);
		return actual_count_after_filter;
	}
	public String filter_Search_based_on_expereince(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_EXPERIENCE_SUBMENU, SEARCH_FILTER_EXPERIENCE_COUNT);
		return actual_count_after_filter;
	}
	public String filter_Search_based_on_company(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_COMPANY_SUBMENU, SEARCH_FILTER_COMPANY_COUNT);
		return actual_count_after_filter;
	}
	public String filter_Search_based_on_jobtype(int position) {
		searchFor("java");
		_Utility.Thread_Sleep(2000);
		String actual_count_after_filter = open_filter_menu(menuDiv(position), SEARCH_FILTER_JOBTYPE_SUBMENU, SEARCH_FILTER_JOBTYPE_COUNT);
		return actual_count_after_filter;
	}



	/**
	 * 
	 * @param menu
	 * @param Submenu
	 * @param countDiv
	 * @param driver
	 * @return
	 */
	public String open_filter_menu(By menu, WebElement Submenu, WebElement countDiv){
		WebDriverWait wait= new WebDriverWait(_Driver, 15);
		Actions action = new Actions(_Driver);
		action.moveToElement(_Driver.findElement(menu)).build().perform();
		wait.until(ExpectedConditions.visibilityOf(Submenu));
		String jCount = get_filter_menu_count(countDiv);
		action.moveToElement(Submenu);
		action.click().build().perform();
		APP_LOGS.debug("Filter Job Count after regex: "+jCount.replaceAll("[^0-9]", ""));
		//jCount = CharMatcher.digit().retainFrom(jCount);
		//APP_LOGS.debug("Job Count after CharMatcher: "+jCount);
		return jCount.replaceAll("[^0-9]", ""); 
	}

	public String get_filter_menu_count(WebElement filter_count_div) {
		_Wait.until(ExpectedConditions.visibilityOf(filter_count_div));
		String search_count = filter_count_div.getText().trim();
		APP_LOGS.debug("Filter Job Count: "+search_count);
		return search_count;
	}



	@FindBy(id= "id_resultCount")
	WebElement SEARCH_RESULT_COUNT;

	public String get_search_count() {
		_Wait.until(ExpectedConditions.visibilityOf(SEARCH_RESULT_COUNT));
		String search_count = SEARCH_RESULT_COUNT.getText().trim();
		APP_LOGS.debug("JSRP Job Count: "+search_count);
		return search_count;
	}

	/**
	 * 
	 * @param menuDiv
	 * @param subMenu
	 * @param _Searchdriver
	 */
	public void match_current_and_expected_job_count(String expected_count) {
		_Utility.Thread_Sleep(6000);
		String actual_count = get_search_count();
		APP_LOGS.debug("Assert JSRP Job Count: "+actual_count);
		APP_LOGS.debug("Assert Filter Job Count: "+expected_count);
		Assert.assertEquals(actual_count, expected_count);
	}

	@FindBy(css="u.relax")
	WebElement RELAX_SEARCH_MESSAGE;


	public String get_relaxed_search_message() {
		_Utility.Thread_Sleep(2000);
		_Wait.until(ExpectedConditions.visibilityOf(RELAX_SEARCH_MESSAGE));
		String actual_relax_msg = RELAX_SEARCH_MESSAGE.getText().trim();
		APP_LOGS.debug("Relax Search Message: "+actual_relax_msg);
		return actual_relax_msg;
	}

	public void verify_relaxed_search_message(String actual_relax_msg){
		Assert.assertEquals(actual_relax_msg, "There are no jobs matching your exact search criteria. Showing jobs based on your search that could be relevant to you.");

	}

	@FindBy(css="div.num_key>h1")
	WebElement JSRP_JOB_TITLE;

	public String get_jsrp_job_title() {
		_Wait.until(ExpectedConditions.visibilityOf(JSRP_JOB_TITLE));
		return JSRP_JOB_TITLE.getText();
	}
	
	@FindBy(css=".num_key>em")
	WebElement JSRP_RESULT_COUNT;
	public String get_jsrp_job_count() {
		_Wait.until(ExpectedConditions.visibilityOf(JSRP_RESULT_COUNT));
		return JSRP_RESULT_COUNT.getText();
	}


}
