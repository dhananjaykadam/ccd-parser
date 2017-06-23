package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.ManufacturedProduct;
import org.openhealthtools.mdht.uml.cda.Material;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.SubstanceAdministration;
import org.openhealthtools.mdht.uml.cda.ccd.MedicationSection;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;

import com.ccd.models.Code;
import com.ccd.models.PatientMedication;
import com.ccd.services.MedicationsService;
import com.ccd.util.CommonUtil;

public class MedicationsServiceImpl implements MedicationsService {

    /**
     * Method to find Patient Medications and build Java class object for per
     * Medication information
     * 
     * @param featureMap
     * @return
     */
    @Override
    public PatientMedication[] findPatientMedication(MedicationSection section) {
	List<PatientMedication> patientMedications = new ArrayList<PatientMedication>();
	try {
	    for (Entry entry : section.getEntries()) {
		SubstanceAdministration substanceAdministration = entry.getSubstanceAdministration();
		if (substanceAdministration != null && substanceAdministration.getConsumable() != null) {
		    ManufacturedProduct manufacturedProduct = substanceAdministration.getConsumable()
			    .getManufacturedProduct();
		    if (manufacturedProduct != null) {
			Material material = manufacturedProduct.getManufacturedMaterial();
			if (material != null && material.getCode() != null) {
			    PatientMedication patientMedication = new PatientMedication();

			    if (material.getCode().getDisplayName() != null
				    && !material.getCode().getDisplayName().trim().equals("")) {
				patientMedication.setMedicationName(material.getCode().getDisplayName());

				if (substanceAdministration.getDoseQuantity() != null
					&& substanceAdministration.getDoseQuantity().getValue() != null
					&& substanceAdministration.getDoseQuantity().getValue().floatValue() > 0) {
				    patientMedication.setDirection(
					    "Dose Quantity: " + substanceAdministration.getDoseQuantity().getValue());
				}

				if (patientMedication.getMedicationCode() == null) {
				    Code medicationCode = new Code();
				    medicationCode.setCode(material.getCode().getCode());
				    medicationCode.setCodeSystem(material.getCode().getCodeSystem());
				    medicationCode.setCodeSystemName(material.getCode().getCodeSystemName());
				    patientMedication.setMedicationCode(medicationCode);
				}
				if (substanceAdministration.getEffectiveTimes() != null
					&& substanceAdministration.getEffectiveTimes().size() > 0
					&& substanceAdministration.getEffectiveTimes().get(0) != null) {
				    if (substanceAdministration.getEffectiveTimes().get(0) instanceof IVL_TS) {
					IVL_TS ts = (IVL_TS) substanceAdministration.getEffectiveTimes().get(0);
					if (ts != null && ts.getLow() != null && ts.getLow().getValue() != null)
					    patientMedication.setStartDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
						    CommonUtil.parseEffectiveTimeyyyMMdd(ts.getLow().getValue())));
					if (ts != null && ts.getHigh() != null && ts.getHigh().getValue() != null)
					    patientMedication.setStoppedDate(
						    CommonUtil.parseEffectiveTimeyyyMMddAsString(CommonUtil
							    .parseEffectiveTimeyyyMMdd(ts.getHigh().getValue())));
				    }
				    patientMedications.add(patientMedication);
				}
			    } else {
				for (CD cd : material.getCode().getTranslations()) {
				    if (cd.getDisplayName() != null && !cd.getDisplayName().trim().equals("")) {
					patientMedication.setMedicationName(cd.getDisplayName());
					Code code = new Code();
					code.setCode(cd.getCode());
					code.setCodeSystem(cd.getCodeSystem());
					code.setCodeSystemName(cd.getCodeSystemName());
					patientMedication.setMedicationCode(code);

					if (substanceAdministration.getDoseQuantity() != null
						&& substanceAdministration.getDoseQuantity().getValue() != null
						&& substanceAdministration.getDoseQuantity().getValue()
							.floatValue() > 0) {
					    patientMedication.setDirection("Dose Quantity: "
						    + substanceAdministration.getDoseQuantity().getValue());
					}
					if (substanceAdministration.getEffectiveTimes() != null
						&& substanceAdministration.getEffectiveTimes().size() > 0
						&& substanceAdministration.getEffectiveTimes().get(0) != null) {
					    if (substanceAdministration.getEffectiveTimes().get(0) instanceof IVL_TS) {
						IVL_TS ts = (IVL_TS) substanceAdministration.getEffectiveTimes().get(0);
						if (ts != null && ts.getLow() != null && ts.getLow().getValue() != null)
						    patientMedication
							    .setStartDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
								    CommonUtil.parseEffectiveTimeyyyMMdd(
									    ts.getLow().getValue())));
						if (ts != null && ts.getHigh() != null
							&& ts.getHigh().getValue() != null)
						    patientMedication.setStoppedDate(
							    CommonUtil.parseEffectiveTimeyyyMMddAsString(
								    CommonUtil.parseEffectiveTimeyyyMMdd(
									    ts.getHigh().getValue())));
					    }
					}
					patientMedications.add(patientMedication);
				    }
				}

			    }

			}
		    }
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientMedications.size() > 0)
	    return patientMedications.toArray(new PatientMedication[patientMedications.size()]);
	return null;
    }

    /**
     * Method to find Patient Medications and build Java class object for per
     * Medication information
     * 
     * @param featureMap
     * @return
     */

    @Override
    public PatientMedication[] findPatientMedication(Section section) {
	List<PatientMedication> patientMedications = new ArrayList<PatientMedication>();
	try {
	    for (Entry entry : section.getEntries()) {
		SubstanceAdministration substanceAdministration = entry.getSubstanceAdministration();
		if (substanceAdministration != null && substanceAdministration.getConsumable() != null) {
		    ManufacturedProduct manufacturedProduct = substanceAdministration.getConsumable()
			    .getManufacturedProduct();
		    if (manufacturedProduct != null) {
			Material material = manufacturedProduct.getManufacturedMaterial();
			if (material != null && material.getCode() != null) {
			    PatientMedication patientMedication = new PatientMedication();

			    if (material.getCode().getDisplayName() != null
				    && !material.getCode().getDisplayName().trim().equals("")) {
				patientMedication.setMedicationName(material.getCode().getDisplayName());

				if (substanceAdministration.getDoseQuantity() != null
					&& substanceAdministration.getDoseQuantity().getValue() != null
					&& substanceAdministration.getDoseQuantity().getValue().floatValue() > 0) {
				    patientMedication.setDirection(
					    "Dose Quantity: " + substanceAdministration.getDoseQuantity().getValue());
				}

				if (patientMedication.getMedicationCode() == null) {
				    Code medicationCode = new Code();
				    medicationCode.setCode(material.getCode().getCode());
				    medicationCode.setCodeSystem(material.getCode().getCodeSystem());
				    medicationCode.setCodeSystemName(material.getCode().getCodeSystemName());
				    patientMedication.setMedicationCode(medicationCode);
				}
				if (substanceAdministration.getEffectiveTimes() != null
					&& substanceAdministration.getEffectiveTimes().size() > 0
					&& substanceAdministration.getEffectiveTimes().get(0) != null) {
				    if (substanceAdministration.getEffectiveTimes().get(0) instanceof IVL_TS) {
					IVL_TS ts = (IVL_TS) substanceAdministration.getEffectiveTimes().get(0);
					if (ts != null && ts.getLow() != null && ts.getLow().getValue() != null)
					    patientMedication.setStartDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
						    CommonUtil.parseEffectiveTimeyyyMMdd(ts.getLow().getValue())));
					if (ts != null && ts.getHigh() != null && ts.getHigh().getValue() != null)
					    patientMedication.setStoppedDate(
						    CommonUtil.parseEffectiveTimeyyyMMddAsString(CommonUtil
							    .parseEffectiveTimeyyyMMdd(ts.getHigh().getValue())));
				    }
				    patientMedications.add(patientMedication);
				}
			    } else {
				for (CD cd : material.getCode().getTranslations()) {
				    if (cd.getDisplayName() != null && !cd.getDisplayName().trim().equals("")) {
					patientMedication.setMedicationName(cd.getDisplayName());
					Code code = new Code();
					code.setCode(cd.getCode());
					code.setCodeSystem(cd.getCodeSystem());
					code.setCodeSystemName(cd.getCodeSystemName());
					patientMedication.setMedicationCode(code);

					if (substanceAdministration.getDoseQuantity() != null
						&& substanceAdministration.getDoseQuantity().getValue() != null
						&& substanceAdministration.getDoseQuantity().getValue()
							.floatValue() > 0) {
					    patientMedication.setDirection("Dose Quantity: "
						    + substanceAdministration.getDoseQuantity().getValue());
					}
					if (substanceAdministration.getEffectiveTimes() != null
						&& substanceAdministration.getEffectiveTimes().size() > 0
						&& substanceAdministration.getEffectiveTimes().get(0) != null) {
					    if (substanceAdministration.getEffectiveTimes().get(0) instanceof IVL_TS) {
						IVL_TS ts = (IVL_TS) substanceAdministration.getEffectiveTimes().get(0);
						if (ts != null && ts.getLow() != null && ts.getLow().getValue() != null)
						    patientMedication
							    .setStartDate(CommonUtil.parseEffectiveTimeyyyMMddAsString(
								    CommonUtil.parseEffectiveTimeyyyMMdd(
									    ts.getLow().getValue())));
						if (ts != null && ts.getHigh() != null
							&& ts.getHigh().getValue() != null)
						    patientMedication.setStoppedDate(
							    CommonUtil.parseEffectiveTimeyyyMMddAsString(
								    CommonUtil.parseEffectiveTimeyyyMMdd(
									    ts.getHigh().getValue())));
					    }
					}
					patientMedications.add(patientMedication);
				    }
				}

			    }

			}
		    }
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (patientMedications.size() > 0)
	    return patientMedications.toArray(new PatientMedication[patientMedications.size()]);
	return null;
    }
}
