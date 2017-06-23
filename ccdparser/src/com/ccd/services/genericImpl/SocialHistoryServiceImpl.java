package com.ccd.services.genericImpl;

import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.impl.CDImpl;

import com.ccd.models.SocialHistory;
import com.ccd.services.SocialHistoryService;
import com.ccd.util.SocialHistoryConstant;

public class SocialHistoryServiceImpl implements SocialHistoryService {

    @Override
    public SocialHistory getSocialHistory(Section socialHistorySection) {
	SocialHistory socialHistory = new SocialHistory();

	if (socialHistorySection == null) {
	    return null;
	}
	try {
	    for (Entry entry : socialHistorySection.getEntries()) {
		Observation observation = entry.getObservation();
		if (isContainsTemplateId(observation, SocialHistoryConstant.CCD_SMOKING_STATUS_ID)) {
		    socialHistory.setSmokingStatus(getSmokingStatus(observation));
		} else if (isContainsTemplateId(observation, SocialHistoryConstant.CCD_TOBACCO_USE_ID)) {
		    socialHistory.setTobaccoUse(getTobaccoUse(observation));
		} else if (isContainsTemplateId(observation, SocialHistoryConstant.CCD_PREGNENCY_OBSERVATION_ID)) {
		    socialHistory.setPregnencyObervation(getPregnencyObervation(observation));
		}

	    }
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage()
		    + " occured into SocialHistoryServiceImpl::getSocialHistory method");
	}
	if (socialHistory.getSmokingStatus() == null && socialHistory.getPregnencyObervation() == null
		&& socialHistory.getTobaccoUse() == null) {
	    return null;
	}
	return socialHistory;
    }

    private String getSmokingStatus(Observation observation) {
	try {
	    if (observation.getValues() != null && !observation.getValues().isEmpty()) {
		if (observation.getValues().get(0) != null) {
		    CDImpl c = (CDImpl) observation.getValues().get(0);
		    return c.getDisplayName();
		}
	    }
	} catch (Exception exception) {
	    System.out.println("Exception occured into Social History Service Implementation#getSmokingStatus -"
		    + exception.getMessage());
	}
	return null;
    }

    private String getPregnencyObervation(Observation observation) {
	try {
	    if (observation.getValues() != null && !observation.getValues().isEmpty()) {
		if (observation.getValues().get(0) != null) {
		    CDImpl c = (CDImpl) observation.getValues().get(0);
		    return (c.getDisplayName());
		}
	    }
	} catch (Exception exception) {
	    System.out.println("Exception occured into Social History Service Implementation#getTobaccoUse -"
		    + exception.getMessage());
	}
	return null;
    }

    private String getTobaccoUse(Observation observation) {
	try {
	    if (observation.getValues() != null && !observation.getValues().isEmpty()) {
		if (observation.getValues().get(0) != null) {
		    CDImpl c = (CDImpl) observation.getValues().get(0);
		    return (c.getDisplayName());
		}
	    }
	} catch (Exception exception) {
	    System.out.println("Exception occured into Social History Service Implementation#getTobaccoUse -"
		    + exception.getMessage());
	}
	return null;
    }

    private boolean isContainsTemplateId(Observation observation, String templateId) {
	for (II ii : observation.getTemplateIds()) {
	    if (ii.getRoot().trim().equals(templateId)) {
		return true;
	    }
	}
	return false;
    }

}
