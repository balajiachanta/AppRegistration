package com.bala.reg.db.table;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dev_reg")
public class DeiviceRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long identifer;
	private String number;
	private String deviceIdentifer;
	private String clientId;
	private String version;
	private String type;
	private String devId;
	
	private int isActive;
	
	public Long getIdentifer() {
		return identifer;
	}
	public void setIdentifer(Long identifer) {
		this.identifer = identifer;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDeviceIdentifer() {
		return deviceIdentifer;
	}
	public void setDeviceIdentifer(String deviceIdentifer) {
		this.deviceIdentifer = deviceIdentifer;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public DeiviceRegistration(String number, String deviceIdentifer, String clientId,
			String version, String type, String devId) {
		this.number = number;
		this.deviceIdentifer = deviceIdentifer;
		this.clientId = clientId;
		this.version = version;
		this.type = type;
		this.devId = devId;
	}
	public DeiviceRegistration() {
		
	}
	
	
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "DeiviceRegistration [identifer=" + identifer + ", number=" + number + ", deviceIdentifer="
				+ deviceIdentifer + ", clientId=" + clientId + ", version=" + version + ", type=" + type + ", devId="
				+ devId + ", isActive=" + isActive + "]";
	}
	
	
}
