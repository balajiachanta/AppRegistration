package com.bala.reg.db.bean;

public class RegistrationResponse {


	private String identifier;
	private boolean isSuccess;
	private String errorMsg;
	private String awsId;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getAwsId() {
		return awsId;
	}
	public void setAwsId(String awsId) {
		this.awsId = awsId;
	}


}
