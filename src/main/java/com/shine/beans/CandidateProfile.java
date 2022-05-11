package com.shine.beans;

import java.util.List;
import java.util.ArrayList;

/**
 * The {@code CandidateProfile} class represents Shine Candidate Profile data set.
 * It represents the desired_job, certifications, education, jobs, workex, resumes
 * skills, personal_detail, total_experience section of user profile data.
 * 
 * @author  Abhishek Dhoundiyal
 * @see     List
 * @Version 1.0
 */

public class CandidateProfile {

	public List<CandidateProfile.desired_job> getDesired_job() {
		return desired_job;
	}

	public void setDesired_job(List<CandidateProfile.desired_job> desired_job) {
		this.desired_job = desired_job;
	}

	/**
     * Desired job structure
     */
	 private List<desired_job> desired_job = new ArrayList<>();

	public class desired_job {
		private String candidate_id;

		public String getCandidate_id() {
			return candidate_id;
		}

		public void setCandidate_id(String candidate_id) {
			this.candidate_id = candidate_id;
		}

		public List<Integer> getIndustry() {
			return industry;
		}

		public void setIndustry(List<Integer> industry) {
			this.industry = industry;
		}

		public List<Integer> getFunctional_area() {
			return functional_area;
		}

		public void setFunctional_area(List<Integer> functional_area) {
			this.functional_area = functional_area;
		}

		public List<Integer> getMinimum_salary() {
			return minimum_salary;
		}

		public void setMinimum_salary(List<Integer> minimum_salary) {
			this.minimum_salary = minimum_salary;
		}

		public List<Integer> getMaximum_salary() {
			return maximum_salary;
		}

		public void setMaximum_salary(List<Integer> maximum_salary) {
			this.maximum_salary = maximum_salary;
		}

		public List<Integer> getCandidate_location() {
			return candidate_location;
		}

		public void setCandidate_location(List<Integer> candidate_location) {
			this.candidate_location = candidate_location;
		}

		public List<Integer> getJob_type() {
			return job_type;
		}

		public void setJob_type(List<Integer> job_type) {
			this.job_type = job_type;
		}

		public List<Integer> getShift_type() {
			return shift_type;
		}

		public void setShift_type(List<Integer> shift_type) {
			this.shift_type = shift_type;
		}

		private List<Integer> industry = new ArrayList<Integer>();
		private List<Integer> functional_area = new ArrayList<Integer>();
		private List<Integer> minimum_salary = new ArrayList<Integer>();
		private List<Integer> maximum_salary = new ArrayList<Integer>();
		private List<Integer> candidate_location = new ArrayList<Integer>();
		private List<Integer> job_type = new ArrayList<Integer>();
		private List<Integer> shift_type = new ArrayList<Integer>();

	}


	/**
     * Certifications structure
     */

	private List<certifications> certifications = new ArrayList<certifications>();

