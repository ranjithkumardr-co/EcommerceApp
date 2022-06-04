package com.ecommerce.service;

import java.util.List;

import com.ecommerce.dto.SearchResponseDto;

public interface ProductService {

	List<SearchResponseDto> searchProduct(String productname);

}
