package com.mettle.feature.db;

import com.mettle.feature.db.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity()
@Table(name = "users")
@Getter
@Setter
public class User extends DbItem {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "user_sequence", sequenceName = "permission_sequence", allocationSize = 50)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email" , nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;


}


