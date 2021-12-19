package com.mettle.feature.request;

import com.mettle.feature.db.enums.AccessType;
import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@Getter
@Setter
public class NewFeatureRequest {

	@NotNull(message = "Name is mandatory")
	private String name;
	private String description;
	private Status status = Status.INACTIVE;
	@NotNull(message = "Access type is mandatory")
	private AccessType accessType;

}
