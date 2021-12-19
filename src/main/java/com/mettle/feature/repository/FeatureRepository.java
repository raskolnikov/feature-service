package com.mettle.feature.repository;


import com.mettle.feature.db.Feature;
import com.mettle.feature.db.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

	@Query("SELECT f from Feature f left join Permission p on (p.feature.id = f.id AND upper(f.accessType)= 'CUSTOM' AND p.status = :permissionStatus ) "
			+ "WHERE (upper(f.accessType) = 'GLOBAL' OR p.user.id = :userId) "
			+ "AND f.status = :featureStatus ")
	List<Feature> getFeaturesByUserId(@Param("userId") long userId,
			@Param("featureStatus") Status featureStatus,
			@Param("permissionStatus") Status permissionStatus,
			Pageable pageable);
}
