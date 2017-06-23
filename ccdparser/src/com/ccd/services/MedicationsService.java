package com.ccd.services;

import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ccd.MedicationSection;

import com.ccd.models.PatientMedication;

public interface MedicationsService {

	/**
	 * Method to find Patient Medications and build Java class object for per
	 * Medication information
	 * 
	 * @param featureMap
	 * @return
	 */
	public PatientMedication[] findPatientMedication(
			Section section);
	/**
	 * Method to find Patient Medications and build Java class object for per
	 * Medication information
	 * 
	 * @param featureMap
	 * @return
	 */
	PatientMedication[] findPatientMedication(MedicationSection section);
}
