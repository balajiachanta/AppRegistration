package com.bala.reg.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteEndpointRequest;
import com.amazonaws.services.sns.model.DeletePlatformApplicationRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.amazonaws.services.sns.model.Topic;
import com.bala.reg.RegSpec;
import com.bala.reg.db.bean.RegistrationRequest;
import com.bala.reg.db.bean.RegistrationResponse;
import com.bala.reg.db.repo.AWSRepo;
import com.bala.reg.db.repo.AndroidProdfileRepo;
import com.bala.reg.db.repo.ClientRepo;
import com.bala.reg.db.repo.DeviceInfoRepo;
import com.bala.reg.db.repo.DeviceRepo;
import com.bala.reg.db.repo.GCMTokenRepo;
import com.bala.reg.db.table.AWSSNSInfo;
import com.bala.reg.db.table.AndroidProfile;
import com.bala.reg.db.table.ClientInfo;
import com.bala.reg.db.table.DeiviceRegistration;
import com.bala.reg.db.table.DeviceInfo;
import com.bala.reg.db.table.GCMTokenInfo;

@RestController
public class AndroidRegistration implements RegSpec {
	
	@Autowired
	private DeviceRepo deviceRepo;
	
	@Autowired
	private ClientRepo clientRepo;
	
	@Autowired
	private AndroidProdfileRepo androidProdfileRepo;
	
	@Autowired
	private DeviceInfoRepo deviceInfoRepo;
	
	@Autowired
	private GCMTokenRepo gCMTokenRepo;
	
	@Autowired
	private AWSRepo aWSRepo;
	
	@Autowired
	private AmazonSNS amazonSns;
	
	@Autowired
	private AWSCredentialsProvider aWSCredentialsProvider;
	
	@Value("${applicationARN}")
	private String applicationArn;
	
