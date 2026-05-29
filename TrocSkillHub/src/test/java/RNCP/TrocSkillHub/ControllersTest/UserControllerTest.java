package RNCP.TrocSkillHub.ControllersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
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
import RNCP.TrocSkillHub.DTOs.UserRequestDTO;
import RNCP.TrocSkillHub.DTOs.UserResponseDTO;
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
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        userRequestDTO = new UserRequestDTO(
            "John", "Doe",
            "test@example.com",
            "Password1!", null, null, null, null, null,null,null,null,null
        
        );
        userResponseDTO = new UserResponseDTO(
            "jean", "ali", "test@example.com",
            "test",
         null, null, null,
            null, null, null, null, null,null
        );
    }

    @Test
    @DisplayName("getAllUsers - Should return a list of users")
    void getAllUsers_shouldReturnUserList() {
        List<User> users = Arrays.asList(user);
        List<UserResponseDTO> responseDTOs = Arrays.asList(userResponseDTO);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toResponseDTOList(users)).thenReturn(responseDTOs);

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).firstName()).isEqualTo("jean");
        assertThat(response.getBody().get(0).lastName()).isEqualTo("ali");
        assertThat(response.getBody().get(0).email()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("getAllUsers - should return empty list when no users")
    void getAllUsers_shouldReturnEmptyList_whenNoUsers() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        when(userMapper.toResponseDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    @DisplayName("getUserById - should return user when user exists")
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        ResponseEntity<?> response = userController.getUserById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userResponseDTO);
    }

    @Test
    @DisplayName("getUserById - should return 404 when user Not Found")
    void getUserById_shouldReturn404_whenUserNotFound() {
        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.getUserById(99L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("createUser - should create user successfully")
    void createUser_shouldCreateUser_successfully() {
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        ResponseEntity<?> response = userController.createUser(userRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userResponseDTO);
    }

    @Test
    @DisplayName("createUser - should return 409 when Conflict")
    void createUser_shouldReturn409_whenConflict() {
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userService.createUser(user)).thenThrow(new RuntimeException("Email déjà utilisé"));

        ResponseEntity<?> response = userController.createUser(userRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().toString()).contains("Erreur");
    }

    @Test
    @DisplayName("updateUser - should update user successfully")
    void updateUser_shouldUpdateUser_successfully() {
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        ResponseEntity<?> response = userController.updateUser(1L, userRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userResponseDTO);
    }

    @Test
    @DisplayName("updateUser - should return 404 when user Not Found")
    void updateUser_shouldReturn404_whenUserNotFound() {
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userService.updateUser(eq(99L), any(User.class)))
                .thenThrow(new RuntimeException("Utilisateur non trouvé"));

        ResponseEntity<?> response = userController.updateUser(99L, userRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

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
