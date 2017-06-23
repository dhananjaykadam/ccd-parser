package com.ccd.models;

import java.util.List;

/**
 * 
 * Model class to carry care plan providers information.
 */
public class Provider {
	private String name;
	private Address address;
	private List<String> telephones;

	public String getName() {
		return name;
	}

	public List<String> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<String> telephones) {
		this.telephones = telephones;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Provider [name=" + name + ", address=" + address + ", telephones=" + telephones + "]";
	}

}
