package com.ccd.services;

import org.openhealthtools.mdht.uml.cda.Section;

import com.ccd.models.PlanOfCare;

public interface PlanOfCareService {
    public PlanOfCare parsePlanOfCareSection(Section section);
}
