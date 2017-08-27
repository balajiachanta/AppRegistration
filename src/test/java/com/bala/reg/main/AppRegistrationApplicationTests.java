package com.bala.reg.main;


import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bala.reg.db.bean.RegistrationRequest;
import com.bala.reg.service.AndroidRegistration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
//@SpringBootTest
@WebAppConfiguration
public class AppRegistrationApplicationTests {

	//@Autowired
	private AndroidRegistration andReg;

	

	/*@Test
	public void contextLoads() throws JsonProcessingException {

		ObjectMapper om= new ObjectMapper();


		RegistrationRequest request = new RegistrationRequest();

		request.setClientName("springboot");
		request.setNumber("987654");
		request.setType("IOS");
		request.setUniqueIndentifer("64e58725325");

		//System.out.println(om.writeValueAsString(request));

		//andReg.createRegistration(request);

		System.out.println("test    " +andReg.sendToken("d_jqM2EhKNc:APA91bFM6Jji4vqXI78ut7JHbfpU3J34OCQWYJfT6dv6_GRdwy5y2qTwLZX1oNgUn_JjxNgtEPsSQhLmvz1x1HKDpuIJFaTmdR2U7iyh8MdWSx8R9cDsGMsIokvwslCWISd3xVRIBBv8"));
		//arn:aws:sns:us-west-2:408428117988:endpoint/GCM/RegistrationApp/a59bc51a-a653-39c0-93f2-f75c221314af
		//assertThat("987654").isEqualTo(andReg.findRegistrations("987654"));
		assertTrue(true);
	}*/


	public void createRegTest() throws JsonProcessingException {

		ObjectMapper om= new ObjectMapper();


		RegistrationRequest request = new RegistrationRequest();


		request.setClientName("balu");
		request.setNumber("987654");
		request.setType("IOS");
		request.setUniqueIndentifer("64e58725325");
		request.setVersion("0.0.1");

		request.setGcmToken("d_jqM2EhKNc:APA91bFM6Jji4vqXI78ut7JHbfpU3J34OCQWYJfT6dv6_GRdwy5y2qTwLZX1oNgUn_JjxNgtEPsSQhLmvz1x1HKDpuIJFaTmdR2U7iyh8MdWSx8R9cDsGMsIokvwslCWISd3xVRIBBv8");
		request.setManufacturerName("motorala");
		request.setModel("G110");

		System.out.println(om.writeValueAsString(request));

		//andReg.createRegistration(request);

		//andReg.getTokenInfo();

		//andReg.deleteSNSInfo();

		assertTrue(true);
	}

	@Test
	public void testCreateRegistraion(){
		final String uri = "http://localhost:9765/create";
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    
	    RegistrationRequest request = new RegistrationRequest();


		request.setClientName("balu");
		request.setNumber("987654");
		request.setType("IOS");
		request.setUniqueIndentifer("64e58725325");
		request.setVersion("0.0.1");

		request.setGcmToken("d_jqM2EhKNc:APA91bFM6Jji4vqXI78ut7JHbfpU3J34OCQWYJfT6dv6_GRdwy5y2qTwLZX1oNgUn_JjxNgtEPsSQhLmvz1x1HKDpuIJFaTmdR2U7iyh8MdWSx8R9cDsGMsIokvwslCWISd3xVRIBBv8");
		request.setManufacturerName("motorala");
		request.setModel("G110");
		
		HttpEntity<RegistrationRequest> entity = new HttpEntity<RegistrationRequest>(request, headers);
		
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		
		ResponseEntity<String> response = restTemplate.exchange(uri,
				HttpMethod.POST, entity, String.class);

		String actual = response.getBody();
		
		System.out.println(actual);

		assertTrue(actual.contains("true"));

	    
	    
	}

}
