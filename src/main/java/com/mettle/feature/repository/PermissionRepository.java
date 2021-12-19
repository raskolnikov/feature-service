package com.mettle.feature.repository;


import com.mettle.feature.db.Permission;
import com.mettle.feature.db.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Modifying
	@Query("UPDATE Permission p SET p.status= :status WHERE p.id= :id")
	void updateStatus(@Param("id") long id, @Param("status") Status status);

}
