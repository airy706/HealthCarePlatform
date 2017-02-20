package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.LocationData;

@Repository
public interface LocationDataDao extends JpaRepository<LocationData, Integer> {

	
	@Query(value="SELECT l FROM LocationData l WHERE l.did=:did")
	List<LocationData> findByDid(@Param("did") String useridentity);

}
