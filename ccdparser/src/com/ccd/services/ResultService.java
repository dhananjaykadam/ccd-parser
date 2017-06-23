package com.ccd.services;

import java.util.List;

import org.eclipse.emf.ecore.util.FeatureMap;

import com.ccd.models.Result;

public interface ResultService {
	public List<Result> findPatientResults(FeatureMap featureMap);
}
