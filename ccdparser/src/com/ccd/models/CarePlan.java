package com.ccd.models;

import java.util.Arrays;

/**
 * Model Composite class for Care Plan information.
 *
 */
public class CarePlan {
	private String carePlanDate;
	private String visitTypeCarePlanType;
	private String careManager;
	private Patient[] patients;
	private Provider[] providers;
	private Practice[] practices;
	private PatientProblemDiagnosis[] patientProblemDiagnosis;
	private Goal[] goals;
	private PatientMedication[] medications;
	private PatientAllergies[] allergies;
	private InterdisclinaryTeam[] interdisclinaryTeams;
	private Education[] educations;
	private ActionItem[] actionItems;
	private Result[] results;
	private VitalSign[] vitalSigns;
	private SocialHistory socialHistory;
	private PlanOfCare planOfCare;
	public Patient[] getPatients() {
		return patients;
	}

	public void setPatients(Patient[] patients) {
		this.patients = patients;
	}

	public Practice[] getPractices() {

		return practices;
	}

	public VitalSign[] getVitalSigns() {
		return vitalSigns;
	}

	public void setVitalSigns(VitalSign[] vitalSigns) {
		this.vitalSigns = vitalSigns;
	}

	public void setPractices(Practice[] practices) {
		this.practices = practices;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

	public String getCarePlanDate() {
		return carePlanDate;
	}

	public void setCarePlanDate(String carePlanDate) {
		this.carePlanDate = carePlanDate;
	}

	public String getVisitTypeCarePlanType() {
		return visitTypeCarePlanType;
	}

	public void setVisitTypeCarePlanType(String visitTypeCarePlanType) {
		this.visitTypeCarePlanType = visitTypeCarePlanType;
	}

	public String getCareManager() {
		return careManager;
	}

	public void setCareManager(String careManager) {
		this.careManager = careManager;
	}

	public Provider[] getProviders() {
		return providers;
	}

	public void setProviders(Provider[] providers) {
		this.providers = providers;
	}

	public PatientProblemDiagnosis[] getPatientProblemDiagnosis() {
		return patientProblemDiagnosis;
	}

	public void setPatientProblemDiagnosis(
			PatientProblemDiagnosis[] patientProblemDiagnosis) {
		this.patientProblemDiagnosis = patientProblemDiagnosis;
	}

	public Goal[] getGoals() {
		return goals;
	}

	public void setGoals(Goal[] goals) {
		this.goals = goals;
	}

	public PatientMedication[] getMedications() {
		return medications;
	}

	public void setMedications(PatientMedication[] medications) {
		this.medications = medications;
	}

	public PatientAllergies[] getAllergies() {
		return allergies;
	}

	public void setAllergies(PatientAllergies[] allergies) {
		this.allergies = allergies;
	}

	public InterdisclinaryTeam[] getInterdisclinaryTeams() {
		return interdisclinaryTeams;
	}

	public void setInterdisclinaryTeams(InterdisclinaryTeam[] interdisclinaryTeams) {
		this.interdisclinaryTeams = interdisclinaryTeams;
	}

	public Education[] getEducations() {
		return educations;
	}

	public void setEducations(Education[] educations) {
		this.educations = educations;
	}

	public ActionItem[] getActionItems() {
		return actionItems;
	}

	public void setActionItems(ActionItem[] actionItems) {
		this.actionItems = actionItems;
	}

	public SocialHistory getSocialHistory() {
		return socialHistory;
	}

	public void setSocialHistory(SocialHistory socialHistory) {
		this.socialHistory = socialHistory;
	}

	@Override
	public String toString() {
		return "CarePlan [carePlanDate=" + carePlanDate + ", visitTypeCarePlanType=" + visitTypeCarePlanType
				+ ", careManager=" + careManager + ", patients=" + Arrays.toString(patients) + ", providers="
				+ Arrays.toString(providers) + ", practices=" + Arrays.toString(practices)
				+ ", patientProblemDiagnosis=" + Arrays.toString(patientProblemDiagnosis) + ", goals="
				+ Arrays.toString(goals) + ", medications=" + Arrays.toString(medications) + ", allergies="
				+ Arrays.toString(allergies) + ", interdisclinaryTeams=" + Arrays.toString(interdisclinaryTeams)
				+ ", educations=" + Arrays.toString(educations) + ", actionItems=" + Arrays.toString(actionItems)
				+ ", results=" + Arrays.toString(results) + ", vitalSigns=" + Arrays.toString(vitalSigns)
				+ ", socialHistory=" + socialHistory + "]";
	}

	/**
	 * @return the planOfCare
	 */
	public PlanOfCare getPlanOfCare() {
	    return planOfCare;
	}

	/**
	 * @param planOfCare the planOfCare to set
	 */
	public void setPlanOfCare(PlanOfCare planOfCare) {
	    this.planOfCare = planOfCare;
	}

	
		
}
