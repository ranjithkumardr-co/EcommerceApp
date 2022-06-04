package com.ecommerce.controller;

import static com.ecommerce.dto.Constants.*;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.SearchResponseDto;
import com.ecommerce.service.ProductService;
@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	ProductService productService;
	
	@PostMapping(value = "/search")
	public ResponseEntity<List<SearchResponseDto>> searchProduct(@Pattern(message = VALID_PRODUCT_NAME ,regexp="(^[a-zA-Z]+$)") @RequestParam("productName")String productname) {

		return new ResponseEntity<>(productService.searchProduct(productname), HttpStatus.OK);

	}

}
