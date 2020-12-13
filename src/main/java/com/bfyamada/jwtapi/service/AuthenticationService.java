package com.bfyamada.jwtapi.service;

import com.bfyamada.jwtapi.api.security.jwt.JwtTokenUtil;
import com.bfyamada.jwtapi.core.dto.JwtResponse;
import com.bfyamada.jwtapi.core.dto.MessageResponse;
import com.bfyamada.jwtapi.core.enums.ERole;
import com.bfyamada.jwtapi.core.models.Role;
import com.bfyamada.jwtapi.core.models.User;
import com.bfyamada.jwtapi.core.models.UserDetailsImpl;
import com.bfyamada.jwtapi.core.repositories.RoleRepository;
import com.bfyamada.jwtapi.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public JwtResponse authenticate(String username, String password){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return new JwtResponse(
                token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public MessageResponse signUp(String username, String email, String password, List<String> roles){
        if (userRepository.existsByUsername(username)) {
            return new MessageResponse("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(username,
                email,
                encoder.encode(password));

        List<String> strRoles = roles;
        Set<Role> newRoles = new HashSet<>();


        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            newRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        newRoles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        newRoles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        newRoles.add(userRole);
                }
            });
        }

        user.setRoles(newRoles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");

    }

}
