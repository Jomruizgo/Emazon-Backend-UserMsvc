package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.exceptions.BadCredentialsException;
import com.emazon.msvc_user.domain.exceptions.DisabledAccountException;
import com.emazon.msvc_user.domain.model.AuthUser;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import com.emazon.msvc_user.domain.spi.ITokenServicePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoder;

    @Mock
    private ITokenServicePort tokenPort;

    @InjectMocks
    private AuthUseCase authUseCase;

    private AuthUser authUser;
    private User validUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authUser = new AuthUser();
        authUser.setUsername("test@test.com");
        authUser.setPassword("password");

        validUser = new User();
        validUser.setEmail("test@test.com");
        validUser.setPassword("encodedPassword");
        validUser.setIsActive(true);
    }

    @Test
    void login_SuccessfulLogin_ShouldReturnAuthUserWithToken() {
        // Arrange
        when(userPersistencePort.findUserByEmail("test@test.com")).thenReturn(validUser);
        when(passwordEncoder.matches("password", validUser.getPassword())).thenReturn(true);
        when(tokenPort.generateToken(validUser)).thenReturn("token123");

        // Act
        AuthUser result = authUseCase.login(authUser);

        // Assert
        assertNotNull(result);
        assertNull(result.getPassword()); // Password should be null after login
        assertEquals("token123", result.getToken());
    }

    @Test
    void login_InvalidEmail_ShouldThrowBadCredentialsException() {
        // Arrange
        when(userPersistencePort.findUserByEmail("test@test.com")).thenReturn(null);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authUseCase.login(authUser));
    }

    @Test
    void login_InvalidPassword_ShouldThrowBadCredentialsException() {
        // Arrange
        when(userPersistencePort.findUserByEmail("test@test.com")).thenReturn(validUser);
        when(passwordEncoder.matches("password", validUser.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authUseCase.login(authUser));
    }

    @Test
    void login_InactiveUser_ShouldThrowDisabledAccountException() {
        // Arrange
        validUser.setIsActive(false);
        when(userPersistencePort.findUserByEmail("test@test.com")).thenReturn(validUser);
        when(passwordEncoder.matches("password", validUser.getPassword())).thenReturn(true);

        // Act & Assert
        assertThrows(DisabledAccountException.class, () -> authUseCase.login(authUser));
    }

    @Test
    void login_NullAuthUser_ShouldThrowBadCredentialsException() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authUseCase.login(null));
    }

    @Test
    void login_NullUsername_ShouldThrowBadCredentialsException() {
        // Arrange
        authUser.setUsername(null);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authUseCase.login(authUser));
    }

    @Test
    void login_NullPassword_ShouldThrowBadCredentialsException() {
        // Arrange
        authUser.setPassword(null);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authUseCase.login(authUser));
    }
}