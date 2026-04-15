package RNCP.TrocSkillHub.Mappers;

import java.util.List;

import org.mapstruct.*;

import RNCP.TrocSkillHub.DTOs.EducationDTO;
import RNCP.TrocSkillHub.DTOs.ExperienceDTO;
import RNCP.TrocSkillHub.DTOs.ProjectDTO;
import RNCP.TrocSkillHub.DTOs.UserDTO;
import RNCP.TrocSkillHub.Models.Education;
import RNCP.TrocSkillHub.Models.Experience;
import RNCP.TrocSkillHub.Models.Project;
import RNCP.TrocSkillHub.Models.User;

@Mapper(
    componentModel="spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface UserMapper {
    UserDTO toDTO(User user);

    EducationDTO toDTO(Education education);

    ExperienceDTO toDTO(Experience experience);

    ProjectDTO toDTO(Project project);


    User toEntity(UserDTO userDTO);


    Education toEntity(EducationDTO educationDTO);


    Experience toEntity(ExperienceDTO experienceDTO);


    Project toEntity(ProjectDTO projectDTO);

    List<UserDTO> toDTOList(List<User> users);
} 
