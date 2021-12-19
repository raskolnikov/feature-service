package com.mettle.feature.request;

import com.mettle.feature.db.enums.Role;
import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@Getter
@Setter
public class NewUserRequest {

	@NotBlank(message = "First name is mandatory")
	private String firstName;
	@NotBlank(message = "Last name is mandatory")
	private String lastName;
	@NotNull(message = "Role is mandatory")
	private Role role;
	@NotNull(message = "Email is mandatory")
	@Email(message = "Email is invalid")
	private String email;
	private String mobileNumber;
	@NotBlank(message = "Password can not be blank")
	private String password;

	private Status status = Status.ACTIVE;

}
