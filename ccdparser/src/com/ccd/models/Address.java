package com.ccd.models;

import java.util.List;

public class Address {
	private List<String> addressLines;
	private String city;
	private String state;
	private String postalCode;
	private String country;

	public List<String> getAddressLines() {
		return addressLines;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAddressLines(List<String> addressLines) {
		this.addressLines = addressLines;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Address [addressLines=" + addressLines + ", city=" + city + ", state=" + state + ", postalCode="
				+ postalCode + ", country=" + country + "]";
	}

}
