package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.LocationDataSerivce;
import com.nirvana.dal.api.LocationDataDao;
import com.nirvana.dal.po.LocationData;

@Service
@Transactional
public class LocationDataServiceBO implements LocationDataSerivce {
	@Autowired
	private LocationDataDao locationdatadao;

	@Override
	public void addData(LocationData data) {
		locationdatadao.save(data);
	}
}
