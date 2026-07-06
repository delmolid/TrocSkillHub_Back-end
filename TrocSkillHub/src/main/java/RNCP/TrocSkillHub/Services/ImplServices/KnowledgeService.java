package RNCP.TrocSkillHub.Services.ImplServices;

import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.Mappers.KnowledgeMapper;
import RNCP.TrocSkillHub.Models.Knowledge;
import RNCP.TrocSkillHub.Repositories.KnowledgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;
    private final KnowledgeMapper knowledgeMapper;

    public KnowledgeService(KnowledgeRepository knowledgeRepository, KnowledgeMapper knowledgeMapper) {
        this.knowledgeRepository = knowledgeRepository;
        this.knowledgeMapper = knowledgeMapper;
    }

    // GET ALL
    public List<KnowledgeDTO> getAllKnowledges() {
        List<Knowledge> knowledges = knowledgeRepository.findAll();
        return knowledgeMapper.toDTOList(knowledges);
    }

    // GET BY ID
    public KnowledgeDTO getKnowledgeById(Long id) {
        Knowledge knowledge = knowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge not found with id: " + id));
        return knowledgeMapper.toDTO(knowledge);
    }

    // CREATE
    @Transactional
    public KnowledgeDTO createKnowledge(KnowledgeDTO knowledgeDTO) {
        Knowledge knowledge = knowledgeMapper.toEntity(knowledgeDTO);
        Knowledge savedKnowledge = knowledgeRepository.save(knowledge);
        return knowledgeMapper.toDTO(savedKnowledge);
    }

    // UPDATE
    @Transactional
    public KnowledgeDTO updateKnowledge(Long id, KnowledgeDTO knowledgeDTO) {
        Knowledge existingKnowledge = knowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge not found with id: " + id));

        existingKnowledge.setName(knowledgeDTO.getName());
        existingKnowledge.setCategoryId(knowledgeDTO.getCategoryId());

        Knowledge updatedKnowledge = knowledgeRepository.save(existingKnowledge);
        return knowledgeMapper.toDTO(updatedKnowledge);
    }

    // DELETE
    @Transactional
    public void deleteKnowledge(Long id) {
        Knowledge knowledge = knowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge not found with id: " + id));
        knowledgeRepository.delete(knowledge);
    }
}