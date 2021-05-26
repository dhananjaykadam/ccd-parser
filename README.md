# CCDA Parser #

* Created this library specifially to parse the ccd/ccda documents most commom patient details  like demographics, medication, allergies, vitals, goals etc.

## Following details will be extracted from the CCDA document ##
* Patient
* Provider
* Practice
* PatientProblemDiagnosis
* Goals
* PatientMedications
* PatientAllergies
* InterdisclinaryTeams
* Educations
* ActionItems
* Results
* VitalSigns
* SocialHistory
* PlanOfCare

`CarePlan`  is the object that has every details, and careplan is returned by the parser, though, you can use specific service to get specific details as needed.

## Usage ##
```
CCDParser parser = new CCDParser();
CarePlan carePlan = parser.parse("path")

PatientAllergies[] allergies = carePlan.getAllergies();
SocialHistory socialHistory = carePlan.getSocialHistory();
// and all the above mentioned details

```

## Feel free to create a PR or issues for the improvements
