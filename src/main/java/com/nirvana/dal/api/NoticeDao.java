package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Notice;

@Repository
public interface NoticeDao extends JpaRepository<Notice, Integer>{

}
