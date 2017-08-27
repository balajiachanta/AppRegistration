package com.bala.reg.db.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gcm_token")
public class GCMTokenInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long gcmId;
	
	
	private String token;
	private Long devId;
	public Long getGcmId() {
		return gcmId;
	}
	public void setGcmId(Long gcmId) {
		this.gcmId = gcmId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getDevId() {
		return devId;
	}
	public void setDevId(Long devId) {
		this.devId = devId;
	}
}
