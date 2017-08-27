package com.bala.reg.db.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dev_info")
public class DeviceInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long devId;
	
	private String devIdentifier;
	private Long profId;
	private String type;
	private String clientId;
	private Long gcmId;
	
	public Long getDevId() {
		return devId;
	}

	public void setDevId(Long devId) {
		this.devId = devId;
	}

	public String getDevIdentifier() {
		return devIdentifier;
	}

	public void setDevIdentifier(String devIdentifier) {
		this.devIdentifier = devIdentifier;
	}

	public Long getProfId() {
		return profId;
	}

	public void setProfId(Long profId) {
		this.profId = profId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DeviceInfo() {
		
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getGcmId() {
		return gcmId;
	}

	public void setGcmId(Long gcmId) {
		this.gcmId = gcmId;
	}
	
	
	
}
