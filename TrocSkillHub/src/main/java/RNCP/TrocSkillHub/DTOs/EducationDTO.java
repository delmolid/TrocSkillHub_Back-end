package RNCP.TrocSkillHub.DTOs;

import java.time.LocalDate;

public record EducationDTO(
        Long id,
        String name,
        String school,
        LocalDate dateStart,
        LocalDate dateEnd) {
}
