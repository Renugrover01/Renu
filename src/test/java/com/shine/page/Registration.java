package com.shine.page;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.shine.base.TestBaseSetup;


public class Registration extends TestBaseSetup{

	WebDriver _Driver;
	WebDriverWait _Wait;

	public Registration(WebDriver _Driver){
		this._Driver = _Driver;
		this._Wait = new WebDriverWait(_Driver, 30);
		PageFactory.initElements(_Driver, this);
	}


	public void open_registration_page_url(String url) {
		_Driver.navigate().to(url);
	}

	public void open_registration_page_url() {
		_Driver.navigate().to(baseUrl+"/registration/parser/");
	}

	public void open_sem_registration_page_url() {
		_Driver.navigate().to(baseUrl+"/sem/registration/");
	}


	@FindBy(partialLinkText="Register")
	WebElement REGISTERATION_BUTTON;


	public void open_registration_page() {
		_Driver.navigate().to(baseUrl);
		_Wait.until(ExpectedConditions.visibilityOf(REGISTERATION_BUTTON));
		REGISTERATION_BUTTON.click();
	}

	public void isFirstRegistrationPageOpened() {
		Assert.assertEquals(_Driver.getCurrentUrl(), baseUrl+"/registration/parser/");
	}

	public void isFirstSemRegistrationPageOpened() {
		Assert.assertEquals(_Driver.getCurrentUrl(), baseUrl+"/sem/registration/");
	}
	/*******************REGISTRATION PAGE 1 FORM ****************************/

	@FindBy(id="id_email")
	WebElement EMAIL_ID;
	@FindBy(id="id_raw_password")
	WebElement PASSWORD_ID;
	@FindBy(id="id_cell_phone")
	WebElement MOBILE_NO_ID;
	@FindBy(id="id_sms_alert_flag")
	WebElement SMS_ALERT_FLAG_ID;
	@FindBy(css="[class='uplod_btn']")
	WebElement SUBMIT_BUTTON_ID;
	@FindBy(id="id_common_register")
	WebElement JSRP_SUBMIT_BUTTON_ID;
	
	public void enter_email_id(String email) {
		_Wait.until(ExpectedConditions.visibilityOf(EMAIL_ID));
		EMAIL_ID.clear();
		EMAIL_ID.sendKeys(email);
		EMAIL_ID.sendKeys(Keys.TAB);
	}

	public String enter_email_id() {
		String email = _Utility.generateEmailid();
		_Wait.until(ExpectedConditions.visibilityOf(EMAIL_ID));
		EMAIL_ID.clear();
		EMAIL_ID.sendKeys(email);
		EMAIL_ID.sendKeys(Keys.TAB);
		return email;
	}

	public void enter_password(String password) {
		_Wait.until(ExpectedConditions.visibilityOf(PASSWORD_ID));
		PASSWORD_ID.clear();
		PASSWORD_ID.sendKeys(password);
		PASSWORD_ID.sendKeys(Keys.TAB);
	}

	public void enter_password() {
		_Wait.until(ExpectedConditions.visibilityOf(PASSWORD_ID));
		PASSWORD_ID.clear();
		PASSWORD_ID.sendKeys("123456");
		PASSWORD_ID.sendKeys(Keys.TAB);
	}

	public void enter_mobile_number(String mobile_number) {
		_Wait.until(ExpectedConditions.visibilityOf(MOBILE_NO_ID));
		MOBILE_NO_ID.sendKeys(mobile_number);
		MOBILE_NO_ID.sendKeys(Keys.TAB);
	}

	public void enter_mobile_number() {
		_Wait.until(ExpectedConditions.visibilityOf(MOBILE_NO_ID));
		MOBILE_NO_ID.sendKeys("9876556789");
		MOBILE_NO_ID.sendKeys(Keys.TAB);
	}

