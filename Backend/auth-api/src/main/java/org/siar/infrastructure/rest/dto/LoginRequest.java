package org.siar.infrastructure.rest.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // Puede ser username o email
    private String password;
}