	@Value("${snstopic}")
	private String snstopic;
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidRegistration.class);
	
	@PostMapping(value="/create",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Override
	public RegistrationResponse createRegistration(@RequestBody RegistrationRequest request) {
		
		RegistrationResponse res = new RegistrationResponse();
		
		logger.debug("creating registration for " +request.getNumber());
		logger.debug("full request " +request);
		
		ClientInfo clientInfo = clientRepo.findByName(request.getClientName());
		if(null != clientInfo){
			logger.debug("found client");
			
			AndroidProfile androidProfile = new AndroidProfile();
			androidProfile.setManufacturer(request.getManufacturerName());
			androidProfile.setModel(request.getModel());
			
			AndroidProfile androidProfileResult = androidProdfileRepo.save(androidProfile);
			
			GCMTokenInfo gcmRequest = new GCMTokenInfo();
			gcmRequest.setToken(request.getGcmToken());
			GCMTokenInfo gcmTokenResult = gCMTokenRepo.save(gcmRequest);
			
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDevIdentifier(Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes()).replace('+', '-').replace('/', '_').substring(0, 22));
			deviceInfo.setGcmId(gcmTokenResult.getGcmId());
			deviceInfo.setProfId(androidProfileResult.getProfId());
			deviceInfo.setClientId(clientInfo.getClientId().toString());
			deviceInfo.setType(request.getType());
			
			DeviceInfo deviceInfoResult = deviceInfoRepo.save(deviceInfo);
			
			
			/*
			List<DeiviceRegistration> idList = deviceRepo.findByNumber(request.getNumber());
			
			idList.stream().parallel().forEach(
					 d -> {
					 	if(d.getDeviceIdentifer().equals(request.getUniqueIndentifer()) && d.getIsActive() == 1){
					 		
					 	}
					 });*/
			
			DeiviceRegistration reg = new DeiviceRegistration(request.getNumber(),request.getUniqueIndentifer()
					,clientInfo.getClientId().toString(),request.getVersion(),request.getType(),deviceInfoResult.getDevId().toString());
			reg.setIsActive(1);
			
			DeiviceRegistration devRegResult = deviceRepo.save(reg);
			logger.debug("device reg saved " +devRegResult.getIdentifer());
			
			String awsId = createSNSRecord(request.getGcmToken(),request.getNumber(),true,deviceInfoResult.getDevId().toString());
			
			
			res.setAwsId(awsId);
			res.setIdentifier(deviceInfoResult.getDevIdentifier());
			res.setSuccess(true);
			
			logger.debug("created reg "+devRegResult.getDeviceIdentifer().toString() +"  and "+devRegResult.getDevId());
			return res;
		}
		
		
		res.setSuccess(false);
		res.setErrorMsg("client ID missing");
		
		return res;
		

	}

	@GetMapping(value="/retriveReg",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Override
	public String findRegistrations(@RequestParam String id) {
		// TODO Auto-generated method stub
		/*List<DeiviceRegistration> idList = deviceRepo.findByNumber(id);
		List<String> li = new ArrayList<>();
		
		return idList.stream().findFirst().get().getNumber();*/
		return null;
	}
	
	
	@GetMapping(value="/sendNotification",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public PublishResult sendNotifcation(@RequestParam String user,@RequestParam String msg) {
		
		logger.debug("sending notification ");
		DeiviceRegistration deiviceRegistration = deviceRepo.findByNumber(user);
		
		 // publishing to a topic
        PublishResult res = amazonSns.publish(aWSRepo.findByDevId(deiviceRegistration.getDevId()).getTopicArn(), msg);
        logger.debug(" notification sent");
        return res;
	}
	
	private String  createSNSRecord(String token, String user,boolean createTopic, String devId){
		
		AWSSNSInfo awsInfo = new AWSSNSInfo();
		
		logger.debug("createSNSRecord notification ");
		
		
		//amazonSns.deletePlatformApplication(deletePlatformApplicationRequest)
		
		
		CreatePlatformEndpointRequest platformEndpointRequest = new CreatePlatformEndpointRequest();
		
		platformEndpointRequest.withPlatformApplicationArn(applicationArn); //application arn (wee need create 1)
        platformEndpointRequest.withToken(token);
        platformEndpointRequest.withCustomUserData(user);
        
        CreatePlatformEndpointResult platformResult = AmazonSNSClientBuilder.standard().withCredentials(aWSCredentialsProvider)
                .withRegion(Regions.US_WEST_2)
                .build().createPlatformEndpoint(platformEndpointRequest);
        awsInfo.setPlatforEndPointArn(platformResult.getEndpointArn());
        
        String topicArun = null;
        
        boolean isTopicExist = false;
        
        // listing out existring topic if not creating one
        if(amazonSns.listTopics().getTopics().size() > 0){
        	for(Topic topic : amazonSns.listTopics().getTopics()){
        		if(topic.getTopicArn().indexOf(snstopic) > 0){
        			topicArun = topic.getTopicArn();
        			isTopicExist = true;
        		}
        	}
        }
        
        if(!isTopicExist){
        	//creating topic
            CreateTopicResult topicResult = amazonSns.createTopic(snstopic);
            topicArun=topicResult.getTopicArn();
        }
    
        awsInfo.setTopicArn(topicArun);
        
        //subscribing to above topic for the application arn for platforendpoint
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setEndpoint(platformResult.getEndpointArn());
        subscribeRequest.setProtocol("application");
        subscribeRequest.setTopicArn(topicArun);
        
        SubscribeResult subscribeResult = amazonSns.subscribe(subscribeRequest);
        awsInfo.setSubscriberArn(subscribeResult.getSubscriptionArn());
        
        awsInfo.setDevId(devId);
        
        AWSSNSInfo awsId = aWSRepo.save(awsInfo);
		return awsId.getAwsId().toString();
	}
	
	@GetMapping(value="/delete",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void deleteSNSInfo(){
		
		DeletePlatformApplicationRequest deletePlatformReq = new DeletePlatformApplicationRequest();
		deletePlatformReq.setPlatformApplicationArn("arn:aws:sns:us-west-2:408428117988:endpoint%2FGCM%2FRegistrationApp%2Fa59bc51a-a653-39c0-93f2-f75c221314af");
		//amazonSns.deletePlatformApplication(deletePlatformReq);
		System.out.println("done");
		
		
		
		//System.out.println(amazonSns.deleteTopic("arn:aws:sns:us-west-2:408428117988:RegistrationApp1"));
		DeleteEndpointRequest deleteEndpointRequest = new DeleteEndpointRequest();
		deleteEndpointRequest.setEndpointArn("arn:aws:sns:us-west-2:408428117988:app%2FGCM%2FRegistrationApp");
		
		amazonSns.deleteEndpoint(deleteEndpointRequest);
	}

	

	@GetMapping(value="/sendToken",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String sendToken(@RequestParam String token) {
		
		CreatePlatformEndpointRequest platformEndpointRequest = new CreatePlatformEndpointRequest();

        platformEndpointRequest.setCustomUserData("sample");
        platformEndpointRequest.setToken(token);
        platformEndpointRequest.setPlatformApplicationArn("arn:aws:sns:us-west-2:408428117988:app/GCM/RegistrationApp");

       // amazonSns.setRegion(Region.getRegion(Regions.US_WEST_2));
        
        //platformEndpointRequest.withPlatformApplicationArn("arn:aws:sns:us-west-2:408428117988:app/GCM/RegistrationApp");
        //platformEndpointRequest.withToken(token)

        // this will create new endpointARN and new subscription
        /*CreatePlatformEndpointResult result = AmazonSNSClientBuilder.standard().withCredentials(aWSCredentialsProvider)
                .withRegion(Regions.US_WEST_2)
                .build().createPlatformEndpoint(platformEndpointRequest);
        */
        
        
        // to publish for the abover created endpoint
        PublishRequest publishRequest = new PublishRequest();
        
        publishRequest.setTargetArn("arn:aws:sns:us-west-2:408428117988:endpoint/GCM/RegistrationApp/a59bc51a-a653-39c0-93f2-f75c221314af");
        publishRequest.setMessage("from app26");
        
        publishRequest.setMessageStructure("json");
        //publishRequest.setMessage(json payload);
        
       // PublishResult res = amazonSns.publish(publishRequest);
        
        //creating topic
        
        //CreateTopicResult res = amazonSns.createTopic("RegistrationApp1");
        
        //subscribing to above topic for the application arn (this is created above (hardcoding))
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setEndpoint("arn:aws:sns:us-west-2:408428117988:endpoint/GCM/RegistrationApp/a59bc51a-a653-39c0-93f2-f75c221314af");
        subscribeRequest.setProtocol("application");
       // subscribeRequest.setTopicArn(res.getTopicArn());
        
        //amazonSns.subscribe(subscribeRequest);
        
        // publishing to a topic
        PublishResult res = amazonSns.publish("arn:aws:sns:us-west-2:408428117988:RegistrationApp1", "topic created program test");
        
        
        
		//return result.getEndpointArn();
        System.out.println(res);
        return "OK";
	}
}
