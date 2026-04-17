package RNCP.TrocSkillHub.DTOs;

import java.time.LocalDate;

public record ProjectDTO(
        Long id,
        String name,
        String description,
        String links,
        LocalDate dateStart,
        LocalDate dateEnd) {
}
