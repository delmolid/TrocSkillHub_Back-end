package RNCP.TrocSkillHub.DTOs;

import java.util.List;


public record UserPublicResponseDTO(
    String firstName,
    String lastName,
    String city,
    String country,
    List<UserKnowledgeDTO> skills,
    List<UserKnowledgeDTO> needs
) {}
