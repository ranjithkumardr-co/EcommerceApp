package com.ecommerce.dto;


import lombok.Data;

@Data
public class AddToCartResponseDto {

	private Integer userId;
	private Double cartTotal;
	private Integer cartItemsCount;

}
