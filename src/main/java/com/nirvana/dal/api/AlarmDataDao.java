package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.AlarmData;
@Repository
public interface AlarmDataDao extends JpaRepository<AlarmData, Integer>{

}
