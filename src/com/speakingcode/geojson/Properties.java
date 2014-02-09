package com.speakingcode.geojson;

public class Properties {
	String name;
	String City;
	String State;
	float Miles;
	
	String TYPE;
	String Remarks;
	String Material;
	String Facility; 
	String County;
	String Hours;
	String Management;
	String Website;
	String Location;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public float getMiles() {
		return Miles;
	}
	public void setMiles(float miles) {
		Miles = miles;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getMaterial() {
		return Material;
	}
	public void setMaterial(String material) {
		Material = material;
	}
	public String getFacility() {
		return Facility;
	}
	public void setFacility(String facility) {
		Facility = facility;
	}
	public String getCounty() {
		return County;
	}
	public void setCounty(String county) {
		County = county;
	}
	public String getHours() {
		return Hours;
	}
	public void setHours(String hours) {
		Hours = hours;
	}
	public String getManagement() {
		return Management;
	}
	public void setManagement(String management) {
		Management = management;
	}
	public String getWebsite() {
		return Website;
	}
	public void setWebsite(String website) {
		Website = website;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public Properties(String name, String city, String state, float miles,
			String tYPE, String remarks, String material, String facility,
			String county, String hours, String management, String website,
			String location) {
		super();
		this.name = name;
		City = city;
		State = state;
		Miles = miles;
		TYPE = tYPE;
		Remarks = remarks;
		Material = material;
		Facility = facility;
		County = county;
		Hours = hours;
		Management = management;
		Website = website;
		Location = location;
	}
	
	
}
