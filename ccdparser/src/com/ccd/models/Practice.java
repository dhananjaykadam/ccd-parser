package com.ccd.models;

import java.util.List;

public class Practice {
	private String Name;
	private List<String> telephones;
	private Address address;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<String> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<String> telephones) {
		this.telephones = telephones;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Practice [Name=" + Name + ", telephones=" + telephones + ", address=" + address + "]";
	}

}
