package com.mettle.feature.config;

import com.mettle.feature.security.PrincipleAuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

	@Bean
	public AuditorAware<Long> auditorProvider() {

		return () -> {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication == null || !authentication.isAuthenticated()) {
				return Optional.empty();
			}

			PrincipleAuthUser principleAuthUser = ((PrincipleAuthUser) authentication.getPrincipal());

			return Optional.of(principleAuthUser.getId());
		};
	}
}