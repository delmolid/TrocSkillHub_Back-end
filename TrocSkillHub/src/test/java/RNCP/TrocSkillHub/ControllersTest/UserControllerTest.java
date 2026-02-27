package RNCP.TrocSkillHub.ControllersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import RNCP.TrocSkillHub.Controllers.UserController;
import RNCP.TrocSkillHub.DTOs.UserDTO;
import RNCP.TrocSkillHub.Models.User;
import RNCP.TrocSkillHub.Mappers.UserMapper;
import RNCP.TrocSkillHub.Services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        userDTO = new UserDTO(
            1L,
            "John",
            "Doe",
            "test@example.com", 
            "password123",
            null,
            null, 
            null,                   
            null,                   
            null                    
        );
    }
    // ========== GET ALL USERS ==========

    @Test
    @DisplayName("getAllUsers - Should return a list of users")
    void getAllUsers_shouldReturnUserList() {

        List<User> users = Arrays.asList(user);
        List<UserDTO> userDTOs = Arrays.asList(userDTO);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toDTOList(users)).thenReturn(userDTOs);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).email()).isEqualTo("test@example.com");
    }

    // ========== GET USER BY ID ==========

    @Test
    @DisplayName("getUserById - should return user when user exists")
    void getUserById_shouldReturnUser_whenUserExists() {

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        ResponseEntity<?> response = userController.getUserById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDTO);
    }

    @Test
    @DisplayName("getUserById - should return 404 when user Not Found")
    void getUserById_shouldReturn404_whenUserNotFound() {

        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.getUserById(99L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ========== CREATE USER ==========

    @Test
    @DisplayName("createUser - should create user successfully")
    void createUser_shouldCreateUser_successfully() {

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        ResponseEntity<?> response = userController.createUser(userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDTO);
    }

    @Test
    @DisplayName("createUser - should return 409 when Conflict")
    void createUser_shouldReturn409_whenConflict() {

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userService.createUser(user)).thenThrow(new RuntimeException("Email déjà utilisé"));

        ResponseEntity<?> response = userController.createUser(userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().toString()).contains("Erreur");
    }

    // ========== UPDATE USER ==========

    @Test
    @DisplayName("updateUser - should update user successfully")
    void updateUser_shouldUpdateUser_successfully() {

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        ResponseEntity<?> response = userController.updateUser(1L, userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDTO);
    }

    @Test
    @DisplayName("updateUser - should return 404 when user Not Found")
    void updateUser_shouldReturn404_whenUserNotFound() {

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userService.updateUser(eq(99L), any(User.class)))
                .thenThrow(new RuntimeException("Utilisateur non trouvé"));

        ResponseEntity<?> response = userController.updateUser(99L, userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ========== DELETE USER ==========

    @Test
    @DisplayName("deleteUser - should delete user successfully")
    void deleteUser_shouldDeleteUser_successfully() {

        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<?> response = userController.deleteUser(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().toString()).contains("supprimé");
    }

    @Test
    @DisplayName("deleteUser - should return 404 when user Not Found")
    void deleteUser_shouldReturn404_whenUserNotFound() {

        doThrow(new RuntimeException("Utilisateur non trouvé"))
                .when(userService).deleteUser(99L);

        ResponseEntity<?> response = userController.deleteUser(99L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
