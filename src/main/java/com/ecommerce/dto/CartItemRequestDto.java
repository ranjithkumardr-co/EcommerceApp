package com.ecommerce.dto;

import static com.ecommerce.dto.Constants.*;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class CartItemRequestDto {
	
	@Min(value = 1, message = PRODUCTID_NOTZERO_MESSAGE)
	private Integer productId;

	private Integer quantity;

}
