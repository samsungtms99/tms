package com.hunar.api.audit;

import com.hunar.api.security.UserInfoDetails;
import com.hunar.api.security.UserInfoService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

//audit
	@Override
	public Optional<String> getCurrentAuditor() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof UserInfoDetails) {
				UserInfoDetails cuDetails = (UserInfoDetails) principal;
				return Optional.ofNullable(cuDetails.getUsername());
			} else {
				return Optional.of("guest");
			}

		} catch (Exception e) {
			// Log the exception or handle it appropriately
			return Optional.of("guest");
		}

	}

}