package com.ecommerce.service.impl;

import static com.ecommerce.dto.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.SearchResponseDto;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.DataNotFoundException;
import com.ecommerce.repo.ProductRepository;
import com.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<SearchResponseDto> searchProduct(String productname) {

		List<SearchResponseDto> searchResponseDtoList=new ArrayList<>();
		List<Product> productList=productRepository.findByProductNameContaining(productname);
		productList.forEach(product->
		{
			SearchResponseDto searchResponseDto=new SearchResponseDto(product.getProductId(), product.getProductName(), product.getProductCategory(), product.getProductPrice());
			searchResponseDtoList.add(searchResponseDto);
		});
		
		if(searchResponseDtoList.isEmpty())
		{
			throw new DataNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
		}
		return searchResponseDtoList;
	}

}