	public List<CandidateProfile.certifications> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<CandidateProfile.certifications> certifications) {
		this.certifications = certifications;
	}

	public class certifications {
		public int getCertification_year() {
			return certification_year;
		}

		public void setCertification_year(int certification_year) {
			this.certification_year = certification_year;
		}

		public String getCertification_name() {
			return certification_name;
		}

		public void setCertification_name(String certification_name) {
			this.certification_name = certification_name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		private int certification_year;
		private String certification_name;
		private String id;
	}



	/**
	 * Education structure 
	 */
	private List<education> education = new ArrayList<education>();

	public List<CandidateProfile.education> getEducation() {
		return education;
	}

	public void setEducation(List<CandidateProfile.education> education) {
		this.education = education;
	}

	public class education {
		private String id;
		private String candidate_id;
		private int education_level;
		private int education_specialization;
		private String institute_name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCandidate_id() {
			return candidate_id;
		}

		public void setCandidate_id(String candidate_id) {
			this.candidate_id = candidate_id;
		}

		public int getEducation_level() {
			return education_level;
		}

		public void setEducation_level(int education_level) {
			this.education_level = education_level;
		}

		public int getEducation_specialization() {
			return education_specialization;
		}

		public void setEducation_specialization(int education_specialization) {
			this.education_specialization = education_specialization;
		}

		public String getInstitute_name() {
			return institute_name;
		}

		public void setInstitute_name(String institute_name) {
			this.institute_name = institute_name;
		}

		public int getYear_of_passout() {
			return year_of_passout;
		}

		public void setYear_of_passout(int year_of_passout) {
			this.year_of_passout = year_of_passout;
		}

		public int getCourse_type() {
			return course_type;
		}

		public void setCourse_type(int course_type) {
			this.course_type = course_type;
		}

		private int year_of_passout;
		private int course_type;

	}

	/**
     * Jobs structure
     */
	private List<jobs> jobs = new ArrayList<jobs>();

	public List<CandidateProfile.jobs> getJobs() {
		return jobs;
	}

	public void setJobs(List<CandidateProfile.jobs> jobs) {
		this.jobs = jobs;
	}


	public class jobs {
		private String id;
		private String candidate_id;
		private int sub_field;
		private String job_title;
		private int industry_id;
		private boolean is_current;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCandidate_id() {
			return candidate_id;
		}

		public void setCandidate_id(String candidate_id) {
			this.candidate_id = candidate_id;
		}

		public int getSub_field() {
			return sub_field;
		}

		public void setSub_field(int sub_field) {
			this.sub_field = sub_field;
		}

		public String getJob_title() {
			return job_title;
		}

		public void setJob_title(String job_title) {
			this.job_title = job_title;
		}

		public int getIndustry_id() {
			return industry_id;
		}

		public void setIndustry_id(int industry_id) {
			this.industry_id = industry_id;
		}

		public boolean isIs_current() {
			return is_current;
		}

		public void setIs_current(boolean is_current) {
			this.is_current = is_current;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public int getStart_year() {
			return start_year;
		}

		public void setStart_year(int start_year) {
			this.start_year = start_year;
		}

		public int getStart_month() {
			return start_month;
		}

		public void setStart_month(int start_month) {
			this.start_month = start_month;
		}

		public int getEnd_year() {
			return end_year;
		}

		public void setEnd_year(int end_year) {
			this.end_year = end_year;
		}

		public int getEnd_month() {
			return end_month;
		}

		public void setEnd_month(int end_month) {
			this.end_month = end_month;
		}

		public int getEnd_date() {
			return end_date;
		}

		public void setEnd_date(int end_date) {
			this.end_date = end_date;
		}

		public String getStart_date() {
			return start_date;
		}

		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}

		public String getSub_field_display_value() {
			return sub_field_display_value;
		}

		public void setSub_field_display_value(String sub_field_display_value) {
			this.sub_field_display_value = sub_field_display_value;
		}

		public String getIndustry_id_display_value() {
			return industry_id_display_value;
		}

		public void setIndustry_id_display_value(String industry_id_display_value) {
			this.industry_id_display_value = industry_id_display_value;
		}

		private String description;
		private String company_name;
		private int start_year;
		private int start_month;
		private int end_year;
		private int end_month;
		private int end_date;
		private String start_date;
		private String sub_field_display_value;
		private String industry_id_display_value;

	}



	/**
     * Work experience structure
     */
	private List<workex> workex = new ArrayList<workex>();

	public List<CandidateProfile.workex> getWorkex() {
		return workex;
	}

	public void setWorkex(List<CandidateProfile.workex> workex) {
		this.workex = workex;
	}


	public class workex {
		private String id;
		private int experience_in_years;
		private int experience_in_months;
		private int team_size_managed;
		private int salary_in_lakh;
		private int salary_in_thousand;
		private String resume_title;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getExperience_in_years() {
			return experience_in_years;
		}

		public void setExperience_in_years(int experience_in_years) {
			this.experience_in_years = experience_in_years;
		}

		public int getExperience_in_months() {
			return experience_in_months;
		}

		public void setExperience_in_months(int experience_in_months) {
			this.experience_in_months = experience_in_months;
		}

		public int getTeam_size_managed() {
			return team_size_managed;
		}

		public void setTeam_size_managed(int team_size_managed) {
			this.team_size_managed = team_size_managed;
		}

		public int getSalary_in_lakh() {
			return salary_in_lakh;
		}

		public void setSalary_in_lakh(int salary_in_lakh) {
			this.salary_in_lakh = salary_in_lakh;
		}

		public int getSalary_in_thousand() {
			return salary_in_thousand;
		}

		public void setSalary_in_thousand(int salary_in_thousand) {
			this.salary_in_thousand = salary_in_thousand;
		}

		public String getResume_title() {
			return resume_title;
		}

		public void setResume_title(String resume_title) {
			this.resume_title = resume_title;
		}

		public int getNotice_period() {
			return notice_period;
		}

		public void setNotice_period(int notice_period) {
			this.notice_period = notice_period;
		}

		public int getPrevious_salary() {
			return previous_salary;
		}

		public void setPrevious_salary(int previous_salary) {
			this.previous_salary = previous_salary;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		private int notice_period;
		private int previous_salary;
		private String summary;

	}



	/**
     * Resume structure
     */

	private List<resumes> resumes = new ArrayList<resumes>();

	public List<CandidateProfile.resumes> getResumes() {
		return resumes;
	}

	public void setResumes(List<CandidateProfile.resumes> resumes) {
		this.resumes = resumes;
	}

	public class resumes {
		private String id;
		private String candidate_id;
		private String creation_date;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCandidate_id() {
			return candidate_id;
		}

		public void setCandidate_id(String candidate_id) {
			this.candidate_id = candidate_id;
		}

		public String getCreation_date() {
			return creation_date;
		}

		public void setCreation_date(String creation_date) {
			this.creation_date = creation_date;
		}

		public String getResume_name() {
			return resume_name;
		}

		public void setResume_name(String resume_name) {
			this.resume_name = resume_name;
		}

		public String getExtension() {
			return extension;
		}

		public void setExtension(String extension) {
			this.extension = extension;
		}

		public int getIs_default() {
			return is_default;
		}

		public void setIs_default(int is_default) {
			this.is_default = is_default;
		}

		private String resume_name;
		private String extension;
		private int is_default;

	}


	/**
     * Skills structure
     */

	private List<skills> skills = new ArrayList<skills>();

	public List<CandidateProfile.skills> getSkills() {
		return skills;
	}

	public void setSkills(List<CandidateProfile.skills> skills) {
		this.skills = skills;
	}


	public class skills {
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCandidate_id() {
			return candidate_id;
		}

		public void setCandidate_id(String candidate_id) {
			this.candidate_id = candidate_id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getYears_of_experience() {
			return years_of_experience;
		}

		public void setYears_of_experience(int years_of_experience) {
			this.years_of_experience = years_of_experience;
		}

		public String getYears_of_experience_display_value() {
			return years_of_experience_display_value;
		}

		public void setYears_of_experience_display_value(String years_of_experience_display_value) {
			this.years_of_experience_display_value = years_of_experience_display_value;
		}

		private String id;
		private String candidate_id;
		private String value;
		private int years_of_experience;
		private String years_of_experience_display_value;

	}


	/**
	 * Personal detail structure
	 */
	private List<personal_detail> personal_detail = new ArrayList<personal_detail>();


	public List<CandidateProfile.personal_detail> getPersonal_detail() {
		return personal_detail;
	}

	public void setPersonal_detail(List<CandidateProfile.personal_detail> personal_detail) {
		this.personal_detail = personal_detail;
	}


	public class personal_detail {
		private String id;
		private String first_name;
		private String last_name;
		private String cell_phone;
		private String country_code;
		private int gender;
		private String email;
		private int candidate_location;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getFirst_name() {
			return first_name;
		}

		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}

		public String getLast_name() {
			return last_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		public String getCell_phone() {
			return cell_phone;
		}

		public void setCell_phone(String cell_phone) {
			this.cell_phone = cell_phone;
		}

		public String getCountry_code() {
			return country_code;
		}

		public void setCountry_code(String country_code) {
			this.country_code = country_code;
		}

		public int getGender() {
			return gender;
		}

		public void setGender(int gender) {
			this.gender = gender;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getCandidate_location() {
			return candidate_location;
		}

		public void setCandidate_location(int candidate_location) {
			this.candidate_location = candidate_location;
		}

		public String getDate_of_birth() {
			return date_of_birth;
		}

		public void setDate_of_birth(String date_of_birth) {
			this.date_of_birth = date_of_birth;
		}

		public String getResume_title() {
			return resume_title;
		}

		public void setResume_title(String resume_title) {
			this.resume_title = resume_title;
		}

		public int getIs_email_verified() {
			return is_email_verified;
		}

		public void setIs_email_verified(int is_email_verified) {
			this.is_email_verified = is_email_verified;
		}

		public int getIs_cell_phone_verified() {
			return is_cell_phone_verified;
		}

		public void setIs_cell_phone_verified(int is_cell_phone_verified) {
			this.is_cell_phone_verified = is_cell_phone_verified;
		}

		public boolean isIs_featured_by_career_plus() {
			return is_featured_by_career_plus;
		}

		public void setIs_featured_by_career_plus(boolean is_featured_by_career_plus) {
			this.is_featured_by_career_plus = is_featured_by_career_plus;
		}

		private String date_of_birth;
		private String resume_title;
		private int is_email_verified;
		private int is_cell_phone_verified;
		private boolean is_featured_by_career_plus;

	}




	/**
     * Total experience structure
     */
	private List<total_experience> total_experience = new ArrayList<total_experience>();

	public List<CandidateProfile.total_experience> getTotal_experience() {
		return total_experience;
	}

	public void setTotal_experience(List<CandidateProfile.total_experience> total_experience) {
		this.total_experience = total_experience;
	}

	public class total_experience {
		public int getExperience_in_years() {
			return experience_in_years;
		}

		public void setExperience_in_years(int experience_in_years) {
			this.experience_in_years = experience_in_years;
		}

		public int getExperience_in_months() {
			return experience_in_months;
		}

		public void setExperience_in_months(int experience_in_months) {
			this.experience_in_months = experience_in_months;
		}

		private int experience_in_years;
		private int experience_in_months;

	}

}
