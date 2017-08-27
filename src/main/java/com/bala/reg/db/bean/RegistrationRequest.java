package com.bala.reg.db.bean;

public class RegistrationRequest {

	private String number;
	private String uniqueIndentifer;
	private String type;
	private String version;
	private String clientName;
	
	private String manufacturerName;
	private String gcmToken;
	
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String model;
	private String token;
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUniqueIndentifer() {
		return uniqueIndentifer;
	}
	public void setUniqueIndentifer(String uniqueIndentifer) {
		this.uniqueIndentifer = uniqueIndentifer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	@Override
	public String toString() {
		return "RegistrationRequest [number=" + number + ", uniqueIndentifer=" + uniqueIndentifer + ", type=" + type
				+ ", version=" + version + ", clientName=" + clientName + "]";
	}
	public String getGcmToken() {
		return gcmToken;
	}
	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}
	
	
}
