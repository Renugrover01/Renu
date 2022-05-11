package com.shine.tests;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;
import com.shine.base.TestBaseSetup;
import com.shine.beans.CandidateProfile;

import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import org.testng.ITestResult;

public class Test_MyProfile extends TestBaseSetup {

	WebDriver _Myprofiledriver;
	WebDriverWait _Wait;
	static String TestLink_testCase="";
	String uid = "";
	int i=0 ;

	static By userAccMenuLink	= By.partialLinkText("Profile");
	static By myProfileLink		= By.linkText("My Profile");

	By resumeUpload    			= By.cssSelector("#id_resume");
	By opaque          			= By.id("opaque");
	By resumeUploadNew 			= By.xpath("//a[contains(text(),'Upload new resume')]");
	By resumeFile      			= By.id("id_file");
	By resumeSubmit    			= By.cssSelector("input.submitred.resumeupload");
	By errorMessage    			= By.cssSelector(".error_text.floatleft");
	//Work Experience
	By workExpEdit           	= By.id("wrkexp");
	By profileTitle    	        = By.id("id_resume_title");
	By profileSummary	        = By.id("id_summary");
	By workExp         			= By.id("id_experience_in_years");
	By workExpInMonth 			= By.id("id_experience_in_months");
	By salaryInLakh    			= By.id("id_minsalLakh");
	By salaryInThou    			= By.id("id_minsalThousand");
	By teamSize                 = By.id("id_teamSize");
	By noticePeriod             = By.id("id_noticePeriod");
	By savework_summary         = By.id("id-save-worksummary-web");
	//Work Experience
	By addJobDetails            = By.id("id_add_new_job");
	By jobTitle                 = By.id("id_job_title");
	By companyName              = By.id("id_company_name");
	By compIndustryDropDown     = By.id("id_industry_search_input");
	By compIndSubDd             = By.id("item-6-1");
	By compFADd                 = By.id("id_department_search_input");
	By compFASubDd              = By.id("item-701-2");
	By jobStartMonth            = By.id("id_start_month");
	By jobStartYear             = By.id("id_start_year");
	By iWorkHereCheckbox		= By.id("id_is_current");
	By saveCompDetails          = By.id("id-save-jobdetails-web");
	//Education Details
	By addEducationDetails      = By.id("id_add_new_qualification");
	By educationDetails  		= By.id("id_education_specialization_search_input");
	By eduspecialization 		= By.id("item-110_551-3");
	By instituteName  			= By.id("id_institute_name");
	By yearOfPassout            = By.id("id_year_of_passing_search_input");
	By selectpassout_year       = By.id("item-key-3");
	By saveEducationDetailss    = By.id("id-save-education-web");
	//skills
	By addMoreSkillBtn          = By.id("id_add_new_skill");
	By skillName                = By.id("id_skill_name");
	By skillExp                 = By.id("id_minexpk"); 
	By saveSkillBtn             = By.id("id-save-skills-web");
	//Certification
	By addMoreCertificationBtn  = By.id("id_add_new_certification");
	By certiName                = By.id("id_certification_name");
	By certiExp                 = By.id("id_certification_year"); 
	By saveCertificationBtn     = By.id("id-save-certification-web");
	By certiDiv					= By.cssSelector("#id_addMorecertifications");

	By deleteResume				= By.className("cross");
	By deleteResumeConfirm		= By.id("id_cpSubmit");
	By resumeCountList			= By.xpath("//ul[@class='profile_resume cls_resume_list']/li");

	Gson _Gson = null;

	String random_String = "";

