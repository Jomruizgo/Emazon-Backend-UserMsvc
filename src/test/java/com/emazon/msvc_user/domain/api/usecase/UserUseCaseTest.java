package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.exceptions.DuplicatedObjectException;
import com.emazon.msvc_user.domain.exceptions.ObjectNotFoundException;
import com.emazon.msvc_user.domain.model.Role;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import com.emazon.msvc_user.domain.util.TestConstants;
import com.emazon.msvc_user.domain.util.TestUserRole;
import com.emazon.msvc_user.domain.util.UserRole;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.management.relation.RoleNotFoundException;
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
        String role = TestUserRole.WAREHOUSE.getRoleName();
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // Act
        userUseCase.saveWarehouseAssistant(user);

        // Assert
        assertEquals(defaultRole, user.getRole());
        assertEquals("encodedPassword", user.getPassword());
        verify(userPersistencePort, times(1)).save(user);
    }

    @Test
    void saveClient_SuccessfulSave_ShouldAssignRoleAndEncodePassword() {
        // Arrange
        String role = TestUserRole.CLIENT.getRoleName();
        defaultRole.setName(role);
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // Act
        userUseCase.saveClient(user);

        // Assert
        assertEquals(defaultRole, user.getRole());
        assertEquals("encodedPassword", user.getPassword());
        verify(userPersistencePort, times(1)).save(user);
    }


    @Test
    void saveUser_EncodePasswordFails_ShouldThrowException() {
        // Arrange
        String role = TestUserRole.CLIENT.getRoleName();
        defaultRole.setName(role);
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);

        // Simula que la codificación de la contraseña falle
        when(passwordEncoder.encode(user.getPassword())).thenThrow(new RuntimeException("Password encoding failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        verify(userPersistencePort, times(0)).save(any(User.class));  // Asegúrate de que no se guarda el usuario
    }



    @Test
    void saveUser_RoleNotFound_ShouldThrowObjectNotFoundException() {
        // Arrange
        String role = TestUserRole.WAREHOUSE.getRoleName();
        when(rolePersistencePort.findRoleByName(role)).thenReturn(null);

        // Act & Assert
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals(TestConstants.ROLE_NOT_FOUND_MESSAGE, exception.getMessage());
        verify(userPersistencePort, times(0)).save(any(User.class));  // Asegurarse de que no se guarda el usuario
    }

    @Test
    void testSaveWarehouseAssistant_InvalidDocumentId_ThrowsException() {
        // Arrange
        user.setDocumentId(null); // Set documentId directly as a String
        String role = TestUserRole.WAREHOUSE.getRoleName();

        // Act & Assert
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Document is mandatory", exception.getMessage());
    }

    @Test
    void testSaveWarehouseAssistantWhenDocumentIdExists() {
        User userSaved = new User();
        userSaved.setDocumentId(123456L);
        String role = TestUserRole.WAREHOUSE.getRoleName();

        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        when(userPersistencePort.findUserByDocumentId(user.getDocumentId())).thenReturn(userSaved);

        DuplicatedObjectException exception = assertThrows(DuplicatedObjectException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("A user with this document id already exists.", exception.getMessage());

        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void testSaveWarehouseAssistant_BlankName_ThrowsException() {
        // Arrange
        user.setName("");
        String role = TestUserRole.WAREHOUSE.getRoleName();

        // Act & Assert
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Name is mandatory", exception.getMessage());

        // Case 2: Name is null
        user.setName(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Name is mandatory", exception.getMessage());
    }

    @Test
    void testSaveWarehouseAssistant_BlankLastName_ThrowsException() {
        // Arrange
        user.setLastName("");
        String role = TestUserRole.WAREHOUSE.getRoleName();

        // Act & Assert
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Last name is mandatory", exception.getMessage());

        // Case 2: LastName is null
        user.setLastName(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Last name is mandatory", exception.getMessage());
    }

    @Test
    void testSaveWarehouseAssistant_InvalidEmail_ThrowsException() {
        // Arrange
        user.setEmail("invalid-email");
        String role = TestUserRole.WAREHOUSE.getRoleName();

        // Act & Assert
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Email should be valid", exception.getMessage());

        // Case 2: Email is null
        user.setEmail(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("Email should be valid", exception.getMessage());
    }

    @Test
    void testSaveWarehouseAssistantWhenEmailExists() {
        User userSaved = new User();
        userSaved.setDocumentId(123L);
        userSaved.setEmail("john.doe@example.com");
        defaultRole = new Role();
        defaultRole.setId(123L);
        defaultRole.setName(TestUserRole.WAREHOUSE.getRoleName());
        userSaved.setRole(defaultRole);

        String role = TestUserRole.WAREHOUSE.getRoleName();

        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        when(userPersistencePort.findUserByEmail(user.getEmail())).thenReturn(userSaved);

        DuplicatedObjectException exception = assertThrows(DuplicatedObjectException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("A user with this email already exists.", exception.getMessage());

        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void testSaveWarehouseAssistant_UnderageUser_ThrowsException() {
        // Arrange
        user.setBirthdate(getBirthdate(17)); // 17 years old
        String role = TestUserRole.WAREHOUSE.getRoleName();

        // Act & Assert
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });

        assertEquals("User must be an adult (18+ years)", exception.getMessage());

        // Case 2: Birthdate is null
        user.setBirthdate(null);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
        });
        assertEquals("User must be an adult (18+ years)", exception.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678901234", "123ABC7890", "12 34", "+12345678901234"}) // Casos de números inválidos
    @NullAndEmptySource // Casos para nulo y vacío
    void testSaveWarehouseAssistant_InvalidMobileNumber_ThrowsException(String mobileNumber) {
        // Arrange
        user.setMobileNumber(mobileNumber);
        String role = TestUserRole.WAREHOUSE.getRoleName();

        // Act & Assert
        when(rolePersistencePort.findRoleByName(role)).thenReturn(defaultRole);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.saveWarehouseAssistant(user);
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