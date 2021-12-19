package com.mettle.feature.service;

import com.mettle.feature.db.Feature;
import com.mettle.feature.exception.NotFoundException;
import com.mettle.feature.repository.FeatureRepository;
import com.mettle.feature.request.NewFeatureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This service class will be doing creation features and retrieving feature detail.
 */

@Service
public class FeatureService {

	private final FeatureRepository featureRepository;

	@Autowired
	public FeatureService(FeatureRepository featureRepository) {

		this.featureRepository = featureRepository;
	}

	/**
	 * This method will create new feature.
	 *
	 * @param newFeatureRequest
	 * 		new feature details will be in here
	 * @return created feature detail
	 */
	@Transactional
	public Feature createFeature(NewFeatureRequest newFeatureRequest) {

		Feature feature = new Feature();

		feature.setName(newFeatureRequest.getName());
		feature.setDescription(newFeatureRequest.getDescription());
		feature.setStatus(newFeatureRequest.getStatus());
		feature.setAccessType(newFeatureRequest.getAccessType());

		return featureRepository.save(feature);

	}

	/**
	 * This method will return feature details for given id
	 *
	 * @param id
	 * 		feature id
	 * @return feature object will return
	 */
	public Feature getFeature(Long id) {

		return featureRepository.findById(id).orElseThrow(() -> new NotFoundException("Feature not found", id));

	}


	/**
	 * This  method will retrieve features for given filtering params.
	 * <p>
	 *
	 * @param offset
	 * 		starting point of list
	 * @param limit
	 * 		size of returning list
	 * @return transfer list
	 */
	public Page<Feature> getFeatures(Integer offset, Integer limit) {

		return featureRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));

	}

}
