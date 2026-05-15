package RNCP.TrocSkillHub.Mappers;
import java.util.List;
import org.mapstruct.*;
import RNCP.TrocSkillHub.DTOs.CategoryDTO;
import RNCP.TrocSkillHub.DTOs.EducationDTO;
import RNCP.TrocSkillHub.DTOs.ExperienceDTO;
import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.DTOs.ProjectDTO;
import RNCP.TrocSkillHub.DTOs.UserKnowledgeDTO;
import RNCP.TrocSkillHub.DTOs.UserRequestDTO;
import RNCP.TrocSkillHub.DTOs.UserResponseDTO;
import RNCP.TrocSkillHub.Models.Category;
import RNCP.TrocSkillHub.Models.Education;
import RNCP.TrocSkillHub.Models.Experience;
import RNCP.TrocSkillHub.Models.Knowledge;
import RNCP.TrocSkillHub.Models.KnowledgeType;
import RNCP.TrocSkillHub.Models.Project;
import RNCP.TrocSkillHub.Models.User;
import RNCP.TrocSkillHub.Models.UserKnowledge;
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    // User → UserResponseDTO (GET)
    @Mapping(target = "skills", expression = "java(mapSkills(user.getUserKnowledge()))")
    @Mapping(target = "needs",  expression = "java(mapNeeds(user.getUserKnowledge()))")
    UserResponseDTO toResponseDTO(User user);
    // UserRequestDTO → User (POST / PUT)
    User toEntity(UserRequestDTO requestDTO);
    // UserKnowledge → UserKnowledgeDTO
    @Mapping(source = "knowledge.id",   target = "knowledgeId")
    @Mapping(source = "knowledge.name", target = "knowledgeName")
    @Mapping(source = "type",           target = "type")
    @Mapping(source = "level",          target = "level")
    UserKnowledgeDTO toUserKnowledgeDTO(UserKnowledge userKnowledge);
    // Filtrage par type
    default List<UserKnowledgeDTO> mapSkills(List<UserKnowledge> list) {
        if (list == null) return List.of();
        return list.stream()
            .filter(uk -> uk.getType() == KnowledgeType.SKILL)
            .map(this::toUserKnowledgeDTO)
            .toList();
    }
    default List<UserKnowledgeDTO> mapNeeds(List<UserKnowledge> list) {
        if (list == null) return List.of();
        return list.stream()
            .filter(uk -> uk.getType() == KnowledgeType.NEED)
            .map(this::toUserKnowledgeDTO)
            .toList();
    };

    KnowledgeDTO toDTO(Knowledge knowledge);
    CategoryDTO toDTO(Category category);
    EducationDTO toDTO(Education education);
    ExperienceDTO toDTO(Experience experience);
    ProjectDTO toDTO(Project project);
    Education toEntity(EducationDTO educationDTO);
    Experience toEntity(ExperienceDTO experienceDTO);
    Project toEntity(ProjectDTO projectDTO);
    List<UserResponseDTO> toResponseDTOList(List<User> users);
}
