/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.nfet.Filter;
import com.nfet.Order;
import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.PrintProtocol;
import com.nfet.dao.ProductDao;
import com.nfet.dao.SnDao;
import com.nfet.entity.Area;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.entity.Shop;
import com.nfet.entity.Sn.Type;

/**
 * Dao - 商品
 * 
 * 
 * 
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	public boolean snExists(String sn) {
		if (sn == null) {
			return false;
		}
		String jpql = "select count(*) from Product product where product.sn = :sn";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

	public Product findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select product from Product product where product.sn = :sn";
		try {
			return entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Product findBySnForUpdate(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select product from Product product where product.sn = :sn";
		try {
			return entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Product> findList(String sn) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (sn != null) {
			restrictions = criteriaBuilder.and(criteriaBuilder.like(root.<String> get("sn"), "%" + sn + "%"));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	public List<Product> findList(ProductCategory productCategory, Shop shop, Boolean isMarketable, Boolean isList, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (shop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shop"), shop));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<Product> findPage(ProductCategory productCategory, Shop shop, Boolean isMarketable, Boolean isList, Boolean isOnline, Byte status, Area area, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (shop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shop"), shop));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isOnline != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isOnline"), isOnline));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (area != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("area"), area), criteriaBuilder.like(root.get("area").<String> get("treePath"), "%" + Area.TREE_PATH_SEPARATOR + area.getId() + Area.TREE_PATH_SEPARATOR + "%")));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Product> findPage(ProductCategory productCategory, List<Product> includeProducts, List<Product> excludeProducts, Boolean isLatest, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (includeProducts != null && !includeProducts.isEmpty()) {
			restrictions = criteriaBuilder.or(criteriaBuilder.equal(root.get("sn"), includeProducts.get(0).getSn()));
			for (int i = 1; i < includeProducts.size(); i++) {
				restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.equal(root.get("sn"), includeProducts.get(i).getSn()));
			}
		}
		if (excludeProducts != null && !excludeProducts.isEmpty()) {
			restrictions = criteriaBuilder.and(criteriaBuilder.notEqual(root.get("sn"), excludeProducts.get(0).getSn()));
			for (int i = 1; i < excludeProducts.size(); i++) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get("sn"), excludeProducts.get(i).getSn()));
			}
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (isLatest != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isLatest"), isLatest));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(ProductCategory productCategory, Boolean isMarketable, Boolean isList, Boolean isOnline, Boolean isNormal) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isOnline != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isOnline"), isOnline));
		}
		if (isNormal != null) {
			if (isNormal) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get("status"), PrintProtocol.PRINTER_STATUS_ABNORMAL));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), PrintProtocol.PRINTER_STATUS_ABNORMAL));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	/**
	 * 设置值并保存
	 * 
	 * @param product
	 *            商品
	 */
	@Override
	public void persist(Product product) {
		Assert.notNull(product);

		setValue(product);
		super.persist(product);
	}

	/**
	 * 设置值并更新
	 * 
	 * @param product
	 *            商品
	 * @return 商品
	 */
	@Override
	public Product merge(Product product) {
		Assert.notNull(product);

		setValue(product);
		return super.merge(product);
	}

	/**
	 * 设置值
	 * 
	 * @param product
	 *            商品
	 */
	private void setValue(Product product) {
		if (product == null) {
			return;
		}
		if (StringUtils.isEmpty(product.getSn())) {
			String sn;
			do {
				sn = snDao.generate(Type.product);
			} while (snExists(sn));
			product.setSn(sn);
		}
	}

}