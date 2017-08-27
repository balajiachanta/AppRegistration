package com.bala.reg.db.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bala.reg.db.table.ClientInfo;

@Profile(value="local")
@Repository
public interface ClientRepo extends CrudRepository<ClientInfo, Long> {

	ClientInfo findByName(String name);
	
}
