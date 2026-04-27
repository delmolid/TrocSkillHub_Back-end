package RNCP.TrocSkillHub.Services;

import RNCP.TrocSkillHub.DTOs.CategoryDTO;
import RNCP.TrocSkillHub.Mappers.CategoryMapper;
import RNCP.TrocSkillHub.Models.Category;
import RNCP.TrocSkillHub.Repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_ShouldReturnListOfCategoryDTOs() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Informatique");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Cuisine");

        List<Category> categories = Arrays.asList(category1, category2);

        CategoryDTO dto1 = new CategoryDTO();
        dto1.setId(1L);
        dto1.setName("Informatique");

        CategoryDTO dto2 = new CategoryDTO();
        dto2.setId(2L);
        dto2.setName("Cuisine");

        List<CategoryDTO> expectedDTOs = Arrays.asList(dto1, dto2);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDTOList(categories)).thenReturn(expectedDTOs);

        // Act
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Informatique");
        assertThat(result.get(1).getName()).isEqualTo("Cuisine");
        
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).toDTOList(categories);
    }

    @Test
    void getAllCategories_ShouldReturnEmptyList_WhenNoCategoriesExist() {
        // Arrange
        List<Category> emptyCategories = Collections.emptyList();
        List<CategoryDTO> emptyDTOs = Collections.emptyList();

        when(categoryRepository.findAll()).thenReturn(emptyCategories);
        when(categoryMapper.toDTOList(emptyCategories)).thenReturn(emptyDTOs);

        // Act
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).toDTOList(emptyCategories);
    }

    @Test
    void getAllCategories_ShouldCallRepositoryAndMapper() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category());
        List<CategoryDTO> dtos = Arrays.asList(new CategoryDTO());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDTOList(anyList())).thenReturn(dtos);

        // Act
        categoryService.getAllCategories();

        // Assert
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).toDTOList(categories);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    void createCategory_ShouldSaveAndReturnCategoryDTO() {
        // Arrange
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("Sport");

        Category entityToSave = new Category();
        entityToSave.setName("Sport");

        Category savedEntity = new Category();
        savedEntity.setId(1L);
        savedEntity.setName("Sport");

        CategoryDTO expectedDTO = new CategoryDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Sport");

        when(categoryMapper.toEntity(inputDTO)).thenReturn(entityToSave);
        when(categoryRepository.save(entityToSave)).thenReturn(savedEntity);
        when(categoryMapper.toDTO(savedEntity)).thenReturn(expectedDTO);

        // Act
        CategoryDTO result = categoryService.createCategory(inputDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Sport");
        
        verify(categoryMapper, times(1)).toEntity(inputDTO);
        verify(categoryRepository, times(1)).save(entityToSave);
        verify(categoryMapper, times(1)).toDTO(savedEntity);
    }

    @Test
    void createCategory_ShouldFollowCorrectExecutionFlow() {
        // Arrange
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("Musique");

        Category entity = new Category();
        Category savedEntity = new Category();
        CategoryDTO outputDTO = new CategoryDTO();

        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(entity);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedEntity);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(outputDTO);

        // Act
        CategoryDTO result = categoryService.createCategory(inputDTO);

        // Assert
        assertThat(result).isNotNull();
        
        // Vérifier l'ordre d'exécution
        var inOrder = inOrder(categoryMapper, categoryRepository);
        inOrder.verify(categoryMapper).toEntity(inputDTO);
        inOrder.verify(categoryRepository).save(entity);
        inOrder.verify(categoryMapper).toDTO(savedEntity);
    }

    @Test
    void createCategory_ShouldMapInputDTOToEntity() {
        // Arrange
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("Art");

        Category entity = new Category();
        Category savedEntity = new Category();
        CategoryDTO outputDTO = new CategoryDTO();

        when(categoryMapper.toEntity(inputDTO)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(savedEntity);
        when(categoryMapper.toDTO(savedEntity)).thenReturn(outputDTO);

        // Act
        categoryService.createCategory(inputDTO);

        // Assert
        verify(categoryMapper, times(1)).toEntity(inputDTO);
    }

    @Test
    void createCategory_ShouldSaveEntityToRepository() {
        // Arrange
        CategoryDTO inputDTO = new CategoryDTO();
        Category entity = new Category();
        Category savedEntity = new Category();
        CategoryDTO outputDTO = new CategoryDTO();

        when(categoryMapper.toEntity(inputDTO)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(savedEntity);
        when(categoryMapper.toDTO(savedEntity)).thenReturn(outputDTO);

        // Act
        categoryService.createCategory(inputDTO);

        // Assert
        verify(categoryRepository, times(1)).save(entity);
    }

    @Test
    void createCategory_ShouldMapSavedEntityToDTO() {
        // Arrange
        CategoryDTO inputDTO = new CategoryDTO();
        Category entity = new Category();
        Category savedEntity = new Category();
        savedEntity.setId(5L);
        savedEntity.setName("Jardinage");

        CategoryDTO outputDTO = new CategoryDTO();

        when(categoryMapper.toEntity(inputDTO)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(savedEntity);
        when(categoryMapper.toDTO(savedEntity)).thenReturn(outputDTO);

        // Act
        categoryService.createCategory(inputDTO);

        // Assert
        verify(categoryMapper, times(1)).toDTO(savedEntity);
    }
}