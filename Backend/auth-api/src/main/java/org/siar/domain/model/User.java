package org.siar.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;
    private String email;
    private String passwordHash;

    private UserStatus status;

    private int failedAttempts;
    private LocalDateTime blockedUntil;

    private LocalDateTime lastLoginAt;
    private String lastLoginIp;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
     * ==========
     * Reglas de negocio básicas
     * ==========
     */

    public boolean isBlocked() {
        if (status != UserStatus.BLOCKED) return false;
        if (blockedUntil == null) return false;
        return blockedUntil.isAfter(LocalDateTime.now());
    }

    public void registerFailedAttempt(int maxAttempts, int blockMinutes) {
        this.failedAttempts++;

        if (this.failedAttempts >= maxAttempts) {
            this.status = UserStatus.BLOCKED;
            this.blockedUntil = LocalDateTime.now().plusMinutes(blockMinutes);
        }
    }

    public void registerSuccessfulLogin(String ip) {
        this.failedAttempts = 0;
        this.status = UserStatus.ACTIVE;
        this.blockedUntil = null;
        this.lastLoginAt = LocalDateTime.now();
        this.lastLoginIp = ip;
    }
}

