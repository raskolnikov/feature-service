package com.mettle.feature.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity()
@Table(name = "permissions")
@Getter
@Setter
public class Permission extends DbItem {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(generator = "permission_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "permission_sequence", sequenceName = "permission_sequence", allocationSize = 50)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "user_id", nullable = false, updatable = false, insertable = false)
	private long userId;

	@Column(name = "feature_id", nullable = false, updatable = false, insertable = false)
	private long featureId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feature_id", nullable = false)
	private Feature feature;

}


