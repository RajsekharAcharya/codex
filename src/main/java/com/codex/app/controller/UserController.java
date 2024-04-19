package com.codex.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codex.app.repository.UserEntityRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserEntityRepository userEntityRepository;

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userEntityRepository.findAll());
    }
    
    
}
