package com.mettle.feature.service;


import com.mettle.feature.db.User;
import com.mettle.feature.db.enums.Role;
import com.mettle.feature.db.enums.Status;
import com.mettle.feature.repository.UserRepository;
import com.mettle.feature.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserAuthenticationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String logonName) throws UsernameNotFoundException {

		User applicationUser = userRepository.findByLogonName(logonName);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(logonName);
		}

		boolean enabled = false;
		if (applicationUser.getStatus() == Status.ACTIVE) {
			enabled = true;
		}

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		AuthUser authUser = new AuthUser(logonName,
				applicationUser.getPassword(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(applicationUser.getRole()),
				applicationUser.getId(),
				applicationUser.getFirstName(),
				applicationUser.getLastName(),
				applicationUser.getRole());

		return authUser;
	}

	public static List<GrantedAuthority> getAuthorities(Role role) {

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(role.name()));

		return authorities;
	}

}