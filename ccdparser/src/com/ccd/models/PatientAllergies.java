package com.ccd.models;


/**
 * 
 * Model class to carry Patient Allergies information.
 */
public class PatientAllergies {
	private String date;
	private String description;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "PatientAllergies [date=" + date + ", description=" + description + "]";
	}
	
	
}
