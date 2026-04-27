package RNCP.TrocSkillHub.Services;

import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.Mappers.KnowledgeMapper;
import RNCP.TrocSkillHub.Models.Knowledge;
import RNCP.TrocSkillHub.Repositories.KnowledgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class KnowledgeServiceTest {

    @Mock
    private KnowledgeRepository knowledgeRepository;

    @Mock
    private KnowledgeMapper knowledgeMapper;

    @InjectMocks
    private KnowledgeService knowledgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ========== Tests pour getAllKnowledges() ==========

    @Test
    void getAllKnowledges_ShouldReturnListOfKnowledgeDTOs() {
        // Arrange
        // Category ID 10 = "Informatique"
        // Category ID 15 = "Cuisine"
        
        Knowledge knowledge1 = new Knowledge();
        knowledge1.setId(1L);
        knowledge1.setName("Java");
        knowledge1.setLevel("Expert");
        knowledge1.setCategoryId(10L);  // Catégorie Informatique

        Knowledge knowledge2 = new Knowledge();
        knowledge2.setId(2L);
        knowledge2.setName("Pâtisserie");
        knowledge2.setLevel("Intermediate");
        knowledge2.setCategoryId(15L);  // Catégorie Cuisine

        List<Knowledge> knowledges = Arrays.asList(knowledge1, knowledge2);

        KnowledgeDTO dto1 = new KnowledgeDTO();
        dto1.setId(1L);
        dto1.setName("Java");
        dto1.setLevel("Expert");
        dto1.setCategoryId(10L);

        KnowledgeDTO dto2 = new KnowledgeDTO();
        dto2.setId(2L);
        dto2.setName("Pâtisserie");
        dto2.setLevel("Intermediate");
        dto2.setCategoryId(15L);

        List<KnowledgeDTO> expectedDTOs = Arrays.asList(dto1, dto2);

        when(knowledgeRepository.findAll()).thenReturn(knowledges);
        when(knowledgeMapper.toDTOList(knowledges)).thenReturn(expectedDTOs);

        // Act
        List<KnowledgeDTO> result = knowledgeService.getAllKnowledges();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Java");
        assertThat(result.get(0).getCategoryId()).isEqualTo(10L);
        assertThat(result.get(1).getName()).isEqualTo("Pâtisserie");
        assertThat(result.get(1).getCategoryId()).isEqualTo(15L);

        verify(knowledgeRepository, times(1)).findAll();
        verify(knowledgeMapper, times(1)).toDTOList(knowledges);
    }

    @Test
    void getAllKnowledges_ShouldReturnEmptyList_WhenNoKnowledgesExist() {
        // Arrange
        List<Knowledge> emptyKnowledges = Collections.emptyList();
        List<KnowledgeDTO> emptyDTOs = Collections.emptyList();

        when(knowledgeRepository.findAll()).thenReturn(emptyKnowledges);
        when(knowledgeMapper.toDTOList(emptyKnowledges)).thenReturn(emptyDTOs);

        // Act
        List<KnowledgeDTO> result = knowledgeService.getAllKnowledges();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(knowledgeRepository, times(1)).findAll();
        verify(knowledgeMapper, times(1)).toDTOList(emptyKnowledges);
    }

    @Test
    void getAllKnowledges_ShouldCallRepositoryAndMapper() {
        // Arrange
        List<Knowledge> knowledges = Arrays.asList(new Knowledge());
        List<KnowledgeDTO> dtos = Arrays.asList(new KnowledgeDTO());

        when(knowledgeRepository.findAll()).thenReturn(knowledges);
        when(knowledgeMapper.toDTOList(anyList())).thenReturn(dtos);

        // Act
        knowledgeService.getAllKnowledges();

        // Assert
        verify(knowledgeRepository, times(1)).findAll();
        verify(knowledgeMapper, times(1)).toDTOList(knowledges);
        verifyNoMoreInteractions(knowledgeRepository, knowledgeMapper);
    }

    // ========== Tests pour getKnowledgeById() ==========

    @Test
    void getKnowledgeById_ShouldReturnKnowledgeDTO_WhenKnowledgeExists() {
        // Arrange
        Long knowledgeId = 1L;
        Knowledge knowledge = new Knowledge();
        knowledge.setId(knowledgeId);
        knowledge.setName("Spring Boot");
        knowledge.setLevel("Advanced");
        knowledge.setCategoryId(10L);  // Catégorie Informatique

        KnowledgeDTO expectedDTO = new KnowledgeDTO();
        expectedDTO.setId(knowledgeId);
        expectedDTO.setName("Spring Boot");
        expectedDTO.setLevel("Advanced");
        expectedDTO.setCategoryId(10L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(knowledge));
        when(knowledgeMapper.toDTO(knowledge)).thenReturn(expectedDTO);

        // Act
        KnowledgeDTO result = knowledgeService.getKnowledgeById(knowledgeId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(knowledgeId);
        assertThat(result.getName()).isEqualTo("Spring Boot");
        assertThat(result.getLevel()).isEqualTo("Advanced");
        assertThat(result.getCategoryId()).isEqualTo(10L);

        verify(knowledgeRepository, times(1)).findById(knowledgeId);
        verify(knowledgeMapper, times(1)).toDTO(knowledge);
    }

    @Test
    void getKnowledgeById_ShouldThrowException_WhenKnowledgeNotFound() {
        // Arrange
        Long knowledgeId = 999L;
        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> knowledgeService.getKnowledgeById(knowledgeId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Knowledge not found with id: " + knowledgeId);

        verify(knowledgeRepository, times(1)).findById(knowledgeId);
        verify(knowledgeMapper, never()).toDTO(any());
    }

    @Test
    void getKnowledgeById_ShouldCallRepositoryWithCorrectId() {
        // Arrange
        Long knowledgeId = 5L;
        Knowledge knowledge = new Knowledge();
        KnowledgeDTO dto = new KnowledgeDTO();

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(knowledge));
        when(knowledgeMapper.toDTO(knowledge)).thenReturn(dto);

        // Act
        knowledgeService.getKnowledgeById(knowledgeId);

        // Assert
        verify(knowledgeRepository, times(1)).findById(knowledgeId);
    }

    // ========== Tests pour createKnowledge() ==========

    @Test
    void createKnowledge_ShouldSaveAndReturnKnowledgeDTO() {
        // Arrange
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Docker");
        inputDTO.setLevel("Beginner");
        inputDTO.setCategoryId(10L);  // Catégorie Informatique

        Knowledge entityToSave = new Knowledge();
        entityToSave.setName("Docker");
        entityToSave.setLevel("Beginner");
        entityToSave.setCategoryId(10L);

        Knowledge savedEntity = new Knowledge();
        savedEntity.setId(1L);
        savedEntity.setName("Docker");
        savedEntity.setLevel("Beginner");
        savedEntity.setCategoryId(10L);

        KnowledgeDTO expectedDTO = new KnowledgeDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Docker");
        expectedDTO.setLevel("Beginner");
        expectedDTO.setCategoryId(10L);

        when(knowledgeMapper.toEntity(inputDTO)).thenReturn(entityToSave);
        when(knowledgeRepository.save(entityToSave)).thenReturn(savedEntity);
        when(knowledgeMapper.toDTO(savedEntity)).thenReturn(expectedDTO);

        // Act
        KnowledgeDTO result = knowledgeService.createKnowledge(inputDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Docker");
        assertThat(result.getLevel()).isEqualTo("Beginner");
        assertThat(result.getCategoryId()).isEqualTo(10L);

        verify(knowledgeMapper, times(1)).toEntity(inputDTO);
        verify(knowledgeRepository, times(1)).save(entityToSave);
        verify(knowledgeMapper, times(1)).toDTO(savedEntity);
    }

    @Test
    void createKnowledge_ShouldFollowCorrectExecutionFlow() {
        // Arrange
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Kubernetes");
        inputDTO.setCategoryId(10L);

        Knowledge entity = new Knowledge();
        Knowledge savedEntity = new Knowledge();
        KnowledgeDTO outputDTO = new KnowledgeDTO();

        when(knowledgeMapper.toEntity(any(KnowledgeDTO.class))).thenReturn(entity);
        when(knowledgeRepository.save(any(Knowledge.class))).thenReturn(savedEntity);
        when(knowledgeMapper.toDTO(any(Knowledge.class))).thenReturn(outputDTO);

        // Act
        KnowledgeDTO result = knowledgeService.createKnowledge(inputDTO);

        // Assert
        assertThat(result).isNotNull();

        // Vérifier l'ordre d'exécution
        var inOrder = inOrder(knowledgeMapper, knowledgeRepository);
        inOrder.verify(knowledgeMapper).toEntity(inputDTO);
        inOrder.verify(knowledgeRepository).save(entity);
        inOrder.verify(knowledgeMapper).toDTO(savedEntity);
    }

    @Test
    void createKnowledge_ShouldHandleDifferentCategories() {
        // Arrange
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Yoga");
        inputDTO.setLevel("Intermediate");
        inputDTO.setCategoryId(20L);  // Catégorie Sport

        Knowledge entity = new Knowledge();
        Knowledge savedEntity = new Knowledge();
        savedEntity.setId(10L);
        savedEntity.setCategoryId(20L);
        
        KnowledgeDTO outputDTO = new KnowledgeDTO();
        outputDTO.setId(10L);
        outputDTO.setCategoryId(20L);

        when(knowledgeMapper.toEntity(inputDTO)).thenReturn(entity);
        when(knowledgeRepository.save(entity)).thenReturn(savedEntity);
        when(knowledgeMapper.toDTO(savedEntity)).thenReturn(outputDTO);

        // Act
        KnowledgeDTO result = knowledgeService.createKnowledge(inputDTO);

        // Assert
        assertThat(result.getCategoryId()).isEqualTo(20L);
        assertThat(result.getId()).isNotEqualTo(result.getCategoryId());
    }

    // ========== Tests pour updateKnowledge() ==========

    @Test
    void updateKnowledge_ShouldUpdateAndReturnKnowledgeDTO_WhenKnowledgeExists() {
        // Arrange
        Long knowledgeId = 1L;
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("React Updated");
        inputDTO.setLevel("Expert");
        inputDTO.setCategoryId(10L);  // Catégorie Informatique

        Knowledge existingKnowledge = new Knowledge();
        existingKnowledge.setId(knowledgeId);
        existingKnowledge.setName("React");
        existingKnowledge.setLevel("Intermediate");
        existingKnowledge.setCategoryId(10L);

        Knowledge updatedEntity = new Knowledge();
        updatedEntity.setId(knowledgeId);
        updatedEntity.setName("React Updated");
        updatedEntity.setLevel("Expert");
        updatedEntity.setCategoryId(10L);

        KnowledgeDTO expectedDTO = new KnowledgeDTO();
        expectedDTO.setId(knowledgeId);
        expectedDTO.setName("React Updated");
        expectedDTO.setLevel("Expert");
        expectedDTO.setCategoryId(10L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(existingKnowledge));
        when(knowledgeRepository.save(existingKnowledge)).thenReturn(updatedEntity);
        when(knowledgeMapper.toDTO(updatedEntity)).thenReturn(expectedDTO);

        // Act
        KnowledgeDTO result = knowledgeService.updateKnowledge(knowledgeId, inputDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(knowledgeId);
        assertThat(result.getName()).isEqualTo("React Updated");
        assertThat(result.getLevel()).isEqualTo("Expert");
        assertThat(result.getCategoryId()).isEqualTo(10L);

        verify(knowledgeRepository, times(1)).findById(knowledgeId);
        verify(knowledgeRepository, times(1)).save(existingKnowledge);
        verify(knowledgeMapper, times(1)).toDTO(updatedEntity);
    }

    @Test
    void updateKnowledge_ShouldThrowException_WhenKnowledgeNotFound() {
        // Arrange
        Long knowledgeId = 999L;
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Non-existent");
        inputDTO.setCategoryId(10L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> knowledgeService.updateKnowledge(knowledgeId, inputDTO))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Knowledge not found with id: " + knowledgeId);

        verify(knowledgeRepository, times(1)).findById(knowledgeId);
        verify(knowledgeRepository, never()).save(any());
        verify(knowledgeMapper, never()).toDTO(any());
    }

    @Test
    void updateKnowledge_ShouldUpdateAllFields() {
        // Arrange
        Long knowledgeId = 2L;
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Vue.js");
        inputDTO.setLevel("Advanced");
        inputDTO.setCategoryId(10L);  // Catégorie Informatique

        Knowledge existingKnowledge = new Knowledge();
        existingKnowledge.setId(knowledgeId);
        existingKnowledge.setName("Old Name");
        existingKnowledge.setLevel("Old Level");
        existingKnowledge.setCategoryId(15L);  // Ancienne catégorie Cuisine

        Knowledge updatedEntity = new Knowledge();
        KnowledgeDTO outputDTO = new KnowledgeDTO();

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(existingKnowledge));
        when(knowledgeRepository.save(existingKnowledge)).thenReturn(updatedEntity);
        when(knowledgeMapper.toDTO(updatedEntity)).thenReturn(outputDTO);

        // Act
        knowledgeService.updateKnowledge(knowledgeId, inputDTO);

        // Assert
        assertThat(existingKnowledge.getName()).isEqualTo("Vue.js");
        assertThat(existingKnowledge.getLevel()).isEqualTo("Advanced");
        assertThat(existingKnowledge.getCategoryId()).isEqualTo(10L);

        verify(knowledgeRepository, times(1)).save(existingKnowledge);
    }

    @Test
    void updateKnowledge_ShouldFollowCorrectExecutionFlow() {
        // Arrange
        Long knowledgeId = 3L;
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Angular");
        inputDTO.setCategoryId(10L);

        Knowledge existingKnowledge = new Knowledge();
        Knowledge updatedEntity = new Knowledge();
        KnowledgeDTO outputDTO = new KnowledgeDTO();

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(existingKnowledge));
        when(knowledgeRepository.save(existingKnowledge)).thenReturn(updatedEntity);
        when(knowledgeMapper.toDTO(updatedEntity)).thenReturn(outputDTO);

        // Act
        knowledgeService.updateKnowledge(knowledgeId, inputDTO);

        // Assert
        var inOrder = inOrder(knowledgeRepository, knowledgeMapper);
        inOrder.verify(knowledgeRepository).findById(knowledgeId);
        inOrder.verify(knowledgeRepository).save(existingKnowledge);
        inOrder.verify(knowledgeMapper).toDTO(updatedEntity);
    }

    @Test
    void updateKnowledge_ShouldChangeCategory() {
        // Arrange - Un knowledge passe de "Informatique" à "Sport"
        Long knowledgeId = 7L;
        KnowledgeDTO inputDTO = new KnowledgeDTO();
        inputDTO.setName("Course à pied");
        inputDTO.setLevel("Intermediate");
        inputDTO.setCategoryId(20L);  // Nouvelle catégorie Sport

        Knowledge existingKnowledge = new Knowledge();
        existingKnowledge.setId(knowledgeId);
        existingKnowledge.setName("Course à pied");
        existingKnowledge.setLevel("Beginner");
        existingKnowledge.setCategoryId(10L);  // Ancienne catégorie Informatique (erreur)

        Knowledge updatedEntity = new Knowledge();
        updatedEntity.setCategoryId(20L);
        
        KnowledgeDTO outputDTO = new KnowledgeDTO();
        outputDTO.setCategoryId(20L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(existingKnowledge));
        when(knowledgeRepository.save(existingKnowledge)).thenReturn(updatedEntity);
        when(knowledgeMapper.toDTO(updatedEntity)).thenReturn(outputDTO);

        // Act
        KnowledgeDTO result = knowledgeService.updateKnowledge(knowledgeId, inputDTO);

        // Assert
        assertThat(existingKnowledge.getCategoryId()).isEqualTo(20L);
        assertThat(result.getCategoryId()).isEqualTo(20L);
    }

    // ========== Tests pour deleteKnowledge() ==========

    @Test
    void deleteKnowledge_ShouldDeleteKnowledge_WhenKnowledgeExists() {
        // Arrange
        Long knowledgeId = 1L;
        Knowledge knowledge = new Knowledge();
        knowledge.setId(knowledgeId);
        knowledge.setName("MongoDB");
        knowledge.setCategoryId(10L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(knowledge));
        doNothing().when(knowledgeRepository).delete(knowledge);

        // Act
        knowledgeService.deleteKnowledge(knowledgeId);

        // Assert
        verify(knowledgeRepository, times(1)).findById(knowledgeId);
        verify(knowledgeRepository, times(1)).delete(knowledge);
    }

    @Test
    void deleteKnowledge_ShouldThrowException_WhenKnowledgeNotFound() {
        // Arrange
        Long knowledgeId = 999L;
        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> knowledgeService.deleteKnowledge(knowledgeId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Knowledge not found with id: " + knowledgeId);

        verify(knowledgeRepository, times(1)).findById(knowledgeId);
        verify(knowledgeRepository, never()).delete(any());
    }

    @Test
    void deleteKnowledge_ShouldCallRepositoryWithCorrectEntity() {
        // Arrange
        Long knowledgeId = 5L;
        Knowledge knowledge = new Knowledge();
        knowledge.setId(knowledgeId);
        knowledge.setCategoryId(15L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(knowledge));
        doNothing().when(knowledgeRepository).delete(knowledge);

        // Act
        knowledgeService.deleteKnowledge(knowledgeId);

        // Assert
        verify(knowledgeRepository, times(1)).delete(knowledge);
    }

    @Test
    void deleteKnowledge_ShouldFollowCorrectExecutionFlow() {
        // Arrange
        Long knowledgeId = 10L;
        Knowledge knowledge = new Knowledge();
        knowledge.setCategoryId(20L);

        when(knowledgeRepository.findById(knowledgeId)).thenReturn(Optional.of(knowledge));
        doNothing().when(knowledgeRepository).delete(knowledge);

        // Act
        knowledgeService.deleteKnowledge(knowledgeId);

        // Assert
        var inOrder = inOrder(knowledgeRepository);
        inOrder.verify(knowledgeRepository).findById(knowledgeId);
        inOrder.verify(knowledgeRepository).delete(knowledge);
    }
}