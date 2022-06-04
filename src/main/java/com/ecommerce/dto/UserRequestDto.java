package com.ecommerce.dto;

import static com.ecommerce.dto.Constants.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequestDto {

	@Pattern(message = VALID_FIRSTNAME_MESSAGE, regexp = "(^[a-zA-Z]+$)")
	@NotEmpty(message = FIRSTNAME_EMPTY_MESSAGE)
	private String firstName;

	@Pattern(message = VALID_LASTNAME_MESSAGE, regexp = "(^[a-zA-Z]+$)")
	@NotEmpty(message = LASTNAME_EMPTY_MESSAGE)
	private String lastName;

	@NotEmpty(message = USERNAME_EMPTY_MESSAGE)
	private String userName;
	
	@Size(min = 10, max = 20, message = VALID_PASSWORD_MESSAGE)
	private String password;

	@Pattern(message = VALID_PHONENO_MESSAGE, regexp = "(^$|[0-9]{10})")
	@NotEmpty(message = PHONENO_EMPTY_MESSAGE)
	private String phoneNo;

	@Email(message = VALID_EMAIL_MESSAGE, regexp = "([a-z0-9]+@[a-z]+\\.[a-z]{2,3})")
	@NotEmpty(message = EMAIL_EMPTY_MESSAGE)
	private String emailId;

}
