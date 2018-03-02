/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.dao.OrderDao;
import com.nfet.entity.Order;
import com.nfet.entity.Product;

/**
 * Dao - 订单
 * 
 * 
 * 
 */
@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

	public Order findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select orders from Order orders where orders.sn = :sn";
		try {
			return entityManager.createQuery(jpql, Order.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Order findBySnForUpdate(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select orders from Order orders where orders.sn = :sn";
		try {
			return entityManager.createQuery(jpql, Order.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Order> findList(Product product, Order.OrderStatus orderStatus, Boolean hasExpired, List<com.nfet.Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull());
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, orders);
	}

	public List<Order> findList(Product product, Order.OrderStatus orderStatus, Date beginDate, Date endDate, Boolean hasExpired, List<com.nfet.Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (beginDate != null) {
			if (Order.OrderStatus.printed.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("finish"), beginDate));
			} else if (Order.OrderStatus.sent.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("lastPush"), endDate));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("receive"), beginDate));
			}
		}
		if (endDate != null) {
			if (Order.OrderStatus.printed.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("finish"), endDate));
			} else if (Order.OrderStatus.sent.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("lastPush"), endDate));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("receive"), endDate));
			}
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull());
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, orders);
	}

	public Page<Order> findPage(Product product, Order.OrderStatus orderStatus, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull());
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Order> findPage(List<Product> products, Order.OrderStatus orderStatus, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (products != null && !products.isEmpty()) {
			restrictions = criteriaBuilder.or(criteriaBuilder.equal(root.get("product"), products.get(0)));
			for (int i = 1; i < products.size(); i++) {
				restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.equal(root.get("product"), products.get(i)));
			}
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull());
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Order.OrderStatus orderStatus, Boolean hasExpired) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull());
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public Long count(Product product, Order.OrderStatus orderStatus, Date beginDate, Date endDate, Boolean hasExpired) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (beginDate != null) {
			if (Order.OrderStatus.printed.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("finish"), beginDate));
			} else if (Order.OrderStatus.sent.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("lastPush"), endDate));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("receive"), beginDate));
			}
		}
		if (endDate != null) {
			if (Order.OrderStatus.printed.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("finish"), endDate));
			} else if (Order.OrderStatus.sent.equals(orderStatus)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("lastPush"), endDate));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("receive"), endDate));
			}
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull());
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public List<Order> findUnprintedOrderList() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("orderStatus"), Order.OrderStatus.received), criteriaBuilder.equal(root.get("orderStatus"), Order.OrderStatus.sent)));
		restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNull());
		criteriaQuery.where(restrictions);

		return super.findList(criteriaQuery, null, null, null, null);
	}

	public int batchExpire(Long interval) {
		String jpql = "update Order orders set orders.expire = now() where expire is null and TIMESTAMPDIFF(SECOND, createDate, now()) > :interval and orderStatus <> 2 ";
		return entityManager.createQuery(jpql).setParameter("interval", interval).executeUpdate();
	}
}