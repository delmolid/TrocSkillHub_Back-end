package RNCP.TrocSkillHub.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import RNCP.TrocSkillHub.Models.Education;;;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findbyName(String name);
}
