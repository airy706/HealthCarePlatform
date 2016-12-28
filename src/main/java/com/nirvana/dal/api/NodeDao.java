package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Node;

@Repository
public interface NodeDao extends JpaRepository<Node, String>{

}
