package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Participant2;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ccd.AlertsSection;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.impl.CDImpl;

import com.ccd.models.PatientAllergies;
import com.ccd.services.AllergyService;
import com.ccd.util.AllergySectionConstants;
import com.ccd.util.CommonUtil;

public class AllergyServiceImpl implements AllergyService {

    /**
     * Method to find Patient Allergies and build Java class object for per
     * Allergy information
     * 
     * @param featureMap
     * @return
     * 
     */
    @Override
    public PatientAllergies[] findPatientAllergiesAlerts(AlertsSection section) {
	List<PatientAllergies> patientAllergiesList = new ArrayList<PatientAllergies>();
	try {
	    for (Entry entry : section.getEntries()) {
		if (entry.getAct() != null && entry.getAct().getEntryRelationships() != null) {
		    for (EntryRelationship entryRelationship : entry.getAct().getEntryRelationships()) {
			if (entryRelationship.getObservation() != null
				&& entryRelationship.getObservation().getParticipants() != null) {
			    for (Participant2 participant2 : entryRelationship.getObservation().getParticipants()) {
				try {
				    if (participant2.getParticipantRole() != null
					    && participant2.getParticipantRole().getPlayingEntity() != null
					    && participant2.getParticipantRole().getPlayingEntity().getCode() != null) {
					PatientAllergies patientAllergy = new PatientAllergies();
					if (participant2.getParticipantRole().getPlayingEntity().getCode()
						.getDisplayName() != null
						&& !participant2.getParticipantRole().getPlayingEntity().getCode()
							.getDisplayName().trim().equals("")) {
					    patientAllergy.setDescription(participant2.getParticipantRole()
						    .getPlayingEntity().getCode().getDisplayName());
					} else {
					    // get from translation
					    if (participant2.getParticipantRole().getPlayingEntity().getCode()
						    .getTranslations() != null
						    && participant2.getParticipantRole().getPlayingEntity().getCode()
							    .getTranslations().size() > 0) {
						for (CD cd : participant2.getParticipantRole().getPlayingEntity()
							.getCode().getTranslations()) {
						    if (patientAllergy.getDescription() == null) {
							patientAllergy.setDescription(cd.getDisplayName());
						    } else {
							patientAllergy.setDescription(patientAllergy.getDescription()
								+ "/" + cd.getDisplayName());
						    }
						}
					    }
					}
					if (entryRelationship.getObservation().getEffectiveTime() != null
						&& entryRelationship.getObservation().getEffectiveTime()
							.getLow() != null) {
					    patientAllergy.setDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
						    CommonUtil.parseEffectiveTimeyyyMMdd(entryRelationship
							    .getObservation().getEffectiveTime().getLow().getValue())));
					} else if (entryRelationship.getObservation().getEffectiveTime() != null
						&& entryRelationship.getObservation().getEffectiveTime()
							.getHigh() != null) {
					    patientAllergy.setDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
						    CommonUtil.parseEffectiveTimeyyyMMdd(
							    entryRelationship.getObservation().getEffectiveTime()
								    .getHigh().getValue())));
					}
					if (patientAllergy.getDescription() != null) {
					    patientAllergiesList.add(patientAllergy);
					}
				    }
				} catch (Exception exception) {
				    System.out.println("Unable to parse Participant, reason:" + exception.getMessage());
				}
			    }
			}
		    }
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientAllergiesList.size() > 0)
	    return patientAllergiesList.toArray(new PatientAllergies[patientAllergiesList.size()]);
	return null;
    }

    /**
     * Method to find Patient Allergies and build Java class object for per
     * Allergy information
     * 
     * @param featureMap
     * @return
     * 
     */
    @Override
    public PatientAllergies[] findPatientAllergiesAlerts(Section section) {
	List<PatientAllergies> patientAllergiesList = new ArrayList<PatientAllergies>();
	try {
	    for (Entry entry : section.getEntries()) {
		if (entry.getAct() != null && entry.getAct().getEntryRelationships() != null) {
		    for (EntryRelationship entryRelationship : entry.getAct().getEntryRelationships()) {
			if (entryRelationship.getObservation() != null
				&& entryRelationship.getObservation().getParticipants() != null) {
			    for (Participant2 participant2 : entryRelationship.getObservation().getParticipants()) {
				try {
				    if (participant2.getParticipantRole() != null
					    && participant2.getParticipantRole().getPlayingEntity() != null
					    && participant2.getParticipantRole().getPlayingEntity().getCode() != null) {
					PatientAllergies patientAllergy = new PatientAllergies();
					if (participant2.getParticipantRole().getPlayingEntity().getCode()
						.getDisplayName() != null
						&& !participant2.getParticipantRole().getPlayingEntity().getCode()
							.getDisplayName().trim().equals("")) {
					    patientAllergy.setDescription(participant2.getParticipantRole()
						    .getPlayingEntity().getCode().getDisplayName());
					    // get from translation
					} else if (participant2.getParticipantRole().getPlayingEntity().getCode()
						.getTranslations() != null
						&& participant2.getParticipantRole().getPlayingEntity().getCode()
							.getTranslations().size() > 0) {
					    for (CD cd : participant2.getParticipantRole().getPlayingEntity().getCode()
						    .getTranslations()) {
						if (patientAllergy.getDescription() == null) {
						    patientAllergy.setDescription(cd.getDisplayName());
						} else {
						    patientAllergy.setDescription(patientAllergy.getDescription() + "/"
							    + cd.getDisplayName());
						}
					    }
					    // get from allergy reaction
					} else {
					    for (EntryRelationship innerEntryRelationship : entryRelationship
						    .getObservation().getEntryRelationships()) {
						if (innerEntryRelationship.getObservation() != null) {
						    for (II ii : innerEntryRelationship.getObservation()
							    .getTemplateIds()) {
							if (ii.getRoot().equals(
								AllergySectionConstants.ALLERGY_REACTION_OBSERVATION)) {
							    if (innerEntryRelationship.getObservation()
								    .getValues() != null
								    && innerEntryRelationship.getObservation()
									    .getValues().size() > 0) {
								patientAllergy
									.setDescription(((CDImpl) innerEntryRelationship
										.getObservation().getValues().get(0))
											.getDisplayName());
							    }
							}
						    }
						}
					    }
					}
					if (entryRelationship.getObservation().getEffectiveTime() != null
						&& entryRelationship.getObservation().getEffectiveTime()
							.getLow() != null) {
					    patientAllergy.setDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
						    CommonUtil.parseEffectiveTimeyyyMMdd(entryRelationship
							    .getObservation().getEffectiveTime().getLow().getValue())));
					} else if (entryRelationship.getObservation().getEffectiveTime() != null
						&& entryRelationship.getObservation().getEffectiveTime()
							.getHigh() != null) {
					    patientAllergy.setDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
						    CommonUtil.parseEffectiveTimeyyyMMdd(
							    entryRelationship.getObservation().getEffectiveTime()
								    .getHigh().getValue())));
					}
					if (patientAllergy.getDescription() != null) {
					    patientAllergiesList.add(patientAllergy);
					}
				    }
				} catch (Exception exception) {
				    System.out.println("Unable to parse Participant, reason:" + exception.getMessage());
				}
			    }
			}
		    }
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientAllergiesList.size() > 0)
	    return patientAllergiesList.toArray(new PatientAllergies[patientAllergiesList.size()]);
	return null;
    }
}
