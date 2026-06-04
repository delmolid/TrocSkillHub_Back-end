package RNCP.TrocSkillHub.DTOs;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDTO(
    String firstName,
    String lastName,
    String email,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String address,
    String city,
    String country,
    String phoneNumber,
    String description,
    List<UserKnowledgeDTO> skills,
    List<UserKnowledgeDTO> needs,
    List<EducationDTO> education,
    List<ExperienceDTO>experience,
    List<ProjectDTO>project

) {}