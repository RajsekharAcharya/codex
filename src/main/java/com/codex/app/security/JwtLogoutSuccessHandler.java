package com.codex.app.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.codex.app.jwt.JwtUtil;
import com.codex.app.jwt.TokenRevocationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private final TokenRevocationService tokenRevocationService;

    private final JwtUtil jwtUtil;

    public JwtLogoutSuccessHandler(TokenRevocationService tokenRevocationService, JwtUtil jwtUtil) {
        this.tokenRevocationService = tokenRevocationService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String extractToken = jwtUtil.extractToken(request);
        tokenRevocationService.revokeToken(extractToken);
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Logout Successful");
        response.getWriter().flush();
    }

}

