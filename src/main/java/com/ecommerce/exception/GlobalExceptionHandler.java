package com.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleException(MethodArgumentNotValidException ex) {
		List<FieldError> errors = ex.getFieldErrors();
		ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
		validationErrorResponse.setDateTime(LocalDateTime.now());
		validationErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		validationErrorResponse.setMessage("Input data has some errors... Please provide proper data");

		for (FieldError f : errors) {
			validationErrorResponse.getErrors().put(f.getField(), f.getDefaultMessage());
		}

		return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ValidationErrorResponse> handleException(ConstraintViolationException ex) {
		ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
		validationErrorResponse.setDateTime(LocalDateTime.now());
		validationErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		validationErrorResponse.setMessage("Input data has some errors... Please provide proper data");

		ex.getConstraintViolations()
				.forEach(error -> validationErrorResponse.getErrors().put("field", error.getMessage()));

		return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(IdNotFoundException ex) {

		ErrorResponse ErrorResponse = new ErrorResponse();
		ErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		ErrorResponse.setDateTime(LocalDateTime.now());
		ErrorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<ErrorResponse> handleException(UserExistException ex) {

		ErrorResponse ErrorResponse = new ErrorResponse();
		ErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		ErrorResponse.setDateTime(LocalDateTime.now());
		ErrorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<ErrorResponse> handleException(UserNotExistException ex) {

		ErrorResponse ErrorResponse = new ErrorResponse();
		ErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		ErrorResponse.setDateTime(LocalDateTime.now());
		ErrorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(DataNotFoundException ex) {

		ErrorResponse ErrorResponse = new ErrorResponse();
		ErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		ErrorResponse.setDateTime(LocalDateTime.now());
		ErrorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleException(NoSuchElementException ex) {
		ErrorResponse ErrorResponse = new ErrorResponse();
		ErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		ErrorResponse.setDateTime(LocalDateTime.now());
		ErrorResponse.setMessage("No product found with the product Id");

		return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(WrongCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleException(WrongCredentialsException ex) {

		ErrorResponse ErrorResponse = new ErrorResponse();
		ErrorResponse.setStatuscode(HttpStatus.BAD_REQUEST.value());
		ErrorResponse.setDateTime(LocalDateTime.now());
		ErrorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);

	}

}
