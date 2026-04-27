package RNCP.TrocSkillHub.Mappers;

import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.Models.Knowledge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KnowledgeMapperTest {

    private KnowledgeMapper knowledgeMapper;

    @BeforeEach
    void setUp() {
        knowledgeMapper = Mappers.getMapper(KnowledgeMapper.class);
    }

    // ========== Tests pour toDTO (Knowledge → KnowledgeDTO) ==========

    @Test
    void toDTO_ShouldConvertKnowledgeToKnowledgeDTO() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(1L);
        knowledge.setName("Java Programming");
        knowledge.setLevel("Expert");
        knowledge.setCategoryId(10L);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Java Programming");
        assertThat(result.getLevel()).isEqualTo("Expert");
        assertThat(result.getCategoryId()).isEqualTo(10L);
    }

    @Test
    void toDTO_ShouldReturnNull_WhenKnowledgeIsNull() {
        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toDTO_ShouldMapAllProperties() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(99L);
        knowledge.setName("Python");
        knowledge.setLevel("Intermediate");
        knowledge.setCategoryId(5L);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
            .extracting(
                KnowledgeDTO::getId,
                KnowledgeDTO::getName,
                KnowledgeDTO::getLevel,
                KnowledgeDTO::getCategoryId
            )
            .containsExactly(99L, "Python", "Intermediate", 5L);
    }

    @Test
    void toDTO_ShouldHandleKnowledgeWithNullFields() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(10L);
        knowledge.setName("React");
        knowledge.setLevel(null);
        knowledge.setCategoryId(null);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("React");
        assertThat(result.getLevel()).isNull();
        assertThat(result.getCategoryId()).isNull();
    }

    // ========== Tests pour toEntity (KnowledgeDTO → Knowledge) ==========

    @Test
    void toEntity_ShouldConvertKnowledgeDTOToKnowledge() {
        // Arrange
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        knowledgeDTO.setId(2L);
        knowledgeDTO.setName("Spring Boot");
        knowledgeDTO.setLevel("Advanced");
        knowledgeDTO.setCategoryId(3L);

        // Act
        Knowledge result = knowledgeMapper.toEntity(knowledgeDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Spring Boot");
        assertThat(result.getLevel()).isEqualTo("Advanced");
        assertThat(result.getCategoryId()).isEqualTo(3L);
    }

    @Test
    void toEntity_ShouldReturnNull_WhenKnowledgeDTOIsNull() {
        // Act
        Knowledge result = knowledgeMapper.toEntity(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toEntity_ShouldMapAllProperties() {
        // Arrange
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        knowledgeDTO.setId(50L);
        knowledgeDTO.setName("Docker");
        knowledgeDTO.setLevel("Beginner");
        knowledgeDTO.setCategoryId(7L);

        // Act
        Knowledge result = knowledgeMapper.toEntity(knowledgeDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
            .extracting(
                Knowledge::getId,
                Knowledge::getName,
                Knowledge::getLevel,
                Knowledge::getCategoryId
            )
            .containsExactly(50L, "Docker", "Beginner", 7L);
    }

    @Test
    void toEntity_ShouldHandleKnowledgeDTOWithNullFields() {
        // Arrange
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        knowledgeDTO.setId(20L);
        knowledgeDTO.setName("Kubernetes");
        knowledgeDTO.setLevel(null);
        knowledgeDTO.setCategoryId(null);

        // Act
        Knowledge result = knowledgeMapper.toEntity(knowledgeDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(20L);
        assertThat(result.getName()).isEqualTo("Kubernetes");
        assertThat(result.getLevel()).isNull();
        assertThat(result.getCategoryId()).isNull();
    }

    // ========== Tests pour toDTOList (List<Knowledge> → List<KnowledgeDTO>) ==========

    @Test
    void toDTOList_ShouldConvertKnowledgeListToKnowledgeDTOList() {
        // Arrange
        Knowledge knowledge1 = new Knowledge();
        knowledge1.setId(1L);
        knowledge1.setName("Java");
        knowledge1.setLevel("Expert");
        knowledge1.setCategoryId(1L);

        Knowledge knowledge2 = new Knowledge();
        knowledge2.setId(2L);
        knowledge2.setName("SQL");
        knowledge2.setLevel("Intermediate");
        knowledge2.setCategoryId(2L);

        Knowledge knowledge3 = new Knowledge();
        knowledge3.setId(3L);
        knowledge3.setName("Git");
        knowledge3.setLevel("Advanced");
        knowledge3.setCategoryId(1L);

        List<Knowledge> knowledges = Arrays.asList(knowledge1, knowledge2, knowledge3);

        // Act
        List<KnowledgeDTO> result = knowledgeMapper.toDTOList(knowledges);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Java");
        assertThat(result.get(0).getLevel()).isEqualTo("Expert");
        assertThat(result.get(0).getCategoryId()).isEqualTo(1L);
        
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getName()).isEqualTo("SQL");
        assertThat(result.get(1).getLevel()).isEqualTo("Intermediate");
        assertThat(result.get(1).getCategoryId()).isEqualTo(2L);
        
        assertThat(result.get(2).getId()).isEqualTo(3L);
        assertThat(result.get(2).getName()).isEqualTo("Git");
        assertThat(result.get(2).getLevel()).isEqualTo("Advanced");
        assertThat(result.get(2).getCategoryId()).isEqualTo(1L);
    }

    @Test
    void toDTOList_ShouldReturnEmptyList_WhenKnowledgesListIsEmpty() {
        // Arrange
        List<Knowledge> emptyList = Collections.emptyList();

        // Act
        List<KnowledgeDTO> result = knowledgeMapper.toDTOList(emptyList);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void toDTOList_ShouldReturnNull_WhenKnowledgesListIsNull() {
        // Act
        List<KnowledgeDTO> result = knowledgeMapper.toDTOList(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toDTOList_ShouldHandleSingleElementList() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(10L);
        knowledge.setName("MongoDB");
        knowledge.setLevel("Beginner");
        knowledge.setCategoryId(4L);

        List<Knowledge> singleKnowledge = Collections.singletonList(knowledge);

        // Act
        List<KnowledgeDTO> result = knowledgeMapper.toDTOList(singleKnowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(0).getName()).isEqualTo("MongoDB");
        assertThat(result.get(0).getLevel()).isEqualTo("Beginner");
        assertThat(result.get(0).getCategoryId()).isEqualTo(4L);
    }

    @Test
    void toDTOList_ShouldHandleListWithMultipleLevels() {
        // Arrange
        Knowledge beginner = new Knowledge();
        beginner.setId(1L);
        beginner.setName("HTML");
        beginner.setLevel("Beginner");
        beginner.setCategoryId(1L);

        Knowledge intermediate = new Knowledge();
        intermediate.setId(2L);
        intermediate.setName("CSS");
        intermediate.setLevel("Intermediate");
        intermediate.setCategoryId(1L);

        Knowledge advanced = new Knowledge();
        advanced.setId(3L);
        advanced.setName("JavaScript");
        advanced.setLevel("Advanced");
        advanced.setCategoryId(1L);

        Knowledge expert = new Knowledge();
        expert.setId(4L);
        expert.setName("TypeScript");
        expert.setLevel("Expert");
        expert.setCategoryId(1L);

        List<Knowledge> knowledges = Arrays.asList(beginner, intermediate, advanced, expert);

        // Act
        List<KnowledgeDTO> result = knowledgeMapper.toDTOList(knowledges);

        // Assert
        assertThat(result).hasSize(4);
        assertThat(result)
            .extracting(KnowledgeDTO::getLevel)
            .containsExactly("Beginner", "Intermediate", "Advanced", "Expert");
    }

    @Test
    void toDTOList_ShouldHandleDifferentCategories() {
        // Arrange
        Knowledge k1 = new Knowledge();
        k1.setId(1L);
        k1.setName("Java");
        k1.setLevel("Expert");
        k1.setCategoryId(1L);

        Knowledge k2 = new Knowledge();
        k2.setId(2L);
        k2.setName("Photoshop");
        k2.setLevel("Intermediate");
        k2.setCategoryId(2L);

        Knowledge k3 = new Knowledge();
        k3.setId(3L);
        k3.setName("Guitar");
        k3.setLevel("Advanced");
        k3.setCategoryId(3L);

        List<Knowledge> knowledges = Arrays.asList(k1, k2, k3);

        // Act
        List<KnowledgeDTO> result = knowledgeMapper.toDTOList(knowledges);

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result)
            .extracting(KnowledgeDTO::getCategoryId)
            .containsExactly(1L, 2L, 3L);
    }

    // ========== Tests de cohérence bidirectionnelle ==========

    @Test
    void toDTO_AndToEntity_ShouldBeReversible() {
        // Arrange
        Knowledge originalKnowledge = new Knowledge();
        originalKnowledge.setId(5L);
        originalKnowledge.setName("Vue.js");
        originalKnowledge.setLevel("Intermediate");
        originalKnowledge.setCategoryId(8L);

        // Act
        KnowledgeDTO dto = knowledgeMapper.toDTO(originalKnowledge);
        Knowledge backToEntity = knowledgeMapper.toEntity(dto);

        // Assert
        assertThat(backToEntity).isNotNull();
        assertThat(backToEntity.getId()).isEqualTo(originalKnowledge.getId());
        assertThat(backToEntity.getName()).isEqualTo(originalKnowledge.getName());
        assertThat(backToEntity.getLevel()).isEqualTo(originalKnowledge.getLevel());
        assertThat(backToEntity.getCategoryId()).isEqualTo(originalKnowledge.getCategoryId());
    }

    @Test
    void toEntity_AndToDTO_ShouldBeReversible() {
        // Arrange
        KnowledgeDTO originalDTO = new KnowledgeDTO();
        originalDTO.setId(7L);
        originalDTO.setName("Angular");
        originalDTO.setLevel("Advanced");
        originalDTO.setCategoryId(9L);

        // Act
        Knowledge entity = knowledgeMapper.toEntity(originalDTO);
        KnowledgeDTO backToDTO = knowledgeMapper.toDTO(entity);

        // Assert
        assertThat(backToDTO).isNotNull();
        assertThat(backToDTO.getId()).isEqualTo(originalDTO.getId());
        assertThat(backToDTO.getName()).isEqualTo(originalDTO.getName());
        assertThat(backToDTO.getLevel()).isEqualTo(originalDTO.getLevel());
        assertThat(backToDTO.getCategoryId()).isEqualTo(originalDTO.getCategoryId());
    }

    // ========== Tests avec des valeurs particulières ==========

    @Test
    void toDTO_ShouldHandleEmptyStrings() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(15L);
        knowledge.setName("");
        knowledge.setLevel("");
        knowledge.setCategoryId(1L);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(15L);
        assertThat(result.getName()).isEmpty();
        assertThat(result.getLevel()).isEmpty();
        assertThat(result.getCategoryId()).isEqualTo(1L);
    }

    @Test
    void toDTO_ShouldHandleLongName() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(20L);
        knowledge.setName("Machine Learning and Deep Neural Networks with TensorFlow and PyTorch");
        knowledge.setLevel("Expert");
        knowledge.setCategoryId(10L);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(20L);
        assertThat(result.getName()).isEqualTo("Machine Learning and Deep Neural Networks with TensorFlow and PyTorch");
        assertThat(result.getLevel()).isEqualTo("Expert");
    }

    @Test
    void toDTO_ShouldHandleSpecialCharactersInName() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(25L);
        knowledge.setName("C++ & C#");
        knowledge.setLevel("Advanced");
        knowledge.setCategoryId(5L);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("C++ & C#");
    }

    @Test
    void toDTO_ShouldHandleZeroCategoryId() {
        // Arrange
        Knowledge knowledge = new Knowledge();
        knowledge.setId(30L);
        knowledge.setName("Test Knowledge");
        knowledge.setLevel("Beginner");
        knowledge.setCategoryId(0L);

        // Act
        KnowledgeDTO result = knowledgeMapper.toDTO(knowledge);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCategoryId()).isEqualTo(0L);
    }
}