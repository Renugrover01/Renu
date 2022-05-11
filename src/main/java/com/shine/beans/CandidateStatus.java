package com.shine.beans;

public class CandidateStatus {
	
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMid_out() {
		return mid_out;
	}
	public void setMid_out(int mid_out) {
		this.mid_out = mid_out;
	}
	public int getResume_mid_out() {
		return resume_mid_out;
	}
	public void setResume_mid_out(int resume_mid_out) {
		this.resume_mid_out = resume_mid_out;
	}
	public String getLast_revival_date() {
		return last_revival_date;
	}
	public void setLast_revival_date(String last_revival_date) {
		this.last_revival_date = last_revival_date;
	}
	public String getRevival_vendor_id() {
		return revival_vendor_id;
	}
	public void setRevival_vendor_id(String revival_vendor_id) {
		this.revival_vendor_id = revival_vendor_id;
	}
	public int getExit_reason() {
		return exit_reason;
	}
	public void setExit_reason(int exit_reason) {
		this.exit_reason = exit_reason;
	}
	public String getExit_date() {
		return exit_date;
	}
	public void setExit_date(String exit_date) {
		this.exit_date = exit_date;
	}
	public String getComputed_exit_date() {
		return computed_exit_date;
	}
	public void setComputed_exit_date(String computed_exit_date) {
		this.computed_exit_date = computed_exit_date;
	}
	int mid_out;
	int resume_mid_out;
	String last_revival_date;
	String revival_vendor_id;
	int exit_reason;
	String exit_date;
	String computed_exit_date;
	


}
