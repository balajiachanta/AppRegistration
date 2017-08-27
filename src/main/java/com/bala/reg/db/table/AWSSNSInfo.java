package com.bala.reg.db.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aws_info")
public class AWSSNSInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long awsId;
	public Long getAwsId() {
		return awsId;
	}
	public void setAwsId(Long awsId) {
		this.awsId = awsId;
	}
	private String devId;
	private String platforEndPointArn;
	private String topicArn;
	private String subscriberArn;
	

	
	public String getPlatforEndPointArn() {
		return platforEndPointArn;
	}
	public void setPlatforEndPointArn(String platforEndPointArn) {
		this.platforEndPointArn = platforEndPointArn;
	}
	public String getTopicArn() {
		return topicArn;
	}
	public void setTopicArn(String topicArn) {
		this.topicArn = topicArn;
	}
	public String getSubscriberArn() {
		return subscriberArn;
	}
	public void setSubscriberArn(String subscriberArn) {
		this.subscriberArn = subscriberArn;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
}
