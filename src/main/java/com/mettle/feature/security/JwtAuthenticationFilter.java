package com.mettle.feature.security;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mettle.feature.request.LoginRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN = "jwtToken";


	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		try {

			LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getLogonName(),
					creds.getPassword(),
					new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth) throws IOException {

		AuthUser authUser = (AuthUser) auth.getPrincipal();
		String token = JWT.create()
				.withClaim("firstName", authUser.getFirstName())
				.withClaim("lastName", authUser.getLastName())
				.withClaim("role", authUser.getRole().name())
				.withClaim("id", authUser.getId())
				.withSubject(String.valueOf(authUser.getId()))
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(HMAC512(SECRET.getBytes()));

		res.resetBuffer();

		res.setStatus(HttpStatus.OK.value());
		res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		res.getOutputStream().print(new ObjectMapper().writeValueAsString(authUser));
		res.addHeader(JWT_TOKEN, TOKEN_PREFIX + token);

		res.flushBuffer();

	}
}