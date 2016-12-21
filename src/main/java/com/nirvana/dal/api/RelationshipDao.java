package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Relationship;

@Repository
public interface RelationshipDao extends JpaRepository<Relationship, Integer>{

}
