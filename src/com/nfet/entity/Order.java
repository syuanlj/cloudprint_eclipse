/*
 * 
 * 
 * 
 */
package com.nfet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Entity - 订单
 * 
 * 
 * 
 */
@Entity
@Table(name = "cloudprint_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_order_sequence")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 8370942500343156156L;

	/**
	 * 订单状态
	 */
	public enum OrderStatus {

		/** 已收到 */
		received,

		/** 已发送 */
		sent,

		/** 已打印 */
		printed
	}

	/** 任务编号 */
	private String taskId;

	/** 订单编号 */
	private String sn;

	/** 订单状态 */
	private OrderStatus orderStatus;

	/** 接收时间 */
	private Date receive;

	/** 到期时间 */
	private Date expire;

	/** 内容 */
	private String content;

	/** 上次推送时间 */
	private Date lastPush;

	/** 赠送次数 */
	private Integer pushTimes;

	/** 打印结束时间 */
	private Date finish;

	/** 产品 */
	private Product product;

	/**
	 * 获取任务编号
	 * 
	 * @return 任务编号
	 */
	@Column(nullable = false, updatable = false, length = 100)
	public String getTaskId() {
		return taskId;
	}

	/**
	 * 设置任务编号
	 * 
	 * @param taskId
	 *            任务编号
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 获取订单编号
	 * 
	 * @return 订单编号
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置订单编号
	 * 
	 * @param sn
	 *            订单编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取订单状态
	 * 
	 * @return 订单状态
	 */
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 设置订单状态
	 * 
	 * @param orderStatus
	 *            订单状态
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 获取接收时间
	 * 
	 * @return 接收时间
	 */
	public Date getReceive() {
		return receive;
	}

	/**
	 * 设置接收时间
	 * 
	 * @param receive
	 *            接收时间
	 */
	public void setReceive(Date receive) {
		this.receive = receive;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return 到期时间
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 设置到期时间
	 * 
	 * @param expire
	 *            到期时间
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/**
	 * 获取商店
	 * 
	 * @return 内容
	 */
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getContent() {
		return content;
	}

	/**
	 * 设置商店
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取上次推送时间
	 * 
	 * @return 上次推送时间
	 */
	public Date getLastPush() {
		return lastPush;
	}

	/**
	 * 设置上次推送时间
	 * 
	 * @param lastPush
	 *            上次推送时间
	 */
	public void setLastPush(Date lastPush) {
		this.lastPush = lastPush;
	}

	/**
	 * 获取推送次数
	 * 
	 * @return 推送次数
	 */
	@NotNull
	@Min(0)
	@Column(nullable = false)
	public Integer getPushTimes() {
		return pushTimes;
	}

	/**
	 * 设置推送次数
	 * 
	 * @param pushTimes
	 *            推送次数
	 */
	public void setPushTimes(Integer pushTimes) {
		this.pushTimes = pushTimes;
	}

	/**
	 * 获取打印打印结束时间
	 * 
	 * @return 打印打印结束时间
	 */
	public Date getFinish() {
		return finish;
	}

	/**
	 * 设置打印打印结束时间
	 * 
	 * @param finish
	 *            打印打印结束时间
	 */
	public void setFinish(Date print) {
		this.finish = print;
	}

	/**
	 * 获取产品
	 * 
	 * @return 产品
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置产品
	 * 
	 * @param product
	 *            产品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * /** 是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean isExpired() {
		return getExpire() != null && new Date().after(getExpire());
	}

}