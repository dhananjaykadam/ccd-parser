package com.ccd.services.genericImpl;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument;

import com.ccd.models.Goal;
import com.ccd.services.GoalsService;
import com.ccd.util.CCDConstants;
import com.ccd.util.CommonUtil;
import com.ccd.util.OpenHealthToolXmlUtil;

public class GoalsServiceImpl implements GoalsService {
    /**
     * {@inheritDoc}
     */
    @Override
    public Goal[] parseGoals(ContinuityOfCareDocument continuityOfCareDocument) {
	if (isMHGGoalsPresent(continuityOfCareDocument)) {
	    return parseMHGGoals(continuityOfCareDocument);
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Goal[] parseMHGGoals(ContinuityOfCareDocument continuityOfCareDocument) {
	Section section = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
		CCDConstants.ASSESSMENT_PLAN_SECTION, true);
	List<Goal> goals = new ArrayList<Goal>();
	try {
	    if (section == null || section.getText() == null) {
		return null;
	    }
	    Goal goal = null;
	    StrucDocText strucDocText = section.getText();
	    System.out.println(section.getText());
	    /*Iterator<org.eclipse.emf.ecore.util.FeatureMap.Entry> masterMaps = strucDocText.getMixed().iterator();
	    while (masterMaps.hasNext()) {
		org.eclipse.emf.ecore.util.FeatureMap.Entry tableEntry = masterMaps.next();
		try {
		    System.out.println(tableEntry.getEStructuralFeature().getName());
		    if (tableEntry.getEStructuralFeature().getName().equals("table")) {
			FeatureMap tableMap= OpenHealthToolXmlUtil.getFeatureMapFromAnyType(OpenHealthToolXmlUtil.getAnyTypeFromEntry(tableEntry));
			if(tableMap!=null){
			Iterator<org.eclipse.emf.ecore.util.FeatureMap.Entry> tbodyEntry = tableMap.iterator();
			while (tbodyEntry.hasNext()) {
			    org.eclipse.emf.ecore.util.FeatureMap.Entry tbody = tbodyEntry.next();
			    try {
				System.out.println(tbody.getEStructuralFeature().getName());
				if (tbody.getEStructuralFeature().getName().equals("tbody")) {

				    FeatureMap trMap= OpenHealthToolXmlUtil.getFeatureMapFromAnyType(OpenHealthToolXmlUtil.getAnyTypeFromEntry(tbody));
					if(trMap!=null){
					Iterator<org.eclipse.emf.ecore.util.FeatureMap.Entry> trEntryIterator = trMap.iterator();
					while (trEntryIterator.hasNext()) {
					    org.eclipse.emf.ecore.util.FeatureMap.Entry trEntry = trEntryIterator.next();
					    try {
						System.out.println(trEntry.getEStructuralFeature().getName());
						if (trEntry.getEStructuralFeature().getName().equals("tr")) {
						    Iterator<> =OpenHealthToolXmlUtil.getFeatureMapFromAnyType(OpenHealthToolXmlUtil.getAnyTypeFromEntry(trEntry)).iterator(;
						    
						    
						    
						    
						    
						    
						    FeatureMap featureMap3 = OpenHealthToolXmlUtil.getFeatureMapFromAnyType(
							    OpenHealthToolXmlUtil.getAnyTypeFromEntry(entry3));
						    Iterator<org.eclipse.emf.ecore.util.FeatureMap.Entry> iteratorTabledata = featureMap3
							    .iterator();
						    String goalStatement = "";
						    while (iteratorTabledata.hasNext()) {
							try {
							    org.eclipse.emf.ecore.util.FeatureMap.Entry entry31 = iteratorTabledata
								    .next();
							    FeatureMap featureMap4 = OpenHealthToolXmlUtil.getFeatureMapFromAnyType(
								    OpenHealthToolXmlUtil.getAnyTypeFromEntry(entry31));
							    BasicFeatureMap basicFeatureMap = (BasicFeatureMap) OpenHealthToolXmlUtil
								    .getFeatureMapFromAnyType(
									    OpenHealthToolXmlUtil.getAnyTypeFromEntry(entry31));
							    Iterator<org.eclipse.emf.ecore.util.FeatureMap.Entry> iterator = basicFeatureMap
								    .iterator();
							    while (iterator.hasNext()) {
								org.eclipse.emf.ecore.util.FeatureMap.Entry entry4 = iterator.next();
								if (entry4.getValue().getClass().equals(String.class)) {
								    goalStatement += entry4.getValue();
								}
							    }
							} catch (Exception exception) {
							    System.out.println("EXCEPTION WHILE PARSING TD " + exception.getMessage());
							}
						    }
						}
						}catch (Exception exception) {
						    System.out.println("EXCEPTION WHILE PARSING TD " + exception.getMessage());
						}
						    
					    }
					}
				    

//				    if (!goalStatement.equals("")) {
//					goal = new Goal();
//					goal.setGoalStatement(goalStatement);
//				    }
				}
			    } catch (Exception exception) {
				System.out.println("[EXCEPTION WHILE PARSING MHG GOALS]");
			    }
			}
			}
			
		    }
		} catch (Exception exception) {
		    System.out.println("[EXCEPTION WHILE PARSING MHG GOALS]");
		}
	    }*/
	    goals.add(goal);
		return goals.toArray(new Goal[1]);
	    
	} catch (Exception exception) {
	    System.out.println("[EXCEPTION WHILE PARSING MHG GOALS]");
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Goal[] parseMartinezGoals(ContinuityOfCareDocument continuityOfCareDocument) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * check if its an MHG CCD which contains ASSESSMENT_PLAN_SECTION
     * 
     * @param continuityOfCareDocument
     * @return
     */
    private boolean isMHGGoalsPresent(ContinuityOfCareDocument continuityOfCareDocument) {
	Section section = CommonUtil.findSectionById(continuityOfCareDocument.getAllSections(),
		CCDConstants.ASSESSMENT_PLAN_SECTION, true);
	if (section != null) {
	    if (section.getText() != null) {
		return true;
	    }
	}
	return false;
    }
}
