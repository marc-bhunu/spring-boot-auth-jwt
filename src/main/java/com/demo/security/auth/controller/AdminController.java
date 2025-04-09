package com.demo.security.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET: FROM THE ADMIN CONTROLLER");
    }
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST: FROM THE ADMIN CONTROLLER");
    }
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("DELETE: FROM THE ADMIN CONTROLLER");
    }
    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping
    public ResponseEntity<String> put() {
        return ResponseEntity.ok("PUT: FROM THE ADMIN CONTROLLER");
    }


}
