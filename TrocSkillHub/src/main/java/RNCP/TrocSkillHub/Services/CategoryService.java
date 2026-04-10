package RNCP.TrocSkillHub.Services;

import RNCP.TrocSkillHub.DTOs.CategoryDTO;
import RNCP.TrocSkillHub.Mappers.CategoryMapper;
import RNCP.TrocSkillHub.Models.Category;
import RNCP.TrocSkillHub.Repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    public List<CategoryDTO> getAllCategories() {
        
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDTOList(categories);
    }
}