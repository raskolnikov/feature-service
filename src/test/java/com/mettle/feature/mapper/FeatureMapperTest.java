package com.mettle.feature.mapper;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.enums.AccessType;
import com.mettle.feature.db.enums.Status;
import com.mettle.feature.response.FeatureDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FeatureMapperTest {

	@InjectMocks
	private FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);

	@Test
	void convertToFeatureDto_whenFeatureIsGivenThenItShouldConvertToFeatureDto() {

		Feature expected = new Feature();

		expected.setId(11L);
		expected.setStatus(Status.ACTIVE);
		expected.setName("My test account");
		expected.setDescription("description");
		expected.setAccessType(AccessType.CUSTOM);

		FeatureDto actual = featureMapper.convertToFeatureDto(expected);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


	}
}