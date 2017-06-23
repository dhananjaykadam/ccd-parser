package com.ccd.services.genericImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.ccd.models.Patient;
import com.ccd.services.PatientsService;
import com.ccd.util.CommonUtil;

public class PatientsServiceImpl implements PatientsService {
    /**
     * Method returns numbers of patient from given CCD XML
     */
    @Override
    public Patient[] getPatients(
	    org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument continuityOfCareDocument) {
	if (continuityOfCareDocument == null || continuityOfCareDocument.getPatients() == null) {
	    return null;
	}
	EList<org.openhealthtools.mdht.uml.cda.Patient> patients = continuityOfCareDocument.getPatients();
	List<Patient> patientList = new ArrayList<Patient>();
	try {
	    for (org.openhealthtools.mdht.uml.cda.Patient pat : patients) {
		Patient patient = new Patient();
		if (pat.getNames() != null && pat.getNames().size() > 0 && pat.getNames().get(0).getGivens() != null
			&& pat.getNames().get(0).getGivens().size() > 0) {
		    patient.setPatientName(pat.getNames().get(0).getGivens().get(0).getText());
		}
		if (pat.getNames() != null && pat.getNames().size() > 0 && pat.getNames().get(0).getFamilies() != null
			&& pat.getNames().get(0).getFamilies().size() > 0) {
		    patient.setPatientName(
			    patient.getPatientName() + " " + pat.getNames().get(0).getFamilies().get(0).getText());
		}
		if (pat.getBirthTime() != null && pat.getBirthTime().getValue() != null
			&& !pat.getBirthTime().getValue().equals("")) {
		    Date dob = CommonUtil.parseEffectiveTimeyyyMMdd(pat.getBirthTime().getValue());

		    if (dob != null) {
			try {
			    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    String date = formatter.format(dob);
			    patient.setPatientBirthDate(date);
			} catch (Exception exception) {
			    System.out.println("Parsing of birthdate failed, reason:" + exception.getMessage());
			}
		    }
		}
		if (pat.getAdministrativeGenderCode() != null && pat.getAdministrativeGenderCode().getCode() != null
			&& !pat.getAdministrativeGenderCode().getCode().equals("")) {
		    patient.setGender(pat.getAdministrativeGenderCode().getCode());
		}
		if (pat.getMaritalStatusCode() != null && pat.getMaritalStatusCode().getCode() != null
			&& !pat.getMaritalStatusCode().getCode().equals("")) {
		    patient.setMaritalStatus(pat.getMaritalStatusCode().getCode());
		}
		if (pat.getEthnicGroupCode() != null && pat.getEthnicGroupCode().getCode() != null) {
		    patient.setEthnicGroupCode(pat.getEthnicGroupCode().getCode());
		}
		if (pat.getLanguageCommunications() != null && pat.getLanguageCommunications().size() > 0
			&& pat.getLanguageCommunications().get(0).getLanguageCode() != null) {
		    patient.setLanguageCode(pat.getLanguageCommunications().get(0).getLanguageCode().getCode());
		}
		if (pat.getReligiousAffiliationCode() != null && pat.getReligiousAffiliationCode().getCode() != null) {
		    patient.setReligiousAffiliationCode(pat.getReligiousAffiliationCode().getCode());
		}
		if (pat.getRaceCode() != null) {
		    patient.setRaceCode(pat.getRaceCode().getDisplayName());
		}
		patientList.add(patient);
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientList.size() > 0)
	    return patientList.toArray(new Patient[patientList.size()]);
	return null;
    }

}
