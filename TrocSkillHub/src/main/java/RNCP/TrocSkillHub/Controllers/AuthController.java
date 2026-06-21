package RNCP.TrocSkillHub.Controllers;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import RNCP.TrocSkillHub.Config.JwtService;
import RNCP.TrocSkillHub.Models.User;
import RNCP.TrocSkillHub.Repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthenticationManager authenticationManager, 
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody Map<String, String> body, HttpServletResponse response) {
    String nom = body.get("nom");
    String prenom = body.get("prenom");
    String email = body.get("email");
    String password = body.get("password");
    String city = body.get("city");
    String country = body.get("country");

    try {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(400)
                    .body(Map.of("error", "Cet email existe déjà"));
        }

        User newUser = new User();
        newUser.setFirstName(prenom);
        newUser.setLastName(nom);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setCity(city);
        newUser.setCountry(country);

        User savedUser = userRepository.save(newUser); 

    
        String token = jwtService.generateToken(email);
        ResponseCookie.from("jwt", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofMinutes(5))
            .sameSite("None")
            .build();

        return ResponseEntity.ok(Map.of(
                "message", "Inscription réussie",
                "id", savedUser.getId() 
        ));

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500)
                .body(Map.of("error", "Erreur lors de l'inscription"));
    }
}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String email = body.get("email");
        String password = body.get("password");
    
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
    
            String token = jwtService.generateToken(email);
    
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("None")
                    .build();
    
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(Map.of("message", "Connexion réussie"));
    
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Email ou mot de passe incorrect"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
            Cookie cookie = new Cookie("jwt", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            
            return ResponseEntity.ok(Map.of("message", "Déconnexion réussie"));
        }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
         
            String token = null;
            
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("jwt".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            
            if (token == null) {
                return ResponseEntity.status(401)
                    .body(Map.of("error", "Non authentifié"));
            }
            
        
            String email = jwtService.extractEmail(token);
            
 
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            
           
            return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "city", user.getCity() != null ? user.getCity() : "",
                "country", user.getCountry() != null ? user.getCountry() : ""
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "Token invalide"));
        }
    }
}