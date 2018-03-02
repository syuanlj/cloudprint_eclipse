/*
 * 
 * 
 * 
 */
package com.nfet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 商品
 * 
 * 
 * 
 */
@Entity
@Table(name = "cloudprint_firmware")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_firmware_sequence")
public class Firmware extends BaseEntity {

	private static final long serialVersionUID = 2167830430439593281L;

	/**
	 * 升级类型
	 */
	public enum Type {
		/**
		 * 立即
		 */
		current,
		/**
		 * 定期
		 */
		schedule
	}

	/**
	 * 产品区分
	 */
	public enum ProductType {
		/**
		 * 包含
		 */
		include,
		/**
		 * 排除
		 */
		exclude
	}

	/** ftp用户名 */
	private String ftpname;

	/** ftp密码 */
	private String ftppwd;

	/** url */
	private String url;

	/** 版本 */
	private String version;

	/** 文件长度 */
	private String length;

	/** 校验码 */
	private String md5;

	/** 日期 */
	private String date;

	/** 终止日期 */
	private String enddate;

	/** 商品分类 */
	private ProductCategory productCategory;

	/** 是否升级完毕 */
	private Boolean isCompleted;

	/** 升级类型 */
	private Type type;

	/** 升级时点 */
	private Integer hour;

	/** 商品 */
	private String productSn;

	/** 是否例外 */
	private ProductType productType;

	/** 目标版本 */
	private String targetVersion;
	
	/** 地区 */
	private Area area;

	/**
	 * 获取ftp用户名
	 * 
	 * @return ftpname
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getFtpname() {
		return ftpname;
	}

	/**
	 * 设置ftp用户名
	 * 
	 * @param ftpname
	 *            ftp用户名
	 */
	public void setFtpname(String ftpname) {
		this.ftpname = ftpname;
	}

	/**
	 * 获取ftp密码
	 * 
	 * @return ftppwd
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getFtppwd() {
		return ftppwd;
	}

	/**
	 * 设置ftp密码
	 * 
	 * @param ftppwd
	 *            ftp密码
	 */
	public void setFtppwd(String ftppwd) {
		this.ftppwd = ftppwd;
	}

	/**
	 * 获取url
	 * 
	 * @return url
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url
	 * 
	 * @param url
	 *            url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取版本
	 * 
	 * @return version
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * 
	 * @param version
	 *            版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 获取文件长度
	 * 
	 * @return length
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getLength() {
		return length;
	}

	/**
	 * 设置文件长度
	 * 
	 * @param version
	 *            文件长度
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * 获取校验码
	 * 
	 * @return md5
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getMd5() {
		return md5;
	}

	/**
	 * 设置校验码
	 * 
	 * @param md5
	 *            校验码
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * 获取日期
	 * 
	 * @return date
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getDate() {
		return date;
	}

	/**
	 * 设置日期
	 * 
	 * @param date
	 *            日期
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 获取终止日期
	 * 
	 * @return enddate
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getEnddate() {
		return enddate;
	}

	/**
	 * 设置终止日期
	 * 
	 * @param enddate
	 *            终止日期
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
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
	 * 获取是否升级完毕
	 * 
	 * @return 是否升级完毕
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsCompleted() {
		return isCompleted;
	}

	/**
	 * 设置是否升级完毕
	 * 
	 * @param isCompleted
	 *            是否升级完毕
	 */
	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	/**
	 * 获取升级类型
	 * 
	 * @return 升级类型
	 */
	@Column(nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置升级类型
	 * 
	 * @param type
	 *            升级类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取推送时点
	 * 
	 * @return 推送时点
	 */
	@Min(0)
	public Integer getHour() {
		return hour;
	}

	/**
	 * 设置推送时点
	 * 
	 * @param hour
	 *            推送时点
	 */
	public void setHour(Integer hour) {
		this.hour = hour;
	}

	/**
	 * 获取商品编号
	 * 
	 * @return 商品编号
	 */
	@Length(max = 200)
	public String getProductSn() {
		return productSn;
	}

	/**
	 * 设置商品编号
	 * 
	 * @param productSn
	 *            商品编号
	 */
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	/**
	 * 获取是否例外
	 * 
	 * @return 是否例外
	 */
	@Column(nullable = false)
	public ProductType getProductType() {
		return productType;
	}

	/**
	 * 设置是否例外
	 * 
	 * @param productType
	 *            是否例外
	 */
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	/**
	 * 获取目标版本
	 * 
	 * @return targetVersion
	 */
	@Length(max = 200)
	public String getTargetVersion() {
		return targetVersion;
	}

	/**
	 * 设置目标版本
	 * 
	 * @param targetVersion
	 *            目标版本
	 */
	public void setTargetVersion(String targetVersion) {
		this.targetVersion = targetVersion;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@ManyToOne(fetch = FetchType.EAGER)
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
}