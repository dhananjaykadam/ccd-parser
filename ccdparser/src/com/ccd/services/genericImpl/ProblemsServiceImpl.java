package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.omg.CORBA.Any;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.ANY;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.impl.CDImpl;
import org.openhealthtools.mdht.uml.hl7.datatypes.impl.PQImpl;

import com.ccd.models.Code;
import com.ccd.models.InterpretationCode;
import com.ccd.models.PatientProblemDiagnosis;
import com.ccd.services.ProblemsService;
import com.ccd.util.CommonUtil;

public class ProblemsServiceImpl implements ProblemsService {

    private static String BLANK = "";

    /**
     * Method to find Patient Problems and build Java class object for per
     * problem
     * 
     * @param org.openhealthtools.mdht.uml.cda.ccd.ProblemSection
     * @return
     */
    public PatientProblemDiagnosis[] findProblems(org.openhealthtools.mdht.uml.cda.ccd.ProblemSection section) {


	List<PatientProblemDiagnosis> patientProblemDiagnosisList = new ArrayList<PatientProblemDiagnosis>(0);
	try {
	    for (org.openhealthtools.mdht.uml.cda.Entry entry : section.getEntries()) {
		for (EntryRelationship entryRelationship : entry.getAct().getEntryRelationships()) {
		    try
		    {
		    if (entryRelationship.getObservation()!=null && entryRelationship.getObservation().getValues() != null){
			for(ANY any: entryRelationship.getObservation().getValues()){
			EList<CD> translations=((CD) any).getTranslations();
			if (translations != null && translations.size()>0) {
			    for(CD cd : translations){
			    PatientProblemDiagnosis patientProblemDiagnosis = new PatientProblemDiagnosis();
			    patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation()));
			    patientProblemDiagnosis.setInterpretationCodes(findInterpretationCode(entryRelationship.getObservation()));
			     String diagnosis = null;
			    Code icdCode = null;
			    if (cd != null) {
				diagnosis = cd.getDisplayName();
				icdCode = new Code();
				if (cd.getCode() != null && cd.getCode() != BLANK){
				    icdCode.setCodeSystemName(cd.getCodeSystemName());
				} 
				if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK)
				    icdCode.setCodeSystem(cd.getCodeSystem());
				if (cd.getCode() != null && cd.getCode() != BLANK)
				    icdCode.setCode(cd.getCode());
			    }
			    if (diagnosis != null && !diagnosis.trim().equals(BLANK)) {
				patientProblemDiagnosis.setDiagnosis(diagnosis);
				patientProblemDiagnosis.setIcdCode(icdCode);
				

				if (entryRelationship.getObservation() != null
					&& entryRelationship.getObservation().getEntryRelationships() != null
					&& entryRelationship.getObservation().getEntryRelationships().size() > 0
					&& entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation() != null) {
				    if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
					    .getObservation())!=null){
				    patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
					    .getObservation()));
				    }
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    try{
				    		ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
					    if(note.getClass().equals(CDImpl.class)){
					    patientProblemDiagnosis.setNote(((CDImpl) note)
						    .getDisplayName());
					    }
					    if(note.getClass().equals(PQImpl.class)){
						if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
						    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
						}
						    }
					    
					    patientProblemDiagnosis.setNote(((CDImpl) entryRelationship.getObservation()
						    .getEntryRelationships().get(0).getObservation().getValues().get(0))
							    .getDisplayName());
					}catch(Exception exception){
					    System.out.println(
						    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
					}
					}
				}
				patientProblemDiagnosisList.add(patientProblemDiagnosis);
			    } else {
				cd = (CD) any;
				if (cd != null) {
				    try{
				    diagnosis = cd.getDisplayName();
				    icdCode = new Code();
				    if (cd.getCodeSystemName() != null && cd.getCodeSystemName() != BLANK){
					icdCode.setCodeSystemName(cd.getCodeSystemName());
				    }
				    if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK){
					icdCode.setCodeSystem(cd.getCodeSystem());
				    }
				    if (cd.getCode() != null && cd.getCode() != BLANK){
					icdCode.setCode(cd.getCode());
				    }else{
					if(cd.getTranslations()!=null && cd.getTranslations().size()>0){
					    icdCode.setCodeSystemName(cd.getTranslations().get(0).getCodeSystemName());
					    icdCode.setCodeSystem(cd.getTranslations().get(0).getCodeSystem());
					    icdCode.setCode(cd.getTranslations().get(0).getCode());
					}
				    }
				 }catch(Exception exception){
					   System.out.println(
						    "[EXCEPTION IN PROBLEM ICD PARSING OCCURED]" + exception.getMessage());
				    }
				}
				if (diagnosis != null && !diagnosis.trim().equals(BLANK)) {
				    patientProblemDiagnosis.setDiagnosis(diagnosis);
				    patientProblemDiagnosis.setIcdCode(icdCode);
				    if (entryRelationship.getObservation() != null
					    && entryRelationship.getObservation().getEntryRelationships() != null
					    && entryRelationship.getObservation().getEntryRelationships().size() > 0
					    && entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation() != null) {
					if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation())!=null){
					patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation()));
					}
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    try{
				    		ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
					    if(note.getClass().equals(CDImpl.class)){
					    patientProblemDiagnosis.setNote(((CDImpl) note)
						    .getDisplayName());
					    }
					    if(note.getClass().equals(PQImpl.class)){
						if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
						    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
						}
						    }
					    
					    patientProblemDiagnosis.setNote(((CDImpl) entryRelationship.getObservation()
						    .getEntryRelationships().get(0).getObservation().getValues().get(0))
							    .getDisplayName());
					}catch(Exception exception){
					    System.out.println(
						    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
					}
					}
				    }
				    patientProblemDiagnosisList.add(patientProblemDiagnosis);
				}
			    }
			}
			} else {
			    // code to check if value is in value tag itself
			    PatientProblemDiagnosis patientProblemDiagnosis = new PatientProblemDiagnosis();
			    CD cd = (CD) any;
			    patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation()));
			    patientProblemDiagnosis.setInterpretationCodes(findInterpretationCode(entryRelationship.getObservation()));
			    String diagnosis = null;
			    Code icdCode = null;
			    if (cd != null) {
				diagnosis = cd.getDisplayName();
				icdCode = new Code();
				if (cd.getCodeSystemName() != null && cd.getCodeSystemName() != BLANK) {
				    icdCode.setCodeSystemName(cd.getCodeSystemName());
				}
				if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK)
				    icdCode.setCodeSystem(cd.getCodeSystem());
				if (cd.getCode() != null && cd.getCode() != BLANK)
				    icdCode.setCode(cd.getCode());
			    }
			    if (diagnosis != null && !diagnosis.trim().equals(BLANK)) {
				patientProblemDiagnosis.setDiagnosis(diagnosis);
				patientProblemDiagnosis.setIcdCode(icdCode);
				
				try {
				    if (entryRelationship.getObservation() != null
					    && entryRelationship.getObservation().getEntryRelationships() != null
					    && entryRelationship.getObservation().getEntryRelationships().size() > 0
					    && entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation() != null) {
					if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation())!=null){
					patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation()));
					}
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    		ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
						    if(note.getClass().equals(CDImpl.class)){
						    patientProblemDiagnosis.setNote(((CDImpl) note)
							    .getDisplayName());
						    }
						    if(note.getClass().equals(PQImpl.class)){
							if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
							    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
							}
							    }
					}
				    }
				} catch (Exception exception) {
				    System.out.println(
					    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
				}
				patientProblemDiagnosisList.add(patientProblemDiagnosis);
			    } else {
				cd = (CD)any;
				if (cd != null) {
				    try{
				    diagnosis = cd.getDisplayName();
				    icdCode = new Code();
				    if (cd.getCodeSystemName() != null && cd.getCodeSystemName() != BLANK){
					icdCode.setCodeSystemName(cd.getCodeSystemName());
				    }
				    if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK){
					icdCode.setCodeSystem(cd.getCodeSystem());
				    }
				    if (cd.getCode() != null && cd.getCode() != BLANK){
					icdCode.setCode(cd.getCode());
				    }else{
					if(cd.getTranslations()!=null && cd.getTranslations().size()>0){
					    icdCode.setCodeSystemName(cd.getTranslations().get(0).getCodeSystemName());
					    icdCode.setCodeSystem(cd.getTranslations().get(0).getCodeSystem());
					    icdCode.setCode(cd.getTranslations().get(0).getCode());
					}
				    }
				    }catch(Exception exception){
					   System.out.println(
						    "[EXCEPTION IN PROBLEM ICD PARSING OCCURED]" + exception.getMessage());
				    }
				}
				try {
				    if (entryRelationship.getObservation() != null
					    && entryRelationship.getObservation().getEntryRelationships() != null
					    && entryRelationship.getObservation().getEntryRelationships().size() > 0
					    && entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation() != null) {
					if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation())!=null){
					patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation()));
					}
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
						    if(note.getClass().equals(CDImpl.class)){
						    patientProblemDiagnosis.setNote(((CDImpl)note)
							    .getDisplayName());
						    }
						    if(note.getClass().equals(PQImpl.class)){
							if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
							    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
							}
							    }
					}
				    }
				} catch (Exception exception) {
				    System.out.println(
					    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
				}
			    }
			}
		    }
		    }
		}catch(Exception exception){
		    System.out.println("Exception while parsing problems"+exception.getMessage());
		}
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientProblemDiagnosisList.size() > 0)
	    return patientProblemDiagnosisList.toArray(new PatientProblemDiagnosis[patientProblemDiagnosisList.size()]);
	return null;
    
    }
