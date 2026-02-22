package org.siar.application.usecase;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siar.domain.model.User;
import org.siar.domain.model.UserStatus;
import org.siar.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    private MockedStatic<BcryptUtil> bcryptUtilMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bcryptUtilMock = Mockito.mockStatic(BcryptUtil.class);
    }

    @AfterEach
    void tearDown() {
        bcryptUtilMock.close();
    }

    @Test
    void execute_shouldLoginSuccessfully() {
        // Given
        String username = "testuser";
        String password = "password123";
        String hashedPassword = "hashedPassword";
        String ipAddress = "127.0.0.1";

        User user = User.builder()
                .username(username)
                .passwordHash(hashedPassword)
                .status(UserStatus.ACTIVE)
                .failedAttempts(0)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        bcryptUtilMock.when(() -> BcryptUtil.matches(password, hashedPassword)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        User loggedInUser = loginUserUseCase.execute(username, password, ipAddress);

        // Then
        assertNotNull(loggedInUser);
        assertEquals(0, loggedInUser.getFailedAttempts());
        assertNotNull(loggedInUser.getLastLoginAt());
        assertEquals(ipAddress, loggedInUser.getLastLoginIp());
        verify(userRepository).save(user);
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // Given
        String username = "nonexistent";
        String password = "password";
        String ipAddress = "127.0.0.1";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loginUserUseCase.execute(username, password, ipAddress);
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void execute_shouldThrowExceptionWhenPasswordIsIncorrect() {
        // Given
        String username = "testuser";
        String password = "wrongpassword";
        String hashedPassword = "hashedPassword";
        String ipAddress = "127.0.0.1";

        User user = User.builder()
                .username(username)
                .passwordHash(hashedPassword)
                .status(UserStatus.ACTIVE)
                .failedAttempts(0)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        bcryptUtilMock.when(() -> BcryptUtil.matches(password, hashedPassword)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loginUserUseCase.execute(username, password, ipAddress);
        });
        assertEquals("Invalid credentials", exception.getMessage());
        
        // Verify failed attempts incremented
        assertEquals(1, user.getFailedAttempts());
        verify(userRepository).save(user);
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIsBlocked() {
        // Given
        String username = "blockeduser";
        String password = "password";
        String ipAddress = "127.0.0.1";

        User user = User.builder()
                .username(username)
                .status(UserStatus.BLOCKED)
                .blockedUntil(LocalDateTime.now().plusMinutes(10))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            loginUserUseCase.execute(username, password, ipAddress);
        });
        assertEquals("User is blocked. Try again later.", exception.getMessage());
        
        // Verify no password check was done
        bcryptUtilMock.verifyNoInteractions();
    }
}
