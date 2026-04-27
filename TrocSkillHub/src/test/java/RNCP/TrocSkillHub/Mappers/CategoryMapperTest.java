package RNCP.TrocSkillHub.Mappers;

import RNCP.TrocSkillHub.DTOs.CategoryDTO;
import RNCP.TrocSkillHub.Models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        // Utiliser Mappers.getMapper() au lieu de Spring
        categoryMapper = Mappers.getMapper(CategoryMapper.class);
    }

    // ========== Tests pour toDTO (Category → CategoryDTO) ==========

    @Test
    void toDTO_ShouldConvertCategoryToCategoryDTO() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Informatique");

        // Act
        CategoryDTO result = categoryMapper.toDTO(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Informatique");
    }

    @Test
    void toDTO_ShouldReturnNull_WhenCategoryIsNull() {
        // Act
        CategoryDTO result = categoryMapper.toDTO(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toDTO_ShouldMapAllProperties() {
        // Arrange
        Category category = new Category();
        category.setId(99L);
        category.setName("Sport");

        // Act
        CategoryDTO result = categoryMapper.toDTO(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
            .extracting(CategoryDTO::getId, CategoryDTO::getName)
            .containsExactly(99L, "Sport");
    }

    // ========== Tests pour toEntity (CategoryDTO → Category) ==========

    @Test
    void toEntity_ShouldConvertCategoryDTOToCategory() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(2L);
        categoryDTO.setName("Cuisine");

        // Act
        Category result = categoryMapper.toEntity(categoryDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Cuisine");
    }

    @Test
    void toEntity_ShouldReturnNull_WhenCategoryDTOIsNull() {
        // Act
        Category result = categoryMapper.toEntity(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toEntity_ShouldMapAllProperties() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(50L);
        categoryDTO.setName("Musique");

        // Act
        Category result = categoryMapper.toEntity(categoryDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
            .extracting(Category::getId, Category::getName)
            .containsExactly(50L, "Musique");
    }

    // ========== Tests pour toDTOList (List<Category> → List<CategoryDTO>) ==========

    @Test
    void toDTOList_ShouldConvertCategoryListToCategoryDTOList() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Informatique");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Cuisine");

        Category category3 = new Category();
        category3.setId(3L);
        category3.setName("Sport");

        List<Category> categories = Arrays.asList(category1, category2, category3);

        // Act
        List<CategoryDTO> result = categoryMapper.toDTOList(categories);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Informatique");
        
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getName()).isEqualTo("Cuisine");
        
        assertThat(result.get(2).getId()).isEqualTo(3L);
        assertThat(result.get(2).getName()).isEqualTo("Sport");
    }

    @Test
    void toDTOList_ShouldReturnEmptyList_WhenCategoriesListIsEmpty() {
        // Arrange
        List<Category> emptyList = Collections.emptyList();

        // Act
        List<CategoryDTO> result = categoryMapper.toDTOList(emptyList);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void toDTOList_ShouldReturnNull_WhenCategoriesListIsNull() {
        // Act
        List<CategoryDTO> result = categoryMapper.toDTOList(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toDTOList_ShouldHandleSingleElementList() {
        // Arrange
        Category category = new Category();
        category.setId(10L);
        category.setName("Art");

        List<Category> singleCategory = Collections.singletonList(category);

        // Act
        List<CategoryDTO> result = categoryMapper.toDTOList(singleCategory);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(0).getName()).isEqualTo("Art");
    }

    // ========== Tests de cohérence bidirectionnelle ==========

    @Test
    void toDTO_AndToEntity_ShouldBeReversible() {
        // Arrange
        Category originalCategory = new Category();
        originalCategory.setId(5L);
        originalCategory.setName("Jardinage");

        // Act
        CategoryDTO dto = categoryMapper.toDTO(originalCategory);
        Category backToEntity = categoryMapper.toEntity(dto);

        // Assert
        assertThat(backToEntity).isNotNull();
        assertThat(backToEntity.getId()).isEqualTo(originalCategory.getId());
        assertThat(backToEntity.getName()).isEqualTo(originalCategory.getName());
    }

    @Test
    void toEntity_AndToDTO_ShouldBeReversible() {
        // Arrange
        CategoryDTO originalDTO = new CategoryDTO();
        originalDTO.setId(7L);
        originalDTO.setName("Photographie");

        // Act
        Category entity = categoryMapper.toEntity(originalDTO);
        CategoryDTO backToDTO = categoryMapper.toDTO(entity);

        // Assert
        assertThat(backToDTO).isNotNull();
        assertThat(backToDTO.getId()).isEqualTo(originalDTO.getId());
        assertThat(backToDTO.getName()).isEqualTo(originalDTO.getName());
    }

    // ========== Tests avec des valeurs particulières ==========

    @Test
    void toDTO_ShouldHandleCategoryWithNullName() {
        // Arrange
        Category category = new Category();
        category.setId(15L);
        category.setName(null);

        // Act
        CategoryDTO result = categoryMapper.toDTO(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(15L);
        assertThat(result.getName()).isNull();
    }

    @Test
    void toDTO_ShouldHandleCategoryWithEmptyName() {
        // Arrange
        Category category = new Category();
        category.setId(20L);
        category.setName("");

        // Act
        CategoryDTO result = categoryMapper.toDTO(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(20L);
        assertThat(result.getName()).isEmpty();
    }

    @Test
    void toDTO_ShouldHandleCategoryWithLongName() {
        // Arrange
        Category category = new Category();
        category.setId(25L);
        category.setName("Développement Web et Applications Mobiles");

        // Act
        CategoryDTO result = categoryMapper.toDTO(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(25L);
        assertThat(result.getName()).isEqualTo("Développement Web et Applications Mobiles");
    }
}