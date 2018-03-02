/*
 * 
 * 
 * 
 */
package com.nfet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.nfet.entity.OrderEntity;
import com.nfet.entity.ProductCategory;

/**
 * 模板
 * 
 * 
 * 
 */
@Entity
@Table(name = "cloudprint_template")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cloudprint_template_sequence")
public class Template extends OrderEntity {

	private static final long serialVersionUID = -517540800437045215L;

	/** 名称 */
	private String name;

	/** 描述 */
	private String content;

	/** 产品类型 */
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

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
	 * 获取描述
	 * 
	 * @return 描述
	 */
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getContent() {
		return content;
	}

	/**
	 * 设置描述
	 * 
	 * @param content
	 *            描述
	 */
	public void setContent(String description) {
		this.content = description;
	}

	/**
	 * 获取商品类型
	 * 
	 * @return 商品类型
	 */
	@OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	/**
	 * 设置商品类型
	 * 
	 * @param productCategories
	 *            商品类型
	 */
	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

}