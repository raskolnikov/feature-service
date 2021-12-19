package com.mettle.feature.db;

import com.mettle.feature.db.enums.AccessType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity()
@Table(name = "features")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Feature extends DbItem {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(generator = "feature_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "feature_sequence", sequenceName = "feature_sequence", allocationSize = 50)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "access_type")
	private AccessType accessType;

}


