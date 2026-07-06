package RNCP.TrocSkillHub.Services.ImplServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import RNCP.TrocSkillHub.Services.EmailService;

@Service
public class SimpleEmailService implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleEmailService.class);

    private final JavaMailSender mailSender;
    private final String mailFrom;

    public SimpleEmailService(JavaMailSender mailSender,
            @Value("${app.mail.from}") String mailFrom) {
        this.mailSender = mailSender;
        this.mailFrom = mailFrom;
    }

    @Override
    public void sendPasswordResetCode(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(to);
            message.setSubject("Your Password Reset Code");
            message.setText("Your password reset code is: " + code + "\n\n" +
                    "This code will expire in 15 minutes.\n\n" +
                    "If you did not request this, please ignore this email.");
            mailSender.send(message);
            logger.info("Password reset code sent to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send password reset code to {}: {}", to, e.getMessage());
        }
    }
}
