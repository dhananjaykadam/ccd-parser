package com.ccd.models;

/**
 * Model to carry InterdisclinaryTeam data
 *
 */
public class InterdisclinaryTeam {
	private String role;
	private String name;
	private String location;
	private String phone;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "InterdisclinaryTeam [role=" + role + ", name=" + name + ", location=" + location + ", phone=" + phone
				+ "]";
	}

}
