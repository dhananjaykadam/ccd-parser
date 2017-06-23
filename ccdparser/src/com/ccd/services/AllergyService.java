package com.ccd.services;

import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ccd.AlertsSection;

import com.ccd.models.PatientAllergies;

public interface AllergyService {

	/**
	 * Method to find Patient Allergies and build Java class object for per
	 * Allergy information
	 * 
	 * @param featureMap
	 * @return
	 */
	public PatientAllergies[] findPatientAllergiesAlerts(
			AlertsSection alertsSection);
	/**
	 * Method to find Patient Allergies and build Java class object for per
	 * Allergy information
	 * 
	 * @param featureMap
	 * @return
	 */
	public PatientAllergies[] findPatientAllergiesAlerts(
			Section section);
}
