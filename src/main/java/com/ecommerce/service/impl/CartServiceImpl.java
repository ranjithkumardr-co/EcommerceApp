package com.ecommerce.service.impl;

import static com.ecommerce.dto.Constants.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.AddToCartRequestDto;
import com.ecommerce.dto.AddToCartResponseDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.User;
import com.ecommerce.exception.IdNotFoundException;
import com.ecommerce.repo.CartItemRepository;
import com.ecommerce.repo.CartRepository;
import com.ecommerce.repo.ProductRepository;
import com.ecommerce.repo.UserRepository;
import com.ecommerce.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public AddToCartResponseDto addToCart(@Valid AddToCartRequestDto addToCartRequestDto) {

		Optional<User> optionalUser = userRepository.findById(addToCartRequestDto.getUserId());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Cart cart = user.getCart();

			if (cart == null) {
				Cart newCart = new Cart();
				newCart.setCreatedAt(LocalDateTime.now());
				newCart.setUser(user);
				Double calculatedtotal = calculateTotalPriceOfCartItems(addToCartRequestDto);
				newCart.setCartTotal(calculatedtotal);
				Integer calculatedItemsCount = calculateTotalCountOfCartItems(addToCartRequestDto);
				newCart.setCartItemsCount(calculatedItemsCount);

				List<CartItem> cartItemList = new ArrayList<>();

				addToCartRequestDto.getCartItems().forEach(cartItemRequest -> {
					CartItem cartItem = new CartItem(cartItemRequest.getQuantity(),
							productRepository.findById(cartItemRequest.getProductId()).get(), newCart);
					cartItemList.add(cartItem);
				});
				newCart.setCartItems(cartItemList);
				Cart savedCart = cartRepository.save(newCart);
				return generateAddToCartResponse(savedCart);

			} else {
				return updateExistingCartOfUser(user, addToCartRequestDto);
			}
		} else {
			throw new IdNotFoundException(ID_NOT_FOUND_MESSAGE);
		}

	}

	@Override
	public AddToCartResponseDto generateAddToCartResponse(Cart cart) {
		AddToCartResponseDto addToCartResponseDto = new AddToCartResponseDto();
		addToCartResponseDto.setCartItemsCount(cart.getCartItemsCount());
		addToCartResponseDto.setCartTotal(cart.getCartTotal());
		addToCartResponseDto.setUserId(cart.getUser().getUserId());
		return addToCartResponseDto;
	}

	@Override
	public AddToCartResponseDto updateExistingCartOfUser(User user, AddToCartRequestDto addToCartRequestDto) {

		Cart existedCart = cartRepository.findByUser(user);
		Double calculatedtotal = calculateTotalPriceOfCartItems(addToCartRequestDto);

		existedCart.setUser(user);
		existedCart.setCreatedAt(LocalDateTime.now());

		List<CartItem> cartItemList = existedCart.getCartItems();

		addToCartRequestDto.getCartItems().forEach(cartItemRequest -> {

			CartItem cartItem = new CartItem(cartItemRequest.getQuantity(),
					productRepository.findById(cartItemRequest.getProductId()).get(), existedCart);

			cartItemList.add(cartItem);

		});
		existedCart.setCartTotal(existedCart.getCartTotal() + calculatedtotal);
		Integer calculatedItemsCount = calculateTotalCountOfCartItems(addToCartRequestDto);
		existedCart.setCartItemsCount(existedCart.getCartItemsCount() + calculatedItemsCount);
		Cart savedCart = cartRepository.save(existedCart);

		return generateAddToCartResponse(savedCart);
	}

	@Override
	public Double calculateTotalPriceOfCartItems(AddToCartRequestDto addToCartRequestDto) {

		return addToCartRequestDto.getCartItems().stream()
				.mapToDouble(cartItem -> productRepository.findById(cartItem.getProductId()).get().getProductPrice()
						* cartItem.getQuantity())
				.sum();
	}

	@Override
	public Integer calculateTotalCountOfCartItems(AddToCartRequestDto addToCartRequestDto) {

		return addToCartRequestDto.getCartItems().stream().mapToInt(cartItem -> cartItem.getQuantity()).sum();
	}

}