	public void select_sms_alert_flag() {
		_Wait.until(ExpectedConditions.visibilityOf(SMS_ALERT_FLAG_ID));
		if(!SMS_ALERT_FLAG_ID.isSelected())
			SMS_ALERT_FLAG_ID.click();
	}
	public void de_select_sms_alert_flag() {
		_Wait.until(ExpectedConditions.visibilityOf(SMS_ALERT_FLAG_ID));
		if(SMS_ALERT_FLAG_ID.isSelected())
			SMS_ALERT_FLAG_ID.click();
	}
	public void click_on_submit_button() {
		_Wait.until(ExpectedConditions.visibilityOf(SUBMIT_BUTTON_ID));
		if(SUBMIT_BUTTON_ID.isDisplayed())
			SUBMIT_BUTTON_ID.click();
	}
	public void click_on_jsrp_reg_submit_button() {
		_Wait.until(ExpectedConditions.visibilityOf(JSRP_SUBMIT_BUTTON_ID));
		if(JSRP_SUBMIT_BUTTON_ID.isDisplayed())
			JSRP_SUBMIT_BUTTON_ID.click();
	}



	/**
	 * Simple and hard-coded registration page 1
	 * @return email
	 */
	public String fill_and_submit_first_registration_page() {
		open_registration_page();
		isFirstRegistrationPageOpened();
		String email = enter_email_id();
		enter_email_id(email);
		enter_password();
		enter_mobile_number();
		select_sms_alert_flag();
		click_on_submit_button();
		return email;
	}

	/**
	 * Simple and custom registration page 1
	 * @param email
	 * @param password
	 * @param mobile_no
	 */
	public void fill_and_submit_first_registration_page(String email, String password, String mobile_no) {
		open_registration_page();
		isFirstRegistrationPageOpened();
		enter_email_id(email);
		enter_password(password);
		enter_mobile_number(mobile_no);
		select_sms_alert_flag();
		click_on_submit_button();
	}
	
	/**
	 * This method doesn't call page, user had to open the page first
	 * @param email
	 * @param password
	 * @param mobile_no
	 */
	public void fill_and_submit_first_registration(String email, String password, String mobile_no) {
		enter_email_id(email);
		enter_password(password);
		enter_mobile_number(mobile_no);
		select_sms_alert_flag();
		click_on_submit_button();
	}

	//JSRP QUICK REGISTRATION
	@FindBy(css="[value='Register']")
	WebElement REGISTER_BUTTON_ID;

	@FindBy(id="id_jsrp_resumeUpload_div")
	WebElement REGISTER_BUTTON_SCROLL;

	@FindBy(id="Password")
	WebElement REGISTER_JSRP_PASSWORD_ID;

	public void click_on_register_button() {
		_Wait.until(ExpectedConditions.visibilityOf(REGISTER_BUTTON_ID));
		if(REGISTER_BUTTON_ID.isDisplayed())
			REGISTER_BUTTON_ID.click();
	}

	public void scroll_to_jsrp_registration() {
		_Wait.until(ExpectedConditions.visibilityOf(REGISTER_BUTTON_ID));
		_Utility.scrollTOElement(REGISTER_BUTTON_SCROLL, _Driver);
	}

	public void enter_jsrp_reg_password(String password) {
		_Wait.until(ExpectedConditions.visibilityOf(REGISTER_JSRP_PASSWORD_ID));
		REGISTER_JSRP_PASSWORD_ID.sendKeys(password);
	}

	public void fill_and_submit_jsrp_registration(String email, boolean isJSRPPopup) {
		enter_email_id(email);
		enter_mobile_number("9876556789");
		enter_jsrp_reg_password("123456");
		if(isJSRPPopup)
			click_on_jsrp_reg_submit_button();
		else
			click_on_register_button();
	}

	/*******************REGISTRATION PAGE 2 FORM ****************************/


	public void isSecondRegistrationPageOpened() {
		Assert.assertEquals(_Driver.getCurrentUrl(), baseUrl+"/registration/parser/");
	}


	@FindBy(id="id_name")
	WebElement USERNAME_ID;
	@FindBy(id="id_candidate_location")
	WebElement CITY_DD_ID;
	@FindBy(css="#id_candidate_location option")
	List<WebElement> CITY_DD_LIST;
	
	@FindBy(id="id_experience_in_years")
	WebElement EXPERIENCE_DD_ID;
	@FindBy(css="#id_experience_in_years option")
	List<WebElement> EXPERIENCE_DD_LIST;
	@FindBy(id="id_gender_0")
	WebElement GENDER_MALE;
	@FindBy(id="id_gender_1")
	WebElement GENDER_FEMALE;
	@FindBy(css=".cls_partial_save")
	List<WebElement> CONTINUE_BUTTON;

