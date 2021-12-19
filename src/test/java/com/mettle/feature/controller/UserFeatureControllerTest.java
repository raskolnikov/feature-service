package com.mettle.feature.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mettle.feature.db.Feature;
import com.mettle.feature.mapper.FeatureMapper;
import com.mettle.feature.response.FeatureDto;
import com.mettle.feature.service.UserAuthenticationService;
import com.mettle.feature.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserFeatureController.class)
class UserFeatureControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private FeatureMapper featureMapper;

	@MockBean
	private UserAuthenticationService userAuthenticationService;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ObjectMapper objectMapper;

	private String accountControllerPath = "/api/v1/users";


	@Test
	@WithMockUser()
	void getUserFeatures_whenValidParamsAreGivenThenItShouldReturnUserFeatureList() throws Exception {

		Long userId = 22L;
		Integer offset = 0;
		Integer limit = 3;

		Feature feature = mock(Feature.class);
		List<Feature> featureList = Collections.nCopies(limit, feature);

		when(userService.getUserFeatures(userId, offset, limit)).thenReturn(featureList);

		FeatureDto featureDto = new FeatureDto();
		featureDto.setId(12L);

		List<FeatureDto> expectedTransferDtoList = Collections.nCopies(limit, featureDto);

		when(featureMapper.convertToFeatureDto(feature)).thenReturn(featureDto);

		MvcResult mvcResult = this.mockMvc.perform(get(accountControllerPath + "/{id}/features", userId).contentType(
				MediaType.APPLICATION_JSON).param("offset", offset.toString()).param("limit", limit.toString()))
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		List<FeatureDto> actualTransfers = objectMapper.readValue(content, new TypeReference<List<FeatureDto>>() {
		});

		assertThat(actualTransfers).usingRecursiveFieldByFieldElementComparator().containsAll(expectedTransferDtoList);


	}
}