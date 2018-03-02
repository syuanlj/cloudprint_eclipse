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
 * Entity - 用户第三方应用店铺
 * 
 * 
 * 
 */
@Entity
@Table(name = "clouldprint_user_app_shop")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_user_app_shop_sequence")
public class UserAppShop extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8915076795563835192L;

	/** 用户id */
	private User user;
	
	/** 应用id */
	private ThirdApp app;
	
	private String accessId;
	private String accessToken;
	private Date createDate;
	private Date accessTokenExpired;
	private String shopOpenId;
	private String shopName;
	private Date modifyDate;
	
	/**
	 * 获取用户
	 * 
	 * @return 用户
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param username
	 *            用户
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * 获取用户第三方应用
	 * 
	 * @return 第三方应用
	 */
	public ThirdApp getApp() {
		return app;
	}

	/**
	 * 设置用户
	 * 
	 * @param app
	 *            第三方应用
	 */
	public void setApp(ThirdApp app) {
		this.app = app;
	}

	
	

}
