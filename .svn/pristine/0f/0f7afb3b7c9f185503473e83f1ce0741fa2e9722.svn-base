/*
 * 
 * 
 * 
 */
package com.nfet.service;

import java.util.List;

import com.nfet.Filter;
import com.nfet.Order;
import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.entity.Area;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.entity.Shop;

/**
 * Service - 商品
 * 
 * 
 * 
 */
public interface ProductService extends BaseService<Product, Long> {

	/**
	 * 判断商品编号是否存在
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品编号是否存在
	 */
	boolean snExists(String sn);

	/**
	 * 根据商品编号查找商品
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品，若不存在则返回null
	 */
	Product findBySn(String sn);

	/**
	 * 根据商品编号查找商品
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品，若不存在则返回null
	 */
	Product findBySnForUpdate(String sn);

	/**
	 * 根据商品编号查找商品
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品，若不存在则返回null
	 */

	List<Product> findList(String sn);

	/**
	 * 判断商品编号是否唯一
	 * 
	 * @param previousSn
	 *            修改前商品编号(忽略大小写)
	 * @param currentSn
	 *            当前商品编号(忽略大小写)
	 * @return 商品编号是否唯一
	 */
	boolean snUnique(String previousSn, String currentSn);

	/**
	 * 查找商品
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param shop
	 *            品牌
	 * @param isMarketable
	 *            是否运营
	 * @param isList
	 *            是否列出
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 商品
	 */
	List<Product> findList(ProductCategory productCategory, Shop shop, Boolean isMarketable, Boolean isList, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找商品分页
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param shop
	 *            品牌
	 * @param isMarketable
	 *            是否运营
	 * @param isList
	 *            是否列出
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Product> findPage(ProductCategory productCategory, Shop shop, Boolean isMarketable, Boolean isList, Boolean isOnline, Byte status, Area area, Pageable pageable);

	/**
	 * 查找商品分页
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param includeProducts
	 *            指定打印机
	 * @param excludeProducts
	 *            排除打印机
	 * @param isLatest
	 *            是否最新版本
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Product> findPage(ProductCategory productCategory, List<Product> includeProducts, List<Product> excludeProducts, Boolean isLatest, Pageable pageable);

	/**
	 * 查询商品数量
	 * 
	 * @param isMarketable
	 *            是否运营
	 * @param isList
	 *            是否列出
	 * @return 商品数量
	 */
	Long count(ProductCategory productCategory, Boolean isMarketable, Boolean isList, Boolean isOnline, Boolean isNormal);
}