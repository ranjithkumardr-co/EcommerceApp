package com.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderNumber")
@Data
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderNumber;
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "orderId", nullable = false)
	@JsonIgnoreProperties("orderItems")
	@JsonIgnore
	private UserOrder user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "productId", nullable = false)
	@JsonIgnoreProperties("orderItems")
	@JsonIgnore
	private Product product;

}
