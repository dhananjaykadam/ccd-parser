package com.ccd.models;

import java.util.Map;

public class Result {
	private String test;
	private String testType;
	private Map<String,String> testObservations;
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public Map<String, String> getTestObservations() {
		return testObservations;
	}
	public void setTestObservations(Map<String, String> testObservations) {
		this.testObservations = testObservations;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	@Override
	public String toString() {
		return "Result [test=" + test + ", testType=" + testType + ", testObservations=" + testObservations + "]";
	}
	
}
