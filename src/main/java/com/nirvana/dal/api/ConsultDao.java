package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Consult;

@Repository
public interface ConsultDao extends JpaRepository<Consult, Integer> {

	@Query("SELECT c FROM Consult c WHERE c.user.userid=:userid AND c.isfinish=0")
	List<Consult> findUndoByUid(@Param("userid") Integer userid);

	@Query("SELECT c FROM Consult c WHERE c.user.userid=:userid AND c.isfinish=1")
	List<Consult> findDoneByUid(@Param("userid") Integer userid);

}
