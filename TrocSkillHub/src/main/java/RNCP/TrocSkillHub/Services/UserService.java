package RNCP.TrocSkillHub.Services;

import RNCP.TrocSkillHub.DTOs.UserRequestDTO;
import RNCP.TrocSkillHub.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserRequestDTO requestDTO);
    User patchUser(Long id, UserRequestDTO requestDTO);
    void deleteUser(Long id);

    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email);
    List<User> getUsersByCity(String city);
    List<User> getUsersByCountry(String country);
    
}
