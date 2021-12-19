package com.mettle.feature.controller;

import com.mettle.feature.db.Feature;
import com.mettle.feature.mapper.FeatureMapper;
import com.mettle.feature.request.NewFeatureRequest;
import com.mettle.feature.response.FeatureDto;
import com.mettle.feature.service.FeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This API Controller will be responsible for accepting feature CRUD or related operations
 */

@RestController
@RequestMapping("/api/v1/admin/features")
public class FeatureController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);

	private final FeatureService featureService;
	private final FeatureMapper featureMapper;

	@Autowired
	public FeatureController(FeatureService featureService, FeatureMapper featureMapper) {
		this.featureService = featureService;
		this.featureMapper = featureMapper;
	}


	/**
	 * This API method will return features.
	 * <p>
	 * Returning list will contain all features
	 *
	 * @param offset
	 * 		starting point of list
	 * @param limit
	 * 		size of returning list
	 * @return transfer list
	 */
	@GetMapping(value = "/", produces = "application/json")
	public List<FeatureDto> getFeatures(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "25") Integer limit) {

		Page<Feature> featureList = featureService.getFeatures(offset, limit);

		return featureList.get().map(featureMapper::convertToFeatureDto).collect(Collectors.toList());

	}

	/**
	 * This API method will create new feature
	 *
	 * @param newFeatureRequest
	 * 		new feature details will be in here
	 * @return created feature detail
	 */
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public FeatureDto createFeature(@Valid @RequestBody NewFeatureRequest newFeatureRequest) {

		Feature feature = featureService.createFeature(newFeatureRequest);

		return featureMapper.convertToFeatureDto(feature);

	}

	/**
	 * This API method will retrieve feature detail for given @param id
	 *
	 * @param id
	 * 		feature id
	 * @return feature details
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	public FeatureDto getFeature(@PathVariable("id") long id) {

		Feature feature = featureService.getFeature(id);

		return featureMapper.convertToFeatureDto(feature);

	}


}
