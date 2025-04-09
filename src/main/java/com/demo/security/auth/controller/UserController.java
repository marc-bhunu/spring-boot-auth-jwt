package com.demo.security.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET: FROM THE USER CONTROLLER");
    }

    @PostMapping
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST: FROM THE USER CONTROLLER");
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("DELETE: FROM THE USER CONTROLLER");
    }

    @PutMapping
    public ResponseEntity<String> put() {
        return ResponseEntity.ok("PUT: FROM THE USER CONTROLLER");
    }


}
