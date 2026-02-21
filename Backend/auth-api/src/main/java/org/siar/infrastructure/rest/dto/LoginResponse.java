package org.siar.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token; // Placeholder para JWT
    private String message;
    private UserResponse user;
}
