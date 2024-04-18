package com.codex.basicsecurity.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.codex.basicsecurity.model.UserEntity;
import com.codex.basicsecurity.repository.UserEntityRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @Autowired
    UserEntityRepository entityRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String redirectUrl = null;
        if (auth != null) {
            UserEntity user = (UserEntity) auth.getPrincipal();
            if (user.isTwoFactorEnabled()) {
                user.setOtp("1234");
                entityRepository.save(user);
                redirectUrl = "/otp-verification";
            } else {
                redirectUrl = "/index";
            }
        } else {
            redirectUrl = "/403";
            logger.info("Not Authorize");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
