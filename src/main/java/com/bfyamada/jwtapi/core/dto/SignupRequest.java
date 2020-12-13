package com.bfyamada.jwtapi.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private List<String> roles;
}