/*
 * Two methods are created with the same code because this library has problem while casting problemSection to his parent Section class
 */
    @Override
    public PatientProblemDiagnosis[] findProblems(Section section) {

	List<PatientProblemDiagnosis> patientProblemDiagnosisList = new ArrayList<PatientProblemDiagnosis>(0);
	try {
	    for (org.openhealthtools.mdht.uml.cda.Entry entry : section.getEntries()) {
		for (EntryRelationship entryRelationship : entry.getAct().getEntryRelationships()) {
		    try
		    {
		    if (entryRelationship.getObservation()!=null && entryRelationship.getObservation().getValues() != null){
			for(ANY any: entryRelationship.getObservation().getValues()){
			EList<CD> translations=((CD) any).getTranslations();
			if (translations != null && translations.size()>0) {
			    for(CD cd : translations){
			    PatientProblemDiagnosis patientProblemDiagnosis = new PatientProblemDiagnosis();
			    patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation()));
			    patientProblemDiagnosis.setInterpretationCodes(findInterpretationCode(entryRelationship.getObservation()));
			     String diagnosis = null;
			    Code icdCode = null;
			    if (cd != null) {
				diagnosis = cd.getDisplayName();
				icdCode = new Code();
				if (cd.getCode() != null && cd.getCode() != BLANK){
				    icdCode.setCodeSystemName(cd.getCodeSystemName());
				} 
				if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK)
				    icdCode.setCodeSystem(cd.getCodeSystem());
				if (cd.getCode() != null && cd.getCode() != BLANK)
				    icdCode.setCode(cd.getCode());
			    }
			    if (diagnosis != null && !diagnosis.trim().equals(BLANK)) {
				patientProblemDiagnosis.setDiagnosis(diagnosis);
				patientProblemDiagnosis.setIcdCode(icdCode);
				

				if (entryRelationship.getObservation() != null
					&& entryRelationship.getObservation().getEntryRelationships() != null
					&& entryRelationship.getObservation().getEntryRelationships().size() > 0
					&& entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation() != null) {
				    if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
					    .getObservation())!=null){
				    patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
					    .getObservation()));
				    }
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    try{
				    		ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
					    if(note.getClass().equals(CDImpl.class)){
					    patientProblemDiagnosis.setNote(((CDImpl) note)
						    .getDisplayName());
					    }
					    if(note.getClass().equals(PQImpl.class)){
						if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
						    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
						}
						    }
					    
					    patientProblemDiagnosis.setNote(((CDImpl) entryRelationship.getObservation()
						    .getEntryRelationships().get(0).getObservation().getValues().get(0))
							    .getDisplayName());
					}catch(Exception exception){
					    System.out.println(
						    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
					}
					}
				}
				patientProblemDiagnosisList.add(patientProblemDiagnosis);
			    } else {
				cd = (CD) any;
				if (cd != null) {
				    try{
				    diagnosis = cd.getDisplayName();
				    icdCode = new Code();
				    if (cd.getCodeSystemName() != null && cd.getCodeSystemName() != BLANK){
					icdCode.setCodeSystemName(cd.getCodeSystemName());
				    }
				    if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK){
					icdCode.setCodeSystem(cd.getCodeSystem());
				    }
				    if (cd.getCode() != null && cd.getCode() != BLANK){
					icdCode.setCode(cd.getCode());
				    }else{
					if(cd.getTranslations()!=null && cd.getTranslations().size()>0){
					    icdCode.setCodeSystemName(cd.getTranslations().get(0).getCodeSystemName());
					    icdCode.setCodeSystem(cd.getTranslations().get(0).getCodeSystem());
					    icdCode.setCode(cd.getTranslations().get(0).getCode());
					}
				    }
				 }catch(Exception exception){
					   System.out.println(
						    "[EXCEPTION IN PROBLEM ICD PARSING OCCURED]" + exception.getMessage());
				    }
				}
				if (diagnosis != null && !diagnosis.trim().equals(BLANK)) {
				    patientProblemDiagnosis.setDiagnosis(diagnosis);
				    patientProblemDiagnosis.setIcdCode(icdCode);
				    if (entryRelationship.getObservation() != null
					    && entryRelationship.getObservation().getEntryRelationships() != null
					    && entryRelationship.getObservation().getEntryRelationships().size() > 0
					    && entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation() != null) {
					if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation())!=null){
					patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation()));
					}
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    try{
				    		ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
					    if(note.getClass().equals(CDImpl.class)){
					    patientProblemDiagnosis.setNote(((CDImpl) note)
						    .getDisplayName());
					    }
					    if(note.getClass().equals(PQImpl.class)){
						if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
						    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
						}
						    }
					    
					    patientProblemDiagnosis.setNote(((CDImpl) entryRelationship.getObservation()
						    .getEntryRelationships().get(0).getObservation().getValues().get(0))
							    .getDisplayName());
					}catch(Exception exception){
					    System.out.println(
						    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
					}
					}
				    }
				    patientProblemDiagnosisList.add(patientProblemDiagnosis);
				}
			    }
			}
			} else {
			    // code to check if value is in value tag itself
			    PatientProblemDiagnosis patientProblemDiagnosis = new PatientProblemDiagnosis();
			    CD cd = (CD) any;
			    patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation()));
			    patientProblemDiagnosis.setInterpretationCodes(findInterpretationCode(entryRelationship.getObservation()));
			    String diagnosis = null;
			    Code icdCode = null;
			    if (cd != null) {
				diagnosis = cd.getDisplayName();
				icdCode = new Code();
				if (cd.getCodeSystemName() != null && cd.getCodeSystemName() != BLANK) {
				    icdCode.setCodeSystemName(cd.getCodeSystemName());
				}
				if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK)
				    icdCode.setCodeSystem(cd.getCodeSystem());
				if (cd.getCode() != null && cd.getCode() != BLANK)
				    icdCode.setCode(cd.getCode());
			    }
			    if (diagnosis != null && !diagnosis.trim().equals(BLANK)) {
				patientProblemDiagnosis.setDiagnosis(diagnosis);
				patientProblemDiagnosis.setIcdCode(icdCode);
				
				try {
				    if (entryRelationship.getObservation() != null
					    && entryRelationship.getObservation().getEntryRelationships() != null
					    && entryRelationship.getObservation().getEntryRelationships().size() > 0
					    && entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation() != null) {
					if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation())!=null){
					patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation()));
					}
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    		ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
						    if(note.getClass().equals(CDImpl.class)){
						    patientProblemDiagnosis.setNote(((CDImpl) note)
							    .getDisplayName());
						    }
						    if(note.getClass().equals(PQImpl.class)){
							if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
							    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
							}
							    }
					}
				    }
				} catch (Exception exception) {
				    System.out.println(
					    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
				}
				patientProblemDiagnosisList.add(patientProblemDiagnosis);
			    } else {
				cd = (CD)any;
				if (cd != null) {
				    try{
				    diagnosis = cd.getDisplayName();
				    icdCode = new Code();
				    if (cd.getCodeSystemName() != null && cd.getCodeSystemName() != BLANK){
					icdCode.setCodeSystemName(cd.getCodeSystemName());
				    }
				    if (cd.getCodeSystem() != null && cd.getCodeSystem() != BLANK){
					icdCode.setCodeSystem(cd.getCodeSystem());
				    }
				    if (cd.getCode() != null && cd.getCode() != BLANK){
					icdCode.setCode(cd.getCode());
				    }else{
					if(cd.getTranslations()!=null && cd.getTranslations().size()>0){
					    icdCode.setCodeSystemName(cd.getTranslations().get(0).getCodeSystemName());
					    icdCode.setCodeSystem(cd.getTranslations().get(0).getCodeSystem());
					    icdCode.setCode(cd.getTranslations().get(0).getCode());
					}
				    }
				    }catch(Exception exception){
					   System.out.println(
						    "[EXCEPTION IN PROBLEM ICD PARSING OCCURED]" + exception.getMessage());
				    }
				}
				try {
				    if (entryRelationship.getObservation() != null
					    && entryRelationship.getObservation().getEntryRelationships() != null
					    && entryRelationship.getObservation().getEntryRelationships().size() > 0
					    && entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation() != null) {
					if(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation())!=null){
					patientProblemDiagnosis.setDate(CommonUtil.getEffectiveTime(entryRelationship.getObservation().getEntryRelationships().get(0)
						    .getObservation()));
					}
					if (entryRelationship.getObservation().getEntryRelationships().get(0)
						.getObservation().getValues() != null
						&& entryRelationship.getObservation().getEntryRelationships().get(0)
							.getObservation().getValues().size() > 0){
					    ANY note=entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getValues().get(0);
						    if(note.getClass().equals(CDImpl.class)){
						    patientProblemDiagnosis.setNote(((CDImpl)note)
							    .getDisplayName());
						    }
						    if(note.getClass().equals(PQImpl.class)){
							if(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode()!=null){
							    patientProblemDiagnosis.setNote(entryRelationship.getObservation().getEntryRelationships().get(0).getObservation().getCode().getDisplayName()+" : "+((PQImpl) note).getValue());
							}
							    }
					}
				    }
				} catch (Exception exception) {
				    System.out.println(
					    "[EXCEPTION IN PROBLEM NOTE PARSING OCCURED]" + exception.getMessage());
				}
			    }
			}
		    }
		    }
		}catch(Exception exception){
		    System.out.println("Exception while parsing problems"+exception.getMessage());
		}
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientProblemDiagnosisList.size() > 0)
	    return patientProblemDiagnosisList.toArray(new PatientProblemDiagnosis[patientProblemDiagnosisList.size()]);
	return null;
    }
    
    private InterpretationCode[] findInterpretationCode(Observation observation){
	try{
	    if(observation!=null){
		if(observation.getInterpretationCodes()!=null && observation.getInterpretationCodes().size()>0){
		    List<InterpretationCode> interpretationCodes=new ArrayList<InterpretationCode>();
		    for(CE ce:observation.getInterpretationCodes()){
			if(ce!=null){
			    InterpretationCode interpretationCode=new InterpretationCode();
			    Code code=new Code();
			    code.setCode(ce.getCode());
			    code.setCodeSystem(ce.getCodeSystem());
			    code.setCodeSystemName(ce.getCodeSystemName());
			    interpretationCode.setName(ce.getDisplayName());
			    interpretationCode.setCode(code);
			    interpretationCodes.add(interpretationCode);
			}
		    }
		    if (interpretationCodes.size() > 0)
			    return interpretationCodes.toArray(new InterpretationCode[interpretationCodes.size()]);
		}
	    }
	}catch(Exception exception){
	    System.out.println("Exception while parsing problems [Interpretation Code]"+exception.getMessage());
	}
	return null;
    }

}