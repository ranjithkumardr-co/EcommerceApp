package com.ecommerce.controller.test;


import static com.ecommerce.dto.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.ecommerce.controller.ProductController;
import com.ecommerce.dto.SearchResponseDto;
import com.ecommerce.exception.DataNotFoundException;
import com.ecommerce.service.ProductService;

@SpringBootTest
 class ProductControllerTest {

	
	@Mock
	ProductService productService;
	
	@InjectMocks
	ProductController productController;
	
	SearchResponseDto searchResponseDto;
	
	List<SearchResponseDto> SearchResponseDtoList;
	
	Validator validator;
	
	@BeforeEach
	 void setUp()
	{
		searchResponseDto=new SearchResponseDto(1, "Redmi Phone", "Electronics", 5000.00);
		SearchResponseDtoList=new ArrayList<SearchResponseDto>();
		SearchResponseDtoList.add(searchResponseDto);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		
	}
	
	
	@Test
	@DisplayName("Search product:Positive")
	 void searchProductTest_Positive()
	{
		when(productService.searchProduct("Redmi")).thenReturn(SearchResponseDtoList);
		
		ResponseEntity<List<SearchResponseDto>> result=productController.searchProduct("Redmi");
		
		assertEquals(1, result.getBody().size());
	}
	
	@Test
	@DisplayName("Search product:Negative")
	 void searchProductTest_Negative()
	{
		when(productService.searchProduct("Redmi")).thenThrow(new DataNotFoundException("No Products Found"));
		
		DataNotFoundException e = assertThrows(DataNotFoundException.class, () -> {
			productController.searchProduct("Redmi");
		});
		
		assertEquals("No Products Found",e.getMessage());
	}
	@Test
	@DisplayName("Product Name Test:Negative")
	void productNameTest_Negative() {
		
		Set<ConstraintViolation<String>> violations = validator.validate("123");

		violations.forEach(violation -> {
			assertEquals(VALID_PRODUCT_NAME,violation.getMessage());
		});

	}
}
