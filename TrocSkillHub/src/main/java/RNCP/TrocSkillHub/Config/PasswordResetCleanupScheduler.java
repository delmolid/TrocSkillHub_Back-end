package RNCP.TrocSkillHub.Config;

import RNCP.TrocSkillHub.Repositories.PasswordResetRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class PasswordResetCleanupScheduler {

    private final PasswordResetRequestRepository resetRepository;
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetCleanupScheduler.class);

    public PasswordResetCleanupScheduler(PasswordResetRequestRepository resetRepository) {
        this.resetRepository = resetRepository;
    }

    /**
     * Scheduled task to delete expired password reset requests.
     * Runs every hour at the start of the hour.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void cleanupExpiredResetRequests() {
        logger.info("Starting cleanup of expired password reset requests...");
        try {
            var expiredRequests = resetRepository.findByExpiresAtBefore(LocalDateTime.now());
            if (!expiredRequests.isEmpty()) {
                resetRepository.deleteAll(expiredRequests);
                logger.info("Deleted {} expired password reset requests.", expiredRequests.size());
            } else {
                logger.info("No expired password reset requests found.");
            }
        } catch (Exception e) {
            logger.error("Error during cleanup of password reset requests: {}", e.getMessage(), e);
        }
    }
}
