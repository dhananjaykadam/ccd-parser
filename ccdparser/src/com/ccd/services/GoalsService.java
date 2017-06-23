package com.ccd.services;

import java.util.List;

import org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument;

import com.ccd.models.Goal;

public interface GoalsService {
    /**
     * Generic Method to parse goals
     * @param continuityOfCareDocument
     * @return {@link List}of{@link Goal}
     */
    public Goal[] parseGoals(ContinuityOfCareDocument continuityOfCareDocument);
    /**
     * Public method to parse MHG goals
     * @param continuityOfCareDocument
     * @return
     */
    public Goal[] parseMHGGoals(ContinuityOfCareDocument continuityOfCareDocument);
    /**
     * Public method to parse Martenes goals
     * @param continuityOfCareDocument
     * @return
     */
	    
    public Goal[] parseMartinezGoals(ContinuityOfCareDocument continuityOfCareDocument);
}
