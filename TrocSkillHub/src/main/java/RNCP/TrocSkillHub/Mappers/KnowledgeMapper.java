package RNCP.TrocSkillHub.Mappers;

import org.mapstruct.Mapper;
import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.Models.Knowledge;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KnowledgeMapper {

    KnowledgeDTO toDTO(Knowledge knowledge);
    
    List<KnowledgeDTO> toDTOList(List<Knowledge> knowledges);
    
    Knowledge toEntity(KnowledgeDTO knowledgeDTO);
}
