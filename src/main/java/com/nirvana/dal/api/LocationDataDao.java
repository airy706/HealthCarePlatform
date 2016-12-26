package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.LocationData;

@Repository
public interface LocationDataDao extends JpaRepository<LocationData, Integer> {

}
