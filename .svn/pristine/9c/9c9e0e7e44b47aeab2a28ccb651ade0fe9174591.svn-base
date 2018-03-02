/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.dao.UpgradeLogDao;
import com.nfet.entity.Product;
import com.nfet.entity.UpgradeLog;

/**
 * Dao - 升级履历
 * 
 * 
 * 
 */
@Repository("upgradeLogDaoImpl")
public class UpgradeLogDaoImpl extends BaseDaoImpl<UpgradeLog, Long> implements UpgradeLogDao {

	public Page<UpgradeLog> findPage(Product product, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UpgradeLog> criteriaQuery = criteriaBuilder.createQuery(UpgradeLog.class);
		Root<UpgradeLog> root = criteriaQuery.from(UpgradeLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}