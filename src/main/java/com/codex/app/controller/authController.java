package com.codex.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codex.app.jwt.JwtUtil;
import com.codex.app.modal.AuthenticationRequest;
import com.codex.app.modal.AuthenticationResponse;
import com.codex.app.modal.MyUserDetail;
import com.codex.app.response.ResponseHandler;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class authController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Authenticate the user
            final Authentication auth = doAuthenticate(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword());
                    // MyUserDetail user = (MyUserDetail) auth.getPrincipal();

            // Check if the user has an active subscription plan
                SecurityContextHolder.getContext().setAuthentication(auth);
                String token = jwtUtil.generateToken(auth);
                
                return ResponseHandler.generateResponse("Authentication Token Use with [Bearer ]", HttpStatus.ACCEPTED, new AuthenticationResponse(token));

        } catch (AuthenticationException e) {
            return ResponseHandler.generateResponse("Invalid username or password", HttpStatus.UNAUTHORIZED, null);
        }
    }

    private Authentication doAuthenticate(String username, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }



}
