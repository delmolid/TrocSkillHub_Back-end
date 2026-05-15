package RNCP.TrocSkillHub.DTOs;

import java.util.List;

public record UserRequestDTO(
    String firstName,
    String lastName,
    String email,
    String address,
    String city,
    String country,
    String phoneNumber,
    String description,
    List<UserKnowledgeDTO> skills,
    List<UserKnowledgeDTO> needs
) {}
