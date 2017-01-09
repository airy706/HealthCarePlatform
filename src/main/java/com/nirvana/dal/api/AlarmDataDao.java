package com.nirvana.dal.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.AlarmData;
@Repository
public interface AlarmDataDao extends JpaRepository<AlarmData, Integer>{

	@Query(value="SELECT a FROM AlarmData a WHERE a.reasontype=:reasontype AND a.did=:did ORDER BY status_change_time DESC")
	Page<AlarmData> findLatest(@Param("reasontype") Integer reasontype,@Param("did") String did,Pageable pageable);
	
	@Query("SELECT a FROM AlarmData a WHERE a.hasresloved=0")
	List<AlarmData> findUnresloved();
	
	@Query("SELECT a FROM AlarmData a WHERE a.status_change_time>:time")
	List<AlarmData> findAfter(@Param("time") Date time);

	@Query("SELECT DISTINCT a.reasontype from AlarmData a")
	List<Integer> findAlltype();

	@Query("SELECT COUNT(*) FROM AlarmData a WHERE a.reasontype=:type")
	Integer findTypeTimes(@Param("type") Integer alarmType);

	@Query("SELECT a FROM AlarmData a WHERE a.did in :dids AND a.status_change_time>=:start AND a.status_change_time<=:end AND a.reasontype in :types ORDER BY status_change_time")
	List<AlarmData> findFilter(@Param("types") List<Integer> types,@Param("dids") List<String> dids,@Param("start") Date start,@Param("end") Date end);

	@Query("SELECT a FROM AlarmData a ORDER BY status_change_time DESC")
	List<AlarmData> findAllDESC();

}
