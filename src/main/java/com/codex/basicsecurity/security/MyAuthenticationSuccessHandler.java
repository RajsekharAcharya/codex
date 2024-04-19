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

import com.codex.basicsecurity.model.MyUserDetails;
import com.codex.basicsecurity.model.UserEntity;
import com.codex.basicsecurity.repository.UserEntityRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @Autowired
    private UserEntityRepository entityRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String redirectUrl;

        if (auth != null) {
            // Cast the principal to MyUserDetails
            MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
            // Retrieve the UserEntity from MyUserDetails
            UserEntity user = myUserDetails.getUser();

            if (user.isTwoFactorEnabled()) {
                // Generate OTP and save it to the user entity
                String generatedOtp = generateOtp();
                user.setOtp(generatedOtp);
                entityRepository.save(user);

                // Redirect to the OTP verification page
                redirectUrl = "/login?otp-verification";
            } else {
                // Redirect to the home page
                redirectUrl = "/index";
            }
        } else {
            // Redirect to the error page
            redirectUrl = "/403";
            logger.info("Unauthorized access attempt.");
        }

        // Perform the redirect
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    /**
     * Generate a one-time password (OTP).
     */
    private String generateOtp() {
        // Generate a random OTP (you can customize this method)
        return "1234"; // For simplicity, this is a static OTP
    }
}
