package com.nirvana.dal.api;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.NodeData;

@Repository
public interface NodeDataDao extends JpaRepository<NodeData, Integer> {

	@Query(nativeQuery = true, value = "SELECT TOP 1 n.data FROM NodeData n WHERE n.did=:did AND n.sensortype=:type ORDER BY status_change_time DESC")
	String findLatestByDidAndType(@Param("did") String did, @Param("type") Integer type);

}
