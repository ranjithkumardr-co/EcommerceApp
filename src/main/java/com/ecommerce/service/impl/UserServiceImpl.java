package com.ecommerce.service.impl;

import static com.ecommerce.dto.Constants.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserLoginRequestDto;
import com.ecommerce.dto.UserLoginResponseDto;
import com.ecommerce.dto.UserRequestDto;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserOrder;
import com.ecommerce.exception.DataNotFoundException;
import com.ecommerce.exception.UserExistException;
import com.ecommerce.exception.UserNotExistException;
import com.ecommerce.exception.WrongCredentialsException;
import com.ecommerce.repo.UserOrderRepository;
import com.ecommerce.repo.UserRepository;
import com.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserOrderRepository userOrderRepository;

	@Override
	public String saveUserData(@Valid UserRequestDto userRequestDto) {

		User user = userRepository.findByUserName(userRequestDto.getUserName());
		if (user != null) {
			throw new UserExistException(USER_EXIST_MESSAGE);
		} else {
			User savedUser = new User();
			BeanUtils.copyProperties(userRequestDto, savedUser);
			savedUser.setPhoneNo(Long.valueOf(userRequestDto.getPhoneNo()));
			userRepository.save(savedUser);

			return "User saved Successfully";
		}

	}

	@Override
	public List<UserLoginResponseDto> userLogin(@Valid UserLoginRequestDto userLoginRequestDto) {

		User user = userRepository.findByUserName(userLoginRequestDto.getUserName());

		List<UserOrder> userOrderList;
		List<UserLoginResponseDto> userLoginResponseDtoList = new ArrayList<>();

		if (user != null) {
			if (userLoginRequestDto.getPassword().equals(user.getPassword())) {

				userOrderList = userOrderRepository.findByUser(user);
				userOrderList.forEach(userOrder -> {
					UserLoginResponseDto userLoginResponseDto= new UserLoginResponseDto();
					BeanUtils.copyProperties(userOrder, userLoginResponseDto);
					userLoginResponseDtoList.add(userLoginResponseDto);
					
				});
				
				if(userLoginResponseDtoList.isEmpty())
					throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
				
				return userLoginResponseDtoList;
			} else {
				throw new WrongCredentialsException(WRONG_CREDENTIAL_MESSAGE);
			}
		} else {
			throw new UserNotExistException(USER_NOT_EXIST_MESSAGE);
		}
	}

}