	public void enter_username() {
		_Wait.until(ExpectedConditions.visibilityOf(USERNAME_ID));
		USERNAME_ID.sendKeys("9876556789");
		USERNAME_ID.sendKeys(Keys.TAB);
	}
	public void enter_username(String username) {
		_Wait.until(ExpectedConditions.visibilityOf(USERNAME_ID));
		USERNAME_ID.sendKeys(username);
		USERNAME_ID.sendKeys(Keys.TAB);
	}


	public void select_location(String location_name) {
		new Select(CITY_DD_ID).selectByVisibleText(location_name);
	}
	public void select_location() {
		new Select(EXPERIENCE_DD_ID).selectByIndex(_Utility.randomIndex(CITY_DD_LIST.size()));
	}
	public void select_experience(String experience_in_year) {
		new Select(EXPERIENCE_DD_ID).selectByVisibleText(experience_in_year);
	}
	public void select_experience() {
		new Select(EXPERIENCE_DD_ID).selectByIndex(_Utility.randomIndex(EXPERIENCE_DD_LIST.size()));
	}

	public void select_gender(String gender) {
		if(gender.equalsIgnoreCase("male")) {
			_Wait.until(ExpectedConditions.visibilityOf(GENDER_MALE));
			if(!GENDER_MALE.isSelected())
				GENDER_MALE.click();
		}
		else if(gender.equalsIgnoreCase("female")) {
			_Wait.until(ExpectedConditions.visibilityOf(GENDER_FEMALE));
			if(!GENDER_FEMALE.isSelected())
				GENDER_FEMALE.click();
		}
	}


	public void click_on_continue_button() {
		try {
			for(WebElement button : CONTINUE_BUTTON) {
				if(button.isDisplayed()) {
					button.click();
					break;
				}
			}
		} catch (Throwable e) {
			APP_LOGS.error("Wrong Button: "+e.getMessage());
		}
	}



	/**
	 * 
	 * @param isFresher
	 * @param specialexpFlow
	 * @param _RegMidoutDriver
	 */
	public void fill_and_submit_personal_details(String email, boolean isFresher) {
		String extarctUsername = StringUtils.substringBefore(email, "_").trim();
		APP_LOGS.debug("USERNAME: "+extarctUsername);
		enter_username(extarctUsername);
		select_location("Delhi");
		if(isFresher==true)
			select_experience("0 Yr");
		else 	
			select_experience("5 Yr");
		select_gender("male");
		click_on_continue_button();
	}
	
	

	@FindBy(id="id_job_title")
	WebElement JOB_TITLE_ID;
	@FindBy(id="id_company_name")
	WebElement COMPANY_NAME_ID;

	@FindBy(css=".cls_addCompanyInOther")
	WebElement ADD_COMPANY_NAME_ID;	

	@FindBy(xpath="(//button[@type='button'])[1]")
	WebElement INDUSTRY_DD_ID;
	@FindBy(id="id_industry_id_18")
	WebElement INDUSTRY_SDD_ID;

	@FindBy(xpath="(//button[@type='button'])[2]")
	WebElement FA_DD_ID;
	@FindBy(id="id_sub_field_1405")
	WebElement FA_SDD_ID;

	@FindBy(id="id_salary_in_lakh")
	WebElement SALARY_IN_LAKH_ID;
	@FindBy(id="id_salary_in_thousand")
	WebElement SALARY_IN_THOUSAND_ID;

	@FindBy(id="id_start_month")
	WebElement JOB_START_MONTH_ID;
	@FindBy(id="id_start_year")
	WebElement JOB_START_YEAR_ID;

	@FindBy(id="id_is_current")
	WebElement isPRESENT_CHECKBOX_ID;


	public void enter_job_title(String job_title) {
		_Wait.until(ExpectedConditions.visibilityOf(JOB_TITLE_ID));
		JOB_TITLE_ID.sendKeys(job_title);
		JOB_TITLE_ID.sendKeys(Keys.TAB);
	}

	public String enter_job_title() {
		String job_title = _Faker.job().title();
		_Wait.until(ExpectedConditions.visibilityOf(JOB_TITLE_ID));
		JOB_TITLE_ID.sendKeys(job_title);
		JOB_TITLE_ID.sendKeys(Keys.TAB);
		return job_title;
	}
	
	@FindBy(css=".cls_addCompanyInOther")
	WebElement ADD_COMPANY_LINK;

