package RNCP.TrocSkillHub.Util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory rate limiter for password reset requests.
 * Limits the number of reset requests per email address within a time window.
 */
@Component
public class RateLimiter {

    private static final int MAX_REQUESTS = 3;
    private static final int TIME_WINDOW_MINUTES = 30;
    private final Map<String, RequestLog> requestLogs = new HashMap<>();

    public boolean isAllowed(String email) {
        LocalDateTime now = LocalDateTime.now();
        RequestLog log = requestLogs.computeIfAbsent(email, k -> new RequestLog());

        // Remove old entries outside the time window
        log.timestamps.removeIf(ts -> ts.isBefore(now.minusMinutes(TIME_WINDOW_MINUTES)));

        if (log.timestamps.size() < MAX_REQUESTS) {
            log.timestamps.add(now);
            return true;
        }
        return false;
    }

    private static class RequestLog {
        final java.util.List<LocalDateTime> timestamps = new java.util.ArrayList<>();
    }
}
