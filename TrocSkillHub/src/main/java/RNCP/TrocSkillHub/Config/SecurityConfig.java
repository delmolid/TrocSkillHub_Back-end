package RNCP.TrocSkillHub.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import RNCP.TrocSkillHub.Services.ImplServices.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFIlter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, JwtAuthFIlter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
           
                // Endpoints d'authentification (publics)
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()

                // Endpoints catégories (publics)
                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categories").permitAll()
            
                // Endpoints knowledges
                .requestMatchers(HttpMethod.GET, "/api/knowledges").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/knowledges/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/knowledges").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/knowledges/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/knowledges/{id}").authenticated()
            
                // Endpoints users (authentifiés)
                .requestMatchers(HttpMethod.POST, "/api/users").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/users").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/users/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                // Tous les autres endpoints nécessitent une authentification
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}