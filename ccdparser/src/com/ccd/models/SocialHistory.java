package com.ccd.models;

public class SocialHistory {
public String smokingStatus;
public String tobaccoUse;
public String pregnencyObervation;
public String getSmokingStatus() {
	return smokingStatus;
}
public void setSmokingStatus(String smokingStatus) {
	this.smokingStatus = smokingStatus;
}
public String getTobaccoUse() {
	return tobaccoUse;
}
public void setTobaccoUse(String tobaccoUse) {
	this.tobaccoUse = tobaccoUse;
}
public String getPregnencyObervation() {
	return pregnencyObervation;
}
public void setPregnencyObervation(String pregnencyObervation) {
	this.pregnencyObervation = pregnencyObervation;
}
@Override
public String toString() {
	return "SocialHistory [smokingStatus=" + smokingStatus + ", tobaccoUse=" + tobaccoUse + ", pregnencyObervation="
			+ pregnencyObervation + "]";
}


}
