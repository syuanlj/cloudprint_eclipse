/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.nfet.Filter;
import com.nfet.Order;
import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.dao.ProductDao;
import com.nfet.entity.Area;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.entity.Shop;
import com.nfet.service.ProductService;

/**
 * Service - 商品
 * 
 * 
 * 
 */
@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

	@Resource(name = "productDaoImpl")
	private ProductDao productDao;

	@Resource(name = "productDaoImpl")
	public void setBaseDao(ProductDao productDao) {
		super.setBaseDao(productDao);
	}

	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return productDao.snExists(sn);
	}

	@Transactional(readOnly = true)
	public Product findBySn(String sn) {
		return productDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public Product findBySnForUpdate(String sn) {
		return productDao.findBySnForUpdate(sn);
	}

	@Transactional(readOnly = true)
	public List<Product> findList(String sn) {
		if (sn == null) {
			return null;
		}
		return productDao.findList(sn);
	}

	@Transactional(readOnly = true)
	public boolean snUnique(String previousSn, String currentSn) {
		if (StringUtils.equalsIgnoreCase(previousSn, currentSn)) {
			return true;
		} else {
			if (productDao.snExists(currentSn)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public List<Product> findList(ProductCategory productCategory, Shop shop, Boolean isMarketable, Boolean isList, Integer count, List<Filter> filters, List<Order> orders) {
		return productDao.findList(productCategory, shop, isMarketable, isList, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<Product> findPage(ProductCategory productCategory, Shop shop, Boolean isMarketable, Boolean isList, Boolean isOnline, Byte status, Area area, Pageable pageable) {
		return productDao.findPage(productCategory, shop, isMarketable, isList, isOnline, status, area, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Product> findPage(ProductCategory productCategory, List<Product> includeProducts, List<Product> excludeProducts, Boolean isLatest, Pageable pageable) {
		return productDao.findPage(productCategory, includeProducts, excludeProducts, isLatest, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(ProductCategory productCategory, Boolean isMarketable, Boolean isList, Boolean isOnline, Boolean isNormal) {
		return productDao.count(productCategory, isMarketable, isList, isOnline, isNormal);
	}

	@Override
	@Transactional
	public void save(Product product) {
		Assert.notNull(product);

		super.save(product);
		productDao.flush();
	}

	@Override
	@Transactional
	public Product update(Product product) {
		Assert.notNull(product);

		Product pProduct = super.update(product);
		productDao.flush();
		return pProduct;
	}

}