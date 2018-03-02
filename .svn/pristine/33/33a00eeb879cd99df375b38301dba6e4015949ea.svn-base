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
import javax.validation.constraints.NotNull;

/**
 * Entity - 商品
 * 
 * 
 * 
 */
@Entity
@Table(name = "cloudprint_upgrade_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_upgrade_log_sequence")
public class UpgradeLog extends BaseEntity {

	private static final long serialVersionUID = 2167830430439593283L;

	/** 商品 */
	private Product product;

	/** 代码 */
	private Byte code;

	/**
	 * 获取代码
	 * 
	 * @return code
	 */
	@NotNull
	@Column(nullable = false)
	public Byte getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 *            代码
	 */
	public void setCode(Byte code) {
		this.code = code;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param product
	 *            商品分类
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

}