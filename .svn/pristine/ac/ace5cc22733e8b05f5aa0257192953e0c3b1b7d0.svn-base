/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.nfet.dao.AdminDao;
import com.nfet.entity.User;

/**
 * Dao - 管理员
 * 
 * 
 * 
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<User, Long> implements AdminDao {

	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from Admin admin where lower(admin.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}

	public User findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select admin from User admin where lower(admin.username) = lower(:username)";
			return entityManager.createQuery(jpql, User.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}