package com.ccd.services;

import org.openhealthtools.mdht.uml.cda.Section;

import com.ccd.models.VitalSign;

public interface VitalsService {
	public VitalSign[] findVitalSigns(Section vitalSignSection);
}
