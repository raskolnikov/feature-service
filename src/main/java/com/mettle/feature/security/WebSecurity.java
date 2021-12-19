package com.mettle.feature.security;

import com.mettle.feature.db.enums.Role;
import com.mettle.feature.service.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static com.mettle.feature.security.JwtAuthenticationFilter.JWT_TOKEN;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final UserAuthenticationService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public WebSecurity(UserAuthenticationService userDetailsService, PasswordEncoder passwordEncoder) {

		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;

	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
				.expressionHandler(webExpressionHandler())
				.antMatchers("api/v1/*")
				.hasRole(Role.USER.name())
				.antMatchers("api/v1/admin/*")
				.hasRole(Role.ADMIN.name())
				.anyRequest()
				.authenticated()
				.and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		final CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With",
				"Content-Type",
				"Accept",
				"Origin",
				"Authorization"));
		configuration.setExposedHeaders(Arrays.asList("Date", JWT_TOKEN));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public RoleHierarchy roleHierarchy() {

		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

		String hierarchy = Role.ADMIN.name().concat(" > ").concat(Role.USER.name());
		roleHierarchy.setHierarchy(hierarchy);

		return roleHierarchy;
	}

	private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {

		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());

		return defaultWebSecurityExpressionHandler;
	}


}
