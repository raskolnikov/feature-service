package com.mettle.feature.exception;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */


public class NotFoundException extends FeatureApiException {

	private Long itemId;

	public NotFoundException(String message) {

		super(message);
	}

	public NotFoundException(String message, Long itemId) {

		this(message);
		this.itemId = itemId;
	}

}
