package RNCP.TrocSkillHub.Repositories;

import RNCP.TrocSkillHub.Models.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {
}