package com.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.AddToCartRequestDto;
import com.ecommerce.dto.AddToCartResponseDto;
import com.ecommerce.service.CartService;

@RestController
@Validated
public class CartController {

	@Autowired
	CartService cartService;

	@PostMapping(value = "/cart/add")
	public ResponseEntity<AddToCartResponseDto> addtoCart(@Valid @RequestBody AddToCartRequestDto addToCartRequestDto) {

		return new ResponseEntity<>(cartService.addToCart(addToCartRequestDto), HttpStatus.OK);

	}
}
