package org.siar.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siar.domain.model.User;
import org.siar.domain.model.UserStatus;
import org.siar.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldRegisterUserSuccessfully() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Simulate ID generation
            return user;
        });

        // When
        User registeredUser = registerUserUseCase.execute(username, email, password);

        // Then
        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertEquals(email, registeredUser.getEmail());
        assertNotNull(registeredUser.getPasswordHash()); // Password should be hashed
        assertEquals(UserStatus.ACTIVE, registeredUser.getStatus());
        assertNotNull(registeredUser.getCreatedAt());
        assertNotNull(registeredUser.getUpdatedAt());
        assertEquals(0, registeredUser.getFailedAttempts());
    }

    @Test
    void execute_shouldThrowExceptionWhenUsernameExists() {
        // Given
        String username = "existinguser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(User.builder().username(username).build()));
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registerUserUseCase.execute(username, email, password);
        });
        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailExists() {
        // Given
        String username = "testuser";
        String email = "existing@example.com";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().email(email).build()));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registerUserUseCase.execute(username, email, password);
        });
        assertEquals("Email already exists", exception.getMessage());
    }
}
