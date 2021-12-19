package com.mettle.feature.controller;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.User;
import com.mettle.feature.mapper.FeatureMapper;
import com.mettle.feature.mapper.UserMapper;
import com.mettle.feature.request.NewUserRequest;
import com.mettle.feature.response.FeatureDto;
import com.mettle.feature.response.UserDto;
import com.mettle.feature.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This API Controller will be responsible for accepting user CRUD or related operations
 */

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	private final UserMapper userMapper;
	private final FeatureMapper featureMapper;

	@Autowired
	public UserController(UserService userService, UserMapper userMapper, FeatureMapper featureMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.featureMapper = featureMapper;
	}


	/**
	 * This API method will create new user
	 *
	 * @param newUserRequest
	 * 		new user details will be in here
	 * @return created user detail
	 */
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public UserDto createUser(@Valid @RequestBody NewUserRequest newUserRequest) {

		User user = userService.createUser(newUserRequest);

		return userMapper.convertToUserDto(user);

	}

	/**
	 * This API method will retrieve user detail for given @param id
	 *
	 * @param id
	 * 		user id
	 * @return user details
	 */
	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public UserDto getUser(@PathVariable("id") long id) {

		User user = userService.getUser(id);

		return userMapper.convertToUserDto(user);

	}

	/**
	 * This API method will delete user for given @param id
	 *
	 * @param id
	 * 		user id
	 * @return user details
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public String deleteUser(@PathVariable("id") long id) {

		userService.deleteUser(id);

		return String.format("User %s has been deleted", id);
	}

}
