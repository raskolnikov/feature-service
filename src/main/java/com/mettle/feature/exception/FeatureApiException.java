package com.mettle.feature.exception;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

public class FeatureApiException extends RuntimeException {

	private static final long serialVersionUID = -7785949343623980782L;

	public FeatureApiException() {
		super();
	}

	public FeatureApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public FeatureApiException(String message) {
		super(message);
	}

	public FeatureApiException(Throwable cause) {
		super(cause);
	}


}
