package com.ccd.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ccd.AlertsSection;
import org.openhealthtools.mdht.uml.cda.ccd.CCDPackage;
import org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;

import com.ccd.models.CarePlan;
import com.ccd.models.PlanOfCare;
import com.ccd.models.Practice;
import com.ccd.models.Provider;
import com.ccd.services.AllergyService;
import com.ccd.services.GoalsService;
import com.ccd.services.MedicationsService;
import com.ccd.services.PatientsService;
import com.ccd.services.PlanOfCareService;
import com.ccd.services.PracticeService;
import com.ccd.services.ProblemsService;
import com.ccd.services.ProvidersService;
import com.ccd.services.ResultService;
import com.ccd.services.SocialHistoryService;
import com.ccd.services.VitalsService;
import com.ccd.services.genericImpl.AllergyServiceImpl;
import com.ccd.services.genericImpl.GoalsServiceImpl;
import com.ccd.services.genericImpl.MedicationsServiceImpl;
import com.ccd.services.genericImpl.PatientsServiceImpl;
import com.ccd.services.genericImpl.PlanOfCareServiceImpl;
import com.ccd.services.genericImpl.PracticeServiceImpl;
import com.ccd.services.genericImpl.ProblemsServiceImpl;
import com.ccd.services.genericImpl.ProvidersServiceImpl;
import com.ccd.services.genericImpl.ResultServiceImpl;
import com.ccd.services.genericImpl.SocialHistoryServiceImpl;
import com.ccd.services.genericImpl.VitalsServiceImpl;
import com.ccd.util.CCDConstants;
import com.ccd.util.CommonUtil;

public class CCDParser {

    static CarePlan carePlan;
    private AllergyService allergyService;
    private MedicationsService medicationsService;
    private PatientsService patientsService;
    private PracticeService practiceService;
    private ProblemsService problemsService;
    private ProvidersService providersService;
    private ResultService resultService;
    private VitalsService vitalsService;
    private SocialHistoryService socialHistoryService;
    private GoalsService goalsService;
    private PlanOfCareService planOfCareService;

    private void initilizeGenericServices() {
	allergyService = new AllergyServiceImpl();
	medicationsService = new MedicationsServiceImpl();
	patientsService = new PatientsServiceImpl();
	practiceService = new PracticeServiceImpl();
	problemsService = new ProblemsServiceImpl();
	providersService = new ProvidersServiceImpl();
	resultService = new ResultServiceImpl();
	vitalsService = new VitalsServiceImpl();
	socialHistoryService = new SocialHistoryServiceImpl();
	goalsService = new GoalsServiceImpl();
	planOfCareService = new PlanOfCareServiceImpl();

    }

