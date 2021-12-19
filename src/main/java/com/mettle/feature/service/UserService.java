package com.mettle.feature.service;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.User;
import com.mettle.feature.db.enums.Status;
import com.mettle.feature.exception.NotFoundException;
import com.mettle.feature.repository.FeatureRepository;
import com.mettle.feature.repository.UserRepository;
import com.mettle.feature.request.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This service class will be doing creation users and retrieving user detail.
 */

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final FeatureRepository featureRepository;

	@Autowired
	public UserService(UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			FeatureRepository featureRepository) {

		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.featureRepository = featureRepository;
	}

	/**
	 * This method will create new user.
	 *
	 * @param newUserRequest
	 * 		new user details will be in here
	 * @return created user detail
	 */
	@Transactional
	public User createUser(NewUserRequest newUserRequest) {

		User user = new User();

		user.setFirstName(newUserRequest.getFirstName());
		user.setLastName(newUserRequest.getLastName());
		user.setEmail(newUserRequest.getEmail());
		user.setStatus(newUserRequest.getStatus());
		user.setRole(newUserRequest.getRole());

		user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));

		return userRepository.save(user);

	}

	/**
	 * This method will return user details for given id
	 *
	 * @param id
	 * 		user id
	 * @return user object will return
	 */
	public User getUser(Long id) {

		return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found", id));

	}

	/**
	 * This method will mark user as deleted
	 *
	 * @param id
	 * 		user id
	 */
	@Transactional
	public void deleteUser(Long id) {

		userRepository.updateStatus(id, Status.DELETED);

	}

	public List<Feature> getUserFeatures(Long userId, Integer offset, Integer limit) {

		return featureRepository.getFeaturesByUserId(userId,
				Status.ACTIVE,
				Status.ACTIVE,
				PageRequest.of(offset, limit, Sort.by("createdAt").descending()));

	}
}
