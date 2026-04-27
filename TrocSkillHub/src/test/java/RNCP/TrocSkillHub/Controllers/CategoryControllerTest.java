package RNCP.TrocSkillHub.Controllers;

import RNCP.TrocSkillHub.DTOs.CategoryDTO;
import RNCP.TrocSkillHub.Services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_ShouldReturnListOfCategories() {
        // Arrange
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("Informatique");

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Cuisine");

        List<CategoryDTO> mockCategories = Arrays.asList(category1, category2);
        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        // Act
        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Informatique");
        assertThat(response.getBody().get(1).getName()).isEqualTo("Cuisine");
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getAllCategories_ShouldReturnEmptyList_WhenNoCategories() {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void createCategory_ShouldReturnCreatedCategory() {
        // Arrange
        CategoryDTO inputCategory = new CategoryDTO();
        inputCategory.setName("Sport");

        CategoryDTO createdCategory = new CategoryDTO();
        createdCategory.setId(1L);
        createdCategory.setName("Sport");

        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(createdCategory);

        // Act
        ResponseEntity<CategoryDTO> response = categoryController.createCategory(inputCategory);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Sport");
        verify(categoryService, times(1)).createCategory(inputCategory);
    }

    @Test
    void createCategory_ShouldCallServiceWithCorrectParameter() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Musique");

        CategoryDTO createdCategory = new CategoryDTO();
        createdCategory.setId(3L);
        createdCategory.setName("Musique");

        when(categoryService.createCategory(categoryDTO)).thenReturn(createdCategory);

        // Act
        ResponseEntity<CategoryDTO> response = categoryController.createCategory(categoryDTO);

        // Assert
        assertThat(response.getBody())
            .isNotNull()
            .extracting(CategoryDTO::getId, CategoryDTO::getName)
            .containsExactly(3L, "Musique");
        verify(categoryService, times(1)).createCategory(categoryDTO);
    }
}