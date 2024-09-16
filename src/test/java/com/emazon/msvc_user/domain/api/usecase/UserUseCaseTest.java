package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.exceptions.DuplicatedObjectException;
import com.emazon.msvc_user.domain.model.Role;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import com.emazon.msvc_user.domain.util.TestConstants;
import com.emazon.msvc_user.domain.util.TestUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoder;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;
    private Role defaultRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setName("John");
        user.setLastName("Doe");
        user.setDocumentId(123456789L);
        user.setMobileNumber("+1234567890");
        user.setEmail("john.doe@example.com");
        user.setBirthdate(new Date(2000-01-01)); // 18+ años
        user.setPassword("plainPassword");

        defaultRole = new Role();
        defaultRole.setName(TestUserRole.WAREHOUSE.toString());
    }

    @Test
    void saveUser_SuccessfulSave_ShouldAssignRoleAndEncodePassword() {
        // Arrange
        when(rolePersistencePort.findRoleByName(TestUserRole.WAREHOUSE.toString())).thenReturn(defaultRole);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userPersistencePort.findUserByEmail(user.getEmail())).thenReturn(null);
        when(userPersistencePort.findUserByDocumentId(user.getDocumentId())).thenReturn(null);

        // Act
        userUseCase.saveUser(user);

        // Assert
        assertEquals(defaultRole, user.getRole());
        assertEquals("encodedPassword", user.getPassword());
        verify(userPersistencePort, times(1)).save(user);
    }

    @Test
    void testSaveUser_InvalidDocumentId_ThrowsException() {
        // Arrange
        user.setDocumentId(null); // Set documentId directly as a String

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Document is mandatory", exception.getMessage());
    }

    @Test
    void testSaveUserWhenDocumentIdExists() {
        User userSaved = new User();
        userSaved.setDocumentId(123456L);


        when(userPersistencePort.findUserByDocumentId(user.getDocumentId())).thenReturn(userSaved);

        DuplicatedObjectException exception = assertThrows(DuplicatedObjectException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("A user with this document id already exists.", exception.getMessage());

        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void testSaveUser_BlankName_ThrowsException() {
        // Arrange
        user.setName("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Name is mandatory", exception.getMessage());

        // Case 2: Name is null
        user.setName(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Name is mandatory", exception.getMessage());
    }

    @Test
    void testSaveUser_BlankLastName_ThrowsException() {
        // Arrange
        user.setLastName("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Last name is mandatory", exception.getMessage());

        // Case 2: LastName is null
        user.setLastName(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Last name is mandatory", exception.getMessage());
    }

    @Test
    void testSaveUser_InvalidEmail_ThrowsException() {
        // Arrange
        user.setEmail("invalid-email");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Email should be valid", exception.getMessage());

        // Case 2: Email is null
        user.setEmail(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Email should be valid", exception.getMessage());
    }

    @Test
    void testSaveUserWhenEmailExists() {
        User userSaved = new User();
        userSaved.setDocumentId(123L);
        userSaved.setEmail("john.doe@example.com");

        when(userPersistencePort.findUserByEmail(user.getEmail())).thenReturn(userSaved);

        DuplicatedObjectException exception = assertThrows(DuplicatedObjectException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("A user with this email already exists.", exception.getMessage());

        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void testSaveUser_UnderageUser_ThrowsException() {
        // Arrange
        user.setBirthdate(getBirthdate(17)); // 17 years old

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("User must be an adult (18+ years)", exception.getMessage());

        // Case 2: Birthdate is null
        user.setBirthdate(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });
        assertEquals("User must be an adult (18+ years)", exception.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678901234", "123ABC7890", "12 34", "+12345678901234"}) // Casos de números inválidos
    @NullAndEmptySource // Casos para nulo y vacío
    void testSaveUser_InvalidMobileNumber_ThrowsException(String mobileNumber) {
        // Arrange
        user.setMobileNumber(mobileNumber);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("Mobile number must be up to 13 characters: 12 digits and additionally can start with '+'", exception.getMessage());
    }

    // Utilidad para generar fechas de nacimiento
    private Date getBirthdate(int age) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -age);
        return calendar.getTime();
    }

}