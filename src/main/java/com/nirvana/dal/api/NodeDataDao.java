package com.nirvana.dal.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.NodeData;

@Repository
public interface NodeDataDao extends JpaRepository<NodeData, Integer> {

	@Query(value = "SELECT n FROM NodeData n WHERE n.did=:did AND n.sensortype=:type ORDER BY n.status_change_time DESC")
	Page<NodeData> findLatestByDidAndType(@Param("did") String did, @Param("type") Integer type, Pageable pageable);

	@Query("SELECT n FROM NodeData n WHERE n.did=:did AND n.sensortype=:type AND n.status_change_time>=:start AND n.status_change_time<=:end")
	List<NodeData> findByDidAndTypeinWeek(@Param("did") String did, @Param("type") Integer type,
			@Param("start") Date start, @Param("end") Date end);
}
