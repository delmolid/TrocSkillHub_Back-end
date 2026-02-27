package RNCP.TrocSkillHub.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Argon2id avec les paramètres recommandés par OWASP:
        //16 octets de sel, 32 octets de hash, 1 thread, 19 Mo de mémoire, 2 itérations.
        return new Argon2PasswordEncoder(16, 32, 1, 19456, 2);
    }
}