package com.codex.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codex.app.response.ResponseHandler;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/test")
    public ResponseEntity<?> getMethodName() {
        return ResponseHandler.generateResponse("Working", HttpStatus.ACCEPTED, null);
    }
    
    
}
