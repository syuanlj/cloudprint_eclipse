/*
 * 
 * 
 * 
 */
package com.nfet.dao;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.entity.Product;
import com.nfet.entity.UpgradeLog;

/**
 * Dao - 升级履历
 * 
 * 
 * 
 */
public interface UpgradeLogDao extends BaseDao<UpgradeLog, Long> {

	Page<UpgradeLog> findPage(Product product, Pageable pageable);
}