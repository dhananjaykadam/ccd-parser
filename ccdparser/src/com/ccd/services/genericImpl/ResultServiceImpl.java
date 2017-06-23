package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.FeatureMap;

import com.ccd.models.Result;
import com.ccd.services.ResultService;

public class ResultServiceImpl implements ResultService {
    /**
     * Method to find Patient Results and build Java class object for per Result
     * information
     * 
     * @param featureMap
     * @return
     */
    public List<Result> findPatientResults(FeatureMap featureMap) {
	List<Result> results = new ArrayList<Result>();
	try {
	    // Part Not Available in CCD
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (results.size() > 0)
	    return results;
	return null;
    }
}
