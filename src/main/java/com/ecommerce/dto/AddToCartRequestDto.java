package com.ecommerce.dto;

import static com.ecommerce.dto.Constants.*;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddToCartRequestDto {

	
	@NotNull(message = USERID_NOTNULL_MESSAGE)
	private Integer userId;

	@NotEmpty(message =CARTITEM_EMPTY_MESSAGE)
	private List<CartItemRequestDto> cartItems;

}
