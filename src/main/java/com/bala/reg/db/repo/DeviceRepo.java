package com.bala.reg.db.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bala.reg.db.table.DeiviceRegistration;

@Profile(value="local")
@Repository
public interface DeviceRepo extends CrudRepository<DeiviceRegistration, Long> {

	@Query(value="select d from DeiviceRegistration d where d.number=?1")
	DeiviceRegistration findByNumber(String number);
}
