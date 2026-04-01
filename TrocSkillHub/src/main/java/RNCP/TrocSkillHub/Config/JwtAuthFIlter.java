package RNCP.TrocSkillHub.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import RNCP.TrocSkillHub.Services.ImplServices.CustomUserDetailsService;

import java.io.IOException;

@Component
public class JwtAuthFIlter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    public CorsConfig corsConfig;

    public JwtAuthFIlter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Extraire le token depuis les cookies
        String token = null;

        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Vérifier le token et authentifier l'utilisateur
        if (token != null && jwtService.isTokenValid(token)) {
            try {
                String email = jwtService.extractEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Si l'utilisateur n'existe pas ou autre erreur, on ignore simplement
                // Le filtre ne bloque pas la requête
                System.err.println("JWT authentication error: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}