    public CarePlan getCCDInstanceAndParse(InputStream inputStream) {
	try {
	    ClinicalDocument clinicalDocument = CDAUtil.loadAs(inputStream,
		    CCDPackage.eINSTANCE.getContinuityOfCareDocument());
	    if (clinicalDocument instanceof org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument) {
		org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument continuityOfCareDocument = (org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument) clinicalDocument;
		return parse(continuityOfCareDocument);
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	return null;
    }

    /**
     * Generic method takes file path as argument and return parsed java object
     * 
     * @param fileName
     *            As {@link String}
     * @return {@link CarePlan} object
     */
    public CarePlan parse(String fileName) {
	FileInputStream fileInputStream;
	try {
	    File file = new File(fileName);
	    fileInputStream = new FileInputStream(file);
	    initilizeGenericServices();
	    return getCCDInstanceAndParse(fileInputStream);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * Method takes CCD doc text as argument and return parsed java object
     * 
     * @param content
     *            As String
     * @return {@link CarePlan} object
     */
    public CarePlan parseStringAsCCD(String content) {
	try {
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
	    initilizeGenericServices();
	    return getCCDInstanceAndParse(byteArrayInputStream);
	} catch (Exception exception) {
	    System.out.println("String contents can not be empty");
	}
	return null;
    }

     /**
     * Method takes CCD doc text and encoding as argument and return parsed java object
     * 
     * @param content
     *            As String
     * @return {@link CarePlan} object
     */
    public CarePlan parseStringAsCCD(String content,String encoding) {
	try {
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes(encoding));
	    initilizeGenericServices();
	    return getCCDInstanceAndParse(byteArrayInputStream);
	} catch (Exception exception) {
	    System.out.println("String contents can not be empty");
	}
	return null;
    }
    
    
    /**
     * parse given {@link ContinuityOfCareDocument} and returns {@link CarePlan}
     * 
     * @param file
     * @return {@link CarePlan}
     */
    private CarePlan parse(org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument continuityOfCareDocument) {
	carePlan = new CarePlan();
	try {
	    carePlan.setPatients(patientsService.getPatients(continuityOfCareDocument));

	    if (continuityOfCareDocument.getEffectiveTime() != null) {
		if (continuityOfCareDocument.getEffectiveTime().getValue().length() >= 8) {
		    carePlan.setCarePlanDate(
			    CommonUtil.parseEffectiveTimeyyyMMddAsString(CommonUtil.parseEffectiveTimeyyyMMdd(
				    (continuityOfCareDocument.getEffectiveTime().getValue().substring(0, 8)))));
		}
	    }

	    if (continuityOfCareDocument.getRecordTargets() != null) {
		Practice[] practices = practiceService.findPractice(continuityOfCareDocument.getRecordTargets());
		if (practices == null || practices.length < 1) {
		    if (continuityOfCareDocument.getAuthors() != null) {
			practices = practiceService
				.findPracticesFromAuthorSection(continuityOfCareDocument.getAuthors());
		    }
		}
		if (practices == null || practices.length < 1) {
		    if (continuityOfCareDocument.getDocumentationOfs() != null) {
			practices = practiceService.findPractices(continuityOfCareDocument.getDocumentationOfs());
		    }
		}
		carePlan.setPractices(practices);
	    }
	    if (continuityOfCareDocument.getDocumentationOfs() != null) {
		Provider[] providers = providersService.findProviders(continuityOfCareDocument.getDocumentationOfs());
		carePlan.setProviders(providers);
	    }

	    try {
		Section section = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
			    CCDConstants.CCD_PROBLEM_SECTION_ID, false);
		if (section != null) {
		    carePlan.setPatientProblemDiagnosis(problemsService.findProblems(section));
		} else {
		    org.openhealthtools.mdht.uml.cda.ccd.ProblemSection problemSection = continuityOfCareDocument
				.getProblemSection();
		    if (problemSection != null) {
			carePlan.setPatientProblemDiagnosis(problemsService.findProblems(problemSection));
		    }
		}
	    } catch (Exception exception) {
		System.out.println(exception.getMessage());
	    }
	    try {
		org.openhealthtools.mdht.uml.cda.ccd.MedicationsSection medicationsSection = continuityOfCareDocument
			.getMedicationsSection();
		if (medicationsSection != null) {
		    carePlan.setMedications(medicationsService.findPatientMedication(medicationsSection));
		} else {
		    Section section = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
			    CCDConstants.CCD_MEDICATION_SECTION_ID, false);
		    if (section != null) {
			carePlan.setMedications(medicationsService.findPatientMedication(section));
		    }
		}
	    } catch (Exception exception) {
		System.out.println(exception.getMessage());
	    }
	    try {
		AlertsSection alertsSection = continuityOfCareDocument.getAlertsSection();
		if (alertsSection != null) {
		    carePlan.setAllergies(allergyService.findPatientAllergiesAlerts(alertsSection));
		} else {
		    Section section = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
			    CCDConstants.CCD_ALLERGIES_SECTION_ID, false);
		    if (section != null) {
			carePlan.setAllergies(allergyService.findPatientAllergiesAlerts(section));
		    }
		}
	    } catch (Exception exception) {
		System.out.println(exception.getMessage());
	    }

	    try {
		Section vitalSignSection = continuityOfCareDocument.getVitalSignsSection();
		if (vitalSignSection == null) {
		    vitalSignSection = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
			    CCDConstants.CCD_VITAL_SIGN_SECTION_ID, false);
		    if (vitalSignSection != null && vitalSignSection.getText() != null) {
			carePlan.setVitalSigns(vitalsService.findVitalSigns(vitalSignSection));
		    }
		} else {
		    carePlan.setVitalSigns(vitalsService.findVitalSigns(vitalSignSection));
		}
	    } catch (Exception exception) {
		System.out.println(exception);
	    }
	    try {
		Section socialHistorySection = continuityOfCareDocument.getSocialHistorySection();
		if (socialHistorySection == null) {
		    socialHistorySection = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
			    CCDConstants.CCD_SOCIAL_HISTORY_SECTION, false);
		}
		if (socialHistorySection != null) {
		    getCarePlan().setSocialHistory(getSocialHistoryService().getSocialHistory(socialHistorySection));
		}
	    } catch (Exception exception) {
		System.out.println(exception.getMessage());
	    }
	    try {
		Section planOfCareSection = continuityOfCareDocument.getPlanOfCareSection();
		if (planOfCareSection == null) {
		    planOfCareSection = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
			    CCDConstants.CCD_PLAN_OF_CARE_SECTION, false);
		    if (planOfCareSection != null) {
			PlanOfCare planOfCare = planOfCareService.parsePlanOfCareSection(planOfCareSection);
			carePlan.setPlanOfCare(planOfCare);
			if (planOfCare != null) {
			    carePlan.setGoals(planOfCare.getGoals());
			}
		    }
		}
	    } catch (Exception exception) {
		System.out.println(exception.getMessage());
	    }

	    // try{
	    // carePlan.setGoals(getGoalsService().parseGoals(continuityOfCareDocument));
	    // }catch(Exception exception){
	    // System.out.println(exception.getMessage());
	    // }
	    // System.out.println("");
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	return carePlan;
    }

    /**
     * @return the carePlan
     */
    public static CarePlan getCarePlan() {
	return carePlan;
    }

    /**
     * @param carePlan
     *            the carePlan to set
     */
    public static void setCarePlan(CarePlan carePlan) {
	CCDParser.carePlan = carePlan;
    }

    /**
     * @return the allergyService
     */
    public AllergyService getAllergyService() {
	return allergyService;
    }

    /**
     * @param allergyService
     *            the allergyService to set
     */
    public void setAllergyService(AllergyService allergyService) {
	this.allergyService = allergyService;
    }

    /**
     * @return the medicationsService
     */
    public MedicationsService getMedicationsService() {
	return medicationsService;
    }

    /**
     * @param medicationsService
     *            the medicationsService to set
     */
    public void setMedicationsService(MedicationsService medicationsService) {
	this.medicationsService = medicationsService;
    }

    /**
     * @return the patientsService
     */
    public PatientsService getPatientsService() {
	return patientsService;
    }

    /**
     * @param patientsService
     *            the patientsService to set
     */
    public void setPatientsService(PatientsService patientsService) {
	this.patientsService = patientsService;
    }

    /**
     * @return the practiceService
     */
    public PracticeService getPracticeService() {
	return practiceService;
    }

    /**
     * @param practiceService
     *            the practiceService to set
     */
    public void setPracticeService(PracticeService practiceService) {
	this.practiceService = practiceService;
    }

    /**
     * @return the problemsService
     */
    public ProblemsService getProblemsService() {
	return problemsService;
    }

    /**
     * @param problemsService
     *            the problemsService to set
     */
    public void setProblemsService(ProblemsService problemsService) {
	this.problemsService = problemsService;
    }

    /**
     * @return the providersService
     */
    public ProvidersService getProvidersService() {
	return providersService;
    }

    /**
     * @param providersService
     *            the providersService to set
     */
    public void setProvidersService(ProvidersService providersService) {
	this.providersService = providersService;
    }

    /**
     * @return the resultService
     */
    public ResultService getResultService() {
	return resultService;
    }

    /**
     * @param resultService
     *            the resultService to set
     */
    public void setResultService(ResultService resultService) {
	this.resultService = resultService;
    }

    /**
     * @return the vitalsService
     */
    public VitalsService getVitalsService() {
	return vitalsService;
    }

    /**
     * @param vitalsService
     *            the vitalsService to set
     */
    public void setVitalsService(VitalsService vitalsService) {
	this.vitalsService = vitalsService;
    }

    /**
     * @return the socialHistoryService
     */
    public SocialHistoryService getSocialHistoryService() {
	return socialHistoryService;
    }

    /**
     * @param socialHistoryService
     *            the socialHistoryService to set
     */
    public void setSocialHistoryService(SocialHistoryService socialHistoryService) {
	this.socialHistoryService = socialHistoryService;
    }

    /**
     * @return the goalsService
     */
    public GoalsService getGoalsService() {
	return goalsService;
    }

    /**
     * @param goalsService
     *            the goalsService to set
     */
    public void setGoalsService(GoalsService goalsService) {
	this.goalsService = goalsService;
    }

}
