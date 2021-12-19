package com.mettle.feature.request;

import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@Getter
@Setter
public class NewPermissionRequest {

	private String name;
	@NotNull(message = "User id is mandatory")
	private Long userId;
	@NotNull(message = "Feature id is mandatory")
	private Long featureId;
	private String description;
	private Status status = Status.ACTIVE;

}
