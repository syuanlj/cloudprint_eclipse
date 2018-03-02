/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nfet.Template;
import com.nfet.dao.TemplateDao;
import com.nfet.service.TemplateService;

/**
 * Service - 模板
 * 
 * 
 * 
 */
@Service("templateServiceImpl")
public class TemplateServiceImpl extends BaseServiceImpl<Template, Long> implements TemplateService {

	@Resource(name = "templateDaoImpl")
	public void setBaseDao(TemplateDao templateDao) {
		super.setBaseDao(templateDao);
	}
}