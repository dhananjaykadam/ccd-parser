package com.ccd.util;

import org.eclipse.emf.ecore.xml.type.AnyType;

public class OpenHealthToolXmlUtil {
    public static AnyType getAnyTypeFromEntry(org.eclipse.emf.ecore.util.FeatureMap.Entry entry) {
	try{
	return (AnyType) entry.getValue();
	}catch(Exception exception){
	    return null;
	}
    }

    public static org.eclipse.emf.ecore.util.FeatureMap getFeatureMapFromAnyType(AnyType anyType) {
	try{
	return anyType.getMixed();
    }catch(Exception exception){
	    return null;
	}
    }

    public static org.eclipse.emf.ecore.util.FeatureMap.Entry getEntryFromMixed(
	    org.eclipse.emf.ecore.util.FeatureMap featureMap, int index) {
	try{
	return featureMap.get(index);
    }catch(Exception exception){
	    return null;
	}
    }

}
