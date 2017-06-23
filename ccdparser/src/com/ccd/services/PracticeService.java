package com.ccd.services;

import org.eclipse.emf.common.util.EList;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.DocumentationOf;
import org.openhealthtools.mdht.uml.cda.RecordTarget;

import com.ccd.models.Practice;

public interface PracticeService {

	/**
	 * this method finds Practices from given CCD XML file
	 * @param recordTargets
	 * @return
	 */
	public  Practice[] findPractice(EList<RecordTarget> recordTargets);

	Practice[] findPracticesFromAuthorSection(EList<Author> authors);

	Practice[] findPractices(EList<DocumentationOf> documentationOfs); 
}
