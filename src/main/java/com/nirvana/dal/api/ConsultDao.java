package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Consult;

@Repository
public interface ConsultDao extends JpaRepository<Consult, Integer> {

	@Query("SELECT c FROM Consult c WHERE c.user.userid=:userid AND c.isfinish=0")
	Page<Consult> findUndoByUid(@Param("userid") Integer userid,Pageable pageable);

	@Query("SELECT c FROM Consult c WHERE c.user.userid=:userid AND c.isfinish=1")
	Page<Consult> findDoneByUid(@Param("userid") Integer userid,Pageable pageable);
	
	@Query("SELECT c FROM Consult c WHERE c.user.community.communityid=:communityid AND c.user.username LIKE %:key% ORDER BY c.committime")
	Page<Consult> findPageByKeyAndCid(@Param("communityid") Integer communityid,@Param("key") String key,Pageable pageable);

}
