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
/**
 * 
 * @author Bin
 * 节点数据书库访问层
 */
@Repository
public interface NodeDataDao extends JpaRepository<NodeData, Integer> {

	/**
	 * 查询所属用户一种类型传感器的数据 按时间降续排列
	 * @param did 身份证
	 * @param type 节点类型
	 * @param pageable 分页
	 * @return 返回节点数据页
	 */
	@Query(value = "SELECT n FROM NodeData n WHERE n.did=:did AND n.sensortype=:type ORDER BY n.status_change_time DESC")
	Page<NodeData> findLatestByDidAndType(@Param("did") String did, @Param("type") Integer type, Pageable pageable);

	/**
	 * 查询所属用户 一种类型数据的所有数据
	 * @param did 身份证
	 * @param type 节点类型
	 * @param start 开始时间
	 * @param end 截止时间
	 * @return 节点数据集合
	 */
	@Query("SELECT n FROM NodeData n WHERE n.did=:did AND n.sensortype=:type AND n.status_change_time>=:start AND n.status_change_time<=:end ORDER BY n.status_change_time")
	List<NodeData> findByDidAndTypeinWeek(@Param("did") String did, @Param("type") Integer type,
			@Param("start") Date start, @Param("end") Date end);

	/**
	 * 根据身份证查询所有节点数据
	 * @param useridentity
	 * @return 节点数据集合
	 */
	@Query(value="SELECT n FROM NodeData n WHERE n.did=:did")
	List<NodeData> findByDid(@Param("did") String useridentity);

}
