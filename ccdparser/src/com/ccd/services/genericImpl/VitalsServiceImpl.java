package com.ccd.services.genericImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openhealthtools.mdht.uml.cda.Component4;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.impl.PQImpl;

import com.ccd.models.VitalSign;
import com.ccd.services.VitalsService;
import com.ccd.util.CommonUtil;

public class VitalsServiceImpl implements VitalsService {
    // Using SNOMED-CT Codes
    private static final String[] HEIGHT = { "3138-5", "50373000", "8302-2" };
    private static final String[] TEMPRATURE = { "35095-9","8310-5" };
    private static final String[] SYSTOLIC_BP = { "8480-6", "271649006" };
    private static final String[] DIASTOLIC_BP = { "8462-4", "271650006" };
    private static final String[] WEIGHT = { "3141-9","27113001" };
    private static final String[] BSA = { "3140-1" };
    private static final String[] BMI = { "59574-4","39156-5" };
    private static final String[] PULSE = { "8893-0","8867-4" };

    private static final String INCH_US = "in_us";
    private static final String LB_AV = "lb_av";
    private static final String LB = "lb";
    private static final String CM = "cm";
    private static final String KG = "kg";
    private static final String oz_av = "[oz_av]";
    private static final String in_i = "[in_i]";
    private static final String[] celsius = {"celsius","C"};

    public VitalSign[] findVitalSigns(Section section) {
	if (section == null || section.getText() == null)
	    return null;
	List<VitalSign> vitalSigns = new ArrayList<VitalSign>();
	try {
	    for (Entry entry : section.getEntries()) {
		VitalSign vitalSign = null;
		for (Component4 entry2 : entry.getOrganizer().getComponents()) {
		    if (entry2.getObservation() != null && entry2.getObservation().getCode() != null) {
			if (entry2.getObservation().getValues() != null
				&& entry2.getObservation().getValues().size() > 0) {
				//vitalSign = new VitalSign();
				String date=CommonUtil
					.parseEffectiveTimeyyyMMddAsString(CommonUtil.parseEffectiveTimeyyyMMdd(
						entry2.getObservation().getEffectiveTime().getValue()));
				vitalSign=findVitalByDate(date, vitalSigns);
				if(vitalSign==null){
				    if(date!=null){
				    vitalSign=new VitalSign();
				    vitalSign.setDate(date);
				    vitalSigns.add(vitalSign);
				    }else{
					if(vitalSigns.size()>0){
					    vitalSign=vitalSigns.get(0);
					}else{
					    vitalSign=new VitalSign();
					    vitalSigns.add(vitalSign);
					}
				    }
				}
			    try {
				if (entry2.getObservation().getValues() != null
					&& entry2.getObservation().getValues().size() > 0) {
				    if (((PQImpl) entry2.getObservation().getValues().get(0)) != null
					    && ((PQImpl) entry2.getObservation().getValues().get(0))
						    .getValue() != null) {
					try {
					    vitalSign = setRuntimeValue(vitalSign,
						    entry2.getObservation().getCode().getCode(),
						    ((PQImpl) entry2.getObservation().getValues().get(0)).getUnit(),
						    ((PQImpl) entry2.getObservation().getValues().get(0)).getValue()
							    .doubleValue());
					} catch (Exception exception) {
					    System.out.println("Parsing vital value failed");
					}
				    }
				}
			    } catch (Exception exception) {
				System.out.println("Can not set vital signs value, reason:" + exception.getMessage());
			    }
			}
		    }
		    
		    
		if (vitalSign != null) {
		    try {
			// BSA Calculation
			if (vitalSign.getWtLb() != null) {
			    double weightforBSA = vitalSign.getWtLb() / 2.2;
			    double heightforBSA = Double.valueOf("" + vitalSign.getHtFt() + "." + vitalSign.getHtIn())
				    * 30.48;
			    double bsa = Math.sqrt(((heightforBSA * weightforBSA) / 3600));
			    DecimalFormat df = new DecimalFormat("###.#");
			    bsa = Double.valueOf(df.format(bsa));
			    vitalSign.setBsa(bsa);
			}

		    } catch (Exception ex) {
			ex.printStackTrace();
		    }
		}
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (vitalSigns.size() > 0)
	    return vitalSigns.toArray(new VitalSign[vitalSigns.size()]);
	return null;
    }

    private VitalSign setRuntimeValue(VitalSign vitalSign, String code, String unit, Double value) {
	try {
	    if (value != null && code != null) {
		if (Arrays.asList(HEIGHT).contains(code)) {
		    try {
			double feet = 0;
			if (unit.toLowerCase().equals(INCH_US)||unit.toLowerCase().equals("["+INCH_US+"]")) {
			    feet = value / 12;
			} else if (unit.toLowerCase().equals(CM)||unit.toLowerCase().equals("["+CM+"]")) {
			    feet = value / 30;
			} else if (unit.toLowerCase().equals(in_i)|| unit.toLowerCase().equals("["+in_i+"]")) {
			    feet = value / 12;
			} else {
			    feet = value/12;
			}
			double inch=(feet-((long)feet));
			vitalSign.setHtFt((long)feet);
			vitalSign.setHtIn(Math.round((inch*12)));
		    } catch (Exception ex) {
			ex.printStackTrace();
		    }
		} else if (Arrays.asList(WEIGHT).contains(code)) {
		    if (value != null) {
			if (KG.equals(unit.toLowerCase())) {
			    vitalSign.setWtLb(value * 2.2);
			} else if (oz_av.equals(unit.trim().toLowerCase()) || unit.trim().toLowerCase().equals("["+oz_av+"]")){
			    try {
				vitalSign.setWtLb(value/16);
			    } catch (Exception exception) {
				vitalSign.setWtLb(value);
			    }

			} else {
			    vitalSign.setWtLb(value);
			}
		    }
		} else if (Arrays.asList(TEMPRATURE).contains(code)) {
		    if(Arrays.asList(celsius).contains(unit.toLowerCase())){
			value=(value*1.8000)+32;
		    }
		    vitalSign.setTemp(value);
		} else if (Arrays.asList(SYSTOLIC_BP).contains(code)) {
		    if(vitalSign.getSystolic()==null || vitalSign.getSystolic().equals("")){
			vitalSign.setSystolic(String.valueOf(value.intValue()));
		    }else{
			vitalSign.setSystolic(vitalSign.getSystolic()+","+String.valueOf(value.intValue()));
		    }
		} else if (Arrays.asList(DIASTOLIC_BP).contains(code)) {
		    if(vitalSign.getDiastolic()!=null && !vitalSign.getDiastolic().equals("")){
			vitalSign.setDiastolic(vitalSign.getDiastolic() + "," + value.intValue());
		    }else{
			vitalSign.setDiastolic(String.valueOf(value.intValue()));
		    }
		} else if (Arrays.asList(BSA).contains(code)) {
		    vitalSign.setBsa(value);
		} else if (Arrays.asList(BMI).contains(code)) {
		    vitalSign.setBmi(value);
		} else if (Arrays.asList(PULSE).contains(code)) {
		    vitalSign.setPulse(value.intValue());
		}
	    }
	} catch (Exception exception) {
	    System.out.println("Can Not Set Value");

	}
	return vitalSign;
    }
    private VitalSign findVitalByDate(String date,List<VitalSign> vitalSigns){
	if(date!=null){
	for(VitalSign vital: vitalSigns){
	    if(vital.getDate().equals(date)){
		return vital;
	    }
	}
	}
	return null;
    }
}
