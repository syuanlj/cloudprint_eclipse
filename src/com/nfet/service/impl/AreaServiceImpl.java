/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nfet.dao.AreaDao;
import com.nfet.entity.Area;
import com.nfet.service.AreaService;

/**
 * Service - 地区
 * 
 * 
 * 
 */
@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

	@Resource(name = "areaDaoImpl")
	private AreaDao areaDao;

	@Resource(name = "areaDaoImpl")
	public void setBaseDao(AreaDao areaDao) {
		super.setBaseDao(areaDao);
	}

	@Transactional(readOnly = true)
	public List<Area> findRoots() {
		return areaDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<Area> findRoots(Integer count) {
		return areaDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public Area findByFullName(String fullName) {
		return areaDao.findByFullName(fullName);
	}
}