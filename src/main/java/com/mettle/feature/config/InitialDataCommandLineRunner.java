package com.mettle.feature.config;

import com.mettle.feature.db.enums.Role;
import com.mettle.feature.db.enums.Status;
import com.mettle.feature.request.NewUserRequest;
import com.mettle.feature.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataCommandLineRunner implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitialDataCommandLineRunner.class);

	private final UserService userService;

	public InitialDataCommandLineRunner(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		NewUserRequest newUserRequest = new NewUserRequest();

		newUserRequest.setEmail("mehmet@yopmail.com");
		newUserRequest.setPassword("simple");
		newUserRequest.setFirstName("mehmet");
		newUserRequest.setLastName("ak");
		newUserRequest.setRole(Role.ADMIN);

		newUserRequest.setStatus(Status.ACTIVE);

		userService.createUser(newUserRequest);

		LOGGER.info("User has been created");
		*/

	}
}