	public void enter_company_name(String company_name, boolean isADDCompany) {
		_Wait.until(ExpectedConditions.visibilityOf(COMPANY_NAME_ID));
		COMPANY_NAME_ID.sendKeys(company_name);
		COMPANY_NAME_ID.sendKeys(Keys.TAB);
		if(isADDCompany) {
			_Wait.until(ExpectedConditions.visibilityOf(ADD_COMPANY_LINK));
			ADD_COMPANY_LINK.click();
		}
		
	}

	public String enter_company_name() {
		String company_name = _Faker.company().name();
		_Wait.until(ExpectedConditions.visibilityOf(COMPANY_NAME_ID));
		COMPANY_NAME_ID.sendKeys(company_name);
		COMPANY_NAME_ID.sendKeys(Keys.TAB);
		click_on_add_company();
		return company_name;
	}


	public void click_on_add_company() {
		_Wait.until(ExpectedConditions.visibilityOf(ADD_COMPANY_NAME_ID));
		if(!ADD_COMPANY_NAME_ID.isSelected())
			ADD_COMPANY_NAME_ID.click();
	}

	public void select_industry() {
		_Wait.until(ExpectedConditions.visibilityOf(INDUSTRY_DD_ID));
		INDUSTRY_DD_ID.click();
		_Wait.until(ExpectedConditions.visibilityOf(INDUSTRY_SDD_ID));
		INDUSTRY_SDD_ID.click();
	}

	public void select_department() {
		_Wait.until(ExpectedConditions.visibilityOf(FA_DD_ID));
		FA_DD_ID.click();
		_Utility.scrollTOElement(FA_SDD_ID, _Driver);
		_Wait.until(ExpectedConditions.visibilityOf(FA_SDD_ID));
		FA_SDD_ID.click();
	}

	public void select_salary_in_lakh(String salary_in_lakh) {
		new Select(SALARY_IN_LAKH_ID).selectByVisibleText(salary_in_lakh);
	}

	public void select_salary_in_thousand(String salary_in_thousand) {
		new Select(SALARY_IN_THOUSAND_ID).selectByVisibleText(salary_in_thousand);
	}

	public void select_salary() {
		select_salary_in_lakh("5");
		select_salary_in_thousand("0");
	}

	public void select_salary(String salary_in_lakh, String salary_in_thousand) {
		select_salary_in_lakh(salary_in_lakh);
		select_salary_in_thousand(salary_in_thousand);
	}

	public void select_job_start_month(String salary_in_lakh) {
		new Select(JOB_START_MONTH_ID).selectByVisibleText(salary_in_lakh);
	}

	public void select_job_start_year(String salary_in_thousand) {
		new Select(JOB_START_YEAR_ID).selectByVisibleText(salary_in_thousand);
	}

	public void select_job_duration() {
		select_job_start_month("Jan");
		select_job_start_year("2012");
	}

	public void select_job_duration(String start_month, String start_year) {
		select_job_start_month(start_month);
		select_job_start_year(start_year);
	}

	public void select_is_present_job() {
		_Wait.until(ExpectedConditions.visibilityOf(isPRESENT_CHECKBOX_ID));
		if(!isPRESENT_CHECKBOX_ID.isSelected())
			isPRESENT_CHECKBOX_ID.click();
	}



	public void fill_and_submit_job_details() {
		//enter_job_title("Software Developer");
		enter_job_title();
		//enter_company_name("HCL Technologies Limited");
		enter_company_name();
		select_industry();
		select_department();
		select_salary();
		select_job_duration();
		select_is_present_job();
		click_on_continue_button();
	}
	public void fill_and_submit_job_details(String job_title, String company_name, boolean isADDCompany) {
		enter_job_title(job_title);
		enter_company_name(company_name, isADDCompany);
		select_industry();
		select_department();
		select_salary();
		select_job_duration();
		select_is_present_job();
		click_on_continue_button();
	}
	

	@FindBy(xpath="//span[contains(text(),'Highest Qualification')]")
	WebElement EDUCATION_SPECIALIZATION;
	@FindBy(css="li[label='M.C.A'] label[data-group='group_23']")
	WebElement EDUCATION_SPECIALIZATION_MCA;
	@FindBy(css="li[label='M.Tech'] label[data-group='group_28']")
	WebElement EDUCATION_SPECIALIZATION_MTECH;
	@FindBy(id="id_education_specialization_128_555")
	WebElement EDUCATION_SUB_SPECIALIZATION_MTECH_CS;

