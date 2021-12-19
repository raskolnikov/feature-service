package com.mettle.feature.response;

import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PermissionDto {

	private Long id;
	private String name;
	private Long featureId;
	private Long userId;
	private String description;
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
