/*
 * 
 * 
 * 
 */
package com.nfet.service;

import java.util.List;

import com.nfet.Order;
import com.nfet.entity.Firmware;

/**
 * Service - 品牌
 * 
 * 
 * 
 */
public interface FirmwareService extends BaseService<Firmware, Long> {

	/**
	 * 查找固件
	 * 
	 * @param Type
	 *            类型
	 * @param isCompleted
	 *            是否结束
	 * @param orders
	 *            排序
	 * @return 固件
	 */
	List<Firmware> findList(Firmware.Type type, Boolean isCompleted, List<Order> orders);
}