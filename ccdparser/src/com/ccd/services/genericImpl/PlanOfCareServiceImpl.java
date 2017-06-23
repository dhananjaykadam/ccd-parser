package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.ANY;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;

import com.ccd.models.Code;
import com.ccd.models.Goal;
import com.ccd.models.PlanOfCare;
import com.ccd.services.PlanOfCareService;

public class PlanOfCareServiceImpl implements PlanOfCareService {

    @Override
    public PlanOfCare parsePlanOfCareSection(Section section) {
	if (section == null) {
	    return null;
	}
	PlanOfCare planOfCare = new PlanOfCare();
	List<Goal> goals = new ArrayList<Goal>();

	try {
	    for (Entry entry : section.getEntries()) {
		if (entry.getObservation() != null && entry.getObservation().getMoodCode() != null
			&& entry.getObservation().getMoodCode().getLiteral() != null
			&& entry.getObservation().getMoodCode().getLiteral().equals("GOL")) {
		    Observation observation = entry.getObservation();
		    if (observation.getEntryRelationships().size() > 0) {
			for (EntryRelationship innerEntry : observation.getEntryRelationships()) {
			    try {
				Observation innerObservation = innerEntry.getObservation();
				if (innerObservation != null) {
				    for (ANY any : innerObservation.getValues()) {
					CD cd = (CD) any;
					if (cd.getTranslations() != null && cd.getTranslations().size() > 0) {
					    for (CD cd2 : cd.getTranslations()) {
						try {
						    Goal goal = new Goal();
						    Code code = new Code();
						    goal.setGoalStatement(cd2.getDisplayName());
						    code.setCode(cd2.getCode());
						    code.setCodeSystem(cd2.getCodeSystem());
						    code.setCodeSystemName(cd2.getCodeSystemName());
						    goal.setIcdCode(code);
						    goals.add(goal);
						} catch (Exception exception) {
						    System.out.println("EXCEPTION WHILE PARSING PLAN OF CARE SECTION"
							    + exception.getMessage());
						}
					    }
					} else {
					    Goal goal = new Goal();
					    Code code = new Code();
					    goal.setGoalStatement(cd.getDisplayName());
					    code.setCode(cd.getCode());
					    code.setCodeSystem(cd.getCodeSystem());
					    code.setCodeSystemName(cd.getCodeSystemName());
					    goal.setIcdCode(code);
					    goals.add(goal);
					}
				    }
				}
			    } catch (Exception exception) {
				System.out.println(
					"EXCEPTION WHILE PARSING PLAN OF CARE SECTION" + exception.getMessage());
			    }
			}
		    } else {
			try {
			    for (ANY any : observation.getValues()) {
				CD cd = (CD) any;
				if (cd.getTranslations() != null && cd.getTranslations().size() > 0) {
				    for (CD cd2 : cd.getTranslations()) {
					try {
					    Goal goal = new Goal();
					    Code code = new Code();
					    goal.setGoalStatement(cd2.getDisplayName());
					    code.setCode(cd2.getCode());
					    code.setCodeSystem(cd2.getCodeSystem());
					    code.setCodeSystemName(cd2.getCodeSystemName());
					    goal.setIcdCode(code);
					    goals.add(goal);
					} catch (Exception exception) {
					    System.out.println("EXCEPTION WHILE PARSING PLAN OF CARE SECTION"
						    + exception.getMessage());
					}
				    }
				} else {
				    Goal goal = new Goal();
				    Code code = new Code();
				    goal.setGoalStatement(cd.getDisplayName());
				    code.setCode(cd.getCode());
				    code.setCodeSystem(cd.getCodeSystem());
				    code.setCodeSystemName(cd.getCodeSystemName());
				    goal.setIcdCode(code);
				    goals.add(goal);
				}
			    }
			} catch (Exception exception) {
			    System.out.println("EXCEPTION WHILE PARSING PLAN OF CARE SECTION" + exception.getMessage());
			}
		    }
		}
	    }
	} catch (Exception exception) {
	    System.out.println("EXCEPTION WHILE PARSING PLAN OF CARE SECTION" + exception.getMessage());
	}
	if (!goals.isEmpty()) {
	    planOfCare.setGoals(goals.toArray(new Goal[goals.size()]));
	    return planOfCare;
	}
	return null;
    }
}
