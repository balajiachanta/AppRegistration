package com.bala.reg.db.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bala.reg.db.table.DeviceInfo;

@Profile(value="local")
@Repository
public interface DeviceInfoRepo extends CrudRepository<DeviceInfo, Long> {

	
}
