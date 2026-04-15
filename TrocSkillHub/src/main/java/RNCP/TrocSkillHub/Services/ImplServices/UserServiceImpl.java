package RNCP.TrocSkillHub.Services.ImplServices;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import RNCP.TrocSkillHub.Models.User;
import RNCP.TrocSkillHub.Repositories.EducationRepository;
import RNCP.TrocSkillHub.Repositories.ExperienceRepository;
import RNCP.TrocSkillHub.Repositories.ProjectRepository;
import RNCP.TrocSkillHub.Repositories.UserRepository;
import RNCP.TrocSkillHub.Services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder, EducationRepository educationRepository,
            ExperienceRepository experienceRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User createUser(User user) {
        // Vérif email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Cet email existe déjà!");
        }

        // Valider la force du mot de passe
        validatePasswordStrength(user.getPassword());

        // Hacher le mot de passe avant de le stocker
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setAddress(user.getAddress());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPicture(user.getPicture());
                    existingUser.setCity(user.getCity());
                    existingUser.setCountry(user.getCountry());
                    existingUser.setPhoneNumber(user.getPhoneNumber());
                    existingUser.setDescription(user.getDescription());
                    existingUser.setUpdatedAt(LocalDate.now());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé avec l'id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getUsersByCity(String city) {
        return userRepository.findByCity(city);
    }

    @Override
    public List<User> getUsersByCountry(String country) {
        return userRepository.findByCountry(country);
    }

    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new RuntimeException("Le mot de passe doit contenir au moins 8 caractères");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Le mot de passe doit contenir au moins une majuscule");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new RuntimeException("Le mot de passe doit contenir au moins une minuscule");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new RuntimeException("Le mot de passe doit contenir au moins un chiffre");
        }
        if (!password.matches(".*[@#$%^&+=!?*].*")) {
            throw new RuntimeException("Le mot de passe doit contenir au moins un caractère spécial (@#$%^&+=!?*)");
        }
    }

}
