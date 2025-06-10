package com.sreekutt.ProjectManagementSystem.controller;

import com.sreekutt.ProjectManagementSystem.io.AuthRequest;
import com.sreekutt.ProjectManagementSystem.service.AppUserDetailsService;
import com.sreekutt.ProjectManagementSystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserDetailsService userDetailsService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        System.out.println("Extracted email from AAAA: " + request.getEmail());
        authenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        ResponseCookie cookie =ResponseCookie.from("jwt",jwtToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(jwtToken);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully");
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
    }
}
