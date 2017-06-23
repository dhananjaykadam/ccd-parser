package com.ccd.models;

import java.util.Arrays;

/**
 * Model class to carry Patient Problem Information.
 *
 */
public class PatientProblemDiagnosis {
	private String diagnosis;
	private String note;
	private Code icdCode;
	private String date;
	private InterpretationCode [] interpretationCodes;
	
	/**
	 * @return the diagnosis
	 */
	public String getDiagnosis() {
		return diagnosis;
	}
	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the icdCode
	 */
	public Code getIcdCode() {
		return icdCode;
	}
	/**
	 * @param icdCode the icdCode to set
	 */
	public void setIcdCode(Code icdCode) {
		this.icdCode = icdCode;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
	    return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
	    this.date = date;
	}
	/**
	 * @return the interpretationCodes
	 */
	public InterpretationCode[] getInterpretationCodes() {
	    return interpretationCodes;
	}
	/**
	 * @param interpretationCodes the interpretationCodes to set
	 */
	public void setInterpretationCodes(InterpretationCode[] interpretationCodes) {
	    this.interpretationCodes = interpretationCodes;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	    return "PatientProblemDiagnosis [diagnosis=" + diagnosis + ", note=" + note + ", icdCode=" + icdCode
		    + ", date=" + date + ", interpretationCodes=" + Arrays.toString(interpretationCodes) + "]";
	}
	
	
}
