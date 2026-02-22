package org.siar.infrastructure.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.siar.domain.model.User;
import org.siar.domain.service.TokenService;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtTokenService implements TokenService {

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://siar.org/issuer")
    String issuer;

    @Override
    public String generateToken(User user) {
        Set<String> groups = new HashSet<>();
        groups.add("User"); // Por defecto todos son usuarios. Podrías añadir "Admin" si el usuario lo fuera.

        return Jwt.issuer(issuer)
                .upn(user.getUsername())
                .subject(user.getId().toString())
                .groups(groups)
                .claim("email", user.getEmail())
                .expiresIn(Duration.ofHours(24)) // Token válido por 24 horas
                .sign();
    }
}
