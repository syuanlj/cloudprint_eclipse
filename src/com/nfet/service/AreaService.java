/*
 * 
 * 
 * 
 */
package com.nfet.service;

import java.util.List;

import com.nfet.entity.Area;

/**
 * Service - 地区
 * 
 * 
 * 
 */
public interface AreaService extends BaseService<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @return 顶级地区
	 */
	List<Area> findRoots();

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

	/**
	 * 根据全称查找地区
	 * 
	 * @param 全称
	 *            地区
	 * @return 若不存在则返回null
	 */
	Area findByFullName(String fullName);
}