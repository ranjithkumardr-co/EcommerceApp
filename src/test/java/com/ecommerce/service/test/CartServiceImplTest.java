package com.ecommerce.service.test;

import static com.ecommerce.dto.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ecommerce.controller.CartController;
import com.ecommerce.dto.AddToCartRequestDto;
import com.ecommerce.dto.AddToCartResponseDto;
import com.ecommerce.dto.CartItemRequestDto;
import com.ecommerce.entity.*;
import com.ecommerce.exception.IdNotFoundException;
import com.ecommerce.repo.CartItemRepository;
import com.ecommerce.repo.CartRepository;
import com.ecommerce.repo.ProductRepository;
import com.ecommerce.repo.UserRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.impl.CartServiceImpl;

@SpringBootTest
 class CartServiceImplTest {

	@Mock
	CartRepository cartRepository;

	@InjectMocks
	CartServiceImpl cartServiceImpl;

	@Mock
	ProductRepository productRepository;

	@Mock
	CartItemRepository cartItemRepository;

	@Mock
	UserRepository userRepository;

	Cart cart;

	Cart savedCart;

	User user;

	CartItem cartItem;

	List<CartItem> cartItemList;

	Product product;

	AddToCartRequestDto addToCartRequestDto;

	CartItemRequestDto cartItemRequestDto;

	List<CartItemRequestDto> cartItemRequestDtoList;

	AddToCartResponseDto addToCartResponseDto;

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

		user = new User();
		user.setUserId(1);
		user.setFirstName("Ranjith");
		user.setLastName("kumar");
		user.setEmailId("ranjith@gmail.com");
		user.setPassword("password");
		user.setPhoneNo((long) 988955655);
		user.setUserName("ranjith@123");

		product = new Product();
		product.setProductId(1);
		product.setProductName("Redmi Phone");
		product.setProductCategory("Electronics");
		product.setProductPrice(500.00);

		cart = new Cart();
		cart.setCartId(1);
		cart.setCartItemsCount(1);
		cart.setCartTotal(500.00);
		cart.setCreatedAt(LocalDateTime.now());

		savedCart = new Cart();
		BeanUtils.copyProperties(cart, savedCart);

		addToCartResponseDto = new AddToCartResponseDto();
		addToCartResponseDto.setCartTotal(500.00);
		addToCartResponseDto.setCartItemsCount(1);
		addToCartResponseDto.setUserId(1);
	}

	@Test
	@DisplayName("Add to new cart:Positive")
	 void addToNewCartTest_Positive() {
		when(userRepository.findById(addToCartRequestDto.getUserId())).thenReturn(Optional.of(user));
		when(productRepository.findById(addToCartRequestDto.getCartItems().get(0).getProductId()))
				.thenReturn(Optional.of(product));
		when(cartRepository.save(any(Cart.class))).thenReturn(savedCart);
		savedCart.setUser(user);

		AddToCartResponseDto result = cartServiceImpl.addToCart(addToCartRequestDto);

		assertEquals(1, result.getUserId());
	}

	@Test
	@DisplayName("Add to Existing cart:Positive")
	 void addToExistingCartTest_Positive() {

		user.setCart(cart);

		cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setCartNumber(1);
		cartItem.setQuantity(1);
		cartItem.setProduct(product);

		cartItemList = new ArrayList<CartItem>();
		cartItemList.add(cartItem);

		cart.setCartItems(cartItemList);

		when(userRepository.findById(addToCartRequestDto.getUserId())).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(cart);
		when(productRepository.findById(addToCartRequestDto.getCartItems().get(0).getProductId()))
				.thenReturn(Optional.of(product));
		when(cartRepository.save(any(Cart.class))).thenReturn(savedCart);
		savedCart.setUser(user);

		AddToCartResponseDto result = cartServiceImpl.addToCart(addToCartRequestDto);

		assertEquals(1, result.getUserId());
	}

	@Test
	@DisplayName("Add to cart:Negative")
	 void addToCartTest_Negative() {
		
		Optional<User> optionalUser=Optional.empty();
		
		when(userRepository.findById(addToCartRequestDto.getUserId())).thenReturn(optionalUser);

		IdNotFoundException e = assertThrows(IdNotFoundException.class, () -> {
			cartServiceImpl.addToCart(addToCartRequestDto);
		});

		assertEquals(ID_NOT_FOUND_MESSAGE, e.getMessage());
	}

}
