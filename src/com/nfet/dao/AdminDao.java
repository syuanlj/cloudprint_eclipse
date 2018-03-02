/*
 * 
 * 
 * 
 */
package com.nfet.dao;

import com.nfet.entity.User;

/**
 * Dao - 管理员
 * 
 * 
 * 
 */
public interface AdminDao extends BaseDao<User, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	User findByUsername(String username);

}