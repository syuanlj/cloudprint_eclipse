/*
 * 
 * 
 * 
 */
package com.nfet;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 系统设置
 * 
 * 
 * 
 */
public class Setting implements Serializable {

	private static final long serialVersionUID = -1478999889661796840L;

	/**
	 * 小数位精确方式
	 */
	public enum RoundType {

		/** 四舍五入 */
		roundHalfUp,

		/** 向上取整 */
		roundUp,

		/** 向下取整 */
		roundDown
	}

	/**
	 * 账号锁定类型
	 */
	public enum AccountLockType {

		/** 会员 */
		member,

		/** 管理员 */
		admin
	}

	/** 缓存名称 */
	public static final String CACHE_NAME = "setting";

	/** 缓存Key */
	public static final Integer CACHE_KEY = 0;

	/** 分隔符 */
	private static final String SEPARATOR = ",";

	/** 网站网址 */
	private String siteUrl;

	/** 价格精确位数 */
	private Integer priceScale;

	/** 价格精确方式 */
	private RoundType priceRoundType;

	/** 货币符号 */
	private String currencySign;

	/** 货币单位 */
	private String currencyUnit;

	/** 账号锁定类型 */
	private AccountLockType[] accountLockTypes;

	/** 连续登录失败最大次数 */
	private Integer accountLockCount;

	/** 自动解锁时间 */
	private Integer accountLockTime;

	/** 安全密匙有效时间 */
	private Integer safeKeyExpiryTime;

	/** 允许上传产品文件扩展名 */
	private String productFileExtension;

	/** 允许上传升级文件扩展名 */
	private String firmwareFileExtension;

	/** 允许上传记录文件扩展名 */
	private String recordFileExtension;

	/** 记录文件上传路径 */
	private String recordFilePath;

	/** Cookie路径 */
	private String cookiePath;

	/** Cookie作用域 */
	private String cookieDomain;

	/** admin用户名 */
	private String adminUsername;

	/** admin密码 */
	private String adminPassword;

	/** server主机 */
	private String serverHost;

	/** server用户名 */
	private String serverUsername;

	/** server密码 */
	private String serverPassword;

	/** 推送间隔 */
	private String pushInterval;

	/** 过期间隔 */
	private String expireInterval;

	/** 服务器序号 */
	private String serverNo;

	/** 服务器数量 */
	private String serverCount;

	/** 服务器列表 */
	private String serverUrl;

	/**
	 * 获取网站网址
	 * 
	 * @return 网站网址
	 */
	@NotEmpty
	@Length(max = 200)
	public String getSiteUrl() {
		return siteUrl;
	}

