package com.ecommerce.exception;

public class UserNotExistException extends  RuntimeException {
	
	public UserNotExistException(String message) {
		super(message);
	}

}
