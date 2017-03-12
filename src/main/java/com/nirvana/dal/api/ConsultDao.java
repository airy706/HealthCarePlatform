package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Consult;

/**
 * 
 * @author Bin 咨询数据库访问层
 */
@Repository
public interface ConsultDao extends JpaRepository<Consult, Integer> {

	/**
	 * 查询用户的所有未完成的咨询服务
	 * @param userid 用户id
	 * @param pageable 分页
	 * @return 返回咨询分页
	 */
	@Query("SELECT c FROM Consult c WHERE c.user.userid=:userid AND c.isfinish=0")
	Page<Consult> findUndoByUid(@Param("userid") Integer userid, Pageable pageable);

	/**
	 * 查询所有已经完成的咨询服务 
	 * @param userid 用户id
	 * @param pageable 分页
	 * @return 咨询分页
	 */
	@Query("SELECT c FROM Consult c WHERE c.user.userid=:userid AND c.isfinish=1")
	Page<Consult> findDoneByUid(@Param("userid") Integer userid, Pageable pageable);

	/**
	 * 根据搜索值模糊查询用户名字 查找符合的咨询服务 按照时间降续排列
	 * @param communityid 社区id
	 * @param key 搜索值
	 * @param pageable 分页
	 * @return 咨询服务页
	 */
	@Query("SELECT c FROM Consult c WHERE c.user.community.communityid=:communityid AND c.user.username LIKE %:key% ORDER BY c.committime")
	Page<Consult> findPageByKeyAndCid(@Param("communityid") Integer communityid, @Param("key") String key,
			Pageable pageable);

	@Query("SELECT c FROM Consult c WHERE c.user.community.communityid=:communityid AND c.user.username LIKE %:key% AND c.isfinish=0 ORDER BY c.committime")
	Page<Consult> findPageByKeyAndCidfinish(@Param("communityid") Integer communityid, @Param("key") String key,
			Pageable pageable);

}
