package com.ccd.services;

import org.eclipse.emf.common.util.EList;
import org.openhealthtools.mdht.uml.cda.DocumentationOf;

import com.ccd.models.Provider;

public interface ProvidersService {
	/**
	 * This method find the Providers from given CCD XML from {@link DocumentationOf} section
	 * @param documentationOfs
	 * @return
	 */
		public  Provider[] findProviders(
				EList<DocumentationOf> documentationOfs);
}
