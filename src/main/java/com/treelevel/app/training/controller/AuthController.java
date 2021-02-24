package com.treelevel.app.training.controller;

import com.treelevel.app.training.model.auth.AuthenticationRequest;
import com.treelevel.app.training.model.auth.AuthenticationResponse;
import com.treelevel.app.training.model.auth.CustomUserDetails;
import com.treelevel.app.training.service.UserDetailsServiceImpl;
import com.treelevel.app.training.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtils;

    @PostMapping("/api/authenticate")
    public ResponseEntity<?> createAuthorizationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }
        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtils.generateToken(userDetails);


        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
