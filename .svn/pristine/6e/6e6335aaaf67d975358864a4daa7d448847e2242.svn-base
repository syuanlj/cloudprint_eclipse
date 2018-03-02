/*
 * 
 * 
 * 
 */
package com.nfet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 品牌
 * 
 * 
 * 
 */
@Entity
@Table(name = "cloudprint_shop")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_shop_sequence")
public class Shop extends OrderEntity {

	private static final long serialVersionUID = -6109590619136943215L;

	/** 名称 */
	private String name;

	/** 商品 */
	private Product product;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToOne(mappedBy = "shop", fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Product product = getProduct();
		if (product != null) {
			product.setShop(null);
		}
	}

}