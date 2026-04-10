package RNCP.TrocSkillHub.Mappers;


import org.mapstruct.Mapper;

import RNCP.TrocSkillHub.DTOs.CategoryDTO;
import RNCP.TrocSkillHub.Models.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);
    
    List<CategoryDTO> toDTOList(List<Category> categories);
    
    Category toEntity(CategoryDTO categoryDTO);

}
