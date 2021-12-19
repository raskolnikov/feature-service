package com.mettle.feature.repository;


import com.mettle.feature.db.Feature;
import com.mettle.feature.db.User;
import com.mettle.feature.db.enums.Role;
import com.mettle.feature.db.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT u FROM User u WHERE u.email =:logonName or u.mobileNumber=:logonName")
	User findByLogonName(@Param("logonName") String logonName);

	@Modifying
	@Query("UPDATE User SET status= :status WHERE id= :id")
	void updateStatus(@Param("id") long id, @Param("status") Status status);

}
