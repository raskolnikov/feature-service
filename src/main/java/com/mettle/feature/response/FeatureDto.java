package com.mettle.feature.response;

import com.mettle.feature.db.enums.AccessType;
import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeatureDto {

	private Long id;
	private String name;
	private String description;
	private AccessType accessType;
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
