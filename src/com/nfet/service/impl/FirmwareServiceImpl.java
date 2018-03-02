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

import com.nfet.Order;
import com.nfet.dao.FirmwareDao;
import com.nfet.entity.Firmware;
import com.nfet.service.FirmwareService;

/**
 * Service - 品牌
 * 
 * 
 * 
 */
@Service("firmwareServiceImpl")
public class FirmwareServiceImpl extends BaseServiceImpl<Firmware, Long> implements FirmwareService {

	@Resource(name = "firmwareDaoImpl")
	private FirmwareDao firmwareDao;

	@Resource(name = "firmwareDaoImpl")
	public void setBaseDao(FirmwareDao firmwareDao) {
		super.setBaseDao(firmwareDao);
	}
	@Transactional(readOnly = true)
	public List<Firmware> findList(Firmware.Type type, Boolean isCompleted, List<Order> orders) {
		return firmwareDao.findList(type, isCompleted, orders);
	}

}