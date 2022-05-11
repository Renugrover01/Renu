package com.shine.beans;


public class QunitReportData {
	
	String project_name;
	String report_header;
	String user_agent;
	String testcase_total_count;
	String testcase_passed_count;
	String testcase_failed_count;
	String testcase_skipped_count;
	String report_status;
	String result;
	
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}	
	public String getReport_header() {
		return report_header;
	}
	public void setReport_header(String report_header) {
		this.report_header = report_header;
	}
	public String getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}
	public String getTestcase_total_count() {
		return testcase_total_count;
	}
	public void setTestcase_total_count(String testcase_total_count) {
		this.testcase_total_count = testcase_total_count;
	}
	public String getTestcase_passed_count() {
		return testcase_passed_count;
	}
	public void setTestcase_passed_count(String testcase_passed_count) {
		this.testcase_passed_count = testcase_passed_count;
	}
	public String getTestcase_failed_count() {
		return testcase_failed_count;
	}
	public void setTestcase_failed_count(String testcase_failed_count) {
		this.testcase_failed_count = testcase_failed_count;
	}
	public String getTestcase_skipped_count() {
		return testcase_skipped_count;
	}
	public void setTestcase_skipped_count(String testcase_skipped_count) {
		this.testcase_skipped_count = testcase_skipped_count;
	}
	public String getReport_status() {
		return report_status;
	}
	public void setReport_status(String report_status) {
		this.report_status = report_status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}


}
