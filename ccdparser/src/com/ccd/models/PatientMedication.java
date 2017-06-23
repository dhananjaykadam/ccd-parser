package com.ccd.models;


/**
 * Model class to carry Patient Medication Information
 * 
 */
public class PatientMedication {
	private String medicationName;
	private Code medicationCode;
	private String direction;
	private String comment;
	private String startDate;
	private String stoppedDate;
	
	
	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStoppedDate() {
		return stoppedDate;
	}

	public void setStoppedDate(String stoppedDate) {
		this.stoppedDate = stoppedDate;
	}

	/**
	 * @return the medicationCode
	 */
	public Code getMedicationCode() {
		return medicationCode;
	}

	/**
	 * @param medicationCode the medicationCode to set
	 */
	public void setMedicationCode(Code medicationCode) {
		this.medicationCode = medicationCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PatientMedication [medicationName=" + medicationName + ", medicationCode=" + medicationCode
				+ ", direction=" + direction + ", comment=" + comment + ", startDate=" + startDate + ", stoppedDate="
				+ stoppedDate + "]";
	}


}
