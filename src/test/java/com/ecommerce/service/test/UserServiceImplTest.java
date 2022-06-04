package com.ecommerce.service.test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.controller.UserController;
import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.DataNotFoundException;
import com.ecommerce.exception.UserExistException;
import com.ecommerce.exception.UserNotExistException;
import com.ecommerce.exception.WrongCredentialsException;
import com.ecommerce.repo.UserOrderRepository;
import com.ecommerce.repo.UserRepository;
import com.ecommerce.service.*;
import com.ecommerce.service.impl.UserServiceImpl;

@SpringBootTest
 class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	UserRequestDto userRequestDto;

	@Mock
	UserService userService;

	@Mock
	UserOrderRepository userOrderRepository;

	User user;

	User savedUser;

	UserLoginRequestDto userLoginRequestDto;

	UserLoginResponseDto userLoginResponseDto;

	List<UserLoginResponseDto> userLoginResponseDtoList;

	UserOrder userOrder;

	List<UserOrder> userOrdersList;

	@BeforeEach
	 void setUp() {

		userRequestDto = new UserRequestDto();
		userRequestDto.setFirstName("Ranjith");
		userRequestDto.setLastName("kumar");
		userRequestDto.setEmailId("ranjith@gmail.com");
		userRequestDto.setPassword("password");
		userRequestDto.setPhoneNo("988955655");
		userRequestDto.setUserName("ranjith@123");

		user = new User();
		user.setUserId(1);
		BeanUtils.copyProperties(userRequestDto, user);

		savedUser = new User();

		BeanUtils.copyProperties(user, savedUser);

		userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setUserName("ranjith@123");
		userLoginRequestDto.setPassword("password");

		userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setOrderId(1);
		userLoginResponseDto.setOrderDateTime(LocalDateTime.now());
		userLoginResponseDto.setTotalPrice(500.00);

		userLoginResponseDtoList = new ArrayList<UserLoginResponseDto>();

		userLoginResponseDtoList.add(userLoginResponseDto);

		userOrder = new UserOrder();
		BeanUtils.copyProperties(userLoginResponseDto, userOrder);
		userOrder.setUser(user);

		userOrdersList = new ArrayList<>();
		userOrdersList.add(userOrder);

	}

	@Test
	@DisplayName("Save User Data:Positive")
	 void saveUserDataTest_Positive() {
		// context
		when(userRepository.findByUserName(userRequestDto.getUserName())).thenReturn(null);
		when(userRepository.save(user)).thenReturn(savedUser);

		// event
		String result = userServiceImpl.saveUserData(userRequestDto);

		// outcome
		assertEquals("User saved Successfully", result);

	}

	@Test
	@DisplayName("Save User Data:Negative")
	 void saveUserDataTest_Negative() {
		// context

		when(userRepository.findByUserName(userRequestDto.getUserName())).thenReturn(user);

		// event
		UserExistException e = assertThrows(UserExistException.class, () -> {
			userServiceImpl.saveUserData(userRequestDto);
		});

		// outcome
		assertEquals("Username already Exist please try with different Username", e.getMessage());

	}

	@Test
	@DisplayName("User Login:Positive")
	 void userLoginTest_Positive() {
		// context

		when(userRepository.findByUserName(userLoginRequestDto.getUserName())).thenReturn(user);
		when(userOrderRepository.findByUser(user)).thenReturn(userOrdersList);

		// event
		List<UserLoginResponseDto> result = userServiceImpl.userLogin(userLoginRequestDto);

		// outcome
		assertEquals(1, result.size());

	}

	@Test
	@DisplayName("User Login Wrong Username:Negative")
	 void userLoginWrongUsernameTest_Negative() {
		// context

		when(userRepository.findByUserName(userLoginRequestDto.getUserName())).thenReturn(null);
		UserNotExistException e = assertThrows(UserNotExistException.class, () -> {
			userServiceImpl.userLogin(userLoginRequestDto);
		});

		// outcome
		assertEquals("No User Exist please Register", e.getMessage());

	}

	@Test
	@DisplayName("User Login Wrong Password:Negative")
	 void userLoginWrongPasswordTest_Negative() {
		// context

		user.setPassword("wrongpassword");
		when(userRepository.findByUserName(userLoginRequestDto.getUserName())).thenReturn(user);
		WrongCredentialsException e = assertThrows(WrongCredentialsException.class, () -> {
			userServiceImpl.userLogin(userLoginRequestDto);
		});

		// outcome
		assertEquals("Wrong password please enter correct password", e.getMessage());

	}

	@Test
	@DisplayName("DataNotFoundTest:Negative")
	 void DataNotFound_Negative() {
		// context

		when(userRepository.findByUserName(userLoginRequestDto.getUserName())).thenReturn(user);
		DataNotFoundException e = assertThrows(DataNotFoundException.class, () -> {
			userServiceImpl.userLogin(userLoginRequestDto);
		});

		// outcome
		assertEquals("Login Successful But No Orders Found", e.getMessage());

	}

}
