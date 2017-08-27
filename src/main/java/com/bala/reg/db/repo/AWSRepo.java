package com.bala.reg.db.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bala.reg.db.table.AWSSNSInfo;

@Profile(value="local")
@Repository
public interface AWSRepo extends CrudRepository<AWSSNSInfo, Long> {

	AWSSNSInfo findByDevId(String devid);
}
