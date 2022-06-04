package com.ecommerce.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.SearchResponseDto;
import com.ecommerce.entity.*;
import com.ecommerce.exception.DataNotFoundException;
import com.ecommerce.repo.ProductRepository;
import com.ecommerce.service.impl.ProductServiceImpl;


@SpringBootTest
 class ProductServiceImplTest {
	
	
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	
	
	Product product;
	
	List<Product> productList;
	
	
    SearchResponseDto searchResponseDto;
	
	List<SearchResponseDto> SearchResponseDtoList;
	
	
	
	@BeforeEach
	 void setUp()
	{
		
		product=new Product();
		product.setProductId(1);
		product.setProductName("Redmi Phone");
		product.setProductCategory("Electronics");
		product.setProductPrice(5000.00);
		
		productList=new ArrayList<Product>();
		productList.add(product);
		
		searchResponseDto=new SearchResponseDto(1, "Redmi Phone", "Electronics", 5000.00);
		SearchResponseDtoList=new ArrayList<SearchResponseDto>();
		SearchResponseDtoList.add(searchResponseDto);
		
	}
	
	
	@Test
	@DisplayName("Search product:Positive")
	 void searchProductTest_Positive()
	{
		when(productRepository.findByProductNameContaining("Redmi")).thenReturn(productList);
		
		List<SearchResponseDto> result=productServiceImpl.searchProduct("Redmi");
		
		assertEquals(1, result.size());
	}
	
	@Test
	@DisplayName("Search product:Negative")
	 void searchProductTest_Negative()
	{
		List<Product> emptyproductList=new ArrayList<>();
		when(productRepository.findByProductNameContaining("Redmi")).thenReturn(emptyproductList);
		
		DataNotFoundException e = assertThrows(DataNotFoundException.class, () -> {
			productServiceImpl.searchProduct("Redmi");
		});
		
		assertEquals("No Data found With the Product Name",e.getMessage());
	}

}
