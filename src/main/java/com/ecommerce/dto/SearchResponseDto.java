package com.ecommerce.dto;

import lombok.Data;

@Data
public class SearchResponseDto {

	private Integer productId;
	private String productName;
	private String productCategory;
	private Double productPrice;

	public SearchResponseDto(Integer productId, String productName, String productCategory, Double productPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productPrice = productPrice;
	}

}
