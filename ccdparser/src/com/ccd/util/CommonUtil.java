package com.ccd.util;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.ADXP;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;

import com.ccd.models.Address;

public class CommonUtil {
    private static final int yyyyMMdd_FORMAT_LENGTH = 8;

    /**
     * For straight Search of Section from 0th element of array to nth element
     * priority for 0th element from given sectionIds Array
     * 
     * @param sections
     *            {@link EList} of {@link Section}
     * @param sectionIds
     *            {@link Array} of {@link String}
     * @return
     */
    public static Section findSectionById(EList<Section> sections, String[] sectionIds,
	    Boolean AllowEmptyEntriesInSection) {
	try {
	    Map<String, Section> templateIds = new HashMap<String, Section>();
	    for (Section section : sections) {
		for (II id : section.getTemplateIds()) {
		    if (id.getRoot() != null && Arrays.asList(sectionIds).contains(id.getRoot())) {
			// consider for map if section is not just empty
			if ((section.getEntries() != null && section.getEntries().size() > 0)
				|| AllowEmptyEntriesInSection) {
			    templateIds.put(id.getRoot(), section);
			}
		    }
		}
	    }
	    for (String sectionId : sectionIds) {
		if (templateIds.containsKey(sectionId)) {
		    return templateIds.get(sectionId);
		}
	    }
	} catch (Exception exception) {
	    System.out.println("[EXCEPTION OCCURED WHILE SEARCHING FOR SECTION] for " + sectionIds);
	}
	return null;
    }

