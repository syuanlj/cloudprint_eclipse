/*
 * 
 * 
 * 
 */
package com.nfet.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 商品
 * 
 * 
 * 
 */
@Entity
@Table(name = "cloudprint_product")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_product_sequence")
public class Product extends BaseEntity {

	private static final long serialVersionUID = 2167830430439593293L;

	/**
	 * 工作状态
	 */
	public enum WorkStatus {

		normal,

		abnormal,

		busy
	}

	/** 编号 */
	private String sn;

	/** 版本 */
	private String bootVersion;

	/** 版本 */
	private String softwareVersion;

	/** 版本 */
	private String hardwareVersion;

	/** 版本 */
	private String fontVersion;

	/** 版本 */
	private String oemSn;

	/** 版本 */
	private String voltage;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 用户名 */
	private String mqttUsername;

	/** 密码 */
	private String mqttPassword;

	/** 是否运营 */
	private Boolean isMarketable;

	/** 是否列出 */
	private Boolean isList;

	/** 是否在线 */
	private Boolean isOnline;

	/** 商品分类 */
	private ProductCategory productCategory;

	/** 制造时间 */
	private Date manufacture;

	/** 店铺 */
	private Shop shop;

	/** 地区 */
	private Area area;

	/** 经度 */
	private String latitude;

	/** 维度 */
	private String longitude;

	/** 代码 */
	private Byte status;

	/** 异常码 */
	private String error;

	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();

	/** 升级日志 */
	private Set<UpgradeLog> upgradeLogs = new HashSet<UpgradeLog>();

	/** 是否最新版本 */
	private Boolean isLatest;
	
	/** 产品名称 */
	private String productName;
	
