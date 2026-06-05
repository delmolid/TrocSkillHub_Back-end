package RNCP.TrocSkillHub.DTOs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

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
    List<UserKnowledgeDTO> needs,
    List<EducationDTO> education,
    List<ExperienceDTO> experience,
    @JsonAlias("projet") List<ProjectDTO> project,
    String password
) {}
