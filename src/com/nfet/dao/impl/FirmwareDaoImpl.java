/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nfet.Order;
import com.nfet.dao.FirmwareDao;
import com.nfet.entity.Firmware;
import com.nfet.entity.Firmware.Type;

/**
 * Dao - 品牌
 * 
 * 
 * 
 */
@Repository("firmwareDaoImpl")
public class FirmwareDaoImpl extends BaseDaoImpl<Firmware, Long> implements FirmwareDao {

	@Override
	public List<Firmware> findList(Type type, Boolean isCompleted, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Firmware> criteriaQuery = criteriaBuilder.createQuery(Firmware.class);
		Root<Firmware> root = criteriaQuery.from(Firmware.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (isCompleted != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isCompleted"), isCompleted));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, orders);
	}
}