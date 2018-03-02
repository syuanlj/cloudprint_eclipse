/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nfet.dao.RoleDao;
import com.nfet.entity.Role;
import com.nfet.service.RoleService;

/**
 * Service - 角色
 * 
 * 
 * 
 */
@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

	@Resource(name = "roleDaoImpl")
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}
}