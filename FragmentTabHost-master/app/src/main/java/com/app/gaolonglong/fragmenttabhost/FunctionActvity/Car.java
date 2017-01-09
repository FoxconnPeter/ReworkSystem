package com.app.gaolonglong.fragmenttabhost.FunctionActvity;


public class Car {
	private String lc;
	private String dc;
	private String manufacturer;
	private String time;
	private String model;
	private String sn;
	private String station;
	private String location;
	private String partsn;
	private String description;
	private String repair_engneer;
	private String type;
	private String failReason;

	public Car() {
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPartsn() {
		return partsn;
	}

	public void setPartsn(String partsn) {
		this.partsn = partsn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRepair_engneer() {
		return repair_engneer;
	}

	public void setRepair_engneer(String repair_engneer) {
		this.repair_engneer = repair_engneer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Car(String lc, String dc, String manufacturer, String time, String model, String sn, String station, String location,
			   String partsn, String description,
			   String repair_engneer, String type, String failReason) {
		this.lc = lc;
		this.type = type;
		this.dc = dc;

		this.manufacturer = manufacturer;
		this.time = time;
		this.model = model;
		this.sn = sn;
		this.station = station;
		this.location = location;
		this.partsn = partsn;
		this.description = description;
		this.repair_engneer = repair_engneer;
		this.failReason = failReason;

	}





}