    public static String getText(FeatureMap featureMap) {
	try {
	    if (featureMap.size() > 0) {
		return ((org.eclipse.emf.ecore.util.FeatureMap.Entry) featureMap.get(0)).getValue().toString().trim();
	    }
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage() + " occured into CommonUtil::getText method");
	}
	return "";
    }

    public static FeatureMap getInternalFeatureMapAt(FeatureMap featureMap, int index) {
	try {
	    if (featureMap.size() > 0) {
		org.eclipse.emf.ecore.util.FeatureMap.Entry entry = (org.eclipse.emf.ecore.util.FeatureMap.Entry) featureMap
			.get(index);
		if (entry != null) {
		    AnyType anyType = (AnyType) ((org.eclipse.emf.ecore.util.FeatureMap.Entry) entry).getValue();
		    if (anyType != null)
			return anyType.getMixed();
		}
	    }
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage()
		    + " occured into CommonUtil::getInternalFeatureMapAt method");
	}
	return null;
    }

    public static org.eclipse.emf.ecore.util.FeatureMap.Entry getEntryFromFeatureMapAt(FeatureMap featureMap,
	    int index) {
	try {
	    if (featureMap.size() > 0)
		return (org.eclipse.emf.ecore.util.FeatureMap.Entry) featureMap.get(index);
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage()
		    + " occured into CommonUtil::getEntryFromFeatureMapAt method");
	}
	return null;
    }

    public static AnyType getAnyTypeFromEntry(Entry entry) {
	try {
	    if (((org.eclipse.emf.ecore.util.FeatureMap.Entry) entry).getValue() instanceof String != true)
		return (AnyType) ((org.eclipse.emf.ecore.util.FeatureMap.Entry) entry).getValue();
	} catch (Exception exception) {
	    System.out.println(
		    "Exception: " + exception.getMessage() + " occured into CommonUtil::getAnyTypeFromEntry method");
	}
	return null;
    }

    public static Date parseDate(String string) {
	try {
	    if (string != null && !string.equals("")) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(string);
	    }
	    return null;
	} catch (Exception exception) {
	}
	return null;
    }

    public static Date parseEffectiveTimeyyyMMdd(String timeStamp) {
	try {
	    if (timeStamp.length() == yyyyMMdd_FORMAT_LENGTH) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.parse(timeStamp);
	    } else if (timeStamp.length() > 8) {
		return parseEffectiveTimeyyyMMdd(timeStamp.substring(0, 8));
	    }
	    return null;
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage()
		    + " occured into CommonUtil::parseEffectiveTimeyyyMMdd method");
	}
	return null;
    }

    public static Address buildAddress(AD ad) {
	try {
	    Address address = new Address();
	    if (ad.getCities() != null && ad.getCities().size() > 0) {
		if (ad.getCities().get(0) != null) {
		    address.setCity(ad.getCities().get(0).getText());
		}
		if (ad.getStates() != null && ad.getStates().get(0) != null) {
		    address.setState(ad.getStates().get(0).getText());
		}
		if (ad.getCounties() != null && ad.getCounties().size() > 0 && ad.getCounties().get(0) != null) {
		    address.setCountry(ad.getCounties().get(0).getText());
		}
		if (ad.getPostalCodes() != null && ad.getPostalCodes().size() > 0
			&& ad.getPostalCodes().get(0) != null) {
		    address.setPostalCode(ad.getPostalCodes().get(0).getText());
		}
		if (ad.getStreetAddressLines() != null && ad.getStreetAddressLines().size() > 0) {
		    List<String> addLines = new ArrayList<String>();
		    for (ADXP adxp : ad.getStreetAddressLines()) {
			addLines.add(adxp.getText());
		    }
		    address.setAddressLines(addLines);
		}
		return address;
	    } else {
		// represent one line address
		if (ad != null) {
		    List<String> addLines = new ArrayList<String>();
		    addLines.add(ad.getText());
		    address.setAddressLines(addLines);
		    return address;
		}
	    }
	} catch (Exception exception) {
	    System.out
		    .println("Exception: " + exception.getMessage() + " occured into CommonUtil::buildAddress method");
	}
	return null;
    }

    public static String getName(EList<PN> pns) {
	try {
	    String name = "";
	    if (pns != null && pns.size() > 0) {
		if (pns.get(0) != null) {
		    if (pns.get(0).getGivens() != null && pns.get(0).getGivens().size() > 0
			    || pns.get(0).getFamilies() != null && pns.get(0).getFamilies().size() > 0) {
			if (pns.get(0).getGivens().size() > 0 && pns.get(0).getGivens().get(0) != null) {
			    name = pns.get(0).getGivens().get(0).getText();
			}
			if (pns.get(0).getFamilies().size() > 0 && pns.get(0).getFamilies().get(0) != null) {
			    return (name + " " + pns.get(0).getFamilies().get(0).getText()).trim();
			}
			if (!name.equals("")) {
			    return name;
			}
			return null;
		    } else {
			return pns.get(0).getText();
		    }
		}
	    }
	    return null;
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage() + " occured into CommonUtil::getName method");
	}
	return null;
    }

    public static String parseEffectiveTimeyyyMMddAsString(Date date) {
	try {
	    if (date != null) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	    }
	    return null;
	} catch (Exception exception) {
	    System.out.println("Exception: " + exception.getMessage()
		    + " occured into CommonUtil::parseEffectiveTimeyyyMMddAsString method");
	}
	return null;
    }

    public static String getEffectiveTime(Observation observation) {
	try{
	if (observation.getEffectiveTime() != null && observation.getEffectiveTime().getLow() != null) {
	    return CommonUtil.parseEffectiveTimeyyyMMddAsString(
		    CommonUtil.parseEffectiveTimeyyyMMdd(observation.getEffectiveTime().getLow().getValue()));
	} else if (observation.getEffectiveTime() != null && observation.getEffectiveTime().getHigh() != null) {
	    return CommonUtil.parseEffectiveTimeyyyMMddAsString(
		    CommonUtil.parseEffectiveTimeyyyMMdd(observation.getEffectiveTime().getHigh().getValue()));
	}
	}catch(Exception exception){
	    System.out.println("Exception: " + exception.getMessage()
	    + " occured into CommonUtil::getEffectiveTime(Observation observation) method");
	}
	return null;
    }
}
