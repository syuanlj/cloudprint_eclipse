/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nfet.dao.SnDao;
import com.nfet.entity.Sn.Type;
import com.nfet.service.SnService;

/**
 * Service - 序列号
 * 
 * 
 * 
 */
@Service("snServiceImpl")
public class SnServiceImpl implements SnService {

	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Transactional
	public String generate(Type type) {
		return snDao.generate(type);
	}

}