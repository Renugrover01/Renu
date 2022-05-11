package com.shine.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code CandidateProfile} class represents Shine Candidate Profile data set.
 * It represents the desired_job, certifications, education, jobs, workex, resumes
 * skills, personal_detail, total_experience section of user profile data.
 * 
 * @author  Abhishek Dhoundiyal
 * @see     ArrayList
 * @Version 1.0
 */

public class Search {

	public List<Search.results> getResults() {
		return results;
	}

	public void setResults(List<Search.results> results) {
		this.results = results;
	}

	private List<results> results = new ArrayList<>();

	public class results{
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getjSlug() {
			return jSlug;
		}

		public void setjSlug(String jSlug) {
			this.jSlug = jSlug;
		}

		public int getjEType() {
			return jEType;
		}

		public void setjEType(int jEType) {
			this.jEType = jEType;
		}

		public int getjTypeC() {
			return jTypeC;
		}

		public void setjTypeC(int jTypeC) {
			this.jTypeC = jTypeC;
		}

		private String jSlug;
		private int jEType;
		private int jTypeC;
	}
}