	@FindBy(id="id_institute_name")
	WebElement INSTITUTE_NAME;
	@FindBy(id="id_year_of_passout")
	WebElement YEAR_OF_PASSOUT;



	public void select_highest_qualification() {
		_Wait.until(ExpectedConditions.visibilityOf(EDUCATION_SPECIALIZATION));
		EDUCATION_SPECIALIZATION.click();
		_Utility.scrollTOElement(EDUCATION_SPECIALIZATION_MCA, _Driver);
		_Wait.until(ExpectedConditions.visibilityOf(EDUCATION_SPECIALIZATION_MTECH));
		EDUCATION_SPECIALIZATION_MTECH.click();
		_Wait.until(ExpectedConditions.visibilityOf(EDUCATION_SUB_SPECIALIZATION_MTECH_CS));
		EDUCATION_SUB_SPECIALIZATION_MTECH_CS.click();
	}

	public void enter_institute_name() {
		_Wait.until(ExpectedConditions.visibilityOf(INSTITUTE_NAME));
		INSTITUTE_NAME.sendKeys("IIIT Hyderabad");
		INSTITUTE_NAME.sendKeys(Keys.TAB);
	}
	public void enter_institute_name(String institute_name) {
		_Wait.until(ExpectedConditions.visibilityOf(INSTITUTE_NAME));
		INSTITUTE_NAME.sendKeys(institute_name);
		INSTITUTE_NAME.sendKeys(Keys.TAB);
	}

	public void select_year_of_passout(String year_of_passout) {
		new Select(YEAR_OF_PASSOUT).selectByVisibleText(year_of_passout);
	}
	public void select_year_of_passout() {
		new Select(YEAR_OF_PASSOUT).selectByVisibleText("2012");
	}

	public void fill_and_submit_education_details() {
		select_highest_qualification();
		enter_institute_name("IIIT Hyderabad");
		select_year_of_passout("2012");
		click_on_continue_button();

	}
	public void fill_and_submit_education_details(String institute_name) {
		select_highest_qualification();
		enter_institute_name(institute_name);
		select_year_of_passout("2012");
		click_on_continue_button();

	}

	public void fill_and_submit_education_details(String institute_name, String year_of_passout) {
		select_highest_qualification();
		enter_institute_name(institute_name);
		select_year_of_passout(year_of_passout);
		click_on_continue_button();

	}
	

	@FindBy(id="id_reg_skill")
	WebElement SKILL_ID;

	public void enter_skill(String skill_name) {
		_Wait.until(ExpectedConditions.visibilityOf(SKILL_ID));
		SKILL_ID.clear();
		SKILL_ID.sendKeys(skill_name+",");
	}


	public void fill_and_submit_skill_details () {
		APP_LOGS.debug("Submitting skills: java,python,databases,c++,javascript");
		enter_skill("java, python, databases, c++, javascript,");
		click_on_continue_button();

	}
	public void fill_and_submit_skill_details (String skill) {
		APP_LOGS.debug("Submitting skills: "+skill);
		enter_skill(skill);
		click_on_continue_button();

	}



	public void fill_and_submit_second_registration_page(String email, boolean isFresher) {
		fill_and_submit_personal_details(email, false);
		if(!isFresher)
			fill_and_submit_job_details();
		fill_and_submit_education_details();
		fill_and_submit_skill_details();
		_Utility.Thread_Sleep(2000);
	}



	public void perform_complete_registration_flow_test() {
		String email = _Utility.generateEmailid();
		fill_and_submit_first_registration(email, "123456", "9876556789");
		fill_and_submit_second_registration_page(email, false);
		_Utility.Thread_Sleep(2000);
		Resume _Resume = new Resume(_Driver);
		_Resume.upload_resume("resumefile");
	}

	public void perform_jsrp_complete_registration_flow_test(boolean isJSRPPopup) {
		String email = _Utility.generateEmailid();
		fill_and_submit_jsrp_registration(email, isJSRPPopup);
		fill_and_submit_second_registration_page(email, false);
		_Utility.Thread_Sleep(2000);
		Resume _Resume = new Resume(_Driver);
		_Resume.upload_resume("resumefile");
	}

}
