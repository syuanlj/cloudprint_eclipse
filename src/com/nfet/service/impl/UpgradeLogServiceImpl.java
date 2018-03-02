/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.dao.UpgradeLogDao;
import com.nfet.entity.Product;
import com.nfet.entity.UpgradeLog;
import com.nfet.service.UpgradeLogService;

/**
 * Service - 升级履历
 * 
 * 
 * 
 */
@Service("upgradeLogServiceImpl")
public class UpgradeLogServiceImpl extends BaseServiceImpl<UpgradeLog, Long> implements UpgradeLogService {

	@Resource(name = "upgradeLogDaoImpl")
	private UpgradeLogDao upgradeLogDao;

	@Resource(name = "upgradeLogDaoImpl")
	public void setBaseDao(UpgradeLogDao upgradeLogDao) {
		super.setBaseDao(upgradeLogDao);
	}

	@Transactional(readOnly = true)
	public Page<UpgradeLog> findPage(Product product, Pageable pageable) {
		return upgradeLogDao.findPage(product, pageable);
	}
}