package com.mettle.feature.db;

import com.mettle.feature.db.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class DbItem {

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(name = "created_by", nullable = false)
	@CreatedBy
	private long createdBy;

	@Column(name = "updated_at", nullable = false)
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(name = "updated_by", nullable = false)
	@LastModifiedBy
	private long updatedBy;


}
