package com.ecommerce.service;

import java.util.List;

import javax.validation.Valid;

import com.ecommerce.dto.UserLoginRequestDto;
import com.ecommerce.dto.UserLoginResponseDto;
import com.ecommerce.dto.UserRequestDto;

public interface UserService {

	String saveUserData(@Valid UserRequestDto userRequestDto);

	List<UserLoginResponseDto> userLogin(@Valid UserLoginRequestDto userLoginRequestDto);

}
