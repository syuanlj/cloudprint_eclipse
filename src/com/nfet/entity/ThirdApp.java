package com.nfet.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Session;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 用户第三方应用
 * 
 * 
 * 
 */
@Entity
@Table(name = "clouldprint_apps")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_apps_sequence")
public class ThirdApp extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1246265793863678601L;
	
	/** 应用名 */
	private String appName;
	
	/** 应用描述 */
	private String appDescription;
	
	/** 引用logo图片路径 */
	private String appImgUrl;
	
	public String getAppName(){
		return appName;
	}
	
	public void setAppName(String appName){
		this.appName = appName;
	}
	
	public String getAppDescription(){
		return appDescription;
	}
	
	public void setAppDescription(String appDescription){
		this.appDescription = appDescription;
	}
	
	
	public String getAppImgUrl(){
		return appImgUrl;
	}
	
	public void setAppImgUrl(String appImgUrl){
		this.appImgUrl = appImgUrl;
	}
	

}
