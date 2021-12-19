package com.mettle.feature.response;

import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String email;
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
