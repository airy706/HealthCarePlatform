package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.LocationData;

@Repository
public interface LocationDataDao extends JpaRepository<LocationData, Integer> {

	
	@Query(nativeQuery=true,value="DELETE FROM LocationData WHERE did=:did")
	void delByDid(@Param("did") String useridentity);

}
