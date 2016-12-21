package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.NodeData;

@Repository
public interface NodeDataDao extends JpaRepository<NodeData, Integer>{

}
