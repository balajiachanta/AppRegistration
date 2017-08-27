package com.bala.reg.db.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="android_info")
public class AndroidProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long profId;
	
	private String manufacturer;
	private String model;
	
	private Long devId;
	
	public Long getProfId() {
		return profId;
	}
	public void setProfId(Long profId) {
		this.profId = profId;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Long getDevId() {
		return devId;
	}
	public void setDevId(Long devId) {
		this.devId = devId;
	}
}
