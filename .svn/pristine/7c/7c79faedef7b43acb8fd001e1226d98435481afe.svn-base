/*
 * 
 * 
 * 
 */
package com.nfet.service;

import java.util.Date;
import java.util.List;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.entity.Order.OrderStatus;

/**
 * Service - 订单
 * 
 * 
 * 
 */
public interface OrderService extends BaseService<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Order findBySnForUpdate(String sn);

	/**
	 * 查找订单列表
	 * 
	 * @param product
	 *            产品
	 * @param orderStatus
	 *            订单状态
	 * @param hasExpired
	 *            是否已过期
	 * @param orders
	 *            排序
	 * @return 订单分页
	 */
	List<Order> findList(Product product, OrderStatus orderStatus, Boolean hasExpired, List<com.nfet.Order> orders);

	/**
	 * 查找订单列表
	 * 
	 * @param product
	 *            产品
	 * @param orderStatus
	 *            订单状态
	 * @param hasExpired
	 *            是否已过期
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param orders
	 *            排序
	 * @return 订单分页
	 */
	List<Order> findList(Product product, OrderStatus orderStatus, Date beginDate, Date endDate, Boolean hasExpired, List<com.nfet.Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param product
	 *            产品
	 * @param orderStatus
	 *            订单状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Order> findPage(Product product, OrderStatus orderStatus, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找订单分页
	 * 
	 * @param products
	 *            产品
	 * @param orderStatus
	 *            订单状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Order> findPage(List<Product> products, OrderStatus orderStatus, Boolean hasExpired, Pageable pageable);

	/**
	 * 查询订单数量
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(OrderStatus orderStatus, Boolean hasExpired);

	Long count(Product product, Order.OrderStatus orderStatus, Date beginDate, Date endDate, Boolean hasExpired);

	List<Order> findUnprintedOrderList();

	int batchExpire(Long interval);
}