package com.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartNumber")
@Data
@NoArgsConstructor
@Table(name = "cartItems")
public class CartItem {

	public CartItem(Integer quantity2, Product product2, Cart newCart) {
		this.quantity=quantity2;
		this.product=product2;
		this.cart=newCart;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cartNumber;
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cartId", nullable = false)
	@JsonIgnoreProperties("cartItems")
	@JsonIgnore
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "productId", nullable = false)
	@JsonIgnoreProperties("cartItems")
	@JsonIgnore
	private Product product;


}
