package com.nirvana.dal.api;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.AlarmData;
/**
 * 
 * @author Bin
 * 报警数据 数据访问层
 */
@Repository
public interface AlarmDataDao extends JpaRepository<AlarmData, Integer> {

	/**
	 * 查询最新的未解决报警数据 根据报警类型 以及身份证
	 * @param reasontype
	 * @param did
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT a FROM AlarmData a WHERE a.reasontype=:reasontype AND a.did=:did AND a.hasresloved=0 ORDER BY status_change_time DESC")
	Page<AlarmData> findLatest(@Param("reasontype") Integer reasontype, @Param("did") String did, Pageable pageable);

	/**
	 * 查询所有未解决的报警
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.hasresloved=0 ORDER BY status_change_time DESC")
	List<AlarmData> findUnresloved();

	/**
	 * 查询一定时间点之后所有未解决的报警数据
	 * @param time
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.status_change_time>:time AND a.hasresloved=0 ORDER BY status_change_time DESC")
	List<AlarmData> findAfter(@Param("time") Date time);

	/**
	 * 查询所有报警数据可能的不同原因
	 * @return
	 */
	@Query("SELECT DISTINCT a.reasontype from AlarmData a")
	List<Integer> findAlltype();

	/**
	 * 查询相应报警类型的数据数目
	 * @param alarmType
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM AlarmData a WHERE a.reasontype=:type")
	Integer findTypeTimes(@Param("type") Integer alarmType);

	/**
	 * 查询一定时间段内 所有制定类型所有制定人员的报警数据
	 * @param types
	 * @param dids
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.did in :dids AND a.status_change_time>=:start AND a.status_change_time<=:end AND a.reasontype in :types ORDER BY status_change_time")
	List<AlarmData> findFilter(@Param("types") List<Integer> types, @Param("dids") List<String> dids,
			@Param("start") Date start, @Param("end") Date end);

	/**
	 * 查询所有的报警数据按照时间降续排列
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a ORDER BY status_change_time DESC")
	List<AlarmData> findAllDESC();

	/**
	 * 查询社区类所有未解决的异常
	 * @param dids
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.hasresloved=0 AND a.did IN :dids ORDER BY status_change_time DESC")
	List<AlarmData> findUnreslovedByCid(@Param("dids") List<String> dids);

	/**
	 * 查询一定时间点之后社区内未解决的异常
	 * @param time
	 * @param dids
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.status_change_time>:time AND a.did IN :dids AND a.hasresloved=0 ORDER BY status_change_time DESC")
	List<AlarmData> findAfterByCid(@Param("time") Date time, @Param("dids") List<String> dids);

	/**
	 * 查询某个用户未解决的异常
	 * @param did
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.did=:did AND a.hasresloved=0 ORDER BY a.status_change_time DESC")
	List<AlarmData> findUndoByDid(@Param("did") String did);

	/**
	 * 查询一定时间段内某个社区内某些用户 的某些类型的异常数据
	 * @param typesint
	 * @param useridentity
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT a FROM AlarmData a WHERE a.did=:did AND a.status_change_time>=:start AND a.status_change_time<=:end AND a.reasontype in :types ORDER BY status_change_time")
	List<AlarmData> findPeopleFilter(@Param("types") List<Integer> typesint, @Param("did") String useridentity,
			@Param("start") Date start, @Param("end") Date end);

	/**
	 * 查询某个用户的所有异常
	 * @param useridentity
	 * @return
	 */
	@Query(value="SELECT a FROM AlarmData a WHERE a.did=:did")
	List<AlarmData> findByDid(@Param("did") String useridentity);

}
