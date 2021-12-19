package com.mettle.feature.controller;

import com.mettle.feature.db.Feature;
import com.mettle.feature.mapper.FeatureMapper;
import com.mettle.feature.response.FeatureDto;
import com.mettle.feature.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This API Controller will be responsible for accepting user feature operations
 */

@RestController
@RequestMapping("/api/v1/users")
public class UserFeatureController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserFeatureController.class);

	private final UserService userService;
	private final FeatureMapper featureMapper;

	@Autowired
	public UserFeatureController(UserService userService, FeatureMapper featureMapper) {
		this.userService = userService;
		this.featureMapper = featureMapper;
	}

	/**
	 * This API method will retrieve user enabled features for given @param id
	 *
	 * @param userId
	 * 		user id
	 * @return user enabled features details
	 */
	@GetMapping(value = "/{id}/features", produces = "application/json")
	public List<FeatureDto> getUserFeatures(@PathVariable("id") long userId,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "25") Integer limit) {

		List<Feature> userFeatures = userService.getUserFeatures(userId, offset, limit);

		return userFeatures.stream().map(featureMapper::convertToFeatureDto).collect(Collectors.toList());

	}

}
