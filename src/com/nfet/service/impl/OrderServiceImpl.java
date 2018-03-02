/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.dao.OrderDao;
import com.nfet.dao.ProductDao;
import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.entity.Order.OrderStatus;
import com.nfet.service.OrderService;

/**
 * Service - 订单
 * 
 * 
 * 
 */
@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;

	@Resource(name = "orderDaoImpl")
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}

	@Transactional(readOnly = true)
	public Order findBySn(String sn) {
		return orderDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public Order findBySnForUpdate(String sn) {
		return orderDao.findBySnForUpdate(sn);
	}

	@Transactional(readOnly = true)
	public List<Order> findList(Product product, OrderStatus orderStatus, Boolean hasExpired, List<com.nfet.Order> orders) {
		return orderDao.findList(product, orderStatus, hasExpired, orders);
	}

	@Transactional(readOnly = true)
	public List<Order> findList(Product product, OrderStatus orderStatus, Date beginDate, Date endDate, Boolean hasExpired, List<com.nfet.Order> orders) {
		return orderDao.findList(product, orderStatus, beginDate, endDate, hasExpired, orders);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Product product, OrderStatus orderStatus, Boolean hasExpired, Pageable pageable) {
		return orderDao.findPage(product, orderStatus, hasExpired, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(List<Product> products, OrderStatus orderStatus, Boolean hasExpired, Pageable pageable) {
		return orderDao.findPage(products, orderStatus, hasExpired, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(OrderStatus orderStatus, Boolean hasExpired) {
		return orderDao.count(orderStatus, hasExpired);
	}

	@Transactional(readOnly = true)
	public Long count(Product product, Order.OrderStatus orderStatus, Date beginDate, Date endDate, Boolean hasExpired) {
		return orderDao.count(product, orderStatus, beginDate, endDate, hasExpired);
	}

	@Transactional(readOnly = true)
	public List<Order> findUnprintedOrderList() {
		return orderDao.findUnprintedOrderList();
	}

	@Transactional(readOnly = true)
	public int batchExpire(Long interval) {
		return orderDao.batchExpire(interval);
	}
}