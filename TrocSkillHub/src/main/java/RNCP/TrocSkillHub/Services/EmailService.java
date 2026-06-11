package RNCP.TrocSkillHub.Services;

public interface EmailService {
    void sendPasswordResetCode(String to, String code);
}