	@BeforeClass
	public void TestSetup() {
		random_String = _Utility.get_random_string();
		_Gson = new Gson();
		_Myprofiledriver = getDriver(_Myprofiledriver);
		loggedInShine(_Myprofiledriver, email_new, pass_new);
		_Utility.clickOnNotification(_Myprofiledriver);
		_Wait = new WebDriverWait(_Myprofiledriver, 20);
		_Utility.openMenuLink(userAccMenuLink, myProfileLink, _Myprofiledriver);
		JavascriptExecutor jse = (JavascriptExecutor) _Myprofiledriver;
		try {
			uid = (String) jse.executeScript("return sc.UID");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	@Test (priority =3)
	public void test_UploadInvalidResume() {
		TestLink_testCase = "Validate invalid resume upload on myprofile";
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(resumeUpload, _Myprofiledriver);
		WebDriverWait _Wait = new WebDriverWait(_Myprofiledriver, 30);	
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(resumeUploadNew));
		_Wait.until(ExpectedConditions.elementToBeClickable(resumeUploadNew));
		_Myprofiledriver.findElement(resumeUploadNew).click();
		_Utility.Thread_Sleep(500);
		setResumeName("nonmatchingresume.doc", _Myprofiledriver);
		//upload incorrect resume
		_Myprofiledriver.findElement(resumeFile).sendKeys(userDirectory+CONFIG.getProperty("resumeerrorfile"));
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(resumeSubmit).click();
		_Utility.Thread_Sleep(5000);
		String errormsg = _Myprofiledriver.findElement(By.className("cls_error_resume")).getText();
		Assert.assertEquals(errormsg, "Your name, email or phone number in resume must be same as in your profile.");

	}


	@Test (priority =5)
	public void test_UploadValidResume() {
		TestLink_testCase = "Validate valid resume upload on myprofile";
		setResumeName("Resume.docx", _Myprofiledriver);
		//upload correct resume
		_Myprofiledriver.findElement(resumeFile).sendKeys(userDirectory+CONFIG.getProperty("resumefile"));
		_Utility.Thread_Sleep(3000);
		_Myprofiledriver.findElement(resumeSubmit).click();
		_Utility.Thread_Sleep(6000);
	}


	
	  @Test (priority =6) 
	  public void test_UploadAndDelete() { 
	  TestLink_testCase = "Validate valid resume upload on myprofile";
	  _Utility.Thread_Sleep(2000);
	  _Myprofiledriver.findElement(resumeUploadNew).click();
	  _Utility.Thread_Sleep(500); setResumeName("Resume_v2.docx", _Myprofiledriver);
	  _Myprofiledriver.findElement(resumeFile).sendKeys(userDirectory+CONFIG.getProperty("resumefilev2"));
	  _Utility.Thread_Sleep(3000);
	  _Myprofiledriver.findElement(resumeSubmit).click();
	  _Utility.Thread_Sleep(6000); 
	  _Myprofiledriver.findElement(deleteResume).click();
	  _Utility.Thread_Sleep(2000);
	  }

	/*
	 * public void test_UploadAndDelete() { TestLink_testCase =
	 * "Validate valid resume upload on myprofile"; _Utility.Thread_Sleep(2000);
	 * _Myprofiledriver.findElement(resumeUploadNew).click();
	 * _Utility.Thread_Sleep(500); setResumeName("Resume_v2.docx",
	 * _Myprofiledriver);
	 * _Myprofiledriver.findElement(resumeFile).sendKeys(userDirectory+CONFIG.
	 * getProperty("resumefilev2")); _Utility.Thread_Sleep(3000);
	 * _Myprofiledriver.findElement(resumeSubmit).click();
	 * _Utility.Thread_Sleep(6000); //int previous_count =
	 * _Myprofiledriver.findElements(resumeCountList).size();
	 * //APP_LOGS.debug("previous count>> "+previous_count);
	 * _Myprofiledriver.findElement(deleteResume).click();
	 * _Utility.Thread_Sleep(2000); //JavascriptExecutor jse =
	 * (JavascriptExecutor)_Myprofiledriver;
	 * //jse.executeScript("arguments[0].click();",_Myprofiledriver.findElement(
	 * deleteResumeConfirm)); //_Utility.Thread_Sleep(4000); //int current_count
	 * =_Myprofiledriver.findElements(resumeCountList).size();
	 * //APP_LOGS.debug("current count>> "+current_count);
	 * //Assert.assertTrue(current_count<previous_count,
	 * "Comment: "+current_count+" : "+previous_count); }
	 */
	  

	@Test (priority =10)
	public void edit_work_experience() {
		//TestLink_testCase = "Validate edit personal details in myprofile";
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.navigate().refresh();
		_Utility.scrollTOElement(workExpEdit, _Myprofiledriver);
		_Myprofiledriver.findElement(By.id("edit-icon-work-summary-web")).click();
		_Utility.Thread_Sleep(3000);
		_Myprofiledriver.findElement(profileTitle).clear();
		_Myprofiledriver.findElement(profileTitle).sendKeys("M.Tech with 10+ years of IT experience in Software Development");
		_Myprofiledriver.findElement(profileSummary).clear();
		_Myprofiledriver.findElement(profileSummary).sendKeys("Test Engineer");
		new Select(_Myprofiledriver.findElement(workExp)).selectByVisibleText("5 Yrs");
		new Select(_Myprofiledriver.findElement(workExpInMonth)).selectByVisibleText("6 Months");
		new Select(_Myprofiledriver.findElement(teamSize)).selectByVisibleText("2");
		new Select(_Myprofiledriver.findElement(salaryInLakh)).selectByVisibleText("6");
		new Select(_Myprofiledriver.findElement(salaryInThou)).selectByVisibleText("35");
		new Select(_Myprofiledriver.findElement(noticePeriod)).selectByVisibleText("5 weeks");
		//_Wait.until(ExpectedConditions.visibilityOfElementLocated(savework_summary));
		_Myprofiledriver.findElement(savework_summary).click();
	}

	@Test (priority =11, dependsOnMethods= {"edit_work_experience"})
	public void verify_work_experience() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("edit_workex");
	}

