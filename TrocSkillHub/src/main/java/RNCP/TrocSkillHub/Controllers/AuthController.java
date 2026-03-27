package RNCP.TrocSkillHub.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import RNCP.TrocSkillHub.Config.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String email = body.get("email");
        String password = body.get("password");

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            String token = jwtService.generateToken(email);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(false); // False in localhost and True in HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(86400);

            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of("message", "Connexion réussie"));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
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
}
