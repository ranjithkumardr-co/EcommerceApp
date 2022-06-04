package com.ecommerce.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserLoginRequestDto;
import com.ecommerce.dto.UserLoginResponseDto;
import com.ecommerce.dto.UserRequestDto;
import com.ecommerce.service.UserService;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/register")
	public ResponseEntity<String> saveUserData(@Valid @RequestBody UserRequestDto userRequestDto) {

		return new ResponseEntity<>(userService.saveUserData(userRequestDto), HttpStatus.OK);

	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<List<UserLoginResponseDto>> userLogin(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {

		return new ResponseEntity<>(userService.userLogin(userLoginRequestDto), HttpStatus.OK);

	}
	

}
