package com.bala.reg;

import com.bala.reg.db.bean.RegistrationRequest;
import com.bala.reg.db.bean.RegistrationResponse;

public interface RegSpec {

	public RegistrationResponse createRegistration(RegistrationRequest request);
	
	public String findRegistrations(String request);
}
