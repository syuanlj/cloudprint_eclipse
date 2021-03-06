/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.nfet.dao.AreaDao;
import com.nfet.entity.Area;

/**
 * Dao - 地区
 * 
 * 
 * 
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public Area findByFullName(String fullName) {
		if (fullName == null) {
			return null;
		}
		String jpql = "select area from Area area where lower(area.fullName) = lower(:fullName)";
		try {
			return entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("fullName", fullName).getSingleResult();
		} catch (NoResultException e) {
			
			return null;
		}
	}

}