/*
 * 
 * 
 * 
 */
package com.nfet.dao;

import java.util.List;

import com.nfet.entity.Area;

/**
 * Dao - 地区
 * 
 * 
 * 
 */
public interface AreaDao extends BaseDao<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

	/**
	 * 根据全程查找地区
	 * 
	 * @param fullName
	 *            全程
	 * @return 地区，若不存在则返回null
	 */
	Area findByFullName(String fullName);
}