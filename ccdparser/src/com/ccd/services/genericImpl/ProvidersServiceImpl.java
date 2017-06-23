package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.openhealthtools.mdht.uml.cda.DocumentationOf;
import org.openhealthtools.mdht.uml.cda.Performer1;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;

import com.ccd.models.Address;
import com.ccd.models.Provider;
import com.ccd.services.ProvidersService;
import com.ccd.util.CommonUtil;

public class ProvidersServiceImpl implements ProvidersService {

    /**
     * This method find the Providers from given CCD XML
     * 
     * @param documentationOfs
     * @return
     */
    @Override
    public Provider[] findProviders(EList<DocumentationOf> documentationOfs) {
	List<Provider> providers = new ArrayList<Provider>();
	try {
	    for (DocumentationOf documentationOf : documentationOfs) {
		EList<Performer1> performer1s = documentationOf.getServiceEvent().getPerformers();
		for (Performer1 performer1 : performer1s) {
		    Provider provider = new Provider();
		    Address address = new Address();
		    List<String> telecoms = new ArrayList<String>();
		    if (performer1.getAssignedEntity() != null) {
			if (performer1.getAssignedEntity().getAssignedPerson() != null) {
			    if (performer1.getAssignedEntity().getAssignedPerson().getNames() != null
				    && performer1.getAssignedEntity().getAssignedPerson().getNames().size() > 0) {
				provider.setName(CommonUtil
					.getName(performer1.getAssignedEntity().getAssignedPerson().getNames()));
			    }
			}
			if (performer1.getAssignedEntity().getAddrs() != null
				&& performer1.getAssignedEntity().getAddrs().size() > 0) {
			    address = CommonUtil.buildAddress(performer1.getAssignedEntity().getAddrs().get(0));
			}
			if (performer1.getAssignedEntity().getTelecoms() != null) {
			    for (TEL tel : performer1.getAssignedEntity().getTelecoms()) {
				telecoms.add(tel.getValue());
			    }
			}
		    }
		    provider.setAddress(address);
		    provider.setTelephones(telecoms);
		    providers.add(provider);
		}
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (providers.size() > 0)
	    return providers.toArray(new Provider[providers.size()]);
	return null;
    }
}
