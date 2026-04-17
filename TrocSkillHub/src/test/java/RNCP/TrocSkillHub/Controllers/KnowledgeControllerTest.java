package RNCP.TrocSkillHub.Controllers;

import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.Services.KnowledgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class KnowledgeControllerTest {

    @Mock
    private KnowledgeService knowledgeService;

    @InjectMocks
    private KnowledgeController knowledgeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ========== Tests pour getAllKnowledges() ==========

    @Test
    void getAllKnowledges_ShouldReturnListOfKnowledges() {
        // Arrange
        KnowledgeDTO knowledge1 = new KnowledgeDTO();
        knowledge1.setId(1L);
        knowledge1.setName("Java");
        knowledge1.setLevel("Expert");
        knowledge1.setCategoryId(1L);

        KnowledgeDTO knowledge2 = new KnowledgeDTO();
        knowledge2.setId(2L);
        knowledge2.setName("Python");
        knowledge2.setLevel("Intermediate");
        knowledge2.setCategoryId(2L);

        List<KnowledgeDTO> mockKnowledges = Arrays.asList(knowledge1, knowledge2);
        when(knowledgeService.getAllKnowledges()).thenReturn(mockKnowledges);

        // Act
        ResponseEntity<List<KnowledgeDTO>> response = knowledgeController.getAllKnowledges();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Java");
        assertThat(response.getBody().get(1).getName()).isEqualTo("Python");
        verify(knowledgeService, times(1)).getAllKnowledges();
    }

    @Test
    void getAllKnowledges_ShouldReturnEmptyList_WhenNoKnowledges() {
        // Arrange
        when(knowledgeService.getAllKnowledges()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<KnowledgeDTO>> response = knowledgeController.getAllKnowledges();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(knowledgeService, times(1)).getAllKnowledges();
    }

    // ========== Tests pour getKnowledgeById() ==========

    @Test
    void getKnowledgeById_ShouldReturnKnowledge_WhenIdExists() {
        // Arrange
        Long knowledgeId = 1L;
        KnowledgeDTO mockKnowledge = new KnowledgeDTO();
        mockKnowledge.setId(knowledgeId);
        mockKnowledge.setName("Spring Boot");
        mockKnowledge.setLevel("Advanced");
        mockKnowledge.setCategoryId(3L);

        when(knowledgeService.getKnowledgeById(knowledgeId)).thenReturn(mockKnowledge);

        // Act
        ResponseEntity<KnowledgeDTO> response = knowledgeController.getKnowledgeById(knowledgeId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(knowledgeId);
        assertThat(response.getBody().getName()).isEqualTo("Spring Boot");
        assertThat(response.getBody().getLevel()).isEqualTo("Advanced");
        verify(knowledgeService, times(1)).getKnowledgeById(knowledgeId);
    }

    @Test
    void getKnowledgeById_ShouldCallServiceWithCorrectId() {
        // Arrange
        Long knowledgeId = 5L;
        KnowledgeDTO mockKnowledge = new KnowledgeDTO();
        mockKnowledge.setId(knowledgeId);

        when(knowledgeService.getKnowledgeById(knowledgeId)).thenReturn(mockKnowledge);

        // Act
        knowledgeController.getKnowledgeById(knowledgeId);

        // Assert
        verify(knowledgeService, times(1)).getKnowledgeById(knowledgeId);
    }

    // ========== Tests pour createKnowledge() ==========

    @Test
    void createKnowledge_ShouldReturnCreatedKnowledge() {
        // Arrange
        KnowledgeDTO inputKnowledge = new KnowledgeDTO();
        inputKnowledge.setName("Docker");
        inputKnowledge.setLevel("Beginner");
        inputKnowledge.setCategoryId(4L);

        KnowledgeDTO createdKnowledge = new KnowledgeDTO();
        createdKnowledge.setId(1L);
        createdKnowledge.setName("Docker");
        createdKnowledge.setLevel("Beginner");
        createdKnowledge.setCategoryId(4L);

        when(knowledgeService.createKnowledge(any(KnowledgeDTO.class))).thenReturn(createdKnowledge);

        // Act
        ResponseEntity<KnowledgeDTO> response = knowledgeController.createKnowledge(inputKnowledge);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Docker");
        assertThat(response.getBody().getLevel()).isEqualTo("Beginner");
        verify(knowledgeService, times(1)).createKnowledge(inputKnowledge);
    }

    @Test
    void createKnowledge_ShouldCallServiceWithCorrectParameter() {
        // Arrange
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        knowledgeDTO.setName("Kubernetes");
        knowledgeDTO.setLevel("Intermediate");
        knowledgeDTO.setCategoryId(5L);

        KnowledgeDTO createdKnowledge = new KnowledgeDTO();
        createdKnowledge.setId(2L);
        createdKnowledge.setName("Kubernetes");

        when(knowledgeService.createKnowledge(knowledgeDTO)).thenReturn(createdKnowledge);

        // Act
        knowledgeController.createKnowledge(knowledgeDTO);

        // Assert
        verify(knowledgeService, times(1)).createKnowledge(knowledgeDTO);
    }

    // ========== Tests pour updateKnowledge() ==========

    @Test
    void updateKnowledge_ShouldReturnUpdatedKnowledge() {
        // Arrange
        Long knowledgeId = 1L;
        KnowledgeDTO inputKnowledge = new KnowledgeDTO();
        inputKnowledge.setName("React");
        inputKnowledge.setLevel("Advanced");
        inputKnowledge.setCategoryId(6L);

        KnowledgeDTO updatedKnowledge = new KnowledgeDTO();
        updatedKnowledge.setId(knowledgeId);
        updatedKnowledge.setName("React");
        updatedKnowledge.setLevel("Advanced");
        updatedKnowledge.setCategoryId(6L);

        when(knowledgeService.updateKnowledge(eq(knowledgeId), any(KnowledgeDTO.class)))
            .thenReturn(updatedKnowledge);

        // Act
        ResponseEntity<KnowledgeDTO> response = knowledgeController.updateKnowledge(knowledgeId, inputKnowledge);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(knowledgeId);
        assertThat(response.getBody().getName()).isEqualTo("React");
        assertThat(response.getBody().getLevel()).isEqualTo("Advanced");
        verify(knowledgeService, times(1)).updateKnowledge(knowledgeId, inputKnowledge);
    }

    @Test
    void updateKnowledge_ShouldCallServiceWithCorrectParameters() {
        // Arrange
        Long knowledgeId = 3L;
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        knowledgeDTO.setName("Vue.js");
        knowledgeDTO.setLevel("Expert");
        knowledgeDTO.setCategoryId(7L);

        KnowledgeDTO updatedKnowledge = new KnowledgeDTO();
        updatedKnowledge.setId(knowledgeId);

        when(knowledgeService.updateKnowledge(knowledgeId, knowledgeDTO)).thenReturn(updatedKnowledge);

        // Act
        knowledgeController.updateKnowledge(knowledgeId, knowledgeDTO);

        // Assert
        verify(knowledgeService, times(1)).updateKnowledge(knowledgeId, knowledgeDTO);
    }

    @Test
    void updateKnowledge_ShouldUpdateDifferentFields() {
        // Arrange
        Long knowledgeId = 10L;
        KnowledgeDTO inputKnowledge = new KnowledgeDTO();
        inputKnowledge.setName("Angular");
        inputKnowledge.setLevel("Beginner");
        inputKnowledge.setCategoryId(8L);

        KnowledgeDTO updatedKnowledge = new KnowledgeDTO();
        updatedKnowledge.setId(knowledgeId);
        updatedKnowledge.setName("Angular");
        updatedKnowledge.setLevel("Beginner");
        updatedKnowledge.setCategoryId(8L);

        when(knowledgeService.updateKnowledge(knowledgeId, inputKnowledge)).thenReturn(updatedKnowledge);

        // Act
        ResponseEntity<KnowledgeDTO> response = knowledgeController.updateKnowledge(knowledgeId, inputKnowledge);

        // Assert
        assertThat(response.getBody())
            .extracting(
                KnowledgeDTO::getName,
                KnowledgeDTO::getLevel,
                KnowledgeDTO::getCategoryId
            )
            .containsExactly("Angular", "Beginner", 8L);
    }

    // ========== Tests pour deleteKnowledge() ==========

    @Test
    void deleteKnowledge_ShouldReturnNoContent() {
        // Arrange
        Long knowledgeId = 1L;
        doNothing().when(knowledgeService).deleteKnowledge(knowledgeId);

        // Act
        ResponseEntity<Void> response = knowledgeController.deleteKnowledge(knowledgeId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(knowledgeService, times(1)).deleteKnowledge(knowledgeId);
    }

    @Test
    void deleteKnowledge_ShouldCallServiceWithCorrectId() {
        // Arrange
        Long knowledgeId = 5L;
        doNothing().when(knowledgeService).deleteKnowledge(knowledgeId);

        // Act
        knowledgeController.deleteKnowledge(knowledgeId);

        // Assert
        verify(knowledgeService, times(1)).deleteKnowledge(knowledgeId);
    }

    @Test
    void deleteKnowledge_ShouldNotReturnBody() {
        // Arrange
        Long knowledgeId = 10L;
        doNothing().when(knowledgeService).deleteKnowledge(knowledgeId);

        // Act
        ResponseEntity<Void> response = knowledgeController.deleteKnowledge(knowledgeId);

        // Assert
        assertThat(response.hasBody()).isFalse();
        assertThat(response.getBody()).isNull();
    }

    // ========== Tests de vérification des interactions ==========

    @Test
    void getAllKnowledges_ShouldNotHaveUnwantedInteractions() {
        // Arrange
        when(knowledgeService.getAllKnowledges()).thenReturn(Collections.emptyList());

        // Act
        knowledgeController.getAllKnowledges();

        // Assert
        verify(knowledgeService, times(1)).getAllKnowledges();
        verifyNoMoreInteractions(knowledgeService);
    }

    @Test
    void createKnowledge_ShouldNotHaveUnwantedInteractions() {
        // Arrange
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        KnowledgeDTO createdKnowledge = new KnowledgeDTO();
        
        when(knowledgeService.createKnowledge(knowledgeDTO)).thenReturn(createdKnowledge);

        // Act
        knowledgeController.createKnowledge(knowledgeDTO);

        // Assert
        verify(knowledgeService, times(1)).createKnowledge(knowledgeDTO);
        verifyNoMoreInteractions(knowledgeService);
    }
}