package RNCP.TrocSkillHub.Services;

import RNCP.TrocSkillHub.DTOs.PasswordResetDto;
import RNCP.TrocSkillHub.DTOs.PasswordResetRequestDto;
import RNCP.TrocSkillHub.DTOs.PasswordResetVerifyDto;
import RNCP.TrocSkillHub.Models.PasswordResetRequest;
import RNCP.TrocSkillHub.Models.User;
import RNCP.TrocSkillHub.Repositories.PasswordResetRequestRepository;
import RNCP.TrocSkillHub.Repositories.UserRepository;
import RNCP.TrocSkillHub.Services.ImplServices.PasswordResetServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetRequestRepository resetRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private PasswordResetService passwordResetService;

    @BeforeEach
    void setUp() {
        passwordResetService = new PasswordResetServiceImpl(
                userRepository,
                resetRepository,
                emailService,
                passwordEncoder,
                15,
                5);
    }

    @Test
    void testRequestReset_UnknownEmail_NoCodeGenerated() {
        PasswordResetRequestDto dto = new PasswordResetRequestDto("unknown@example.com");
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        passwordResetService.requestReset(dto);

        verify(resetRepository, never()).save(any());
        verify(emailService, never()).sendPasswordResetCode(anyString(), anyString());

    }

    @Test
    void testRequestReset_KnownEmail_CodeGenerated() {
        User user = new User();
        user.setId(1L);
        user.setEmail("known@example.com");

        PasswordResetRequestDto dto = new PasswordResetRequestDto("known@example.com");
        when(userRepository.findByEmail("known@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_code");
        when(resetRepository.save(any())).thenAnswer(invocation -> {
            PasswordResetRequest prr = invocation.getArgument(0);
            prr.setId(1L);
            return prr;
        });

        passwordResetService.requestReset(dto);

        verify(resetRepository).save(any(PasswordResetRequest.class));
        verify(emailService).sendPasswordResetCode(eq("known@example.com"), anyString());
    }

    @Test
    void testVerifyCode_ValidCode() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setUser(user);
        prr.setCodeHash("hashed_1234");
        prr.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        prr.setUsed(false);
        prr.setAttempts(0);

        PasswordResetVerifyDto dto = new PasswordResetVerifyDto("test@example.com", "1234");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(resetRepository.findTopByUserOrderByCreatedAtDesc(user)).thenReturn(Optional.of(prr));
        when(passwordEncoder.matches("1234", "hashed_1234")).thenReturn(true);

        String token = passwordResetService.verifyCode(dto);

        assertEquals("1", token);
        verify(resetRepository, never()).save(any());
    }

    @Test
    void testVerifyCode_InvalidCode_AttemptIncremented() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setUser(user);
        prr.setCodeHash("hashed_1234");
        prr.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        prr.setUsed(false);
        prr.setAttempts(0);

        PasswordResetVerifyDto dto = new PasswordResetVerifyDto("test@example.com", "wrong");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(resetRepository.findTopByUserOrderByCreatedAtDesc(user)).thenReturn(Optional.of(prr));
        when(passwordEncoder.matches("wrong", "hashed_1234")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> passwordResetService.verifyCode(dto));
        assertEquals(1, prr.getAttempts());
        verify(resetRepository).save(prr);
    }

    @Test
    void testVerifyCode_ExpiredCode() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setUser(user);
        prr.setCodeHash("hashed_1234");
        prr.setExpiresAt(LocalDateTime.now().minusMinutes(5));
        prr.setUsed(false);
        prr.setAttempts(0);

        PasswordResetVerifyDto dto = new PasswordResetVerifyDto("test@example.com", "1234");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(resetRepository.findTopByUserOrderByCreatedAtDesc(user)).thenReturn(Optional.of(prr));

        assertThrows(IllegalArgumentException.class, () -> passwordResetService.verifyCode(dto));
    }

    @Test
    void testVerifyCode_MaxAttemptsExceeded() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setUser(user);
        prr.setCodeHash("hashed_1234");
        prr.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        prr.setUsed(false);
        prr.setAttempts(5);

        PasswordResetVerifyDto dto = new PasswordResetVerifyDto("test@example.com", "wrong");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(resetRepository.findTopByUserOrderByCreatedAtDesc(user)).thenReturn(Optional.of(prr));

        assertThrows(IllegalArgumentException.class, () -> passwordResetService.verifyCode(dto));
        assertTrue(prr.isUsed());
    }

    @Test
    void testResetPassword_ValidPassword() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("old_password");

        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setUser(user);
        prr.setCodeHash("hashed_1234");
        prr.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        prr.setUsed(false);
        prr.setAttempts(0);

        PasswordResetDto dto = new PasswordResetDto("1", "NewPassword123", "NewPassword123");
        when(resetRepository.findById(1L)).thenReturn(Optional.of(prr));
        when(passwordEncoder.encode("NewPassword123")).thenReturn("hashed_NewPassword123");

        passwordResetService.resetPassword(dto);

        assertTrue(prr.isUsed());
        verify(userRepository).save(user);
        verify(resetRepository).save(prr);
    }

    @Test
    void testResetPassword_PasswordMismatch() {
        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        prr.setUsed(false);

        PasswordResetDto dto = new PasswordResetDto("1", "NewPassword123", "DifferentPassword");
        when(resetRepository.findById(1L)).thenReturn(Optional.of(prr));

        assertThrows(IllegalArgumentException.class, () -> passwordResetService.resetPassword(dto));
    }

    @Test
    void testResetPassword_PasswordTooShort() {
        PasswordResetRequest prr = new PasswordResetRequest();
        prr.setId(1L);
        prr.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        prr.setUsed(false);

        PasswordResetDto dto = new PasswordResetDto("1", "short", "short");
        when(resetRepository.findById(1L)).thenReturn(Optional.of(prr));

        assertThrows(IllegalArgumentException.class, () -> passwordResetService.resetPassword(dto));
    }
}
