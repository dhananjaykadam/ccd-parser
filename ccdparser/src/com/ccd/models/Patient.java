package com.ccd.models;

public class Patient {
	private String patientName;
	private String patientBirthDate;
	private int age;
	private String maritalStatus;
	private String gender;
	private String religiousAffiliationCode;
	private String raceCode;
	private String ethnicGroupCode;
	private String languageCode;
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientBirthDate() {
		return patientBirthDate;
	}
	public void setPatientBirthDate(String patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReligiousAffiliationCode() {
		return religiousAffiliationCode;
	}
	public void setReligiousAffiliationCode(String religiousAffiliationCode) {
		this.religiousAffiliationCode = religiousAffiliationCode;
	}
	public String getRaceCode() {
		return raceCode;
	}
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}
	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}
	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	@Override
	public String toString() {
		return "Patient [patientName=" + patientName + ", patientBirthDate="
				+ patientBirthDate + ", age=" + age + ", maritalStatus="
				+ maritalStatus + ", gender=" + gender
				+ ", religiousAffiliationCode=" + religiousAffiliationCode
				+ ", raceCode=" + raceCode + ", ethnicGroupCode="
				+ ethnicGroupCode + ", languageCode=" + languageCode + "]";
	}
	
}
