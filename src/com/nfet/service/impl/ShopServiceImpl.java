/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nfet.dao.ShopDao;
import com.nfet.entity.Shop;
import com.nfet.service.ShopService;

/**
 * Service - 品牌
 * 
 * 
 * 
 */
@Service("shopServiceImpl")
public class ShopServiceImpl extends BaseServiceImpl<Shop, Long> implements ShopService {

	@Resource(name = "shopDaoImpl")
	public void setBaseDao(ShopDao shopDao) {
		super.setBaseDao(shopDao);
	}
}