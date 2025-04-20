package com.hunar.api.service.impl;

import com.hunar.api.entity.UserEntity;
import com.hunar.api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutHandler {

    @Autowired
    UserRepository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        UserEntity storedToken = userRepository.findBySecretToken(jwt);
        if (storedToken != null) {
            storedToken.setSecretToken(null);
            userRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