	/** 产品手机卡号 */
	private String productCardno;

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Length(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	// /**
	// * 获取在线状态
	// *
	// * @return 在线状态
	// */
	// @Transient
	// public Boolean getIsOnline() {
	// String productSn = getSn();
	// if (PushManager.getInstance().isConnected(productSn)) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	/**
	 * 获取版本
	 * 
	 * @return bootVersion
	 */
	@Length(max = 200)
	public String getBootVersion() {
		return bootVersion;
	}

	/**
	 * 设置版本
	 * 
	 * @param bootVersion
	 *            版本
	 */
	public void setBootVersion(String bootVersion) {
		this.bootVersion = bootVersion;
	}

	/**
	 * 获取版本
	 * 
	 * @return softwareVersion
	 */
	@Length(max = 200)
	public String getSoftwareVersion() {
		return softwareVersion;
	}

	/**
	 * 设置版本
	 * 
	 * @param softwareVersion
	 *            版本
	 */
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	/**
	 * 获取版本
	 * 
	 * @return hardwareVersion
	 */
	@Length(max = 200)
	public String getHardwareVersion() {
		return hardwareVersion;
	}

	/**
	 * 设置版本
	 * 
	 * @param hardwareVersion
	 *            版本
	 */
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	/**
	 * 获取版本
	 * 
	 * @return fontVersion
	 */
	@Length(max = 200)
	public String getFontVersion() {
		return fontVersion;
	}

	/**
	 * 设置版本
	 * 
	 * @param fontVersion
	 *            版本
	 */
	public void setFontVersion(String fontVersion) {
		this.fontVersion = fontVersion;
	}

	/**
	 * 获取版本
	 * 
	 * @return oemSn
	 */
	@Length(max = 200)
	public String getOemSn() {
		return oemSn;
	}

	/**
	 * 设置版本
	 * 
	 * @param oemSn
	 *            版本
	 */
	public void setOemSn(String oemSn) {
		this.oemSn = oemSn;
	}

	/**
	 * 获取版本
	 * 
	 * @return voltage
	 */
	@Length(max = 200)
	public String getVoltage() {
		return voltage;
	}

	/**
	 * 设置版本
	 * 
	 * @param voltage
	 *            版本
	 */
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	/**
	 * 获取用户名
	 * 
	 * @return username
	 */
	@Length(max = 200)
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return password
	 */
	@Length(max = 200)
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取用户名
	 * 
	 * @return mqttUsername
	 */
	@Length(max = 200)
	public String getMqttUsername() {
		return mqttUsername;
	}

	/**
	 * 设置用户名
	 * 
	 * @param mqttUsername
	 *            用户名
	 */
	public void setMqttUsername(String mqttUsername) {
		this.mqttUsername = mqttUsername;
	}

	/**
	 * 获取密码
	 * 
	 * @return mqttPassword
	 */
	@Length(max = 200)
	public String getMqttPassword() {
		return mqttPassword;
	}

	/**
	 * 设置密码
	 * 
	 * @param mqttPassword
	 *            密码
	 */
	public void setMqttPassword(String mqttPassword) {
		this.mqttPassword = mqttPassword;
	}

	/**
	 * 获取是否运营
	 * 
	 * @return 是否运营
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	/**
	 * 设置是否运营
	 * 
	 * @param isMarketable
	 *            是否运营
	 */
	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	/**
	 * 获取是否列出
	 * 
	 * @return 是否列出
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsList() {
		return isList;
	}

	/**
	 * 设置是否列出
	 * 
	 * @param isList
	 *            是否列出
	 */
	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	/**
	 * 获取是否在线
	 * 
	 * @return 是否在线
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsOnline() {
		return isOnline;
	}

	/**
	 * 设置是否列出
	 * 
	 * @param isOnline
	 *            是否在线
	 */
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * 获取制造时间
	 * 
	 * @return 制造时间
	 */
	public Date getManufacture() {
		return manufacture;
	}

	/**
	 * 设置制造时间
	 * 
	 * @param manufacture
	 *            制造时间
	 */
	public void setManufacture(Date manufacture) {
		this.manufacture = manufacture;
	}

	/**
	 * 获取店铺
	 * 
	 * @return 店铺
	 */
	@OneToOne(fetch = FetchType.EAGER)
	public Shop getShop() {
		return shop;
	}

	/**
	 * 设置店铺
	 * 
	 * @param shop
	 *            店铺
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取经度
	 * 
	 * @return 经度
	 */
	@Length(max = 200)
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 设置经度
	 * 
	 * @param latitude
	 *            经度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * 获取纬度
	 * 
	 * @return 纬度
	 */
	@Length(max = 200)
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 设置纬度
	 * 
	 * @param longitude
	 *            纬度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 获取代码
	 * 
	 * @return status
	 */
	@Column(nullable = false)
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置代码
	 * 
	 * @param status
	 *            代码
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取异常码
	 * 
	 * @return error
	 */
	@Length(max = 200)
	public String getError() {
		return error;
	}

	/**
	 * 设置异常码
	 * 
	 * @param error
	 *            代码
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取升级日志
	 * 
	 * @return 升级日志
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<UpgradeLog> getUpgradeLogs() {
		return upgradeLogs;
	}

	/**
	 * 设置升级日志
	 * 
	 * @param upgradeLogs
	 *            升级日志
	 */
	public void setUpgradeLogs(Set<UpgradeLog> upgradeLogs) {
		this.upgradeLogs = upgradeLogs;
	}

	/**
	 * 获取是否最新版本
	 * 
	 * @return 是否最新版本
	 */
	public Boolean getIsLatest() {
		return isLatest;
	}

	/**
	 * 设置是否最新版本
	 * 
	 * @param isLatest
	 *            是否最新版本
	 */
	public void setIsLatest(Boolean isLatest) {
		this.isLatest = isLatest;
	}
	
	/**
	 * 获取产品客户定义名称
	 * 
	 * @return 客户定义名称
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置产品客户定义名称
	 * 
	 * @param productName
	 *            产品客户定义名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**
	 * 获取产品手机卡号
	 * 
	 * @return productCardno
	 *            产品手机卡号
	 */
	public String getProductCardno() {
		return productCardno;
	}
	
	/**
	 * 设置产品手机卡号
	 * 
	 * @param productCardno
	 *            产品手机卡号
	 */
	public void setProductCardno(String productCardno) {
		this.productCardno = productCardno;
	}

}