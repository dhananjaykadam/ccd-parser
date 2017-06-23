package com.ccd.services;

import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ccd.ProblemSection;

import com.ccd.models.PatientProblemDiagnosis;

public interface ProblemsService {

	/**
	 * Method to find Patient Problems and build Java class object for per
	 * problem
	 * 
	 * @param featureMap
	 * @return
	 */
	public PatientProblemDiagnosis[] findProblems(
			org.openhealthtools.mdht.uml.cda.ccd.ProblemSection problemSection) ;
	
	/**
	 * Method to find Patient Problems and build Java class object for per
	 * problem [if no {@link ProblemSection} object found]
	 * 
	 * @param featureMap
	 * @return
	 */
	public PatientProblemDiagnosis[] findProblems(Section section) ;
}
