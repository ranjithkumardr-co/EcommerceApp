package com.ecommerce.controller.test;

import static com.ecommerce.dto.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ecommerce.controller.CartController;
import com.ecommerce.dto.AddToCartRequestDto;
import com.ecommerce.dto.AddToCartResponseDto;
import com.ecommerce.dto.CartItemRequestDto;
import com.ecommerce.exception.IdNotFoundException;
import com.ecommerce.service.CartService;

@SpringBootTest
class CartControllerTest {

	@Mock
	CartService cartService;

	@InjectMocks
	CartController cartController;

	AddToCartRequestDto addToCartRequestDto;

	CartItemRequestDto cartItemRequestDto;

	List<CartItemRequestDto> cartItemRequestDtoList;

	AddToCartResponseDto addToCartResponseDto;

	Validator validator;

	@BeforeEach
	void setUp() {
		addToCartRequestDto = new AddToCartRequestDto();

		addToCartRequestDto.setUserId(1);

		cartItemRequestDto = new CartItemRequestDto();
		cartItemRequestDto.setProductId(1);
		cartItemRequestDto.setQuantity(1);

		cartItemRequestDtoList = new ArrayList<CartItemRequestDto>();
		cartItemRequestDtoList.add(cartItemRequestDto);
		addToCartRequestDto.setCartItems(cartItemRequestDtoList);

		addToCartResponseDto = new AddToCartResponseDto();
		addToCartResponseDto.setCartTotal(500.00);
		addToCartResponseDto.setCartItemsCount(1);
		addToCartResponseDto.setUserId(1);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@Test
	@DisplayName("Add to cart:Positive")
	void addToCartTest_Positive() {
		when(cartService.addToCart(addToCartRequestDto)).thenReturn(addToCartResponseDto);

		ResponseEntity<AddToCartResponseDto> result = cartController.addtoCart(addToCartRequestDto);

		assertEquals(1, result.getBody().getUserId());
	}

	@Test
	@DisplayName("Add to cart:Negative")
	void addToCartTest_Negative() {
		when(cartService.addToCart(addToCartRequestDto)).thenThrow(new IdNotFoundException("No User Found"));

		IdNotFoundException e = assertThrows(IdNotFoundException.class, () -> {
			cartController.addtoCart(addToCartRequestDto);
		});

		assertEquals("No User Found", e.getMessage());
	}

	@Test
	@DisplayName("User Id Valid Test:Negative")
	void userIdTest_Negative() {
		addToCartRequestDto.setUserId(null);
		Set<ConstraintViolation<AddToCartRequestDto>> violations = validator.validate(addToCartRequestDto);

		violations.forEach(violation -> {
			assertEquals(USERID_NOTNULL_MESSAGE, violation.getMessage());
		});

	}

	@Test
	@DisplayName("User Id Valid Test:Positive")
	void userIdTest_Positive() {

		Set<ConstraintViolation<AddToCartRequestDto>> violations = validator.validate(addToCartRequestDto);

		assertTrue(violations.isEmpty());

	}

	@Test
	@DisplayName("Cart Items Valid Test:Negative")
	void cartItemTest_Negative() {
		cartItemRequestDtoList.clear();
		Set<ConstraintViolation<AddToCartRequestDto>> violations = validator.validate(addToCartRequestDto);

		violations.forEach(violation -> {
			assertEquals(CARTITEM_EMPTY_MESSAGE, violation.getMessage());
		});

	}

	@Test
	@DisplayName("Cart Items Valid Test:Positive")
	void cartItemTest_Positive() {

		Set<ConstraintViolation<AddToCartRequestDto>> violations = validator.validate(addToCartRequestDto);

		assertTrue(violations.isEmpty());

	}

	@Test
	@DisplayName("Cart Item product Id Valid Test:Negative")
	void cartItemProductIdTest_Negative() {
		cartItemRequestDto.setProductId(0);
		Set<ConstraintViolation<CartItemRequestDto>> violations = validator.validate(cartItemRequestDto);

		violations.forEach(violation -> {
			assertEquals(PRODUCTID_NOTZERO_MESSAGE, violation.getMessage());
		});

	}

	@Test
	@DisplayName("Cart Item product Id Valid Test:Positive")
	void cartItemProductIdTest_Positive() {
		Set<ConstraintViolation<CartItemRequestDto>> violations = validator.validate(cartItemRequestDto);

		assertTrue(violations.isEmpty());
	}

}
