package com.codex.basicsecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("List Data");
    }
    
    
}
