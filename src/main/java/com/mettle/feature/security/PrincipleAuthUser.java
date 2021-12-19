package com.mettle.feature.security;

import com.mettle.feature.db.enums.Role;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@Getter
@Setter
public class PrincipleAuthUser{

	private long id;
	private String firstName;
	private String lastName;
	private Role role;


}
