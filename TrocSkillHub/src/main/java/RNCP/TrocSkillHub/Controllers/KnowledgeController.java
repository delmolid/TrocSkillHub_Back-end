package RNCP.TrocSkillHub.Controllers;

import RNCP.TrocSkillHub.DTOs.KnowledgeDTO;
import RNCP.TrocSkillHub.Services.ImplServices.KnowledgeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledges")
public class KnowledgeController {
    
    private final KnowledgeService knowledgeService;
    
    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }
    
    // GET /api/knowledges - Liste tous les knowledges
    @GetMapping
    public ResponseEntity<List<KnowledgeDTO>> getAllKnowledges() {
        List<KnowledgeDTO> knowledges = knowledgeService.getAllKnowledges();
        return ResponseEntity.ok(knowledges);
    }
    
    // GET /api/knowledges/{id} - Récupère un knowledge par son ID
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> getKnowledgeById(@PathVariable Long id) {
        KnowledgeDTO knowledge = knowledgeService.getKnowledgeById(id);
        return ResponseEntity.ok(knowledge);
    }
    
    // POST /api/knowledges - Crée un nouveau knowledge
    @PostMapping
    public ResponseEntity<KnowledgeDTO> createKnowledge(@RequestBody KnowledgeDTO knowledgeDTO) {
        KnowledgeDTO created = knowledgeService.createKnowledge(knowledgeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    // PUT /api/knowledges/{id} - Met à jour un knowledge existant
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> updateKnowledge(
            @PathVariable Long id, 
            @RequestBody KnowledgeDTO knowledgeDTO) {
        KnowledgeDTO updated = knowledgeService.updateKnowledge(id, knowledgeDTO);
        return ResponseEntity.ok(updated);
    }
    
    // DELETE /api/knowledges/{id} - Supprime un knowledge
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(@PathVariable Long id) {
        knowledgeService.deleteKnowledge(id);
        return ResponseEntity.noContent().build();
    }
}