package com.ecommerce.controller.test;

import static com.ecommerce.dto.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import com.ecommerce.controller.UserController;
import com.ecommerce.dto.UserLoginRequestDto;
import com.ecommerce.dto.UserLoginResponseDto;
import com.ecommerce.dto.UserRequestDto;
import com.ecommerce.exception.UserExistException;
import com.ecommerce.exception.UserNotExistException;
import com.ecommerce.service.UserService;

@SpringBootTest
class UserControllerTest {

	@Mock
	UserService userservice;

	@InjectMocks
	UserController userController;

	UserRequestDto userRequestDto;

	UserLoginRequestDto userLoginRequestDto;

	UserLoginResponseDto userLoginResponseDto;

	List<UserLoginResponseDto> userLoginResponseDtoList;

	Validator validator;

	@BeforeEach
	void setUp() {

		userRequestDto = new UserRequestDto();
		userRequestDto.setFirstName("Ranjith");
		userRequestDto.setLastName("kumar");
		userRequestDto.setEmailId("ranjith@gmail.com");
		userRequestDto.setPassword("password@123");
		userRequestDto.setPhoneNo("9889556555");
		userRequestDto.setUserName("ranjith@123");

		userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setUserName("ranjith@123");
		userLoginRequestDto.setPassword("password@123");

		userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setOrderId(1);
		userLoginResponseDto.setOrderDateTime(LocalDateTime.now());
		userLoginResponseDto.setTotalPrice(500.00);

		userLoginResponseDtoList = new ArrayList<UserLoginResponseDto>();

		userLoginResponseDtoList.add(userLoginResponseDto);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@Test
	@DisplayName("Save User Data:Positive")
	void saveUserDataTest_Positive() {
		// context

		when(userservice.saveUserData(userRequestDto)).thenReturn("User saved successfully");

		// event
		ResponseEntity<String> result = userController.saveUserData(userRequestDto);

		// outcome
		assertEquals("User saved successfully", result.getBody());

	}

	@Test
	@DisplayName("Save User Data:Negative")
	void saveUserDataTest_Negative() {
		// context

		when(userservice.saveUserData(userRequestDto)).thenThrow(new UserExistException("Username Already Exist"));

		// event
		UserExistException e = assertThrows(UserExistException.class, () -> {
			userController.saveUserData(userRequestDto);
		});

		// outcome
		assertEquals("Username Already Exist", e.getMessage());

	}

	@Test
	@DisplayName("User Login:Positive")
	void userLoginTest_Positive() {
		// context

		when(userservice.userLogin(userLoginRequestDto)).thenReturn(userLoginResponseDtoList);

		// event
		ResponseEntity<List<UserLoginResponseDto>> result = userController.userLogin(userLoginRequestDto);

		// outcome
		assertEquals(1, result.getBody().size());

	}

	@Test
	@DisplayName("User Login:Negative")
	void userLoginTest_Negative() {
		// context

		when(userservice.userLogin(userLoginRequestDto)).thenThrow(new UserNotExistException("No User Found"));

		UserNotExistException e = assertThrows(UserNotExistException.class, () -> {
			userController.userLogin(userLoginRequestDto);
		});

		// outcome
		assertEquals("No User Found", e.getMessage());

	}

	@Test
	@DisplayName("firstName Test:Negative")
	void firstNameTest_Negative() {
		userRequestDto.setFirstName("123");
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		violations.forEach(violation -> {
			assertEquals(VALID_FIRSTNAME_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("lastName Test:Negative")
	void lastNameTest_Negative() {
		userRequestDto.setLastName("123");
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		violations.forEach(violation -> {
			assertEquals(VALID_LASTNAME_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("Email Test:Negative")
	void emailTest_Negative() {
		userRequestDto.setEmailId("ranjithgmail");;
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		violations.forEach(violation -> {
			assertEquals(VALID_EMAIL_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("PhoneNoTest:Negative")
	void phoneNoTest_Negative() {
		userRequestDto.setPhoneNo("88994");;
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		violations.forEach(violation -> {
			assertEquals(VALID_PHONENO_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("PasswordTest:Negative")
	void passwordTest_Negative() {
		userRequestDto.setPassword("ranj");;
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		violations.forEach(violation -> {
			assertEquals(VALID_PASSWORD_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("UserNameTest:Negative")
	void userNameTest_Negative() {
		userRequestDto.setUserName("");;
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		violations.forEach(violation -> {
			assertEquals(USERNAME_EMPTY_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("All Field Test:Positive")
	void allFieldTest_Positive() {
		
		Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

		assertTrue(violations.isEmpty());

	}
	@Test
	@DisplayName("UserName Empty Test:Negative")
	void userNameEmptyTest_Negative() {
		userLoginRequestDto.setUserName("");;
		Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(userLoginRequestDto);

		violations.forEach(violation -> {
			assertEquals(USERNAME_EMPTY_MESSAGE,violation.getMessage());
		});

	}
	
	@Test
	@DisplayName("Password Empty Test:Negative")
	void passwordEmptyTest_Negative() {
		userLoginRequestDto.setPassword("");;
		Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(userLoginRequestDto);

		violations.forEach(violation -> {
			assertEquals(PASSWORD_EMPTY_MESSAGE,violation.getMessage());
		});

	}
	@Test
	@DisplayName("All Field Test for Login:Positive")
	void allFieldTestForLogin_Positive() {
		
		Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(userLoginRequestDto);

		assertTrue(violations.isEmpty());

	}


}