	@Test (priority =12)
	public void verify_personal_details() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("personal_detail");
	}



	@Test (priority =20)
	public void add_employment_details() {
		//TestLink_testCase = "Validate edit current job in myprofile";
		_Utility.Thread_Sleep(3000);
		//WebDriverWait _Wait = new WebDriverWait(_Myprofiledriver, 30);
		//_Wait.until(ExpectedConditions.visibilityOfElementLocated(addJobDetails));
		_Myprofiledriver.findElement(addJobDetails).click();
		_Utility.Thread_Sleep(3000);
		_Myprofiledriver.findElement(jobTitle).clear();
		_Myprofiledriver.findElement(jobTitle).sendKeys("Quality Analyst "+random_String);
		_Myprofiledriver.findElement(jobTitle).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(companyName).clear();
		_Myprofiledriver.findElement(companyName).click();
		_Myprofiledriver.findElement(companyName).sendKeys("IBM India");
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(companyName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(3000);
		_Myprofiledriver.findElement(companyName).sendKeys(Keys.TAB);
		_Myprofiledriver.findElement(companyName).sendKeys(Keys.ESCAPE);
		_Myprofiledriver.findElement(compIndustryDropDown).click();
		_Myprofiledriver.findElement(compIndSubDd).click();
		_Myprofiledriver.findElement(compFADd).click();
		_Utility.scrollTOElement(companyName, _Myprofiledriver);
		_Myprofiledriver.findElement(compFASubDd).click();
		new Select(_Myprofiledriver.findElement(jobStartMonth)).selectByVisibleText("Feb");
		new Select(_Myprofiledriver.findElement(jobStartYear)).selectByVisibleText("2013");
		_Myprofiledriver.findElement(iWorkHereCheckbox).click();
		_Myprofiledriver.findElement(saveCompDetails).click();

	}

	@Test (priority =21, dependsOnMethods= {"add_employment_details"})
	public void verify_employment_details() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("add_employment");
	}


	@Test (priority =40)
	public void add_education_details() {
		TestLink_testCase = "Validate edit education details in myprofile";
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(addEducationDetails, _Myprofiledriver);
		_Utility.Thread_Sleep(1000);
		_Myprofiledriver.findElement(addEducationDetails).click();
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(educationDetails).click();
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(eduspecialization));
		_Myprofiledriver.findElement(eduspecialization).click();
		_Myprofiledriver.findElement(instituteName).sendKeys("Delhi Public School");
		_Myprofiledriver.findElement(yearOfPassout).click();
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(selectpassout_year));
		_Myprofiledriver.findElement(selectpassout_year).click();		
		_Myprofiledriver.findElement(saveEducationDetailss).click();
	}

	@Test (priority =41, dependsOnMethods= {"add_education_details"})
	public void verify_add_education_details() {
		_Utility.Thread_Sleep(3000);
		verify_profile_api("add_education");
	}


	@Test (priority =50)
	public void add_skills() {
		_Myprofiledriver.navigate().refresh();
		TestLink_testCase = "Validate edit skills in myprofile";
		_Utility.scrollTOElement(addMoreSkillBtn, _Myprofiledriver);
		_Myprofiledriver.findElement(addMoreSkillBtn).click();	
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(skillName).clear();
		_Myprofiledriver.findElement(skillName).sendKeys("Quality Assurance");
		_Utility.Thread_Sleep(1000);
		_Myprofiledriver.findElement(skillName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(1000);
		new Select(_Myprofiledriver.findElement(skillExp)).selectByVisibleText("2 Yrs");
		_Myprofiledriver.findElement(saveSkillBtn).click();
	}

	@Test (priority =51, dependsOnMethods= {"add_skills"})
	public void verify_add_skills() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("add_skills");
	}

	@Test (priority =52)
	public void edit_skills() {
		_Myprofiledriver.navigate().refresh();
		TestLink_testCase = "Validate edit skills in myprofile";
		WebDriverWait _Wait = new WebDriverWait(_Myprofiledriver, 30);
		_Utility.scrollTOElement(addMoreSkillBtn, _Myprofiledriver);
		_Myprofiledriver.findElement(By.id("id_test_24")).click();	
		_Utility.Thread_Sleep(3000);
		_Myprofiledriver.findElement(skillName).clear();
		_Myprofiledriver.findElement(skillName).sendKeys("SQA 9876556789");
		_Utility.Thread_Sleep(1000);
		_Myprofiledriver.findElement(skillName).sendKeys(Keys.TAB);
		_Utility.Thread_Sleep(1000);
		new Select(_Myprofiledriver.findElement(skillExp)).selectByVisibleText("2 Yrs");
		_Wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@value='Save'])[1]")));
		_Myprofiledriver.findElement(By.xpath("(//*[@value='Save'])[1]")).click();
	}

	@Test (priority =53, dependsOnMethods= {"edit_skills"})
	public void verify_edit_skills() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("edit_skills");
	}

	@Test (priority =70)
	public void add_certification() {
		_Myprofiledriver.navigate().refresh();
		TestLink_testCase = "Validate add first certification in myprofile";
		_Utility.Thread_Sleep(1000);
		_Utility.scrollTOElement(certiDiv, _Myprofiledriver);
		_Myprofiledriver.findElement(addMoreCertificationBtn).click();
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(certiName).click();
		_Myprofiledriver.findElement(certiName).sendKeys("testing");
		new Select(_Myprofiledriver.findElement(certiExp)).selectByVisibleText("2008");
		_Myprofiledriver.findElement(saveCertificationBtn).click();

	}

	@Test (priority =71, dependsOnMethods= {"add_certification"})
	public void verify_add_certification() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("add_certification");
	}


	@Test (priority =72)
	public void edit_certification() {
		_Myprofiledriver.navigate().refresh();
		TestLink_testCase = "Validate add first certification in myprofile";
		_Utility.Thread_Sleep(2000);
		_Utility.scrollTOElement(certiDiv, _Myprofiledriver);
		_Myprofiledriver.findElement(By.id("id_test_22")).click();
		_Utility.Thread_Sleep(2000);
		_Myprofiledriver.findElement(certiName).click();
		_Myprofiledriver.findElement(certiName).clear();
		_Myprofiledriver.findElement(certiName).sendKeys("testing 9876556789");
		new Select(_Myprofiledriver.findElement(certiExp)).selectByVisibleText("2008");
		_Myprofiledriver.findElement(saveCertificationBtn).click();

	}

	@Test (priority =73, dependsOnMethods= {"edit_certification"})
	public void verify_edit_certification() {
		_Utility.Thread_Sleep(2000);
		verify_profile_api("edit_certification");
	}

	@Test (priority =75)
	public void verify_desired_job() {
		verify_profile_api("desired_job");
	}




	@Test (priority =80, dataProvider="skilldata")
	public void add_MultipleCertification(String cert, String Year) {
		_Myprofiledriver.navigate().refresh();
		TestLink_testCase="Validate add more certificate in myprofile";
		_Utility.Thread_Sleep(3000);
		WebDriverWait _Wait = new WebDriverWait(_Myprofiledriver, 30);
		_Wait.until(ExpectedConditions.visibilityOfElementLocated(addMoreCertificationBtn));
		_Myprofiledriver.findElement(addMoreCertificationBtn).click();//.cls_editregistration.cls_certification_add
		_Utility.Thread_Sleep(3000);
		_Myprofiledriver.findElement(certiName).click();
		_Myprofiledriver.findElement(certiName).sendKeys(cert);
		new Select(_Myprofiledriver.findElement(certiExp)).selectByVisibleText(Year);
		_Myprofiledriver.findElement(saveCertificationBtn).click();

	}

	@DataProvider
	public Object [][] skilldata(){
		Object [][] data= new Object [3][2];

		data[0][0]="java";
		data[0][1]="2010";		

		data[1][0]="c sharp";
		data[1][1]="2013";

		data[2][0]="QTP";
		data[2][1]="2015";

		return data;

	}

	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _Myprofiledriver);
	}

	@AfterClass(alwaysRun=true)
	public void quitbrowser() {
		if(_Myprofiledriver!=null)
			_Myprofiledriver.quit();

	}



	/*********************************************************************************************************/		


	/*GET API RESPONSE*/
	public void verify_profile_api(String sectionName){
		try{
			APP_LOGS.debug("UID: "+uid);
			String api_url = baseUrl+"/api/v4/candidate-profiles/"+uid+"/";
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(api_url);
			APP_LOGS.debug(api_url);
			String data = email_new + ":" + pass_new;	
			String encodedBytes = Base64.getEncoder().encodeToString((data).getBytes("UTF-8"));
			APP_LOGS.debug("encodedBytes " + encodedBytes);
			Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.header("Content-Type", "application/json")
					.header("Authorization", "Basic "+encodedBytes)
					.header("Accept", "application/json")
					.get();
			//cXVhbGl0eWFiaGlzaGVrQGdtYWlsLmNvbTpwYXNzd29yZA==
			String api_result = response.readEntity(String.class);
			APP_LOGS.debug("Job Alert Api Response: "+api_result);
			int status = response.getStatus();
			APP_LOGS.info("status: " + status);
			Assert.assertEquals(status, 200);
			assert_response(api_result, sectionName);
			response.close(); 
			client.close();
		}
		catch (Exception e) {
			APP_LOGS.debug("Error: "+e.getMessage());
		}
	}

	/*Parse json response*/
	public void assert_response(String response, String sectionName){
		CandidateProfile _CandidateProfile = _Gson.fromJson(response, CandidateProfile.class); 

		if(sectionName.equals("add_employment")) {
			int flag = 0;
			for(int i=0; i<_CandidateProfile.getJobs().size(); i++) {
				String job_title = _CandidateProfile.getJobs().get(i).getJob_title();
				if(job_title.equals("Quality Analyst "+random_String)) {
					int sub_field = _CandidateProfile.getJobs().get(i).getSub_field();
					int industry_id = _CandidateProfile.getJobs().get(i).getIndustry_id();
					boolean is_current = _CandidateProfile.getJobs().get(i).isIs_current();
					String company_name = _CandidateProfile.getJobs().get(i).getCompany_name();
					int start_year = _CandidateProfile.getJobs().get(i).getStart_year();
					int start_month = _CandidateProfile.getJobs().get(i).getStart_month();
					int end_year, end_month = 0;
					try {
						end_year = _CandidateProfile.getJobs().get(i).getEnd_year();
					}catch(Throwable t) {
						APP_LOGS.debug("Handling null point on end_year");
						end_year = 0;
					}
					try {
						end_month = _CandidateProfile.getJobs().get(i).getEnd_month();
					}catch(Throwable t) {
						APP_LOGS.debug("Handling null point on end_month");
						end_month = 0;
					}
					String sub_field_display_value = _CandidateProfile.getJobs().get(i).getSub_field_display_value();
					String industry_id_display_value = _CandidateProfile.getJobs().get(i).getIndustry_id_display_value();

					SoftAssert _SAssert = new SoftAssert();
					
					_SAssert.assertEquals(sub_field, 4556);
					_SAssert.assertEquals(job_title, "Quality Analyst "+random_String);
					_SAssert.assertEquals(industry_id, 18);
					_SAssert.assertEquals(is_current, true);
					_SAssert.assertEquals(company_name, "IBM India");
					_SAssert.assertEquals(start_year, 2013);
					_SAssert.assertEquals(start_month, 2);
					_SAssert.assertEquals(end_year, 0);
					_SAssert.assertEquals(end_month, 0);
					_SAssert.assertEquals(sub_field_display_value, "Audit");
					_SAssert.assertEquals(industry_id_display_value, "IT - Software");
					_SAssert.assertAll();
					flag = 1;
					break;
				}
			}
			if(flag==0)
				Assert.assertTrue(false, "Assertion failed explicitly - No job found");

		}
		else if(sectionName.equals("edit_workex")) {
			for(int i=0; i<_CandidateProfile.getWorkex().size(); i++) {
				int experience_in_years = _CandidateProfile.getWorkex().get(i).getExperience_in_years();
				int experience_in_months = _CandidateProfile.getWorkex().get(i).getExperience_in_months();
				int team_size_managed = _CandidateProfile.getWorkex().get(i).getTeam_size_managed();
				int salary_in_lakh = _CandidateProfile.getWorkex().get(i).getSalary_in_lakh();
				int salary_in_thousand = _CandidateProfile.getWorkex().get(i).getSalary_in_thousand();
				String resume_title = _CandidateProfile.getWorkex().get(i).getResume_title();
				int notice_period = _CandidateProfile.getWorkex().get(i).getNotice_period();
				int previous_salary = _CandidateProfile.getWorkex().get(i).getPrevious_salary();
				String summary = _CandidateProfile.getWorkex().get(i).getSummary();

				SoftAssert _SAssert = new SoftAssert();
				
				_SAssert.assertEquals(experience_in_years, 13);
				_SAssert.assertEquals(experience_in_months, 8);
				_SAssert.assertEquals(team_size_managed, 2);
				_SAssert.assertEquals(salary_in_lakh, 6);
				_SAssert.assertEquals(salary_in_thousand, 35);
				_SAssert.assertEquals(resume_title, "M.Tech with 10+ years of IT experience in Software Development");
				_SAssert.assertEquals(notice_period, 5);
				_SAssert.assertEquals(previous_salary, 12);
				_SAssert.assertEquals(summary, "Test Engineer");
				_SAssert.assertAll();

			}
		}

		else if(sectionName.equals("update_workex")) {
			for(int i=0; i<_CandidateProfile.getWorkex().size();) {
				int experience_in_years = _CandidateProfile.getWorkex().get(i).getExperience_in_years();
				int experience_in_months = _CandidateProfile.getWorkex().get(i).getExperience_in_months();
				int team_size_managed = _CandidateProfile.getWorkex().get(i).getTeam_size_managed();
				int salary_in_lakh = _CandidateProfile.getWorkex().get(i).getSalary_in_lakh();
				int salary_in_thousand = _CandidateProfile.getWorkex().get(i).getSalary_in_thousand();
				String resume_title = _CandidateProfile.getWorkex().get(i).getResume_title();
				int notice_period = _CandidateProfile.getWorkex().get(i).getNotice_period();
				int previous_salary = _CandidateProfile.getWorkex().get(i).getPrevious_salary();
				String summary = _CandidateProfile.getWorkex().get(i).getSummary();
				SoftAssert _SAssert = new SoftAssert();
				_SAssert.assertEquals(experience_in_years, 23);
				_SAssert.assertEquals(experience_in_months, 8);
				_SAssert.assertEquals(team_size_managed, 4);
				_SAssert.assertEquals(salary_in_lakh, 7);
				_SAssert.assertEquals(salary_in_thousand, 30);
				_SAssert.assertEquals(resume_title, "M.Tech with 20+ years of IT experience in Software Development");
				_SAssert.assertEquals(notice_period, 1);
				_SAssert.assertEquals(previous_salary, 13);
				_SAssert.assertEquals(summary, "Senior Test Engineer");
				_SAssert.assertAll();
				break;
			}


		}
		else if(sectionName.equals("add_education")) {
			int flag = 0;
			for(int i=0; i<_CandidateProfile.getEducation().size(); i++) {
				String institute_name = _CandidateProfile.getEducation().get(i).getInstitute_name();
				if(institute_name.equals("Delhi Public School")) {
					int education_level = _CandidateProfile.getEducation().get(i).getEducation_level();
					int education_specialization = _CandidateProfile.getEducation().get(i).getEducation_specialization();
					int year_of_passout = _CandidateProfile.getEducation().get(i).getYear_of_passout();
					int course_type = _CandidateProfile.getEducation().get(i).getCourse_type();
					SoftAssert _SAssert = new SoftAssert();
					_SAssert.assertEquals(education_level, 101);
					_SAssert.assertEquals(education_specialization, 507);
					_SAssert.assertEquals(institute_name, "Delhi Public School");
					_SAssert.assertEquals(year_of_passout, 2005);
					_SAssert.assertEquals(course_type, 1);
					_SAssert.assertAll();
					flag =1;
					break;
				}

			}
			if(flag==0)
				Assert.assertTrue(false, "Assertion failed explicitly - No pre-filled education found");
		}
		else if(sectionName.equals("add_certification")) {
			int flag = 0;
			for(int i=0; i<_CandidateProfile.getCertifications().size(); i++) {
				String certification_name = _CandidateProfile.getCertifications().get(i).getCertification_name();
				if(certification_name.equals("testing")) {
					int certification_year = _CandidateProfile.getCertifications().get(i).getCertification_year();
					SoftAssert _SAssert = new SoftAssert();
					_SAssert.assertEquals(certification_year, 2008);
					_SAssert.assertEquals(certification_name, "testing");
					_SAssert.assertAll();
					flag = 1;
					break;
				}
			}
			if(flag==0)
				Assert.assertTrue(false, "Assertion failed explicitly - No pre-filled skill found");

		}

		else if(sectionName.equals("add_skills")) {
			int flag = 0;
			for(int i=0; i<_CandidateProfile.getSkills().size(); i++) {
				String value = _CandidateProfile.getSkills().get(i).getValue();
				if(value.equals("Quality Assurance")) {
					int years_of_experience = _CandidateProfile.getSkills().get(i).getYears_of_experience();
					String years_of_experience_display_value = _CandidateProfile.getSkills().get(i).getYears_of_experience_display_value();
					SoftAssert _SAssert = new SoftAssert();
					_SAssert.assertEquals(value, "Quality Assurance");
					_SAssert.assertEquals(years_of_experience, 5);
					_SAssert.assertEquals(years_of_experience_display_value, "2 Yrs");
					_SAssert.assertAll();
					flag = 1;
					break;
				}
			}
			if(flag==0)
				Assert.assertTrue(false, "Assertion failed explicitly - No pre-filled skill found");


		}

		else if(sectionName.equals("personal_detail")) {
			for(int i=0; i<_CandidateProfile.getPersonal_detail().size();) {
				String cell_phone = _CandidateProfile.getPersonal_detail().get(i).getCell_phone();
				String country_code = _CandidateProfile.getPersonal_detail().get(i).getCountry_code();
				int gender = _CandidateProfile.getPersonal_detail().get(i).getGender();
				String email = _CandidateProfile.getPersonal_detail().get(i).getEmail();
				int candidate_location = _CandidateProfile.getPersonal_detail().get(i).getCandidate_location();
				String date_of_birth = _CandidateProfile.getPersonal_detail().get(i).getDate_of_birth();
				String resume_title = _CandidateProfile.getPersonal_detail().get(i).getResume_title();
				boolean is_featured_by_career_plus = _CandidateProfile.getPersonal_detail().get(i).isIs_featured_by_career_plus();
				SoftAssert _SAssert = new SoftAssert();
				_SAssert.assertEquals(cell_phone, "9876556789");
				_SAssert.assertEquals(country_code, "91");
				_SAssert.assertEquals(gender, 1);
				_SAssert.assertEquals(email, email_new);
				_SAssert.assertEquals(candidate_location, 406);
				_SAssert.assertEquals(date_of_birth, "1980-01-09");
				_SAssert.assertEquals(resume_title, "M.Tech with 10+ years of IT experience in Software Development");
				_SAssert.assertEquals(is_featured_by_career_plus, false);
				_SAssert.assertAll();
				break;
			}

		}
		else if(sectionName.equals("desired_job")) {
			for(int i=0; i<_CandidateProfile.getDesired_job().size();) {
				List<Integer> minimum_salary = _CandidateProfile.getDesired_job().get(i).getMinimum_salary();
				List<Integer> maximum_salary = _CandidateProfile.getDesired_job().get(i).getMaximum_salary();
				SoftAssert _SAssert = new SoftAssert();
				_SAssert.assertTrue(String.valueOf(minimum_salary).contains("13"), String.valueOf(minimum_salary));
				_SAssert.assertTrue(String.valueOf(maximum_salary).contains("19"), String.valueOf(maximum_salary));
				_SAssert.assertAll();
				break;
			}

		}

		else if(sectionName.equalsIgnoreCase("edit_skills")) {
			int flag = 0;
			for(int i=0; i<_CandidateProfile.getSkills().size(); i++) {
				String value = _CandidateProfile.getSkills().get(i).getValue();
				if(value.equals("SQA 9876556789")) {
					int years_of_experience = _CandidateProfile.getSkills().get(i).getYears_of_experience();
					String years_of_experience_display_value = _CandidateProfile.getSkills().get(i).getYears_of_experience_display_value();
					SoftAssert _SAssert = new SoftAssert();
					_SAssert.assertEquals(value, "SQA 9876556789");
					_SAssert.assertEquals(years_of_experience, 5);
					_SAssert.assertEquals(years_of_experience_display_value, "2 Yrs");
					_SAssert.assertAll();
					flag = 1;
					break;
				}
			}
			if(flag==0)
				Assert.assertTrue(false, "Assertion failed explicitly - No pre-filled skill found");
		}

		else if(sectionName.equalsIgnoreCase("edit_certification")) {
			int flag = 0;
			for(int i=0; i<_CandidateProfile.getCertifications().size(); i++) {
				String certification_name = _CandidateProfile.getCertifications().get(i).getCertification_name();
				if(certification_name.equals("testing 9876556789")) {
					int certification_year = _CandidateProfile.getCertifications().get(i).getCertification_year();
					SoftAssert _SAssert = new SoftAssert();
					_SAssert.assertEquals(certification_year, 2008);
					_SAssert.assertEquals(certification_name, "testing 9876556789");
					_SAssert.assertAll();
					flag = 1;
					break;
				}
			}
			if(flag==0)
				Assert.assertTrue(false, "Assertion failed explicitly - No pre-filled skill found");



		}


	}


	public static void setResumeName(String file_name, WebDriver driver) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("document.getElementsByClassName('cls_fileName filename')[0].textContent = '"+file_name+"'");
		}catch(Throwable t) {
			APP_LOGS.error(t.getMessage());
		}
	}



}
