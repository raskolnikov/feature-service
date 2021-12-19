package com.mettle.feature.service;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.User;
import com.mettle.feature.db.enums.Role;
import com.mettle.feature.db.enums.Status;
import com.mettle.feature.repository.FeatureRepository;
import com.mettle.feature.repository.UserRepository;
import com.mettle.feature.request.NewUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private FeatureRepository featureRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void createUser_whenValidRequestIsGivenThenItShouldCreateNewUser() {

		NewUserRequest newUserRequest = new NewUserRequest();

		newUserRequest.setPassword("123");
		newUserRequest.setRole(Role.USER);
		newUserRequest.setEmail("my@yopmail.com");
		newUserRequest.setStatus(Status.ACTIVE);
		newUserRequest.setFirstName("Mehmet");
		newUserRequest.setLastName("Hey");
		newUserRequest.setMobileNumber("447777777777");


		User createdUSer = mock(User.class);

		when(userRepository.save(any(User.class))).thenReturn(createdUSer);

		User actual = userService.createUser(newUserRequest);

		assertThat(actual).isEqualTo(createdUSer);

		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

		verify(userRepository, times(1)).save(argumentCaptor.capture());

		User capturedUser = argumentCaptor.getValue();

		assertThat(capturedUser.getId()).isNull();
		assertThat(capturedUser.getEmail()).isEqualTo(newUserRequest.getEmail());
		assertThat(capturedUser.getFirstName()).isEqualTo(newUserRequest.getFirstName());
		assertThat(capturedUser.getLastName()).isEqualTo(newUserRequest.getLastName());
		assertThat(capturedUser.getPassword()).isNotEqualTo(newUserRequest.getPassword());
		assertThat(capturedUser.getMobileNumber()).isNotEqualTo(newUserRequest.getMobileNumber());

		verify(passwordEncoder, times(1)).encode(newUserRequest.getPassword());

		verifyNoMoreInteractions(userRepository, passwordEncoder);

	}

	@Test
	void getUser_whenIdIsGivenThenItShouldReturnUser() {

		Long userId = 12L;
		User user = mock(User.class);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		User actual = userService.getUser(userId);

		assertThat(actual).isEqualTo(user);

	}

	@Test
	void deleteUser_whenIdIsGivenThenItShouldUpdateStatusToDelete() {

		long userId = 12L;

		userService.deleteUser(userId);

		verify(userRepository, times(1)).updateStatus(userId, Status.DELETED);
		verifyNoMoreInteractions(userRepository);

	}

	@Test
	void getUserFeatures_whenUserIdIsGivenThenItShouldReturnUserEnabledFeatures() {

		Long userId = 12L;
		Integer offset = 0;
		Integer limit = 10;

		User user = new User();

		user.setRole(Role.USER);
		user.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Feature feature = mock(Feature.class);
		List<Feature> featureList = Collections.nCopies(2, feature);

		when(featureRepository.getFeaturesByUserId(eq(userId),
				eq(Status.ACTIVE),
				eq(Status.ACTIVE),
				any(PageRequest.class))).thenReturn(featureList);

		List<Feature> actualFeatureList = userService.getUserFeatures(userId, offset, limit);

		assertThat(actualFeatureList).containsExactlyElementsOf(featureList);

	}
}