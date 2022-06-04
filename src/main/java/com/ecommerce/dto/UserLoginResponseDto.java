package com.ecommerce.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserLoginResponseDto {

	private Integer orderId;
	private LocalDateTime orderDateTime;
	private Double totalPrice;

}
