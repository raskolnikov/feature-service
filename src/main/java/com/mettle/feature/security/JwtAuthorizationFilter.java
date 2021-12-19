package com.mettle.feature.security;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mettle.feature.db.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mettle.feature.security.JwtAuthenticationFilter.SECRET;
import static com.mettle.feature.security.JwtAuthenticationFilter.TOKEN_PREFIX;
import static com.mettle.feature.service.UserAuthenticationService.getAuthorities;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public static final String HEADER_STRING = "Authorization";

	public JwtAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {

			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
					.build()
					.verify(token.replace(TOKEN_PREFIX, ""));

			PrincipleAuthUser principleAuthUser = new PrincipleAuthUser();

			principleAuthUser.setId(decodedJWT.getClaim("id").asLong());
			principleAuthUser.setFirstName(decodedJWT.getClaim("firstName").asString());
			principleAuthUser.setLastName(decodedJWT.getClaim("lastName").asString());
			principleAuthUser.setRole(Role.create(decodedJWT.getClaim("role").asString()));

			return new UsernamePasswordAuthenticationToken(principleAuthUser,
					null,
					getAuthorities(principleAuthUser.getRole()));

		}

		return null;
	}
}

