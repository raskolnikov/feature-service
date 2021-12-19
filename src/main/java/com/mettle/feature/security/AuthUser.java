package com.mettle.feature.security;

import com.mettle.feature.db.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class AuthUser extends User {

	private final long id;
	private final String firstName;
	private final String lastName;
	private final Role role;

	public AuthUser(String username,
			String password,
			boolean enabled,
			boolean accountNonExpired,
			boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			long id,
			String firstName,
			String lastName,
			Role role) {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

}
