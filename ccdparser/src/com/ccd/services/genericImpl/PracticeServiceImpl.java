package com.ccd.services.genericImpl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.DocumentationOf;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.Performer1;
import org.openhealthtools.mdht.uml.cda.RecordTarget;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;

import com.ccd.models.Address;
import com.ccd.models.Practice;
import com.ccd.services.PracticeService;
import com.ccd.util.CommonUtil;

public class PracticeServiceImpl implements PracticeService {

    public Practice[] findPractice(EList<RecordTarget> recordTargets) {
	List<Practice> practices = new ArrayList<Practice>();
	try {
	    for (RecordTarget recordTarget : recordTargets) {
		Organization organization = recordTarget.getPatientRole().getProviderOrganization();
		if (organization == null) {
		    continue;
		}
		Practice practice = new Practice();
		Address address = new Address();
		List<String> telecoms = new ArrayList<String>();
		if (organization.getNames() != null && organization.getNames().size() > 0
			&& organization.getNames().get(0) != null) {
		    practice.setName(organization.getNames().get(0).getText());
		}
		if (organization.getAddrs() != null && organization.getAddrs().size() > 0) {
		    address = CommonUtil.buildAddress(organization.getAddrs().get(0));
		}
		if (organization.getTelecoms() != null && organization.getTelecoms().size() > 0) {
		    for (TEL tel : organization.getTelecoms()) {
			telecoms.add(tel.getValue());
		    }
		}
		practice.setTelephones(telecoms);
		practice.setAddress(address);
		practices.add(practice);
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (practices.size() > 0)
	    return practices.toArray(new Practice[practices.size()]);
	return null;
    }

    /**
     * This method find the Providers from given CCD XML
     * 
     * @param documentationOfs
     * @return
     */
    @Override
    public Practice[] findPractices(EList<DocumentationOf> documentationOfs) {
	List<Practice> practices = new ArrayList<Practice>();
	try {
	    for (DocumentationOf documentationOf : documentationOfs) {
		EList<Performer1> performer1s = documentationOf.getServiceEvent().getPerformers();
		for (Performer1 performer1 : performer1s) {
		    Practice practice = new Practice();
		    Address address = new Address();
		    List<String> telecoms = new ArrayList<String>();
		    if (performer1.getAssignedEntity() != null) {
			if (performer1.getAssignedEntity().getRepresentedOrganizations() != null) {
			    for (Organization organization : performer1.getAssignedEntity()
				    .getRepresentedOrganizations()) {
				if (organization.getNames() != null && organization.getNames().size() > 0) {
				    practice.setName(CommonUtil.getText(organization.getNames().get(0).getMixed()));
				    if (organization.getAddrs() != null && organization.getAddrs().size() > 0) {
					address = CommonUtil.buildAddress(organization.getAddrs().get(0));
				    }
				    if (organization.getTelecoms() != null && organization.getTelecoms().size() > 0) {
					for (TEL tel : organization.getTelecoms()) {
					    telecoms.add(tel.getValue());
					}
				    }
				}
			    }
			}
			practice.setAddress(address);
			practice.setTelephones(telecoms);
			practices.add(practice);
		    }
		}
	    }

	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (practices.size() > 0)
	    return practices.toArray(new Practice[practices.size()]);
	return null;
    }

    @Override
    public Practice[] findPracticesFromAuthorSection(EList<Author> authors) {
	List<Practice> practices = new ArrayList<Practice>();
	try {
	    for (Author author : authors) {
		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		if (assignedAuthor != null) {
		    Practice practice = new Practice();
		    Address address = new Address();
		    List<String> telecoms = new ArrayList<String>();
		    if (assignedAuthor.getRepresentedOrganization() != null) {
			Organization organization = assignedAuthor.getRepresentedOrganization();
			if (organization.getNames() != null && organization.getNames().size() > 0) {
			    practice.setName(CommonUtil.getText(organization.getNames().get(0).getMixed()));
			    if (organization.getAddrs() != null && organization.getAddrs().size() > 0) {
				address = CommonUtil.buildAddress(organization.getAddrs().get(0));
			    }
			    if (organization.getTelecoms() != null && organization.getTelecoms().size() > 0) {
				for (TEL tel : organization.getTelecoms()) {
				    telecoms.add(tel.getValue());
				}
			    }
			}
			practice.setAddress(address);
			practice.setTelephones(telecoms);
			practices.add(practice);
		    }
		}
	    }

	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	if (practices.size() > 0)
	    return practices.toArray(new Practice[practices.size()]);
	return null;
    }
}
