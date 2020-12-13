package com.bfyamada.jwtapi.api.controllers;

import com.bfyamada.jwtapi.api.security.jwt.JwtTokenUtil;
import com.bfyamada.jwtapi.core.dto.LoginRequest;
import com.bfyamada.jwtapi.core.dto.JwtResponse;
import com.bfyamada.jwtapi.core.dto.SignupRequest;
import com.bfyamada.jwtapi.service.AuthenticationService;
import com.bfyamada.jwtapi.service.JwtUserDetailsService;
import com.bfyamada.jwtapi.core.models.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signUp(signupRequest.getUsername(),signupRequest.getEmail(),signupRequest.getPassword(),signupRequest.getRoles()));
    }


}
