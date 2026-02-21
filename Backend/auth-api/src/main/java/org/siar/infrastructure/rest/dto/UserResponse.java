package org.siar.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.siar.domain.model.UserStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserStatus status;
    private LocalDateTime createdAt;
}
