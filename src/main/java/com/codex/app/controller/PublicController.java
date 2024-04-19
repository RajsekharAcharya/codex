package com.codex.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.app.model.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/api")
public class PublicController {

    @GetMapping("/test")
    public ResponseEntity<?> getMethodName() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hello Word");
    }

    @PostMapping("/2fa-verification")
    public String verifyOtp(@RequestParam("otp") String otp, HttpServletRequest request, HttpServletResponse response) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Verify the OTP
        if (user.getOtp().equals(otp)) {
            // OTP is valid; redirect to home page
            return "redirect:/home";
        } else {
            // OTP is invalid; set error message in session
            request.getSession().setAttribute("loginError", "Invalid OTP. Please try again.");
            return "redirect:login?error";
        }
    }
    
    
    
    
}
