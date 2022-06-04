package com.ecommerce.service;

import javax.validation.Valid;

import com.ecommerce.dto.AddToCartRequestDto;
import com.ecommerce.dto.AddToCartResponseDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;

public interface CartService {

	AddToCartResponseDto addToCart(@Valid AddToCartRequestDto addToCartRequestDto);

	AddToCartResponseDto generateAddToCartResponse(Cart cart);

	AddToCartResponseDto updateExistingCartOfUser(User user, AddToCartRequestDto addToCartRequestDto);

	Double calculateTotalPriceOfCartItems(AddToCartRequestDto addToCartRequestDto);
	
	Integer calculateTotalCountOfCartItems(AddToCartRequestDto addToCartRequestDto);

}
