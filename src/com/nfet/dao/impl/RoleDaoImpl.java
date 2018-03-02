/*
 * 
 * 
 * 
 */
package com.nfet.dao.impl;

import org.springframework.stereotype.Repository;

import com.nfet.dao.RoleDao;
import com.nfet.entity.Role;

/**
 * Dao - 角色
 * 
 * 
 * 
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}