	/**
	 * 设置网站网址
	 * 
	 * @param siteUrl
	 *            网站网址
	 */
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = StringUtils.removeEnd(siteUrl, "/");
	}

	/**
	 * 获取价格精确位数
	 * 
	 * @return 价格精确位数
	 */
	@NotNull
	@Min(0)
	@Max(3)
	public Integer getPriceScale() {
		return priceScale;
	}

	/**
	 * 设置价格精确位数
	 * 
	 * @param priceScale
	 *            价格精确位数
	 */
	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	/**
	 * 获取价格精确方式
	 * 
	 * @return 价格精确方式
	 */
	@NotNull
	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	/**
	 * 设置价格精确方式
	 * 
	 * @param priceRoundType
	 *            价格精确方式
	 */
	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	/**
	 * 获取货币符号
	 * 
	 * @return 货币符号
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCurrencySign() {
		return currencySign;
	}

	/**
	 * 设置货币符号
	 * 
	 * @param currencySign
	 *            货币符号
	 */
	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	/**
	 * 获取货币单位
	 * 
	 * @return 货币单位
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCurrencyUnit() {
		return currencyUnit;
	}

	/**
	 * 设置货币单位
	 * 
	 * @param currencyUnit
	 *            货币单位
	 */
	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	/**
	 * 获取账号锁定类型
	 * 
	 * @return 账号锁定类型
	 */
	public AccountLockType[] getAccountLockTypes() {
		return accountLockTypes;
	}

	/**
	 * 设置账号锁定类型
	 * 
	 * @param accountLockTypes
	 *            账号锁定类型
	 */
	public void setAccountLockTypes(AccountLockType[] accountLockTypes) {
		this.accountLockTypes = accountLockTypes;
	}

	/**
	 * 获取连续登录失败最大次数
	 * 
	 * @return 连续登录失败最大次数
	 */
	@NotNull
	@Min(1)
	public Integer getAccountLockCount() {
		return accountLockCount;
	}

	/**
	 * 设置连续登录失败最大次数
	 * 
	 * @param accountLockCount
	 *            连续登录失败最大次数
	 */
	public void setAccountLockCount(Integer accountLockCount) {
		this.accountLockCount = accountLockCount;
	}

	/**
	 * 获取自动解锁时间
	 * 
	 * @return 自动解锁时间
	 */
	@NotNull
	@Min(0)
	public Integer getAccountLockTime() {
		return accountLockTime;
	}

	/**
	 * 设置自动解锁时间
	 * 
	 * @param accountLockTime
	 *            自动解锁时间
	 */
	public void setAccountLockTime(Integer accountLockTime) {
		this.accountLockTime = accountLockTime;
	}

	/**
	 * 获取安全密匙有效时间
	 * 
	 * @return 安全密匙有效时间
	 */
	@NotNull
	@Min(0)
	public Integer getSafeKeyExpiryTime() {
		return safeKeyExpiryTime;
	}

	/**
	 * 设置安全密匙有效时间
	 * 
	 * @param safeKeyExpiryTime
	 *            安全密匙有效时间
	 */
	public void setSafeKeyExpiryTime(Integer safeKeyExpiryTime) {
		this.safeKeyExpiryTime = safeKeyExpiryTime;
	}

	/**
	 * 获取允许上传产品文件扩展名
	 * 
	 * @return 允许上传产品文件扩展名
	 */
	@Length(max = 200)
	public String getProductFileExtension() {
		return productFileExtension;
	}

	/**
	 * 设置允许上传产品文件扩展名
	 * 
	 * @param productFileExtension
	 *            允许上传产品文件扩展名
	 */
	public void setProductFileExtension(String productFileExtension) {
		if (productFileExtension != null) {
			productFileExtension = productFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.productFileExtension = productFileExtension;
	}

	/**
	 * 获取允许上传升级文件扩展名
	 * 
	 * @return 允许上传升级文件扩展名
	 */
	@Length(max = 200)
	public String getFirmwareFileExtension() {
		return firmwareFileExtension;
	}

	/**
	 * 设置允许上传升级文件扩展名
	 * 
	 * @param firmwareFileExtension
	 *            允许上传升级文件扩展名
	 */
	public void setFirmwareFileExtension(String firmwareFileExtension) {
		if (firmwareFileExtension != null) {
			firmwareFileExtension = firmwareFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.firmwareFileExtension = firmwareFileExtension;
	}

	/**
	 * 获取允许上传记录文件扩展名
	 * 
	 * @return 允许上传记录文件扩展名
	 */
	@Length(max = 200)
	public String getRecordFileExtension() {
		return recordFileExtension;
	}

	/**
	 * 设置允许上传文件扩展名
	 * 
	 * @param recordFileExtension
	 *            允许上传记录文件扩展名
	 */
	public void setRecordFileExtension(String recordFileExtension) {
		if (recordFileExtension != null) {
			recordFileExtension = recordFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.recordFileExtension = recordFileExtension;
	}

	/**
	 * 获取允许上传产品文件扩展名
	 * 
	 * @return 允许上传产品文件扩展名
	 */
	public String[] getProductFileExtensions() {
		return StringUtils.split(productFileExtension, SEPARATOR);
	}

	/**
	 * 获取允许上传升级文件扩展名
	 * 
	 * @return 允许上传升级文件扩展名
	 */
	public String[] getFirmwareFileExtensions() {
		return StringUtils.split(firmwareFileExtension, SEPARATOR);
	}

	/**
	 * 获取记录文件上传路径
	 * 
	 * @return 记录文件上传路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getRecordFilePath() {
		return recordFilePath;
	}

	/**
	 * 设置记录文件上传路径
	 * 
	 * @param recordFilePath
	 *            记录文件上传路径
	 */
	public void setRecordFilePath(String recordFilePath) {
		if (recordFilePath != null) {
			if (!recordFilePath.startsWith("/")) {
				recordFilePath = "/" + recordFilePath;
			}
			if (!recordFilePath.endsWith("/")) {
				recordFilePath += "/";
			}
		}
		this.recordFilePath = recordFilePath;
	}

	/**
	 * 获取Cookie路径
	 * 
	 * @return Cookie路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCookiePath() {
		return cookiePath;
	}

	/**
	 * 设置Cookie路径
	 * 
	 * @param cookiePath
	 *            Cookie路径
	 */
	public void setCookiePath(String cookiePath) {
		if (cookiePath != null && !cookiePath.endsWith("/")) {
			cookiePath += "/";
		}
		this.cookiePath = cookiePath;
	}

	/**
	 * 获取Cookie作用域
	 * 
	 * @return Cookie作用域
	 */
	@Length(max = 200)
	public String getCookieDomain() {
		return cookieDomain;
	}

	/**
	 * 设置Cookie作用域
	 * 
	 * @param cookieDomain
	 *            Cookie作用域
	 */
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	/**
	 * 获取admin用户名
	 * 
	 * @return admin用户名
	 */
	@Length(max = 200)
	public String getAdminUsername() {
		return adminUsername;
	}

	/**
	 * 设置admin用户名
	 * 
	 * @param adminUsername
	 *            admin用户名
	 */
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	/**
	 * 获取admin密码
	 * 
	 * @return admin密码
	 */
	@Length(max = 200)
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * 设置admin密码
	 * 
	 * @param adminPassword
	 *            admin密码
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * 获取server主机
	 * 
	 * @return server主机
	 */
	@Length(max = 200)
	public String getServerHost() {
		return serverHost;
	}

	/**
	 * 设置server主机
	 * 
	 * @param serverHost
	 *            server主机
	 */
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	/**
	 * 获取server用户名
	 * 
	 * @return server用户名
	 */
	@Length(max = 200)
	public String getServerUsername() {
		return serverUsername;
	}

	/**
	 * 设置server用户名
	 * 
	 * @param serverUsername
	 *            server用户名
	 */
	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}

	/**
	 * 获取server密码
	 * 
	 * @return server密码
	 */
	@Length(max = 200)
	public String getServerPassword() {
		return serverPassword;
	}

	/**
	 * 设置server密码
	 * 
	 * @param serverPassword
	 *            server密码
	 */
	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	/**
	 * 获取推送间隔
	 * 
	 * @return 推送间隔
	 */
	@Length(max = 200)
	public String getPushInterval() {
		return pushInterval;
	}

	/**
	 * 设置推送间隔
	 * 
	 * @param pushInterval
	 *            推送间隔
	 */
	public void setPushInterval(String pushInterval) {
		this.pushInterval = pushInterval;
	}

	/**
	 * 获取过期间隔
	 * 
	 * @return 过期间隔
	 */
	@Length(max = 200)
	public String getExpireInterval() {
		return expireInterval;
	}

	/**
	 * 设置过期间隔
	 * 
	 * @param expireInterval
	 *            过期间隔
	 */
	public void setExpireInterval(String expireInterval) {
		this.expireInterval = expireInterval;
	}

	/**
	 * 获取服务器序号
	 * 
	 * @return 服务器序号
	 */
	@Length(max = 200)
	public String getServerNo() {
		return serverNo;
	}

	/**
	 * 设置服务器序号
	 * 
	 * @param serverNo
	 *            服务器序号
	 */
	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}

	/**
	 * 获取服务器数量
	 * 
	 * @return 服务器数量
	 */
	@Length(max = 200)
	public String getServerCount() {
		return serverCount;
	}

	/**
	 * 设置服务器数量
	 * 
	 * @param serverCount
	 *            服务器数量
	 */
	public void setServerCount(String serverCount) {
		this.serverCount = serverCount;
	}

	/**
	 * 获取服务器列表
	 * 
	 * @return 服务器列表
	 */
	@Length(max = 200)
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * 设置服务器数量
	 * 
	 * @param serverUrl
	 *            服务器数量
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * 设置精度
	 * 
	 * @param amount
	 *            数值
	 * @return 数值
	 */
	public BigDecimal setScale(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		int roundingMode;
		if (getPriceRoundType() == RoundType.roundUp) {
			roundingMode = BigDecimal.ROUND_UP;
		} else if (getPriceRoundType() == RoundType.roundDown) {
			roundingMode = BigDecimal.ROUND_DOWN;
		} else {
			roundingMode = BigDecimal.ROUND_HALF_UP;
		}
		return amount.setScale(getPriceScale(), roundingMode);
	}

}