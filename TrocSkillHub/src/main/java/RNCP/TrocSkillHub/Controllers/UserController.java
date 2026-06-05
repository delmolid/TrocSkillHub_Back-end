package RNCP.TrocSkillHub.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import RNCP.TrocSkillHub.DTOs.UserRequestDTO;
import RNCP.TrocSkillHub.DTOs.UserResponseDTO;
import RNCP.TrocSkillHub.Mappers.UserMapper;
import RNCP.TrocSkillHub.Models.User;
import RNCP.TrocSkillHub.Services.UserService;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> userDTOs = userMapper.toResponseDTOList(users);
        return ResponseEntity.ok(userDTOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDTO(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO requestDTO) {
        try {
            User user = userMapper.toEntity(requestDTO);
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(userMapper.toResponseDTO(createdUser), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erreur: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO requestDTO) {
        try {
            User updatedUser = userService.updateUser(id, requestDTO);
            return ResponseEntity.ok(userMapper.toResponseDTO(updatedUser));
        } catch (RuntimeException e) {
            return handleUpdateError(e);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody UserRequestDTO requestDTO) {
        try {
            User updatedUser = userService.patchUser(id, requestDTO);
            return ResponseEntity.ok(userMapper.toResponseDTO(updatedUser));
        } catch (RuntimeException e) {
            return handleUpdateError(e);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        }
    }

    private ResponseEntity<?> handleUpdateError(RuntimeException e) {
        String message = e.getMessage() != null ? e.getMessage() : "";
        if (message.contains("existe déjà")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erreur: " + message);
        }
        if (message.contains("mot de passe")
                || message.contains("Connaissance")
                || message.contains("knowledgeId")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Erreur: " + message);
    }
}
