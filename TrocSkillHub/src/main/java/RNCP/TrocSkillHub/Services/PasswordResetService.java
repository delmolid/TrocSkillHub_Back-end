package RNCP.TrocSkillHub.Services;

import RNCP.TrocSkillHub.DTOs.PasswordResetDto;
import RNCP.TrocSkillHub.DTOs.PasswordResetRequestDto;
import RNCP.TrocSkillHub.DTOs.PasswordResetVerifyDto;

public interface PasswordResetService {
    void requestReset(PasswordResetRequestDto requestDto);

    // returns a short-lived token or reset identifier to allow reset
    String verifyCode(PasswordResetVerifyDto verifyDto);

    void resetPassword(PasswordResetDto resetDto);
}
