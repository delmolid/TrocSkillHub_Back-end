package RNCP.TrocSkillHub.DTOs;

import java.time.LocalDate;

public record ExperienceDTO(
        Long id,
        String company,
        String job,
        LocalDate dateStart,
        LocalDate dateEnd) {
}
