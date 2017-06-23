package com.ccd.services;

import com.ccd.models.Patient;

public interface PatientsService {
	/**
	 * Method returns numbers of patient from given CCD XML
	 */
	public Patient[] getPatients(org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument continuityOfCareDocument);
}
